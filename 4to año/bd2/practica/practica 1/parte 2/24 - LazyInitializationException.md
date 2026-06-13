¿Cómo podría producirse una LazyInitializationException en el modelo? Investigue de qué representa esta excepción y escribir un escenario concreto explicando al menos formas de resolverlo sin cambiar el FetchType a EAGER.

Ocurre cuando intentas acceder a una relación marcada como `FetchType.LAZY` en una entidad que está **"detached"** (desconectada).

En JPA, las entidades están vinculadas a una **Session** (o `EntityManager`). Cuando la transacción termina o el contexto de persistencia se cierra, la entidad se desconecta. Si en ese momento intentas acceder a un atributo que no fue cargado inicialmente (porque era Lazy), Hibernate intenta ir a la base de datos, pero como la conexión ya se cerró, lanza la excepción: _“no Session or session was closed”_.

Ejemplo: En la capa de servicio me traigo un Purchase con getPurchaseById(Long id). si quiero mostrar los `itemService` en una `Puchase` (que son Lazy) por ejemplo `purchase.getItemServiceList().get(0).getService().getName();` no hay una conexion abierta y falla

1. Fetch join
```java
// En tu Repository o DAO 
@Query("SELECT p FROM Purchase p JOIN FETCH p.itemServiceList WHERE p.id = :id") Purchase findPurchaseWithItems(@Param("id") Long id);
```
2. @EntityGraph
```java
@EntityGraph(attributePaths = {"itemServiceList", "route"})
@Query("SELECT p FROM Purchase p WHERE p.id = :id")
Purchase findDetailedPurchaseById(Long id);
```
3. Mantener la sesion
   La PEOR de todas pero podes mantener una sesion abierta con la db constantemente y asi siempre podes recuperar. NO RECOMIENDO :)
4. @Transactional
```java
   //en el service
   @Transactional public Purchase getPurchase(Long id) { Purchase p = repository.findById(id).orElseThrow(); Hibernate.initialize(p.getItemServiceList()); // Fuerza la carga mientras hay conexión 
   return p; }
```
En criollo, en el service le decis dame la lista de itemService al hacer esta consulta particular al service -> esta es la que banco porque no tenes que manejar la query con HQL