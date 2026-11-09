package com.example.order_manager.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "orders")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Item item;

    @Column(nullable = false)
    private long quantity;

    @Column(nullable = false)
    private Instant creationDate = Instant.now();

    protected CustomerOrder() {
    }

    public CustomerOrder(Customer customer, Item item, long quantity) {
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Item getItem() {
        return item;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Instant getCreationDate() {
        return creationDate;
    }
}
