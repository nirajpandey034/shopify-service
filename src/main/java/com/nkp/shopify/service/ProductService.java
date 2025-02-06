package com.nkp.shopify.service;

import com.nkp.shopify.entities.ProductEntity;
import com.nkp.shopify.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id).orElse(new ProductEntity("No Product", "", 0));
    }

    public ProductEntity saveProduct(ProductEntity product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            return new ProductEntity();
        }
    }

    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        try {
            productRepository.deleteById(id);
            return ResponseEntity.ok().body("{\"message\": \"Product deleted successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Some Error Occured\"}");
        }
    }

    public ProductEntity updateProductname(@RequestBody ProductEntity product) {
        try {
            Optional<ProductEntity> productEntity = productRepository.findById(product.getId());
            if (productEntity.isPresent()) {
                ProductEntity existingProduct = productEntity.get();
                existingProduct.setName(product.getName());
                return productRepository.save(existingProduct);
            } else {
                throw new RuntimeException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ProductEntity();
        }
    }

    public ProductEntity updateProductdescription(@RequestBody ProductEntity product) {
        try {
            Optional<ProductEntity> productEntity = productRepository.findById(product.getId());
            if (productEntity.isPresent()) {
                ProductEntity existingProduct = productEntity.get();
                existingProduct.setDescription(product.getDescription());
                return productRepository.save(existingProduct);
            } else {
                throw new RuntimeException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ProductEntity();
        }
    }

    public ProductEntity updateProductprice(@RequestBody ProductEntity product) {
        try {
            Optional<ProductEntity> productEntity = productRepository.findById(product.getId());
            if (productEntity.isPresent()) {
                ProductEntity existingProduct = productEntity.get();
                existingProduct.setPrice(product.getPrice());
                return productRepository.save(existingProduct);
            } else {
                throw new RuntimeException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ProductEntity();
        }
    }
}
