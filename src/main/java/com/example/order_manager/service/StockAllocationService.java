package com.example.order_manager.service;

import com.example.order_manager.model.CustomerOrder;
import com.example.order_manager.model.Stock;
import com.example.order_manager.model.StockAllocation;
import com.example.order_manager.repository.CustomerOrderRepository;
import com.example.order_manager.repository.StockAllocationRepository;
import com.example.order_manager.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StockAllocationService {
    private static final Logger LOG = LoggerFactory.getLogger("business");

    private final StockAllocationRepository allocations;
    private final StockRepository stocks;
    private final CustomerOrderRepository customerOrders;
    private final NotificationService notifications;

    public StockAllocationService(StockAllocationRepository allocations, StockRepository stocks, CustomerOrderRepository customerOrders, NotificationService notifications) {
        this.allocations = allocations;
        this.stocks = stocks;
//        this.notifications = notifications;
        this.customerOrders = customerOrders;
        this.notifications = notifications;
    }

    public void allocateForOrder(CustomerOrder order) {
        long missing = order.getQuantity() - allocations.sumByCustomerOrder(order.getId());
        if (missing <= 0) return;

        for (Stock stock : stocks.findByItemIdOrderByIdAsc(order.getItem().getId())) {
            if (missing == 0) break;
            missing -= allocate(order, stock, missing);
        }
        if (missing == 0) complete(order);
    }

    private long allocate(CustomerOrder order, Stock stock, long missing) {
        long allocated = Math.min(missing, remaining(stock));

        if (allocated <= 0) return 0;

        allocations.save(new StockAllocation(order, stock, allocated));
        LOG.info("STOCK ALLOCATION: {} units of stock added #{} to the order #{}", allocated, stock.getId(), order.getId());
        return allocated;
    }

    public void allocateForStock(Stock stock) {
        for (CustomerOrder customerOrder : customerOrders.findByItemIdOrderByIdAsc(stock.getItem().getId())) {
            if (remaining(stock) == 0) return;

            long missing = customerOrder.getQuantity() - allocations.sumByCustomerOrder(customerOrder.getId());
            if (missing <= 0) continue;

            long allocated = allocate(customerOrder, stock, missing);
            if (allocated == missing) {
                complete(customerOrder);
            }
        }
    }

    public long remaining(Stock stock) {
        return stock.getQuantity() - allocations.sumByStock(stock.getId());
    }

    private void complete(CustomerOrder order) {
        LOG.info("ORDER COMPLETE: order #{}", order.getId());

        try {
            notifications.orderCompleted(order);
        } catch (Exception e) {
            LOG.error("ORDER COMPLETE: order #{} has failed", order.getId(), e);
        }
    }
}
