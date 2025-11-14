package dev.matichelo.client.tracker.store.services;

import dev.matichelo.client.tracker.store.models.Element;
import reactor.core.publisher.Flux;

public interface ElementService {
    public Flux<Element> elementsPriceMax(Double price);
}
