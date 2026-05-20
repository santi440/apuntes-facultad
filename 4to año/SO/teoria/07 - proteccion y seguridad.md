- Proteccion: Mecanismos específicos del SO para resguardar la información dentro de una computadora, para controlar el acceso a los recursos existentes
- Seguridad: cantidad de mecanismo que se aplican en forma correcta. No es algo que se aplica es algo que se logra. Medida de la confianza en que se puede preservar la integridad de un sistema y sus datos. La seguridad se aplica en capas.
![[Pasted image 20260520183430.png]]
Amenazas: 
- encriptacion
- hash

Un sistema informático es una colección de procesos y
objetos
 **Objetos**: de HW (CPU, Memoria, etc.) o de SW (archivos,
programas, semáforos)
Cada objeto debe tener un identificador único que permita
referenciarlo
Los procesos pueden realizar un conjunto finito de
operaciones sobre los objetos
Un **dominio** es un conjunto de pares (objeto, derecho).
Cada par especifica un objeto y un subconjunto de
operaciones que se pueden realizar con él.
Un **derecho** (right) significa autorización para efectuar esas
operaciones.

![[Pasted image 20260520185807.png]]
POLA: necesitas saber que necesita acceder el proceso, en forma declarativa . Se le otorga lo menos posible y si no esta declarado como permitido esta denegado

setuid y setgid: funciona como el bit pegajoso de modo que ahora se ejecuta como root para el usuario actual. casos:
![[Pasted image 20260520190952.png]]
--- ![[Pasted image 20260520191603.png]]
![[Pasted image 20260520191612.png]]PResencia de switch es la que me indica si es dinamica o estatica (no lo tiene).

Generalmente se almacenan solo los elementos ocupados utilizando 2 métodos:
- Almacenarla por filas: Lista de Capacidades por Dominio
- Almacenarla por columnas: Lista de Control de Acceso por objetos
-  Se debe optimizar el acceso para que sea rápido
columna:
![[Pasted image 20260520192450.png]]
o
![[Pasted image 20260520192828.png]]

fila:
![[Pasted image 20260520192755.png]]
