package dev.matichelo.crud.products.reactive.app.services;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface UserDetailService {
    Mono<UserDetails> findByUsername(String username);
}
