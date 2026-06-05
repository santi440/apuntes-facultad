Un conjunto de procesos están en deadlock cuando cada uno de ellos está esperando por un recurso que está siendo usado por otro proceso del mismo conjunto  Un estado de Deadlock puede involucrar recursos de diferentes tipos.

MAjor number = clase de recurso, tipo de driver
minor number = instancias diferentes

Ciclo: conjunto de procesos que estan esperando por un conjunto de recursos que estan siendo utilizados por otro proceso

# Deadlock 
sar un protocolo que asegure que NUNCA se entrará en estado de deadlock
1.1 Prevenir: Que no se cumple alguna de las 4 condiciones
1.2 Evitar: Tomar decisiones de asignación en base al estado del sistema

Los SO, delegan en software de usuarios que no ocurra Deadlock (el kernel confia en diosito)

# Evitar

Un estado seguro garantiza que NO hay deadlock.

Si los recursos necesarios de Pi no están disponibles inmediatamente, entonces Pi puede esperar hasta que todos los Pj hayan terminado

Cuando Pi termina, Pi +1 puede obtener sus recursos necesarios, y así sucesivamente Si no se puede construir esta secuencia, el estado del sistema es inseguro

![[Pasted image 20260603190800.png]]

El algoritmo banquero es re contra improductivo, entonces cada x tiempo corre el algoritmo de banquero, revisar si hay deadlock y recuperarlo 
![[Pasted image 20260603192852.png]]

Con recursos con una sola instancia =  Análisis del grafo de asignación
Con recursos de varias instancias =  Algoritmo del Banquero

![[Pasted image 20260603194019.png]]
puto
me  habia olvidado
por negro puto
se