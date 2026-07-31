package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.entity.Seller;
import com.ecommerce.ProductService.exception.ProductNotFoundException;
import com.ecommerce.ProductService.exception.SellerNotFoundException;
import com.ecommerce.ProductService.model.ProductRequestDTO;
import com.ecommerce.ProductService.service.ProductService;
import com.ecommerce.ProductService.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SellerService sellerService;

    @Operation(summary = "Get all products", description = "Fetches all the products available in the store")
    @ApiResponse(responseCode = "200", description = "Successfully fetched all products")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get product by productId", description = "Fetches a product by its productId")
    @ApiResponse(responseCode = "200", description = "Successfully fetched product details")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public Product getProductById(@Parameter(description = "Product ID of the product to be fetched") @PathVariable String id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    @Operation(summary = "Add a new product", description = "Adds a new product to the inventory")
    @ApiResponse(responseCode = "201", description = "Product added successfully")
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequestDTO product) throws SellerNotFoundException {
        Product product1 = new Product();
        Seller seller = sellerService.getSellerById(product.getSellerEmailId());
        product1.setCategory(product.getCategory());
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setDescription(product.getDescription());
        product1.setSeller(seller);
        product1.setProductId(String.valueOf(UUID.randomUUID()));
        productService.addProduct(product1);

        return new ResponseEntity<>("Product added successfully - Product ID - " + product1.getProductId(), HttpStatus.CREATED);
    }

    @Operation(summary = "Update product details", description = "Updates the product details by product ID")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@Parameter(description = "Product ID of the product to be updated") @PathVariable String id,
                                                @RequestBody ProductRequestDTO product) throws ProductNotFoundException {
        Product p = productService.getProductById(id);
        if(ObjectUtils.isNotEmpty(p)) {
            p.setPrice(product.getPrice());
            p.setCategory(product.getCategory());
            p.setDescription(product.getDescription());
            p.setName(product.getName());
            productService.updateProduct(id, p);
            return new ResponseEntity<>("Product updated successfully!", HttpStatus.CREATED);

        }else{
            throw new ProductNotFoundException("Product not found with Id "+ id);
        }
    }

    @Operation(summary = "Delete product", description = "Deletes a product by its Product ID")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@Parameter(description = "Product ID of the product to be deleted") @PathVariable String id) throws ProductNotFoundException {
        productService.deleteProduct(id);
        return "Product deleted successfully!";
    }
}