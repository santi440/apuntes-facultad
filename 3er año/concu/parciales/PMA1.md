Resolver con PMA (Pasaje de Mensajes ASINCRÓNICOS) el siguiente problema. En un negocio hay 5 empleados que atienden a N personas que van a pedir un presupuesto, de acuerdo al orden de llegada. Cuando el cliente sabe que empleado lo va a atender le entrega el listado de productos que necesita, y luego el empleado le entrega el presupuesto del mismo. Nota: maximizar la concurrencia. Existe una función HacerPresupuesto (lista) que simula la elaboración del presupuesto por parte de los empleados.

```c
chan esperando(int);
chan pasar[N](int);
chan pedirPresupuesto[5](int,Texto);
chan darPresupuesto[N](Texto);

process persona[id: 0..N-1]::
	Texto lista = determinarQueQuiero();
	Texto presupuesto;
	int empleado;
	send esperando(id);
	receive pasar[id](empleado);
	send pedirPresupuesto[empleado](id,lista);
	receive darPresupuesto[id](presupuesto);


process empleado[id:0..4]::
	int cli;
	while true
		receive esperando(cli);
		send pasar[cli](id);
		receive pedirPresupuesto[id](productos);
		Texto p = presupuestar(productos);
		send darPresupuesto[cli](p);
```