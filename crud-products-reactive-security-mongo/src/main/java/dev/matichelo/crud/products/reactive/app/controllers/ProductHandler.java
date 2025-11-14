package dev.matichelo.crud.products.reactive.app.controllers;

import dev.matichelo.crud.products.reactive.app.models.Product;
import dev.matichelo.crud.products.reactive.app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class ProductHandler {
    @Autowired
    private ProductService productService;
/*
    ResponseEntity es una clase que representa una respuesta HTTP, en este caso se utiliza para devolver una respuesta con un cuerpo de tipo Flux<Product>.
    ServerResponse es una interfaz que representa una respuesta HTTP, en este caso se utiliza para devolver una respuesta con un cuerpo de tipo Mono<Product>.

   ResponseEntity<Flux<Product>> y Mono<ResponseEntity<Product>> , sus diferencias son las siguientes:
    1. ResponseEntity<Flux<Product>> es una respuesta que contiene un cuerpo de tipo Flux<Product>, que es una secuencia de productos, mientras que Mono<ResponseEntity<Product>> es una respuesta que contiene un cuerpo de tipo ResponseEntity<Product>, que es una respuesta HTTP con un cuerpo de tipo Product.
    2. ResponseEntity<Flux<Product>> se utiliza para devolver una respuesta con un cuerpo de tipo Flux<Product>, que es una secuencia de productos, mientras que Mono<ResponseEntity<Product>> se utiliza para devolver una respuesta con un cuerpo de tipo ResponseEntity<Product>, que es una respuesta HTTP con un cuerpo de tipo Product.
    3. caso de una respuesta vacía, ResponseEntity<Flux<Product>> devolverá un cuerpo vacío, mientras que Mono<ResponseEntity<Product>> devolverá una respuesta HTTP con un cuerpo de tipo ResponseEntity<Product>, que es una respuesta HTTP con un cuerpo de tipo Product.

 controller en el que el estado y la cabecera deben devolverse de forma síncrona, mientras que el cuerpo de tipo String se debe devolver de forma asíncrona , en que caso suede eso?
 Este caso ocurre cuando necesitas devolver una respuesta HTTP donde el estado y las cabeceras se determinan inmediatamente (de forma síncrona), pero el cuerpo de la respuesta se genera o se obtiene de forma asíncrona. Esto es útil en aplicaciones reactivas cuando el contenido del cuerpo puede tardar en generarse o recuperarse, pero el cliente necesita recibir el estado y las cabeceras de inmediato.

Un ejemplo típico es cuando el cuerpo de la respuesta es un flujo reactivo (Mono o Flux) que se procesa de forma asíncrona, pero el estado HTTP y las cabeceras se configuran antes de que el cuerpo esté disponible.
 */

    /*
    En Spring WebFlux, necesitas primero extraer el cuerpo de la petición (request body) como un objeto Product usando req.bodyToMono(Product.class) porque el cuerpo de la petición HTTP aún no está deserializado cuando llega al handler. El método del servicio (productService.createProduct) espera un objeto Product, no un ServerRequest.

Por eso, el flujo es:


req.bodyToMono(Product.class) convierte el cuerpo JSON en un objeto Product de forma reactiva.
Luego, con .flatMap(p -> productService.createProduct(p)), pasas ese objeto al servicio.
Finalmente, construyes la respuesta con el resultado.
No puedes pasar directamente req.bodyToMono(Product.class) al servicio porque el servicio espera un Product, no un Mono<Product>. Por eso necesitas el encadenamiento con flatMap.
     */
    @Bean
    public RouterFunction<ServerResponse> productRoute(){
        return RouterFunctions.route(RequestPredicates.GET("/api/v2/products"),
                req -> ServerResponse.ok().body(productService.products(), Product.class))
                .andRoute(RequestPredicates.GET("/api/v2/products/category/{category}"),
                        req -> ServerResponse.ok().body(productService.productsCategory(req.pathVariable("category"))
                                , Product.class))
                .andRoute(RequestPredicates.GET("/api/v2/product"),
                        req -> ServerResponse.ok().body(productService.productById(req.queryParam("codProduct").orElse(""))
                                , Product.class))
                .andRoute(RequestPredicates.POST("/api/v2/products"),
                        req -> req.bodyToMono(Product.class)
                                .flatMap(p -> productService.createProduct(p))
                                .flatMap(p -> ServerResponse.status(HttpStatus.CREATED).bodyValue(p))
                )
                .andRoute(RequestPredicates.DELETE("/api/v2/products/{codProduct}"),
                        req -> productService.deleteProduct(req.pathVariable("codProduct"))
                                .flatMap(p -> ServerResponse.ok().bodyValue(p))
                                .switchIfEmpty(ServerResponse.notFound().build()))
                .andRoute(RequestPredicates.PUT("/api/v2/products/{codProduct}/price/{price}"),
                        request -> productService.updatePrice(request.pathVariable("codProduct"),
                                Double.parseDouble(request.pathVariable("price")))
                                .flatMap(p -> ServerResponse.ok().bodyValue(p))
                                .switchIfEmpty(ServerResponse.notFound().build()));
    }

    /*Usa bodyValue(p) cuando tienes un objeto simple.
    Usa body(publisher, Class) cuando tienes un Mono o Flux.*/

//    @Bean
//    CorsWebFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return new CorsWebFilter(source);
//    }
}
