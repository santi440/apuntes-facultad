# DTO y proyecciones
Hasta ahora todos los repositorios devuelven entidades completas. En muchos casos de uso real, la aplicación solo necesita un subconjunto de los datos. Para esto Spring Data JPA ofrece proyecciones y DTOs.
## 4.1 Qué es un DTO y cuando usarlo
### 32. ¿Que es un DTO (Data Transfer Object)? ¿Cuál es su propósito dentro de la arquitectura de una aplicación? ¿Por qué no siempre conviene devolver una entidad JPA directamente desde la capa de acceso a datos?

Un **DTO (Data Transfer Object)** es un objeto plano, sin lógica de negocio, cuyo único propósito es agrupar datos para trasladarlos entre capas o sistemas. No tiene anotaciones de persistencia, no está gestionado por ningún contexto, y su estructura refleja exactamente qué datos necesita el receptor — ni más ni menos.

**Propósito dentro de la arquitectura:** La capa de acceso a datos conoce el modelo de persistencia; la capa de presentación o los clientes externos conocen contratos de datos. El DTO es el punto de traducción entre ambos mundos. Permite que cada capa evolucione de forma independiente: si cambia la tabla, el DTO no cambia; si cambia lo que necesita el cliente, el DTO cambia sin tocar la entidad.

**¿Por qué no conviene devolver siempre una entidad JPA?**

Tres razones estructurales:
1. **Sesión cerrada**: Una entidad con colecciones `LAZY` no inicializadas lanzará `LazyInitializationException` en el momento en que alguien intente accederlas fuera de la transacción activa — exactamente el momento en que el serializador intenta convertirla a JSON.
2. **Exceso de datos**: Una entidad JPA modela el dominio completo. Devolver `Route` para mostrar solo su nombre y precio arrastra `stops`, `purchaseList`, `driverList`, `tourGuideList` — iniciando N+1 queries o cargando datos que nadie usa.
3. **Contratos implícitos frágiles**: Los consumidores programan contra los campos de la entidad. Cuando el modelo interno cambia (un campo renombrado, una relación reestructurada), el contrato externo se rompe sin advertencia.
### 33. Describir dos situaciones concretas del modelo donde devolver un DTO sería más adecuado que devolver la entidad completa. Para cada caso indicar qué campos contendría el DTO y por que.

### Caso 1 — Listado de rutas para panel de administración
Al mostrar una tabla de rutas se necesita: nombre, precio, cantidad de compras. Devolver la entidad `Route` completa obligaría a inicializar `purchaseList`, `stops`, `driverList` y `tourGuideList`. Ninguna de esas colecciones es necesaria.

**DTO adecuado:**
```java
RouteSummaryDTO { routeName, purchaseCount, averagePrice }
```

El `purchaseCount` y `averagePrice` se calculan en una sola query JPQL con `count()` y `avg()`, evitando completamente el problema de lazy loading.

### Caso 2 — Perfil público de usuario
`getUserByUsername` devuelve `User`. Esa entidad incluye `password`, `purchaseList` (colección lazy de compras con sus ítems y reviews) y `active` (detalle interno de implementación). Nada de eso debe salir de la capa de servicio hacia un endpoint REST.

**DTO adecuado:**

```
UserProfileDTO { username, fullName, email, phoneNumber }
```

Solo los cuatro campos visibles públicamente. El `password` nunca viaja fuera, y `purchaseList` no se toca, eliminando la posibilidad de una `LazyInitializationException` en el serializador.
### 34. ¿Qué riesgos concretos tiene exponer entidades JPA directamente como respuesta de un servicio o endpoint? Mencionar al menos: ciclos de serialización y acoplamiento entre capas.

### Ciclos de serialización
El modelo tiene relaciones bidireccionales. `Purchase` tiene un `User`, y `User` tiene una `List<Purchase>`. Jackson (el serializador JSON por defecto de Spring) intentará serializar `User → purchaseList → Purchase → user → purchaseList → ...` de forma infinita hasta un `StackOverflowError`. La solución habitual con `@JsonIgnore` o `@JsonManagedReference` es un parche que acopla la entidad de dominio al framework de serialización — exactamente el problema que se quería evitar.

### Acoplamiento entre capas
Una entidad JPA es un contrato vivo con la base de datos: sus campos mapean columnas, sus anotaciones definen estrategias de carga, su grafo de relaciones refleja la estructura del esquema. Cuando esa entidad se expone como respuesta de un servicio o endpoint, **el esquema de BD se convierte en el contrato público**. Cualquier renombrado de campo, cambio de tipo, o reestructuración de relación rompe inmediatamente a todos los consumidores. Con un DTO, ese contrato se puede mantener estable mientras el modelo interno evoluciona libremente.
## 4.2 Implementación
### 35. Definir e implementar un DTO que resuma información de las rutas del modelo: nombre de la ruta, cantidad de compras realizadas y el precio promedio de esas compras. Implementar la consulta correspondiente en RouteRepository.