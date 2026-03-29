Investigue sobre los métodos de Session: session.save(), session.persist(),
session.merge() y session.saveOrUpdate(). ¿Qué permite hacer cada uno y cuál es la
diferencia entre ellos? Indicar en qué estado debe estar un objeto para usar cada uno
correctamente

# session.persist();
Es el método estándar de **JPA**.
- **Qué hace:** Indica que una instancia debe hacerse persistente. No garantiza que el `INSERT` ocurra inmediatamente (espera al _flush_ o al _commit_). No retorna nada (`void`).
- **Estado inicial:** El objeto debe estar en **Transient**.
- **Diferencia clave:** Si lo usás con un objeto que ya tiene ID (Detached), suele lanzar una excepción porque espera crear un registro nuevo.

# session.save();
Es el método original y "nativo" de **Hibernate**.
- **Qué hace:** Muy similar a `persist()`, pero **retorna el identificador** (`Serializable`) generado inmediatamente. Esto obliga a Hibernate a ejecutar el insert o consultar la secuencia en ese mismo instante para obtener el ID.
- **Estado inicial:** El objeto debe estar en **Transient**.
- **Diferencia clave:** A diferencia de `persist()`, se puede usar fuera de una transacción (aunque no es buena práctica) y devuelve el ID generado.
# session.merge();
Es el método de **JPA** para lidiar con objetos que vienen de sesiones cerradas.
- **Qué hace:** Toma un objeto "suelto", copia sus valores en una instancia **Managed** (la busca en la base si no está en el caché) y devuelve esa instancia gestionada. **Ojo:** El objeto original que le pasaste sigue estando _Detached_; tenés que usar el que te devuelve el método.
- **Estado inicial:** El objeto suele estar en **Detached** (aunque funciona con Transient creando uno nuevo).
- **Diferencia clave:** No "reengancha" el objeto original, sino que crea una copia gestionada.
==por qué? para que quiero ambos objetos, el que me devuelve y el que existia antes==
# session.saveOrUpdate();
Es un método "comodín" propio de **Hibernate**.
- **Qué hace:** Hibernate mira el objeto: si no tiene ID, hace un `save()`; si ya tiene un ID, hace un `update()`. Es muy útil cuando no sabés si el usuario está creando una `Purchase` nueva o editando una existente.
- **Estado inicial:** Puede estar en **Transient** (sin ID) o **Detached** (con ID).
- **Diferencia clave:** A diferencia de `merge()`, este método sí intenta convertir al objeto original en **Managed**
==si es tan comodin, no podria literalmente hacer todo con esto y fue?==