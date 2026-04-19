Para la relación Purchase -> ItemService (composición uno-a-muchos):
h) ¿Qué anotaciones se necesitan en cada lado?
Para una relación **bidireccional** (la más común y eficiente), se utilizan las siguientes anotaciones:
- **En la clase `Purchase` (Lado Padre/Uno):** Se usa `@OneToMany`. Como es una composición, es fundamental incluir `cascade = CascadeType.ALL` y `orphanRemoval = true` para que los ítems dependan totalmente del ciclo de vida de la compra.
- **En la clase `ItemService` (Lado Hijo/Muchos):** Se usa `@ManyToOne` sobre el atributo que referencia a la compra. También se suele añadir `@JoinColumn` para especificar el nombre de la clave foránea.
i) ¿Qué columna o tabla aparece en la base de datos para representar esta relación?
En una relación `@ManyToOne` estándar, **no aparece una tabla intermedia**. Lo que se genera es:
- **Una columna de Clave Foránea (Foreign Key):** Esta columna se crea en la tabla de la entidad "muchos" (en la tabla `item_service`).   
- Por defecto, JPA la nombrará algo como `purchase_id`, y servirá para apuntar a la clave primaria de la tabla `purchase`.
j) ¿Qué es mappedBy y en qué lado de la relación va? ¿Qué ocurre si se omite en
ambos lados?
- **Qué es:** `mappedBy` le indica a JPA que la relación ya está definida por el otro lado. Establece quién es el **dueño de la relación** (el que tiene la clave foránea en la BD).
- **Dónde va:** Siempre va en el lado "Uno" (`Purchase`), dentro de la anotación `@OneToMany`. Su valor es el nombre del atributo en la clase hija (ej. `mappedBy = "purchase"`).
- **Si se omite en ambos lados:** JPA asumirá que ambas entidades son independientes. En lugar de una simple clave foránea, **creará una tabla de unión (join table)** innecesaria para conectar ambas entidades, lo cual es menos eficiente y no refleja correctamente una relación uno-a-muchos simple.
k) ¿Es esta relación bidireccional o unidireccional según el diagrama? ¿Cómo se refleja
en el código Java?
Segun el diagrama es bidireccional-  - para poder acceder a la compra desde el ítem.