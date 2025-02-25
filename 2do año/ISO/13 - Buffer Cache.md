Se utilizan buffers en memoria principal para almacenamiento temporario de bloques de disco con el objetivo de minimizar la frecuencia de acceso al disco. Dos alternativas:
- Cuando un proceso necesita un bloque se copia al espacio de direcciones de este, como los procesos no pueden tocar el espacio de direcciones de otro, no permitiría compartir el bloque.
- Se trabaja como memoria compartida. Se cargan y descargan bloques de memoria y se puede compartir. El espacio es finito -> debe existir un algoritmo de reemplazo para determinar que bloque será la victima.
Cuando se necesita un buffer para cargar un nuevo bloque, se elige el que hace más tiempo que no es referenciado (LRU). Decimos que al referenciarse pasa al final de la lista, el movimiento es a nivel de punteros, no se mueven los bloques en memoria. Tambien se podria usar Least Frecuently Used (se reemplaza el que tenga menor número de referencias).

El módulo de buffer cache es independiente del sistema de archivos y  de los dispositivos de  hardware. Es un servicio brindado por SO. 
Tiene dos partes:
- Headers: Contienen información para modelar: la ubicación del bloque en RAM, numero del bloque, estado, relaciónes entre headers, etc
- El buffer en sí: el lugar en RAM (referenciado por el header) donde se almacena realmente el bloque del  dispositivo llevado a memoria.
### Headers
Estructura de datos que mantiene el sistema operativo, el conjunto de headers en su totalidad. Cada Header tiene información del número de bloque y el dispositivo que corresponde a ese bloque de datos (bloque 5 de la particion sda1), un estado y punteros, dos para la hash queue, dos para la free list y uno para el bloque en el que realmente esta la información.

![[buffer header.png]]
#### Estado
- Free: El bloque se puede utilizar, no significa que este vacio sino que no se esta usando, se uso y quedo cargado en memoria o la estructura sigue teniendo espacio libre (vacio).
- Busy: Un proceso lo pidió y lo esta usando
- Escribiendo: La operación es asincronica, lo manda a leer/escribir pero no se cuando termine, el disco me va a avisar por interrupción.
- Delayed Write: bloques/buffers de memoria que han sido modificados pero los cambios no se vieron reflejados en el bloque original.
#### Free list
Enlaza los headers que estan en estado disponible. Organiza los buffers disponibles para ser utilizados para cargar nuevos bloques de disco. Se ordena segun LRU y sigue el principio de una lista doblemente enlazada. 

![[free list.png]]

#### Hash queue
Puede tener un monton de bloques cargados en memoria utiliza una estructura de datos mas optima para la busqueda, mantiene un conjunto de listas de acuerdo a una función de hash aplicada sobre el dispositivo y # bloque. Agrupa los buffers cuyo resultado dio igual y mantiene tantas listas como posibles resultados tiene la función utilizada. Esta estructura se utiliza para determinar si el bloque esta cargado en memoria, independiente del estado.
Cada header tiene dos punteros porque se comporta como una lista doblemente enlazada. 

![[Hash queue.png]]
#### Funcionamiento
Un proceso requiere acceder a un archivo, utiliza el i-nodo para localizar los bloques de datos donde se encuentra y buffer cache recibe el requerimiento y evalua si puede satisfacer el requerimiento o debe pedir E/S.
Se pueden dar 5 escenarios:
1) El kernel encuentra el bloque en la hash queue y el buffer está libre (en la
free list). Se le asigna al proceso y cambia el estado a BUSY, eliminandolo de la free list, no hizo falta hacer E/S
2) El kernel no encuentra el bloque en la hash queue y utiliza un buffer libre. Se selecciona el primer elemento de la free list, se realiza E/S y se marca ese buffer como BUSY. Se reacomodan los punteros de la hash queue
3) Idem 2, pero el bloque libre esta marcado como DW. Se manda a escribir a disco y se selecciona el siguiente como victima, al termina de escribir el buffer continua al principio de la free list
4) El kernel no encuentra el bloque en la hash queue y la free list está vacía. El proceso se bloquea hasta que algun buffer sea liberado, cuando el proceso se despierta se verifica nuevamente si el bloque solicitado no esta cargado, alguien puede haberlo pedido mientras dormia.
5) El kernel encuentra el bloque en la hash queue pero está BUSY. El proceso se bloquea hasta que ESE bloque se libere. Pueden liberarse otros buffers pero no se cargará información repetida en cache para evitar inconsistencias o un uso ineficiente de memoria. 