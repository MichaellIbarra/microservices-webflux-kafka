package dev.matichelo.crud.products.reactive.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("products")
public class Product implements Persistable<Integer> {
    @Id
    @Column("codProduct")
    private int codProduct;
    private String name;
    private String category;
    @Column("priceUnit")
    private double priceUnit;
    private int stock;

    // la anotación @Transient indica que este campo no debe ser persistido en la base de datos, es decir, no se guardará en la tabla de productos.
    @Transient
    private boolean newP;

    @Override
    public Integer getId() {
        return codProduct;
    }

    @Override
    public boolean isNew() {
        return newP;
    }
}
