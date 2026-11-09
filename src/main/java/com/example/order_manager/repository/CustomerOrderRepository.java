package com.example.order_manager.repository;

import com.example.order_manager.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByItemIdOrderByIdAsc(Long itemId);
    boolean existsByCustomerId(Long customerId);
    boolean existsByItemId(Long itemId);
}