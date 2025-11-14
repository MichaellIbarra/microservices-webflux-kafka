package dev.matichelo.crud.products.reactive.app.repositories;

import dev.matichelo.crud.products.reactive.app.models.Product;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findByCategory(String category);

    @Transactional // esta anotación indica que la operación se ejecuta dentro de una transacción, lo que es importante para garantizar la consistencia de los datos en caso de que se realicen múltiples operaciones de modificación.
    @Modifying // este anotación indica que este método modifica datos en la base de datos es necesario para que la operación de eliminación funcione correctamente.
    Mono<Void> deleteByName(String name);
}