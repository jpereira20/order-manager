package com.ordermanager.controller;

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

import com.ordermanager.dao.UserDAO;
import com.ordermanager.model.User;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

	@Inject
	private UserDAO userDao;

	@POST
	public Response createUser(String name, String email) {
		User newUser = new User();
		newUser.setName(name);
		newUser.setEmail(email);
		userDao.create(newUser);
		return Response.status(Response.Status.CREATED).build();
	}

	@GET
	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	@GET
	@Path("/{id}")
	public User getUserById(@PathParam("id") Long id) {
		return userDao.findById(id);
	}

	@GET
	@Path("/by-name/{name}")
	public User getUserByName(@PathParam("name") String name) {
		return userDao.findByName(name);
	}

	@GET
	@Path("/by-email/{email}")
	public User getUserByEmail(@PathParam("email") String email) {
		return userDao.findByEmail(email);
	}
}