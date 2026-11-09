package com.example.order_manager;

import com.example.order_manager.dto.*;
import com.example.order_manager.exception.ConflictException;
import com.example.order_manager.service.CustomerOrderService;
import com.example.order_manager.service.CustomerService;
import com.example.order_manager.service.ItemService;
import com.example.order_manager.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderManagerApplicationTests {

    @Autowired
    CustomerService customers;
    @Autowired
    ItemService items;
    @Autowired
    CustomerOrderService orders;
    @Autowired
    StockService stocks;

    @Test
    void orderBeforeStock_usesSeveralStocksAndCompletes() {
        Long customerId = customers.createCustomer(new CustomerRequest("Ana", "ana@example.com")).getId();
        Long itemId = items.createItem(new ItemRequest("Widget")).getId();

        CustomerOrderResponse order = orders.create(new CustomerOrderRequest(customerId, itemId, 10));
        assertEquals("OPEN", order.status());
        assertEquals(0, order.fulfilledQuantity());

        stocks.create(new StockRequest(itemId, 4));
        CustomerOrderResponse partial = orders.get(order.id());
        assertEquals(4, partial.fulfilledQuantity());
        assertEquals(40.0, partial.completion());
        assertEquals("OPEN", partial.status());

        stocks.create(new StockRequest(itemId, 8));
        CustomerOrderResponse done = orders.get(order.id());
        assertEquals("COMPLETE", done.status());
        assertEquals(2, done.stockMovements().size());
    }

    @Test
    void stockBeforeOrder_completesImmediately() {
        Long customerId = customers.createCustomer(new CustomerRequest("Ana", "ana@example.com")).getId();
        Long itemId = items.createItem(new ItemRequest("Gadget")).getId();

        StockResponse stock = stocks.create(new StockRequest(itemId, 10));

        CustomerOrderResponse order = orders.create(new CustomerOrderRequest(customerId, itemId, 4));
        assertEquals("COMPLETE", order.status());
        assertEquals(4, order.fulfilledQuantity());
        assertEquals(100.0, order.completion());

        StockResponse after = stocks.get(stock.id());
        assertEquals(6, after.remainingQuantity());
        assertEquals(1, after.orders().size());
        assertEquals(order.id(), after.orders().get(0).orderId());
    }

    @Test
    void oneStockServesSeveralOrdersInFifoOrder() {
        Long customerId = customers.createCustomer(new CustomerRequest("Ana", "ana@example.com")).getId();
        Long itemId = items.createItem(new ItemRequest("Gizmo")).getId();

        CustomerOrderResponse first = orders.create(new CustomerOrderRequest(customerId, itemId, 3));
        CustomerOrderResponse second = orders.create(new CustomerOrderRequest(customerId, itemId, 4));

        StockResponse stock = stocks.create(new StockRequest(itemId, 5));

        assertEquals("COMPLETE", orders.get(first.id()).status());

        CustomerOrderResponse partial = orders.get(second.id());
        assertEquals(2, partial.fulfilledQuantity());
        assertEquals(50.0, partial.completion());
        assertEquals("OPEN", partial.status());

        StockResponse after = stocks.get(stock.id());
        assertEquals(0, after.remainingQuantity());
        assertEquals(2, after.orders().size());
        assertEquals(first.id(), after.orders().get(0).orderId());
        assertEquals(second.id(), after.orders().get(1).orderId());
    }

    @Test
    void cannotDeleteOrderThatAlreadyHasAllocations() {
        Long customerId = customers.createCustomer(new CustomerRequest("Ana", "ana@example.com")).getId();
        Long itemId = items.createItem(new ItemRequest("Doohickey")).getId();

        stocks.create(new StockRequest(itemId, 5));
        CustomerOrderResponse order = orders.create(new CustomerOrderRequest(customerId, itemId, 2));

        assertThrows(ConflictException.class, () -> orders.delete(order.id()));

        assertEquals("COMPLETE", orders.get(order.id()).status());
    }

}
