![[Pasted image 20260513164655.png]]
![[Pasted image 20260513164701.png]]
![[Pasted image 20260513164724.png]]
![[Pasted image 20260513164729.png]]
Spring Data JPA es una capa de abstracción que simplifica el acceso a datos usando repositorios, evitando tener que describir manualmente muchas operaciones repetitivas.
Resuelve el tener que abrir/cerrar sesiones, escribir save, delete o muchas consultas simples a mano
Dos situaciones donde simplifica serían
1. Evitar código manual de save, get, delete
2. Tener que declarar métodos manualmente y sus queries
![[Pasted image 20260513164734.png]]
Spring Data JPA es una capa de abstracción sobre JPA/Hibernate que se encarga de simplificar el acceso a datos mediante repositorios, query methods, paginación y generación automática de implementaciones de métodos
Hibernate sigue siendo quien hace internamente el trabajo de ORM
Spring Data JPA decide qué operación de repositorio ejecutar. Hibernate ejecuta realmente el mapeo y la persistencia 

![[Pasted image 20260513164740.png]]
![[Pasted image 20260513164746.png]]

![[Pasted image 20260513164754.png]]
![[Pasted image 20260513164800.png]]
- Mantener el mapeo JPA/Hibernate de entidades, relaciones, herencia, cascadas y tipos de fetch
- Reemplazar los repositorios manuales que usaban SessionFactory y getCurrentSession por interfaces Spring Data JPA
- Crear un repositorio por entidad extendiendo CrudRepository
- Eliminar la implementación manual de operaciones básicas
- Reemplazar las consultas HQL por query methods o métodos con @Query
- Reemplazar la configuración manual de Hibernate por SpringDataConfiguration y application.properties
- Configurar application.properties con la conexión a la base, usuario, contraseña, driver, dialecto y spring.jpa.hibernate.ddl-auto
- Actualizar TourServiceImpl para que use repositorio inyectados en lugar de acceder directamente a Session
- Mantener las transacciones en la capa de servicio con @Transactional
![[Pasted image 20260513164805.png]]
Para reemplazar esa configuración usando application.properties se aprovecha que Spring Boot crea automáticamente el DataSource, el EntityManagerFactory y los repositorios SpringData
De esta forma, en lugar de obtener la session, se utilizan directamente los repositorios

![[Pasted image 20260513164812.png]]
Las propiedades más relevantes para Spring Data JPA que deben configurarse son las relacionadas a la conexión de base de datos y con Hibernate/JPA
```
spring.datasource.url=jdbc:postgresql://localhost:5432/tours
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
```
Descripción
- spring.datasource.url
	- Define la URL de conexión a la db
- spring.datasource.username
	- Usuario de la db
- spring.datasource.password
	- Contraseña del usuario
- spring.datasource.driver-class-name
	- Driver JDBC utilizado
- spring.jpa.database-platform
	- Dialecto hibernate, que le indica cómo generar sql para el motor utilizado
- spring.jpa.hibernate.ddl-auto
	- Controla qué hace hibernate con el esquema
		- create: crea el esquema de cero
		- create-drop: crea al iniciar y elimina al finalizar
		- update: actualiza el esquema manteniendo datos
		- validate: solo valida que coincida con el mapeo
		- none: no realiza cambios
Durante el desarrollo conviene usar "update" porque permite modificar entidades y que Hibernate actualice automáticamente el esquema sin perder los datos existentes

![[Pasted image 20260513164818.png]]
CrudRepository es una interfaz de Spring Data JPA que provee automáticamente las operaciones básicas CRUD
Hereda de la interfaz `Repository<T, ID>` que actúa como interfaz marcador dentro de Spring Data
Al extender CrudRepository, Spring genera automáticamente métodos del CRUD como
```java
<S extends T> S save(S entity);

Optional<T> findById(ID id);

Iterable<T> findAll();

void deleteById(ID id);

boolean existsById(ID id);

long count();
```
![[Pasted image 20260513164822.png]]
La jerarquía de repositorios de Spring Data agrega funcionalidades progresivamente
- CrudRepository
	- Operaciones CRUD básicas como save, findById, findAll, deleteById, existsById, count
- PagingAndSortingRepository
	- Agrega soporte para paginación y ordenamiento mediante Pageable y Sort
- JpaRepository
	- Agrega funcionalidades específicas de JPA, como flush, saveAndFlush, deleteAllInBatch, paginación avanzada y manejo más complejo de colecciones (List en lugar de Iterable)
![[Pasted image 20260513164827.png]]
[Ejercicio 9 BD2 · neftalito/UNLP@fb5444d](https://github.com/neftalito/UNLP/commit/fb5444d5ec3f15f425b15505c01eb5c963ac4e15)
![[Pasted image 20260513164832.png]]
Spring Data JPA genera la implementación concreta de los repositorios en tiempo de ejecución
Cuando se define una interfaz como
```
public interface RouteRepository extends CrudRepository<Route, Long> {}
```
Spring detecta esa interfaz al iniciar la aplicación y crea un proxy dinámico que implementa sus métodos
Ese proxy se encarga de
- Ejecutar métodos ya existentes como save, findById, findAll, delete
- Interpretar query methods
- Ejecutar consultas anotadas con @Query
- Delegar internamente en JPA/Hibernate para hacer la operación real sobre la base
![[Pasted image 20260513164837.png]]
En Spring Data JPA, `save()` unifica operaciones que en Hibernate directo se hacían con métodos separados como `session.save()` y `session.merge()`
En Hibernate:
```java
session.save(entity);   // inserta una entidad nueva
session.merge(entity);  // actualiza o re-adjunta una entidad detached
```
En Spring Data JPA:
```java
repository.save(entity);
```
`save()` sirve tanto para insertar como para actualizar. Spring Data JPA decide qué operación realizar según si la entidad es considerada nueva o no. Generalmente se determina observado el valor del id: si es nulo, es una entidad nueva y hace INSERT, si tiene un valor considera que la entidad ya existe y hace un UPDATE mediante merge
![[Pasted image 20260513165340.png]]
Práctica 1:
```java
package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import unlp.info.bd2.model.Purchase;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PurchaseRepository extends RepositoryBase<Purchase>
{
    public PurchaseRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Purchase> getAllPurchasesOfUsername(String username){
        return this.getSession().createQuery("from Purchase where user.username = :username", Purchase.class)
                .setParameter("username", username)
                .getResultList();
    }

    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        Long count = getSession()
                .createQuery("""
                select count(p)
                from Purchase p
                where p.date between :start and :end
            """, Long.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .uniqueResult();
        return count != null ? count : 0L;
    }
}

```
Práctica 2:
```java
package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Purchase;

import java.util.Date;
import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>
{
    List<Purchase> findByUserUsername(String username);

    long countByDateBetween(Date start, Date end);
}
```

Se eliminaron bastantes lineas, ya no es necesario implementar el save, el findById, findAll, delete, obtención/apertura/cierre del session, consultas simples, conteos simples entre fechas
![[Pasted image 20260513165345.png]]
La dependencia se declara mediante el constructor del servicio y Spring inyecta automáticamente la implementación generada del repositorio
La lógica de apertura y cierre de sesiones deja de estar en los repositorios y pasa a ser administrada automáticamente por Spring y JPA dentro del contexto transaccional definido con @Transactional
![[Pasted image 20260513165352.png]]
Spring Data JPA puede generar consultas automáticamente a partir del nombre del método definido en el repositorio.
Spring analiza el nombre del método y construye internamente la consulta JPQL correspondiente utilizando los atributos de la entidad
La estructura general suele ser `<operación>By<atributo><condición>`
Principales palabras clave:
- findBy
	- Busca entidades
	  `List<User> findByEmail(String email)`
- existsBy
	- Verifica existencia
	  `boolean existsByUsername(String username)`
- countBy
	- Cuenta registros
	  `long countByDateBetween(Date start, Date end)`
- deleteBy
	- Elimina registros
	  `void deleteByUsername(String username)`
Estas palabras se combinan con atributos de la entidad y operadores como
- And
- Or
- Between
- LessThan
- GreaterThan
- Like
- Containing
- StartingWith
- EndingWith
- OrderBy
Por ejemplo:
`List<Route> findByPriceLessThanOrderByName(float price)`
- findBy -> busqueda
- PriceLessThan -> `price < ?`
- OrderByName -> ordenado por nombre

![[Pasted image 20260513165357.png]]
Los parámetros de un Query Method se pasan como parámetros normales del método
```java
List<Route> findByPriceBetween(float min, float max);
```
Spring Data JPA asocia automáticamente cada parámetro con la condición correspondiente según el orden en el nombre del método y el orden de los parámetros del método
Por ejemplo
```java
List<Service> findByNameAndPrice(String name, float price);
```
Spring interpreta name y price como primer y segundo parámetro respectivamente
Si el orden de los parámetros no coindice con el orden de las condiciones en el nombre del método, la consulta funcionará incorrectamente porque Spring asigna los valores posicionalmente
Por ejemplo
```java
findByNameAndPrice(float price, String name)
```
Es incorrecto y generaría una consulta incorrecta o un error de tipos
![[Pasted image 20260513165406.png]]
```java
List<Purchase> findByUserUsername(String username);
```
![[Pasted image 20260513165409.png]]
```java
boolean existsByRoute(Route route);
```
![[Pasted image 20260513165413.png]]
```java
long countByRatingGreaterThanEqual(int rating);
```
![[Pasted image 20260513165417.png]]
```java
List<Route> findByPriceLessThanOrderByName(float price);
```
![[Pasted image 20260513165422.png]]
```java
Optional<User> findByEmail(String email);
```
![[Pasted image 20260513165427.png]]
Como hay que agrupar, no se puede hacer con Query Methods
```Java
@Query("""
    select p.route
    from Purchase p
    group by p.route
    order by count(p) desc
""")
List<Route> getTop3RoutesWithMostPurchases(Pageable pageable);
```
![[Pasted image 20260513165432.png]]
Kerywords comunes para Query Methods en Spring Data JPA

| Tipo        | Keywords                                                                  |
| ----------- | ------------------------------------------------------------------------- |
| Búsqueda    | `findBy`, `readBy`, `getBy`, `queryBy`, `searchBy`                        |
| Existencia  | `existsBy`                                                                |
| Conteo      | `countBy`                                                                 |
| Borrado     | `deleteBy`, `removeBy`                                                    |
| Comparación | `LessThan`, `LessThanEqual`, `GreaterThan`, `GreaterThanEqual`, `Between` |
| Igualdad    | `Is`, `Equals`, `Not`, `IsNot`                                            |
| Nulos       | `IsNull`, `IsNotNull`                                                     |
| Texto       | `Like`, `NotLike`, `StartingWith`, `EndingWith`, `Containing`             |
| Booleanos   | `True`, `False`                                                           |
| Colecciones | `In`, `NotIn`                                                             |
| Lógica      | `And`, `Or`                                                               |
| Orden       | `OrderBy`, `Asc`, `Desc`                                                  |
| Límite      | `Top`, `First`                                                            |
| Duplicados  | `Distinct`                                                                |
| Fechas      | `Before`, `After`                                                         |

Ejemplos
```java
List<Route> findByPriceLessThanOrderByNameAsc(float price);

Optional<User> findByEmail(String email);

boolean existsByRoute(Route route);

long countByRatingGreaterThanEqual(int rating);

List<Service> findTop3ByPriceGreaterThanOrderByPriceDesc(float price);

List<User> findByEmailContainingIgnoreCase(String email);
```

Soportan consultas simples sobre atributos, comparaciones, texto, nulos, booleanos, ordenamiento y combinaciones con And/Or. Para agregaciones como sum, avg, count con group by, rankings complejos o subconsultas conviene usar @Query

![[Pasted image 20260513165436.png]]
Los Query Methods tienen limitaciones porque sirven principalmente para consultas simples sobre atributos y relaciones directas. Entre las limitaciones están
- No manejan bien agregaciones complejas
	- No permiten expresar GROUP BY, COUNT, SUM, AVG
- Consultas con múltiples JOIN complejos
	- Aunque se pueden usar propiedades anidadas, se vuelve limitado cuando hay varios JOINS o se necesitan condiciones complejas entre entidades
- Consultas dinámicas complejas
	- No permiten armar queries condicionales del tipo
		- "Filtrar por precio si viene, por fecha si viene, etc."
	- El nombre del método no puede adaptarse dinámicamente
- Consultas muy largas o dificiles de leer
	- Se vuelven poco mantenibles
- Subconsultas o lógica avanzada
	- No soportan subqueries
	- No permiten lógica compleja dentro de la consulta
Tres casos del modelo donde la técnica resulta insuficiente
- Top 3 rutas con más compras
- Filtrado dinámico de Purchase (por usuario, fecha o precio opcionalmente)
- Buscar Route con condiciones sobre múltiples relaciones

Los enfoques alternativos posibles son
- @Query
	- Permite escribir la consulta manualmente
	- Flexible y claro
- Specification (Criteria API)
	- Para consultas dinámicas
	  ```java
	  Specification<Purchase> spec = (root, query, cb) -> {
		  return cb.equal(root.get("user").get("username"), username);
	  }
	  ```
	- Permite combinar filtros dinámicamente
- QueryDSL
	- Alternativa más potente y tipada al Criteria API
	- Ideal para queries complejas y mantenibles
- Named Queries
	- Queries definidas de la entidad
	- Util para consultas reutilizables

![[Pasted image 20260513165441.png]]
HQL es el lenguaje de consultas propio de hibernate, mientras que JPQL es el estándar definido por JPA
Ambos trabajan sobre entidades, atributos y relaciones del modelo y no directamente sobre tablas y columnas SQL

La diferencia principal es que JPQL es el estándar JPA y HQL es la implementación de hibernate

Generalmente son intercambiables, aunque HQL puede soportar características adicionales específicas de Hibernate

La anotación @Query utiliza JPQL por defecto

![[Pasted image 20260513165445.png]]
Una consulta @Query normal utiliza JPQL mientras que @Query(nativeQuery = true) utiliza SQL nativo
Con JPQL
- Se trabaja con entidades y atributos java
- Hibernate genera el SQL automáticamente
- Es portable entre motores de base de datos
```java
@Query("""
    select p.user
    from Purchase p
    group by p.user
    having sum(p.totalPrice) >= :mount
""")
List<User> getUserSpendingMoreThan(float mount);
```
Con nativeQuery = true
- Se escribe SQL real
- Se usan tablas y columnas de la db
- Depende del motor utilizado
```java
@Query(value = """
    SELECT *
    FROM routes r
    ORDER BY (
        SELECT AVG(rv.rating)
        FROM reviews rv
        JOIN purchases p ON rv.purchase_id = p.id
        WHERE p.route_id = r.id
    ) DESC
    LIMIT 3
""", nativeQuery = true)
List<Route> getTop3Routes();
```

Conviene usar
- JQPL para consultas orientadas al modelo y portables
- SQL nativo cuando se necesitan funciones específicas del motor, optimizaciones particulares o características que JPQL no soporta fácilmente
![[Pasted image 20260513165449.png]]
Los parámetros en @Query pueden pasarse de dos formas
Parámetros posicionales
Se referencian por posición usando ?1, ?2, etc
Ejemplo
```java
@Query("""
    select p
    from Purchase p
    where p.date between ?1 and ?2
""")
List<Purchase> getPurchasesBetween(Date start, Date end);
```
Donde cada posición referencia al parámetro de esa posición

Parámetros nombrados
Se usan nombres mediante :nombre y @Param
Ejemplo
```java
@Query("""
    select p
    from Purchase p
    where p.date between :start and :end
	""")
List<Purchase> getPurchasesBetween(
        @Param("start") Date start,
        @Param("end") Date end
);
```

La forma recomendada es usar parámetros nombrados porque mejora la legibilidad y evita errores cuando se cambia el orden de parámetros. Además, la consulta queda más clara y facilita el mantenimiento

![[Pasted image 20260513165454.png]]
Pageable es una interfaz de Spring Data JPA que representa información de paginación y ordenamiento para una consulta. Permite indicar: número de página, cantidad de elementos por página, ordenamiento. Se construye normalmente usando PageRequest
Ejemplo
```java
Pageable pageable = PageRequest.of(0, 10);
```
Esto representa la página 0 y 10 elementos por página
También puede tener ordenamiento
```java
Pageable pageable = PageRequest.of(
    0,
    10,
    Sort.by("date").descending()
);
```
Y luego se usa como parámetro
```
Page<Purchase> findByUserUsername(String username, Pageable pageable);
```

![[Pasted image 20260513165458.png]]
`Page<T>` y `Slice<T>` representan resultados paginados pero tienen una diferencia importante
- Page devuelve información completa de paginación
- Slice devuelve solo una porción de resultados

Page incluye
- contenido
- número de página
- tamaño
- total de páginas
- total de elementos
- información sobre página siguiente y anterior

Slice incluye
- contenido
- información sobre si existe una siguiente página
- no conoce el total de elementos ni el total de páginas

Page ejecuta una consulta adicional COUNT que Slice no

Conviene utilizar Page cuando se necesita mostrar cantidad total de páginas o resultados y Slice cuando sólo interesa navegar secuencialmente sin conocer el total

![[Pasted image 20260513165502.png]]
Para obtener la segunda página de compras de un usuario con 10 resultados ordenados por fecha descendente, desde la capa de servicio se usaría
```java
Pageable pageable = PageRequest.of(
    1,
    10,
    Sort.by("date").descending()
);

Page<Purchase> purchases =
        purchaseRepository.findByUserUsername(username, pageable);
```
![[Pasted image 20260513165507.png]]
Se agrega ordenamiento a un Query Method usando OrderBy en el nombre del método
```java
List<Route> findByPriceLessThanOrderByNameAsc(float price);
```
![[Pasted image 20260513165512.png]]

![[Pasted image 20260513165519.png]]

![[Pasted image 20260513165525.png]]
![[Pasted image 20260513165530.png]]
```java
// Práctica 1
public List<Route> getTop3RoutesWithMaxRating() {
        return getSession()
                .createQuery("""
                select p.route
                from Review r
                join r.purchase p
                group by p.route
                order by avg(r.rating) desc
            """, Route.class)
                .setMaxResults(3)
                .getResultList();
    }
    
// Práctica 2
@Query("""
	select p.route
	from Review r
	join r.purchase p
	group by p.route
	order by avg(r.rating) desc
""")
List<Route> getTop3RoutesWithMaxRating(Pageable pageable);
```

La diferencia principal es que en la práctica 1 el repositorio estaba más acoplado a Hibernate directo, dependía de SessionFactory y Session
En Spring Data JPA, el repositorio sólo declara qué consulta necesita y Spring genera la implementación

Reduce código repetitivo
La ventaja es que el código queda más corto, legible y fácil de mantener

![[Pasted image 20260513165536.png]]
El atributo readOnly =true en @Transactional indica que el método sólo realiza lectura y no debería modificar la base de datos
Ejemplo:
```Java
@Transactional(readOnly = true)
public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
}
```
Puede activar optimizaciones como evitar chequeos innecesarios de cambios sobre entidades, reducir trabajo del contexto de persistencia y mejorar la intención del código

Conviene aplicarlo en métodos de consulta que sólo obtienen datos

![[Pasted image 20260513165541.png]]
Spring maneja el rollback automático cuando ocurre una excepción durante la ejecución de un método antoado con @Transactional
Por defecto, Spring hace rollback unicamente sobre excepciones no verificadas (RuntimeException y Error)
Las excepciones verificadas (Exception, IOException, etc.) no provocan rollback automáticamente
Para configurar rollback sobre otras excepciones se usa
```Java
@Transactional(rollbackFor = Exception.class)
```
Ejemplo
```java
@Transactional(rollbackFor = ToursException.class)
public void assignDriverByUsername(String username, Long idRoute)
        throws ToursException {

    DriverUser driver = userRepository
            .findDriverUserByUsername(username)
            .orElseThrow(() ->
                    new ToursException("No existe el chofer"));

    Route route = routeRepository.findById(idRoute)
            .orElseThrow(() ->
                    new ToursException("No existe la ruta"));

    route.addDriver(driver);

    routeRepository.save(route);
}
```
Si ocurre un ToursException, Spring hará rollback y deshará cualquier modificación realizada durante la transacción

![[Pasted image 20260513165545.png]]
![[Pasted image 20260513165609.png]]
Un DTO (Data Transfer Object) es un objeto utilizado para transferir datos entre capas de una aplicación, generalmente conteniendo solo la información necesaria para un caso de uso específico
Su propósito es
- reducir la cantidad de datos transferidos
- desacoplar la capa de persistencia de otras capas
- evita exponer directamente las entidades JPA
- mejorar seguridad y mantenimiento
No siempre conviene devolver entidades JPA directamente porque
- pueden contener relaciones inncesarias
- pueden producir cargas LAZY inesperadas
- pueden generar ciclos de serialización
- acoplan la estructura interna de persistencia con el exterior
- exponen datos que no deberían mostrarse
![[Pasted image 20260513165614.png]]
Caso 1: Resumen de rutas
En lugar de devolver la entidad completa Route, conviene devolver un DTO con información resumida para listados
```java
public class RouteSummaryDTO {
    private String routeName;
    private float price;
    private int stopsCount;
}
```
Esto evita enviar relaciones completas, choferes, guías, compras, reviews, etc.

Caso 2: Información resumida de compras
Para mostrar historial de compras de un usuario
```java
public class PurchaseDTO {
    private String code;
    private Date date;
    private String routeName;
    private float totalPrice;
}
```
No hace falta devolver todos los ItemService, reviews completas, usuario completo, relaciones asociadas

En ambos casos, el DTO reduce datos transferidos, evita problemas de serialización y desacopla la capa de persistencia de la presentación
![[Pasted image 20260513165618.png]]
Exponer entidades JPA directamente desde un servicio o endpoint tiene varios riesgos
Uno de los principales es la aparición de ciclos de serialización en relaciones bidireccionales
Ejemplo
```
Purchase -> User
User -> purchases
```
Al serializar una compra, también se serializa el user, que a su vez serializa nuevamente sus compras, produciendo recursión infinita o errores

Otro problema es el fuerte acoplamiento entre capas.
Si se exponen entidades directamente, cualquier cambio en el modelo de persistencia afecta automáticamente a las respuestas del sistema y a las capas  consumidoras
Además
- pueden exponerse atributos sensibles
- pueden dispararse cargas lazy inesperadas
- se transfieren más datos de los necesarios
![[Pasted image 20260513165622.png]]
![[Pasted image 20260513165627.png]]
El borrado lógico consiste en no eliminar físicamente un registro de la base de datos, sinó marcarlo como inactivo o eliminado
En el modelo de tours, si un usuario se da de baja, no conviene eliminarlo físicamente porque ese usuario puede tener compras asociadas. Si se borra el registro real, podrían quedar inconsistencias o perderse datos históricos de compras, reviews y facturación
Con soft delete, el usuario queda marcado como inactivo por un campo "active" o a través del seteo de una fecha en un campo "deletedAt"
De esta forma se conserva el historial de compras y no se nullean relaciones con Purchase. Además, el usuario dejaría de aparecer en consultas normales pero se sigue pudiendo recuperar su información
La ventaja del borrado lógico es que mantiene la integridad histórica del sistema sin perder datos importantes
![[Pasted image 20260513165632.png]]
Campo booleano
- Se agrega un atributo como "active"
- Al eliminar una entidad, cambia ese valor a false
- Es simple, fácil de consultar
- No guarda cuándo ocurrió el borrado
Campo fecha
- Se agrega un atributo como "deletedAt"
- Al eliminar una entidad, ese atributo toma un valor no nulo
- Permite saber cuándo se borró aunque ocupa más espacio y las consultas son un poco más complejas

![[Pasted image 20260513165639.png]]
@SQLDelete permite redefinir qué SQL ejecuta Hibernate cuando se llama a delete() sobre una entidad
En lugar de hacer un DELETE físico, puede ejecutarse con UPDATE para marcar el registro como inactivo
@Where agrega automáticamente una condición a todas las consultas generadas por Hibernate para esa entidad
Ejemplo sobre User
```java
package unlp.info.bd2.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "users")
@SQLDelete(sql = """
    UPDATE users
    SET active = false
    WHERE id = ?
""")
@Where(clause = "active = true")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private boolean active = true;
}
```
Cuando se ejecuta el método delete, hibernate ejecuta un UPDATE sobre el atributo active en lugar de ejecutar un DELETE. Además, gracias al @Where, todas las consultas normales ignorarán usuarios inactivos

![[Pasted image 20260513165647.png]]
```java
@Entity
@Table(name="users")
@SQLDelete(sql = """
    UPDATE users
    SET active = false
    WHERE id = ?
""")
@Where(clause = "active = true")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	...
	
	@Column(nullable=false)
    private boolean active;
	
	...
}
```

![[Pasted image 20260513165652.png]]
![[Pasted image 20260513165658.png]]

Con soft delete, ese método cambia de sentido
Antes servía para evitar borrar físicamente usuarios  con compras porque eso podía romper relaciones
Ahora como delete no elimina el registro físico ya no se rompen las FK ni se pierden compras históricas
Este método puede seguir siendo útil para validar reglas de negocio antes de marcar al usuario como inactivo, pero el soft delete reduce el problema de la integridad referencial así que no es completamente necesario

