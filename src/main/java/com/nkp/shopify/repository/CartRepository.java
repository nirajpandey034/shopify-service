package com.nkp.shopify.repository;

import com.nkp.shopify.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE CartEntity c SET c.quantity = c.quantity + 1 WHERE c.id = :id")
    int incrementQuantityById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE CartEntity c SET c.price = c.price + (c.price / (c.quantity - 1)) WHERE c.id = :id")
    int adjustPriceById(@Param("id") Long id);


    // Custom method to find all records by user ID
    List<CartEntity> findAllByUserId(Long userId);
}
