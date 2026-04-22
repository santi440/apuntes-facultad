# Ejercicio 3
b- Ejecute cada programa individualmente, observe las diferencias y similitudes del
PID y THREAD_ID en cada caso. Conteste en qué mecanismo de concurrencia las
distintas tareas:
i. Comparten el mismo PID y THREAD_ID
ii. Comparten el mismo PID pero con diferente THREAD_ID
iii. Tienen distinto PID

| **Escenario**                         | **Mecanismo**                       | **Comportamiento**                                                                                                                                                                                                             |
| ------------------------------------- | ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **i. Mismo PID y THREAD_ID**          | **Procesamiento Serial (monohilo)** | Si un programa no utiliza hilos ni procesos, todas las tareas se ejecutan secuencialmente en el mismo hilo del mismo proceso. El SO ve un solo identificador. caso 03-ult                                                      |
| **ii. Mismo PID, distinto THREAD_ID** | **Kernel Level Threads (KLT)**      | Al usar `pthread_create()`, cada hilo es una unidad independiente para el kernel. Todos los hilos pertenecen al mismo proceso (comparten `getpid()`), pero el sistema asigna un `gettid()` único a cada uno. Casp 02-tk-thread |
| **iii. Distinto PID**                 | **Multiprocesamiento (fork())**     | Al usar `fork()`, el sistema operativo crea una copia completa del proceso. El hijo es una entidad independiente con su propio PID y su propio espacio de memoria (independientemente del _Copy-On-Write_). Caso 01-subprocess |
![[Pasted image 20260412202806.png]]
c- Ejecute cada programa usando strace (strace ./nombre_programa > /dev/null) y
responda:
i. ¿En qué casos se invoca a la systemcall clone o clone3 y en cuál no? ¿Por
qué?
- 01- subprocess  invoca clone : 
```bash
clone(child_stack=NULL, flags=CLONE_CHILD_CLEARTID|CLONE_CHILD_SETTID|SIGCHLD, child_tidptr=0x7faece7d4a10) = 5336
```
  -  02-kl-thread: invoca clone 3:
```bash   
clone3({flags=CLONE_VM|CLONE_FS|CLONE_FILES|CLONE_SIGHAND|CLONE_THREAD|CLONE_SYSVSEM|CLONE_SETTLS|CLONE_PARENT_SETTID|CLONE_CHILD_CLEARTID, child_tid=0x7f6bfe056990, parent_tid=0x7f6bfe056990, exit_signal=0, stack=0x7f6bfd856000, stack_size=0x7fff80, tls=0x7f6bfe0566c0} => {parent_tid=[5355]}, 88) = 5355
```
- 03-ul-thread: creo que no llama a ninguno

ii. Observe los flags que se pasan al invocar a clone o clone3 y verifique en qué caso se usan los flags CLONE_THREAD y CLONE_VM.
- CLONE_THREAD = Grupos de hilos, denota que se esta creando o asignando un nuevo kernel a nivel de SO (KLT) y como se comparten los recursos
- CLONE_VM = o virtual memory, corresponde a una creacion de un nuevo proceso, similar a fork pero permite compartir recursos como la memoria y no solo una copia del mismo.

iii. Investigue qué significan los flags CLONE_THREAD y CLONE_VM usando la
manpage de clone y explique cómo se relacionan con las diferencias entre
procesos e hilos.
**CLONE_VM** (Virtual Memory)
- **Significado:** Si este flag está activo, el proceso llamador (padre) y el nuevo proceso (hijo) comparten el mismo **espacio de direcciones de memoria**.
- **Efecto:** Cualquier cambio en la memoria (una variable global, el heap) realizado por uno es visible inmediatamente para el otro.
- **Relación:** Es la característica definitoria de los **hilos**. Los procesos tradicionales (creados con `fork()`) no usan este flag y obtienen una copia privada de la memoria
**CLONE_THREAD** (Thread Group)
- **Significado:** Coloca al nuevo proceso en el mismo **grupo de hilos** que el proceso llamador.    
- **Efecto:** El nuevo hilo tendrá el mismo **PID** (Process ID) que el padre, aunque tendrá un **TID** (Thread ID) distinto. Además, comparten señales y el estado de salida.
- **Relación:** Permite que el sistema operativo vea a múltiples flujos de ejecución como una sola entidad (un proceso multihilo).

iv. printf() eventualmente invoca la syscall write (con primer argumento 1,
indicando que el file descriptor donde se escribirá el texto es STDOUT). Vea
la salida de strace y verifique qué invocaciones a write(1, ...) ocurren en
cada caso.
- 01
  ![[Pasted image 20260420163702.png]]
- 02
  ![[Pasted image 20260420163519.png]]
- 03 
![[Pasted image 20260420163751.png]]
algo de que llena un buffer
==ni idea==
v. Pruebe invocar de nuevo strace con la opción -f y vea qué sucede respecto a
las invocaciones a write(1, …). Investigue qué es esa opción en la manpage
de strace. ¿Por qué en el caso del ULT se puede ver la invocación a write(1,
…) por parte del thread hijo aún sin usar -f?
> **-f, --follow-forks**: Trace child processes as they are created by currently traced processes as a result of the fork(2), vfork(2) and clone(2) system calls.

Cuando usas hilos a nivel de kernel (KLT), como los de la librería `pthread` moderna en Linux, cada hilo es para el sistema operativo una "tarea" (task) con su propio ID. Si no usas `-f`, `strace` ignora las llamadas al sistema que realizan estos hilos secundarios porque los considera entidades de ejecución distintas a la principal.

Todos los hilos corren dentro del contexto del hilo principal. El kernel solo ve un proceso haciendo múltiples llamadas a `write()`

4. Resuelva y responda utilizando el contenido del directorio practica3/02-memory:
a. Compile los 3 programas C usando el comando make.
b. Ejecute los 3 programas.
c. Observe qué pasa con la modificación a la variable number en cada caso. ¿Por qué
suceden cosas distintas en cada caso?
![[Pasted image 20260422161035.png]]
Viendo el codigo en c de cada uno,  number es una variable global inicializada en 42 y que el hijo modifica al doble. Por eso:
- en el 1, haciendo un fork, se copia hasta el espacio de direcciones como esta, el padre tiene una variable numer y el proceso hijo otra diferente. Antes de crearlo el padre informa 42, el hijo termina e informa 84 y el padre luego de que termino el hijo 42
- Si se usan KLT se comparte el mismo espacio de direcciones. Por eso ambos terminan informando 84.
- Se usan ULT, mismo caso que el anterior solo que ahora manejado por la libreria en lugar del SO
4. El directorio practica3/03-cpu-bound contiene programas en C y en Python que ejecutan
una tarea CPU-Bound (calcular el enésimo número primo).
a. Ejecute htop en una terminal separada para monitorear el uso de CPU en los
siguientes incisos.
b. Ejecute los distintos ejemplos con make (usar make help para ver cómo) y observe
cómo aparecen los resultados, cuánto tarda cada thread y cuanto tarda el programa
completo en finalizar.
- run_klt: le di 4 nucleos y se los comió con fritas. La foto es cuando termino, durante los 30 seg esos estaban a 100%
  ![[Pasted image 20260422161958.png]]
- run_ult: Me secuestro el cpu2 y se ejecutaron en forma secuencial en lugar de paralela, aunque cada uno tardo menos para mi fueron como 1:15 min.
  ![[Pasted image 20260422162139.png]]
- run_klt_py: Parejo, no paso del 40% en ningun CPU
  ![[Pasted image 20260422162418.png]]
c. ¿Cuántos threads se crean en cada caso?
No se a que hilos se refiere, entiendo el que dice mi amigo htop como thr
- run_klt : 6
- run_ult : 1
- run_klt_py : 6
- run_ult_py: 1.
Si se refiere al id que me informa me da 5 diferentes todos. el 6to en los casos de klt es el hilo main
d. ¿Cómo se comparan los tiempos de ejecución de los programas escritos en C (ult y
klt)?
ult tardo mucho más que el klt = 25seg contra 75seg aprox
e. ¿Cómo se comparan los tiempos de ejecución de los programas escritos en Python
(ult.py y klt.py)?
klt:
![[Pasted image 20260422162918.png]]
ult:
![[Pasted image 20260422163227.png]]
Sorprendente pero ult fue muchisimo mas rapido
f. Modifique la cantidad de threads en los scripts Python con la variable NUM_THREADS para que en ambos casos se creen solamente 2 threads, vuelva a ejecutar y comparar los tiempos. ¿Nota algún cambio? ¿A qué se debe?
klt:
![[Pasted image 20260422163556.png]]

ult: 
![[Pasted image 20260422163727.png]]
Cuantos menos hilos le des para ejecutar a un programa que tenga un GIL mejor.

g. ¿Qué conclusión puede sacar respecto a los ULT en tareas CPU-Bound?

Como son transparentes para el SO no tienen un buen paralelismos pero en los casos en que nos manejamos con lenguajes como python que tienen GIL es mejor
Sin GIL la ejecucion es muy similar a con ULT.

DATO DE COLOR: modificar el common.mk que nos dan para sacarle el network :
```bash
PY_NOGIL = podman run -it --rm --network none -v .:/mnt docker.io/felopez/python-nogil:latest python3 -X gil=0
```
5. El directorio practica3/04-io-bound contiene programas en C y en Python que ejecutan una tarea que simula ser IO-Bound (tiene una llamada a sleep lo que permite interleaving de forma similar al uso de IO).
a. Ejecute htop en una terminal separada para monitorear el uso de CPU en los siguientes incisos.
b. Ejecute los distintos ejemplos con make (usar make help para ver cómo) y observe cómo aparecen los resultados, cuánto tarda cada thread y cuanto tarda el programa completo en finalizar.
- run_klt:
  ![[Pasted image 20260422165616.png]]
- run_ult:
  ![[Pasted image 20260422165848.png]]
- run_klt_py:
  ![[Pasted image 20260422165928.png]]
- run_ult_py:
  ![[Pasted image 20260422165958.png]]
- run_klt_py:
  ![[Pasted image 20260422170029.png]]
No estoy muy seguro que tengo que ver. Son todos muy parecidos y casi no consumieron nada de lo que veo en htop pestaña I/O ni se mueve :(
c. ¿Cómo se comparan los tiempos de ejecución de los programas escritos en C (ult y klt)?
Pos casi igual 
d. ¿Cómo se comparan los tiempos de ejecución de los programas escritos en Python (ult.py y klt.py)?
Pos casi igual
e. ¿Qué conclusión puede sacar respecto a los ULT en tareas IO-Bound?
tener hilos es mejor que no tenerlos, no importa cual sea? 
7. Diríjase nuevamente en la terminal a practica3/03-cpu-bound y modifique klt.py de forma que vuelva a crear 5 threads.
a. Ejecute htop en una terminal separada para monitorear el uso de CPU en los siguientes incisos.
b. Ejecute una versión de Python que tenga el GIL deshabilitado usando: `make run_klt_py_nogil` (esta operación tarda la primera vez ya que necesita descargar un container con una versión de Python compilada explícitamente con el GIL deshabilitado).
c. ¿Cómo se comparan los tiempos de ejecución de klt.py usando la versión normal de Python en contraste con la versión sin GIL?
![[Pasted image 20260422170618.png]]
Mejoro considerablemente 
d. ¿Qué conclusión puede sacar respecto a los KLT con el GIL de Python en tareas CPU-Bound?
El Gil de python es fundamental para mantener consistencias pero en tareas que no necesitan porque se mantiene separados los datos entorpece la ejecución llevando a grandes retardos.
El GIL anula la ventaja principal de los KLT. Convierte un sistema multihilo capaz de paralelismo real en un sistema de **concurrencia aparente**, donde los hilos compiten por un recurso único (el intérprete), degradando el rendimiento en lugar de mejorarlo.
Incluso siendo peor que el uso de ULT porque con KLT tengo un cambio de contexto

- **No hay paralelismo real:** El tiempo total de ejecución será, en el mejor de los casos, igual a ejecutar las tareas una tras otra de forma secuencial.
- **Overhead de contexto:** A menudo, el tiempo de ejecución es **mayor** que en una versión secuencial debido a la sobrecarga que genera el Kernel intentando planificar hilos que pasan todo el tiempo bloqueados luchando por el GIL (_lock contention_).