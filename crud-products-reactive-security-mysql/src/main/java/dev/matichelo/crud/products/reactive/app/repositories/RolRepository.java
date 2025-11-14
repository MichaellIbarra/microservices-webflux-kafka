package dev.matichelo.crud.products.reactive.app.repositories;

import dev.matichelo.crud.products.reactive.app.models.Rol;
import dev.matichelo.crud.products.reactive.app.models.RolPk;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RolRepository extends ReactiveCrudRepository<Rol, RolPk> {
    @Query("SELECT * FROM roles WHERE `user` = :User")
    Flux<Rol> findByIdUser(String User);
}
