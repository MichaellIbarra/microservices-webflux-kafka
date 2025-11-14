package dev.matichelo.msvc.tracker.store.services;

import dev.matichelo.msvc.tracker.store.models.Credentials;
import dev.matichelo.msvc.tracker.store.models.Element;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElementImpl implements ElementService{

    private final WebClient.Builder clientBuilder;

    String store1 = "http://crud-products-reactive";
    String store2 = "http://crud-products-reactive-v2";

    String token1, token2;
    @PostConstruct
    public void init(){
        Credentials credentials1 = new Credentials("user", "user123");
        Credentials credentials2 = new Credentials("admin", "admin123");
//        loadToken(store1, credentials1).subscribe(
//                token -> this.token1 = token,
//                error -> System.err.println("Error loading token for store1: " + error.getMessage())
//
//        );
//        loadToken(store2, credentials2).subscribe(
//                token -> this.token2 = token,
//                error -> System.err.println("Error loading token for store2: " + error.getMessage())
//        );
    }

    private Mono<String> loadToken(String url, Credentials credentials) {
        return WebClient.create(url)
                .post()
                .uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(credentials)
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(String.class);
    }


    @Override
    public Flux<Element> elementsPriceMax(Double price) {
        Flux<Element> store1Elements = catalog(store1, "store1", token1);
        Flux<Element> store2Elements = catalog(store2, "store2", token2);
        return Flux.merge(store1Elements, store2Elements)
                .filter(e -> e.getPriceUnit() <= price);
    }

    private Flux<Element> catalog(String url, String store, String token) {
//        WebClient client = WebClient.create(url);
        WebClient webClient = clientBuilder.build();
        return webClient.get()
                .uri(url+"/api/products")
//                .header("Authorization", "Basic "+ getEncoderBase64Credentials("user", "user123"))
//                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Element.class)
                .map(e -> {
                    e.setStore(store);
                    log.info("Endpoint: {}, Store: {}, Element: {}", url, store, e);
                    return e;
                });
    }

    private String getEncoderBase64Credentials(String user, String pwd){
        return Base64.getEncoder().encodeToString( (user + ":" + pwd).getBytes() );
    }

}
