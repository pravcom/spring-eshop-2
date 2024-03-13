package com.akhtyamov.springeshop.service;

import com.akhtyamov.springeshop.dao.ProductRepository;
import com.akhtyamov.springeshop.domain.Bucket;
import com.akhtyamov.springeshop.domain.Product;
import com.akhtyamov.springeshop.domain.User;
import com.akhtyamov.springeshop.dto.BucketDTO;
import com.akhtyamov.springeshop.dto.ProductDTO;
import com.akhtyamov.springeshop.mapper.ProductMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;
    private final SimpMessagingTemplate template;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService, SimpMessagingTemplate template) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
        this.template = template;
    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    @Transactional
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found - " + username);
        }

        Bucket bucket = user.getBucket();
        Bucket newBucket = null;
        if (bucket == null) {
            newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        } else {
            bucketService.addProduct(bucket, Collections.singletonList(productId));
        }
        //Добавляем Web Socket для динмаическго добавления продуктов в Bucket
        com.akhtyamov.springeshop.ws.bucket.Bucket bucketSW = new com.akhtyamov.springeshop.ws.bucket.Bucket();
        BucketDTO bucketDTO = bucketService.getBucketByUser(username);

        if (bucket ==null) {
            bucketSW.setId(newBucket.getId());
        }else {
            bucketSW.setId(bucket.getId());
        }

        bucketSW.setSum(bucketDTO.getSum());
        template.convertAndSend("/topic/bucket", bucketSW);
    }

    @Override
    @Transactional
    public void addProduct(ProductDTO dto) {
        Product product = mapper.toProduct(dto);
        Product savedProduct = productRepository.save(product);
        template.convertAndSend("/topic/products",ProductMapper.MAPPER.fromProduct(savedProduct));
    }

    @Override
    public ProductDTO getById(Long id) {
        Product product = productRepository.findById(id).orElse(new Product());
        return ProductMapper.MAPPER.fromProduct(product);

    }


}
