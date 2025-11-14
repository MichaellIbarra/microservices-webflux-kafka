package dev.matichelo.crud.products.reactive.app;

import dev.matichelo.crud.products.reactive.app.models.Product;
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
    @Order(3)
    void testRemoveProduct() {
        StepVerifier.create(productService.deleteProduct(102))
                .expectSubscription()
                .expectNextMatches(p -> p.getCodProduct() == 102)
                .verifyComplete();
    }

    @Test
    @Order(1)
    void testProducts() {
        StepVerifier.create(productService.products())
                // expectSubscription es una verificación que espera que se haya realizado una suscripción al flujo
                .expectSubscription()
                .expectNextCount(8)
                .verifyComplete();
    }

    @Test
    @Order(2)
    void testProductsCategory() {
        StepVerifier.create(productService.productsCategory("Alimentación"))
                // expectSubscription es una verificación que espera que se haya realizado una suscripción al flujo
                .expectSubscription()
                .expectNextMatches(p -> p.getName().equals("Azucar"))
                .expectNextMatches(p -> p.getName().equals("Leche"))
                .expectNextMatches(p -> p.getName().equals("Huevos"))
//                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    @Order(4)
    void testCreate(){

//        Product p = new Product(939,"Cerveza", "Bebida", 1.50, 20);
//        StepVerifier.create(productService.createProduct(p))
//                .expectSubscription()
//                .expectNextMatches(product -> product.getCodProduct() == 939)
//                .expectComplete()
//                .verify();
    }

}
