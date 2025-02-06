package com.nkp.shopify.controller;

import com.nkp.shopify.entities.UserEntity;
import com.nkp.shopify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public UserEntity createUser(@RequestBody UserEntity userEntity) {
        return userService.createUser(userEntity);
    }

    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody UserEntity userEntity) throws Exception {
        return userService.loginUser(userEntity);
    }
}
