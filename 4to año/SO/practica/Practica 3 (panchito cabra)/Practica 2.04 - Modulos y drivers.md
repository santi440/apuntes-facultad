Conceptos generales
1. ¿Cómo se denomina en GNU/Linux a la porción de código que se agrega al kernel en tiempo  de ejecución? ¿Es necesario reiniciar el sistema al cargarlo? Si no se pudiera utilizar esto. ¿Cómo deberíamos hacer para proveer la misma funcionalidad en Gnu/Linux?
	La porción de codigo se llama modulo. No hace falta reiniciar el sistema, se permite la carga dinamica durante el tiempo de ejecucion y bajo demanda usando comandos como `insmod` o `modprobe`.. Sino existiera deberiamos de tener el codigo de todos modulos que existen y van a existir cargados en el binario de nuestro kernel o perder soporte para ese apartado. Caso contrario, podriamos recompilar el kernel incorporandolo para tener soporte.
2. ¿Qué es un driver? ¿Para qué se utiliza?
	Es un componente que se comunica con un dispositivo. Actúa como "traductor" entre las llamadas genéricas del kernel (ej. `read()`) y los comandos específicos que entiende el hardware (registros, interrupciones, señales eléctricas).
	
3. ¿Por qué es necesario escribir drivers?
	es necesario porque el kernel no puede conocer de antemano la forma en la que debe comunicarse con todos los dispositivos que le puedas enchufar. El driver te da una capita de abstraccion adicional.
4. ¿Cuál es la relación entre módulo y driver en GNU/Linux?
	- Un **Driver** es un concepto funcional (software que maneja hardware).  
	- Un **Módulo** es un formato de entrega (un archivo `.ko` que se carga en el kernel).
	- **En Linux:** La gran mayoría de los drivers se implementan como módulos para ahorrar memoria y dar flexibilidad, aunque podrías tener un módulo que no sea un driver (como uno que solo agregue un sistema de archivos o una syscall extra).

5. ¿Qué implicancias puede tener un bug en un driver o módulo?
	Depende el tipo de bug que estemos contemplando. Considerar que estamos corriendo ese pedazo de codigo en modo kernel (tenes permisos de hacer cualquier cosa). Podria causar un kernel panic, una brecha de seguridad, corromper datos, etc.
6. ¿Qué tipos de drivers existen en GNU/Linux?
	- **Caracter (Char):** Envían/reciben datos byte a byte (ej. teclados, puertos serie).
	- **Bloque (Block):** Manejan datos en bloques de tamaño fijo y permiten acceso aleatorio (ej. discos rígidos, SSDs).
	- **Red (Network):** No aparecen en `/dev`, manejan paquetes de datos y protocolos (ej. placas Ethernet, Wi-Fi)
7. ¿Qué hay en el directorio /dev? ¿Qué tipos de archivo encontramos en esa ubicación?
	**archivos de dispositivo** (_device nodes_). En Linux, "todo es un archivo".
	- **Tipos de archivo:** Verás archivos especiales de tipo **caracter** (empiezan con `c` en un `ls -l`) y de tipo **bloque** (empiezan con `b`). Estos archivos no ocupan espacio en disco, son "portales" al driver correspondiente.
	![[Pasted image 20260403184631.png]]
8. ¿Para qué sirven el archivo /lib/modules/<version>/modules.dep utilizado por el comando modprobe?
	Es la lista de **dependencias** entre módulos.
	El comando `modprobe` lo consulta para saber que si intentás cargar el módulo A, y este necesita el módulo B para funcionar, debe cargar ambos en el orden correcto. Se genera con el comando `depmod`.
9. ¿En qué momento/s se genera o actualiza un initramfs? 
	Se genera al instalar un nuevo kernel, al actualizar drivers críticos (como los de video o RAID) o cuando se cambia la configuración de montaje del disco raíz.
	Se carga en la RAM **antes** de que el sistema de archivos real sea montado. Su función es proveer los drivers necesarios para que el kernel pueda "ver" el disco duro y terminar de arrancar.
10. ¿Qué módulos y drivers deberá tener un initramfs mínimamente para cumplir su objetivo?
	- **Drivers de almacenamiento:** Para manejar la controladora de disco (SATA, NVMe, SCSI).	    
	- **Drivers de sistema de archivos:** Para poder leer la partición raíz (ext4, xfs, btrfs).
	- **Scripts de montaje:** Un entorno mínimo de ejecución (usualmente basado en `busybox`) para encontrar y montar el `/` real.