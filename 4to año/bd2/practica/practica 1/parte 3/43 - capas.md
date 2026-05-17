¿En qué capa de la aplicación debería gestionarse la transacción: en el repositorio o en lacapa de servicio? Justificar la elección. ¿Qué ocurre si una misma operación necesita devarios accesos a la base de datos?

La transacción debe gestionarse en la capa de Servicio. 
- **Unidad de Trabajo (Atomicidad):** La capa de servicio es donde reside la **lógica de negocio**. Una operación de negocio suele implicar varios pasos. Si gestionás la transacción en el repositorio, cada llamada sería una transacción independiente. 
- **Granularidad:** El repositorio es "atómico" a nivel de datos (CRUD), pero el servicio es "atómico" a nivel de proceso.
- **Gestión de Errores:** Si algo falla en el paso 3 de un proceso de negocio, la capa de servicio puede decidir hacer un `rollback` de los pasos 1 y 2. Si las transacciones fueran por repositorio, los pasos 1 y 2 ya estarían persistidos y no podrías volver atrás fácilmente.

Gracias a la propagación de transacciones (usualmente controlada por `@Transactional`), todos esos accesos comparten la **misma sesión de Hibernate** y la **misma transacción de base de datos**.
**Si la transacción estuviera en el Repositorio:** Tendrías "transacciones fragmentadas". Se guardaría el usuario, luego la ruta, luego la compra... y si fallan los ítems, te quedaría una compra grabada en la base de datos **sin ítems**, lo cual es una inconsistencia grave (un "dato basura"). -> Atomicidad y Unidad de trabajo 