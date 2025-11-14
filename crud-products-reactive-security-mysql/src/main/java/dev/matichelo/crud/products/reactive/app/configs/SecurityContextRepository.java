package dev.matichelo.crud.products.reactive.app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    /*
    # Documentación de SecurityContextRepository

La clase `SecurityContextRepository` es un componente esencial en la implementación de seguridad JWT para aplicaciones Spring WebFlux reactivas.

## Implementación de ServerSecurityContextRepository

Esta clase implementa la interfaz `ServerSecurityContextRepository` porque:

1. **Gestión del contexto de seguridad**: Proporciona los mecanismos para cargar y guardar el `SecurityContext` en un entorno reactivo basado en WebFlux.

2. **Integración con JWT**: Adapta el mecanismo de autenticación tradicional de Spring Security para trabajar con tokens JWT en lugar de sesiones.

3. **Procesamiento no bloqueante**: Mantiene el modelo de programación reactiva necesario en Spring WebFlux.

## Métodos principales

### save(ServerWebExchange exchange, SecurityContext context)

```java
@Override
public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    return Mono.empty();
}
```

Este método:
- Retorna un `Mono` vacío porque en una arquitectura basada en JWT no es necesario guardar el contexto de seguridad del lado del servidor.
- Los tokens JWT son stateless (sin estado), por lo que la información de autenticación se almacena en el propio token y no en una sesión del servidor.

### load(ServerWebExchange exchange)

```java
@Override
public Mono<SecurityContext> load(ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("Authorization"))
            .filter(authHeader -> authHeader.startsWith("Bearer "))
            .flatMap(authHeader ->
                    this.authManager.authenticate(new UsernamePasswordAuthenticationToken(authHeader.substring(7), null))
                        .map(SecurityContextImpl::new));
}
```

Este método:
1. **Extrae el token JWT**: Busca el header "Authorization" en la solicitud HTTP y verifica que comience con "Bearer ".
2. **Procesa el token**:
   - Crea un objeto `UsernamePasswordAuthenticationToken` temporal con el token JWT (sin el prefijo "Bearer ").
   - Este token actúa como un contenedor para pasar el JWT al `AuthManager`.
3. **Delega la autenticación**: Envía el token al `AuthManager` para verificar su validez y extraer la información.
4. **Crea el contexto de seguridad**: Si la autenticación es exitosa, crea un nuevo `SecurityContextImpl` con la autenticación verificada.

## Dependencias

La clase depende de:
- `AuthManager`: Inyectado mediante `@Autowired` para realizar la verificación y procesamiento del token JWT.

## Flujo de trabajo

1. Cuando llega una solicitud, Spring Security invoca el método `load()`.
2. Si hay un token JWT válido, se crea un contexto de seguridad con la información del usuario.
3. Este contexto permite que Spring Security aplique las reglas de autorización definidas en la configuración.

Este componente es crucial para la arquitectura de seguridad stateless basada en JWT, ya que conecta los tokens de solicitud con el sistema de autorización interno de Spring Security.
     SecurityContextImpl es una implementación de `SecurityContext` que contiene la información de autenticación del usuario, permitiendo que Spring Security maneje la autorización de manera adecuada.
     justOrEmpty es un método de Reactor que crea un `Mono` a partir de un valor existente o devuelve un `Mono.empty
                          ()` si el valor es nulo, lo que permite manejar de manera reactiva la ausencia de un token JWT en la solicitud.
     ServerWebExchange es una interfaz que representa el contexto de la solicitud y respuesta en un entorno WebFlux, permitiendo acceder a los headers, parámetros y otros detalles de la solicitud HTTP.
     */

    @Autowired
    private AuthManager authManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("Authorization"))
                .filter(authHeader -> authHeader != null && authHeader.startsWith("Bearer "))
                .flatMap(authHeader ->
                        this.authManager.authenticate(new UsernamePasswordAuthenticationToken(null, authHeader))
                                .map(SecurityContextImpl::new)
                );
    }
}
