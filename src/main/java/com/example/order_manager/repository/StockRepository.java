package com.example.order_manager.repository;

import com.example.order_manager.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByItemIdOrderByIdAsc(Long itemId);

    boolean existsByItemId(Long itemId);
}
