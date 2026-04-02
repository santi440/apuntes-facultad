Proceso = unidad principal de asignación de recursos, NO al hilo 
Text logica del programa
![[Pasted image 20260401181457.png]]
originalmente solo habia un hilo, apuntado por la PC. Una secuencialidad absoluta y se bloquea todo hasta qeu no termine la ejecucion de la Instrucción actual.

hilo unidad de procesamiento basico.

---
![[Pasted image 20260401182018.png]]
Limitaciones fisicas. 
debe ser chiquito para que este lo más junto posible pero no tanto como para que la cantidad de transistores generen suficiente calor como para quemar la placa. 
Arch State -> Conjunto de estados del procesador
APIC -> manejador de interrupciones 

Dual core -> procesadores se comunican por la cache. minimiza el tiempo de comunicacion.


Los HT son muy buenos y mejoran en un 20-30% la ejecución pero es necesario que el software se adapte a ellos
![[Pasted image 20260401182804.png]]
Cada hilo comparte espacio de direcciones de procesos (DATA).

![[Pasted image 20260401183722.png]]

TCB
![[Pasted image 20260401190921.png]]

Sincronizacion: Por medio de instrucciones de hilos como pthread_delay_np (sleep es de procesos a nivel so, duerme todo el proceso -> incluso todos sus hilos)

Hilo de usuario
![[Pasted image 20260401194812.png]]
![[Pasted image 20260401194822.png]]
Hilos de kernel
![[Pasted image 20260401195007.png]]
Cantidad de KLT = Cantidad de nucleos pero podria tener más por cuestiones de planificacion.\
El SO que soporta hilos planifica KLT que es el que tiene tiempo de CPU, unidad reconocido y programable por el SO. El SO no conoce ULT, un ULT se mapea a un KLT