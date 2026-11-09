package com.example.order_manager.service;

import com.example.order_manager.dto.CustomerOrderRequest;
import com.example.order_manager.dto.CustomerOrderResponse;
import com.example.order_manager.dto.QuantityRequest;
import com.example.order_manager.dto.StockUse;
import com.example.order_manager.exception.ConflictException;
import com.example.order_manager.exception.NotFoundException;
import com.example.order_manager.model.Customer;
import com.example.order_manager.model.CustomerOrder;
import com.example.order_manager.model.Item;
import com.example.order_manager.repository.CustomerOrderRepository;
import com.example.order_manager.repository.CustomerRepository;
import com.example.order_manager.repository.ItemRepository;
import com.example.order_manager.repository.StockAllocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerOrderService {
    private static final Logger LOG = LoggerFactory.getLogger("business");

    private final CustomerRepository customers;
    private final ItemRepository items;
    private final CustomerOrderRepository orders;
    private final StockAllocationRepository allocations;
    private final StockAllocationService allocationService;

    public CustomerOrderService(CustomerRepository customers, ItemRepository items, CustomerOrderRepository orders, StockAllocationRepository allocations, StockAllocationService allocationService) {
        this.customers = customers;
        this.items = items;
        this.orders = orders;
        this.allocations = allocations;
        this.allocationService = allocationService;
    }

    public CustomerOrderResponse create(CustomerOrderRequest request) {
        Customer customer = customers.findById(request.customerId())
                .orElseThrow(() -> new NotFoundException("Customer " + request.customerId() + " not found"));

        Item item = items.findByIdForUpdate(request.itemId())
                .orElseThrow(() -> new NotFoundException("Item " + request.itemId() + " not found"));

        CustomerOrder order = orders.save(new CustomerOrder(customer, item, request.quantity()));
        LOG.info("ORDER CREATED: order #{} (item: #{}, quantity: {}), by customer {}", order.getId(), item.getId(), order.getQuantity(), customer.getId());

        allocationService.allocateForOrder(order);
        return response(order);
    }

    public CustomerOrderResponse response(CustomerOrder order) {
        List<StockUse> used = allocations.findByCustomerOrderIdOrderByIdAsc(order.getId()).stream()
                .map(alloc -> new StockUse(alloc.getStock().getId(), alloc.getQuantity()))
                .toList();

        long fulfilled = used.stream().mapToLong(StockUse::quantity).sum();
        double completion = Math.round(fulfilled * 100.0 / order.getQuantity());

        String status = fulfilled >= order.getQuantity() ? "COMPLETE" : "OPEN";

        return new CustomerOrderResponse(order.getId(), order.getCustomer().getId(), order.getItem().getId(), order.getQuantity(), order.getCreationDate(), fulfilled, completion, status, used);
    }

    @Transactional(readOnly = true)
    public List<CustomerOrderResponse> list() {
        return orders.findAll()
                .stream()
                .map(this::response)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerOrderResponse get(Long id) {
        CustomerOrder order = findCustomerOrder(id);
        return response(order);
    }

    public void delete(Long id) {
        CustomerOrder order = findCustomerOrder(id);
        items.findByIdForUpdate(order.getItem().getId());
        if (allocations.existsByCustomerOrderId(id)) {
            throw new ConflictException("Order " + id + " already has allocations and cannot be deleted");
        }
        orders.delete(order);
    }

    public CustomerOrderResponse update(Long id, QuantityRequest request) {
        CustomerOrder order = findCustomerOrder(id);
        items.findByIdForUpdate(order.getItem().getId());
        if (allocations.existsByCustomerOrderId(id)) {
            throw new ConflictException("Order " + id + " already has allocations and cannot be changed");
        }
        order.setQuantity(request.quantity());
        allocationService.allocateForOrder(order);
        return response(order);
    }

    private CustomerOrder findCustomerOrder(Long id) {
        return orders.findById(id)
                .orElseThrow(() -> new NotFoundException("Order " + id + " not found"));
    }
}
