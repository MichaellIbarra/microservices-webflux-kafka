package dev.matichelo.crud.products.reactive.app.repositories;

import dev.matichelo.crud.products.reactive.app.models.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findByUser(String username);
}
