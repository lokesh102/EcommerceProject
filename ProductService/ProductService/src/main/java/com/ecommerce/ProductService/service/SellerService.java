package com.ecommerce.ProductService.service;

import com.ecommerce.ProductService.entity.Seller;
import com.ecommerce.ProductService.exception.SellerAlreadyExistsException;

import com.ecommerce.ProductService.exception.SellerNotFoundException;
import com.ecommerce.ProductService.model.SellerRequestDTO;

import java.util.List;

public interface SellerService {
    public List<Seller> getAllSellers();
    public Seller getSellerById(String email) throws SellerNotFoundException;
    public void addSeller(SellerRequestDTO seller) throws SellerAlreadyExistsException;
    public void updateSeller(String email, Seller seller) throws SellerNotFoundException;
    public void deleteSeller(String email) throws SellerNotFoundException;
}
