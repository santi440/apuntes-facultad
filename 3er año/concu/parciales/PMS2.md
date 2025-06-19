Resolver con PMS (Pasaje de Mensajes SINCRÓNICOS) el siguiente problema. En una carrera hay C corredores y 3 Coordinadores. Al llegar los corredores deben dirigirse a los coordinadores para que cualquiera de ellos le dé el número de "chaleco" con el que van a correr y luego se va. Los coordinadores atienden a los corredores de acuerdo al orden de llegada (cuando un coordinador está libre atiende al primer corredor que está esperando). Nota: maximizar la concurrencia.

```c
process Corredores[id: 0..C-1]::
	int chaleco;
	Admin!llegue(id);
	Coordinador[*]chaleco(chaleco);
process Coordinador[id: 0..2]::
	while true
		Admin!siguiente(id);
		Admin?proximo(corredor);
		int num - determinarNum();
		Corredor[corredor]!(num);
process Admin::
	cola orden
	int coord;
	do
		Corredores[*]?llegue(prox) => {push(orden,prox)}
		□ (not empty(orden)); Coordinador[*]?siguiente(coord) => {Coordinador[coord]!proximo(pop(orden))}
	od
```