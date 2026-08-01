package com.ecommerce.orderService.repository;

import com.ecommerce.orderService.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {
    @Query("Select o from OrderDetails o where o.userId = :userId")
    public List<OrderDetails> getOrderDetailsByUserId(@Param("userId") String userId);

}
