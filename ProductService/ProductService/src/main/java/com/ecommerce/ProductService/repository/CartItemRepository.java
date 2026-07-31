package com.ecommerce.ProductService.repository;

import com.ecommerce.ProductService.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.productId = :productId AND c.cart.id = :cartId")
    void deleteProductFromCart(@Param("productId") String productId, @Param("cartId") String cartId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.cart.id = :cartId")
    void deleteProductFromCart(@Param("cartId") String cartId);
}
