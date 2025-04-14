package com.ordermanager.controller;

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

import com.ordermanager.dao.ItemDAO;
import com.ordermanager.model.Item;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

	@Inject
	private ItemDAO itemDAO;

	@POST
	public Response createItem(Item newItem) {
		itemDAO.create(newItem);
		return Response.status(Response.Status.CREATED).build();
	}

	@GET
	public List<Item> getAllItems() {
		return itemDAO.findAll();
	}

	@GET
	@Path("/{id}")
	public Item getItemById(@PathParam("id") Long id) {
		return itemDAO.findById(id);
	}

	@GET
	@Path("/by-item/{itemName}")
	public Item getItemByItemName(@PathParam("itemName") String itemName) {
		return itemDAO.findByName(itemName);
	}

	@PUT
	@Path("/{id}")
	public Response updateItem(@PathParam("id") Long id, Item updatedItem) {
		Item existing = itemDAO.findById(id);
		if (existing == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		existing.setName(updatedItem.getName());
		itemDAO.update(existing);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteItem(@PathParam("id") Long id) {
		Item existing = itemDAO.findById(id);
		if (existing == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		itemDAO.delete(existing);
		return Response.noContent().build();
	}
}
