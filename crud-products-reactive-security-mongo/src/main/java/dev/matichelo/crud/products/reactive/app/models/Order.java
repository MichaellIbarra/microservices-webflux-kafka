package dev.matichelo.crud.products.reactive.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private String codProduct;
    private String nomeProduct;
    private int units;
    private String address;
}