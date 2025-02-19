## Ejemplos de File System 
### Unix
![[Tipos de archivos en unix.png]]
Los links "duros" son dos entradas en el filesystem que apuntan al mismo inodo y son consideradas para calcular el link count de ese inodo.

Los links simbólicos son un archivo que su contenido es una ruta a la que esta referenciando. Es lo más parecido a un acceso directo. 


Cada disco puede ser dividido lógicamente en uno o más particiones y cada partición cuenta con:
- Boot block: Información de arranque del SO.
- SuperBlock: Contiene información que detalla como es el resto del FileSystem y sus atributos (a veces separado, otras veces dentro de ellos entre ellos, los bloques/clusters libres).
- I-node table: Tabla que contiene la información de todos los inodos. Link count = 0, inodo libre

> [!NOTE] I-NODO
> Estructura de control que contiene la información clave de un archivo. Cada i-nodo es un archivo. 
- Root-dir: Todo sistema de archivos cuenta con un directorio raíz.
- Files and directories/Data Blocks: Bloques de datos de los archivos.
![[Division de un disco.png]]
#### I-NODO
Estructura de datos que posee información (atributos de interes) sobre cada archivo, directorio u objeto que se almacene en el sistema de archivos menos el nombre. Los inodos no se crean dinamicamente, al formatearse se determina la cantidad posible.
Atributos que hay en un I-Nodo:
![[Inodo información.png]]
I-Nodo graficámente:
![[Inodo graficamente.png]]

#### Directorios
Los nombres de archivos se almacenan en los directorios. El directorio no deja de ser un inodo que referencia o apunta a otros inodos.
El directorio es una tabla que tuplas del tipo “Numero de i-nodo, nombre de archivo".
![[Buscar inodo de un archivo.png]]

### Windows
// Al final de la diapositiva hay material sobre los filesystem que usa windows y detalla de la FAT y NTFS. Basicamente: FAT se mantiene como filesystem soportado por compatibilidad y soporte, tiene 3 versiones FAT12, FAT16 y FAT32 (el numero refiere a la cantidad de bits que puede referenciar). Al principio de cada partición encontras la File allocation table, un duplicado y el directorio raiz. La FAT tiene tantas entradas como bloques y utiliza un esquema de ASIGNACION ENCADENADA, el puntero al proximo bloque está en la FAT y no en los bloques. Resulta sumamente sencillo y rapido en ciertas operaciones. NTFS usa 64 bits y permite: Tamaños de archivo y de discos mayores, Mejora performance en discos grandes, Nombres de archivos de hasta 255 caracteres, Atributos de seguridad,Transaccional.