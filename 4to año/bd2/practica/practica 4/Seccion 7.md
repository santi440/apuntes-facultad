65. Agregar a un conjunto ordenado llamado passengers los siguientes datos (score nombre):
2.5 federico 4 alejandra 3 julian 1 ivan 2 tomas 2 luciana 2.4 natalia

`ZADD passengers 2.5 federico 4 alejandra 3 julian 1 ivan 2 tomas 2 luciana 2.4 natalia`
66. Obtener los valores del conjunto passengers.
    `ZRANGE passengers 0 -1` = se le puede concatenar WITHSCORE
67. Actualizar el score de luciana a 2.7.
    `ZADD passengers 2.7 luciana`
68. Agregar al conjunto passengers a silvia con score 5.1.
    `ZADD passengers 5.1 silvia`
69. Incrementar en 2 el score de alejandra.
    `ZINCRBY passengers 2 alejandra`
70. Obtener los valores del conjunto passengers con sus scores.
    `ZRANGE passengers 0 -1 WITHSCORES`
71. Obtener los valores del conjunto passengers con sus scores en orden inverso.
    `ZRANGE passengers 0 -1 REV WITHSCORES`
72. Obtener la cantidad de elementos del conjunto passengers.
    `ZCARD passengers`
73. Obtener la cantidad de elementos que tienen scores entre 2 y 3.
    `ZCOUNT passengers 2 3`
74. Obtener el ranking de julian en el conjunto passengers.
    `ZRANK passengers julian`
75. Obtener el score de tomas en el conjunto passengers.
    `ZSCORE passengers tomas`
76. Extraer el elemento con menor score del conjunto passengers.
    `ZPOPMIN passengers`
77. Extraer el elemento con mayor score del conjunto passengers.
    `ZPOPMAX passengers`
78. Eliminar del conjunto passengers al valor silvia.
    `ZREM passengers silvia`