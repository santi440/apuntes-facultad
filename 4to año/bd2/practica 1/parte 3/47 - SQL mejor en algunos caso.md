¿En qué casos conviene usar una consulta SQL nativa en lugar de HQL/JPQL? Describir
al menos un caso concreto del modelo donde esto sería necesario

### ¿Cuándo conviene usar SQL Nativo?
- **Uso de funciones específicas del motor:** Si necesitas funciones que no están estandarizadas en JPQL o en el dialecto de Hibernate (como funciones geoespaciales avanzadas de PostGIS o funciones JSON de MySQL 8.0).
- **Optimización de performance (Queries complejos):** Para reportes pesados donde Hibernate genera `JOINs` innecesarios o subconsultas ineficientes. El SQL nativo te permite usar `HINTS` de optimización o `Common Table Expressions` (CTE) que HQL no siempre soporta bien.
- **Operaciones masivas (Bulk):** Si bien HQL soporta `UPDATE` y `DELETE`, a veces es más eficiente ejecutar un `INSERT INTO ... SELECT` nativo para mover millones de filas sin cargar los objetos en el contexto de persistencia (evitando saturar la memoria).
- **Llamadas a Procedimientos Almacenados (Stored Procedures):** Si la lógica de negocio reside en la base de datos, SQL nativo es la vía directa para ejecutarlos.