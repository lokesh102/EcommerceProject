package com.ecommerce.ProductService.service;

import com.ecommerce.ProductService.entity.Seller;
import com.ecommerce.ProductService.exception.SellerAlreadyExistsException;
import com.ecommerce.ProductService.exception.SellerNotFoundException;
import com.ecommerce.ProductService.model.SellerRequestDTO;
import com.ecommerce.ProductService.repository.SellerRepository;
//import com.ecommerce.product.service.SellerService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller getSellerById(String email) throws SellerNotFoundException {
        return sellerRepository.findByEmail(email)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + id));
    }

    @Override
    public void addSeller(SellerRequestDTO sellerRequestDTO) throws SellerAlreadyExistsException {

        Seller sellers = sellerRepository.findByEmail(sellerRequestDTO.getEmail()).orElse(null);
        if(ObjectUtils.isNotEmpty(sellers)){
            throw new SellerAlreadyExistsException("Seller already present");
        }

        Seller seller = Seller.builder()
                .email(sellerRequestDTO.getEmail())
                .phone(sellerRequestDTO.getPhone())
                .sellerName(sellerRequestDTO.getSellerName())
                .build();

        sellerRepository.save(seller);
    }

    @Override
    public void updateSeller(String email, Seller seller) throws SellerNotFoundException {
        Optional<Seller> existingSeller = sellerRepository.findByEmail(email);
        if (existingSeller.isPresent()) {
            Seller updatedSeller = existingSeller.get();
            updatedSeller.setSellerName(seller.getSellerName());
            updatedSeller.setEmail(seller.getEmail());
            updatedSeller.setPhone(seller.getPhone());
            sellerRepository.save(updatedSeller);
        } else {
            throw new SellerNotFoundException("Seller not found with email: " + email);
        }
    }

    @Override
    public void deleteSeller(String email) throws SellerNotFoundException {
        if (sellerRepository.findByEmail(email).isPresent()) {
            sellerRepository.deleteById(sellerRepository.findByEmail(email).get().getId());
        } else {
            throw new SellerNotFoundException("Seller not found with id: " + id);
        }
    }
}
