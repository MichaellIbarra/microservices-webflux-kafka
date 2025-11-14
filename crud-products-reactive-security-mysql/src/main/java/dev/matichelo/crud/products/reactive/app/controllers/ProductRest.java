package dev.matichelo.crud.products.reactive.app.controllers;

import dev.matichelo.crud.products.reactive.app.models.Product;
import dev.matichelo.crud.products.reactive.app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductRest {
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

    @GetMapping
    public ResponseEntity<Flux<Product>> products(){
        return new ResponseEntity<>(productService.products(), HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Flux<Product>> productsCategory(@PathVariable String category){
        return new ResponseEntity<>(productService.productsCategory(category), HttpStatus.OK);
    }

    @GetMapping("/{codProduct}")
    public ResponseEntity<Mono<Product>> productById(@PathVariable int codProduct){
        return new ResponseEntity<>(productService.productById(codProduct), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Mono<Product>> createProduct(@RequestBody Product product){
        product.setNewP(true);
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/{codProduct}/price/{price}")
    public Mono<ResponseEntity<Product>> updatePrice(@PathVariable int codProduct, @PathVariable double price){
        return productService.updatePrice(codProduct, price).map(
                p -> new ResponseEntity<>(p, HttpStatus.OK)
        ).switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/{codProduct}")
    public Mono<ResponseEntity<Product>> deleteProduct(@PathVariable int codProduct){
        return productService.deleteProduct(codProduct).map(
                p -> new ResponseEntity<>(p, HttpStatus.OK)
        ).switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

}
