1. ¿Qué es Spring Data JPA? ¿Qué problema resuelve respecto de usar Hibernate directamente? Describir dos situaciones del proyecto donde Spring Data JPA simplifica código que en la Práctica 1 requería implementación manual.
	**Spring Data JPA** es una parte del ecosistema Spring que busca reducir significativamente el **código repetitivo (boilerplate)** necesario para implementar repositorios de datos.
	**El problema que resuelve:** Cuando usás Hibernate directamente, tenés que gestionar manualmente la `SessionFactory`, abrir y cerrar sesiones, manejar transacciones (si no usás `@Transactional`) y escribir consultas HQL/SQL para operaciones básicas (CRUD). Spring Data JPA elimina la necesidad de escribir la implementación de los repositorios para la mayoría de los casos comunes.
	   - Operaciones CRUD Básicas: para un `save` = `getSession().save(user);` ahora pasa a ser simplemente que la interfaz extienda de `JpaRepository<User, Long>` o `CRUDRepository<User, Long>` y hacer un `save()`
	   - Consultas por nombres de métodos (Query Methods): Si querías buscar un usuario por email, tenías que escribir un String HQL: `"from User where email = :email"` y setear el parámetro. Ahora `findByEmail(String email)`  y lo hace solito
2. Spring Data JPA no es un ORM sino una capa de abstracción sobre el ORM. Explicar la
diferencia: ¿qué hace Spring Data JPA? ¿Qué sigue haciendo Hibernate internamente?
La diferencia esta en que Spring Data JPA usa internamente Hibernate, no inventa nada, solo brinda una capa de abstracción más alta, una interfaz limpia y entendible. A diferencia de Hibernate que es trabajar lo más proximo a la db <-> objetos que se puede.
#### ¿Qué hace Spring Data JPA?
- **Gestión de Repositorios:** Crea automáticamente las implementaciones de tus interfaces de repositorio en tiempo de ejecución.
- **Traducción de Excepciones:** Convierte errores específicos de la base de datos o de Hibernate en excepciones de acceso a datos de Spring (más genéricas y fáciles de manejar).
- **Paginación y Ordenamiento:** Ofrece herramientas sencillas (`Pageable`, `Sort`) que de forma manual en Hibernate requieren cálculos de offset y límites bastante tediosos.
#### ¿Qué sigue haciendo Hibernate internamente?
Aunque vos no se vea, Hibernate sigue siendo el **motor (el obrero)** que hace el trabajo sucio:
- **Mapeo Objeto-Relacional:** Sigue leyendo las anotaciones `@Entity`, `@Table`, `@Id` para saber cómo transformar tus objetos Java en tablas de MySQL.
- **Generación de SQL:** Spring Data JPA genera HQL, pero es **Hibernate** quien traduce ese HQL al SQL nativo que entiende tu base de datos.
- **Manejo del Contexto de Persistencia:** Hibernate sigue gestionando la "Cache de primer nivel", detectando qué objetos cambiaron (_Dirty Checking_) para hacer los `UPDATE` automáticos antes de cerrar la transacción.

3. La siguiente tabla lista tareas relacionadas con la persistencia. Marcar con una X la
columna de la tecnología que resuelve ese problema en la nueva implementación con
Spring Data JPA:

| Tarea                                                    | JDBC | Hibernate | Spring Data JPA |
| -------------------------------------------------------- | ---- | --------- | --------------- |
| Abrir y cerrar la conexión a la base de datos            | X    |           |                 |
| Implementar save(), findById() y deleteById()            |      |           | X               |
| Gestionar el ciclo de vida de las entidades<br>(@Entity) |      | X         |                 |
| Derivar una consulta a partir del nombre del<br>método   |      |           | X               |
| Manejar el pool de conexiones                            | X    |           |                 |
| Propagar transacciones con @Transactional                |      |           | X               |
| Generar la implementación del repositorio en<br>runtime  |      |           | X               |
| Mapear ResultSet a objetos Java                          |      | X         |                 |
| Proveer soporte nativo de paginación<br>(Pageable)       |      |           | X               |
## 1.2 Configuración del proyecto
4. Listar los cambios que deben realizarse en el proyecto de la Práctica 1 para incorporar Spring Data JPA.
   -  `pom.xml` -  dependencias
```xml
<!-- Reemplazar la dependencia de Hibernate standalone por: -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```
- Eliminar `HibernateConfiguration.java` y `AppConfig.java` si solo contenía el bean de `SessionFactory`/`TourServiceImpl`
-  Crear application.properties
- Cambiar clases concretas que trabajan con HibernateRepository que sean interfaces que implementen CrudRepository
- Reemplazar el uso de SessionFactory 
```java
// Actual
sessionFactory.getCurrentSession()
    .createQuery("from Stop s where s.name like :n", Stop.class)
    .setParameter("n", name + "%").list();

// Con Spring Data JPA
@PersistenceContext
private EntityManager entityManager;

entityManager.createQuery("from Stop s where s.name like :n", Stop.class)
    .setParameter("n", name + "%").getResultList();
```
- cambiar import de transaccional de `import jakarta.transaction.Transactional` por `import org.springframework.transaction.annotation.Transactional` en el service (Spring gestiona la transacción).
5. En la Práctica 1 se configuraba Hibernate mediante una clase de configuración Java para obtener la SessionFactory. ¿Cómo se reemplaza esta configuración usando application.properties en Spring Boot?

|Lo que hace `HibernateConfiguration.java`|Equivalente en Spring Boot|
|---|---|
|Crea el `DataSource` (url, user, pass, driver)|`spring.datasource.*` en `application.properties`|
|Construye la `SessionFactory` / `EntityManagerFactory`|Spring Boot lo hace automáticamente al detectar Spring Data JPA en el classpath|
|Configura el dialecto y otras propiedades Hibernate|`spring.jpa.properties.hibernate.*`|
|Controla la creación del esquema|`spring.jpa.hibernate.ddl-auto`|
|Registra el `PlatformTransactionManager`|Spring Boot lo registra automáticamente|
6. Describir las propiedades más relevantes de Spring Data JPA que deben configurarse en application.properties para este proyecto. Incluir al menos: datasource (url, username, password, driver), dialecto de Hibernate, y la propiedad que controla si Hibernate debe crear, actualizar o validar el esquema. ¿Cuál de estos valores conviene usar durante el desarrollo?

```java
# ── DataSource ────────────────────────────────────────────────────────────────
spring.datasource.url=jdbc:mysql://localhost:3306/tours_db
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ── Dialecto de Hibernate ─────────────────────────────────────────────────────
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# ── Creación / validación del esquema ─────────────────────────────────────────
spring.jpa.hibernate.ddl-auto=create-drop

# ── Opcional pero útil en desarrollo ─────────────────────────────────────────
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

```

### Valores posibles de `ddl-auto`

| Valor         | Qué hace                                                                  | Cuándo usarlo                                               |
| ------------- | ------------------------------------------------------------------------- | ----------------------------------------------------------- |
| `create`      | Crea el esquema al arrancar, destruyendo el anterior                      | Tests con datos fresh                                       |
| `create-drop` | Crea al arrancar, **elimina al apagar**                                   | **Desarrollo activo** (garantiza esquema limpio)            |
| `update`      | Agrega columnas/tablas nuevas, no borra nada                              | Desarrollo cuando se quiere conservar datos entre reinicios |
| `validate`    | Solo valida que el esquema coincide con las entidades, sin modificar nada | Producción (junto con Flyway/Liquibase para migraciones)    |
| `none`        | No hace nada                                                              | Producción cuando el esquema se gestiona externamente       |
**Durante el desarrollo conviene `create-drop`**: garantiza que el esquema siempre refleja exactamente las entidades mapeadas y evita errores por columnas huérfanas de cambios anteriores.

## 1.3 La interfaz CrudRepository y la jerarquía de repositorios
### 7. ¿Qué es CrudRepository? ¿De qué interfaz hereda y qué operaciones provee automáticamente?
   
**`CrudRepository`** es una interfaz genérica de Spring Data que provee operaciones de persistencia básicas (CRUD: Create, Read, Update, Delete) sobre una entidad específica.   Hereda directamente de la interfaz **`Repository`** (que es una interfaz marcadora vacía de Spring Data utilizada para escanear y capturar componentes de persistencia).

**Operaciones que provee automáticamente:**
- `save(S entity)` / `saveAll(Iterable<S> entities)`
- `findById(ID id)` / `findAll()` / `findAllById(Iterable<ID> ids)`
- `existsById(ID id)` / `count()`
- `deleteById(ID id)` / `delete(T entity)` / `deleteAll()`
### 8. ¿Que agrega cada nivel de la jerarquía respecto del anterior? Describir brevemente las diferencias entre CrudRepository, PagingAndSortingRepository y JpaRepository, indicando que operaciones o capacidades incorpora cada uno 

Cada nivel de la jerarquía extiende al anterior agregando capacidades específicas para simplificar el desarrollo:

```
Repository (Marcadora)
      ▲
      │
CrudRepository (Operaciones CRUD básicas)
      ▲
      │
PagingAndSortingRepository (Paginación y Ordenamiento)
      ▲
      │
JpaRepository (Operaciones específicas de JPA/Hibernate + Flushing)
```

- **`CrudRepository`:** Ofrece únicamente las operaciones básicas de persistencia que mencionamos en el punto anterior. No sabe nada de páginas ni de tecnologías específicas como JPA.
- **`PagingAndSortingRepository`:** Agrega la capacidad de **paginar y ordenar** colecciones de datos masivas de forma nativa. Incorpora métodos sobrecargados como `findAll(Sort sort)` y `findAll(Pageable pageable)`, evitando tener que calcular offsets manualmente en SQL.
- **`JpaRepository`:** Es la interfaz más completa y está acoplada a la especificación JPA (Hibernate). Agrega métodos específicos para optimizar el rendimiento del ORM, tales como:
    - `flush()`: Fuerza la sincronización inmediata de los cambios en memoria con la base de datos.
    - `saveAndFlush(T entity)`: Guarda e impacta los cambios en el mismo acto.
    - `deleteInBatch(Iterable<T> entities)`: Borra registros emitiendo una sola sentencia SQL masiva, lo cual es mucho más rápido que el borrado uno por uno de `CrudRepository`.
    - Retorna colecciones de tipo `List` en lugar de `Iterable`.
### 9. Crear las interfaces de repositorio para las entidades del modelo extendiendo CrudRepository. Indicar los parámetros de tipo correctos (entidad e ID) para cada una: PurchaseRepository, RouteRepository, UserRepository, ServiceRepository, SupplierRepository y ReviewRepository.

```java
package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.*;

// 1. UserRepository
public interface UserRepository extends CrudRepository<User, Long> { }

// 2. PurchaseRepository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> { }

// 3. RouteRepository
public interface RouteRepository extends CrudRepository<Route, Long> { }

// 4. ServiceRepository
public interface ServiceRepository extends CrudRepository<Service, Long> { }

// 5. SupplierRepository
public interface SupplierRepository extends CrudRepository<Supplier, Long> { }

// 6. ReviewRepository
public interface ReviewRepository extends CrudRepository<Review, Long> { }
```
### 10. ¿Cómo genera Spring Data JPA la implementación concreta de los repositorios en tiempo de ejecución? ¿Qué rol juega el proxy dinámico de Java en este mecanismo?

Spring Data JPA funciona bajo el paradigma de **programación declarativa**. Vos solo definís interfaces, y Spring se encarga del código real en tiempo de ejecución (runtime) usando **Proxies Dinámicos de Java** (`java.lang.reflect.Proxy`).
1. **Escaneo de Componentes:** Al arrancar la aplicación, Spring busca todas las interfaces que extiendan de `Repository` (o sus derivados).
2. **Creación del Proxy:** Instancia un objeto Proxy dinámico que "implementa" esa interfaz en memoria.
3. **Delegación a `SimpleJpaRepository`:** Este proxy intercepta cualquier llamada a los métodos estándar (como `save()` o `findById()`) y redirige la ejecución a una clase concreta interna de Spring llamada `SimpleJpaRepository`, la cual contiene el código real que interactúa con la `EntityManager` de JPA/Hibernate.
4. **Traducción de Query Methods:** Si llamás a un método personalizado (como `findByUsername`), el proxy analiza el nombre del método usando Reflection, construye el query HQL/JPQL correspondiente, le pasa los parámetros y lo ejecuta contra la base de datos.
> Reflection: Spring Data JPA funciona bajo el paradigma de **programación declarativa**. Vos solo definís interfaces, y Spring se encarga del código real en tiempo de ejecución (runtime) usando **Proxies Dinámicos de Java** (`java.lang.reflect.Proxy`).

5. **Escaneo de Componentes:** Al arrancar la aplicación, Spring busca todas las interfaces que extiendan de `Repository` (o sus derivados).
6. **Creación del Proxy:** Instancia un objeto Proxy dinámico que "implementa" esa interfaz en memoria.
7. **Delegación a `SimpleJpaRepository`:** Este proxy intercepta cualquier llamada a los métodos estándar (como `save()` o `findById()`) y redirige la ejecución a una clase concreta interna de Spring llamada `SimpleJpaRepository`, la cual contiene el código real que interactúa con la `EntityManager` de JPA/Hibernate.
8. **Traducción de Query Methods:** Si llamás a un método personalizado (como `findByUsername`), el proxy analiza el nombre del método usando Reflection, construye el query HQL/JPQL correspondiente, le pasa los parámetros y lo ejecuta contra la base de datos.
### 11. ¿Que diferencia hay entre save() en Spring Data JPA y session.save() / session.merge() en Hibernate directo? ¿Cómo decide Spring Data JPA si debe hacer un INSERT o un UPDATE?
En Hibernate directo tenías métodos separados según el estado de la entidad: `session.save()` (o `persist()`) para objetos nuevos (_Transient_), y `session.merge()` (o `update()`) para objetos que ya venían con un ID pero estaban desenganchados de la sesión (_Detached_).
El método **`save()` de Spring Data JPA unifica ambos comportamientos** en una sola interfaz limpia. Por detrás, delega la decisión a la clase `SimpleJpaRepository`.
#### ¿Cómo decide Spring Data JPA si debe hacer un INSERT o un UPDATE?
Para determinar si una entidad es nueva o existente, Spring Data JPA analiza su clave primaria (`@Id`):
- **Hace un `INSERT` (vía `entityManager.persist()`):** Si el atributo anotado con `@Id` es **`null`** (o vale `0` en tipos numéricos primitivos). Como no tiene ID, Spring asume que es una entidad completamente nueva y la inserta en la base de datos.
- **Hace un `UPDATE` (vía `entityManager.merge()`):** Si el atributo `@Id` **ya tiene un valor asignado**. En este caso, Spring asume que el registro ya existe en la base de datos. Ejecuta un `merge()` que primero busca el registro en la base de datos (o en la caché), actualiza los campos modificados en memoria y luego impacta el cambio con un `UPDATE` SQL.
