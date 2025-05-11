Se debe simular una maratón con C corredores donde en la llegada hay UNA máquina expendedoras de agua con capacidad para 20 botellas. Además, existe un repositor encargado de reponer las botellas de la máquina. Cuando los C corredores han llegado al inicio comienza  la carrera. Cuando un corredor termina la carrera se dirigen a la máquina expendedora, espera su turno (respetando el orden de llegada), saca una botella y se retira. Si encuentra la máquina sin botellas, le avisa al repositor para que cargue nuevamente la máquina con 20 botellas; espera a que se haga la recarga; saca una botella y se retira.
**Nota**: mientras se reponen las botellas se debe permitir que otros corredores se encolen.

```c
process corredor[1..C]{
	carrera.llegue();
	//corre
	//termina la carrera
	expendedora.sacarBotella()
}
process repositor{
	expendedora.llegue();
	while(true){
		//cargo las botellas
		expendedora.termine()
	}
}
Monitor carrera
	cond espera;
	int llegaron = 0;
	procedure llegue(){
		llegaron++;
		if(llegaron == C){
			signalAll(espera);
		}else{wait(espera)}
	}
Monitor expendedora
	cond fila;
	cond repo; //supongo que el repositor puede no arrancar parado al lado de la maquina
	int botellas = 20; //supongo que arranca cargada, sino tmb funciona :)
	procedure sacarBotella(){
		if((botellas == 0)){
			signal(repo);
			wait(fila);
		}
		botellas--;
		signal(fila);
	}
	
	procedure llegue(){
		if(botellas > 0){wait(repo);}
	}
	procedure termine(){
		botellas = 20;
		signal(fila);
		wait(repo);
	}
```