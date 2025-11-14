package dev.matichelo.crud.products.reactive.app.repositories;

import dev.matichelo.crud.products.reactive.app.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findByCategory(String category);
    Mono<Void> deleteByName(String name);
}