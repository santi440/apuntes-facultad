El proceso con id 0 es el swapper que se crea, se carga una imagen del proceso ya existente. 
La ejecucion puede ser
1. padre se continua ejecutando en forma concurrente al hijo.
2. padre espera a que todos sus hijos terminen. Es el menos utilizado, pero por medio de la llamada al sistema wait se puede emular para cumplir con la lógica. no lo determina el SO en su implementación.
### Creación de proceso:
En UNIX, el hijo es un duplicado del proceso padre, comparten espacio de direcciones (fisico), sus datos son independientes. System calls:
- fork(): crea un nuevo proceso. Retorna 2 valores. Para el padre, numero positivo (>0) si fue exitoso (PID del hijo), y negativo (< 0) si fallo. Para el hijo == 0.
- excecv(): carga un nuevo programa dentro de un proceso (en el espacio de direcciones), es destructivo al actual.
![[fork.png]]

Proceso padre puede terminar la ejecución de sus hijos (kill): Terminación en cascada. La tarea asignada al hijo se terminó. Cuando el padre termina su ejecución Habitualmente no se permite a los hijos continuar, pero existe la opción. 