package com.ecommerce.ProductService.service;

import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.exception.ProductNotFoundException;
import com.ecommerce.ProductService.repository.ProductRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllProduct();
    }

    @Override
    public Product getProductById(String id) throws ProductNotFoundException {
        return productRepository.findByProductId(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(String id, Product product) throws ProductNotFoundException {
        Optional<Product> existingProduct = productRepository.findByProductId(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setCategory(product.getCategory());
            productRepository.save(updatedProduct);
        } else {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }

    @Override
    public void deleteProduct(String id) throws ProductNotFoundException {
        Product listProduct =  productRepository.existsByProductId(id);
        if (ObjectUtils.isNotEmpty(listProduct)) {
            productRepository.deleteById(listProduct.getId());
        } else {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }
}
