package dev.matichelo.client.tracker.store.controllers;

import dev.matichelo.client.tracker.store.services.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

@Controller
public class ElementController {
    @Autowired
    ElementService elementService;

    @GetMapping("/search/price/max")
    public String search(@RequestParam(name = "price") Double priceMax, Model model){
        // Condiciones para validar el precio
        if (priceMax == null || priceMax <= 0) {
            model.addAttribute("error", "Invalid price value");
            return "error";
        }

        // Llamada al servicio para obtener los elementos con el precio máximo
        // IReactiveDataDriverContextVariable es una variable de contexto que permite la paginación y la carga diferida de datos en Thymeleaf
        IReactiveDataDriverContextVariable contextVariable =
                new ReactiveDataDriverContextVariable(elementService.elementsPriceMax(priceMax), 1);

        model.addAttribute("elements", contextVariable);
        return "elements";
    }

    @GetMapping
    public String home(){
        return "index";
    }

}
