package dev.matichelo.crud.products.reactive.app.configs;

import dev.matichelo.crud.products.reactive.app.services.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@EnableWebFluxSecurity
@Configuration
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthManager authManager;
    private final SecurityContextRepository securityContextRepository;
    private final UserDetailService userDetailService;

//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        List<UserDetails> users = List.of(
//                User.withUsername("admin")
//                        .password("admin123") // {noop} indicates that no password encoder is used
//                        .roles("ADMIN", "USER")
//                        .build(),
//                User.withUsername("user")
//                        .password("user123") // {noop} indicates that no password encoder is used
//                        .roles("USER")
//                        .build()
//        );
//        return new MapReactiveUserDetailsService(users);
//    }
    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return userDetailService::findByUsername;
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http)  {

        http
                // csrf es protección contra ataques CSRF (Cross-Site Request Forgery)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // authenticationManager es el administrador de autenticación personalizado
                .authenticationManager(authManager)
                // securityContextRepository es el repositorio del contexto de seguridad personalizado
                .securityContextRepository(securityContextRepository)
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers(HttpMethod.POST,"/api/products").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE,"/api/products/{codProduct}").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT,"/api/products/{codProduct}/price/{price}").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET,"/api/products/**").authenticated()
//                        .anyExchange().denyAll()
//                                .anyExchange().authenticated()
                        .anyExchange().permitAll() // Allow all other requests
//                        .anyExchange().access(hasRole("USER")) // Allow all other requests for users with the USER role
                );
//                .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic authentication

        return http.build();
    }

}
