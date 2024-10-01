## Memoria Virtual
No todo el espacio de direcciones del proceso debe estar en todo momento en memoria (Librerías que se usan una vez, código ya ejecutado y no se vuelve a usar). El SO solo sube a memoria las partes que el proceso va necesitando
**Conjunto residente (working set )**: La porción del espacio de direcciones que se encuentra en memoria. Cuando no se requiere una porción que no esta en el working set se produce una excepción, me estoy apoyando en el hardware.
- Ventajas:
	1. Mayor grado de multiprogramación.
	2. Un proceso puede ser mas grande que la memoria principal, sigue estando limitado por la arquitectura y el bus de direcciones.
### Paginación por demanda
Subir paginas a memoria a medida que se van necesitando. El HW debe poder soportarlo (y/o segmentación) y el SO ser capaz de manejar la carga y descarga de paginas. Se requiere de otro dispositivo (disco), donde guardar temporalmente las paginas que no se usan (swap). El HW solo debe traducir las direcciones requeridas y avisar cuando no se encuentra en memoria.
Cada entrada en la tabla de paginas tiene bits de control:
1. Bit V: La pagina esta en memoria. Validez. Lo modifica el SO, lo usa el HW.
2. Bit M: La página no fue modificada. Se debe reflejar los cambios en memoria secundaria. Lo modifica el HW, lo usa el SO.

> [!NOTE] PTE : Page Table Entry : Entrada de la tabla de paginas.
![[PTE.png]]

#### Page Fault
Cuando la MMU intenta traducir una dirección lógica de una pagina que no se encuentra cargada en memoria (Bit V = 0), se genera un trap, el proceso se bloquea porque la continuidad de ese proceso requiere que la pagina faltante se cargue en memoria (busca un marco libre y ejecuta una operación de E/S, cuando termina el proceso vuelve a Ready to run para competir nuevamente por CPU). 
La E/S finaliza -> interrupción al SO -> actualiza la tabla de paginas (bit V en 1 y dirección del Frame) -> proceso vuelve a Ready -> se vuelve a ejecutar la instrucción que genero el fallo.

### Performance de PxD
Si la taza de fallos es excesivo (tiende a 1) la performance se desploma. Debería ir a buscar una página a disco cada que quiero acceder a una dirección. El fallo de pagina deberia tender a 0, nunca serlo porque la tecnica depende de que existan de vez en cuando (paginación por **demanda**).
![[Tiempo efectivo de acceso.png]]

### Tabla de pagina de dos niveles
Considerando una tabla de paginas como una estructura lineal, llega un punto que no entra en RAM. Por eso se la piensa de mas de un nivel. 
Se explotan las entradas de la tabla de paginas original en n subtablas de paginas. Interpreta la direccion logica de modo que existe un primer nivel de pagina (refenciado por los primeros n bits), que le proporciona la acceso a una entrada de la primera tabla (ahora no esta el frame sino que esta la dirección base del siguiente nivel de tabla). Con la segunda parte de los bits indexa la entrada de esta tabla, donde si se encuentra el Frame, que sumado al desplazamiento permite llegar a la dirección física que busca. Puede ser de N niveles, repitiendo el proceso.
**Ventajas**:
- Toda tabla de paginas, excepto la de primer nivel, se puede paginar. Se cargan y descargan de memoria.
- En 1 nivel tengo espacio ocupado por entradas vacías, en +1 nivel solo tengo subtablas de las secciones que si tengo con información útil. Haciendo potencialmente que ocupen menos memoria. 
**Desventajas**:
- Se requieren mas accesos de la MMU a memoria principal (N accesos, siendo N los niveles) para traducir la dirección física. 