package dev.matichelo.crud.products.reactive.app;

import dev.matichelo.crud.products.reactive.app.services.ProductService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @TestMethodOrder es una anotación que se utiliza para ordenar la ejecución de los métodos de prueba en una clase de prueba. En este caso, se está utilizando el orden de anotación para ejecutar los métodos de prueba en el orden en que están anotados con @Order.
@SpringBootTest
class CrudProductsReactiveApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    @Order(1)
    void testProducts() {
        StepVerifier.create(productService.products())
                .expectNextCount(8)
                .verifyComplete();
    }
}
