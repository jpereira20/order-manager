package com.ordermanager.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.ordermanager.model.StockMovement;

public class StockMovementDAO extends Dao<StockMovement> {

	public StockMovementDAO(EntityManager entityManager) {
		super(entityManager, StockMovement.class);
	}

	public void create(StockMovement stockMovement) {
		super.create(stockMovement);
	}

	public StockMovement findById(Long id) {
		return super.findById(id);
	}

	public List<StockMovement> findAll() {
		return super.findAll();
	}

	public void update(StockMovement stockMovement) {
		super.update(stockMovement);
	}

	public void delete(StockMovement stockMovement) {
		super.delete(stockMovement);
	}

	public void save(StockMovement entity) {
		super.save(entity);
	}

	// Method for finding an item by creationDate
	public List<StockMovement> findStockMovementsByCreationDate(Date creationDate) {
		return entityManager.createQuery("FROM StockMovement WHERE creation_date = :creationDate", StockMovement.class)
				.setParameter("creationDate", creationDate).getResultList();
	}

	// Method for finding an item by ItemId
	public List<StockMovement> findStockMovementsByItemId(Long itemId) {
		return entityManager.createQuery("FROM Order WHERE item_id = :itemId", StockMovement.class)
				.setParameter("itemId", itemId).getResultList();
	}

	public List<StockMovement> findStockMovementsByQuantity(int quantity) {
		return entityManager.createQuery("FROM Order WHERE quantity = :quantity", StockMovement.class)
				.setParameter("quantity", quantity).getResultList();
	}

}
