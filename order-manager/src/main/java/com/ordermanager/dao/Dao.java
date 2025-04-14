package com.ordermanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public abstract class Dao<T> {

	protected EntityManager entityManager;
	protected Class<T> entityClass;

	public Dao(EntityManager entityManager, Class<T> entityClass) {
		this.entityManager = entityManager;
		this.entityClass = entityClass;
	}

	public void create(T entity) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			entityManager.persist(entity);
			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public T findById(Long id) {
		return entityManager.find(entityClass, id);
	}

	public List<T> findAll() {
		return entityManager.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
	}

	public void update(T entity) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			entityManager.merge(entity);
			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void delete(T entity) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void save(T entity) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			if (entityManager.contains(entity) || entity.getClass().getMethod("getId").invoke(entity) != null) {
			} else {
				entityManager.persist(entity);
			}
			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			e.printStackTrace();
		}
	}
}
