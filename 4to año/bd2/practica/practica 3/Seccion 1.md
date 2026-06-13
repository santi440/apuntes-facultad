1. ¿Cuales de los siguientes conceptos de RDBMS existen en MongoDB? En caso de no
existir, ¿hay alguna alternativa? ¿Cual es?
• Base de Datos -> Existe, funciona igual es un contenedor fisico
• Tabla / Relacion -> En mongo se lo llama coleccion.Agrupación de documentos. A diferencia de las tablas, no imponen un esquema rígido.
• Fila / Tupla -> Documento. La unidad básica de datos. Se guarda en formato BSON (representación binaria de JSON).
• Columna -> hablamos de campo. Los pares clave-valor dentro del documento. Pueden contener datos simples, arrays u otros subdocumentos.
2. ¿Existen claves foraneas en MongoDB? ¿Que diferencias existen con las bases de datos
de tipo relacional?

**No existen las restricciones de claves foráneas (Foreign Keys) nativas** en MongoDB de la misma manera que en un RDBMS.
La responsabilidad de mantener la coherencia y la integridad referencial recae enteramente en la capa de aplicación (por ejemplo, manejando la lógica de validación o los borrados en cascada directamente desde tu API en Flask). Si bien puedes relacionar documentos guardando el `_id` de uno en otro, MongoDB no vigilará que ese `_id` siga existiendo. Para unificar datos en lectura, se usa el operador `$lookup` dentro de un _Aggregation Pipeline_, aunque abusar de esto puede impactar el rendimiento.

3. Para acelerar las consultas, MongoDB tiene soporte para indices. ¿Que tipos de indices soporta?

	Para evitar escaneos completos de colecciones (_collection scans_), MongoDB soporta varios tipos de índices estructurados típicamente en árboles B (B-Trees):
- **Un solo campo (Single Field):** Indexa los valores de un único campo, ya sea en orden ascendente o descendente.
- **Compuestos (Compound):** Agrupa múltiples campos en un solo índice. El orden en que se declaran es vital (se leen de izquierda a derecha).
- **Multikey:** Diseñados específicamente para indexar el contenido de los arrays. MongoDB crea una entrada de índice separada para cada elemento del array.
- **Geoespaciales (Geospatial):** Optimizados para calcular distancias o intersecciones en mapas (coordenadas planas `2d` o esféricas `2dsphere`).
- **De Texto (Text):** Permiten búsquedas complejas de strings dentro de textos largos, ignorando "stop words" (artículos, preposiciones) y soportando múltiples idiomas.
- **Hashed:** Indexan el valor hash de un campo. Son fundamentales para distribuir datos de manera uniforme a través de múltiples servidores en configuraciones particionadas (_sharding_), algo muy útil cuando escalas arquitecturas en entornos distribuidos.

4. En MongoDB existen dos tipos de vistas. Explicar brevemente cuales son y que
diferencias existen entre ellas. Ademas, mencionar algunos casos donde podria
utilizarlas.

Las vistas se construyen a partir de consultas de agregación (_Aggregation Pipelines_) y se dividen en dos categorías:

**A. Vistas Estándar (Standard Views)**
- **Qué son:** Son de solo lectura y se calculan en tiempo real cada vez que las consultas. No ocupan espacio en disco (solo se guarda la definición de la vista).
- **Casos de uso:** Excelentes para la capa de presentación y la seguridad. Si necesitas enviar datos a una interfaz en Vue.js, puedes usar una vista estándar para excluir campos sensibles (como contraseñas o tokens) de la colección de usuarios, exponiendo solo el perfil público.
   
**B. Vistas Materializadas Bajo Demanda (On-Demand Materialized Views)**
- **Qué son:** Generan resultados que _sí_ se escriben físicamente en el disco en otra colección (utilizando etapas como `$merge` o `$out`). No se actualizan solas; debes ejecutar el pipeline para refrescarlas.
- **Casos de uso:** Ideales para analítica de datos pesada. Si tienes pipelines que procesan millones de registros para generar métricas mensuales, recalcular eso en vivo destruiría el rendimiento. La vista materializada guarda el resultado pre-calculado para que las consultas de reportes sean instantáneas.

4. Los documentos de una coleccion pueden diferir en la cantidad y tipos de campos.
¿Existen algunas formas de validar los elementos a insertar en una coleccion para evitar
esta disparidad?

Aunque MongoDB es famoso por ser "schema-less" (o más bien, de esquema dinámico), **sí existen formas nativas de validar los datos** para evitar que una colección se convierta en un caos estructural.

Esto se implementa a través de **JSON Schema Validation** (`$jsonSchema`). A nivel de colección, puedes definir reglas estrictas que el motor evaluará antes de permitir un `insert` o `update`:

- **Campos Requeridos:** Forzar que ciertos campos (ej. `email`, `fecha_creacion`) siempre estén presentes.
- **Tipado Estricto:** Asegurar que la `edad` sea un entero (_int_) y no un string.
- **Restricciones de Valores:** Exigir que un string cumpla con una expresión regular (Regex) o que un número caiga dentro de un rango específico.
- **Niveles de Validación:** Puedes configurar si un documento que falla la validación es rechazado por completo (`strict`) o si simplemente se registra un warning en los logs del sistema (`moderate`), lo cual es útil cuando auditas o migras bases de datos legacy.

4. MongoDB tiene soporte para transacciones, pero no es igual que el de los RDBMS. ¿Cual es el alcance de una transaccion en MongoDB?

MongoDB fue diseñado originalmente bajo la premisa de que **la actualización de un único documento es siempre atómica**.

Sin embargo, a medida que los sistemas evolucionaron, MongoDB incorporó soporte para transacciones ACID reales.

**El alcance de una transacción:**
- Una transacción en MongoDB permite ejecutar múltiples operaciones de lectura y escritura de forma atómica a través de **múltiples documentos, múltiples colecciones e incluso múltiples bases de datos**, siempre y cuando todo resida dentro del mismo clúster (ya sea un _Replica Set_ o un _Sharded Cluster_).
- Si una de las operaciones falla, toda la transacción hace un _rollback_ (se deshace). Si todas tienen éxito, se hace un _commit_ (se aplican todas juntas).
**La gran diferencia con RDBMS:** En SQL, normalizamos al máximo y usamos transacciones constantemente para actualizar tres o cuatro tablas al mismo tiempo (ej. `Facturas`, `Items_Factura`, `Inventario`). En MongoDB, el motor está optimizado para operaciones de un solo documento. Las transacciones multidocumento implican un bloqueo (_lock_) y un alto costo de rendimiento. La recomendación oficial es **no depender de las transacciones para el diseño diario**, sino modelar los datos para que las actualizaciones conjuntas ocurran dentro de un mismo documento (embebido) y reservar las transacciones solo para casos críticos, como transferencias de saldos en sistemas financieros.

5. Las relaciones entre documentos en MongoDB pueden establecerse mediante documentos embebidos o referencias. Investigar como se implementa cada una y analizar las ventajas y desventajas de cada una, comparandola con la forma estandar de establecer relaciones en una base de datos relacional.

#### A. Documentos Embebidos (Desnormalización)

La información relacionada se guarda dentro del mismo documento principal, típicamente como un sub-documento JSON o un array de sub-documentos.

- **Implementación:**
    ```JSON
    {
      "_id": ObjectId("..."),
      "nombre": "Museo Histórico",
      "ubicacion": {
        "calle": "Av. 50",
        "ciudad": "La Plata"
      }
    }
    ```
- **Ventajas:** Lecturas ultra rápidas. Con una sola consulta traes toda la información necesaria para el frontend. Las actualizaciones de todos esos datos son atómicas.
- **Desventajas:** Cada documento tiene un límite estricto de **16 MB**. Si embebes un array que crece sin límites (ej. _logs_ de visitas), la aplicación fallará al alcanzar el límite. Además, si embebes datos que se repiten en muchos documentos y esos datos cambian, tendrás que hacer actualizaciones masivas.
#### B. Referencias (Normalización)

Se almacena únicamente el `_id` del documento relacionado, el cual vive en su propia colección.
- **Implementación:**    
    ```JSON
    // Colección "sitios"
    {
      "_id": ObjectId("..."),
      "nombre": "Fuerte de San Miguel",
      "categoria_id": ObjectId("5f1b...")
    }
    ```
- **Ventajas:** Evita la duplicación de datos. Soluciona el problema de los arrays infinitos y respeta el límite de 16MB. Es ideal para relaciones muchos a muchos o jerarquías complejas.
- **Desventajas:** Para obtener los datos completos, la API (por ejemplo, tu backend) debe hacer múltiples peticiones a la base de datos, o debes usar el operador `$lookup` en un _pipeline_ de agregación, lo cual consume más CPU y tiempo de respuesta.
6. Tomando como referencia el modelo de los trabajos practicos anteriores y suponiendo que este podria mapearse a una base de datos en MongoDB, proponer algunos casos donde la relacion seria conveniente mapearla como referencia y otros como documentos embebidos. Justificar la eleccion

| Relación                                 | Estrategia     | Motivo clave                                                           |
| ---------------------------------------- | -------------- | ---------------------------------------------------------------------- |
| `Purchase` → `Review`                    | **Embebido**   | OneToOne, ciclo de vida compartido, no se consulta sola                |
| `Purchase` →`ItemService`                | **Embebido**   | Ítems de línea, array acotado, snapshot de precio al momento de compra |
| `Route` → `Stop`                         | **Embebido**   | Parte de la definición de la ruta, cardinalidad acotada                |
| `Purchase` → `Route`                     | **Referencia** | Ruta mutable compartida por muchas purchases                           |
| `Purchase` → `User`                      | **Referencia** | Usuario con vida propia, datos actualizables                           |
| `Route` → `DriverUser` / `TourGuideUser` | **Referencia** | ManyToMany real, personas asignadas a múltiples rutas                  |
| `Service` → `Supplier`                   | **Referencia** | Entidad legal independiente, muchos servicios por supplier             |
Regla general aplicada: se embebe cuando hay un solo propietario natural, la cardinalidad es acotada y el acceso es siempre conjunto. Se usa referencia cuando la entidad es mutable, compartida entre muchos documentos, o tiene identidad y vida propias fuera del contexto de la  relación.