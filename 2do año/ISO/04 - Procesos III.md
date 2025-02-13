La manera de pensar el algoritmo de planificación depende directamente de la naturaleza de los procesos que va a ejecutar el SO
- CPU-bound: Mayor parte del tiempo utilizando la CPU 
- I/O-bound (I/O = E/S) : Mayor parte del tiempo esperando por I/O 
- La velocidad de la CPU es mucho mas rápida que la de los dispositivos de E/S

# Planificación
Necesidad de determinar cual de todos los procesos que están listos para ejecutarse, se ejecutará a continuación en un ambiente multiprogramado. Puede ser
1. Apropiativo: Un proceso en ejecución es expulsado por el short term.
   ![[apropiaciones.png]]
2. No Apropiativo: Sale por su propia voluntad. Acciones como exit, read, write, sleep o wait
3. Equitativo: Otorga una parte de la CPU a cada proceso.
4. Balance: Mantener ocupadas todas las partes del sistema.

### Archivos Batch != archivos interactivos
Los primeros son automatización los archivos batch están diseñados para ejecutar tareas automáticamente sin requerir la interacción del usuario, no pide datos o entradas adicionales y suelen ejecutarse en segundo plano de modo no apropiativo, liberando al usuario de la necesidad de estar presente. Ideal para tareas repetitivas o de larga duración que no requieren intervención humana. menor riesgo de error humano, ya que no depende de entradas manuales.
Ejemplos de planificadores para procesos Batch
- First Come First Served: (FIFO)
- Shortest Job First (SJF) : Se toma el proceso que menos tiempo de ejecución requiera. Desventaja es determinar el tiempo estimado de ejecución, la predicción la realiza el SO en base a ejecuciones pasadas, desperdiciando tiempo de CPU.

Los segundos son scripts o conjunto de comandos que requieren la interacción del usuario durante su ejecución. Estos scripts suelen pedir entradas o confirmaciones antes de continuar con la ejecución y son apropiativos. Permiten un alto grado de flexibilidad, ya que el usuario puede ajustar su comportamiento según la necesidad del momento y son útiles cuando se necesitan decisiones en tiempo real o cuando los parámetros de entrada varían.
Ejemplos:
- Round Robin: FIFO pero a cada proceso se le asigna un quantum a cada proceso, cuando se le acaba lo envía al final de la cola hasta que termine. Quantums muy pequeños sobrecargan la CPU por cambios de contexto.
- Prioridades: Cada proceso tiene una prioridad ordenada por algún criterio. Pueden producir que algunos procesos entren en inanición si su prioridad es muy baja. Puede ser apropiativo o no apropiativo
- Colas Multinivel: Mezcla de estrategias. Por ejemplo tengo un orden de prioridad y cada una tiene su propia queue con round robin. Para evitar la inanición, la prioridad es variable (inversión de prioridades) , si un proceso lleva mucho tiempo sin ejecutarse se le sube la prioridad, una vez que entra a la CPU se le devuelve su nivel original.
- Shortest remaining time first (SRTF): similar al SJF pero apropiativo, el proceso con menor tiempo restante se apropia la CPU.

### Política y Mecanismo:
El kernel implementa un mecanismo. El algoritmo de planificación debe estar parametrizado, de manera que los procesos/usuarios pueden indicar los parámetros para modificar la planificación. A través de los parámetros se modificar la política. 
Windows Home: 32 niveles de prioridad con un quantum pequeño.
Windows server: 32 niveles de prioridad con un quantum muy grande.
Mismo mecanismo pero con distinta política. 
