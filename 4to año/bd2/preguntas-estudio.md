# Preguntas de Estudio — BD2

> Basadas en las prácticas 1-4 de la materia.
> 30 preguntas: multiple choice y desarrollo.

---

## Módulo I — JPA / Hibernate (Práctica 1)

### 1. (Multiple Choice)
¿Cuál de los siguientes NO es un problema que resuelve un ORM respecto a JDBC puro?

- a) Boilerplate code en el mapeo de ResultSet a objetos
- b) Manejo de relaciones y lazy loading
- c) Generación automática de índices en la base de datos
- d) Dependencia del dialecto SQL del motor

---

### 2. (Desarrollo)
Describí los cuatro estados de una entidad en Hibernate (transient, managed, detached, removed) y las operaciones que disparan cada transición.
¿Qué diferencia hay entre `session.save()` y `session.persist()`?

---

### 3. (Multiple Choice)
¿Qué diseño pattern implementa SessionFactory y por qué se crea una única instancia?

- a) Singleton y Factory Method — porque es liviana y se crea por cada request
- b) Singleton y Factory Method — porque es pesada de construir, inmutable y thread-safe
- c) Builder y Prototype — porque necesita clonarse para cada operación
- d) Abstract Factory — porque crea Sessions de distinto tipo

---

### 4. (Multiple Choice)
¿Cuál es la principal diferencia entre `openSession()` y `getCurrentSession()`?

- a) `openSession()` requiere transacción; `getCurrentSession()` no
- b) `openSession()` crea una nueva Session cada vez; `getCurrentSession()` se vincula al contexto y Hibernate la cierra automáticamente
- c) `openSession()` solo funciona con JPA; `getCurrentSession()` solo con Hibernate nativo
- d) Son equivalentes, solo cambia el nombre

---

### 5. (Desarrollo)
¿Qué es el *impedance mismatch* entre el modelo relacional y el OO?
Mencioná al menos 3 manifestaciones concretas.

---

### 6. (Multiple Choice)
¿Cuál es el valor por defecto de `FetchType` para `@OneToMany` y `@ManyToOne` respectivamente?

- a) LAZY / LAZY
- b) EAGER / EAGER
- c) LAZY / EAGER
- d) EAGER / LAZY

---

### 7. (Desarrollo)
Explicá qué es la `LazyInitializationException`, describí un escenario concreto en el modelo de tours donde ocurre, y mencioná al menos 2 formas de resolverla sin cambiar a EAGER.

---

### 8. (Multiple Choice)
¿Qué ocurre si definís `CascadeType.REMOVE` en un `@ManyToMany` entre `Route` y `DriverUser` y eliminás una ruta?

- a) Se elimina solo el registro en la tabla intermedia
- b) Se elimina la ruta y el DriverUser asociado de la BD, perdiendo la cuenta de usuario
- c) Se lanza una excepción de integridad referencial
- d) No ocurre nada porque REMOVE no aplica a ManyToMany

---

### 9. (Multiple Choice)
Diferencia entre `cascade = REMOVE` y `orphanRemoval = true`:

- a) Son equivalentes, hacen exactamente lo mismo
- b) REMOVE elimina hijos al eliminar el padre; orphanRemoval elimina hijos al sacarlos de la colección aunque el padre no se elimine
- c) orphanRemoval solo funciona con `@OneToOne`
- d) REMOVE solo aplica a colecciones; orphanRemoval a atributos individuales

---

### 10. (Desarrollo)
Describí las tres estrategias de mapeo de herencia (SINGLE_TABLE, JOINED, TABLE_PER_CLASS).
¿Cuál es más robusta ante la futura incorporación de nuevas subclases y por qué?

---

## Módulo II — Spring Data JPA (Práctica 2)

### 11. (Multiple Choice)
¿Spring Data JPA es un ORM?

- a) Sí, reemplaza completamente a Hibernate
- b) No, es una capa de abstracción sobre el ORM. Hibernate sigue haciendo el mapeo y generación de SQL
- c) Sí, pero solo funciona con bases relacionales
- d) No, solo sirve para generar consultas nativas

---

### 12. (Desarrollo)
¿Cómo genera Spring Data JPA la implementación de los repositorios en tiempo de ejecución?
¿Qué rol juega el *dynamic proxy*?

---

### 13. (Multiple Choice)
`save()` de Spring Data JPA ¿cómo decide si hace INSERT o UPDATE?

- a) Siempre hace UPDATE primero y si no afecta filas, hace INSERT
- b) Si el `@Id` es null → consulta la BD; si tiene valor → UPDATE
- c) Si el `@Id` es null → INSERT; si tiene valor → UPDATE
- d) Depende de la anotación `@GeneratedValue`

---

### 14. (Desarrollo)
Explicá cómo funcionan los Query Methods de Spring Data.
Dado el método `findByPriceLessThanOrderByNameAsc`, ¿qué consulta JPQL genera aproximadamente?
Mencioná al menos 2 limitaciones de los Query Methods.

---

### 15. (Multiple Choice)
¿Cuál es la diferencia entre `Page<T>` y `Slice<T>`?

- a) Page y Slice son equivalentes
- b) Page ejecuta un COUNT extra para saber el total; Slice solo sabe si hay una página siguiente
- c) Slice es para MongoDB; Page para JPA
- d) Page contiene datos paginados; Slice no contiene datos

---

### 16. (Desarrollo)
¿Qué es un DTO?
Mencioná al menos 2 razones por las que no conviene devolver entidades JPA directamente desde la capa de acceso a datos.

---

### 17. (Multiple Choice)
Por defecto, Spring hace rollback automático cuando el servicio lanza:

- a) Cualquier excepción, incluyendo checked exceptions
- b) Solo RuntimeException y Error
- c) Solo excepciones de tipo SQLException
- d) Ninguna, hay que configurarlo manualmente siempre

---

### 18. (Desarrollo)
¿Qué es el soft delete? ¿Cómo se implementa en JPA con `@SQLDelete` y `@Where`?
¿Qué problema concreto resuelve en el modelo de tours?

---

### 19. (Multiple Choice)
¿Qué optimización activa `@Transactional(readOnly = true)`?

- a) Cambia a FetchType.LAZY en todas las relaciones
- b) Desactiva dirty checking, establece FlushMode.MANUAL y pasa hints al driver JDBC
- c) Evita la creación de la Session
- d) Hace que todas las consultas usen índices

---

### 20. (Multiple Choice)
¿En qué capa debería manejarse la transacción según la práctica?

- a) En el repositorio, porque es quien accede a la BD
- b) En la capa de servicio, porque una operación de negocio puede abarcar múltiples accesos
- c) En el controlador, porque recibe los requests HTTP
- d) En la entidad, porque conoce sus propios datos

---

## Módulo III — MongoDB (Práctica 3)

### 21. (Multiple Choice)
¿Cuál de estos conceptos relacionales NO existe nativamente en MongoDB?

- a) Base de datos
- b) Documento
- c) Clave foránea con integridad referencial
- d) Colección

---

### 22. (Desarrollo)
Explicá la diferencia entre modelos embebidos y referencias en MongoDB.
Dado el modelo de tours, ¿cómo modelarías la relación Purchase → ItemService y por qué?

---

### 23. (Multiple Choice)
¿Cuál es el tamaño máximo de un documento en MongoDB?

- a) 4 MB
- b) 16 MB
- c) 64 MB
- d) No hay límite

---

### 24. (Multiple Choice)
¿Qué stage del aggregation pipeline se usa para unir documentos de distintas colecciones?

- a) `$match`
- b) `$group`
- c) `$lookup`
- d) `$project`

---

### 25. (Desarrollo)
¿Qué son los índices en MongoDB? Mencioná al menos 3 tipos de índices que soporta.

---

## Módulo IV — Redis (Práctica 4)

### 26. (Multiple Choice)
¿Dónde almacena Redis los datos por defecto?

- a) En disco exclusivamente
- b) En RAM (volátil) con persistencia opcional mediante RDB o AOF
- c) En una combinación de SSD y RAM administrada automáticamente
- d) En memoria flash

---

### 27. (Multiple Choice)
¿Cuál de los siguientes NO es un tipo de dato nativo de Redis?

- a) Strings
- b) Lists
- c) Documents (BSON)
- d) Sorted Sets

---

### 28. (Desarrollo)
Compará los mecanismos de persistencia RDB y AOF en Redis.
¿Cuál prioriza velocidad de recuperación? ¿Cuál minimiza la pérdida de datos?

---

### 29. (Multiple Choice)
¿Qué devuelve `TTL` en Redis cuando la clave no tiene expiración?

- a) -2
- b) -1
- c) 0
- d) nil

---

### 30. (Desarrollo)
¿En qué casos conviene usar Redis vs una base relacional?
Mencioná al menos 2 escenarios donde Redis es la mejor opción y 2 donde NO conviene usarlo.

---

---

# Preguntas de Estudio — BD2 (Segunda Parte)

> 30 preguntas adicionales con énfasis en código y aplicación práctica.

---

## Módulo I — JPA / Hibernate (Práctica 1)

### 31. (Código)
Dado el siguiente mapeo, ¿qué anotaciones faltan? Completalas.

```java
public class Purchase {

    private Long id;
    private LocalDate date;
    private User user;
    private List<ItemService> items;
}
```

> Se espera: `@Entity`, `@Id`, `@GeneratedValue`, `@ManyToOne User`, `@OneToMany List<ItemService>`, `@JoinColumn`, fechas con `@Temporal(TemporalType.DATE)` o equivalente.

---

### 32. (Código)
Escribí el mapeo completo de `Purchase → ItemService` como composición (one-to-many), incluyendo cascade y orphanRemoval. Indicá qué columna se genera en la BD.

---

### 33. (Multiple Choice)
¿Qué anotaciones son **mínimas e indispensables** para que JPA trate una clase como entidad persistente?

- a) `@Entity`, `@Table`, `@Column`, `@Id`
- b) `@Entity` e `@Id`
- c) `@Entity`, `@Id`, `@GeneratedValue`
- d) `@Entity` solamente

---

### 34. (Código)
Dada la jerarquía `User ← DriverUser` con SINGLE_TABLE:

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public class User { ... }
```

Escribí la clase `DriverUser` con las anotaciones necesarias. ¿Qué DDL genera aproximadamente?

---

### 35. (Desarrollo)
¿Qué es `mappedBy`? ¿En qué lado de una relación bidireccional se coloca? ¿Qué pasa si se omite en ambos lados?

---

### 36. (Código)
Completá el siguiente código de un repository con Hibernate nativo (P1):

```java
public class PurchaseRepository {

    public Purchase findById(Long id) {
        // obtener sesión y buscar Purchase por id
    }
}
```

Usá `getCurrentSession()`. Explicá quién abre y cierra la sesión.

---

### 37. (Multiple Choice)
Dado:

```java
@ManyToMany
@JoinTable(name = "route_driver",
    joinColumns = @JoinColumn(name = "route_id"),
    inverseJoinColumns = @JoinColumn(name = "driver_id"))
private List<DriverUser> drivers;
```

Si se agrega `cascade = CascadeType.REMOVE`, al eliminar una Route:

- a) Se borra solo la fila en `route_driver`
- b) Se borra la fila en `route_driver` y el `DriverUser` completo
- c) Se lanza excepción porque DriverUser tiene compras asociadas
- d) Hibernate ignora `REMOVE` en `@ManyToMany`

---

### 38. (Código)
Escribí una consulta HQL que devuelva todas las `Purchase` de un usuario cuyo username se pasa como parámetro.

```java
// Completar:
String hql = " ... ";
Query<Purchase> query = session.createQuery(hql, Purchase.class);
query.setParameter("username", username);
```

---

### 39. (Desarrollo)
¿Qué ventaja tiene la estrategia de generación de IDs `SEQUENCE` sobre `IDENTITY` para inserciones masivas? ¿Qué rol juega `allocationSize`?

---

### 40. (Multiple Choice)
En una relación `@OneToMany` unidireccional **sin** `mappedBy`, JPA genera por defecto:

- a) Una columna FK en la tabla hija
- b) Una tabla intermedia (join table)
- c) No genera nada, lanza excepción
- d) Una columna adicional en la tabla padre

---

## Módulo II — Spring Data JPA (Práctica 2)

### 41. (Código)
Convertí el siguiente repository de P1 a Spring Data JPA (P2):

```java
// P1
public class UserRepository {
    public User findByEmail(String email) { ... }
    public boolean existsByUsername(String username) { ... }
}
```

Escribí la interfaz equivalente.

---

### 42. (Código)
Escribí un `@Query` JPQL en `PurchaseRepository` que devuelva el total gastado por un usuario (username como parámetro nombrado). Usá `SUM`.

---

### 43. (Multiple Choice)
Dado:

```java
public interface RouteRepository extends CrudRepository<Route, Long> {
    List<Route> findByStopsName(String stopName);
}
```

¿Qué consulta JPQL genera Spring Data JPA?

- a) `SELECT r FROM Route r WHERE r.stops.name = ?1`
- b) `SELECT r FROM Route r JOIN r.stops s WHERE s.name = ?1`
- c) `SELECT r FROM stops WHERE name = ?1`
- d) `SELECT * FROM route JOIN stop WHERE stop.name = ?1`

---

### 44. (Código)
Dado el método de servicio:

```java
public Page<Purchase> getPurchasesByUser(String username, int page, int size) {
    // implementar usando Pageable
}
```

Implementá el cuerpo usando `PurchaseRepository`. Asumí que existe `findByUserUsername`.

---

### 45. (Desarrollo)
¿Qué diferencia hay entre una proyección con DTO y usar `Object[]` en una consulta JPQL? ¿Cuándo conviene usar `new com.example.dto.RouteSummaryDTO(r.name, ...)` dentro del JPQL?

---

### 46. (Código)
Escribí la clase `UserProfileDTO` con los campos `username`, `fullName`, `email`, `phoneNumber`. Luego escribí el `@Query` que devuelva una lista de estos DTOs.

---

### 47. (Multiple Choice)
Para implementar soft delete en `User`, la combinación correcta es:

- a) `@SQLDelete` + `@Filter`
- b) `@SQLDelete` + `@Where(clause = "active = true")`
- c) `@SoftDelete`
- d) `@SQLDelete` + `@Formula("false")`

---

### 48. (Código)
Implementá soft delete en la entidad `User`:

- Agregar campo `active`
- Anotación `@SQLDelete`
- Anotación `@Where`

---

### 49. (Desarrollo)
¿Cuándo usarías `nativeQuery = true` en lugar de JPQL? Describí un caso concreto del modelo de tours.

---

### 50. (Multiple Choice)
¿Cuál es la diferencia entre `CrudRepository`, `PagingAndSortingRepository` y `JpaRepository`?

- a) Son exactamente iguales, solo cambia el nombre
- b) Cada uno agrega funcionalidad: `CrudRepository` → CRUD básico, `PagingAndSortingRepository` → paginación/sort, `JpaRepository` → todo lo anterior + flush/deleteInBatch
- c) `JpaRepository` es solo para JPA nativo, no para Spring
- d) `PagingAndSortingRepository` no incluye operaciones CRUD

---

## Módulo III — MongoDB (Práctica 3)

### 51. (Código)
Dado el siguiente documento:

```javascript
{
  "_id": ObjectId("..."),
  "nombre": "Tour San Telmo",
  "precio": 2500,
  "paradas": ["Plaza de Mayo", "San Telmo", "La Boca"],
  "totalKm": 15
}
```

Escribí el comando para:
- Actualizar `totalKm` a 18
- Agregar la parada "Puerto Madero" al array `paradas`

---

### 52. (Código)
Escribí una consulta que devuelva todos los recorridos con precio mayor a 2000 y que tengan "San Telmo" como parada.

---

### 53. (Multiple Choice)
¿Cuál es la diferencia clave entre `$push` y `$addToSet` en MongoDB?

- a) `$push` agrega siempre; `$addToSet` solo si el valor no existe ya en el array
- b) `$push` agrega al principio; `$addToSet` al final
- c) `$push` funciona solo en strings; `$addToSet` en cualquier tipo
- d) Son equivalentes

---

### 54. (Código)
Dada la colección `routes`, escribí un aggregation pipeline que:
1. Filtre rutas con más de 3 paradas
2. Desenrolle el array de paradas
3. Agrupe por código de ruta contando paradas
4. Ordene de mayor a menor cantidad de paradas

---

### 55. (Desarrollo)
¿Qué es el operador `$lookup` y cómo se relaciona con el concepto de "referencias" en MongoDB? Escribí un ejemplo.

---

### 56. (Multiple Choice)
¿Con qué operador se limitan los campos que devuelve una consulta en MongoDB?

- a) `$match`
- b) `$filter`
- c) `$project`
- d) `$limit`

---

### 57. (Código)
Dada una colección `stops` con documentos `{ code: "S1", name: "San Telmo", ... }` y una colección `routes` con `{ stops: ["S1", "S2"], ... }`, escribí un `$lookup` que traiga los datos completos de cada parada en cada ruta.

---

## Módulo IV — Redis (Práctica 4)

### 58. (Código)
Dado el siguiente escenario: queremos mantener un contador de visitas a cada ruta turística.

```
clave: "ruta:123:visitas"
```

Escribí los comandos Redis para:
- Incrementar el contador en 1
- Obtener el valor actual
- Establecer que expire en 1 hora

---

### 59. (Código)
Tenemos un sorted set con líderes de viajes:

```
ZADD leaders 150 "Carlos" 200 "Ana" 80 "Pedro" 300 "Lucia"
```

Escribí los comandos para:
- Obtener el top 3 (mayor puntaje)
- Obtener el puntaje de "Ana"
- Incrementar el puntaje de "Pedro" en 50

---

### 60. (Desarrollo)
Explicá en qué se diferencian `MULTI/EXEC` en Redis de una transacción ACID tradicional. ¿Redis soporta rollback? ¿Por qué?

---
