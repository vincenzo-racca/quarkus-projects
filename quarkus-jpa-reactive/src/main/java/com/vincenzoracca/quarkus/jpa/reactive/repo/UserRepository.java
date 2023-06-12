package com.vincenzoracca.quarkus.jpa.reactive.repo;

import com.vincenzoracca.quarkus.jpa.reactive.model.User;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
