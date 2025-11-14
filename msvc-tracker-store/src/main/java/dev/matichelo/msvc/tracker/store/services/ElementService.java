package dev.matichelo.msvc.tracker.store.services;

import dev.matichelo.msvc.tracker.store.models.Element;
import reactor.core.publisher.Flux;

public interface ElementService {
    Flux<Element> elementsPriceMax(Double price);
}
