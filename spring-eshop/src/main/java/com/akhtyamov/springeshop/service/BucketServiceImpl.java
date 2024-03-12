package com.akhtyamov.springeshop.service;

import com.akhtyamov.springeshop.dao.BucketRepository;
import com.akhtyamov.springeshop.dao.ProductRepository;
import com.akhtyamov.springeshop.domain.*;
import com.akhtyamov.springeshop.dto.BucketDTO;
import com.akhtyamov.springeshop.dto.BucketDetailDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final SimpMessagingTemplate template;
    private final OrderService orderService;

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> products = getCollectRefProductsByIds(productIds);
        bucket.setProducts(products);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                //getOne вытаскивает ссылку на объект, findById - вытасквает сам объект
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addProduct(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProducts = products == null ? new ArrayList<>() : new ArrayList<>(products);

        newProducts.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProducts);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);
        if (user == null || user.getBucket() == null) {
            return new BucketDTO();
        }

        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();

        for (Product product : products) {
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new BucketDetailDTO(product));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1)));
                detail.setSum((detail.getSum()) + Double.valueOf(product.getPrice().toString()));
            }

            bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
            bucketDTO.aggregate();
        }

        return bucketDTO;
    }

    @Override
    public void removeProduct(Long id, Principal principal) {
        User user = userService.findByName(principal.getName());
        if (user == null) throw new RuntimeException("User not found - " + principal.getName());

        Bucket bucket = user.getBucket();
        bucket.getProducts().removeIf(element -> element.getId() == id);
        bucketRepository.delete(bucket);
        bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    public void commitBucketToOrder(String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Bucket bucket = user.getBucket();
        if (bucket == null || bucket.getProducts().isEmpty()) {
            return;
        }
        // Создаем заказ
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);

        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetail> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetail(order, pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setDetails(orderDetails);
        order.setSum(total);
        order.setAddress("none");

        orderService.saveOrder(order);
        bucket.getProducts().clear();
        bucketRepository.save(bucket);

    }
}
