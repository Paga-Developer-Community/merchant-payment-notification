package com.pagatech.merchant.notification.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @PostMapping("/test")
    public String testService(){
        return "test service";
    }

    @PostMapping("/svc/v1/public/accounts/{accountNumber}")
    public String getPublicAccountDataLinkedTo(@PathVariable final int accountNumber){
        return "Public Account Linked To : "+accountNumber;
    }

    @GetMapping("/svc/v1/private/accounts/{accountNumber}")
    public String getPrivateAccountDataLinkedTo(@PathVariable final int accountNumber){
        return "Public Account Linked To : "+accountNumber;
    }
}
