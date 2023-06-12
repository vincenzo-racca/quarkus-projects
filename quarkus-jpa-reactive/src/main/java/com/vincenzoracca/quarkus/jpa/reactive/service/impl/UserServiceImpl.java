package com.vincenzoracca.quarkus.jpa.reactive.service.impl;

import com.vincenzoracca.quarkus.jpa.reactive.model.User;
import com.vincenzoracca.quarkus.jpa.reactive.repo.UserRepository;
import com.vincenzoracca.quarkus.jpa.reactive.service.UserService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
@WithSession
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Uni<List<User>> findAll() {
        return userRepository.listAll();
    }

    public Uni<User> findById(Long id) {
        return userRepository.findById(id)
                .onItem().ifNull().failWith(new NotFoundException());
    }

    public Uni<User> insert(User user) {
        return Panache.withTransaction(() -> userRepository.persist(user));
    }

    public Uni<User> put(Long id, User user) {
        return userRepository.findById(id)
                .flatMap(entity -> Panache.withTransaction(() -> {
                    if(entity == null) {
                        return userRepository.persist(user);
                    }
                    entity.setName(user.getName());
                    entity.setSurname(user.getSurname());
                    return userRepository.persist(entity);
                }));
    }

    public Uni<Boolean> delete(Long id) {
        return Panache.withTransaction(() -> userRepository.deleteById(id));
    }


}
