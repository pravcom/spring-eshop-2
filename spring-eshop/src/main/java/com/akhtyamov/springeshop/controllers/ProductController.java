package com.akhtyamov.springeshop.controllers;

import com.akhtyamov.springeshop.dto.BucketDTO;
import com.akhtyamov.springeshop.dto.ProductDTO;
import com.akhtyamov.springeshop.service.BucketService;
import com.akhtyamov.springeshop.service.ProductService;
import com.akhtyamov.springeshop.service.SessionObjectHolder;
import com.akhtyamov.springeshop.service.UserService;
import com.akhtyamov.springeshop.ws.bucket.Bucket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final BucketService bucketService;
    private final SessionObjectHolder sessionObjectHolder;

    public ProductController(ProductService productService, BucketService bucketService, SessionObjectHolder sessionObjectHolder) {
        this.productService = productService;
        this.bucketService = bucketService;
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @GetMapping
    public String list(Model model, Principal principal) {
        sessionObjectHolder.addClick();
        List<ProductDTO> productDTOS = productService.getAll();
        model.addAttribute("products", productDTOS);

        if (principal==null){
            model.addAttribute("bucket", new BucketDTO(0,0.0,null));
        }else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        }

        return "products";
    }

    @GetMapping("{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal){
        sessionObjectHolder.addClick();
        if (principal==null) return "redirect:/products";

        productService.addToUserBucket(id,principal.getName());
        return "redirect:/products";
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(ProductDTO dto){
        productService.addProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @MessageMapping("/products")
    public void messageAddProduct(ProductDTO dto){
        productService.addProduct(dto);
    }

    @MessageMapping("/bucket")
    public void messageAddProductToBucket(Bucket bucket,Principal principal){
        if (principal==null) return;
        productService.addToUserBucket(bucket.getId(),principal.getName());
    }
}
