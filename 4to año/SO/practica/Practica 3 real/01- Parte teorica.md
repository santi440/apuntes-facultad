## Ejercicio 1
Cuál es la diferencia fundamental entre un proceso y un thread?
**aislamiento y la memoria**
- **Proceso:** Es una instancia de un programa en ejecución que posee su propio espacio de direccionamiento, archivos abiertos y recursos asignados por el sistema operativo. Existe una separación total entre procesos (incluso si son del mismo programa).
- **Hilo (Thread):** Es una unidad básica de ejecución _dentro_ de un proceso. Todos los hilos de un mismo proceso comparten el mismo espacio de memoria (código, datos y recursos), pero cada hilo mantiene su propio contador de programa (PC), registros y pila (stack).

# Ejercicio 2
¿Qué son los User-Level Threads (ULT) y cómo se diferencian de los Kernel-Level Threads
(KLT)?
- **ULT (Hilos de nivel de usuario):** Son gestionados enteramente por librerías de usuario (como `pthread`, ==entiendo que pthread_create me permite mapear un ULT a un KLT, tmb lo crea??????==). El Kernel no tiene conocimiento de su existencia; para el sistema operativo, el proceso es solo una unidad de ejecución única.
- **KLT (Hilos de nivel de kernel):** Son gestionados directamente por el sistema operativo. El kernel es consciente de la existencia de estos hilos y es quien los crea, destruye y planifica.
## Ejercicio 3
¿Quién es responsable de la planificación de los ULT? ¿y los KLT? ¿Cómo afecta esto al
rendimiento en sistemas con múltiples núcleos?
- **¿Quién planifica?**
    - **ULT:** La planificación la realiza un **planificador de hilos (scheduler)** en la biblioteca de usuario.
    - **KLT:** La planificación la realiza el **planificador del sistema operativo (kernel scheduler)**.
- **Impacto en rendimiento (Multinúcleo):**
    - Los **ULT** tienen una desventaja crítica: si un hilo realiza una llamada al sistema bloqueante (como leer un archivo), **todo el proceso se bloquea**, ya que el kernel no sabe que hay otros hilos listos para ejecutarse. Además, no pueden aprovechar varios núcleos simultáneamente porque el SO solo ve un hilo de ejecución por proceso.
    - Los **KLT** están diseñados para sistemas modernos: permiten que diferentes hilos de un mismo proceso se ejecuten **paralelamente en núcleos distintos**, maximizando el aprovechamiento del hardware.
## Ejercicio 4
¿Cómo maneja el sistema operativo los KLT y en qué se diferencian de los procesos?
El sistema operativo maneja los **KLT** mediante una estructura de datos llamada _Thread Control Block_ (TCB) dentro del kernel, mientras que los **procesos** se gestionan mediante el _Process Control Block_ (PCB).
- **Diferencia clave:** Al realizar un cambio de contexto, el SO debe cambiar todo el mapa de memoria (espacio de direccionamiento) cuando pasa de un proceso a otro, lo cual es costoso. Al cambiar entre **KLT** del mismo proceso, el mapa de memoria se mantiene, lo que hace que el cambio de contexto sea mucho más rápido y ligero.
## Ejercicio 5
¿Qué ventajas tienen los KLT sobre los ULT? ¿Cuáles son sus desventajas?

|**Característica**|**Ventajas de los KLT**|**Desventajas de los KLT**|
|---|---|---|
|**Paralelismo**|Pueden ejecutarse en múltiples núcleos simultáneamente.|Mayor sobrecarga (_overhead_) debido a las llamadas al sistema.|
|**Bloqueo**|Si un hilo se bloquea, el kernel puede ejecutar otro hilo del mismo proceso.|El manejo de estructuras de datos en el kernel es más complejo.|
|**Gestión**|No requieren soporte especial en aplicaciones; son transparentes.|El cambio de contexto entre hilos es más lento que en los ULT.

## Ejercicio 6
Qué retornan las siguientes funciones:
	a. getpid(): Retorna el identificador (PID) del **proceso** que realiza la llamada.
	b. getppid(): Retorna el identificador del proceso padre (_Parent Process ID_).
	c. gettid(): Retorna el ID del hilo que lo llamo a nivel SO. hilo del kernel actual o TID
	d. pthread_self(): Retorna el ID del hilo a nivel libreria o hilo de usuario.
	e. pth_self(): Retorna un tipo pth_t, puntero a la estructura que maneja el hilo. Es de GNU pth
## Ejercicio 7
¿Qué mecanismos de sincronización se pueden usar? ¿Es necesario usar mecanismos de
sincronización si se usan ULT?
Sincronización:
	○ Mutex (`pthread_mutex_t`)
	○ Condicionales (`pthread_cond_t`)
	○ Barreras (`pthread_barrier_t`)
	○ Semáforos (`sem_init`)
**Sí, absolutamente.** Aunque los ULT sean gestionados por una librería de usuario, siguen accediendo al mismo espacio de memoria compartido. Si dos hilos modifican una variable al mismo tiempo sin sincronización, el resultado será impredecible (condición de carrera o race condicion), independientemente de quién realice la planificación. 
## Ejercicio 8
Procesos
	a. ¿Qué utilidad tiene ejecutar fork() sin ejecutar exec()?
		Crea un nuevo proceso identico al actual. Se usa en servidores concurrentes donde el padre sigue aceptando conexiones y el hijo procesa la petición, o para realizar tareas paralelas donde ambos procesos comparten el mismo código.
	b. ¿Qué utilidad tiene ejecutar fork() + exec()?
		Es el patrón estándar para **ejecutar un programa nuevo**. El `fork()` crea el proceso (hijo), y `exec()` reemplaza la imagen de memoria del hijo con el nuevo ejecutable.
	c. ¿Cuál de las 2 asigna un nuevo PID fork() o exec()?
		El fork. exec no reemplaza la estructura de PCB sino que solo pisa el código del proceso.
	d. ¿Qué implica el uso de Copy-On-Write (COW) cuando se hace fork()?
		Es una optimización donde, tras el `fork()`, el padre y el hijo comparten las mismas páginas de memoria físicas como "solo lectura". Si uno de ellos intenta escribir en una página, el SO crea una copia privada de esa página solo para ese proceso. Esto evita copiar toda la memoria innecesariamente.
	e. ¿Qué consecuencias tiene no hacer wait() sobre un proceso hijo?
		Si el padre no invoca `wait()`, el proceso hijo, al terminar, se convierte en un **proceso zombie**. El hijo sigue existiendo en la tabla de procesos del kernel (consumiendo una entrada) hasta que el padre lo recoja.
	f. ¿Quién tendrá la responsabilidad de hacer el wait() si el proceso padre termina sin
	hacer wait()?
		Si el padre muere antes de hacer `wait()`, el proceso hijo se convierte en un **proceso huérfano**. El proceso **`init` (o `systemd`, con PID 1)** adopta al huérfano y se encarga de ejecutar el `wait()` cuando el hijo finalice, liberando su entrada en la tabla de procesos.
## Ejercicio 9
Kernel Level Threads
a. ¿Qué elementos del espacio de direcciones comparten los threads creados con
pthread_create()?
	Comparten el espacio de direcciones completo, código, datos globales, montículo (_heap_) y descriptores de archivos. Cada uno tiene su propia pila (_stack_) y registros.
b. ¿Qué relaciones hay entre getpid() y gettid() en los KLT?
	En un sistema con KLT (como Linux), todos los hilos de un mismo proceso comparten el mismo `getpid()` (ID de grupo de proceso), pero cada uno tiene un `gettid()` único. Esto permite al kernel planificarlos individualmente.
c. ¿Por qué pthread_join() es importante en programas que usan múltiples hilos?¿Cuándo se liberan los recursos de un hilo zombie?
	`pthread_join()` bloquea al hilo padre hasta que el hilo hijo termina, permitiendo recuperar su valor de retorno y liberar recursos. Un hilo "zombie" (a veces llamado _detached thread_ si no se une correctamente) libera sus recursos cuando el proceso completo termina o cuando se invoca `pthread_detach()`.
d. ¿Qué pasaría si un hilo del proceso bloquea en read()? ¿Afecta a los demás hilos?
	**En KLT:** Solo el hilo que hace la llamada `read()` se bloquea. El kernel sabe que son hilos distintos, por lo que el planificador puede asignar el tiempo de CPU a los demás hilos del mismo proceso que están listos para ejecutarse. **No afecta al resto.**
	**En ULT**: si se bloquea porque no reconoce o no los diferencia. 
e. Describí qué ocurre a nivel de sistema operativo cuando se invoca pthread_create()
(¿es syscall? ¿usa clone?).
	`pthread_create` es una función de la biblioteca (glibc). A nivel de kernel, esta invoca la llamada al sistema **`clone()`** (en Linux). `clone()` permite especificar exactamente qué recursos (memoria, descriptores, etc.) se comparten entre el nuevo contexto de ejecución y el llamador, creando efectivamente un KLT.
## Ejercicio 10
User Level Threads
	a. ¿Por qué los ULTs no se pueden ejecutar en paralelo sobre múltiples núcleos?
		El sistema operativo solo tiene visibilidad del **proceso** como entidad de planificación, no de sus hilos internos. Para que ocurra paralelismo real en multinúcleo, el kernel debe poder asignar diferentes unidades de ejecución (hilos del kernel) a diferentes núcleos.
	b. ¿Qué ventajas tiene el uso de ULTs respecto de los KLTs?
	- **Eficiencia en el cambio de contexto:** El cambio de contexto ocurre totalmente en espacio de usuario, sin necesidad de hacer una transición de modo (User Mode a Kernel Mode), lo que evita la sobrecarga de las llamadas al sistema.    
	- **Flexibilidad en la planificación:** La aplicación puede implementar un algoritmo de planificación personalizado (ej. basado en prioridades específicas de la lógica del negocio) que sea más eficiente para sus necesidades que el planificador general del sistema operativo.
    - **Portabilidad:** Son independientes del sistema operativo, ya que la gestión reside en una librería de usuario.
	c. ¿Qué relaciones hay entre getpid(), gettid() y pth_self() (en GNU Pth)?
	- **`getpid()`:** Devuelve el PID del proceso completo. Todos los ULTs de ese proceso retornan el mismo valor. 
	- **`gettid()`:** Como los ULTs son invisibles para el kernel, esta función devolverá el ID del hilo del kernel (KLT) que está ejecutando al proceso en ese momento. Si varios ULTs se turnan en un mismo KLT, todos verán el mismo `gettid()`. 
	- **`pth_self()`:** En GNU Pth, esta función devuelve un **identificador único del hilo de usuario** gestionado por la biblioteca. Es independiente del kernel y sirve para que la aplicación identifique cuál de sus hilos internos está operando.
	d. ¿Qué pasaría si un ULT realiza una syscall bloqueante como read()?
		Si un hilo ejecuta una llamada bloqueante (como `read()` en un archivo), el **kernel bloquea todo el proceso** asociado porque para él, el proceso completo es el que hizo la petición. Los demás ULTs del proceso, aunque estuvieran listos para trabajar, quedan suspendidos hasta que la llamada de E/S finalice.
	e. ¿Qué tipos de scheduling pueden tener los ULTs? ¿Cuál es el más común?
	Los ULTs suelen utilizar planificadores de espacio de usuario:
	- **Round-Robin:** El más común; los hilos se ejecutan en orden circular durante periodos de tiempo fijos (_time slices_).
	- **Prioridades:** Algunos hilos pueden tener mayor peso que otros.
	- **Cooperativo (Non-preemptive):** El hilo actual debe ceder voluntariamente el control (`yield()`) para que otro se ejecute. Es muy común en sistemas de eventos (como _Event Loops_ en JavaScript o librerías ligeras).
	El **Round-Robin con preempción** (donde la librería usa señales, como `SIGALRM`, para interrumpir al hilo y cambiarlo) es el más frecuente para simular un comportamiento similar al del SO.
## Ejercicio 11
Global Interpreter Lock
a. ¿Qué es el GIL (Global Interpreter Lock)? ¿Qué impacto tiene sobre programas multi-thread en Python y Ruby?
	El GIL es un **mutex (bloqueo de exclusión mutua)** que protege el acceso a los objetos de Python (o Ruby en sus versiones antiguas/específicas) de manera que **solo un hilo puede ejecutar _bytecodes_ de Python a la vez**, incluso en procesadores con múltiples núcleos.
- **Impacto en programas Multi-thread:**
    - **En tareas intensivas de E/S (I/O Bound):** El impacto es mínimo o nulo. Cuando un hilo realiza una operación de red o disco (como `read()` o `sleep()`), el GIL se libera, permitiendo que otro hilo tome el control.
    - **En tareas intensivas de CPU (CPU Bound):** El impacto es severo. Si tienes un programa que realiza cálculos matemáticos complejos, el GIL crea un "cuello de botella". Aunque intentes usar 8 hilos en un procesador de 8 núcleos, el programa **no será más rápido** que en un solo núcleo, porque los hilos compiten por el GIL y terminan ejecutándose de forma serializada.
b. ¿Por qué en CPython o MRI se recomienda usar procesos en vez de hilos para tareas intensivas en CPU?
	En CPython (la implementación estándar de Python) y MRI (Matz's Ruby Interpreter), los procesos son la alternativa preferida por una razón fundamental: **el aislamiento de memoria**.
	1. **Independencia de GIL:** Cada proceso de Python tiene su **propio intérprete y su propio GIL**. Si lanzas 4 procesos para realizar cálculos pesados, tendrás 4 GILs distintos. Cada proceso puede ser planificado por el sistema operativo en un núcleo diferente, aprovechando el hardware multinúcleo de forma efectiva.
	2. **Paralelismo Real:** Mientras que los hilos con el GIL solo logran "concurrencia" (turnos rápidos), los procesos logran "paralelismo" (ejecución simultánea real).
	3. **Evitar la contención:** Al usar procesos, eliminas la competencia por el mismo bloqueo global, lo que permite que el rendimiento escale casi linealmente con el número de núcleos disponibles.