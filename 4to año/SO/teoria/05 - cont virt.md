# Service isolation
## Chroot
Chroot es una forma de aislar aplicaciones del resto del
sistema. Engaña al proceso para que crea que su directorio raiz es otro.
ej  chroot /usr/local/so ls /tmp
La idea era darle seguridad a los procesos que corre ahi adentro pero se les fueron encontrando fallas de seguridad 

## Control groups
Por defecto el so no tiene prioridad o preferencia por algun proceso por sobre otro en a tiempo de CPU, memoria RAM, “I/O bandwidth”. El kernel no puede determinar cual es mas importante que otra 

La interface de cgroups del kernel es provista mediante un
pseudo-filesystem llamado cgroups. 
Permiten un control “fine-grained” en la alocación, priorización, denegación y monitoreo de los recursos del sistema

Los procesos no saben que estan siendo controlados por un control group sino que ven recursos acotados, finitos. De normal podrian consumir todos los que quiera

control group termina siendo un fs, no todos los controladores se pasaron de la v1 a la v2 -> v1 se empezo a implementar antes de diseñarse. 
Ambas versiones pueden funcionar en un linux a la vez. un proceso no puede estar funcionando en dos cgroups a la vez de la misma jerarquia

 cgroup: asocia un conjunto de procesos con un conjunto de
par´ametros o l´ımites para uno o m´as subsistemas.
• Subsistema: componente del kernel que modifica el
comportamiento de los procesos en un cgroup. Tambi´en
llamado resource controllers o simplemente controllers.
• Jerarqu´ıa: es un conjunto de cgroups organizados en una
jerarqu´ıa.

Un proceso creado mediante un ”fork”pertenece al mismo
cgroup que el padre.

V2 jerarquia unificada
- cgroup.controllers: archivo de solo lectura que indica los controladores disponibles en un cgroup -> lo que esta especificado en el padre no puede ser superado por los hijos 
- cgroup.subtree control: archivo de lectura/escritura que indica los controladores que se habilitar´an en los cgroups hijos. Inicialmente vac´ıo.
![[Pasted image 20260415184208.png]]
En la version 2 solo pueden haber procesos en las hojas y un nivel de jerarquia no pueede habilitarle a su hijo algo que a el no le habilitaron 
events =  Populated: si es 1, este cgroup o alguno de sus descendientes
tiene procesos miembros.
• Frozen: si es 1, este cgroup est´a freezado.
• Permite notificar cuando un cgroup est´a vac´ıo

NameSpace Isolation : el proceso dentro del espacio de nombre cree que el solo posee el recurso. LAs modificaciones no se ven fuera de ese espacio de nombre.
Un proceso solo puede estar en un namespace de un tipo a la
vez.
![[Pasted image 20260415185514.png]]

Espacio de nombres son aislados de los demas pero el level 0 ve todo
![[Pasted image 20260415190102.png]]
User NameSpace
Cualquier usuario que esta dentro de un userspace puede crear nuevos procesos  si los recursos de lo permiten 
![[Pasted image 20260415191112.png]]
comparacion
![[Pasted image 20260415191411.png]]



ACLARACION: un proceso puede estar en un solo control group EN TODA LA JERARQUIA