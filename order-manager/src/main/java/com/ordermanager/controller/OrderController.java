package com.ordermanager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
	public Response createOrder(Order newOrder) {
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

	@PUT
	@Path("/{id}")
	public Response updateOrder(@PathParam("id") Long id, Order updatedOrder) {
		Order existingOrder = orderDAO.findById(id);
		if (existingOrder == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		existingOrder.setItemId(updatedOrder.getItemId());
		existingOrder.setQuantity(updatedOrder.getQuantity());
		existingOrder.setUserId(updatedOrder.getUserId());
		existingOrder.setCreationDate(updatedOrder.getCreationDate());

		orderDAO.update(existingOrder);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteOrder(@PathParam("id") Long id) {
		Order existingOrder = orderDAO.findById(id);
		if (existingOrder == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		orderDAO.delete(existingOrder);
		return Response.noContent().build();
	}
}
