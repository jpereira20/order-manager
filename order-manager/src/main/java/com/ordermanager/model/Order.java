package com.ordermanager.model;

import java.util.Date;

public class Order {
	private Long id;
	private Date creationDate;
	private Long itemId;
	private int quantity;
	private Long userId;

	public Order() {
	}

	public Order(Long id, Date creationDate, Long itemId, int quantity, Long userId) {
		this.id = id;
		this.creationDate = creationDate;
		this.itemId = itemId;
		this.quantity = quantity;
		this.userId = userId;
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
