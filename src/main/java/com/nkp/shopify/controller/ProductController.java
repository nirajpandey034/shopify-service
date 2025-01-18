package com.nkp.shopify.controller;

import com.nkp.shopify.entities.ProductEntity;
import com.nkp.shopify.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<ProductEntity> showProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductEntity getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/updateTitle")
    public ProductEntity updateProductname(@RequestBody ProductEntity product) {
        return productService.updateProductname(product);
    }

    @PutMapping("/updateDescription")
    public ProductEntity updateProductdescription(@RequestBody ProductEntity product) {
        return productService.updateProductdescription(product);
    }

    @PutMapping("/updatePrice")
    public ProductEntity updateProductprice(@RequestBody ProductEntity product) {
        return productService.updateProductprice(product);
    }
}
