1. Crear el archivo memory.c con el siguiente código (puede estar en cualquier directorio, incluso fuera del directorio del kernel):
```c
#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
MODULE_LICENSE("Dual BSD/GPL");

static int hello_init(void) {
    printk("Hello world!\n");
    return 0;
}

static void hello_exit(void) {
    printk("Bye, cruel world\n");
}

module_init(hello_init);
module_exit(hello_exit);
## Lo hice todo con este codigo en lugar de solo la licencia porque no tenia sentido. NT
```
2. Crear el archivo Makefile con el siguiente contenido:
	```makefile
	obj-m := memory.o 
	```
	Responda lo siguiente:
	a. Explique brevemente cual es la utilidad del archivo Makefile.
		En este contexto, el `Makefile` le indica al sistema de construcción del kernel (`kbuild`) qué archivos objeto (`.o`) deben transformarse en un módulo cargable (`.ko`). Sin él, tendrías que escribir manualmente comandos de compilación larguísimos con flags específicos que solo el kernel conoce.
	b. ¿Para qué sirve la macro MODULE_LICENSE? ¿Es obligatoria?
	 Indica bajo qué licencia se distribuye el código. Esto le dice al kernel si el módulo es "Open Source" o "Propietario".
	- **¿Es obligatoria?** Técnicamente el código compila sin ella, pero el kernel se marcará como **"Tainted"** (contaminado). Además, muchas funciones avanzadas del kernel (marcadas como `EXPORT_SYMBOL_GPL`) **solo** pueden ser usadas por módulos que declaren una licencia compatible con GPL.
3. Ahora es necesario compilar nuestro módulo usando el mismo kernel en que correrá el mismo, utilizaremos el que instalamos en el primer paso del ejercicio guiado.
```zsh
make -C <KERNEL_CODE> M=$(pwd) modules
```
Kernel Code es la ruta en la que esta el codigo del kernel <- pongan una nota hdp
![[Pasted image 20260403190527.png]]
Responda lo siguiente:
a. ¿Cuál es la salida del comando anterior?

b. ¿Qué tipos de archivo se generan? Explique para qué sirve cada uno.
- **`memory.o`**: El código objeto (compilado pero no linkeado).
- **`Module.symvers`**: Lista de símbolos exportados por el módulo.
-  **`memory.mod.o`**: Contienen metadatos del módulo (versión del kernel, firma, etc.).
- **`.module-common.o`** : Contiene estructuras de datos que el cargador de módulos del kernel (`insmod`) necesita para identificar el módulo.
- **`memory.ko`**: **Kernel Object**. Este es el archivo más importante; es el módulo final que se carga con `insmod`. 
c. Con lo visto en la Práctica 1 sobre Makefiles, construya un Makefile de manera que si ejecuto
	i. make, nuestro módulo se compila
	ii. make clean, limpia el módulo y el código objeto generado
	iii. make run, ejecuta el programa
Asi quedo
```makefile
obj-m := memory.o
PWD := $(shell pwd)
KERNEL_CODE := /home/so/kernel/linux-6.13/

compilar : memory.c
        $(MAKE) -C $(KERNEL_CODE)  M=$(PWD) modules
clean : memory.o memory
        rm memory.o && memory
run : 
        $(MAKE) compilar && /usr/sbin/insmod memory.ko && /usr/sbin/rmmod memory && dmesg | tail -n 2
```
4. El paso que resta es agregar y eventualmente quitar nuestro módulo al kernel en tiempo de ejecución.
Ejecutamos:
```bash
insmod memory.ko
```
¿Para qué sirven el comando insmod y el comando modprobe? ¿En qué se diferencian?

insmod es de bajo nivel y se encarga de Insertar un archivo de objeto kernel (`.ko`) directo en la memoria del nucleo. Se le tiene que pasar la ruta exacta y no identifica dependencia de modulos. Si existiera dependencia entre dos modulos es tu responsabilidad cargarlos en orden. 
modprobe: es una versión de alto nivel para el mismo proposito pero busca por nombre en el directorio estandar (`/lib/modules/$(uname -r)/`) y, la principal diferencia con su antecesor es que, si mira el archivo modules.dep para conocer si el modulo que esta cargando necesita algun otro antes.  Si no es el directorio estandar no lo encuentra.

5. Ahora ejecutamos:
```bash
lsmod | grep memory
```
a. ¿Cuál es la salida del comando? Explique cuál es la utilidad del comando lsmod.
![[Pasted image 20260403194054.png]]
- **Salida:** Muestra una lista de todos los módulos que el kernel tiene cargados en la memoria RAM en ese instante. Las columnas suelen ser: **Nombre del módulo**, **Tamaño** (en bytes), y el **Contador de uso** (cuántos otros procesos o módulos lo están usando actualmente).
- **Utilidad:** Es la forma más sencilla para un administrador o desarrollador de verificar si su driver está "vivo" y activo. Básicamente, "formatea" la información cruda del kernel para que sea legible por humanos.
con grep filtramos por el memory que acabamos de cargar.

b. ¿Qué información encuentra en el archivo /proc/modules?
![[Pasted image 20260403194504.png]]
Te muestra lo mismo que ves en lsmod pero ademas le suma la direccion de memoria en la cuale esta vivo actualmente el modulo. `lsmod` lee este archivo 
c. Si ejecutamos more /proc/modules encontramos los siguientes fragmentos ¿Qué información obtenemos de aquí?:
```bash
memory 8192 0 - Live 0x0000000000000000 (OE)
binfmt_misc 24576 1 - Live 0x0000000000000000
intel_rapl_msr 16384 0 - Live 0x0000000000000000
intel_rapl_common 32768 1 intel_rapl_msr, Live 0x0000000000000000
```
El formato es:
nombre | tamaño | instancias | dependencias (-) | Estado (Live, Loading, Unloading) | Dir de memoria ram donde el kernel saltara a ejecutar el codigo.
d. ¿Con qué comando descargamos el módulo de la memoria?
rmmod

![[Pasted image 20260403195024.png]]
6. Descargue el módulo memory. Para corroborar que efectivamente el mismo ha sido eliminado del kernel ejecute el siguiente comando:
```zhs
lsmod | grep memory
```
soy un adelantado
7. Modifique el archivo memory.c de la siguiente manera:
```c
#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
MODULE_LICENSE("Dual BSD/GPL");
static int hello_init(void) {
	printk("Hello world!\n");
	return 0;
}
static void hello_exit(void) {
	printk("Bye, cruel world\n");
}
module_init(hello_init);
module_exit(hello_exit);
```
a. Compile y cargue en memoria el módulo.
b. Invoque al comando dmesg
	aparece el hello world en los logs del kernel
c. Descargue el módulo de memoria y vuelva a invocar a dmesg
	aparece el bye cruel world
8. Responder
	a. ¿Para qué sirven las funciones module_init y module_exit?. ¿Cómo haría para ver la información del log que arrojan las mismas?.
		`module_init` indica al kernel cual es la funcion de entrada. Caso contrario `module_exit` indica cual es la funcion de salida. Podemos ver los logs que generaron ambas funciones con `dmesg`, como escriben con `printk`, podemos cargar el modulo y ver el log de `module_init`, descargar y ver el de `module_exit`
	b. Hasta aquí hemos desarrollado, compilado, cargado y descargado un módulo en nuestro kernel. En este punto y sin mirar lo que sigue. ¿Qué nos falta para tener un driver completo?.
		Faltarian las operaciones para trabajar con archivos tipo open(), release(), write(), un numero para identificar el driver y que este realmente en /dev para considerarlo un kernel del sistema
	c. Clasifique los tipos de dispositivos en Linux. Explique las características de cada uno.

|**Tipo**|**Características**|**Ejemplos**|
|---|---|---|
|**Carácter (Char)**|Se accede a los datos como un **flujo de bytes** secuencial. No permiten "saltar" (seek) fácilmente a cualquier posición. Se leen/escriben byte a byte.|Teclados, mouses, puertos serie (UART), sensores.|
|**Bloque (Block)**|Permiten acceso aleatorio (puedes leer el bloque 500 sin pasar por el 1). Los datos se transfieren en **bloques de tamaño fijo** (ej: 512 o 4096 bytes).|Discos rígidos (HDD), SSDs, pendrives, tarjetas SD.|
|**Red (Network)**|No aparecen como archivos en `/dev`. Se gestionan a través de la pila de protocolos del kernel y envían/reciben **paquetes**.|Placas Ethernet (eth0), placas Wi-Fi (wlan0).|
