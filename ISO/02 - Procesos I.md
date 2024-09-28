### Proceso : 
Es un programa de usuario en ejecución una entidad de abstracción que solo vive si existe un SO. 
![[proceso y programa.png]]
Como minimo tiene 
- Sección de Codigo
- Sección de datos (se puede modificar)
- Stacks(s): datos temporales. En general, hay mas de una pila (modo usuario y modo kernel)
Y los atributos:
- PID : Process ID
- PPID: Parent Process ID. Todo proceso excepto el 1 es creado por otro proceso
- UID: Usuario que lo disparo, en monousuario no tiene mucho sentido pero como la mayoria son multiusuario aparece.
- GID: Grupo que lo disparo. Determina que tanto puede hacer por UGO (User Group Others)
- Terminal : que terminal lo ejecuto.
### Process Control Block (PCB):
Es una estructura de datos asociada al proceso, también llamada estructura interna de un proceso. Existe una por proceso y se guarda toda la información referente a este (registros, atributos, secciones, estado, prioridad, tiempos).
Primer pasa al crear un proceso y ultimo al eliminarlo.

### Espacio de Direcciones:
Es el conjunto de direcciones de memoria que ocupa el proceso. Independiente de la PCB. En modo usuario solo puedo acceder a mi espacio de direcciones y en los modos establecidos (solo lectura para código, por ejemplo). En modo kernel puedo acceder a otras PCBs o espacio de direcciones de otros procesos.
![[proceso graficamente.png]]

### Context Switch
El contexto incluye toda la información necesaria para administrar el proceso. Cuando se cambia de un proceso a otro se debe resguardar el contexto del programa saliente para cargar el contexto del nuevo programa. El tiempo depende del soporte que tenga el HW pero se considera no productivo para la CPU (es tiempo que no estoy ejecutando ningún proceso).

## Kernel
No es un proceso, por mas que tenga todas las características( estructuras de abstracción, código, compite por CPU, necesita memoria, etc). Es un conjunto de modulos de software.

1. Entidad intendente: Tiene su propia región de memoria y stack. Cada que se produce una interrupción, se produce un context switch y el kernel como entidad separada se carga. La desventaja es que cada vez que se produce una interrupción por clock hay  tiempos ociosos para cambiar el contexto y se produce una sobrecarga, un desperdicio de ciclos de CPU.
2. En todos los procesos: El “Código” del Kernel (no por duplicado, similar a librería) se encuentra dentro del espacio de direcciones de cada proceso y la ejecución se realiza en el mismo contexto del proceso de usuario (implica el cambio de modo y el salto de dirección). Si bien puede compartir una misma pila con el proceso, por motivos de seguridad (sino desde usuario podria acceder a elementos del modo kernel) debería estar separada. Como el espacio de direcciones de un proceso esta acotado por la arquitectura ( en 32 bits máximo 4GB), al contener al kernel, estoy reduciendo el espacio de memoria de mi proceso. 