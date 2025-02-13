### Funciones del SO sobre Memoria
#### Demonio de paginación 
Proceso creado por el SO durante el arranque. Encargado de limpiar las paginas en memoria sincronizándolas con la swap (reduce el tiempo de transferencia porque se realiza con varias paginas continuas y no todas de golpe al final), mantener una cierta cantidad de marcos libres manejando el working set de los procesos (si un proceso tiene una baja tasa de PF, se le reduce la cantidad de marcos asignados) y demorar la liberación de una página hasta que haga falta realmente. Se ejecuta cuando hay poca memoria libre, hay mucha memoria modificada o hay poco uso de CPU.  No requiere de una interrupción, es otro proceso 

#### Memoria Compartida
**Eficiencia**: Procesos que comparten informacion, no tiene sentido mantener copias identicas en memoria (ej: el mismo programa corriendo a la vez), en las tablas de paginas de ambos procesos se referencia a la misma página. Los datos **SIEMPRE** son privados. Lo hace el SO en forma automática (con librerías y código, optimizando el uso de memoria) 
**Colaboración**: Si se desea delegar o trabajo en conjunto de procesos deben tener un espacio de comunicación de información o datos. 

#### Mapeo de Archivos a Memoria
Manera alternativa a las I/O tradicionales, se mapea en forma directa una direccion de memoria con una direccion fisica del archivo, el archivo se sube a memoria dentro del espacio de direcciones del proceso. Cuando se baja la modificacion a memoria es un swap-out al archivo en lugar de a la swap. La info se carga y sube desde el archivo y no desde la swap

#### Copia en Escritura (Copy-on-write)
Permite a los proceso padre e hijo compartir las mismas entradas de tabla de pagina (instruccion fork), con las entradas de datos se las marca (para ambos) como solo lectura. Cuando uno de los procesos intenta modificar la informacion produce una interrupcion por acceso indebido a memoria, genera una nueva pagina de datos identica a la anterior y modifica ambas tablas de pagina. De esta manera, mejora la performance de la instruccion fork, sin duplicar informacion y retrazando la creacion de la nueva pagina de datos lo mas posible, puede que nunca sea necesario. 

#### Área de intercambio 
Puede ser un archivo o un area separada del file system (otra partición).
a) Cada veza que se crea un proceso se reserva un espacio en el área de intercambio igual a las páginas que este va a ocupar. Ocupa área de swap de más. Hay páginas que nunca se van a bajar (código y librerias que ya están guardadas en otro marco).
b) A cada página se le asigna un espacio en el área de swap cuando se realiza el swap-out y se libera cuando tiene sube nuevamente a memoria. Se debe llevar una estructura adicional en memoria que mapee la localización pagina a página en disco. 
Se utiliza la B, Linux en concreto, aprovecha los bits restantes de la PTE ([[07 - Memoria II]]). Sabiendo que el bit V = 0 esta en el área de intercambio, los otros 32 bits pueden ser utilizados para mapear en memoria con un page slot idenfier y un número de área de intercambio (puede haber más de uno)

### Hiperpaginación (thrashing)
 Decimos que un sistema está en thrashing cuando pasa más tiempo paginando que ejecutando procesos, afectando significativamente la performance del sistema. Ocurre solo con reemplazo global y no con local, en local entraria en thrashing los procesos individualmente sin afectar a la totalidad del sistema.
 ![[thrashing.png]]
 Se puede limitar el thrashing con reemplazo local, aun perjudicando al sistema pero siendo mucho mas controlable. Si un proceso cuenta con los frames que necesita no produce thrasing. Maneras de abordar el thrasing:
#### Working Set(Localidad)
El principio de localidad especifica que las paginas recientemente utilizadas es probable que se vuelvan a utilizar y las paginas tienden a aruparse de modo que las paginas siguientes tienen mas chance de utilizarse (localidad temporal y espacial respectivamente).
Working Set se apoya en el principio de localidad y afirma que la localidad de un proceso es el conjunto de paginas actualmente cargadas en memoria. De modo que un programa esta compuesto por varias localidades y mantener las paginas mas activas en memoria reduce la hiperpaginación.
La ventana de WS(Δ) son las referencias de memoria más recientes, siendo Working set: es el conjunto de páginas que tienen las más recientes Δ referencias a páginas (Δ chico: no cubrirá la localidad, y un Δ grande: puede tomar varias localidades).

![[Working set.png]]
- **Cada proceso mantiene su Working Set actualizado** dentro de una ventana de tiempo Δ.
- **Si el número de marcos asignados a un proceso es menor que su Working Set, aumentan los fallos de página**, lo que puede provocar **thrashing**.
- **Si hay demasiadas páginas en el Working Set**, se pueden liberar algunas páginas poco utilizadas para optimizar el uso de memoria.
- **El sistema monitorea la carga global** de la memoria. Si hay demasiados procesos causando thrashing, puede **suspender** temporalmente algunos procesos para liberar memoria.
#### Page Fault Frecuency
Utiliza la tasa de fallos de pagina del proceso para determinar si el proceso tiene un conjunto residente adecuado. Se estableze un PFF maximo y minimo para mantener una tasa de PF aceptable.
Si el proceso excede el maximo aceptable gana frames porque probablemente los necesite. 
Si el proceso no alcanza el minimo aceptable pierde frames para ser utilizado por los procesos que necesiten mas. 
Puede darse que todos los procesos esten por arriba del PFF maximo y no haya frames libres, entonces el SO debe poner en suspensión temporal un proceso 
![[Page Fault Frecuency.png]]