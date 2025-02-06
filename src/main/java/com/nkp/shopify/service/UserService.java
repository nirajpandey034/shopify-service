package com.nkp.shopify.service;

import com.nkp.shopify.entities.UserEntity;
import com.nkp.shopify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserEntity createUser(UserEntity userEntity) {
        try {
            userEntity.setPassword(encoder.encode(userEntity.getPassword()));
            return userRepository.save(userEntity);
        } catch (Exception e) {
            return new UserEntity();
        }
    }

    public Map<String, Object> loginUser(UserEntity userEntity) throws Exception {
        Map<String, Object> obj = new HashMap<>();
        String token = jwtService.generateToken(userEntity.getEmail());
        Object id = userRepository.findByEmail(userEntity.getEmail()).getId();

        obj.put("token", token);
        obj.put("id", id);

        return obj;
    }
}
