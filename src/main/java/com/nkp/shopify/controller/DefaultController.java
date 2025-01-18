package com.nkp.shopify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DefaultController {

    @GetMapping("/default")
    public String defaultRoute() {
        return "Some error occurred, Please check URL";
    }
}
