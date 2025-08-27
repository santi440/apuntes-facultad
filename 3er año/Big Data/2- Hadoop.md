Es un framework que soporta procesamiento de grandes bases de datos en un ambiente distribuido: Similar a unificación de fileSystems
Ejecuta aplicaciones para el tratamiento de grandes volúmenes de datos
Incluye un sistema de archivos distribuidos (HDFS).
Tolerante a fallas - Si un nodo falla se le asigna su laburo a otros.

Más rápido es tenerlo en registro, luego memoria, luego disco, luego transmisión a otra maquina.

Maneja sistema de bloques de 64MB, si no es multiplo de 64 se desperdicia. Maneja replicas para ser soportable a fallos.
![[Pasted image 20250826153128.png]]
Namenode == inodo
Similar a maestro exclavo

# Map Reduce
Escriba una vez y lea muchas
◦ Paralelización y distribución de tareas automática -> transparente para mi
◦ Escalable -> sea una maquina, una arquitectura o lo que sea funciona igual
◦ Tolerante a fallos
◦ Monitoreo y capacidad de seguridad
◦ Flexibilidad de programación (**Java**, Python, C#, Ruby, C++)
◦ Abstracción al programado

- Fase map: en la que los datos de entrada son procesados, uno a uno,
y transformados en un conjunto intermedio de datos.
- Fase reduce: se reúnen los resultados intermedios y se reducen a un
conjunto de datos resumidos, que es el resultado final de la tarea.

JobTracker : es el master, se encarga de monitorear no de calcular.
TaskTraker: Tiene la fase map y reduce sobre los datos.
Shuffle y sort = fases intermedias que no pueden cambiar el formato.
![[Pasted image 20250826160215.png]]
Utiliza el concepto de clave-valor que pueden ir variando
![[Pasted image 20250826160255.png]]

Las tuplas se manejan de manera independiente, no conoce ni sabe si es la ultima. Cada tupla puede producir de 0, 1 o N tuplas, depende del problema.
La salida va a ser una cantidad de archivos = cantidad de reducer que se hagan