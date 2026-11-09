package com.example.order_manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockRequest(@NotNull Long itemId, @Positive long quantity) {
}
