package com.example.order_manager.service;

import com.example.order_manager.dto.CustomerRequest;
import com.example.order_manager.exception.ConflictException;
import com.example.order_manager.exception.NotFoundException;
import com.example.order_manager.model.Customer;
import com.example.order_manager.repository.CustomerOrderRepository;
import com.example.order_manager.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customers;
    private final CustomerOrderRepository customerOrders;

    public CustomerService(CustomerRepository customers, CustomerOrderRepository customerOrders) {
        this.customers = customers;
        this.customerOrders = customerOrders;
    }

    public Customer createCustomer(CustomerRequest request) {
        return customers.save(new Customer(request.name(), request.email()));
    }

    @Transactional(readOnly = true)
    public List<Customer> customerList() {
        return customers.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getCustomer(Long id) {
        return findCustomer(id);
    }

    public Customer updateCustomer(Long id, CustomerRequest request) {
        Customer customer = getCustomer(id);
        customer.setName(request.name());
        customer.setEmail(request.email());
        return customer;
    }

    public void deleteCustomer(Long id) {
        Customer customer = findCustomer(id);
        if (customerOrders.existsByCustomerId(id)) {
            throw new ConflictException("Customer " + id + " has orders and cannot be deleted");
        }
        customers.delete(customer);
    }

    private Customer findCustomer(Long id) {
        return customers.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer " + id + " not found"));
    }

}
