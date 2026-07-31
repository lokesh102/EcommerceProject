package com.ecommerce.ProductService.controller;


import com.ecommerce.ProductService.entity.Seller;
import com.ecommerce.ProductService.exception.SellerAlreadyExistsException;

import com.ecommerce.ProductService.exception.SellerNotFoundException;
import com.ecommerce.ProductService.model.SellerRequestDTO;
import com.ecommerce.ProductService.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Operation(summary = "Get all sellers", description = "Fetches a list of all sellers")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of sellers")
    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    @Operation(summary = "Get seller by Email", description = "Fetches seller details by seller Email")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved seller")
    @GetMapping("/{email}")
    public Seller getSellerByEmail(@Parameter(description = "Seller ID") @PathVariable String email) throws SellerNotFoundException {
        return sellerService.getSellerById(email);
    }

    @Operation(summary = "Add a new seller", description = "Adds a new seller to the system")
    @ApiResponse(responseCode = "201", description = "Successfully added seller")
    @PostMapping("/add")
    public String addSeller(@RequestBody SellerRequestDTO sellerRequestDTO) throws SellerAlreadyExistsException {
        sellerService.addSeller(sellerRequestDTO);
        return "Seller added successfully!";
    }

    @Operation(summary = "Update seller details", description = "Updates an existing seller's information")
    @ApiResponse(responseCode = "200", description = "Successfully updated seller")
    @PutMapping("/update/{email}")
    public String updateSeller(@Parameter(description = "Seller ID") @PathVariable String email,
                               @RequestBody SellerRequestDTO seller) throws SellerNotFoundException {
        Seller seller1 = sellerService.getSellerById(email);
        if(seller1 != null){
            seller1.setSellerName(seller.getSellerName());
            seller1.setEmail(email);
            seller1.setPhone(seller.getPhone());
            sellerService.updateSeller(email, seller1);
            return "Seller updated successfully!";
        }else{
            throw new SellerNotFoundException("Seller not found with seller email id "+ email );
        }

    }

    @Operation(summary = "Delete seller", description = "Deletes a seller based on ID")
    @ApiResponse(responseCode = "200", description = "Successfully deleted seller")
    @DeleteMapping("/delete/{email}")
    public String deleteSeller(@Parameter(description = "Seller ID") @PathVariable String email) throws SellerNotFoundException {
        sellerService.deleteSeller(email);
        return "Seller deleted successfully!";
    }
}