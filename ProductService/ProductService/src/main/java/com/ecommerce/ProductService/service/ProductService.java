package com.ecommerce.ProductService.service;

import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(String productId) throws ProductNotFoundException;
    void addProduct(Product product);
    void updateProduct(String productId, Product product) throws ProductNotFoundException;
    void deleteProduct(String productId) throws ProductNotFoundException;
}
