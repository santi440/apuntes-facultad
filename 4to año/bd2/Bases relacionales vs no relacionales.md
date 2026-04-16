Ventajas vs desventajas de Relacionales
- SQL = es un lenguaje de consultas para poder recuperar o tratar con los datos
- estructura = se arma una estructura de tablas que no se debe modificar por integracion con otros sistemas (ver punto sig)
- Transaccion = concurrencia
Desventajas:
- la estrucutra me limita por si tengo un modelo cambiante
- diferencia de impedancia 
----
- Cada servicio tiene una bd y se comunican entre ellos por medio de service, no es necesario que todas sean relacionales o del mismo tipo
---
- Volumen de datos =conviene tener varias db y verlas como una sola. 
Cluster = conjunto de bases de datos no relacionales
## No SQL
- clave valor: reddis -> suele tener pocas actualizaciones y estoy limitado en las consultas a conocer la clave pero recupero directo (hash)
- documentos: JSON -> el esquema que se genera no es fijo, tiene estructura pero menos que una sql
- columnas: Cassandra -> agregacion a nivel familia de columnas (super columna) si te interesa por columna y no por fila. Cada que agrego un dato agrego una columna
### Agregaciones
Depende del como se tengan que recuperar los datos la estructuracion que armes. 
Transacciones = se dice que no tienen transacciones pero algunos motores si lo poseen. Mongo lo hace a nivel documento. noSQL tiene mejor sentido para cosas que no tienen una actualizacion frecuente

### Relaciones
No tiene relaciones explicitas. Se puede en mondo mantener una referencia a otro documento. Se cae a los pedazos en relaciones complejas

## OMG GRAFOS HI!!!
manejan las relaciones entre los distintos datos que tengo en mi modelo. Son nodos y aristas. Muy performantes. Cuando guardo un nodo tengo que guardar todas las relaciones de ese nodo -> las inserciones son carisimas pero las consulas SON LOCURA neo4j

## Mongo DB
Se basa en HASH. No maneja esquemas pero si conexiones. No tiene DDL para definir datos. Almacena en BJSON que es más optimo que JSON normal  binari json.
Tabla = coleccion
fila = doc
campo = campo doc
tamaño maximo de doc 16MB

#### Relaciones
1 a muchos embebidos
1 a muchos =  referencian el documento del que dependen 
1 a millones = referencia al padre

#### Indices
Logra que las consultas sean mas performantes. Se definen a nivel de colecciones. createIndex y es orientado a la recuperacion no a limintantes score 1 ascentente
#### Agregation framework
aggregate -> operacion
Hace un pipeline
match ->group -> lockup -> ...
match es un filter. Puede ejecutarse varias veces y tengo que asegurarme que no cambie el resultado en base a cuantas veces se ejecute.