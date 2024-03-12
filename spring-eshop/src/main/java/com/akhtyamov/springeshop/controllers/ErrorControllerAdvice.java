package com.akhtyamov.springeshop.controllers;

import com.akhtyamov.springeshop.dto.BucketDTO;
import com.akhtyamov.springeshop.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

@ControllerAdvice
public class ErrorControllerAdvice {
    private final BucketService bucketService;

    public ErrorControllerAdvice(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Exception exception, Model model, Principal principal) {
        String errorMessage = (exception != null ? exception.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
//Добавляем bucket
//        if (principal==null){
//            model.addAttribute("bucket", new BucketDTO(0,0.0,null));
//        }else {
//            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
//            model.addAttribute("bucket", bucketDTO);
//        }

        return "error";
    }
}
