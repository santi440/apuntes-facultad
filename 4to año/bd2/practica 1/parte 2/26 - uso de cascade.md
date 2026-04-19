 ¿Cuál es el comportamiento por defecto cuando no se define cascade? ¿Cuál es la
finalidad general de CASCADE? Proponga un caso del modelo donde definir un
CASCADE inadecuado podría traer problemas a la consistencia de la base de datos.

- **Comportamiento por defecto:** Ninguna operación se propaga (`null`). Si guardas un padre con hijos nuevos sin cascada, JPA lanzará una excepción porque los hijos no existen en la DB.
- **Finalidad:** Simplificar la programación al evitar tener que llamar manualmente al repositorio por cada objeto relacionado, manteniendo la integridad referencial.
- **Caso de riesgo:** Si en la relación **`Route -> Stop`** usas `CascadeType.REMOVE`, pero por error lo configurás al revés (**`Stop -> Route`**), al borrar una simple parada (Stop) podrías terminar borrando la Ruta completa, dejando huérfanas a todas las demás paradas y compras asociadas.