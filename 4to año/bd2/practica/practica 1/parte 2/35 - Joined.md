Reimplementar el mapeo de la misma jerarquía usando la estrategia JOINED.
y) Ejecutar los tests y analizar el DDL: ¿cuantas tablas aparecen? ¿Qué FK existe entre
ellas?
3 una por cada clase (user, driver y tourGuide)
un id de usuario en driver y tourGuide
z) Comparar el SQL generado por Hibernate al cargar un DriverUser en JOINED vs. en
SINGLE_TABLE. ¿Cómo difiere?
- **En SINGLE_TABLE:** Hibernate genera un `SELECT` simple sobre una única tabla: `SELECT * FROM user WHERE id = ? AND user_type = 'DRIVER'`. Es una operación de lectura directa y extremadamente rápida.
- **En JOINED:** Hibernate debe reconstruir el objeto realizando un **JOIN**: `SELECT * FROM user t1 INNER JOIN driver_user t2 ON t1.id = t2.user_id WHERE t1.id = ?`. Si la consulta fuera polimórfica (traer todos los usuarios), Hibernate tendría que hacer `LEFT JOIN` con todas las tablas hijas existentes, lo que aumenta la complejidad de la consulta.

aa) ¿Cuáles son las ventajas y desventajas de JOINED para este modelo?
**Ventajas:**
- **Normalización:** No hay celdas con valores `NULL` innecesarios. Cada tabla contiene solo lo que le corresponde.
- **Integridad Estricta:** Podés aplicar restricciones `NOT NULL` a nivel de base de datos en las columnas `expedient` o `education`, ya que están en tablas separadas.
- **Evolución:** Si en el futuro las subclases crecen mucho en atributos, la tabla padre permanece limpia y pequeña.

**Desventajas:**
- **Performance:** Las consultas son más lentas debido a la necesidad de realizar `JOINs` para recuperar una sola entidad.
- **Complejidad de Inserción:** Para crear un chofer, Hibernate debe ejecutar dos sentencias `INSERT` (una en cada tabla) dentro de la misma transacción.