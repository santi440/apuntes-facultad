Resolver con PMA (Pasaje de Mensajes ASINCRÓNICOS) el siguiente problema. En una oficina hay 3 empleados y P personas que van para ser atendidas para realizar un trámite. Cuando una persona llega espera hasta ser atendido por cualquiera de los empleados, le indica el trámite a realizar y espera a que le den el resultado. Los empleados atienden las solicitudes en orden de llegada; si no hay personas esperando, durante 5 minutos resuelven trámites pendientes (simular el proceso de resolver trámites pendientes por medio de un delay). Nota: no generar demora innecesaria; cada persona hace sólo un pedido y termina; los empleados no necesitan terminar su ejecución.

```c
chan quieroLaburar(int);
chan privado[3](int);
chan atender[P](int);
chan tramite[3](Texto);
chan resultado[P](Texto);
chan llegue(int);
process empleado[id:0..2]::
	int cli;
	Texto tipo;
	while true
		send quieroLaburar(id);
		receive privado[id](cli);
		if (cli <> -1) {
			send atender[cli](id);
			receive tramite[id](tipo);
			Texto res = resolver(tipo);
			send resultado[cli](res);
		}else {delay(300)}
		
process Buffer::
	int emp, cli;
	while true
		receive quieroLaburar(emp);
		if (not empty(llegue)) {receive llegue(cli))}
		else {cli = -1}
		send privado[emp](cli);
		
process persona[id:0..P-1]::
	int emp;
	Texto tipo = determinarTipo();
	Texto res;
	send llegue(id);
	receive atender[id](emp);
	send tramite[emp](tipo);
	receive resultado[id](res);
```