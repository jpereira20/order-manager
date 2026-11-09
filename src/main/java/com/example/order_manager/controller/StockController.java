package com.example.order_manager.controller;

import com.example.order_manager.dto.QuantityRequest;
import com.example.order_manager.dto.StockRequest;
import com.example.order_manager.dto.StockResponse;
import com.example.order_manager.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse create(@Valid @RequestBody StockRequest request) {
        return stockService.create(request);
    }

    @GetMapping
    public List<StockResponse> list() {
        return stockService.list();
    }

    @GetMapping("/{id}")
    public StockResponse get(@PathVariable Long id) {
        return stockService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        stockService.delete(id);
    }

    @PutMapping("/{id}")
    public StockResponse update(@PathVariable Long id, @Valid @RequestBody QuantityRequest request) {
        return stockService.update(id, request);
    }
}
