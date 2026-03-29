¿Qué significa que JPA use persistencia por alcance (persistence by reachability)? ¿Qué
consecuencia tiene si un objeto referenciado no está todavía persistido?

Persistencia por alcance: si un objeto ya está en estado **Managed** (gestionado por Hibernate) y tiene una referencia a otro objeto, ese segundo objeto también debería volverse persistente automáticamente. Se "propaga" a través de las asociaciones del modelo de objetos. En lugar de tener que llamar manualmente a `session.persist()` para cada objeto individual, JPA puede "navegar" por el grafo de objetos y guardar todo lo que esté conectado.
Para que esto funcione en JPA, debemos definir explícitamente la **cascada**. Si no lo hacemos, JPA no asumirá por defecto que debe persistir los objetos relacionados por seguridad.
```java
@OneToMany(mappedBy = "purchase", cascade = CascadeType.PERSIST) 
private List<ItemService> items;
```
Sino esta configurado de esta forma entonces lanza la  excepción: `TransientObjectException`
```java
org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing
```
Por ejmplo si quiero hacer un Purchase y el User que la hizo no esta persistido, no tengo el id para la FK. La solución es a mano hago las persist en el orden que van o le pongo el cascade y que lo haga automatico