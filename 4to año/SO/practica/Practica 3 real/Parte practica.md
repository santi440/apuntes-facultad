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
ii. Observe los flags que se pasan al invocar a clone o clone3 y verifique en qué
caso se usan los flags CLONE_THREAD y CLONE_VM.
iii. Investigue qué significan los flags CLONE_THREAD y CLONE_VM usando la
manpage de clone y explique cómo se relacionan con las diferencias entre
procesos e hilos.
iv. printf() eventualmente invoca la syscall write (con primer argumento 1,
indicando que el file descriptor donde se escribirá el texto es STDOUT). Vea
la salida de strace y verifique qué invocaciones a write(1, ...) ocurren en
cada caso.
v. Pruebe invocar de nuevo strace con la opción -f y vea qué sucede respecto a
las invocaciones a write(1, …). Investigue qué es esa opción en la manpage
de strace. ¿Por qué en el caso del ULT se puede ver la invocación a write(1,
…) por parte del thread hijo aún sin usar -f?