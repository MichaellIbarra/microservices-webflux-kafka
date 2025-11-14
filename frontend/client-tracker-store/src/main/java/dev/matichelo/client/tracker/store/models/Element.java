package dev.matichelo.client.tracker.store.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Element {
    private String name;
    private String category;
    private double priceUnit;
    private String store;
}
