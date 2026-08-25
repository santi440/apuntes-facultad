![[Pasted image 20260528192133.png]]
Redis es una base de datos NoSQL clave-valor en memoria
A diferencia de una relacional, no usa tablas, filas ni SQL Tradicional
Frente a MongoDB, Redis no se centra en documentos persistentes, sino en estructuras rápidas en memoria como strings, listas, sets, hashes, sorted sets
![[Pasted image 20260528192137.png]]
Redis almacena los datos en memoria RAM
Esto lo hace muy rápido pero implica que la persistencia debe configurarse aparte para no perder datos ante reinicios o fallos
![[Pasted image 20260528192140.png]]
- Strings
	- Valores simples de texto, números o binarios
- Lists
	- Listas ordenadas
- Sets
	- Conjuntos sin repetidos
- Sorted Sets
	- Conjuntos ordenados
- Hashes
	- Mapas campo-valor
- Streams
	- Secuencias de eventos
- Bitmaps
	- Operaciones sobre bits
- HyperLogLogs
	- Conteo aproximado de elementos únicos
- Geospatial
	- Coordenadas geográficas basadas en sorted sets
![[Pasted image 20260528192143.png]]
- Alta velocidad
- Almacenamiento en memoria
- Estructura clave-valor
- Soporte de múltiples tipos de datos
- Expiración de claves
- Replicación
- Clustering
![[Pasted image 20260528192147.png]]
Conviene usar redis cuando se necesita mucha velocidad: caché, sesiones, colas simples, contadores, rate limiting o datos temporales
No conviene como reemplazo principal de un RDBMS cuando se requieren consultas complejas, relaciones fuertes, integridad referencial o joins
![[Pasted image 20260528192151.png]]
Sí, se soportan transacciones con `MULTI`, `EXEC`, `DISCARD` y `WATCH`
Las operaciones se encolan y luego se ejecutan juntas
Garantiza ejecución secuencial y atómica del bloque, pero no ofrece rollback automático como una base de datos ACID tradicional
![[Pasted image 20260528192154.png]]
Sí, tiene persistencia
- RDB
	- Genera snapshots del estado en determinados momentos
	- Es compacto y rápido para restaurar, pero puede perder cambios recientes
- AOF
	- Guarda cada operación  de escritura en un log
	- Permite menor pérdida de datos pero suele ocupar ma´s espacio y ser más lento
![[Pasted image 20260528192158.png]]
- Caché
- Sesiones
- Colas
- Contadores
- Rate limiting
- Datos temporales con expiración
- Procesamiento de eventos en tiempo real
![[Pasted image 20260528192203.png]]
![[Pasted image 20260530133812.png]]
![[Pasted image 20260528192207.png]]
![[Pasted image 20260530133828.png]]
![[Pasted image 20260528192211.png]]
![[Pasted image 20260530133836.png]]
![[Pasted image 20260528192216.png]]
![[Pasted image 20260530133856.png]]
![[Pasted image 20260528192220.png]]
![[Pasted image 20260530133915.png]]
![[Pasted image 20260528192223.png]]
![[Pasted image 20260530133939.png]]
![[Pasted image 20260528192321.png]]
![[Pasted image 20260530134025.png]]
![[Pasted image 20260528192325.png]]
![[Pasted image 20260530134035.png]]
![[Pasted image 20260528192329.png]]
![[Pasted image 20260530134119.png]]
![[Pasted image 20260528192352.png]]
![[Pasted image 20260530134131.png]]
![[Pasted image 20260528192356.png]]
![[Pasted image 20260530134141.png]]
![[Pasted image 20260528192400.png]]
![[Pasted image 20260530134158.png]]
![[Pasted image 20260528192404.png]]
![[Pasted image 20260530134242.png]]
![[Pasted image 20260528192415.png]]
![[Pasted image 20260530134307.png]]
![[Pasted image 20260528192419.png]]
![[Pasted image 20260530134339.png]]
![[Pasted image 20260528192423.png]]
![[Pasted image 20260530134900.png]]
![[Pasted image 20260528192427.png]]
![[Pasted image 20260530134937.png]]
![[Pasted image 20260528192432.png]]
![[Pasted image 20260530134949.png]]
![[Pasted image 20260528192442.png]]
![[Pasted image 20260530135023.png]]
![[Pasted image 20260528192447.png]]
`RENAMENX`
![[Pasted image 20260530135112.png]]
![[Pasted image 20260528192450.png]]
Para borrar todas las claves de la DB actual es `FLUSHDB`, para borrar todas las claves de todas las bases de datos del servidor de redis, se usa `FLUSHALL`
![[Pasted image 20260530135149.png]]
![[Pasted image 20260528192455.png]]
![[Pasted image 20260530140011.png]]
![[Pasted image 20260528192459.png]]
![[Pasted image 20260530140018.png]]
![[Pasted image 20260528192502.png]]
![[Pasted image 20260530140028.png]]
![[Pasted image 20260528192507.png]]
![[Pasted image 20260530140033.png]]
![[Pasted image 20260528192511.png]]
![[Pasted image 20260530140101.png]]
![[Pasted image 20260530140130.png]]
![[Pasted image 20260528192515.png]]
![[Pasted image 20260530140200.png]]
![[Pasted image 20260528192520.png]]
![[Pasted image 20260530140308.png]]
![[Pasted image 20260528192537.png]]
![[Pasted image 20260530140316.png]]
Para obtener los valores de una lista se usa `LRANGE` y se define un rango de índices (`-1` referencia a la última posición)
![[Pasted image 20260530140434.png]]
Para obtener un valor en particular, conociendo el índice, se usa `LINDEX`
![[Pasted image 20260530140451.png]]
![[Pasted image 20260528192541.png]]
![[Pasted image 20260530140513.png]]
![[Pasted image 20260528192544.png]]
![[Pasted image 20260530140524.png]]
![[Pasted image 20260528192550.png]]
![[Pasted image 20260530140544.png]]
![[Pasted image 20260528192553.png]]
![[Pasted image 20260530140555.png]]
![[Pasted image 20260528192557.png]]
![[Pasted image 20260530140610.png]]
![[Pasted image 20260528192601.png]]
![[Pasted image 20260530140639.png]]
![[Pasted image 20260528192605.png]]
`ALPHA` indica que se ordenen alfanuméricamente
![[Pasted image 20260530140749.png]]
SORT devuelve la lista ordenada, pero no modifica la lista original
![[Pasted image 20260528192609.png]]
![[Pasted image 20260530140832.png]]
![[Pasted image 20260528192613.png]]
![[Pasted image 20260530140851.png]]
![[Pasted image 20260528192617.png]]
![[Pasted image 20260530140915.png]]
![[Pasted image 20260528192622.png]]
![[Pasted image 20260530140926.png]]
![[Pasted image 20260528192626.png]]
![[Pasted image 20260530140938.png]]
![[Pasted image 20260528192630.png]]
![[Pasted image 20260530140958.png]]
![[Pasted image 20260528192634.png]]
==Posiciones 3 a 5 como en el comando o el comando debería ser de 2 a 4?==
![[Pasted image 20260530141019.png]]
![[Pasted image 20260528192637.png]]
![[Pasted image 20260530141206.png]]
Aparece dos veces, en la posición 1 y en la 4
`LPOS` permite saber dónde se encuentra un elemento en la lista, con `COUNT 0` nos devuelve todas las posiciones encontradas
![[Pasted image 20260528192654.png]]
![[Pasted image 20260530141502.png]]
![[Pasted image 20260530142330.png]]
![[Pasted image 20260528192700.png]]
![[Pasted image 20260530142358.png]]
Puede diferir de la cantidad de valores ingresados porque un SET almacena elementos únicos, con lo cual se eliminan repetidos
![[Pasted image 20260528192704.png]]
![[Pasted image 20260530142330.png]]
![[Pasted image 20260528192709.png]]
![[Pasted image 20260530142543.png]]
![[Pasted image 20260528192713.png]]
![[Pasted image 20260530142625.png]]
![[Pasted image 20260530142632.png]]
![[Pasted image 20260528192717.png]]
![[Pasted image 20260530142642.png]]
![[Pasted image 20260528192720.png]]
![[Pasted image 20260530142651.png]]
![[Pasted image 20260528192724.png]]
"sla" no existe en airports, por lo que no se mueve (indicado con el 0)
![[Pasted image 20260530142737.png]]
![[Pasted image 20260528192727.png]]
![[Pasted image 20260530142803.png]]
![[Pasted image 20260528192731.png]]
![[Pasted image 20260530142837.png]]
![[Pasted image 20260530142900.png]]
![[Pasted image 20260528192736.png]]
![[Pasted image 20260530142947.png]]
![[Pasted image 20260528192741.png]]
![[Pasted image 20260530143001.png]]
![[Pasted image 20260528192745.png]]
![[Pasted image 20260530143108.png]]
![[Pasted image 20260528192749.png]]
![[Pasted image 20260530143126.png]]
![[Pasted image 20260528192752.png]]
![[Pasted image 20260530143152.png]]
![[Pasted image 20260528192757.png]]
![[Pasted image 20260530143526.png]]
![[Pasted image 20260528192801.png]]
![[Pasted image 20260530143628.png]]
![[Pasted image 20260528192805.png]]
![[Pasted image 20260530143721.png]]
![[Pasted image 20260528192808.png]]
![[Pasted image 20260530143653.png]]
![[Pasted image 20260528192812.png]]
![[Pasted image 20260530143735.png]]
![[Pasted image 20260528192815.png]]
![[Pasted image 20260530143751.png]]
![[Pasted image 20260528192818.png]]
![[Pasted image 20260530143812.png]]
![[Pasted image 20260528192822.png]]
![[Pasted image 20260530143921.png]]
![[Pasted image 20260528192828.png]]
![[Pasted image 20260530143932.png]]
![[Pasted image 20260528192831.png]]
![[Pasted image 20260530143938.png]]
![[Pasted image 20260528192834.png]]
![[Pasted image 20260530143951.png]]
![[Pasted image 20260530144006.png]]
![[Pasted image 20260528192840.png]]
![[Pasted image 20260530144124.png]]
![[Pasted image 20260528192846.png]]
![[Pasted image 20260530144144.png]]
![[Pasted image 20260528192851.png]]
![[Pasted image 20260530144205.png]]
![[Pasted image 20260528192854.png]]
![[Pasted image 20260530144351.png]]
![[Pasted image 20260528192858.png]]
![[Pasted image 20260530144408.png]]
![[Pasted image 20260528192902.png]]
![[Pasted image 20260530144423.png]]
![[Pasted image 20260528192905.png]]
![[Pasted image 20260530144445.png]]
![[Pasted image 20260528192909.png]]
![[Pasted image 20260530144457.png]]
![[Pasted image 20260528192913.png]]
![[Pasted image 20260530144512.png]]
![[Pasted image 20260528192916.png]]
![[Pasted image 20260530144522.png]]
![[Pasted image 20260528192921.png]]
![[Pasted image 20260528192935.png]]
![[Pasted image 20260528192940.png]]
==Si le erré a algo, no me importa, soy más feliz sin saberlo==
![[Pasted image 20260530144649.png]]
![[Pasted image 20260528192945.png]]
![[Pasted image 20260530144835.png]]
![[Pasted image 20260528192953.png]]
![[Pasted image 20260530144858.png]]
![[Pasted image 20260528192957.png]]
![[Pasted image 20260530144915.png]]
![[Pasted image 20260528193000.png]]
![[Pasted image 20260530144957.png]]
![[Pasted image 20260528193004.png]]
![[Pasted image 20260530145018.png]]