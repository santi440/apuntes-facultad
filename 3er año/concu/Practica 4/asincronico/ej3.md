Se debe modelar el funcionamiento de una casa de comida rápida, en la cual trabajan 2 cocineros y 3 vendedores, y que debe atender a C clientes. El modelado debe considerar que:
- Cada cliente realiza un pedido y luego espera a que se lo entreguen.
- Los pedidos que hacen los clientes son tomados por cualquiera de los vendedores y se lo pasan a los cocineros para que realicen el plato. Cuando no hay pedidos para atender, los vendedores aprovechan para reponer un pack de bebidas de la heladera (tardan entre 1 y 3 minutos para hacer esto).
- Repetidamente cada cocinero toma un pedido pendiente dejado por los vendedores, lo cocina y se lo entrega directamente al cliente correspondiente.
Nota: maximizar la concurrencia.

```c

chan colaEspera(int); 
chan atendidos(int, Pedido);
chan pedirLaburoVendedor(int);
chan vendedores[3](int);
chan entrega[C](Texto);

process Cliente[id: 0..C-1]::
	Texto comida;
	send colaEspera(id);
	receive entrega[id](comida);

process AdminVendedorCliente::
	int aux;
	int idC;
	while(true)
		receive pedirLaburoVendedor(aux);
		if (!colaEspera.empty()) {receive colaEspera(idC)}
		else {idC = -1}
		send vendedores[aux] (idC);

process Cocinero[0..1]::
	int proximoCli;
	Pedido p;
	while(true)
		receive atendidos(proximoCli, p);
		Comida plato = prepararComida(p);
		send entrega[proximoCli](plato);

process Vendedor[id: 0..2]::
	int proximoCli;
	while true
		send pedirLaburoVendedor(id);
		receive vendedores[id](proximoCli); 
		if(proximoCli <> -1) {Pedido p = tomarPedido()
								send atendidos(proximoCli, p)}
		else {reponerPack()}
```