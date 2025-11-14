package dev.matichelo.crud.products.reactive.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "roles")
public class Rol {
    @Id
    @Embedded.Empty // se utiliza para indicar que el campo es una clave primaria compuesta
    private RolPk id;
}
