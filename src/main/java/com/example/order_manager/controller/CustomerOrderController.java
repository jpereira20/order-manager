package com.example.order_manager.controller;

import com.example.order_manager.dto.CustomerOrderRequest;
import com.example.order_manager.dto.CustomerOrderResponse;
import com.example.order_manager.dto.QuantityRequest;
import com.example.order_manager.service.CustomerOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOrderResponse create(@Valid @RequestBody CustomerOrderRequest request) {
        return customerOrderService.create(request);
    }

    @GetMapping
    public List<CustomerOrderResponse> list() {
        return customerOrderService.list();
    }

    @GetMapping("/{id}")
    public CustomerOrderResponse get(@PathVariable Long id) {
        return customerOrderService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        customerOrderService.delete(id);
    }

    @PutMapping("/{id}")
    public CustomerOrderResponse update(@PathVariable Long id, @Valid @RequestBody QuantityRequest request) {
        return customerOrderService.update(id, request);
    }
}
