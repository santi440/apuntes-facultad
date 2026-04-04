Mirando el código anterior, investigue y responda lo siguiente?
● ¿Para qué sirven los macros SYS_CALL_DEFINE?
	Estas macros son la forma estándar y segura de declarar el punto de entrada de una llamada al sistema en el código del kernel.
	- **El número al final (`DEFINE1`, `DEFINE2`, etc.):** Indica la cantidad de argumentos que recibe la función. Esto es crucial porque el kernel debe gestionar cómo se pasan los parámetros desde los registros de la CPU (espacio de usuario) a la pila del kernel.
	- **Seguridad:** Implementan mecanismos contra vulnerabilidades (como ataques de inyección en registros) y aseguran que los tipos de datos se manejen correctamente entre arquitecturas.
	El `SYSCALL_DEFINEx`La macro no debe usarse directamente, sino más bien `SYSCALL_DEFINE0`, `SYSCALL_DEFINE1`, `SYSCALL_DEFINE2`, etc. dependiendo del número de parámetros que tome su syscall.
	Esta es la forma normal de definir syscalls en Linux. El propósito de las macros es garantizar que los pragmas del compilador apropiado se apliquen a la función: el [prólogo](https://en.wikipedia.org/wiki/Function_prologue) de [la función](https://en.wikipedia.org/wiki/Function_prologue) estándar y las [convenciones de llamadas](https://en.wikipedia.org/wiki/Calling_convention) no se aplican a las llamadas de sistema.

● ¿Para que se utilizan la macros for_each_process y for_each_thread?
	Son macros de iteración que permiten recorrer las estructuras de datos internas del planificador (_scheduler_):
	- **`for_each_process(task)`:** Recorre la lista circular de todos los procesos activos en el sistema. En cada iteración, el puntero `task` apunta al `struct task_struct` (el bloque de control de proceso) del siguiente proceso.
	- **`for_each_thread(task, thread)`:** Dado un proceso padre (`task`), esta macro recorre todos los hilos (LWP - Light Weight Processes) que pertenecen a ese mismo grupo de hilos.
● ¿Para que se utiliza la función copy_to_user?
	verifica que la dirección de destino sea válida y accesible antes de realizar la copia física de los datos desde el buffer del kernel (`kbuffer`) al buffer del usuario (`buffer`).
	El kernel no puede simplemente escribir en un puntero que le pase un programa de usuario, porque ese puntero podría ser inválido, estar protegido o ser un intento de hackear el sistema. Recordar que son areás separadas de memoria 
● ¿Para qué se utiliza la función printk?, ¿porque no la típica printf?
	- **`printk`:** Es la función de impresión del kernel. Envía mensajes al log del sistema (que puedes ver con el comando `dmesg`). Incluye niveles de prioridad (como `KERN_INFO`).
	- **¿Por qué no `printf`?** La función `printf` es parte de la biblioteca estándar de C (`glibc`), la cual corre en el **espacio de usuario**. El kernel es un software independiente que no tiene acceso a las bibliotecas de usuario. El kernel debe proveer sus propias funciones básicas de manejo de strings y salida, ya que él es quien "da la vida" al sistema y no tiene nada "abajo" en lo que apoyarse.
● Podría explicar que hacen las sytem call que hemos incluido?

### Explicación de las System Calls incluidas:
1. **`my_sys_call(int arg)`**: Es una llamada minimalista de prueba. Solo recibe un entero y lo imprime en los mensajes del sistema (`dmesg`). Sirve para verificar que la conexión entre el usuario y el kernel funciona.
2. **`get_task_info(buffer, length)`**:
    - Itera por todos los procesos del sistema.
    - Recolecta el **PID**, el **Nombre** y el **Estado** de cada proceso.
    - Guarda esta info en un buffer intermedio y luego la envía al usuario.
    - _Nota:_ Tiene un límite de 1024 bytes, por lo que en sistemas con muchos procesos, la información podría cortarse.
        
3. **`get_threads_info(buffer, length)`**:
    - Es más compleja que la anterior. Usa `kmalloc` para pedir memoria dinámica en el kernel (2048 bytes).
    - Realiza un "doble ciclo": por cada proceso, busca todos sus hilos asociados.
    - Genera una salida visual tipo árbol (`├── Hilo...`) que muestra la relación jerárquica entre procesos y sus threads.
    - Finalmente, libera la memoria usada con `kfree` para evitar fugas de memoria en el kernel (_memory leaks_).

Makefile para probar la syscall
```make
nombre: get_threads_info.c
	gcc -o get_threads_info get_threads_info.c	
clean: get_threads_info get_threads_info.o
	rm get_threads_info && rm get_threads_info.o
run: get_threads_info.c
	gcc -o get_threads_info get_threads_info.c && ./get_threads_info
```

![[Pasted image 20260402212935.png]]