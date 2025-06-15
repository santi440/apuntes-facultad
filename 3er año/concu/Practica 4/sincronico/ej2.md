En un laboratorio de genética veterinaria hay 3 empleados. El primero de ellos
continuamente prepara las muestras de ADN; cada vez que termina, se la envía al segundo empleado y vuelve a su trabajo. El segundo empleado toma cada muestra de ADN preparada, arma el set de análisis que se deben realizar con ella y espera el resultado para archivarlo. Por último, el tercer empleado se encarga de realizar el análisis y devolverle el resultado al segundo empleado.
```c
process Primero::
	while true
		Muestra aux = preparar();
		Admin!(aux);

process Admin::
	cola buffer;
	Muestra solicitud;
		//do guardado, similar al de multiples alternativas
		do Primero?(solicitud){push(cola,solicitud)}
		   not empty(buffer); Segundo?() {Solicitud!(pop(cola))}

process Segundo::
	Muestra muestra;
	Resultado archivo;
	while(true)
		Admin!(); //pide muestras de adn
		Admin?(muestra); // recive laburo, si no hay se queda zzz
		Texto set = armarSet(muestra);
		Tercero!(set);
		Tercero?(archivo);
		archivar(archivo);
		
process Tercero ::
	Texto trabajo;
	Resultado aux;
	while(true)
		Segundo?(trabajo);
		aux = craftearADN(trabajo);
		Segundo!(aux);
```

> [!NOTE] Entiendo que el segundo le va a tomar tiempo hacer el set 
>  Como el enunciado me pide que el segundo siga haciendo muestras de ADN no puede esperar al segundo (como si pasa Segundo -> Tercero).Necesitaria un buffer o proceso admin intermedio que se encargue de recibir los pedidos de Primero  para que siga trabajando y pasarle las cosas a Segundo cuando se desocupe.
