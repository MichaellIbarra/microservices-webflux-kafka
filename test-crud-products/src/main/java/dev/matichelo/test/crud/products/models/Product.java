package dev.matichelo.test.crud.products.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private String id;
    private String name;
    private String category;
    private double priceUnit;
    private int stock;
}
