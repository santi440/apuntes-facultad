En un ==corralón== de materiales se deben atender a N ==clientes== de acuerdo con el ==orden de llegada==. Cuando un cliente es llamado para ser atendido, ==entrega una lista ==con los productos que comprará, y espera a que alguno de los ==empleados== le entregue ==el comprobante de la compran== realizada.
a. Implemente una solución considerando que el corralón tiene un único empleado y cada cliente hace un único pedido. El empleado debe terminar su ejecución.
```c
Monitor corralon
	cond clientes;
	int esperando = 0
	bool libre = true


procedure Llegar(){
		if(!libre){
			esperando++;
			wait(clientes);
		}
		else{
			libre = false;
		}
}
procedure Proximo(){
		if(esperando > 0){
			esperando--;
			signal(colaCliente);
		}
		else{
			libre = true;
		}
}

Monitor Escritorio{
bool llego = false;
cond Empleado;
cond Cliente;
ListaProducto lista;
Recibo recibo;
	Procedure Atencion(listaProd: in ListaProducto, R: out Recibo){
		lista = listaProd;
		llego = true;
		signal(Empleado); //aviso al empleado que llegué
		wait(Cliente); //me duermo hasta que me de el recibo
		R = recibo;
		signal(Empleado); //le aviso al empleado que me llegó el recibo
	}
	
	Procedure EsperarLista(l: out ListaProducto){
		if(!llego){
			wait(Empleado) //me duermo hasta que llegue el cliente
		}
		lista = ListaProd; //recibo los productos del cliente
	}
	
	Procedure EntregarRecibo(r: in Recibo){
		recibo = r;
		signal(Cliente); //le aviso al cliente que agarre el recibo
		wait(Empleado); //me duermo para que el cliente me avise cuando lo recibe
		listo = false; //espero otro cliente
	}
}

process Empleado{
	ListaProducto lista;
	Recibo recibo;
	while(true){
		Corralon.Proximo();
		Escritorio.EsperarLista(lista); //espero que me mande los productos
		recibo = armarRecibo(lista);
		Escritorio.EntregarRecibo();
	}
}

Process Cliente[id: 1..N]{
	ListaProducto lista;
	Recibo recibo;
	lista = crafteoLista();
	Corralon.Llegar(idE);
	Escritorio.Atencion(lista, recibo); //entrego la lista y espero el recibo
}
```
b. Implemente una solución considerando que el corralón tiene E empleados (E > 1). Los empleados no deben terminar su ejecución.
```c
Monitor Corralon{
	cond colaCliente;
	cola colaEmpleado;
	int empleadosLibres = 0;
	int esperando = 0;
	procedure Llegar(idE: out int){
		if(empleadosLibres == 0){
			esperando++;
			wait(colaCliente);
		}
		else{
			empleadosLibres--;
		}
		cola.pop(colaEmpleado, idE);
	}
	procedure Proximo(idE: in int){
		push(colaEmpleado, idE);
		if(esperando > 0){
			esperando--;
			signal(colaCliente);
		}
		else{
			empleadosLibres++;
		}
	}
}

Monitor Escritorio[id: 1..E]{
bool llego = false;
cond Empleado;
cond Cliente;
ListaProducto lista;
Recibo recibo;
	Procedure Atencion(listaProd: in ListaProducto, R: out Recibo){
		lista = listaProd;
		llego = true;
		signal(Empleado); //aviso al empleado que llegué
		wait(); //me duermo hasta que me de el recibo
		R = recibo;
		signal(Empleado); //le aviso al empleado que me llegó el recibo
	}
	
	Procedure EsperarLista(l: out ListaProducto){
		if(!llego){
			wait(Empleado) //me duermo hasta que llegue el cliente
		}
		lista = ListaProd; //recibo los productos del cliente
	}
	
	Procedure EntregarRecibo(r: in Recibo){
		recibo = r;
		signal(Cliente); //le aviso al cliente que agarre el recibo
		wait(Empleado); //me duermo para que el cliente me avise cuando lo recibe
		listo = false; //espero otro cliente
	}
}

Process Empleado[id: 1..E]{
	ListaProducto lista;
	Recibo recibo;
	while(true){
		Corralon.Proximo(id);
		Escritorio.EsperarLista(lista); //espero que me mande los productos
		recibo = armarRecibo(lista);
		Escritorio.EntregarRecibo();
	}
}

Process Cliente[id: 1..N]{
	int idE;
	ListaProducto lista;
	Recibo recibo;
	lista = crafteoLista();
	Corralon.Llegar(idE);
	Escritorio[idE].Atencion(lista, recibo); //entrego la lista y espero el recibo
}
```
c. Modifique la solución (b) considerando que los empleados deben terminar su ejecución cuando se hayan atendido todos los clientes (cada cliente hace un único pedido).

```c
Monitor Corralon{
	cond colaCliente;
	cola colaEmpleado;
	int empleadosLibres = 0;
	int esperando = 0;
	int hecho = 0;
	procedure Llegar(idE: out int){
		if(empleadosLibres == 0){
			esperando++;
			wait(colaCliente);
		}
		else{
			empleadosLibres--;
		}
		cola.pop(colaEmpleado, idE);
		hecho++;
	}
	procedure Proximo(idE: in int){
		push(colaEmpleado, idE);
		if(esperando > 0){
			esperando--;
			signal(colaCliente);
		}
		else{
			empleadosLibres++;
		}
	}
	procedure sigo(bool fin: out){
		if(hecho = N){
			fin = false
		}
		else fin = true
	}
}

Monitor Escritorio[id: 1..E]{
	bool llego = false;
	cond Empleado;
	cond Cliente;
	ListaProducto lista;
	Recibo recibo;
	Procedure Atencion(listaProd: in ListaProducto, R: out Recibo){
		lista = listaProd;
		llego = true;
		signal(Empleado); //aviso al empleado que llegué
		wait(); //me duermo hasta que me de el recibo
		R = recibo;
		signal(Empleado); //le aviso al empleado que me llegó el recibo
	}
	
	Procedure EsperarLista(l: out ListaProducto){
		if(!llego){
			wait(Empleado) //me duermo hasta que llegue el cliente
		}
		lista = ListaProd; //recibo los productos del cliente
	}
	
	Procedure EntregarRecibo(r: in Recibo){
		recibo = r;
		signal(Cliente); //le aviso al cliente que agarre el recibo
		wait(Empleado); //me duermo para que el cliente me avise cuando lo recibe
		listo = false; //espero otro cliente
	}
}

Process Empleado[id: 1..E]{
	ListaProducto lista;
	Recibo recibo;
	bool sigo;
	Corralon.sigo(sigo);
	while(sigo){
		Corralon.Proximo(id);
		Escritorio.EsperarLista(lista); //espero que me mande los productos
		recibo = armarRecibo(lista);
		Escritorio.EntregarRecibo();
		Corralon.sigo(sigo);
	}
}

Process Cliente[id: 1..N]{
	int idE;
	ListaProducto lista;
	Recibo recibo;
	lista = crafteoLista();
	Corralon.Llegar(idE);
	Escritorio[idE].Atencion(lista, recibo); //entrego la lista y espero el recibo
}