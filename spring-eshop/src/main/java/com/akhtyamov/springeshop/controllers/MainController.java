package com.akhtyamov.springeshop.controllers;

import com.akhtyamov.springeshop.dto.BucketDTO;
import com.akhtyamov.springeshop.service.BucketService;
import com.akhtyamov.springeshop.service.SessionObjectHolder;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.UUID;

@Controller
public class MainController {

    private final SessionObjectHolder sessionObjectHolder;
    private final BucketService bucketService;

    public MainController(SessionObjectHolder sessionObjectHolder, BucketService bucketService) {
        this.sessionObjectHolder = sessionObjectHolder;
        this.bucketService = bucketService;
    }

    @RequestMapping({"", "/"})
    public String index(Model model, HttpSession httpSession, Principal principal) {
        model.addAttribute("amountClicks", sessionObjectHolder.getAmountClicks());
        if(httpSession.getAttribute("myID")==null){
            String uuid = UUID.randomUUID().toString();
            httpSession.setAttribute("myID", uuid);
            System.out.println("Generated UUID -> "+uuid);
        }
        model.addAttribute("uuid", httpSession.getAttribute("myID"));

        if (principal==null){
            model.addAttribute("bucket", new BucketDTO(0,0.0,null));
        }else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        }

        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
