package com.ordermanager.entity;

import java.util.Date;

public class Order {
	private Long id;
	private Date creationDate;
	private Item item;
	private int quantity;
	private User user;

	public Order() {
	}

	public Order(Long id, Date creationDate, Item item, int quantity, User user) {
		this.id = id;
		this.creationDate = creationDate;
		this.item = item;
		this.quantity = quantity;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
