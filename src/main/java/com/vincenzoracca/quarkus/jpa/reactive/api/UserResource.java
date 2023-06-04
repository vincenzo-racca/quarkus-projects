package com.vincenzoracca.quarkus.jpa.reactive.api;

import com.vincenzoracca.quarkus.jpa.reactive.model.User;
import com.vincenzoracca.quarkus.jpa.reactive.service.UserService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@Path("/users")
public class UserResource {

    private final UserService userService;

    UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<User>> findAll() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<User> findById(Long id) {
        return userService.findById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<User> insert(User user) {
        return userService.insert(user);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<User> put(@PathParam("id") Long id, User user) {
        return userService.put(id, user);
    }

    @ResponseStatus(204)
    @DELETE
    @Path("/{id}")
    public Uni<Boolean> delete(Long id) {
        return userService.delete(id);
    }
}
