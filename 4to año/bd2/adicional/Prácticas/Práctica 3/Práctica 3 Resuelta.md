![[Pasted image 20260513194326.png]]
![[Pasted image 20260513194330.png]]
En MongoDB existen conceptos equivalentes a varios de los de un RDBMS

| RDBMS            | MongoDB       |
| ---------------- | ------------- |
| Base de Datos    | Base de Datos |
| Tabla / Relación | Colección     |
| Fila / Tupla     | Documento     |
| Columna          | Campo         |

![[Pasted image 20260513194338.png]]
No existen claves foráneas como en las DB relacionales, en MongoDB las relaciones normalmente se manejan  de dos formas:
- Referencias: guardar el `_id` de otro documento
- Documentos embebidos: Guardar directamente el documento relacionado dentro del otro
No hay mecanismos automáticos que garanticen la integridad referencial entre colecciones
La diferencia principal es que en MongoDB la integridad referencial suele quedar a cargo de la aplicación, mientras que en un RDBMS la garantiza el motor de base de datos
![[Pasted image 20260513194342.png]]
Single Field Index
- Índice sobre un único campo
Compound Index
- Índice sobre múltiples campos
Geospatial Index
- Para consulta geográficas y coordenadas
Text Index
- Para búsquedas de texto
Hashed Index
- Utilizado principalmente para sharding
Multikey Index
- Se crea automáticamente sobre arrays

![[Pasted image 20260513194347.png]]
Vistas **estándar**
- Son vistas virtuales
- No almacenan datos físicamente
- Se definen a partir de una consulta o pipeline de aggregation
- Cada vez que se consultan, MongoDB ejecuta nuevamente la consulta sobre las colecciones originales
- Son de sólo lectura
- No pueden tener índices propios
Ventajas
- Los datos siempre están actualizados
- No ocupan almacenamiento extra
Desventajas
- Las consultas pueden ser más lentas si el pipeline es complejo
Casos de uso:
- Mostrar recorridos filtrados por precio
- Ocultar ciertos campos sensibles
- Simplificar consultas complejas frecuentes

Vistas **materializadas**
- Almacenan físicamente el resultado
- Generalmente se crean usando $out o $merge en aggregation
- Deben actualizarse manualmente o regenerarse periódicamente
Ventajas
- Consultas mucho más rápidas
- Útiles para reportes y estadísticas
Desventajas
- Los datos pueden quedar desactualizados
- Requieren espacio adicional
Casos de uso:
- Reportes agregados
- Estadísticas precalculadas
- Rankings o dashboards
![[Pasted image 20260513194351.png]]
Se pueden utilizar validaciones mediante JSON Schema al crear o modificar una colección
Esto permite
- Definir campos obligatorios
- Restringir tipos de datos
- Validar rangos, tamaños o formatos
- Evitar documentos con estructuras inválidas
![[Pasted image 20260513194357.png]]
Originalmente, MongoDB garantizaba atomicidad únicamente a nivel de documento, pero actualmente soporta transacciones multi-documento y multi-colección
En un RDBMS las transacciones son centrales y se usan constantemente entre múltiples tablas
En MongoDB se prioriza el modelo de agregaciones/documentos embebidos para mantener la atomicidad dentro de un solo documento
![[Pasted image 20260513194401.png]]
**Documentos embebidos**
Consiste en almacenar directamente un documento dentro de otro
Optimizan lecturas y atomicidad
Ventajas
- Lecturas más rápidas
- Toda la información relevante se obtiene en una sola consulta
- Atomicidad garantizada sobre el documento completo
- Evita joins
Desventajas
- Duplicación de información
- Actualizaciones más difíciles si el dato se repite
- El documento puede crecer demasiado
- Limitación de 16MB por documento
Cuando conviene
- Relaciones 1 a pocos
- Datos que casi siempre se consultan juntos
- Datos poco reutilizados

**Referencias**
Consiste en guardar el `_id` de otro documento
Optimizan reutilización y escalabilidad
Ventajas
- Menor duplicación
- Mejor reutilización de datos
- Documentos más pequeños
- Más flexible para relaciones grandes
Desventajas
- Requiere múltiples consultas o $lookup
- Menor rendimiento en lecturas complejas
- No existe integridad referencial automática
Cuando conviene
- Relaciones muchos a muchos
- Datos compartidos entre muchos documentos
- Relaciones grandes o dinámicas

Comparación con RDBMS
- En una base relacional:
	- Las relaciones se manejan mediante FK y JOINs
	- La integridad referencial la garantiza el motor
	- El modelo está altamente normalizado
- En MongoDB
	- Se prioriza el acceso rápido a agregaciones de datos
	- Muchas relaciones se resuelven embebiendo información
	- Las referencias existen, pero la integridad suele manejarse desde aplicación


![[Pasted image 20260513194412.png]]
Referencia
- Las compras de un usuario
	- Un usuario puede tener muchas compras
	- Si fueran embebidas, el documento podría crecer mucho
	- Los datos del usuario se reutilizan constantemente
- Usuarios asociados a recorridos
	- Un mismo usuario puede participar en muchos recorridos
	- Evita duplicar información
- Proveedores y servicios
	- Los proveedores suelen compartirse entre múltiples servicios
	- Son entidades independientes

Documentos embebidos
- Paradas dentro de un recorrido
	- Dado que normalmente las paradas se consultan junto al recorrido
	- Permite obtener toda la información en una sola lectura
	- La cantidad de paradas suele ser pequeña
- Reseñas dentro de una compra
	- Una reseña pertenece únicamente a una compra
	- Se accede normalmente a las reseñas junto a la compra

![[Pasted image 20260513194419.png]]
No pienso descargar MongoDB local. Abajo hago un contenedor con mongo
![[Pasted image 20260514130341.png]]
Ahora uso la shell de mongo
![[Pasted image 20260514130438.png]]
Creo la db (use la selecciona y la crea cuando se inserte el primer documento)
![[Pasted image 20260514130528.png]]
Creo la colección
![[Pasted image 20260514130554.png]]

![[Pasted image 20260513194430.png]]
![[Pasted image 20260514130613.png]]

![[Pasted image 20260513194434.png]]
![[Pasted image 20260514130634.png]]
La diferencia es que el documento insertado no tiene un `_id` y el documento recuperado lo tiene

![[Pasted image 20260513194438.png]]
Como soy un culiado, tengo q copiar el json al docker e importar
![[Pasted image 20260514131426.png]]
![[Pasted image 20260513194443.png]]
![[Pasted image 20260514131621.png]]
![[Pasted image 20260513194447.png]]
![[Pasted image 20260514131728.png]]
![[Pasted image 20260513194452.png]]
![[Pasted image 20260514131812.png]]
![[Pasted image 20260513194456.png]]
![[Pasted image 20260514131850.png]]
![[Pasted image 20260513194501.png]]
![[Pasted image 20260514132033.png]]
![[Pasted image 20260513194506.png]]
![[Pasted image 20260514132100.png]]
![[Pasted image 20260513194510.png]]
![[Pasted image 20260514132232.png]]
![[Pasted image 20260513194514.png]]
![[Pasted image 20260514132325.png]]
![[Pasted image 20260513194519.png]]
![[Pasted image 20260514134235.png]]
![[Pasted image 20260513194524.png]]
![[Pasted image 20260514134504.png]]
![[Pasted image 20260513194530.png]]
![[Pasted image 20260514134839.png]]
![[Pasted image 20260513194534.png]]
![[Pasted image 20260514135102.png]]
![[Pasted image 20260513194538.png]]
![[Pasted image 20260514135203.png]]
![[Pasted image 20260513194544.png]]
![[Pasted image 20260514135246.png]]
![[Pasted image 20260513194548.png]]
![[Pasted image 20260514135518.png]]
También se puede especificar el regex explícitamente
```js
db.recorridos.find(
  { stops: { $regex: /museo/i } },
  { _id: 0, nombre: 1, stops: 1 }
)
```
![[Pasted image 20260513194552.png]]
![[Pasted image 20260514135604.png]]
![[Pasted image 20260513194603.png]]
![[Pasted image 20260513194616.png]]
![[Pasted image 20260514135926.png]]
![[Pasted image 20260513194626.png]]
![[Pasted image 20260514140228.png]]
![[Pasted image 20260513194630.png]]
![[Pasted image 20260514140419.png]]
![[Pasted image 20260513194635.png]]
Como no hay nada mayor o igual a 90.000, pongo el ejemplo con 900 para que se vea que el filtro funciona
![[Pasted image 20260514140609.png]]
![[Pasted image 20260513194639.png]]
![[Pasted image 20260514140745.png]]
![[Pasted image 20260513194643.png]]
![[Pasted image 20260514141002.png]]
![[Pasted image 20260513194647.png]]
![[Pasted image 20260514141035.png]]
![[Pasted image 20260513194651.png]]
![[Pasted image 20260514141205.png]]
![[Pasted image 20260513194655.png]]
![[Pasted image 20260514141314.png]]
Como el generador nunca genera precios mayores a 15.000, entonces todas las rutas serán agregadas a esa colección
Vamos a tomar como ejemplo rutas con precio menor a 500 para ver que se agrega correctamente a la nueva colección
Primero se borra la colección anterior
![[Pasted image 20260514141500.png]]
La creamos nuevamente con rutas con precio menor a 500
![[Pasted image 20260514141523.png]]
Estas son algunas de las rutas añadidas
![[Pasted image 20260514141548.png]]


![[Pasted image 20260513194700.png]]
![[Pasted image 20260514141329.png]]