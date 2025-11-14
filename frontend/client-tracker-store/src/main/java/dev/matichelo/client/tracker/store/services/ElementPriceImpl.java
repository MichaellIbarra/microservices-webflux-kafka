package dev.matichelo.client.tracker.store.services;

import dev.matichelo.client.tracker.store.models.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
@Service
public class ElementPriceImpl implements ElementService{

    String url = "http://localhost:7000/products";

    @Autowired
    WebClient webClient;
    /*
    Es mejor instanciar el WebClient como un @Bean porque:
    Reutilización: El WebClient configurado como Bean puede ser inyectado en múltiples servicios, evitando crear una nueva instancia cada vez.
    Configuración centralizada: Permite configurar propiedades comunes (timeouts, interceptores, codificadores) en un solo lugar.
    Testabilidad: Facilita el reemplazo del cliente por mocks en pruebas unitarias.
    Gestión del ciclo de vida: Spring se encarga de crear y destruir la instancia.
    En tu código actual ya estás aprovechando esta ventaja al usar @Autowired WebClient webClient en el servicio, permitiendo que Spring inyecte el Bean configurado en ClientTrackerStoreApplication.
    Si crearas el WebClient en cada método (como se ve comentado en elementsPriceMax), perderías estas ventajas y posiblemente desperdiciarías recursos al crear múltiples instancias innecesarias.
     */
    @Override
    public Flux<Element> elementsPriceMax(Double price) {
//        WebClient webClient = WebClient.create(url);
        return webClient.get()
                .uri(url+"/elements/price/max/{price}", price)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Element.class);
    }
}
