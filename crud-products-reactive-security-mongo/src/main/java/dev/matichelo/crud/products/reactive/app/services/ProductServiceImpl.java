package dev.matichelo.crud.products.reactive.app.services;

import dev.matichelo.crud.products.reactive.app.models.Order;
import dev.matichelo.crud.products.reactive.app.models.Product;
import dev.matichelo.crud.products.reactive.app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
/*
    private static List<Product> productList = new ArrayList<>(List.of(new Product(100, "Azucar", "Alimentación", 1.10,
                    20),
            new Product(101, "Leche", "Alimentación", 5, 15),
            new Product(102, "Jabón", "Limpieza", 8, 30),
            new Product(103, "Mesa", "Hogar", 126, 4),
            new Product(104, "Televisión", "Hogar", 650, 10),
            new Product(105, "Huevos", "Alimentación", 2.20, 30),
            new Product(106, "Fregona", "Limpieza", 3.40, 6),
            new Product(107, "Detergente", "Limpieza", 8.7, 12)));

 */
    @Override
    public Flux<Product> products() {
//        return Flux.fromIterable(productList);
//                .delayElements(Duration.ofSeconds(2));
        return productRepository.findAll();
    }

    @Override
    public Flux<Product> productsCategory(String category) {
        /*
        predicate es una función que recibe un elemento y devuelve un booleano
        filter() devuelve un nuevo flujo que contiene solo los elementos que cumplen con la condición
         */
//        return products().filter(p -> p.getCategory().equals(category));
        return productRepository.findByCategory(category);
    }

    @Override
    public Mono<Product> productById(String codProduct) {
//        return productList.stream().filter(p -> p.getCodProduct() == codProduct).findFirst().orElse(null);
  /*      return products().filter(p -> p.getCodProduct() == codProduct)
                .next();*/
//                .switchIfEmpty(Mono.error(new Exception("El producto no existe")));
        return productRepository.findById(codProduct);
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        /*
        Se utiliza Mono.just(product) para crear un flujo reactivo (Mono) que emite el producto proporcionado. Esto es necesario porque el método switchIfEmpty espera un Mono como alternativa en caso de que el flujo original esté vacío.

El uso de Mono.just(product).map(...) permite encapsular el producto en un flujo reactivo y luego transformarlo (en este caso, agregarlo a la lista y devolverlo). Sin Mono.just, no se podría realizar esta operación de manera reactiva.
Mono.defer(() -> {...}) se utiliza para crear un Mono que se ejecutará de forma diferida, es decir, cuando se suscriba a él. Esto es útil para evitar la creación de un Mono innecesario si el producto ya existe.
         */

//        return productById(product.getId())
//                .flatMap(Mono::just)
//                .switchIfEmpty( Mono.defer(() -> {
////                    productList.add(product);
//                    return productRepository.save(product);
////                    return Mono.just(product);
//        }));
        return productRepository.save(product);

//        return productRepository.save(product);


    }

    @Override
    public Mono<Product> updatePrice(String codProduct, double price) {
//        Product product = productById(codProduct);
//        if(product != null){
//            product.setPriceUnit(price);
//        }else {
//            System.out.println("El producto no existe");
//        }
//        return product;

        return productRepository.findById(codProduct)
                .flatMap(p -> {
                    p.setPriceUnit(price);
                    return productRepository.save(p);
//                    return Mono.just(p);
                });


    }

    @Override
    public Mono<Product> deleteProduct(String codProduct) {

        /*
        Uso de flatMap y map:
        flatMap: se utiliza para transformar el resultado de una operación asíncrona en otra operación asíncrona.
        map: se utiliza para transformar el resultado de una operación asíncrona en un valor sincrónico.
        then(): se utiliza para indicar que no se espera ningún valor de la operación asíncrona.
        switchIfEmpty: se utiliza para manejar el caso en que la operación asíncrona no devuelve ningún valor.
        Mono.empty(): se utiliza para indicar que no se espera ningún valor de la operación asíncrona.
        Mono.error(): se utiliza para indicar que ha ocurrido un error en la operación asíncrona.
        Mono.defer(): se utiliza para crear un Mono que se ejecutará de forma diferida, es decir, cuando se suscriba a él.
        publisher: se utiliza para crear un flujo reactivo que emite elementos de forma asíncrona.
        subscribe: se utiliza para suscribirse a un flujo reactivo y recibir los elementos emitidos por él.


        diferencias entre usar then y Mono.empty():
        then se utiliza cuando se espera que la operación asíncrona devuelva un valor, mientras que Mono.empty() se utiliza cuando no se espera ningún valor de la operación asíncrona.

         */

//        return this.productById(codProduct)
//                .flatMap(p -> {
////                    productList.removeIf(product -> product.getCodProduct() == codProduct);
//                    return Mono.just(p);
//                });

//        return this.productById(codProduct)
//                .map(p -> {
//                    productList.remove(p);
//                    return p;
//                });
//                .then();
//                .switchIfEmpty(Mono.error(new Exception("El producto no existe")));

        return productRepository.findById(codProduct)
                .flatMap(p -> productRepository.deleteById(codProduct).then(Mono.just(p)));
    }

    // Kafka listener para recibir órdenes y actualizar el stock de productos
    @KafkaListener(topics = "${kafka.topic.orders}")
    public void managementOrder(Order order){
        System.out.println("Orden recibida: " + order); // Verifica el valor de units aquí

        productRepository.findById(order.getCodProduct())
                .flatMap(pr -> {
//                    if (pr.getStock() >= order.getUnits()) {
                        pr.setStock(pr.getStock() - order.getUnits());
                        return productRepository.save(pr);
//                    }
                })
                .subscribe(
                        updatedProduct -> System.out.println("Producto actualizado: " + updatedProduct),
                        error -> System.err.println("Error al actualizar el producto: " + error.getMessage())
                );
    }

}
