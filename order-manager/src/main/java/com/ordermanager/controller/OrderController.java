package com.ordermanager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ordermanager.dao.OrderDAO;
import com.ordermanager.model.Order;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

	@Inject
	private OrderDAO orderDAO;

	@POST
	public Response createOrder(Long itemId, int quantity, Long userId) {
		Order newOrder = new Order();
		newOrder.setCreationDate(new Date());
		newOrder.setItemId(itemId);
		newOrder.setQuantity(quantity);
		newOrder.setUserId(userId);
		orderDAO.create(newOrder);
		return Response.status(Response.Status.CREATED).build();
	}

	@GET
	public List<Order> getAllOrders() {
		return orderDAO.findAll();
	}

	@GET
	@Path("/{id}")
	public Order getOrderById(@PathParam("id") Long id) {
		return orderDAO.findById(id);
	}

	@GET
	@Path("/by-user/{userId}")
	public List<Order> getOrdersByUser(@PathParam("userId") Long userId) {
		return orderDAO.findOrdersByUserId(userId);
	}

	@GET
	@Path("/by-item/{itemId}")
	public List<Order> getOrdersByItem(@PathParam("itemId") Long itemId) {
		return orderDAO.findOrdersByItemId(itemId);
	}

	@GET
	@Path("/by-creationDate/{creationDate}")
	public List<Order> getOrdersByCreationDate(@PathParam("creationDate") String creationDate) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return orderDAO.findOrdersByCreationDate(format.parse(creationDate));
	}

	@GET
	@Path("/by-quantity/{quantity}")
	public List<Order> getOrdersByQuantity(@PathParam("quantity") int quantity) {
		return orderDAO.findOrdersByQuantity(quantity);
	}
}
