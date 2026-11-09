package com.example.order_manager.dto;

import jakarta.validation.constraints.NotBlank;

public record ItemRequest(@NotBlank String name) {

}
