package com.example.order_manager.dto;

import java.time.Instant;
import java.util.List;

public record StockResponse(Long id, Long itemId, long quantity, Instant creationDate,
                            long allocatedQuantity, long remainingQuantity,
                            List<CustomerOrderUse> orders) {
}
