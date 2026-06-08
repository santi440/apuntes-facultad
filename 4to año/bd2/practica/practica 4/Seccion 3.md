24. Obtener todas las claves que empiecen con "v".
	`KEYS v*`
25. Obtener todas las claves que contengan la letra "t".
	`KEYS *t*`
26. Obtener todas las claves que terminan con "age".
	`KEYS *age`
27. Renombrar la clave "package" por "bariloche package".
	`RENAME package "bariloche package"`
28. ¿Qué comando se utiliza para renombrar una clave solo si el nombre destino no existe aún?
    `RENAMENX clave_original clave_destino`
29. Eliminar todas las claves.
    `FLUSHDB`