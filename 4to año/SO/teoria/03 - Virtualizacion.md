abstraccion del hardwrare fisico que tiene la computadora

- Process Level: permite lograr portabilidad entre
diferentes sistemas. Java Virtual Machine (JVM)
- Storage Level: presente una vista lógica del
almacenamiento al usuario, quien no sabe donde están
los datos guardados (RAID, LVM, etc.) Multiples discos tratarlos como uno solo
- Network Level: integra recursos de hardware de red
con recursos de software
-  Operating System Level: SO permite la existencia de
varias instancias de espacio de usuario aisladas
(containers)
- System Level: permite la creación de máquinas virtuales

![[Pasted image 20260408181840.png]]

![[Pasted image 20260408182145.png]]
El kernel del guest corre en modo usuario si ejecuta alguna instruccion que requiere privilegios, esa instruccion es si o si manejada por el hipervisor y le devuelve simulada -> Trap Emulate

Equivalencia / Fidelidad: se ejecuta como que no estuviera virtualizado
Control de recursos / Seguridad: al menos las privilegidas pasan por el VMM
Eficiencia / Performance: La mayor cantidad posible (todo lo que sea en modo usuario) deberia ser tratada sin intervencion del VMM 

No aplicable en arquitectura x86 porque hay instrucciones que son sensibles pero que no son privilegiadas -> popf de modo kernel se tomaria como un popf de modo usuario porque se ejecuta dentro del contexto de una VM = translacion binaria = primera gen

paravirtualizacion = Modificamos el so de la VM para mejorar la performance. Se comunica directamente con la VMM a traves de las hypercalls

Asistida por Hardware = no se modifica el guest pero se cambia una caracteristica de la bios

![[Pasted image 20260408184438.png]]

