## Procesos comunicacion
- Pipes y FIFOs (named pipes)
 - Sockets: -> idem redes
	○ UNIX
	○ TCP
	○ UDP
-  Memoria compartida (`shm_open` / `mmap`) -> utiliza semaforos y mutex
- archivos -> creas un archivo para comunicacion dentro del propia file system 
E/S sincronica para memoria no compartida. ´
Zombie = hijo de un proceso que termino pero no tuvo un wait adoptado por el  init. ocupa memoria al pedo

## Threads
Comparten el espacio de direcciones (código, datos, heap), pero cada hilo tiene su propio stack. = base de memoria compartida
TID = mismo funcion que el PID pero para hilos
pthread_self()  no devuelve el TID.
gettid = si lo de arriba 

si pthread_create retorna 0 salio mal :(

### ULT 
Al ser transparente al SO, no puedo ejecutar en paralelo sobre múltiples núcleos. Dependen del proceso
