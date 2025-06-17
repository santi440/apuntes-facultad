Simular la atención en un locutorio con 10 cabinas telefónicas, el cual tiene un empleado que se encarga de atender a N clientes. Al llegar, cada cliente espera hasta que el empleado le indique a qué cabina ir, la usa y luego se dirige al empleado para pagarle. El empleado atiende a los clientes en el orden en que hacen los pedidos, pero siempre dando prioridad a los que terminaron de usar la cabina. A cada cliente se le entrega un ticket factura. Nota: maximizar la concurrencia; suponga que hay una función Cobrar() llamada por el empleado que simula que el empleado le cobra al cliente.
```c
chan llegue(int);
chan hayAlguien();
chan buscarCabina[N](int); //para mantener el orden
chan quieroPagar(int, int);
chan terminar[N](Factura);

process empleado::
	int aux;
	int cabina;
	cola cabinasDisp; // con 10 instancias de cabinas (por simplicidad numero de cabina unico)
	while true
		receive hayAlguien; //dormido hasta que llegue gente
		if(!quieroPagar.empty()){receive quieroPagar(aux,cabina);
								push(cabinasDip,cabina);
								Factura f = Cobrar();
								send terminar[aux](factura);
								}
		else if (!llegue.empty()){receive llegue(aux);
								//llega a venir alguin mientras tengo todas ocupadas me quedo esperando a que alguien libere una cabina
								if(cabinasDisp.empty()){
								//mismo codigo que arriba es un copy+paste descarado de mi propio codigo
									receive quieroPagar(aux,cabina);
									push(cabinasDip,cabina);
									Factura f = Cobrar();
									send terminar[aux](factura);
								}
								send buscarCabina[aux](pop (cabinasDip));
								}
process cliente [id: 0..N-1]::
	int cabina;
	Factura factura;
	send llegue(id);
	receive buscarCabina[id](cabina);
	usarCabina(cabina);
	send quieroPagar(id);
	receive terminar[id](factura);
```

Primero se me ocurrió está pero generaria busy waiting si llega gente y no hay cabinas libres. Sin embargo, es mucho más simple y legible.

```c
chan llegue(int);
chan buscarCabina[N](int); //para mantener el orden
chan quieroPagar(int, int);
chan terminar[N](Factura);

process empleado::
	int aux;
	int cabina;
	cola cabinasDisp; // con 10 instancias de cabinas (por simplicidad numero de cabina unico)
	while true
		if(!quieroPagar.empty()){receive quieroPagar(aux,cabina);
								push(cabinasDip,cabina);
								Factura f = Cobrar();
								send terminar[aux](factura);
								}
		else if (!llegue.empty() && !cabinasDisp.empty()){receive llegue(aux);
														  send buscarCabina[aux](pop (cabinasDip));
								}
process cliente [id: 0..N-1]::
	int cabina;
	Factura factura;
	send llegue(id);
	receive buscarCabina[id](cabina);
	usarCabina(cabina);
	send quieroPagar(id);
	receive terminar[id](factura);
```