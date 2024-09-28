1. Proceso se apropie de la CPU
2. Proceso que use E/S, fuera de lo previsto 
3. Acceso indebido a memoria
El SO también compite, como todo proceso, por la CPU. No esta "vivo" o ejecutándose todo el tiempo y es sumamente ineficiente si para cada comprobación debe tomar el control. Por lo tanto se apoya en el HW.
### Limitaciones de modo:
Se definen limitaciones en el conjunto de instrucciones para la ejecución en cada modo (Usuario vs Kernel). Es un bit en la CPU que indica el modo actual, siempre al momento del arranque (y comienzo de un proceso porque context switch se realiza con el fragmento que esta en el proceso que comienza a usar CPU) comienza en modo kernel y cuando se ejecuta un proceso se pasa la modo usuario. La única manera de volver al modo kernel es cuando se produce una interrupción.

![[Kernel vs User.png]]
#### Systems call:
Forma en que los programas de usuario acceden a servicios del SO. Al hacer el llamado a ciertas operaciones, como un read, se ejecuta una librería (diseñada por quien diseño el SO), común a todos los programas. Esta librería identifica el servicio que se quiere ejecutar y lo señaliza en un registro y produce una excepción (interrupción por software) . Cambio de modo, se ejecuta una rutina del vector de interrupciones llamado Sys Call Hundler. Cuando se termina de ejecutar el servicio requerido en modo kernel, vuelve al modo usuario y al puntero (PC) siguiente al que hizo la llamada.

![[Sys Call.png]]
Todas las llamadas a servicios del SO es la misma interrupción, la del Sys call hundler
### Interrupción por Clock:
Evita que un proceso se apropie de la CPU. Junto al clock hay un contador, que se decrementa cada tick de clock, cuando llega a 0 el proceso sale de la CPU y entra otro proceso.

### Protección de memoria:
Se definen limitaciones a las posiciones de memoria que puede acceder cada proceso. El SO carga los procesos en memoria, sabiendo las posiciones que esta utilizando (entre una dirección base y limite) y delega el trabajo de comprobar que los accesos de memoria son dentro del espacio asignado al HW (Si se intenta acceder por fuera -> interrupción por acceso indebido a memoria).

### Tipos de Kernel (diseño)
1. Monolítico: Toda funcionalidad que debe implementar el SO se hace en modo kernel. Se resuelve toda la lógica en modo kernel y luego vuelve a modo usuario. Ventaja: implica menos tiempo por el cambio de contexto, se cambia una cuando entra y otra cuando sale (menos context switch)
2. MicroKernel: Se busca que el kernel sea lo mas chico posible, y tratar de dejar en modo usuario diferentes componentes que hagan de apoyo al kernel. Reduce el tiempo en modo privilegiado (apuntan a la seguridad para evitar errores como el BSOD)

// Linux monolitico Hibrido, es **modular**, lo que significa que se pueden cargar y descargar módulos (drivers y otras funcionalidades) dinámicamente sin necesidad de reiniciar el sistema. Esta característica lo hace similar a los micronúcleos, donde los componentes pueden ser independientes, pero en Linux esto ocurre sin sacrificar el rendimiento. 