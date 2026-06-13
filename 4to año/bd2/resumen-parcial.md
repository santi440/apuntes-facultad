# Resumen para el Parcial — BD2

---

## 1. ORM / JPA / Hibernate (Práctica 1)

### Conceptos clave
- **ORM**: puente entre OO y relacional. Resuelve: boilerplate JDBC, relaciones/lazy loading, portabilidad de dialecto SQL.
- **JPA**: especificación (interfaces + anotaciones). **Hibernate**: implementación concreta.
- **SessionFactory**: Singleton + Factory. Pesada, una sola instancia. Crea Sessions.
- **Session** (`openSession` vs `getCurrentSession`):
  - `openSession()`: nueva conexión cada vez, hay que cerrarla manualmente.
  - `getCurrentSession()`: vinculada al contexto (thread), Hibernate la cierra. Requiere configurar `hibernate.current_session_context_class`. Mejor para repositorios.

### Estados de una entidad
```
Transient → persist() → Managed → close()/clear() → Detached
Managed → remove() → Removed → commit() → Transient (o GC)
```
- **Transient**: sin ID, sin sesión.
- **Managed**: tiene ID, lo trackea la sesión, dirty checking activo.
- **Detached**: tiene ID, sesión cerrada.
- **Removed**: marcado para borrar, desaparece en el flush.

### Métodos de Session
| Método | JPA/Hibernate | Qué hace |
|---|---|---|
| `persist()` | JPA | void, transient → managed |
| `save()` | Hibernate | devuelve ID, transient → managed |
| `merge()` | JPA | detached → managed (copia gestionada) |
| `saveOrUpdate()` | Hibernate | decide save o update según tenga ID o no |

### FetchType
- `@OneToMany` / `@ManyToMany` → **LAZY** por defecto
- `@ManyToOne` / `@OneToOne` → **EAGER** por defecto
- **LazyInitializationException**: al acceder a una relación lazy con sesión cerrada.
  Soluciones: Fetch JOIN en JPQL, `@EntityGraph`, `Hibernate.initialize()`, mantener sesión abierta.

### Cascade
| Tipo | Propaga |
|---|---|
| PERSIST | Al persistir el padre, persiste el hijo |
| MERGE | Al hacer merge del padre, mergea el hijo |
| REMOVE | Al eliminar el padre, elimina el hijo |
| REFRESH / DETACH | Refresca / desvincula en cascada |
| ALL | Todo lo anterior |

- **Default**: sin cascade.
- **`orphanRemoval = true`**: elimina hijos que se sacan de la colección (aunque el padre no se elimine).
- **REMOVE en `@ManyToMany` es peligroso**: borraría la entidad completa, no solo el registro en la join table.

### Anotaciones mínimas
`@Entity` + `@Id` (opcional: `@Table`, `@Column`, `@GeneratedValue`)

### Estrategias de ID
| Estrategia | Cómo funciona | Rendimiento en lotes |
|---|---|---|
| IDENTITY | Auto-increment del motor | ❌ Rompe batch inserts |
| SEQUENCE | Secuencia BD, asigna IDs antes | ✅ Mejor performance |
| TABLE | Simula secuencia con tabla | ❌ Lenta, último recurso |

### Herencia — 3 estrategias
| Estrategia | Tablas | Discriminador | Consulta polimórfica |
|---|---|---|---|
| SINGLE_TABLE | 1 tabla con todos los atributos | Sí | SELECT simple |
| JOINED | 1 por clase (padre + hijas), FK | No obligatorio | JOIN entre tablas |
| TABLE_PER_CLASS | 1 tabla completa por clase concreta | No | UNION entre tablas |

- **SINGLE_TABLE**: más rápida, columnas NULL, no permite NOT NULL en subclases.
- **JOINED**: normalizada, más lenta (JOINs), más robusta para cambios futuros.
- **TABLE_PER_CLASS**: representa mejor OO, pero requiere UNIONs.

### InheritanceType: ¿cuál elegir?
```
SINGLE_TABLE  → si la jerarquía no va a crecer mucho, mejor performance
JOINED        → si se esperan nuevas subclases, más escalable
TABLE_PER_CLASS → casi nunca recomendado (UNIONs costosas)
```

### DAO vs Repository
- **DAO**: abstracción de infraestructura, uno por tabla, métodos `insert/update/delete`.
- **Repository**: abstracción de dominio, por aggregate root, método `add/remove`, parece una colección en memoria.
- El **Repository** interactúa con la **Session** (persistence context / 1er nivel de caché).

### Transacciones
- **Service layer** (no Repository): porque una operación de negocio abarca múltiples accesos a BD.
- Sin transacción activa → `TransactionRequiredException` o auto-commit (peligroso).

### HQL / JPQL / SQL nativo
- **HQL/JPQL**: hablan de entidades, atributos, entienden herencia, navegación por `.`, colecciones (`size()`).
- **SQL nativo**: habla de tablas/columnas. Se usa para funciones del motor, consultas complejas (CTEs, hints), bulk operations.

---

## 2. Spring Data JPA (Práctica 2)

### ¿Qué es?
Capa de abstracción sobre el ORM. No es un ORM. Hibernate sigue haciendo el trabajo pesado.

### Jerarquía de interfaces
```
Repository (marcador)
  └── CrudRepository (CRUD: save, findById, findAll, deleteById, count, etc.)
        └── PagingAndSortingRepository (+ findAll(Pageable), findAll(Sort))
              └── JpaRepository (+ flush, saveAndFlush, deleteInBatch)
```

### Dynamic Proxy
Spring genera la implementación en runtime vía `java.lang.reflect.Proxy`. Intercepta llamadas y delega a `SimpleJpaRepository`.

### save() en Spring Data JPA
- Si `@Id` es **null** → **INSERT**
- Si `@Id` tiene **valor** → **UPDATE**
- Unifica `session.save()` y `session.merge()` de Hibernate.

### Query Methods
- Se generan por **reflection** sobre el nombre del método.
- Keywords: `findBy`, `existsBy`, `countBy`, `deleteBy` + atributos + `And`/`Or` + operadores (`LessThan`, `GreaterThan`, `Between`, `Like`, `Containing`, `In`, `True/False`).
- **Limitaciones**: nombres largos con muchas condiciones, sin GROUP BY/HAVING, sin subconsultas.

Alternativas: `@Query` con JPQL, `nativeQuery = true`, Criteria API.

### @Query
```java
@Query("SELECT p FROM Purchase p WHERE p.user.username = :username")
List<Purchase> findByUsername(@Param("username") String username);
```
- Por defecto JPQL.
- `nativeQuery = true` → SQL nativo.

### Paginación
```java
Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
Page<Purchase> page = repo.findByUserUsername(username, pageable);
```
- `Page<T>`: sabe el total (ejecuta COUNT extra).
- `Slice<T>`: solo sabe si hay más páginas (sin COUNT, para infinite scroll).

### @Transactional
- `readOnly = true`: desactiva dirty checking, FlushMode.MANUAL, hints al driver JDBC. En métodos de solo lectura.
- Rollback automático: solo para `RuntimeException` y `Error`. Las **checked exceptions** no hacen rollback.
- Configurable con `rollbackFor = MiException.class`.

### DTO (Data Transfer Object)
- **No exponer entidades JPA** porque:
  1. LazyInitializationException al serializar.
  2. Datos de más (contraseñas, relaciones enteras).
  3. Acoplamiento DB ↔ API (rompe encapsulamiento).
- DTO: objeto plano con solo los campos necesarios para la capa de presentación.

### Soft Delete
```java
@SQLDelete(sql = "UPDATE users SET active = false WHERE id = ?")
@Where(clause = "active = true")
public class User {
    private boolean active = true;
}
```
- Resuelve: no perder compras históricas al "eliminar" un usuario.
- Preserva integridad referencial, permite reversión.

---

## 3. MongoDB (Práctica 3)

### Conceptos vs RDBMS
| RDBMS | MongoDB |
|---|---|
| Base de datos | Base de datos |
| Tabla | Colección |
| Fila | Documento (BSON) |
| Columna | Campo |
| FOREIGN KEY | ❌ No existe, responsabilidad de la app |

### Documentos embebidos vs Referencias
| Embebido | Referencia |
|---|---|
| Datos dentro del mismo doc | Id/Código que apunta a otro doc |
| Lecturas rápidas (1 sola consulta) | Requiere `$lookup` (unión) |
| Atómico | No atómico entre colecciones |
| Límite 16 MB | Resuelve límite de tamaño |
| Arrays desbordables (unbounded) | Útil para listas grandes |

**Regla práctica**: 1-a-few → embebido, 1-a-many → referencia, 1-to-millions → referencia padre.

### Operaciones básicas
```javascript
db.routes.insertOne({ nombre: "San Telmo", precio: 2500, paradas: [...] })
db.routes.find({ precio: { $gt: 2000 } })
db.routes.updateOne({ _id: ... }, { $set: { totalKm: 18 } })
db.routes.deleteOne({ nombre: "San Telmo" })
```

### Operadores de array
- `$push`: agrega siempre al array.
- `$addToSet`: agrega solo si no existe.
- `$pull`: elimina del array.

### Aggregation Pipeline
```javascript
db.routes.aggregate([
    { $match: { precio: { $gte: 2000 } } },
    { $unwind: "$paradas" },
    { $group: { _id: "$_id", cantParadas: { $sum: 1 } } },
    { $sort: { cantParadas: -1 } },
    { $limit: 5 }
])
```
Stages comunes: `$match`, `$project`, `$group`, `$sort`, `$limit`, `$unwind`, `$lookup`, `$addFields`, `$out`.

### $lookup
```javascript
db.routes.aggregate([
    { $lookup: {
        from: "stops",
        localField: "codigo",
        foreignField: "routeCode",
        as: "stopsData"
    }}
])
```

### Índices
Single Field, Compound, Multikey (arrays), Geospatial (2d/2dsphere), Text, Hashed.

### Schema Validation
```javascript
db.createCollection("routes", {
    validator: {
        $jsonSchema: {
            required: ["nombre", "precio"],
            properties: {
                nombre: { bsonType: "string" },
                precio: { bsonType: "number", minimum: 0 }
            }
        }
    }
})
```

---

## 4. Redis (Práctica 4)

### ¿Qué es?
Base de datos clave-valor en **memoria RAM**. Velocidad microsegundos. Volátil con persistencia opcional.

### Tipos de datos nativos
| Tipo | Ejemplo |
|---|---|
| String | `SET nombre "Juan"` |
| List | `LPUSH cola "tarea1"` |
| Set | `SADD tags "historia"` |
| Sorted Set | `ZADD ranking 100 "Ana"` |
| Hash | `HSET user:1 name "Juan" age 30` |
| Streams | Mensajería |

### Persistencia
| RDB (snapshot) | AOF (Append Only File) |
|---|---|
| Guarda dump completo a intervalos | Guarda cada comando de escritura |
| Recuperación rápida | Recuperación lenta (replay) |
| Posible pérdida de datos | Mínima pérdida de datos |
| Archivos más compactos | Archivos más grandes |

### TTL (Time To Live)
```bash
SET clave "valor" EX 3600
EXPIRE clave 3600
TTL clave   # -2: no existe, -1: existe sin expiración, >0: segundos restantes
```

### Transacciones
```bash
MULTI
SET clave1 "a"
SET clave2 "b"
EXEC
```
- No tienen **rollback** (si un comando falla, los otros se ejecutan).
- Perfecta **aislación** (Redis es single-threaded).
- `WATCH` para optimistic locking.

### Casos de uso
✅ **Cuándo usar Redis**: caching, session store, rate limiting, leaderboards (Sorted Sets), colas de mensajes (Lists), contadores en tiempo real (INCR).
❌ **Cuándo NO**: datos altamente relacionales, datos que exceden RAM, sistemas financieros que requieren rollback, necesidad de consultas complejas.

### Comandos que tenés que conocer
- Strings: `SET`, `GET`, `INCR`, `DECR`, `APPEND`, `STRLEN`
- Lists: `LPUSH`, `RPUSH`, `LPOP`, `RPOP`, `LRANGE`, `LLEN`, `LTRIM`
- Sets: `SADD`, `SMEMBERS`, `SREM`, `SINTER`, `SUNION`, `SCARD`
- Sorted Sets: `ZADD`, `ZRANGE`, `ZREVRANGE`, `ZINCRBY`, `ZSCORE`, `ZRANK`, `ZCOUNT`
- Hashes: `HSET`, `HGET`, `HGETALL`, `HDEL`, `HLEN`, `HKEYS`
- Geo: `GEOADD`, `GEOPOS`, `GEODIST`, `GEORADIUS`
- Generales: `KEYS`, `EXISTS`, `DEL`, `TYPE`, `RENAME`, `TTL`, `EXPIRE`, `FLUSHDB`

---

## Posibles preguntas de parcial

### Típicas de P1 (JPA/Hibernate)
1. Estados de una entidad + transiciones. (**fija**)
2. `openSession` vs `getCurrentSession`.
3. FetchType: defaults, problemas de performance.
4. LazyInitializationException: causas y soluciones.
5. Cascade vs orphanRemoval. REMOVE en `@ManyToMany`.
6. Diferencias `save`/`persist`/`merge`/`saveOrUpdate`.
7. Estrategias de herencia: cuándo usar cada una.
8. `mappedBy`: qué es, dónde va, qué pasa si se omite.
9. HQL vs SQL nativo.
10. Transacciones: ¿en qué capa? ¿por qué?

### Típicas de P2 (Spring Data JPA)
11. ¿Spring Data JPA es un ORM?
12. Jerarquía de interfaces Repository.
13. ¿Cómo implementa los repositorios en runtime? (dynamic proxy)
14. Query Methods: cómo funcionan, limitaciones.
15. `save()`: ¿INSERT o UPDATE? ¿cómo decide?
16. `@Query` con JPQL vs `nativeQuery = true`.
17. Paginación: `Page<T>` vs `Slice<T>`.
18. `@Transactional(readOnly = true)`: qué optimiza.
19. Rollback automático: qué excepciones lo disparan.
20. DTO: por qué no devolver entidades JPA.
21. Soft delete con `@SQLDelete` + `@Where`.

### Típicas de P3 (MongoDB)
22. Equivalencias RDBMS → MongoDB.
23. Embebido vs Referencia: cuándo cada uno.
24. Operadores: `$push` vs `$addToSet`.
25. Aggregation Pipeline: stages principales.
26. `$lookup`: cómo funciona.
27. Índices: tipos.
28. `$expr` + `$size`: filtrar por tamaño de array.

### Típicas de P4 (Redis)
29. Redis: en RAM, persistencia RDB vs AOF.
30. Tipos de datos nativos.
31. Transacciones: MULTI/EXEC, sin rollback.
32. TTL: valores posibles.
33. Sorted Sets: cuándo usarlos.
34. Casos de uso (caching, sesiones, leaderboards).

### Posible ejercicio integrador
> "Dado el modelo de tours (Purchase, User, Route, ItemService, etc.), modelá las relaciones en JPA con sus fetch types y cascade apropiados. Luego implementá un Query Method en Spring Data JPA que devuelva las rutas con más de X paradas ordenadas por precio. Finalmente, mostrá cómo modelarías la misma relación en MongoDB (embebido o referencia) y en Redis."

---

> **Tips**: 
> - Saber dibujar el diagrama de estados de entidades.
> - Saber escribir HQL/JPQL con `JOIN FETCH`, `GROUP BY`, `ORDER BY`.
> - Saber escribir aggregation pipeline de MongoDB en papel.
> - Saber los comandos Redis de memoria (son cortos).
