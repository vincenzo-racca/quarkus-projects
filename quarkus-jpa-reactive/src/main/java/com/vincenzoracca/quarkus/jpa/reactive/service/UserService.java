package com.vincenzoracca.quarkus.jpa.reactive.service;

import com.vincenzoracca.quarkus.jpa.reactive.model.User;
import io.smallrye.mutiny.Uni;

import java.util.List;

 public interface UserService {
    
     Uni<List<User>> findAll();

     Uni<User> findById(Long id);

     Uni<User> insert(User user);

     Uni<User> put(Long id, User user);

    Uni<Boolean> delete(Long id);


}
