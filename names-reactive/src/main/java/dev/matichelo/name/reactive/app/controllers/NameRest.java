package dev.matichelo.name.reactive.app.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

//@RestController
@Configuration
public class NameRest {

//    @GetMapping("/names")
//    public Flux<String> getNames() {
//        // of está es una forma de crear un flujo de datos
//        List<String> names = List.of("one", "two", "three", "four", "five");
//        return Flux.fromIterable(names)
//                .delayElements(Duration.ofSeconds(2));
//    }

    // la anotación @Bean es para que el método se registre como un bean en el contexto de la aplicación
    @Bean
    // RouterFunction es una interfaz que define una función que recibe una solicitud y devuelve una respuesta
    // ServerResponse es una clase que representa una respuesta HTTP
    // RequestPredicates es una clase que define predicados para las solicitudes HTTP

    public RouterFunction<ServerResponse> getName(){
        List<String> names = List.of("one", "two", "three", "four", "five");
        return RouterFunctions.route(RequestPredicates.GET("/names"),
                request -> ServerResponse.ok().body(Flux.fromIterable(names), String.class));

    }


}
