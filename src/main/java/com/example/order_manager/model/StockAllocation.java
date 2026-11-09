package com.example.order_manager.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "stock_allocation")
public class StockAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CustomerOrder customerOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Stock stock;

    @Column(nullable = false)
    private long quantity;

    @Column(nullable = false)
    private Instant creationDate = Instant.now();

    protected StockAllocation() {
    }

    public StockAllocation(CustomerOrder customerOrder, Stock stock, long quantity) {
        this.customerOrder = customerOrder;
        this.stock = stock;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public Stock getStock() {
        return stock;
    }

    public long getQuantity() {
        return quantity;
    }
}
