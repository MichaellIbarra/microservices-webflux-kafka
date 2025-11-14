package dev.matichelo.msvc.tracker.store.controllers;

import dev.matichelo.msvc.tracker.store.models.Element;
import dev.matichelo.msvc.tracker.store.services.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/tracker")
public class ElementController {
    @Autowired
    ElementService elementService;

    @GetMapping("/elements/price/max/{price}")
    public ResponseEntity<Flux<Element>> elementsPriceMax(@PathVariable Double price) {
        Flux<Element> elements = elementService.elementsPriceMax(price);
        return ResponseEntity.ok(elements);
    }
}
