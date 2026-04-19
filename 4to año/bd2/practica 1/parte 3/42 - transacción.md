¿Que es una transacción en el contexto de Hibernate? ¿Por qué es necesaria? ¿Qué
ocurre si se realizan operaciones de escritura sin una transacción activa?
Una transacción es una **unidad lógica de trabajo** que agrupa una serie de operaciones (lecturas, inserciones, actualizaciones o eliminaciones). Se rige por el principio **ACID**:
- **Atomicidad:** O todo se ejecuta con éxito o no se ejecuta nada.
- **Consistencia:** La base de datos pasa de un estado válido a otro estado válido.
- **Aislamiento (Isolation):** Las operaciones de una transacción son invisibles para otras hasta que se confirman.
- **Durabilidad:** Una vez confirmada, los cambios son permanentes.
En Hibernate, una transacción está vinculada a la `Session`. Comienza con `session.beginTransaction()` y termina con un `commit()` (para guardar) o un `rollback()` (para deshacer en caso de error).

- **Integridad de los datos:** Imagina el proceso de una `Purchase` en tu modelo. Debes restar el stock de un servicio, crear el `ItemService` y generar la `Purchase`. Si el sistema falla justo después de restar el stock pero antes de crear la compra, los datos quedarían inconsistentes. La transacción asegura que si algo falla, todo vuelva atrás.
- **Gestión de Concurrencia:** Evita que dos usuarios modifiquen el mismo dato al mismo tiempo de forma caótica, bloqueando los recursos necesarios hasta que la operación termine.

Si intentas hacer un `session.save()`, `update()` o `delete()` fuera de un bloque transaccional, pueden ocurrir las siguientes situaciones dependiendo de la configuración.
1. **Excepción inmediata:** En la mayoría de las configuraciones modernas de Hibernate (especialmente con Spring), se lanzará una **`TransactionRequiredException`**. Hibernate se niega a ejecutar cambios que no estén protegidos por una transacción.
2. **Modificaciones no persistidas:** Hibernate utiliza una técnica llamada **Write-behind**. Los cambios se acumulan en la memoria (caché de primer nivel) y solo se envían a la base de datos cuando se hace un _flush_. El _flush_ suele ocurrir automáticamente al hacer el `commit`. Si no hay transacción, nunca hay `commit`, y por lo tanto, los cambios nunca se sincronizan con la base de datos.
3. **Auto-commit (Peligroso):** Si la conexión JDBC tiene el `auto-commit` en `true` y Hibernate no está gestionando la transacción, cada sentencia SQL se ejecutaría como una transacción individual. Esto destruye la atomicidad: si una operación de una serie falla, las anteriores ya quedaron grabadas y no se pueden deshacer.