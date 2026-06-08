53. Agregar un conjunto llamado airports con los siguientes valores:
eze aep nqn mdz mdq ush fte sla aep nqn brc cpc juj aep tuc eqs

`SADD airports eze aep nqn mdz mdq ush fte sla aep nqn brc cpc juj aep tuc eqs`
54. ¿Cuántos valores tiene el conjunto? ¿Por qué puede diferir de la cantidad de valores ingresados? 
    13 en total. Difiere por los elementos repetidos, un set no te deja tener elementos duplicados como es el caso de aep
55. Listar los valores del conjunto airports.
    `SMEMBERS airports`
56. Quitar el valor "cpc" del conjunto airports.
    `SREM airports cpc`
57. Quitar un valor aleatorio del conjunto airports.
    `SPOP airports` 
58. ¿Qué cantidad de valores tiene airports ahora?
    `SCARD airports` = 11
59. Comprobar si "cpc" es miembro del conjunto airports.
    `SISMEMBERS airports cpc`
60. Mover los valores "sla" y "juj" a un nuevo conjunto denominado noa_airports.
	`SMOVE airports noa_airports  sla` = 0
	`SMOVE airports noa_airports  juj` = 1

 61. Obtener la unión de los conjuntos airports y noa_airports. ¿Modifica los conjuntos originales?
    `SUNION airports noa_airports` = no modifica, es de solo lectura.
62. Realizar la unión de airports y noa_airports y almacenar el resultado en un nuevo conjunto llamado total_airports.
    `SUNIONSTORE total_airports  airports noa_airports`
63. Realizar la intersección entre total_airports y noa_airports.
    `SINTER total_airports noa_airports`
64. Realizar la diferencia entre total_airports y noa_airports.
    `SDIFF total_airports noa_airports`