# Monitores
módulos de programa con más estructura, y que pueden ser implementados tan eficientemente como los semáforos.
Mecanismo de abstracción de datos:
• Encapsulan las representaciones de recursos.
• Brindan un conjunto de operaciones que son los únicos medios para manipular esos recursos.
Contiene variables que almacenan el estado del recurso y procedimientos que implementan las operaciones sobre él.
Exclusión mutua implícita asegurando que los procedures en el mismo monitor no ejecutan concurrentemente.

La sincronización por condición es programada explícitamente con variables condición → cond cv;
El valor asociado a cv es una cola de procesos demorados, no visible directamente al programador. Operaciones sobre las variables condición:
- wait(cv) → el proceso se demora al final de la cola de cv y deja el acceso
exclusivo al monitor.
- signal(cv) → despierta al proceso que está al frente de la cola (si hay alguno) y lo
saca de ella. El proceso despertado recién podrá ejecutar cuando readquiera el
acceso exclusivo al monitor.
- signal_all(cv) → despierta todos los procesos demorados en cv, quedando vacía la
cola asociada a cv.
///
Signal and continued -> no suelto el monitor, solo lo desperté por lo que pasa a competir con los demás sin ningún tipo de prioridad o privilegio, puede no asegurar la eventual entrada :(
Signal and wait -> le dejo el monitor y me duermo.
///

- empty(cv) → retorna true si la cola controlada por cv está vacía.
- wait(cv, rank) → el proceso se demora en la cola de cv en orden ascendente de acuerdo al parámetro rank y deja el acceso exclusivo al monitor.
- minrank(cv) → función que retorna el mínimo ranking de demora.