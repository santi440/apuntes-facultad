```c
monitor negocio
	cond orden;
	bool libre = false;
	int esperando = 0;
	cond empl;

procedure llegue(){
	if(!libre){
		esperando++;
		wait(orden);
	}
	libre = false;
	signal(empl)
}

procedure sig(){
	if(esperando == 0){
		libre = true;
	}else{
		esperando--;
		signal(orden);
	}


monitor mostrador
	cond e, c;
	bool llego = false;
	Tarjeta aux;
	text in;

	procedure atencion(text indicaciones: IN ; Tarjeta tar: OUT){
		llego = true;
		in = indicaciones;
		signal(e);
		wait(c);
		tar = aux;
		wait(c);
		signal(e);
	}

	procedure esperar(text instrucciones : out){
		if !llego {
			wait(e);
		}
		instrucciones = in;
	}
	procedure craftTarjeta(Tarjeta tar: IN){
		llego = false;
		aux = tar;
		signal(c);
		wait(e);
		signal(c);
	}
process empleado{
	for int i+ 0; i<c; i++{
		negocio.sig();
		text i;
		mostrador.esperar(i);
		Tarjeta t = HacerTarjeta(i);
		mostrador.craftTarjeta(t);
	}
}
process cliente[0..c-1]{
	negocio.llegue();
	text i = .......;
	Tarjeta t;
	mostrador.atencion(i,t);
}
```