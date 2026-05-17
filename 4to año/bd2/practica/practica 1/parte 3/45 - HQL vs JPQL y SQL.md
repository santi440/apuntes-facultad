 ¿Que diferencia hay entre HQL/JPQL y SQL nativo? ¿Qué entidades, atributos y
relaciones entienden HQL/JPQL que SQL no conoce directamente

### La diferencia fundamental: ¿A qué le hablo?

- **SQL (Structured Query Language):** Es un lenguaje orientado a la **Base de Datos Relacional**. Tú le hablas a las **tablas, columnas y filas**. SQL no sabe qué es un objeto Java; solo entiende de relaciones matemáticas entre tablas.
- **HQL/JPQL (Hibernate/Java Persistence Query Language):** Es un lenguaje orientado al **Modelo de Objetos**. Tú le hablas a las **Clases y sus Atributos**. Hibernate se encarga de traducir ese "lenguaje de objetos" al SQL específico del motor que estés usando (MySQL, PostgreSQL, etc.).

### Herencia
- **En HQL:** Puedes hacer `from User` y Hibernate es lo suficientemente inteligente para traerte tanto a los usuarios comunes como a los conductores, haciendo los _joins_ o uniones necesarias por detrás.
- **En SQL:** Tendrías que saber exactamente cómo están repartidas las tablas y hacer los `JOIN` a mano.
### Relaciones: El poder de la navegación
HQL entiende las relaciones de una forma que SQL no puede directamente:

- **Navegación por puntos (Dot Notation):** En HQL puedes hacer: `select p.user.username from Purchase p` HQL entiende que `p` tiene un objeto `User` y que ese objeto tiene un `username`. **SQL no entiende esto**; en SQL nativo estás obligado a hacer un `JOIN` explícito entre la tabla `Purchase` y la tabla `User`.
- **Colecciones:** HQL entiende que un objeto puede tener una `List<Stop>`. Puedes preguntar por el tamaño de esa lista directamente en el query (`where size(r.stops) > 5`). En SQL nativo, esto requeriría un `GROUP BY` y un `COUNT`.
- **Polimorfismo:** Si haces un query sobre una interfaz o clase base, HQL conoce todas las implementaciones posibles. SQL es rígido: le pides una tabla y te da esa tabla.

|**Característica**|**SQL Nativo**|**HQL / JPQL**|
|---|---|---|
|**Objetivo**|Tablas y Columnas|Entidades y Atributos|
|**Portabilidad**|Depende del motor (Dialecto)|Independiente (Hibernate traduce)|
|**Herencia**|No la conoce|La maneja automáticamente|
|**Relaciones**|Requiere `JOIN` manual|Permite navegación `objeto.asociacion`|
|**Case Sensitivity**|Depende de la DB|Sensible a los nombres de clases Java|