Utilice el comando ss (reemplazo de netstat) para obtener la siguiente información de su PC:

-t: tcp
-u: udp
-l:listend
-n: no resuelve nombre


a. Para listar las comunicaciones TCP establecidas.
```c
ss -t state ESTABLISHED
```
b. Para listar las comunicaciones UDP establecidas.
```c
ss -u state ESTABLISHED
```
c. Obtener sólo los servicios TCP que están esperando comunicaciones
```c
ss -lt
```
d. Obtener sólo los servicios UDP que están esperando comunicaciones.
```c
ss -lu
```
e. Repetir los anteriores para visualizar el proceso del sistema asociado a la conexión.
```bash
sudo ss -tp
sudo ss -up
sudo ss -ltp
sudo ss -lup
```
f. Obtenga la misma información planteada en los items anteriores usando el comando netstat.
```c
netstat -t | grep ESTABLISHED
netstat -u
netstat -lt
netstat -lu
sudo netstat -tp | grep ESTABLISHED
netstat -up
netstat -ltp
netstat -lup
```