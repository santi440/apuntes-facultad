¿Para qué sirve la propiedad fetch en las anotaciones de relación? ¿Cuáles son los
valores posibles? ¿Cuál es el valor por defecto para @OneToMany, para @ManyToOne y
para @ManyToMany?

La propiedad `fetch` en JPA sirve para determinar **cuándo** se deben cargar los datos de la entidad relacionada desde la base de datos a la memoria de la aplicación. Es una de las herramientas más importantes para optimizar el rendimiento del sistema.
Define la estrategia de carga:
- **Eficiencia:** Evita cargar objetos pesados o listas enormes si no se van a utilizar en ese momento.
- **Rendimiento:** Ayuda a prevenir el problema de las "N+1 consultas", controlando si los datos se traen en un solo `JOIN` o en consultas separadas bajo demanda.
- **`FetchType.LAZY` (Carga diferida/perezosa):** Los datos de la relación **no se cargan** inmediatamente. JPA crea un "proxy" (un objeto vacío). La consulta a la base de datos solo se realiza la primera vez que accedes al atributo (ej. al hacer un `getRelationship()`).
    - _Uso ideal:_ Colecciones (`List`, `Set`) o datos que no siempre se necesitan.    
- **`FetchType.EAGER` (Carga temprana/ansiosa):** Los datos de la relación **se cargan al mismo tiempo** que la entidad principal. Cuando recuperas una entidad, Hibernate realiza un `JOIN` o una consulta adicional automática para traer los datos relacionados.
    - _Uso ideal:_ Relaciones donde el dato es pequeño y casi siempre se utiliza.

|**Anotación**|**Valor por Defecto**|**Razón**|
|---|---|---|
|**`@ManyToOne`**|**`FetchType.EAGER`**|Se asume que traer un solo objeto relacionado no es costoso.|
|**`@OneToOne`**|**`FetchType.EAGER`**|Al ser un único objeto, se recupera por defecto.|
|**`@OneToMany`**|**`FetchType.LAZY`**|Una lista puede tener miles de registros; cargarla por defecto arruinaría el rendimiento.|
|**`@ManyToMany`**|**`FetchType.LAZY`**|Las tablas intermedias y las colecciones suelen ser costosas de procesar.|

Ayuda memoria si de la derecha hay mucho lazy 