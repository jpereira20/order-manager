package com.ordermanager.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.ordermanager.model.Item;

public class ItemDAO extends Dao<Item> {

	public ItemDAO(EntityManager entityManager) {
		super(entityManager, Item.class);
	}

	public void create(Item item) {
		super.create(item);
	}

	public Item findById(Long id) {
		return super.findById(id);
	}

	public List<Item> findAll() {
		return super.findAll();
	}

	public void update(Item item) {
		super.update(item);
	}

	public void delete(Item item) {
		super.delete(item);
	}

	public void save(Item entity) {
		super.save(entity);
	}

	// Method for finding an item by name
	public Item findByName(String name) {
		return entityManager.createQuery("FROM Item WHERE name = :name", Item.class).setParameter("name", name)
				.getSingleResult();
	}
}
