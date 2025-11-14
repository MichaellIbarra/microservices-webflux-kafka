package dev.matichelo.crud.products.reactive.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Credentials {
    private String username;
    private String password;
}
