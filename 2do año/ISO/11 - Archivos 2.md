// File Allocation Table != FAT32
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

### Formas de asignación
### Continua
Se utilizan conjuntos contiguos de bloques de disco, requiere de preasignación. Es necesario llevar una File Allocation Table (FAT) sencilla, una entrada por archivo que incluye Bloque de inicio y longitud. El archivo puede ser leído con una única operación. Puede generar fragmentación externa que potencialmente puede ser solucionada compactando/desfragmentando(operación no trivial, demanda mucho trabajo mover los bloques de disco de modo que queden todos los espacios de memoria libre contiguos). Puede ser un problema encontrar bloques libres continuos en el disco que alcancen para almacenar un nuevo archivo, incluso habiendo suficiente espacio en disco y resolver el incremento del tamaño de un archivo mas alla de lo asignado son los grandes problemas de está técnica. Utilizada en CDROM (solo lectura), se planifica  el tamaño de cada archivo y luego se escribe, es importante una rápida lectura, posible al no tener que mover el cabezal o laser, se lee secuencial
![[Asignación contigua.png]]
### Encadenada
Técnica que rompe la continuidad, los archivos van a ir creciendo bajo demanda (dinámico). La asignación es en base a bloques individuales, donde cada bloque tiene un puntero al próximo bloque del archivo. La File allocation table se mantiene igual con una única entrada por archivo: Bloque de inicio y tamaño del archivo. No hay fragmentación externa
Útil para acceso secuencial (no random), el acceso aleatorio (a una posición especifica del archivo implicaria leer todos los anteriores).
![[Asignación Encadenada.png]]
Potencialmente se puede consolidar el archivo, similar a desfragmentar de continua, para que quede el archivo en bloques contiguos de memoria, mejorando la lectura al requerir menor tiempo de seek.