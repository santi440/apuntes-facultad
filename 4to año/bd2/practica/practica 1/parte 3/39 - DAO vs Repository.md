Definir el patrón DAO (Data Access Object) y el patrón Repository. ¿Cuál es la diferencia
conceptual más importante entre ambos? ¿En qué se diferencia su rol dentro de la
arquitectura de la aplicación?
### 1. El Enfoque (Nivel de Abstracción)
- **DAO (Data Access Object):** Es una abstracción de la **infraestructura de datos**. Su objetivo es ocultar la complejidad de la base de datos (ya sea SQL, archivos o una API externa). El DAO suele ser un "mapeo" casi directo de las tablas. Si tenés una tabla `USERS`, tenés un `UserDAO`.
- **Repository:** Es una abstracción del **Dominio (Negocio)**. Su objetivo es que el programador sienta que está trabajando con una lista de objetos en memoria. El Repositorio no habla el lenguaje de "tablas", sino el lenguaje de "entidades".
### 2. El Grano (Granularidad)
- **DAO:** Suele ser de **grano fino**. Es común tener un DAO por cada tabla de la base de datos.
    - _Ejemplo:_ Si tenés `Purchase` e `ItemService`, tendrías un `PurchaseDAO` y un `ItemServiceDAO`.
- **Repository:** Es de **grano grueso**. Se rige por el concepto de **Aggregate Root** (Raíz de Agregado). Un Repositorio gestiona un grupo de objetos que tienen sentido juntos.
    - _Ejemplo:_ Solo tendrías un `PurchaseRepository`. Los `ItemService` se guardan o recuperan a través del repositorio de la compra, porque no tienen sentido existir por sí solos.
### 3. La Interfaz y el Lenguaje
- **DAO:** Sus métodos suelen reflejar operaciones de base de datos: `insert()`, `update()`, `delete()`, `findById()`.
- **Repository:** Sus métodos reflejan la semántica de una colección: `add()`, `remove()`, `get()`, `matching(Criteria)`.
    
### Comparativa Visual en la Arquitectura

|Característica|**DAO**|**Repository**|
|---|---|---|
|**¿Dónde vive?**|En la capa de Datos (Persistence).|En la capa de Dominio o Servicio.|
|**¿A qué se parece?**|A una interfaz para ejecutar SQL.|A una `List<Entity>` persistente.|
|**Dependencia**|Depende de la tecnología (JDBC, Hibernate).|Intenta ser independiente de la tecnología.|
|**Uso de Criterios**|Suele devolver lo que la tabla ofrece.|Filtra objetos según reglas de negocio.|

### ¿Por qué en Hibernate la línea es borrosa?
**Hibernate ya es, en sí mismo, un motor de persistencia potente.** Cuando usás `session.get(User.class, id)`, Hibernate está haciendo el trabajo de "mapeador". Por eso, en muchas aplicaciones modernas de Spring, lo que implementamos es técnicamente un **Repository** (porque devolvemos entidades y trabajamos con el contexto de persistencia), pero a veces lo llamamos DAO por costumbre.

Si quisieras cambiar de MySQL a una base de datos NoSQL como MongoDB, cambiarías la implementación del **DAO** (el cómo), pero la interfaz del **Repository** (el qué) debería mantenerse casi idéntica para el resto de la aplicación.