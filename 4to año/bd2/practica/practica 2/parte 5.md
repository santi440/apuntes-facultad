# Borrado Lógico
El borrado lógico es una estrategia de persistencia que evita eliminar físicamente registros de la base de datos, marcándolos en cambio como inactivos. Spring Data JPA y Hibernate ofrecen mecanismos para implementarlo de forma transparente para el resto de la aplicación.
## 5.1 Concepto y estrategias
### 36. ¿Que es el borrado lógico (soft delete)? Pensar en el modelo de tours: si un usuario decide darse de baja del sistema, ¿tiene sentido eliminar físicamente su registro? ¿Qué ocurriría con todas las compras que ese usuario ha realizado? Describir por qué el borrado lógico resuelve este problema y qué ventaja ofrece frente al borrado físico en este caso concreto.

El **borrado lógico** es una técnica de persistencia que consiste en marcar un registro como "eliminado" o "inactivo" mediante una bandera o atributo en la base de datos, en lugar de borrar físicamente la fila mediante una sentencia `DELETE` de SQL. El registro permanece intacto en el disco, pero la aplicación lo trata como si ya no existiera para las operaciones cotidianas.
**El problema del borrado físico:** Si ejecutáramos un `DELETE FROM users WHERE id = ...`, el motor de la base de datos se encontraría con un dilema debido a la integridad referencial. Ese usuario tiene asociadas múltiples **compras (`Purchase`)** históricas.
- Si la relación está configurada con `ON DELETE CASCADE`, al borrar al usuario **se borrarían automáticamente todas sus compras**, destruyendo el historial financiero de la empresa y alterando reportes de ganancias y métricas de auditoría.
- Si la relación está configurada con `ON DELETE RESTRICT` (que es lo ideal por seguridad), el motor directamente **abortaría la operación** lanzando un error de violación de clave foránea, impidiendo la baja del usuario.

### Ventajas del borrado lógico en este caso concreto:
1. **Preservación del historial comercial:** Las compras siguen asociadas al ID del usuario para balances contables, estadísticas de rutas más vendidas y auditorías fiscales.
2. **Integridad Referencial Intacta:** No se rompe ninguna clave foránea ni se requiere dejar campos en `NULL` (lo cual generaría compras huérfanas).
3. **Reversibilidad (Undo):** Si el usuario se arrepiente y quiere reactivar su cuenta, el proceso es tan simple como volver a cambiar el estado de la bandera, sin necesidad de recrear el perfil desde cero.
### 37. Describir dos estrategias para implementar soft delete en JPA/Hibernate: campo booleano (active/deleted) y campo de fecha (deletedAt). ¿Qué diferencias hay entre ambas en cuanto a consultas y recuperación de datos?

|**Característica**|**Estrategia Campo Booleano (active / deleted)**|**Estrategia Campo de Fecha (deleted_at)**|
|---|---|---|
|**Definición**|Se utiliza un flag de tipo `boolean` o `tinyint` (`true`/`false` o `1`/`0`).|Se utiliza un campo de tipo `TIMESTAMP` o `DATETIME` que acepta valores `NULL`.|
|**Estado Activo**|`active = true` (o `deleted = false`)|`deleted_at IS NULL`|
|**Estado Borrado**|`active = false` (o `deleted = true`)|`deleted_at = '2026-05-17 18:30:00'`|
|**Riqueza de Datos**|Pobre. Solo sabe _si_ está borrado o no.|Alta. Almacena **cuándo** ocurrió la baja (útil para auditoría cronológica sin tablas extra).|
|**Impacto en Índices Únicos**|Complejo. Si el `email` es único y borrás lógicamente un usuario, nadie más podrá registrarse con ese email porque la fila sigue ahí.|Más flexible. En bases de datos modernas (como PostgreSQL o MySQL con trucos), se pueden crear índices únicos condicionales donde `deleted_at IS NULL`.|
### 38. ¿Que es la anotación @SQLDelete de JPA? ¿Qué permite hacer? ¿Cómo se combina con @Where para que las consultas ordinarias ignoren los registros borrados? Mostrar un ejemplo de uso sobre la entidad User

Hibernate provee un conjunto de anotaciones que permiten automatizar el borrado lógico para que el desarrollador no tenga que modificar manualmente sus repositorios ni acordarse de agregar filtros en cada consulta.
- **`@SQLDelete`:** Permite sobreescribir la sentencia SQL por defecto que Hibernate emite cuando se invoca a `session.delete()` o `repository.deleteById()`. En lugar de ejecutar un `DELETE`, le inyectamos un `UPDATE`.
- **`@Where`:** Aplica un filtro estático automático a **todas** las consultas de lectura (`select`) que se realicen sobre esa entidad (ya sea vía HQL, Criteria o Query Methods), abstrayéndonos de tener que escribir el filtro de estado de forma manual en cada query
```java
@Entity @Table(name = "users") 
// 1. Sobreescribimos el comportamiento del borrado físico por un update lógico 
@SQLDelete(sql = "UPDATE users SET active = false WHERE id = ?") 
// 2. Filtramos automáticamente para que las lecturas normales ignoren los inactivos 
@Where(clause = "active = true") 
public class User {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	@Column(nullable = false, unique = true) 
	private String username; 
	// Este atributo mapea la columna del estado lógico 
	@Column(nullable = false) 
	private boolean active = true;
	...
```
## 5.2 Implementación en el modelo
### 39. Implementar soft delete para la entidad User usando @SQLDelete y @Where. La implementación debe:
g) Agregar el campo necesario a la entidad User (campo y anotaciones).
h) Redefinir el comportamiento de delete para que marque el registro en lugar de
eliminarlo.
i) Asegurar que todas las consultas ordinarias (findAll, findById, Query Methods)
excluyan automáticamente los usuarios borrados.
### 40. La implementacion actual muestra el metodo canBeDeactivate() en User, DriverUser y TourGuideUser. El objetivo de estos métodos es evitar que se eliminen usuarios que poseen compras o asignaciones y, por tanto queden inconsistencias en la base de datos. Describa como esta nueva implementación afecta dichos métodos.

El método `canBeDeactivated()` tenía que responder dos preguntas distintas mezcladas en una:
**Pregunta 1 — integridad referencial:** ¿Puedo hacer un `DELETE` físico sin violar FK constraints?
- `User.canBeDeactivated()` → `return purchaseList.isEmpty()` — si hay compras, un DELETE fallaría por FK
- Consecuencia en `deleteUser`: dos caminos (hard delete / soft delete manual) según el resultado

**Pregunta 2 — regla de negocio:** ¿Es válido desactivar este usuario según las reglas del dominio?
- `TourGuideUser.canBeDeactivated()` → `return super.canBeDeactivated() && routes.isEmpty()` — un guía con rutas asignadas no debe desaparecer
- `DriverUser.canBeDeactivated()` — análogo

### Qué cambia con `@SQLDelete` + `@SQLRestriction`
`@SQLDelete` elimina por completo la **pregunta 1**. Nunca hay un `DELETE` físico, así que las FK nunca se violan independientemente de si hay compras. El check `purchaseList.isEmpty()` deja de tener sentido como guardián: ya no hay ruta de código que necesite saber si el usuario tiene compras para decidir si hacer un hard o soft delete — siempre es soft.

La **pregunta 2 sigue siendo válida y necesaria**. Un `TourGuideUser` soft-deleted sigue ocupando una fila en la tabla con `active = false`. La relación `Route.tourGuideList` es una FK que todavía apunta a esa fila. Si el guía desaparece de todas las consultas por efecto de `@SQLRestriction` pero sigue referenciado en las rutas, esas rutas quedan con guías "fantasma": presentes en la BD pero invisibles para la aplicación. La regla "no se puede desactivar un guía con rutas asignadas" no es una restricción de integridad de BD — es una regla de negocio que el código debe seguir aplicando.