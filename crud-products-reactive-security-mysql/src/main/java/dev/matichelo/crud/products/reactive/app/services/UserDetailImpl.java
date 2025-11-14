package dev.matichelo.crud.products.reactive.app.services;

import dev.matichelo.crud.products.reactive.app.models.User;
import dev.matichelo.crud.products.reactive.app.repositories.RolRepository;
import dev.matichelo.crud.products.reactive.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class UserDetailImpl implements UserDetailService{

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UserRepository userRepository;

    // diferencias entre flatMap y map: el primero permite trabajar con Mono o Flux dentro de la función, mientras que el segundo es para valores simples.

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUser(username)
                .flatMap((User user) -> rolRepository.findByIdUser(username)
                        .map(r -> r.getId().getRol()) // Obtiene un Flux de roles
                        .collectList() // Convierte el Flux en una lista
                        .map(roles -> org.springframework.security.core.userdetails.User.withUsername(user.getUser())
                                .password(user.getPwd())
                                .roles(roles.toArray(new String[0]))
                                .build()) // Construye el UserDetails
                ).switchIfEmpty(Mono.empty());
    }
}
