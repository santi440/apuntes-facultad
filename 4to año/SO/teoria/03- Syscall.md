Es el mecanismo utilizado por un proceso de usuario
para solicitar un servicio al Sistema Operativo (SO).
 Los procesos se comportan, en criollo, en su cajita (modo usuario), un entorno  controlado y limitado con su propio espacio de direcciones y sin poder acceder al hardware. -> es a través de las llamadas al SO por su API (las syscall) para ejecutar acciones que necesitan de mayores privilegios.
 ![[Pasted image 20260325182511.png]]
 GNU C library = funciones que se llaman igual en Windows o linux.
 Se puede tambien llamar directamente a una syscall pero perdes portabilidad.

una system call tiene como maximo 6 parametros

HAL: Hardware abstraccion level = la forma en que podes trabajar con plug and play con la mayoria del hardware en windows
