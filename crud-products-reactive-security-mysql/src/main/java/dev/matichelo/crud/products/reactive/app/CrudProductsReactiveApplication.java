package dev.matichelo.crud.products.reactive.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories
@SpringBootApplication
public class CrudProductsReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudProductsReactiveApplication.class, args);
    }

}
