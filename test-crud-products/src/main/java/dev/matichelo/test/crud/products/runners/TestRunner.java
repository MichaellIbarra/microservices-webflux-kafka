package dev.matichelo.test.crud.products.runners;

import dev.matichelo.test.crud.products.models.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {


        WebClient webClient = WebClient.create("http://localhost:8002");

        // add headers authorization
//        Flux<Product> productFlux = webClient.get()
//                .uri("/api/products")
//                .accept(MediaType.APPLICATION_JSON)
//                .headers(headers -> headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1MjE5NDM4OSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJleHAiOjE3NTIyNzkzODl9.Ay0O6fmbSivC3S-3UPcib2F-Ox34PmkfPoLM-6IgaEGgkEEOKTdfTGYaA4A7yqmG0zj7Cj-1faghqfwzllqGVg"))
//                .retrieve() // Realiza la solicitud HTTP y recupera la respuesta
//                .bodyToFlux(Product.class) // Convierte la respuesta en un flujo de objetos Product
//                .doOnError(error -> System.err.println("Error fetching products: " + error.getMessage()))
//                .doOnComplete(() -> System.out.println("Product fetch completed successfully"))
//                .doOnTerminate(() -> System.out.println("Product fetch terminated successfully"));
//        productFlux.subscribe(product -> System.out.println(
//                "Product: " + product.getId() + ", Name: " + product.getName() +
//                ", Category: " + product.getCategory() + ", Price: " + product.getPriceUnit() +
//                ", Stock: " + product.getStock()
//        ));


//        Mono<Product> productMono = webClient.post()
//                .uri("/api/products")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(new Product(290, "Test Product", "Test Category", 99.99, 10))
//                .retrieve()
//                .bodyToMono(Product.class)
//                        .doOnError(error -> System.err.println("Error creating product: " + error.getMessage()))
//                        .doOnSuccess(product -> System.out.println("Product created successfully: " + product.getName()))
//                                .doOnTerminate(() -> System.out.println("Product terminated successfully"));
        // .block() es una llamada bloqueante que espera a que se complete la operación y devuelve el resultado.
//                                        .block();
//        productMono.subscribe(product -> System.out.println(
//                "Created Product: " + product.getCodProduct() + ", Name: " + product.getName() +
//                ", Category: " + product.getCategory() + ", Price: " + product.getPriceUnit() +
//                ", Stock: " + product.getStock()
//        ));

//        Flux<Product> productFlux = webClient.get()
//                .uri("/api/products")
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToFlux(Product.class);
//        productFlux.subscribe(product -> System.out.println(
//                "Product: " + product.getCodProduct() + ", Name: " + product.getName() +
//                        ", Category: " + product.getCategory() + ", Price: " + product.getPriceUnit() +
//                        ", Stock: " + product.getStock()
//        ));

//        Mono<Product> productDelete = webClient.delete()
//                .uri("/api/products/102")
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, t -> {
//                    System.out.println("No product found with the given ID");
//                    return Mono.empty();
//                })
//                .bodyToMono(Product.class)
//                .doOnError(error -> System.err.println("Error deleting product: " + error.getMessage()))
////                .doOnSuccess(product -> System.out.println("Product deleted successfully: " + product.getName()))
//                .doOnTerminate(() -> System.out.println("Product deletion terminated successfully"));
//        productDelete.subscribe(product -> System.out.println(
//                "Deleted Product: " + product.getCodProduct() + ", Name: " + product.getName() +
//                ", Category: " + product.getCategory() + ", Price: " + product.getPriceUnit() +
//                ", Stock: " + product.getStock()
//        ));
//        Mono<Product> productView = webClient.get()
//                .uri("/api/products/102")
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(Product.class)
//                .switchIfEmpty(Mono.just(new Product()).map(p -> {
//            System.out.println("Product not found, returning default product");
//           return p;}))
//                .doOnError(error -> System.err.println("Error fetching product: " + error.getMessage()))
////                .doOnSuccess(product -> System.out.println("Product fetched successfully: " + product.getName()))
//                .doOnTerminate(() -> System.out.println("Product fetch terminated successfully"));
//
//
//        productView.subscribe(product -> System.out.println(
//                "Fetched Product: " + product.getCodProduct() + ", Name: " + product.getName() +
//                ", Category: " + product.getCategory() + ", Price: " + product.getPriceUnit() +
//                ", Stock: " + product.getStock()
//        ));
//        productView.switchIfEmpty(Mono.just(new Product()).map(p -> {
//            System.out.println("Product not found, returning default product");
//            return p;
//        })).block();

/*       El método .block() es parte de la API de Project Reactor, que es utilizado en WebFlux para programación reactiva. Voy a explicar su función y por qué se usa en este contexto:
Uso limitado: En aplicaciones web con Spring WebFlux, se desaconseja usar .block() porque bloquea el hilo y reduce los beneficios de la programación reactiva.


Bloqueo del hilo: El hilo actual quedará bloqueado hasta que la operación termine o expire el tiempo de espera.
Convierte operaciones asíncronas en síncronas: Project Reactor/WebFlux trabaja con flujos asincrónicos (Mono/Flux), pero .block() espera hasta que la operación termine antes de continuar.


Para casos de prueba o aplicaciones de línea de comandos: En este código específico, estás en un CommandLineRunner, donde necesitas asegurar que la operación se complete antes de que termine la ejecución del método run().


Para obtener el resultado: .block() devuelve el valor contenido en el Mono, permitiendo acceder directamente al resultado.

 */


    }
}
