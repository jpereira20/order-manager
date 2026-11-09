package com.example.order_manager.dto;

import jakarta.validation.constraints.Positive;

public record QuantityRequest(@Positive long quantity) {
}
