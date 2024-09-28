# Estados de un proceso (como mínimo):
![[Estados Procesos.png]]

### New: 
Se crea la PCB y las estructuras adicionales para administrar el proceso. finaliza cuando el proceso esta cargado en memoria (fue amitido).

### Ready: 
Todas las condiciones para ejecutarse están dadas solo falta la CPU, esta cargado en memoria. El grado de multiprogramación es la cantidad de procesos cargados en memoria donde todos compiten por la CPU.
- Ready -> running: El kernel determina que un proceso debe ejecutarse, se produce un context switch, no preempted.

### Running:  
Utilizando la CPU.
- Running -> ready: Ante una interrupción, se produce un context switch.
- Running -> waiting: sys call

### Waiting: 
Evento de E/S que requiere que el proceso espere hasta que termine, mientras tanto otro proceso toma la CPU para que no sea tiempo muerto. Cuando el evento finaliza se vuelve a cargar en memoria.

### Terminated: 
El proceso termino (exit), se libera memoria, destrucción de las estructuras auxiliares y, por ultimo, borra la PCB

### Apropiación:
Cuando un proceso es sacado del estado de running en contra de su voluntad (por ejemplo, se le acabo el quantum, tiempo asignado). Se produce un context switch completo, un proceso es save y otro cargado.
# Colas de planificación
Hay, al menos, una por cada estado. Utilizando la PCB como una abstracción del proceso, enlazan las PCB(en el mismo estado) con punteros. En general, el SO mantiene una cola de todos los procesos, independientemente del estado. 
Potencialmente en el estado de espera uno puede tener varias colas de planificación, por ejemplo, una cola para cada dispositivo sobre el que se puede esperar. Esto permite una búsqueda mas rápida de PCB especifica.
La cola no necesariamente es FIFO, depende de la planificación que se desee hacer para un manejo eficiente.

# Módulos de la planificación
Son módulos (SW) del Kernel que realizan distintas tareas asociadas a la planificación y se disparan con ciertos eventos (la creación de un nuevo proceso con una llamada al sistema, ...).
No necesariamente deben estar separados (podrian ser uno solo), Los principales planificadores son:
- Scheduler de long Term: Selecciona de los procesos en estado de new a ready. Aumenta el grado de multiprogramación. 
- Scheduler de short Term: Selecciona de entre los procesos que estan en estado de ready a running.
- Scheduler de medium Term (swapping): se encarga de bajar el grado de multiprogramación, saca el espacio de direcciones de un proceso de memoria (swap out) y lo pasa a ready-suspended. En algún momento se realizara el swap in en que vuelve a estado ready. 

(nombre proviene de la frecuencia con que suceden)

otros módulos de planificación, que pueden no existir pero si que se cumpla la función: 
- Dispatcher: Hace el cambio de contexto y modo, "despacha" el proceso elegido por el "short term" saltando a la próxima instrucción a ejecutar.
- Loader: Carga en memoria el proceso elegido por el long term. Determina como ese programa se debe cargar en memoria (estructuras, stacks,etc). Crea el espacio de direcciones
![[Diagrama extendido de estados procesos.png]]