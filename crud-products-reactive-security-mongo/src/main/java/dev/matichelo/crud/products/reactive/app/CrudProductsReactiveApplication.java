package dev.matichelo.crud.products.reactive.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class CrudProductsReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudProductsReactiveApplication.class, args);
    }

}
