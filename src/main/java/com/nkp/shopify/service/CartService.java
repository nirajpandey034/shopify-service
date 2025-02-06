package com.nkp.shopify.service;

import com.nkp.shopify.entities.CartEntity;
import com.nkp.shopify.entities.UserEntity;
import com.nkp.shopify.repository.CartRepository;
import com.nkp.shopify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    public List<CartEntity> getAllCartItems(String token) {
        try {
            String username = jwtService.extractUsername(token);
            UserEntity user = userRepository.findByEmail(username);
            Long userId = user.getId();
            return cartRepository.findAllByUserId(userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public CartEntity addItemsToCart(CartEntity data, String token) {
        try {
            // extract username from token
            String username = jwtService.extractUsername(token);

            // fetch user details from user id
            UserEntity userEntity = userRepository.findById(data.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // get requester username
            String requesterUsername = userEntity.getEmail();

            // compare and do save operation
            if (Objects.equals(username, requesterUsername)) {
                return cartRepository.save(data);
            }
            throw new IllegalArgumentException("Username mismatch. Please provide correct user id.");
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public ResponseEntity<?> updateCartItem(Long id) {
        try {
            Optional<CartEntity> item = cartRepository.findById(id);
            if (item.isPresent()) {
                // Increment the quantity first
                cartRepository.incrementQuantityById(id);

                // Adjust the price based on the new quantity
                cartRepository.adjustPriceById(id);
                return ResponseEntity.ok("Quantity increased for item with ID: " + id);
            }
            return ResponseEntity.status(400).body("cart Id not found");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean removeItemFromCart(long id) {
        try {
            cartRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean clearCart() {
        try {
            cartRepository.deleteAll();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
