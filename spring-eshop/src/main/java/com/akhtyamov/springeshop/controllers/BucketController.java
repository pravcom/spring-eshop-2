package com.akhtyamov.springeshop.controllers;

import com.akhtyamov.springeshop.dto.BucketDTO;
import com.akhtyamov.springeshop.service.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
//@RequestMapping("/bucket")
public class BucketController {
    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal){
        if (principal==null){
            model.addAttribute("bucket", new BucketDTO());
        }else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        }
        return "bucket";
    }

    @GetMapping("{id}/remove")
    public String remove(@PathVariable Long id, Principal principal){
        if (principal!=null) {
            bucketService.removeProduct(id, principal);
        }
        return "redirect:/bucket";
    }

    @PostMapping("/bucket")
    public String commitBucket(Principal principal){
        if (principal!=null){
            bucketService.commitBucketToOrder(principal.getName());
        }
        return "redirect:/bucket";
    }

}
