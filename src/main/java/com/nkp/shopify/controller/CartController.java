package com.nkp.shopify.controller;

import com.nkp.shopify.entities.CartEntity;
import com.nkp.shopify.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public List<CartEntity> getAll(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
            return cartService.getAllCartItems(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartEntity item, HttpServletRequest request) {
        try {
            // Extract JWT token from the Authorization header
            String authHeader = request.getHeader("Authorization");
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
            CartEntity cartEntity = cartService.addItemsToCart(item, token);
            return ResponseEntity.status(200).body("Product " + cartEntity.getProductId() + " added to cart with " + cartEntity.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody Map<String, Long> requestBody) {
        try {
            return cartService.updateCartItem(requestBody.get("id"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeFromCart(@PathVariable long id) {
        if (cartService.removeItemFromCart(id)) {
            return ResponseEntity.status(200).body("Product removed successfully from cart");
        }
        return ResponseEntity.status(500).body("Error Occurred");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        if (cartService.clearCart()) {
            return ResponseEntity.status(200).body("Cart Cleared successfully");
        }
        return ResponseEntity.status(500).body("Error Occurred");
    }
}
