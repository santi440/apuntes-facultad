==Revisar fue PANCHO==
1. Que es una syscall? Para que se utiliza?
   Una syscall es una llamada al sistema operativo la cual le permite a los procesos solicitar servicios del sistema operativo.
   Para solicitar servicios al sistema operativo

2. ¿Para qué sirve la macro syscall? Describa el propósito de cada uno de sus parámetros.
   La macro o instrucción `syscall` es el mecanismo fundamental utilizado en sistemas operativos de 64 bits (como Linux x86-64) ==para que un programa en "modo usuario" solicite servicios al núcleo (kernel) del sistema operativo, tales como gestionar archivos, procesos o redes==. Actúa como un puente seguro entre la aplicación y el hardware.

3. Son las versiones del kernel de linux.

---
1. ¿Qué es una System Call? ¿Para qué se utiliza?
	Una **System Call** (Llamada al Sistema) es la interfaz programática que permite a un programa de usuario solicitar servicios del núcleo (kernel) del sistema operativo.
	- **Para qué se utiliza:** Sirve como puente de comunicación entre el _User Mode_ (donde corren tus aplicaciones) y el _Kernel Mode_ (donde el SO tiene control total del hardware). Se utilizan para:
	    - **Gestión de procesos:** Crear, finalizar o esperar procesos (ej. `fork`, `exec`).
	    - **Gestión de archivos:** Leer, escribir, abrir o cerrar archivos (ej. `open`, `read`).
	    - **Gestión de memoria:** Solicitar o liberar espacio de memoria.
	    - **Comunicación/Redes:** Crear conexiones de red (ej. `socket`, `connect`).
2. ¿Para qué sirve la macro syscall? Describa el propósito de cada uno de sus parámetros.Ayuda: http://www.gnu.org/software/libc/manual/html_mono/libc.html#System-Calls
	La macro o función `syscall` de la biblioteca estándar de C (`glibc`) es una función genérica que permite invocar una llamada al sistema directamente por su **número de identificación**, en lugar de usar las funciones envoltorios (wrappers) habituales.

	- **`number`:** Es el número de la llamada al sistema (definido por la arquitectura, ej. `SYS_write`). Indica al kernel qué servicio específico se está solicitando.
	- **`...` (Argumentos variables):** Son los argumentos específicos que requiere la system call que estás invocando (pueden ser punteros a buffers, tamaños, descriptores de archivos, etc.). Dependiendo de la llamada, pueden ser desde cero hasta siete parámetros.
	```C
	#include <unistd.h> #include <sys/syscall.h> 
	long resultado = syscall(SYS_write, 1, "Hola\n", 5);
	```
3. Ejecute el siguiente comando e identifique el propósito de cada uno de los archivos que encuentra
	```zsh
	ls -lh /boot | grep vmlinuz
	```
	lista todos los kernels que estan instalados junto a dos links, un link que apunta al kernel actual (`vmlinuz -> ...`) y la versión anterior con la que inicio el SO (`vmlinuz.old`).
	
	Por ejemplo:
	![[Pasted image 20260402164303.png]]
	Normalmente el old deberia de ser anterior, como estuve cambiando entre kernels no se cumple jajajaj
4. Acceda al codigo fuente de GNU Linux, sea visitando https://kernel.org/ o bien trayendo el código del kernel(cuidado, como todo software monolítico son unos cuantos gigas)
	```zsh
	git clone https://github.com/torvalds/linux.git
	```
	No se que pretendes que haga en este punto :)
	
5. ¿Para qué sirven el siguiente archivo?
	a. arch/x86/entry/syscalls/syscall_64.tbl
		Es la **Tabla de Llamadas al Sistema**. Es un archivo de configuración que mapea los números de las syscalls con sus nombres simbólicos y las funciones internas del kernel (entry points) que las implementan.
		![[Pasted image 20260402165252.png]]
6. ¿Para qué sirve la herramienta strace? ¿Cómo se usa?
	`strace` es una herramienta de diagnóstico y depuración para Linux.
	- **¿Para qué sirve?** Permite interceptar y registrar todas las **llamadas al sistema** que realiza un proceso y las señales que recibe. Es ideal para entender por qué un programa falla sin tener el código fuente.
	- **¿Cómo se usa?**
	    - Para seguir un comando nuevo: `strace ls`
	    - Para adjuntarse a un proceso en ejecución por su PID: `strace -p 1234`
	    - Para guardar el resultado en un archivo: `strace -o salida.txt ./mi_programa`
	    Buenisismo
	    ![[Pasted image 20260402165715.png]]
	    Ahora si
	    ![[Pasted image 20260402165905.png]]
7. ¿Para qué sirve la herramienta ausyscall? ¿Cómo se usa?
	Es parte del paquete `auditd` (sistema de auditoría de Linux).
	- **¿Para qué sirve?** Permite consultar la tabla de llamadas al sistema para convertir números de syscall en nombres o viceversa, filtrando por arquitectura.
	- **¿Cómo se usa?**
	    - Saber el número de una syscall por nombre: `ausyscall write` (devolverá `1` en x86_64).
	    - Saber qué nombre corresponde a un número: `ausyscall 60` (devolverá `exit`).
	    - Listar todas las syscalls de una arquitectura: `ausyscall --dump`
	    Mismo caso de arriba, la vm no la tiene. Bueno mi maquina tampoco -.- (tocará creerle a gemini)
   