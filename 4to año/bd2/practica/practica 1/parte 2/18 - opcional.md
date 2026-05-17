La relación Purchase -> Review es opcional (0..1). Implementar el mapeo de ambos lados.
¿Cómo se representa la opcionalidad en JPA?
La opcionalidad se gestiona principalmente a través del atributo **`optional`** dentro de la anotación de la relación:
1. **`optional = true` (por defecto):** Indica que la relación puede ser `null`. En la base de datos, la columna de la clave foránea permitirá valores nulos.
2. **`optional = false`:** Indica que la relación es obligatoria (equivalente a un `1` en lugar de `0..1`).

En este caso, como el diagrama marca **0..1**, usaremos `optional = true` (o simplemente lo omitimos, ya que es el valor predeterminado).