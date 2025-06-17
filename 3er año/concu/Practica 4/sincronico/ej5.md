En un estadio de fútbol hay una máquina expendedora de gaseosas que debe ser usada por E Espectadores de acuerdo al orden de llegada. Cuando el espectador accede a la máquina en su turno usa la máquina y luego se retira para dejar al siguiente. Nota: cada Espectador una sólo una vez la máquina
```c
process Espectador[id: 0..E-1]::
	Maquina!puedoPasar(id);
	Maquina?pasa();
	usarMaquina();
	Maquina!liberar();
process Maquina::
	cola c;
	bool libre = true;
	do Espectador?puedoPasar(id) -> if(libre) {Espectador[id]!pasa();libre=false} else {push(c,id)}
		Espectador?liberar() -> if(not empty(c)) {Espectador[c.pop()]!pasa()} else {libre = true}
	od
```

Process maquina funciona como quien maneja la exclusion; tambien podria dividirse los if en condiciones del do guardado de modo que si la cola no esta vacia hago esto o si tiene elementos esto otro.