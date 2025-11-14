package dev.matichelo.crud.products.reactive.app.controllers;

import dev.matichelo.crud.products.reactive.app.models.Credentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.stream.Collectors;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin("*")
public class AuthRest {
    @Value("${security.jwt.secret-key}")
    String secretKey;

    private static final long TIME_TO_LIVE = 85_000_000; //  1 día y 1 hora en milisegundos

    // para la autenticación de usuarios
    @Autowired
    MapReactiveUserDetailsService userDetailsService;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody Credentials credentials){
        return userDetailsService.findByUsername(credentials.getUsername())
                .filter(userDetails -> userDetails.getPassword().equals(credentials.getPassword()))
                .map(userDetails -> new ResponseEntity<>(getToken(userDetails), HttpStatus.OK))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    private String getToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .claim("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .expiration(new Date(System.currentTimeMillis() + TIME_TO_LIVE))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }


}
