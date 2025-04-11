package com.ordermanager.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.ordermanager.model.Order;

public class OrderDAO extends Dao<Order> {

	public OrderDAO(EntityManager entityManager, Class<Order> entityClass) {
		super(entityManager, entityClass);
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

	// Method for finding an item by userId
	public List<Order> findOrdersByUserId(Long userId) {
		return entityManager.createQuery("FROM Order WHERE user_id = :userId", Order.class)
				.setParameter("userId", userId).getResultList();
	}

	// Method for finding an item by ItemId
	public List<Order> findOrdersByItemId(Long ItemId) {
		return entityManager.createQuery("FROM Order WHERE item_id = :ItemId", Order.class)
				.setParameter("userId", ItemId).getResultList();
	}

	// Method for finding an item by creationDate
	public List<Order> findOrdersByCreationDate(Date creationDate) {
		return entityManager.createQuery("FROM Order WHERE creation_date = :creationDate", Order.class)
				.setParameter("userId", creationDate).getResultList();
	}
}
