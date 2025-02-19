// File Allocation Table != FAT32 (se ve mejor en [[12- Archivos 3]])
// Los archivos se tratan en memoria RAM y luego se bajan a disco
![[Conceptos FileSystem.png]]
En la manera que lo vemos, se pueden usar sectores o clusters. La diferencia radica en las entradas de la tabla, el cluster al ser mas grande se achican en tamaño las estructuras. Para archivos muy pequeños, cluster grandes generan fragmentacion interna.

> [!NOTE] Ejemplo
> Normalmente los sectores son de 512 bytes, para un archivo de 2 kb, con sectores son 4 entradas de tabla y con cluster de 4 (imagen de arriba) 1 solo. Sin embargo, para un archivo de 50 bytes, en ambos casos es una sola entrada pero el ejemplo del cluster tiene muchisima mas fragementacion interna. El tamaño de la unidad de  trabajo es siempre la misma y no se puede recuperar el espacio de fragmentacion.

## Asignación
### Gestión del tamaño
#### Pre-Asignación
Es una técnica de gestión de almacenamiento en la que se reserva de antemano un espacio en memoria secundaria (disco) para un archivo en el momento de su creación, el sistema debe saber cuanto ocupara, por lo que, el usuario o el SO debe estimarlo. Como es dificil conocer el espacio que ocupara un archivo, y estos tienden a crecer, muchas veces se asisgna más espacio del requerido (fragmentación interna) para evitar problemas futuros. 
Si el archivo crece más allá del espacio preasignado, pueden ocurrir dos cosas:
- Se debe asignar un nuevo bloque de almacenamiento en otra parte del disco, lo que puede fragmentar el archivo y degradar el rendimiento.
- En algunos sistemas, si no hay más espacio contiguo disponible, la operación puede fallar, requiriendo que el usuario asigne más espacio manualmente.
Generalmente se utiliza asignación continua, para mejorar el acceso secuencial, pero podria utilizar otras técnicas dependiendo del sistema de archivos.
#### Asignación dinámica
El espacio se solicita a medida que se necesita. Los bloques de datos pueden quedar de
manera no contigua. 

#### Formas de asignación
##### Continua
Se utilizan conjuntos contiguos de bloques de disco, requiere de preasignación. Es necesario llevar una File Allocation Table (FAT) sencilla, una entrada por archivo que incluye Bloque de inicio y longitud. El archivo puede ser leído con una única operación. Puede generar fragmentación externa que potencialmente puede ser solucionada compactando/desfragmentando(operación no trivial, demanda mucho trabajo mover los bloques de disco de modo que queden todos los espacios de memoria libre contiguos). Puede ser un problema encontrar bloques libres continuos en el disco que alcancen para almacenar un nuevo archivo, incluso habiendo suficiente espacio en disco y resolver el incremento del tamaño de un archivo mas alla de lo asignado son los grandes problemas de está técnica. Utilizada en CDROM (solo lectura), se planifica  el tamaño de cada archivo y luego se escribe, es importante una rápida lectura, posible al no tener que mover el cabezal o laser, se lee secuencial
![[Asignación contigua.png]]
##### Encadenada
Técnica que rompe la continuidad, los archivos van a ir creciendo bajo demanda (dinámico). La asignación es en base a bloques individuales, donde cada bloque tiene un puntero al próximo bloque del archivo. La File allocation table se mantiene igual con una única entrada por archivo: Bloque de inicio y tamaño del archivo. No hay fragmentación externa
Útil para acceso secuencial (no random), el acceso aleatorio (a una posición especifica del archivo implicaria leer todos los anteriores).
![[Asignación Encadenada.png]]
Potencialmente se puede consolidar el archivo, similar a desfragmentar de continua, para que quede el archivo en bloques contiguos de memoria, mejorando la lectura al requerir menor tiempo de seek.

##### Indexada 
La FAT contiene un puntero al bloque índice, una única entrada con la dirección del bloque de índices (index node / i-node), no contiene datos propios del archivo, sino que contiene un índice a los bloques que lo componen. no exijo continuidad por lo que no se produce fragmentación externa. El acceso “random” a un archivo es eficiente.
La estructura de datos del indice es finita, aparece la limitación en el tamaño de un archivo 
![[Asignación indexada.png]]

###### Indexada por sectores
Trata de aplanar la tabla de indices. Cuando los bloques de un archivo se almacenan de manera consecutiva, se guarda una sola entrada con el bloque de comienzo del conjunto y la longitud o bloques consecutivos utilizados por el archivo. 
![[Asignación indexada por sectores.png]]

###### Indexada con niveles de indirección
Se busca complejizar la tabla indice, para poder vencer el tamaño maximo de esta estructura. Se reserva espacio del indice para los distintos niveles de indirección. Aquellos que sean directos apuntaran directamente a datos, los de un nivel de indirección apuntaran a otra estructura indice y está a los datos, los de segundo nivel de indirección apuntaran a un indice de indice de datos. Se puede tener tantos niveles de indirección como se desee y el archivo ira ocupando a medida que lo necesite (primero en los directos -> un nivel de indirección -> dos niveles de indirección -> ... ).
![[Asignacion Indexada con niveles de indireccion.png]]

## Gestión de Espacio Libre
Control sobre cuáles de los bloques de disco están disponibles.
### Tabla de bits
Un vector de bits, 1 bit por cada bloque de disco si el bit esta en 0 esta libre, si el bit esta en 1 esta ocupado, por posición. Es fácil encontrar un un bloque o grupo de bloques libres pero el vector ocupa demasiado espacio en memoria dado por la cuenta 
```
tamaño disco bytes / tamaño bloque en sistema archivo
```
![[Tabla de bits.png]]

### Bloques libres encadenados 
Se tiene un puntero al primer bloque libre y cada bloque libre tiene un puntero al siguiente
bloque libre, excepto el último. Ineficiente para la búsqueda de bloques libres -> Hay que realizar varias operaciones de E/S para obtener un grupo libre. Y muy difícil encontrar bloques libres consecutivos (para mejor performance de lectura). 
La pérdida de un enlace implicaría perder todos los enlaces posteriores. Si estoy escribiendo y se corta la luz puedo generar inconsistencias (en todos los casos, pero en este se ve agravado por potencialmente perder toda la estructura).
![[free-space list.png]]
### Indexada 
Variante de la anterior. Refiere a un indice que tiene los N-1 direcciones libres, y en la posicion N tiene un puntero al siguiente indice, de la misma manera que la asignación indexada con niveles de indirección explicada más arriba. Es eficiente para encontrar bloques de memoria libres, cuando estos se ocupan deberiamos realizar corrimientos.
![[Indexada free space.png]]
### Recuento 
Variante de la anterior. Siguiendo la misma lógica pero ahora se cuenta con una tabla de dos columnas el comienzo o desde y la cantidad de bloques contiguos libres. Achica el tamaño de la estructura pero crece en complejidad.
![[Recuento indexado free space.png]]
La estructura debe ser consistente, es decir, si en el ejemplo anterior se liberan los bloques 25 y 26, debo insertarlos pero manteniendo el criterio, de modo que deberia estar entre "Free 20/5" y "Free 27/2" pero al liberarlos queda como un único bloque más grande. Quedaría en su lugar "Free 20/9" y los corrimientos necesarios. 