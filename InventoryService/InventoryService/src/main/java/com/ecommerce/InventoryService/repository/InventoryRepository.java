package com.ecommerce.InventoryService.repository;

import com.ecommerce.InventoryService.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(String productId);

    @Query("select s from Inventory s where s.sellerId = :sellerId")
    List<Inventory> getAllInventoryBySeller(@Param("sellerId") String sellerId);
}