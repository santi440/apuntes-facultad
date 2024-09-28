La manera de pensar el algoritmo de planificación depende directamente de la naturaleza de los procesos que va a ejecutar el SO
- CPU-bound: Mayor parte del tiempo utilizando la CPU 
- I/O-bound (I/O = E/S) : Mayor parte del tiempo esperando por I/O 
- La velocidad de la CPU es mucho mas rápida que la de los dispositivos de E/S

# Planificación
Necesidad de determinar cual de todos los procesos que están listos para ejecutarse, se ejecutará a continuación en un ambiente multiprogramado. Puede ser
1. Apropiativo
2. No Apropiativo
3. Equitativo: Otorga una parte de la CPU a cada 