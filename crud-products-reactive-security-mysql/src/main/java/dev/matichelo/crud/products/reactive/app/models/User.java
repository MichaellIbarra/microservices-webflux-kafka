package dev.matichelo.crud.products.reactive.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "users")
public class User {
    private String user;
    private String pwd;
    private boolean enabled;
}
