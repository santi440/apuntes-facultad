¿Qué diferencia hay entre las estrategias IDENTITY, SEQUENCE y TABLE para la
generación de IDs? ¿Cuál tiene mejor rendimiento en inserciones masivas y por qué?

| **Estrategia** | **Funcionamiento**                                                                                         | **Quién genera el ID**                                              |
| -------------- | ---------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------- |
| **IDENTITY**   | Usa una columna autoincremental de la base de datos (ej. `SERIAL` en Postgres, `AUTO_INCREMENT` en MySQL). | La **Base de Datos** al insertar.                                   |
| **SEQUENCE**   | Utiliza un objeto especial de la base de datos llamado "Secuencia" para pedir el próximo número.           | La **Base de Datos**, pero Hibernate lo pide **antes** de insertar. |
| **TABLE**      | Emula una secuencia usando una tabla común con una fila que guarda el último ID generado.                  | **Hibernate**, consultando y actualizando esa tabla.                |
==no entiendo table. Cuando lo usaria, no le veo el uso ==
==de hecho, en que caso uso algo que no sea sequence?==

#### **IDENTITY**
- **Ventaja:** Muy simple de configurar y soportada por casi todos los motores.
- **Desventaja Crítica:** **Rompe el "Batch Insert"** (inserciones masivas). Como Hibernate no sabe qué ID tendrá el objeto hasta que realmente lo inserta en la tabla, se ve obligado a ejecutar cada `INSERT` uno por uno para obtener el ID generado por la DB. No puede agrupar 50 compras en un solo paquete de red.

#### **SEQUENCE **
- **Ventaja:** Es la más eficiente. Hibernate puede pedir, por ejemplo, los próximos 50 IDs de una sola vez (usando el parámetro `allocationSize`).
- **Rendimiento:** Al ya conocer los IDs de antemano, Hibernate puede mantener los objetos en el **Persistence Context** y enviarlos todos juntos a la base de datos en un solo "batch" al final de la transacción.

#### **TABLE**
- **Ventaja:** Funciona en cualquier base de datos (incluso las que no tienen secuencias).
- **Desventaja:** Es la más lenta. Requiere bloqueos de fila en la tabla de IDs y múltiples accesos (lectura y actualización) cada vez que se pide un número. Se usa solo como último recurso por compatibilidad.

En inserciones masivas **SEQUENCE es la mejor opción**
- **Batching:** Como mencionamos, al obtener el ID **antes** de la inserción, Hibernate puede agrupar múltiples sentencias SQL en un solo envío a la base de datos.
- **Optimización de Pedidos:** Gracias al parámetro `allocationSize`, Hibernate no necesita ir a la base de datos por cada objeto. Si `allocationSize = 50`, Hibernate va una vez, reserva 50 números en su memoria interna, y reparte esos IDs a los nuevos objetos `Purchase` sin hablar con la DB hasta que se le terminen.
- **Transaccionalidad:** A diferencia de `IDENTITY`, que requiere un `INSERT` inmediato para conocer el ID, `SEQUENCE` permite mantener la inserción en el "limbo" de la memoria (Managed) hasta el mismísimo momento del `commit`