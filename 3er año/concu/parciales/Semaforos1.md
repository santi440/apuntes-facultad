Resolver con SEMAFOROS el siguiente problema. Una empresa de turismo posee ==UN micro== con capacidad para 50 personas. Hay un único ==vendedor== que atiende a los ==C clientes (C > 50)== que intentan comprar un pasaje de acuerdo al orden de llegada (suponga que la atención de un cliente tarda un par de minutos), si aún hay lugares disponibles le indica el número de asiento que le tocó. Cada cliente, luego de ser atendidos por el vendedor, se dirige al micro para subir en caso de que le hayan dado un asiento, y en caso contrario se retira sin viajar. El micro espera a que los 50 pasajeros hayan subido para realizar el viaje. Nota: maximizar la concurrencia.
``` C
sem micro = 0;
sem esperando[1..C]= (C,0);
int pasaje[1..C]= (C,0); //cola?
sem vende = 0;
sem semCola = 1;
cola c;


process vendedor{
	int vendibles = 50;
	for (int i=1; i<C; i++){ //tenia un while (pendejo)
		//espero a que alguien venga a comprar
		P(vende);
		//tomo la cola
		P(semCola);
		int id = pop(c);
		V(semCola);
		
		if(vendibles>0){
			//vendo pasaje
			delay(120) //par de minutos => dos min
			pasaje[id] = vendibles;
			vendibles--;
		}else{
			pasaje[id] = -1;
		}
		V(esperando[id]);
	}
}	
process cliente[id:1..C]{
	//orden de llegada
	P(semCola);
	push(c,id);
	V(semCola);
	V(vende);
	//espero a que me dejen pasar
	P(esperando[id]);
	int boleto = pasaje[id];
	if(boleto != (-1)){
		V(micro)
	}else //me retiro sin viajar muy triste
}
process micro{
	for (int i= 1; i<50; i++){
		P(micro);
	}
	//llegaron los 50 viajamos
}
``` 