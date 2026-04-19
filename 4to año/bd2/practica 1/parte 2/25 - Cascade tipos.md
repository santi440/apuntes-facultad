Enumerar todos los valores de CascadeType y explicar qué operación propaga
cada uno sobre las entidades relacionadas

| **Valor**     | **Operación que propaga**                                                       |
| ------------- | ------------------------------------------------------------------------------- |
| **`PERSIST`** | Al guardar el padre (`persist()`), se guardan automáticamente los hijos nuevos. |
| **`MERGE`**   | Al actualizar el padre (`merge()`), se actualizan los cambios en los hijos.     |
| **`REMOVE`**  | Al eliminar el padre, se eliminan automáticamente todos sus hijos.              |
| **`REFRESH`** | Al recargar el padre desde la DB, se recargan también los hijos.                |
| **`DETACH`**  | Al desconectar el padre del contexto de persistencia, se desconectan los hijos. |
| **`ALL`**     | Engloba todas las operaciones anteriores.                                       |
