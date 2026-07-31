package com.ecommerce.ProductService.repository;

import com.ecommerce.ProductService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductId(@PathVariable String productId);

    @Query("Select p from Product p where productId = :productID")
    Product existsByProductId(@PathVariable  String productId);

    @Query("Select p from Product p")
    List<Product> findAllProduct();
}