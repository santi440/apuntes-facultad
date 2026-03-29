¿Qué es JPA? ¿Qué nos permite definir? ¿Por qué se dice que es una especificación y no
una implementación?

**JPA** (_Java Persistence API_): Es una **capa de abstracción** estándar de Java que define cómo se deben persistir los objetos en una base de datos relacional. Su objetivo es que el desarrollador interactúe con objetos Java (Entidades) en lugar de escribir sentencias SQL manuales para cada operación.

JPA nos permite "mapear" (asociar) la estructura de nuestro código con la estructura de la base de datos mediante **anotaciones** (o XML). Concretamente, permite definir:
- **Entidades:** Qué clases del diagrama (ej. `Purchase`, `Route`, `User`) se transformarán en tablas.
- **Mapeo de Atributos:** Qué atributos de la clase corresponden a qué columnas de la tabla (y sus tipos, longitudes, si permiten nulos, etc.).
- **Identidad:** Cuál es la Clave Primaria (`@Id`) y cómo se genera (ej. `@GeneratedValue`).
- **Relaciones:** La multiplicidad entre clases (`@OneToMany`, `@ManyToOne`, `@ManyToMany`) y cómo se deben comportar (si la carga es _Lazy_ o _Eager_).
- **Herencia:** Qué estrategia de persistencia usar para jerarquías como  `User/DriverUser/TourGuideUser` (ej. `@Inheritance(strategy = InheritanceType.JOINED)`).
**Es una Especificación:** Porque es solo un **conjunto de reglas, interfaces y directrices** (documentación técnica) que definen _cómo_ debería funcionar la persistencia en Java.    
**No es una Implementación:** Porque JPA por sí solo **no tiene código ejecutable** que realice la persistencia. Para que funcione, necesitas un **Proveedor de Persistencia** (una librería real).

> **Analogía clásica:** JPA es la interfaz (el "volante y los pedales") y la implementación es el motor (Hibernate, EclipseLink o OpenJPA). Vos aprendés a manejar con JPA, y si mañana decidís cambiar de "motor" (de Hibernate a EclipseLink), tu código no debería cambiar porque ambos respetan la misma especificación.
