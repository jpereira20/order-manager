package com.example.order_manager.service;

import com.example.order_manager.model.CustomerOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogNotificationService implements NotificationService {
    private static final Logger LOG = LoggerFactory.getLogger("business");

    @Override
    public void orderCompleted(CustomerOrder order) {
        LOG.info("EMAIL SENT to {}: order #{} completed", order.getCustomer().getEmail(), order.getId());
    }
}
