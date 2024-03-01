package com.akhtyamov.springeshopnews.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/news")
    public String news(){
        return "news";
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
