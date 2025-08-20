# RPC y Rendezvous
RPC (Remote Procedure Call) y Rendezvous -> técnicas de comunicación y sincronización entre procesos que suponen un canal bidireccional. ideales para programar aplicaciones Cliente/Servidor

Un enfoque es declarar un procedure para cada operación y crear un nuevo proceso (al menos conceptualmente) para manejar cada llamado (RPC porque el llamador y el cuerpo del procedure pueden estar en distintas máquinas). Para el cliente, durante la ejecución del servicio, es como si tuviera en su sitio el proceso remoto que lo sirve

El segundo enfoque es hacer rendezvous con un proceso existente. Un rendezvous es servido por una sentencia de Entrada (o accept) que espera una invocación, la procesa y devuelve los resultados

## RPC
Los programas se descomponen en módulos (con procesos y procedures),que pueden residir en espacios de direcciones distintos.
Los procesos de un módulo pueden compartir variables y llamar a procedures de ese módulo.
Un proceso en un módulo puede comunicarse con procesos de otro módulo sólo invocando procedimientos exportados por éste.

La implementación de un llamado intermódulo es distinta que para uno local, ya que los dos módulos pueden estar en distintos espacios: un nuevo proceso sirve el llamado, y los argumentos son pasados como mensajes entre el llamador y el proceso server

Si el proceso llamador y el procedure están en el mismo espacio de direcciones, es posible evitar crear un nuevo proceso.
En general, un llamado será remoto, se debe crear un proceso server o alocarlo de un pool preexistente.
Por sí mismo, RPC es solo un mecanismo de comunicación
Necesitamos que los procesos en un módulo sincronicen (procesos server ejecutando llamados remotos y procesos del módulo). Esto comprende Exclusión Mutua y Sincronización por Condición que puede ser
- Si ejecutan con Exclusión Mutua (de a un proceso) las variables compartidas son protegidas automáticamente contra acceso concurrente, pero es necesario programar sincronización por condición.
- Si pueden ejecutar concurrentemente necesitamos mecanismos para programar exclusión mutua y sincronización por condición (cada módulo es un programa concurrente) podemos usar cualquier método ya descripto (semáforos, monitores, o incluso rendezvous)