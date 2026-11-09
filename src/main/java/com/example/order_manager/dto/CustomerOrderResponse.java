package com.example.order_manager.dto;

import java.time.Instant;
import java.util.List;

public record CustomerOrderResponse(Long id, Long customerId, Long itemId, long quantity, Instant creationDate,
                                    long fulfilledQuantity, double completion, String status,
                                    List<StockUse> stockMovements) {
}
