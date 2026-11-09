package com.example.order_manager.service;

import com.example.order_manager.model.CustomerOrder;

public interface NotificationService {
    void orderCompleted(CustomerOrder order);
}
