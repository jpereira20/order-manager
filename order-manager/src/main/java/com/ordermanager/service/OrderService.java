package com.ordermanager.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.ordermanager.dao.OrderDAO;
import com.ordermanager.dao.StockMovementDAO;
import com.ordermanager.dao.UserDAO;
import com.ordermanager.model.Item;
import com.ordermanager.model.Order;
import com.ordermanager.model.StockMovement;
import com.ordermanager.model.User;

public class OrderService {

	private EntityManager entityManager;
	private OrderDAO orderDAO;
	private StockMovementDAO stockMovementDAO;
	private static final Logger logger = Logger.getLogger(OrderService.class);

	public OrderService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.orderDAO = new OrderDAO(entityManager);
		this.stockMovementDAO = new StockMovementDAO(entityManager);
	}

	public void createOrder(Item item, int quantity, User user) {
		logger.info("A criar novo pedido para o item: " + item.getName());

		Order order = new Order();
		order.setItemId(item.getId());
		order.setQuantity(quantity);
		order.setUserId(user.getId());
		order.setCreationDate(new Date());

		orderDAO.save(order);
		logger.info("Pedido criado: " + order.getId());

		tryToFulfillOrder(order);
	}

	private void tryToFulfillOrder(Order order) {
		logger.info("A processar o pedido : " + order.getId());

		Long itemId = order.getItemId();
		int neededQty = order.getQuantity();

		List<StockMovement> stockList = stockMovementDAO.findStockMovementsByItemId(itemId);
		int totalAvailable = stockList.stream().mapToInt(StockMovement::getQuantity).sum();

		logger.info("Quantidade disponível em estoque: " + totalAvailable);

		if (totalAvailable >= neededQty) {
			logger.info("Pedido " + order.getId() + " completo.");

			sendOrderCompletionEmail(order.getUserId(), order);
		} else {
			logger.info("Stock insuficiente para completar o pedido: " + order.getId());
		}
	}

	private void sendOrderCompletionEmail(Long userId, Order order) {
		UserDAO userDAO = new UserDAO(entityManager);
		User user = userDAO.findById(userId);
		logger.info("Enviando email para " + user.getEmail() + " sobre o pedido ID: " + order.getId());

	}

}
