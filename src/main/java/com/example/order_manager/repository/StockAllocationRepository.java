package com.example.order_manager.repository;

import com.example.order_manager.model.StockAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockAllocationRepository extends JpaRepository<StockAllocation, Long> {

    @Query("SELECT coalesce(sum(a.quantity), 0) FROM StockAllocation a where a.customerOrder.id = :id")
    long sumByCustomerOrder(@Param("id") Long id);

    @Query("SELECT coalesce(sum(a.quantity), 0) FROM StockAllocation a where a.stock.id = :id")
    long sumByStock(@Param("id") Long id);

    List<StockAllocation> findByCustomerOrderIdOrderByIdAsc(Long orderId);
    List<StockAllocation> findByStockIdOrderByIdAsc(Long stockId);
    boolean existsByCustomerOrderId(Long orderId);
    boolean existsByStockId(Long stockId);
}
