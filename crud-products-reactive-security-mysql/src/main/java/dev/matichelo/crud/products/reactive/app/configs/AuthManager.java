package dev.matichelo.crud.products.reactive.app.configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthManager implements ReactiveAuthenticationManager {

    @Value("${security.jwt.secret-key}")
    String secretKey;

    /*
    # Documentación de la clase AuthManager

La clase `AuthManager` es un componente fundamental en la implementación de seguridad de una aplicación Spring WebFlux reactiva. Veamos por qué implementa `ReactiveAuthenticationManager` y el propósito de su método `authenticate`.

## Implementación de ReactiveAuthenticationManager

`AuthManager` implementa la interfaz `ReactiveAuthenticationManager` porque:

1. **Modelo reactivo**: En Spring WebFlux (framework reactivo), todos los componentes deben trabajar de manera no bloqueante. `ReactiveAuthenticationManager` es la versión reactiva del `AuthenticationManager` tradicional de Spring Security.

2. **Flujo de autenticación**: Es responsable de validar las credenciales de autenticación y devolver un objeto `Authentication` con los detalles del usuario autenticado, usando programación reactiva.

## El método authenticate

El método `authenticate` es el núcleo de esta clase:

```java
@Override
public Mono<Authentication> authenticate(Authentication authentication) {
    // Implementación...
}
```

Este método:

1. **Recibe**: Un objeto `Authentication` que contiene el token JWT extraído de la solicitud HTTP.

2. **Retorna**: Un `Mono<Authentication>` que representa el resultado asíncrono de la autenticación, conteniendo información del usuario autenticado.

## Funcionamiento interno del método

El método realiza las siguientes operaciones:

1. **Procesa el token JWT**:
   - Convierte el objeto Authentication a un flujo Mono
   - Extrae el token JWT de las credenciales (quitando el prefijo "Bearer ")
   - Verifica la firma usando la clave secreta configurada en `application.properties`

2. **Extrae información**:
   - Obtiene el subject (normalmente el nombre de usuario)
   - Recupera la lista de autoridades/roles del usuario desde el claim "authorities"

3. **Crea un nuevo objeto de autenticación**:
   - Genera un `UsernamePasswordAuthenticationToken` con:
     - El nombre de usuario como principal
     - Null como credenciales (ya no son necesarias)
     - Las autoridades convertidas a objetos `SimpleGrantedAuthority`
# Explicación del paso de creación de objeto de autenticación

## ¿Por qué se crea un nuevo objeto UsernamePasswordAuthenticationToken?

Este paso es crucial porque:

1. **Establece la identidad autenticada**: Una vez validado el token JWT, se necesita crear un objeto que Spring Security reconozca como un usuario autenticado.

2. **Proporciona contexto de seguridad**: Este objeto se almacenará en el `SecurityContext` de Spring Security para autorizar las operaciones posteriores.

3. **Transforma datos JWT en formato Spring Security**: Convierte la información extraída del token JWT en una estructura que Spring Security puede utilizar para el control de acceso.

## Parámetros del UsernamePasswordAuthenticationToken

- **Principal (nombre de usuario)**: Identifica al usuario (obtenido del subject del JWT)
- **Credenciales null**: Las credenciales originales (contraseña) no son necesarias después de la autenticación
- **Autoridades**: Representan los permisos/roles del usuario

## ¿Qué es SimpleGrantedAuthority?

`SimpleGrantedAuthority` es una implementación de la interfaz `GrantedAuthority` de Spring Security que:

1. **Representa un permiso**: Cada objeto `SimpleGrantedAuthority` contiene un string que representa un rol o permiso (como "ROLE_ADMIN", "READ_DATA", etc.)

2. **Se utiliza para autorización**: Spring Security utiliza estas autoridades para determinar si un usuario puede acceder a recursos protegidos mediante expresiones como `hasRole('ADMIN')` o `hasAuthority('READ_DATA')` en configuraciones de seguridad.

3. **Conversión desde JWT**: En este código, las autoridades vienen como strings en el JWT y se transforman en objetos `SimpleGrantedAuthority` para que Spring Security pueda realizar verificaciones de autorización en las rutas protegidas.

Este paso permite que la aplicación use el mecanismo estándar de Spring Security para autorización basado en roles, aunque la autenticación se realice mediante tokens JWT en lugar del mecanismo tradicional de sesiones.
4. **Manejo de errores**:
   - Si el token es inválido o ha expirado, el flujo se interrumpe
   - Utiliza `switchIfEmpty` para manejar el caso cuando el token no es válido

Esta implementación permite que la aplicación procese tokens JWT de manera no bloqueante, verificando su validez y extrayendo la información necesaria para autorizar al usuario en las rutas protegidas del sistema.
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> Jwts.parser()
                        .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                        .build()
                        .parseSignedClaims(auth.getCredentials().toString().replace("Bearer ", ""))
                        .getPayload())
                .switchIfEmpty(Mono.empty())
                .map(claims -> new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        ((List<String>) claims.get("authorities", List.class))
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())
                ));
    }
}
