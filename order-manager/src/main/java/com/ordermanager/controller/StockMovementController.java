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

import com.ordermanager.dao.StockMovementDAO;
import com.ordermanager.model.StockMovement;

@Path("/stock")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StockMovementController {

	@Inject
	private StockMovementDAO stockMovementDAO;

	@POST
	public Response createStockMovement(Long itemId, int quantity) {
		StockMovement newStockMovement = new StockMovement();
		newStockMovement.setCreationDate(new Date());
		newStockMovement.setItem(itemId);
		newStockMovement.setQuantity(quantity);
		stockMovementDAO.create(newStockMovement);
		return Response.status(Response.Status.CREATED).build();
	}

	@GET
	public List<StockMovement> getAllStockMovements() {
		return stockMovementDAO.findAll();
	}

	@GET
	@Path("/by-item/{itemId}")
	public List<StockMovement> getStockByItem(@PathParam("itemId") Long itemId) {
		return stockMovementDAO.findStockMovementsByItemId(itemId);
	}

	@GET
	@Path("/by-creationDate/{creationDate}")
	public List<StockMovement> getStockByCreationDate(@PathParam("creationDate") String creationDate)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return stockMovementDAO.findStockMovementsByCreationDate(format.parse(creationDate));
	}

	@GET
	@Path("/by-quantity/{quantity}")
	public List<StockMovement> getStockByQuantity(@PathParam("quantity") int quantity) {
		return stockMovementDAO.findStockMovementsByQuantity(quantity);
	}
}
