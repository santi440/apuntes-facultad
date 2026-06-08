9. Agregar una clave package con el valor "Bariloche 3 days".
   `SET package "Bariloche 3 days"`
10. Agregar una clave user con el valor "Turismo BD2". Obtener el valor de la clave user.
    `SET user "Turismo BD2"`
    `GET user`
11. Obtener todas las claves almacenadas actualmente.
    `KEYS *`
12. Agregar una clave user con el valor "Cronos Turismo". ¿Cuál es el valor actual de la clave user?
	 `SET user "Cronos Turismo"`
    `GET user
    El valor es Cronos Turismo
13. Concatenar " S.A." a la clave user. ¿Cuál es el valor actual de la clave user?
    `APPEND user "S.A"`
    `GET user` = Cronos TurismoS.A
14. Eliminar la clave user. ¿Qué valor retorna si se intenta obtener la clave user luego de eliminarla?
    `DEL user`
    `GET user` = (nil)
15. Verificar si existe la clave visits.
    `EXISTS visit` = (integer) 0
16. Agregar una clave visits con el valor 0.
    `SET visits 0`
17. Incrementar en 1 la clave visits. ¿Cuál es el valor actual?
    `INCR visits` = 1
18. Incrementar en 5 la clave visits. ¿Cuál es el valor actual?
	`INCRBY visits 5` = 6
19. Decrementar en 1 la clave visits. ¿Cuál es el valor actual?
    `DECR visits` = 5
20. Incrementar en 2 la clave visits. ¿Cuál es el valor actual?
    `INCRBY visits 2` = 7
21. Agregar una clave "value package" con el valor 539789.32.
    `set "value package" 539789.32`
22. Incrementar en 20000 la clave "value package". ¿Cuál es el valor actual?
	`INCRBYFLOAT "value package" 20000` = 559789.31999999999999318
23. ¿Cual es el tipo de datos de "value package", visits y user?
```bash
127.0.0.1:6379> TYPE "value package"
string
127.0.0.1:6379> TYPE user
none
127.0.0.1:6379> TYPE visits
string

```