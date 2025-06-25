Para cada una de las siguientes direcciones IP (172.16.58.223/26, 163.10.5.49/27,
128.10.1.0/23, 10.1.0.0/24, 8.40.11.179/12) determine:
a. ¿De qué clase de red es la dirección dada (Clase A, B o C)?
b. ¿Cuál es la dirección de subred?
c. ¿Cuál es la cantidad máxima de hosts que pueden estar en esa subred?
d. ¿Cuál es la dirección de broadcast de esa subred?
e. ¿Cuál es el rango de direcciones IP válidas dentro de la subred?

| ip               | clase | subred        | cant max | broadcast     | rango                       |
| ---------------- | ----- | ------------- | -------- | ------------- | --------------------------- |
| 172.16.58.223/26 | B     | 172.16.58.192 | 62       | 172.16.58.255 | 172.16.58.193-172.16.58.254 |
| 163.10.5.49/27   | B     | 163.10.5.32   | 30       | 163.10.5.63   | 163.10.5.33-<br>163.10.5.62 |
| 128.10.1.0/23    | B     | 128.10.0.0    | 510      | 128.10.1.255  | 128.10.0.1-128.10.1.254     |
| 10.1.0.0/24      | A     | 10.1.0.0      | 254      | 10.1.0.255    | 10.1.0.1-10.1.0.254         |
| 8.40.11.179/12   | A     | 8.32.0.0      | 4094     | 8.47.255.255  | 8.32.00.1-<br>8.47.255.254  |
