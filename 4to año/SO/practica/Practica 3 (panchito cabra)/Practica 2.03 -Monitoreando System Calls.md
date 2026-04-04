1. Ejecute el programa anteriormente compilado
```zsh
./get_threads_info
``` 
Cual es el output del programa?
```zsh
so@so:~$ make run 
gcc -o get_threads_info get_threads_info.c && ./get_threads_info

Información de los hilos en ejecución:
----------------------------------------
PID: 1 | Nombre: systemd | Estado: 1 
PID: 2 | Nombre: kthreadd | Estado: 1 
PID: 3 | Nombre: pool_workqueue_ | Estado: 1 
PID: 4 | Nombre: kworker/R-rcu_g | Estado: 8 
PID: 5 | Nombre: kworker/R-sync_ | Estado: 8 
PID: 6 | Nombre: kworker/R-slub_ | Estado: 8 
PID: 7 | Nombre: kworker/R-netns | Estado: 8 
PID: 10 | Nombre: kworker/0:0H | Estado: 8 
PID: 11 | Nombre: kworker/u16:0 | Estado: 8 
PID: 12 | Nombre: kworker/R-mm_pe | Estado: 8 
PID: 13 | Nombre: rcu_tasks_kthre | Estado: 8 
PID: 14 | Nombre: rcu_tasks_rude_ | Estado: 8 
PID: 15 | Nombre: rcu_tasks_trace | Estado: 8 
PID: 16 | Nombre: ksoftirqd/0 | Estado: 1 
PID: 17 | Nombre: rcu_preempt | Estado: 8 
PID: 18 | Nombre: rcu_exp_par_gp_ | Estado: 1 
PID: 19 | Nombre: rcu_exp_gp_kthr | Estado: 1 
PID: 20 | Nombre: migration/0 | Estado: 1 
PID: 21 | Nombre: idle_inject/0 | Estado: 1 
PID: 22 | Nombre: cpuhp/0 | Estado: 1 
PID: 23 | Nombre: cpuhp/1 | Estado: 1 
PID: 24 | Nombre: idle_inject/1 | Estado: 1 
PID: 25 | Nombre: migration/1 | Estado: 1 
PID: 
----------------------------------------

```
2. Luego de ejecutar el programa ahora ejecute
``` bash
sudo dmesg
```
¿Cuál es el output? porque?(recuerde printk y lea el man de dmesg)
	que no conoce sudo porque alguien se lo saco -.-
	El output es el **registro de mensajes del buffer circular del kernel**
	- Al principio ves mensajes del **proceso de arranque** (reconocimiento de hardware, montaje de discos, inicio de systemd).
	- Al final (a partir del segundo `663.112201`) aparecen bloques de texto que dicen `PID: X | Nombre: Y`. **Esas son las ejecuciones de tu syscall.**
	¿Por qué aparece eso ahí? (El rol de `printk`)
	Tenés que explicar que en el código de tu syscall usaste la función `printk()`.
	- **A diferencia de `printf`**, que escribe en la "salida estándar" (la terminal) de un proceso de usuario, `printk` escribe en el **log interno del kernel**.
	- Como el kernel no tiene una terminal propia, guarda esos mensajes en un buffer de memoria.
	El comando `dmesg` (display message) se utiliza precisamente para **leer ese buffer circular**.
	- Por eso, cuando corrés tu programa de usuario, no ves los PIDs en la pantalla de tu programa, sino que tenés que ejecutar `dmesg` para "extraer" lo que el kernel escribió mientras ejecutaba tu lógica.
3. Ejecute el programa anteriormente compilado con la herramienta strace
$ strace get_threads_info
Aclaración: Si el programa strace no está instalado, puede instalarlo en distribuciones basadas
en Debian con:
$ sudo apt-get install strace

En alguna parte del log de strace debería ver algo similar a lo siguiente:
```bash
syscall_0x1d4(0xffffed6696a8, 0x400, 0xaaaaadbf08b4, 0xffffb7fcb140, 0x93bb0cc6fda41a0e,
0xffffb7f71e78) = 0x400
```
Si luego ejecuto
```bash
echo $((0x1d4))
```

¿Qué valor obtengo? porque?
468
Valor numerico que representa a la syscall que implementamos :)
