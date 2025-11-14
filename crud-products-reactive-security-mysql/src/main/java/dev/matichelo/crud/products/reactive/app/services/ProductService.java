package dev.matichelo.crud.products.reactive.app.services;

import dev.matichelo.crud.products.reactive.app.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductService {
    Flux<Product> products();
    Flux<Product> productsCategory(String category);
    Mono<Product> productById(int codProduct);
    Mono<Product> createProduct(Product product);
    Mono<Product> updatePrice(int codProduct, double price);
    Mono<Product> deleteProduct(int codProduct);


}
