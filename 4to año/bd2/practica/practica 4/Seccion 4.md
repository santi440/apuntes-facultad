30. Agregar una clave agency con el valor "Cronos Tours".
    `SET agency  "Cronos Tours"`
31. ¿Cuál es el tiempo de vida (TTL) de la clave agency?
    `TTL agency` = -2
32. Agregar una expiración de 30 segundos a la clave agency.
    `EXPIRE agency 30`
33. ¿Cuál es el tiempo de vida de la clave agency luego de agregar la expiración?
    29 y decrementando hasta 0 y de ahi a -2 cuando no existe más
34. Pasados los 30 segundos: ¿cuál es el TTL de agency? ¿Que retorna si se solicita el valor de agency?
    -2 y devuelve (nil)
35. Agregar una clave agency con el valor "Cronos Tours" que expire en 20 segundos desde su creación.
    `SET "Cronos Tours" 20`