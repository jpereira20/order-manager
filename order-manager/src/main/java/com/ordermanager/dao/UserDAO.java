package com.ordermanager.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.ordermanager.model.User;

public class UserDAO extends Dao<User> {

	public UserDAO(EntityManager entityManager) {
		super(entityManager, User.class);
	}

	public void create(User user) {
		super.create(user);
	}

	public User findById(Long id) {
		return super.findById(id);
	}

	public List<User> findAll() {
		return super.findAll();
	}

	public void update(User user) {
		super.update(user);
	}

	public void delete(User user) {
		super.delete(user);
	}

	public void save(User entity) {
		super.save(entity);
	}

	// Method for finding an item by name
	public User findByName(String name) {
		return entityManager.createQuery("FROM User WHERE name = :name", User.class).setParameter("name", name)
				.getSingleResult();
	}

	// Method for finding an item by email
	public User findByEmail(String email) {
		return entityManager.createQuery("FROM User WHERE email = :email", User.class).setParameter("email", email)
				.getSingleResult();
	}
}
