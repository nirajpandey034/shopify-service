package com.nkp.shopify.service;

import com.nkp.shopify.entities.UserEntity;
import com.nkp.shopify.entities.UserPrinciple;
import com.nkp.shopify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserEntity createUser(UserEntity userEntity) {
        try {
            userEntity.setPassword(encoder.encode(userEntity.getPassword()));
            return userRepository.save(userEntity);
        } catch (Exception e) {
            return new UserEntity();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserPrinciple(user);
    }
}
