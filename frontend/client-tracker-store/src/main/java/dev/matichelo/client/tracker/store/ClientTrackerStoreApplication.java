package dev.matichelo.client.tracker.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ClientTrackerStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientTrackerStoreApplication.class, args);
    }

    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }

}
