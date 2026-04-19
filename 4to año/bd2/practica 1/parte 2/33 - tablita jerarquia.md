Describir las tres estrategias de mapeo de herencia. Para cada una indicar qué tablas se
crean, si aparece columna discriminadora y cómo resuelve Hibernate una consulta
polimórfica. Completar la tabla:

| **Aspecto**                     | **SINGLE_TABLE**                                  | **JOINED**                                         | **TABLE_PER_CLASS**                                                             |
| ------------------------------- | ------------------------------------------------- | -------------------------------------------------- | ------------------------------------------------------------------------------- |
| **Tablas en la BD**             | Una sola (ej. `users`).                           | Una por clase (`users`, `drivers`, `guides`).      | Una por subclase concreta con todos los atributos (los propios y los heredados. |
| **Columna discriminadora**      | **Sí** (Obligatoria para distinguir tipos).       | Opcional (se puede deducir por el JOIN).           | **No** necesaria.                                                               |
| **NULLs en subclases**          | **Sí**, muchos (las columnas de otras subclases). | **No**, las tablas están normalizadas.             | **No**, cada tabla es completa.                                                 |
| **Consulta "todos los Users"**  | Muy rápida (un solo `SELECT *`).                  | Lenta (requiere muchos `OUTER JOIN`).              | Lenta (requiere `UNION` de todas las tablas).                                   |
| **Cargar un DriverUser por ID** | Rápida (un solo `SELECT` por ID).                 | Media (requiere `JOIN` entre `users` y `drivers`). | Rápida (solo consulta la tabla `drivers`).                                      |
| **Integridad referencial**      | Muy buena.                                        | Excelente (FKs directas a subclases).              | **Difícil** (otras tablas no pueden apuntar a la superclase fácilmente).        |
| **Performance lectura simple**  | Máxima.                                           | Media.                                             | Alta.                                                                           |
| **Agregar nueva subclase**      | Solo requiere agregar columnas a la tabla.        | Requiere crear una tabla nueva.                    | Requiere crear una tabla nueva.                                                 |
- **¿Cómo resuelve Hibernate una consulta polimórfica?**
    - En **SINGLE_TABLE**, simplemente filtra por la columna discriminadora (`WHERE dtype = 'Driver'`).
    - En **JOINED**, realiza un `LEFT JOIN` con todas las tablas de las subclases para ver qué atributos "rellenar".
    - En **TABLE_PER_CLASS**, debe ejecutar un `UNION` de todos los `SELECT` de cada tabla de subclase, lo cual es costoso si la jerarquía es grande.
- **¿Por qué SINGLE_TABLE es la más usada?** Aunque genera muchos nulos, es la más eficiente para el motor de base de datos porque evita los `JOINs` y `UNIONs`, que son el principal cuello de botella en aplicaciones de alto tráfico.
- **¿Cuándo usar JOINED?** Cuando la integridad referencial es crítica y las subclases tienen demasiados atributos distintos, haciendo que la `SINGLE_TABLE` sea inmanejable por la cantidad de columnas vacías.