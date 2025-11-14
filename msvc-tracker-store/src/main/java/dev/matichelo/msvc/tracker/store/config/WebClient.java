package dev.matichelo.msvc.tracker.store.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClient {
    @Bean
    @LoadBalanced // este anotación permite que el WebClient use el balanceador de carga de Eureka
    public org.springframework.web.reactive.function.client.WebClient.Builder webClientBuilder() {
        return org.springframework.web.reactive.function.client.WebClient.builder();
    }
}
