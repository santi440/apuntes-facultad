## 2.1 Migración de repositorios
12. Comparar el código de PurchaseRepository de la Práctica 1 (con Session de Hibernate) con el nuevo PurchaseRepository de Spring Data JPA. ¿Cuántas líneas de código se eliminaron? ¿Qué operaciones ya no es necesario implementar manualmente?

archivo original - archivo SPRING DATA = 
39 lineas             - 18 lineas = ~21 lineas

todas las operaciones de save, update o cualquier cosa relacionada con sessionFactory se pueden facilmente eliminar y dejar que crudRepository las implemetne en tiempo de ejecucion.

13. En la Práctica 1, los repositorios gestionaban internamente la Session. ¿Que recibe ahora un servicio que necesita usar un repositorio Spring Data? ¿Cómo se declara esa dependencia? ¿Dónde queda ahora la lógica de apertura y cierre de sesiones?
- En la Práctica 1, `TourServiceImpl` recibía **clases concretas** que heredaban de `HibernateRepository`. Con Spring Data JPA, `TourServiceImpl` recibe una **interfaz**. Spring genera automáticamente la implementación real en tiempo de ejecución (un proxy dinámico) -> La variable tiene el mismo nombre y se inyecta igual, pero **el tipo apunta a una interfaz, no a una clase**. No existe ningún archivo `UserRepositoryImpl.java` — Spring lo genera solo.
- Exactamente igual que antes: con `@Autowired`. No cambia la sintaxis. Lo que cambia es lo que hay detrás del tipo inyectado.
- Ahora: ningún repositorio tiene  código del tipo `getSession()`. La gestión de sesión es completamente invisible para el programador y vive en tres capas que Spring administra solas

## 2.2 Query Methods
Spring Data JPA puede generar consultas automáticamente a partir del nombre del método
definido en la interfaz del repositorio. Esta técnica se denomina Query Methods.
### 14. ¿Cómo funciona la generación de consultas por nombre de método en Spring Data JPA? Describir las palabras clave principales que puede interpretar (findBy, existsBy, countBy, deleteBy) y cómo se combinan con los atributos de la entidad.
 Spring Data JPA utiliza un mecanismo de **análisis sintáctico (parsing)** en tiempo de ejecución. Cuando la aplicación arranca, el framework escanea las interfaces de repositorio y descompone los nombres de los métodos utilizando _Reflection_ para construir un Árbol de Sintaxis Abstracta (AST) que luego traduce a consultas JPQL/HQL.
 El nombre de un Query Method se divide en dos partes principales separadas por la palabra clave `By`:
 - **El prefijo (Introducer):** Define la acción o el tipo de operación.
 -  **Los criterios (Criteria):** Define las propiedades de la entidad por las que se va a filtrar.    
#### Palabras clave principales (Prefijos):
- **`findBy`** (o `getBy`, `readBy`, `queryBy`): Indica una operación de selección. Retorna una entidad única (`User`), un `Optional<User>` o una colección (`List<User>`).
- **`existsBy`**: Indica una consulta de comprobación. Traduce a un `SELECT COUNT(...)` o una estructura eficiente que retorna estrictamente un `boolean` (`true` si encuentra coincidencia, `false` si no).
- **`countBy`**: Indica una consulta de agregación. Retorna un tipo numérico (`long` o `int`) con la cantidad de registros que cumplen las condiciones.
- **`deleteBy`** (o `removeBy`): Indica una operación de borrado. Ejecuta la baja de los registros que cumplen el criterio y suele retornar `void` o un `Long` indicando la cantidad de filas eliminadas.
#### Combinación con los atributos de la entidad:
Los atributos se escriben respetando el _CamelCase_. Spring busca esa propiedad en la clase asociada al repositorio.
- **Condiciones simples:** `findByEmail(String email)` busca el atributo `email`.
- **Operadores lógicos:** Se usan las palabras clave **`And`** y **`Or`** para encadenar atributos: `findByUsernameAndActive(String user, boolean act)`.
- **Navegación de relaciones:** Para propiedades de objetos asociados, se concatenan las clases o se usa el guion bajo (`_`) para evitar ambigüedades: `findByUser_Username(String name)` busca el `username` dentro de la entidad embebida/relacionada `User`.
### 15. ¿Cómo se pasan los parametros a un Query Method? ¿Cómo hace Spring Data JPA para asociar cada parámetro del método con la condición correspondiente en la consulta? ¿Qué ocurre si el orden de los parámetros no coincide con el orden de las condiciones en el nombre del método?
- Se pasan como argumentos posicionales estándar en la firma del método de Java.
- Por defecto, Spring Data JPA utiliza la **asociación posicional (basada en el orden)**, no importa el nombre del parametro, solo importa el orden fisico.
- La consulta se generará sintácticamente bien, pero **asociará los valores de forma incorrecta**, produciendo un fallo semántico o un error de tipado en runtime.

### 16. Investigar y para cada uno de los siguientes requerimientos indicar si es posible resolverlo con un Query Method y escribir la firma del método correspondiente. Si no es posible, explicar por qué:
a) Buscar todas las Purchase de un usuario dado su username. = desde Purchase `List<Purchase> findByUserUsername(String username);`
b) Verificar si existe alguna Purchase para una Route dada. = `boolean existsByRoute(Route route);`
c) Contar cuantas Review tienen un rating mayor o igual a un valor dado. = `long countByRatingGreaterThanEqual(int rating);`
d) Obtener todas las Route cuyo precio sea menor a un valor dado, ordenadas por
nombre. =
==RAROOOOOOO probar si anda je ==
- **¿Es posible?** Sí. Se combina el filtro menor que con la cláusula de ordenamiento estático.
- **Firma (en `RouteRepository`):** `List<Route> findByPriceLessThanOrderByWithDrawNameAsc(float price); List<Route> findByPriceLessThanOrderByNameAsc(float price);`	
e) Buscar un User por su email. = `Optional<User> findByEmail(String email);`
f) Obtener los top 3 Route con mayor cantidad de Purchase.
**No, por este mecanismo.** * **Explicación:** Las palabras clave como `Top3` sirven para limitar resultados basados en un ordenamiento de atributos directos de la entidad (ej. `findTop3ByOrderByPriceDesc`). Sin embargo, este requerimiento exige contar la cantidad de elementos en una relación externa (agrupar `Purchase` por `Route` y hacer un `COUNT`), lo cual requiere funciones de agregación (`COUNT`, `GROUP BY`) que la derivación por nombre no puede computar de forma automatizada.
### 17. Investigar cuales son las palabras clave (keywords) disponibles en Spring Data JPA para construir Query Methods. ¿Qué tipos de condiciones, comparaciones y operadores lógicos soporta?
- **Operadores Lógicos:** `And`, `Or`.
- **Comparaciones de Igualdad y Desigualdad:** `Is`, `Equals` (por defecto si no se pone nada), `Not`.
- **Comparaciones Numéricas y de Fechas:** `LessThan`, `LessThanEqual`, `GreaterThan`, `GreaterThanEqual`, `Before` (para fechas), `After` (para fechas), `Between` (requiere dos parámetros).
- **Verificación de Nulidad:** `IsNull`, `IsNotNull`, `NotNull`.
- **Coincidencia de Patrones (Strings):** `Like`, `NotLike`, `StartingWith` (agrega `%` al final), `EndingWith` (agrega `%` al principio), `Containing` (agrega `%` a ambos lados), `IgnoreCase` (ignora mayúsculas/minúsculas).
- **Evaluación de Colecciones:** `Containing` (para ver si una colección contiene un elemento), `IsEmpty`, `IsNotEmpty`.
- **Evaluación de Conjuntos:** `In` (requiere una colección de parámetros), `NotIn`.
- **Booleanos:** `True`, `False`.
### 18. ¿Qué limitaciones tienen los Query Methods? Describir al menos tres casos del modelo donde esta técnica resulta insuficiente y es necesario otro enfoque. Describa los otros enfoques posibles para implementar dichas consultas.
- **Inmanejables con consultas complejas:** A medida que se agregan condiciones (`And`/`Or`) y navegaciones de relaciones, el nombre del método se vuelve ridículamente largo, ilegible y propenso a errores tipográficos
- **Falta de soporte para funciones de agregación avanzadas:** No permiten realizar operaciones complejas de `GROUP BY` o cláusulas `HAVING`.
- **No soportan subconsultas complejas ni uniones ad-hoc:** No se pueden cruzar tablas que no tengan una relación estrictamente mapeada en el modelo de objetos. // no se porque quisieras hacer esto, CRIMINAL
  
  #### Tres casos del modelo donde resulta insuficiente:
1. **`getTop3RoutesWithMaxRating()`**: Requiere calcular el promedio (`AVG`) de los ratings de las reviews de las compras asociadas a cada ruta y agrupar por ruta. Los Query Methods no computan promedios grupales.
2. **`getMostDemandedService()`**: Requiere hacer un `SUM(quantity)` de los ítems y ordenar por ese resultado agregado.
3. **`getTopNSuppliersInPurchases(int n)`**: Requiere una ordenación basada en un conteo de apariciones inter-tabla y, fundamentalmente, **el parámetro `n` es dinámico**, algo que las palabras clave como `Top3` (estáticas) no permiten parametrizar.
#### Alternativas
- **Anotación `@Query` (JPQL/HQL):** Permite escribir la consulta declarativa directamente arriba del método en la interfaz del repositorio.
```JAVA
    @Query("select r from Purchase p join p.route r join p.review rev group by r.id order by avg(rev.rating) desc")
    List<Route> findTop3RoutesByMaxRating(Pageable pageable); // Se usa Pageable para el límite dinámico
```
- **Consultas Nativas (`@Query(nativeQuery = true)`):** Útil si se requiere optimizar la performance con características específicas del motor de base de datos (como el uso de tablas intermedias invisibles para el ORM).
- **Mecanismo de Repositorios Custom (Clases Impl):** Crear una interfaz personalizada (ej. `UserRepositoryCustom`), implementarla en una clase usando `EntityManager` y la API de **Criteria** (ideal para consultas con filtros dinámicos que cambian según los inputs del usuario en la pantalla) y hacer que el repositorio de Spring Data herede también de esta interfaz.

## 2.3 Consultas con @Query
La anotación @Query permite escribir consultas directamente sobre el método del repositorio. Antes de utilizarla es importante entender la relación entre los lenguajes de consulta involucrados.
### 19. En la Práctica 1 se utilizaron consultas HQL (Hibernate Query Language). ¿Que diferencia hay entre HQL y JPQL (Java Persistence Query Language)? ¿Son intercambiables? ¿Cual de los dos acepta @Query por defecto?

- **JPQL (Java Persistence Query Language):** Es el lenguaje de consultas definido por la especificación oficial de **JPA** (Java Persistence API / Jakarta Persistence). Es un estándar genérico del ecosistema Java para que las consultas sean completamente independientes del proveedor de persistencia que elijas.
- **HQL (Hibernate Query Language):** Es el lenguaje propio y nativo de **Hibernate**. Hibernate existía mucho antes de que se creara el estándar JPA. De hecho, JPQL se diseñó basándose fuertemente en HQL. HQL incluye funciones y optimizaciones avanzadas específicas de Hibernate (como soporte para ciertas funciones matemáticas, palabras clave como `Property`, o un manejo de _joins_ más flexible) que JPQL estándar no reconoce.

**No de forma absoluta.** Todo query escrito en JPQL estándar es 100% válido en HQL.
Por defecto, la anotación `@Query` de Spring Data JPA **acepta consultas en JPQL**. Sin embargo, como el proveedor de persistencia subyacente que estás usando en el proyecto es Hibernate, podés escribir extensiones de HQL dentro del string de `@Query` y van a compilar
### 20. ¿Que diferencia hay entre una consulta @Query con JPQL y una con nativeQuery = true? ¿Cuándo conviene cada una? Dar un ejemplo concreto del modelo para cada caso.
- **`@Query` con JPQL (Por defecto):** Le habla al **Modelo de Objetos** (Entidades y Atributos). Hibernate intercepta el query y lo traduce al SQL del motor configurado. En el 90% de los casos. Garantiza portabilidad (si cambiás de MySQL a PostgreSQL, el código no se toca) y aprovecha los mecanismos del ORM, como la herencia polimórfica y la conversión automática de tipos. _Ejemplo:_ Buscar las compras de un usuario por su username.
```java
@Query("select p from Purchase p where p.user.username = :username")
List<Purchase> findPurchasesByUsername(@Param("username") String username);
```
- **`@Query(nativeQuery = true)`:** Se salta por completo al ORM y le envía la consulta **directamente al motor de la Base de Datos**. Habla en términos de **Tablas y Columnas** reales de SQL. Conviene cuando te importa la eficiencia, aprovechar tablas no mappeadas o funciones especificas del motor (como funciones geoespaciales). _Ejemplo:_ Obtener las paradas más visitadas cruzando la tabla intermedia invisible `Route_Stop` para un reporte masivo:
```java
@Query(value = "SELECT s.*, COUNT(*) as total FROM stops s " +
               "JOIN Route_Stop rs ON s.id = rs.stop_id " +
               "GROUP BY s.id ORDER BY total DESC", nativeQuery = true)
List<Stop> getMostVisitedStopsNative();
```
### 21. ¿Cómo se pasan parámetros a una consulta @Query? Describir y comparar las dos formas: parámetros posicionales (?1, ?2) y parámetros nombrados (@Param). ¿Cuál es la forma recomendada y por qué?
#### 1. Parámetros Posicionales (`?1`, `?2`)
Se asocian según el orden físico en el que aparecen los argumentos en la firma del método. El número indica la posición del parámetro (empezando en 1).

```JAVA
@Query("select u from User u where u.email = ?1 and u.active = ?2")
User findByEmailAndStatus(String email, boolean active);
```
#### Parámetros Nombrados (`:nombre` + `@Param`)
Se vinculan explícitamente mediante un nombre antecedido por dos puntos (`:`) en el query, asociándolo con la anotación `@Param("nombre")` en la firma del método.
```java
@Query("select u from User u where u.email = :email and u.active = :status")
User findByEmailAndStatus(@Param("email") String email, @Param("status") boolean active);
```
**el uso de Parámetros Nombrados (`@Param`).** Evita errores humanos catastróficos al refactorizar o modificar la lógica de las consultas, hace que el código sea infinitamente más limpio para que otra persona lo lea

## 2.4 Paginación y ordenamiento
Spring Data JPA incorpora soporte nativo para paginación y ordenamiento a través de Pageable y Sort, sin necesidad de modificar las consultas manualmente. Esta funcionalidad es necesaria para algunas de las consultas que se implementaran en la sección siguiente.
### 22. ¿Qué es la interfaz Pageable? ¿Cómo se construye una instancia de Pageable? ¿Qué información encapsula (número de página, tamaño, ordenamiento)?
**`Pageable`** es una interfaz de Spring Data que encapsula toda la información necesaria para realizar consultas paginadas sobre la base de datos. Abstrae al desarrollador de tener que calcular manualmente los parámetros de desplazamiento (`OFFSET`) y límite (`LIMIT`) en las sentencias SQL.       
#### ¿Qué información encapsula?
- **Número de página (`pageNumber`):** El índice de la página que se quiere recuperar (basado en índice cero; la primera página es la `0`).
- **Tamaño de la página (`pageSize`):** La cantidad máxima de registros que debe retornar la consulta para esa página.
- **Ordenamiento (`sort`):** Los criterios de ordenación (atributos y dirección: `ASC`/`DESC`) aplicados a los resultados.
EJ: 
```java

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

// Paginación simple (Página 0, 20 elementos)
Pageable paginacionSimple = PageRequest.of(0, 20);

// Paginación con ordenamiento (Página 0, 10 elementos, ordenados por precio descendente)
Pageable paginacionConOrden = PageRequest.of(0, 10, Sort.by("price").descending());
```
### 23. ¿Que diferencia hay entre los tipos de retorno `Page<T>` y `Slice<T>`? ¿Cuándo conviene cada uno? ¿Qué consulta adicional ejecuta `Page<T>` que `Slice<T>` no ejecuta?
Ambas interfaces representan una porción (página) de datos, pero la diferencia radica en **la información total que conocen** y el **costo de rendimiento** asociado en la base de datos.
- `Page<T>`: Conoce el subconjunto de datos actual, pero **también conoce la cantidad total de registros y páginas** que existen en toda la base de datos para esa consulta. Hace un Count de más y sirve cuando tenes que saber cuantas páginas hay en total.
- `Slice<T>`Solo sabe si existe una página siguiente o no. Para lograr esto de forma eficiente, Spring le pide a la base de datos un registro más del tamaño solicitado (`pageSize + 1`). Si ese registro extra existe, confirma que hay una página siguiente, pero nunca calcula cuántos elementos totales hay en la tabla. Conviene en los scrolls infinitos o "cargar más"
### 23. Mostrar como se invocará desde la capa de servicio un método paginado para obtener la segunda página de compras de un usuario, con 10 resultados ordenados por fecha descendente.

Para obtener la **segunda página** (índice `1`), con **10 resultados** ordenados por **fecha descendente**, el flujo entre el repositorio y el servicio se estructuraría así:
```java
// PurchaseRepository.java
@Query("select p from Purchase p where p.user.username = :username")
Page<Purchase> findByUserUsername(@Param("username") String username, Pageable pageable);
```
- Construís el objeto `PageRequest` y se lo inyectás al método del repositorio:
```java
//TourServiceImpl.java
public Page<Purchase> getPurchasesBySubscriber(String username) {
    // Segunda página = índice 1. Tamaño = 10. Orden = date DESC
    Pageable pageable = PageRequest.of(1, 10, Sort.by("date").descending());
    
    // Invocamos al repositorio pasando la configuración de paginación
    return purchaseRepository.findByUserUsername(username, pageable);
}
```
### 24. ¿Cómo se agrega ordenamiento a un Query Method sin usar Pageable? ¿Qué palabra clave se usa en el nombre del método? Mostrar un ejemplo concreto con el modelo.
Es posible definir un ordenamiento estático (fijo) directamente en la signatura del método del repositorio utilizando la palabra clave **`OrderBy`**
#### Ejemplo  (`Route`):
Si necesitás obtener todas las rutas cuyo precio sea menor a un valor dado, ordenadas por su nombre de forma alfabética ascendente:
```java
package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Route;
import java.util.List;

public interface RouteRepository extends CrudRepository<Route, Long> {

    // Query Method derivado con ordenamiento estático incorporado
    List<Route> findByPriceLessThanOrderByNameAsc(float price);
}
```

## 2.5 Implementación de las consultas del modelo
Las siguientes consultas fueron implementadas en la Práctica 1 usando HQL con Session de Hibernate. El objetivo es reimplementarlas ahora usando repositorios Spring Data JPA,  eligiendo para cada una la estrategia más adecuada: Query Method o @Query con JPQL.
### 26. Para cada consulta, indicar la estrategia elegida (Query Method o @Query) e implementarla en el repositorio correspondiente:

| Consulta                                               | Repositorio        | Estrategia Elegida |
| ------------------------------------------------------ | ------------------ | ------------------ |
| getAllPurchasesOfUsername(String<br>username)          | PurchaseRepository | Query Method       |
| getUserSpendingMoreThan(float amount)                  | UserRepository     | @Query             |
| getTopNSuppliersInPurchases(int n)                     | SupplierRepository | @Query             |
| getCountOfPurchasesBetweenDates(Date<br>from, Date to) | PurchaseRepository | Query Method       |
| getRoutesWithStop(Stop stop)                           | RouteRepository    | Query Method       |
| getMaxStopOfRoutes()                                   | RouteRepository    | @Query             |
| getRoutesNotSell()                                     | RouteRepository    | Query Method       |
| getTop3RoutesWithMaxRating()                           | RouteRepository    | @Query             |
| getMostDemandedService()                               | ServiceRepository  | @Query             |
| getTourGuidesWithRating1()                             | UserRepository     | @Query             |
###  27. Implementar cada una de las consultas de la tabla anterior en el repositorio correspondiente, utilizando la estrategia indicada. Para las consultas que requieran paginación, utilizar Pageable como parámetro adicional.
### 28. Comparar la implementación de al menos dos consultas complejas (por ejemplo getTopNSuppliersInPurchases y getTop3RoutesWithMaxRating) entre la Práctica 1 y esta práctica. ¿Qué diferencias hay en términos de cantidad de código, legibilidad y acoplamiento a la infraestructura de persistencia? A partir de esta comparación, ¿que es el código boilerplate y que ventaja concreta representa su reducción para el mantenimiento de un proyecto real?

| Dimensión                          | Práctica 1                                                                         | Práctica 2                                                                                      |
| ---------------------------------- | ---------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| **Líneas por consulta**            | ~6–8 (incluyendo obtener Session, crear query, setear resultados, retornar)        | 1–2 (declaración + anotación)                                                                   |
| **Tipo del repositorio**           | Clase concreta que Spring instancia                                                | Interfaz — Spring genera la implementación en tiempo de ejecución                               |
| **Acoplamiento a Hibernate**       | Directo: importa `Session`, `SessionFactory`, llama métodos de la API de Hibernate | Ninguno: solo importa `@Query` y `Pageable` de Spring Data, que son agnósticos al proveedor JPA |
| **¿Quién maneja `setMaxResults`?** | El repositorio, manualmente                                                        | `Pageable` — el servicio le dice cuántos quiere con `PageRequest.of(0, n)`                      |
| **La JPQL en sí**                  | Idéntica                                                                           | Idéntica                                                                                        |
### ¿Qué es el código boilerplate?
El **código boilerplate** es código repetitivo que no expresa lógica de negocio propia, sino que existe únicamente para satisfacer los requisitos de la infraestructura. En la Práctica 1, cada método de repositorio contenía las mismas líneas estructurales:
```java
Session session = sessionFactory.getCurrentSession(); // infraestructura
return session.createQuery("...", Tipo.class)         // infraestructura
              .setMaxResults(n)                        // infraestructura
              .getResultList();                        // infraestructura
```
La única línea con contenido de dominio es el string JPQL. Todo lo demás es boilerplate.
### Ventaja concreta para el mantenimiento

La reducción de boilerplate tiene tres consecuencias directas en un proyecto real:

**1. Menos superficie de error.** Cada línea de infraestructura que no existe no puede tener un bug. El `getCurrentSession()` olvidado, el `setMaxResults` aplicado al objeto equivocado, el `.getResultList()` omitido — ninguno de esos errores puede ocurrir si el código no existe.

**2. Independencia del proveedor JPA.** En P1, cambiar de Hibernate a EclipseLink obligaba a reescribir todos los repositorios porque importaban clases concretas de Hibernate (`Session`, `SessionFactory`). En P2, los repositorios no importan nada de Hibernate — si se cambia el proveedor JPA en `pom.xml`, las interfaces siguen compilando sin modificación.

**3. El foco queda en el dominio.** Cuando un desarrollador lee `SupplierRepository` en P2, ve exactamente una cosa: la JPQL de negocio. No tiene que leer código de plomería para entender qué hace el repositorio. Esto reduce el tiempo de comprensión y acelera el onboarding de nuevos miembros al equipo.