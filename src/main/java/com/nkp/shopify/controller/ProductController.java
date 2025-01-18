package com.nkp.shopify.controller;

import com.nkp.shopify.entities.ProductEntity;
import com.nkp.shopify.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public List<ProductEntity> showProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ProductEntity getProductById(@PathVariable long id) {
        return productRepository.findById(id).orElse(null);
    }
    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        return productRepository.save(product);
    }

}
