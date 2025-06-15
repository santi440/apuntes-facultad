Se desea modelar el funcionamiento de un banco en el cual existen 5 cajas para realizar pagos. Existen P clientes que desean hacer un pago. Para esto, cada una selecciona la caja donde hay menos personas esperando; una vez seleccionada, espera a ser atendido. En cada caja, los clientes son atendidos por orden de llegada por los cajeros. Luego del pago, se les entrega un comprobante. Nota: maximizando la concurrencia.
```c
chan llegue(int);
chan buscarCaja[0..P-1](int);
chan comprobante[0..P-1] (texto);
chan esperando[1..5] (int, texto); //texto = pago que quiere hacer :)
chan liberarCaja(int);

process cliente[id : 0..P-1]::
	Texto pedido;
	int caja;
	Texto resultado;
	//llego al banco
	send llegue(id);
	//espero a que me digan a que caja tengo que ir
	receive buscarCaja[id](caja);
	//hago la cola
	send esperando[caja] (id, pedido);
	//me atienden y dan el aviso que termino
	receive comprobante(resultado);
	
process cajero[id: 0..5-1]::
	int atendiendo;
	Texto pedido;
	while true
		receive esperando[id](atendiendo,pedido);
		send liberarCaja(id);
		Texto resultado = resolver(pedido);
		send comprobante[id] (resultado);
process admin::
	int tamaño[0..4] = [5,0] // las 5 pos con 0
	int cli; 
	int caja;
	while true
	//if de multiples alternativas no deterministico
	//podria producir busy waiting si no hay nada que hacer pero no le da prioridad a ninguno
		if !empty(llegue){receive llegue(cli);
						  int min = minimo(tamaño);
						  tamaño[min]++;
						  send buscarCaja[cli](min);
						  }
			!empty(liberarCaja){receive liberarCaja(caja);
								tamaño[caja]--}
		
		
```