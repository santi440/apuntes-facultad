Suponga que N clientes llegan a la cola de un banco y que serán atendidos por sus empleados. Analice el problema y defina qué procesos, recursos y comunicaciones serán necesarios/convenientes para resolver el problema. Luego, resuelva considerando las siguientes situaciones:
a. Existe un único empleado, el cual atiende por orden de llegada.

```c
chan pedidos(int,Texto);
chan termine[N]();

process empleado
	Texto orden; 
	int act;
	Texto resultado;
	for (i=0; i < N; i ++){
		receive pedidos (act, orden);
		resultado = resolver(orden);
		send termine[act] (resultado);
	}
process cliente [id: N]::
	Texto pedir;
	Texto resultado;
	send pedidos (id, pedir);
	receive termine[id](resultado);
```

b. Ídem a) pero considerando que hay 2 empleados para atender, ¿qué debe
modificarse en la solución anterior?
```c
chan pedidos(int,Texto);
chan termine[N]();

process empleado[0..1]
	Texto orden; 
	int act;
	Texto resultado;
	while(true)
		receive pedidos (act, orden);
		resultado = resolver(orden);
		send termine[act] (resultado);
	}
process cliente [id: N]::
	Texto pedir;
	Texto resultado;
	send pedidos (id, pedir);
	receive termine[id](resultado);
```
c. Ídem b) pero considerando que, si no hay clientes para atender, los empleados
realizan tareas administrativas durante 15 minutos. ¿Se puede resolver sin usar
procesos adicionales? ¿Qué consecuencias implicaría?
```c
chan pedidos(int,Texto);
chan termine[N]();
chan laburar(int,Texto);
chan sig(int);

process coordinador::
	int proximo;
	int cli;
	Texto orden;
	while(true)
		receive sig(proximo);
		if(empty(pedidos))
			send laburar[proximo](-1,"No hay laburo");
		else 
			receive pedidos(cli,orden);
			send laburar[proximo](cli,orden);
			

process empleado[id 0..1]::
	Texto orden; 
	int act;
	Texto resultado;
	while(true){
		send sig (id);
		receive laburar[id](act, orden);
		if act <> -1
			resultado = resolver(orden);
			send termine[act] (resultado);
		else
			delay (900);
	}
process cliente [id: N]::
	Texto pedir;
	Texto resultado;
	send pedidos (id, pedir);
	receive termine[id](resultado);
```
Es enteramente necesario el proceso coordinador. La ausencia de este ultimo implicaria que no se cumple con la consigna o que mas de un empleado a la vez intente sacar datos de ese canal, en el caso de que solo halla un pedido, dos lo quieran sacar (empty les da false) pero uno puede y el otro no -> EXPLOTA