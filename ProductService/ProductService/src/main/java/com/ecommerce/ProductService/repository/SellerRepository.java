package com.ecommerce.ProductService.repository;

import com.ecommerce.ProductService.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT s FROM Seller s WHERE s.email = :email")
    public Optional<Seller> findByEmail(@Param("email") String email);
}
