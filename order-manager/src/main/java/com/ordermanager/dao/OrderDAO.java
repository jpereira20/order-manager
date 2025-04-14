package com.ordermanager.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.ordermanager.model.Order;

public class OrderDAO extends Dao<Order> {

	public OrderDAO(EntityManager entityManager) {
		super(entityManager, Order.class);
	}

	public void create(Order order) {
		super.create(order);
	}

	public Order findById(Long id) {
		return super.findById(id);
	}

	public List<Order> findAll() {
		return super.findAll();
	}

	public void update(Order order) {
		super.update(order);
	}

	public void delete(Order order) {
		super.delete(order);
	}

	public void save(Order entity) {
		super.save(entity);
	}

	// Method for finding an item by userId
	public List<Order> findOrdersByUserId(Long userId) {
		return entityManager.createQuery("FROM Order WHERE user_id = :userId", Order.class)
				.setParameter("userId", userId).getResultList();
	}

	// Method for finding an item by ItemId
	public List<Order> findOrdersByItemId(Long itemId) {
		return entityManager.createQuery("FROM Order WHERE item_id = :itemId", Order.class)
				.setParameter("itemId", itemId).getResultList();
	}

	// Method for finding an item by creationDate
	public List<Order> findOrdersByCreationDate(Date creationDate) {
		return entityManager.createQuery("FROM Order WHERE creation_date = :creationDate", Order.class)
				.setParameter("creationDate", creationDate).getResultList();
	}

	// Method for finding an item by quantity
	public List<Order> findOrdersByQuantity(int quantity) {
		return entityManager.createQuery("FROM Order WHERE quantity = :quantity", Order.class)
				.setParameter("quantity", quantity).getResultList();
	}
}
