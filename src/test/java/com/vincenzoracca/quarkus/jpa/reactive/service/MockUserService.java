package com.vincenzoracca.quarkus.jpa.reactive.service;

import com.vincenzoracca.quarkus.jpa.reactive.model.User;
import io.quarkus.test.Mock;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@Mock
@ApplicationScoped
public class MockUserService implements UserService {

    @Override
    public Uni<List<User>> findAll() {
        var users = List.of(
                new User(1L, "Enzo", "Racca"),
                new User(2L, "Beppe", "Antoni")
        );
        return Uni.createFrom().item(users);
    }

    @Override
    public Uni<User> findById(Long id) {
        return null;
    }

    @Override
    public Uni<User> insert(User user) {
        return null;
    }

    @Override
    public Uni<User> put(Long id, User user) {
        return null;
    }

    @Override
    public Uni<Boolean> delete(Long id) {
        return null;
    }
}
