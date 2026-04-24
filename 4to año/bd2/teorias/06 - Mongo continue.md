La performance pasa a ser lo más importante. 
Escalabilidad horizontal dividir datos en distintos servidores para que cada uno tenga una parte de los datos 
Replicación -> convive junto con sharding manteniendo una fracción del set replicado en las BD para mejorar la disponibilidad y performance y que si un servidor se te cae no perder los datos


ESTA EXPLICADO PARA EL ORTO. Cada particin es una Replica y adentro tenes un shard
# Replica set
Primario: se encarga de decidir sobre las estructuras y los datos
secundario: tiene la capacidad de tomar el rol del primario en forma temporal mientras el primario org no ande 

- ShardKey : a que shard debo ir en base a la funcion de hash
- Mongo me permite mantener documentos embebidos y relaciones para mejorar la performance


Chunk : division logica que mantiene un conjunto de shardskeys permitiendo que el balance no me sature la red y los servidores al transladarlos, si crece más de un limite, se parte y se lo pasa a otro servidor. Un shard tiene muchos chunks 