Resolver el siguiente problema con MONITORES. Simular la atención en una ==Salita== Médica para vacunar contra el coronavirus. Hay UNA ==enfermera== encargada de vacunar a 30 ==pacientes==, cada paciente tiene un turno asignado (valor entero entre 1..30 ya conocido por el paciente). La enfermera atiende a los pacientes de acuerdo al turno que cada uno tiene asignado. Cada paciente al llegar espera a que sea su turno y se dirige al consultorio para que la enfermera lo vacune, y luego se retira. Nota: suponer que existe una función Vacunar() que simula la atención del paciente por parte de la enfermera. Todos los procesos deben terminar.
``` C
process enfermera{
	int proximo;
	for (int i=1; i<30;i++){
		salita.sig(proximo);
		Vacunar(proximo); //creo que podria no pasarlo pero queda facherito
		salita.termine();
	}
}
process paciente[id: 1..30]{
	int turno=..;
	salita.llegue(turno,id);
}
monitor salita
	int sig = 1 
	cola ordenda;
	cond esperando[1..30];
	cond enfermera;
	cond vacunando;
	
	procedure llegue(int turno: in, int id:in){
		if(!(sig == turno)){
			AgregarOrdenado(ordenada,turno,id);
			signal(enfermera);
			wait(esperando[id]);
		}
		wait(vacunando);
		//se va
	}
	procedure sig(int id:out){
		if(ordenada.empty()){
			wait(enfermera);
		}
		int turno;
		turno,id = pop(ordenada);
		signal(esperando[id]);
	}
	
	procedure termine(){
		signal(vacunando);
		sig++;
	}
``` 

Version corregida:
``` C
process enfermera{
	for (int i=1; i<30;i++){
		salita.sig();
		consultorio.ComenzarVacunacion();
		Vacunar(); 
		consultorio.FinVacunacion();
	}
}
process paciente[1..30]{
	int turno=..;
	salita.llegue(turno);
	consultorio.vacuname();
}
monitor salita
	int sig = -1 
	cond esperando[1..30]; //vector segun el turno, no el id como antes
	cond enfermera;
	bool listos[1..30] = (30,false) //vector de que llego, me soluciona un re bardo para saber el proximo
	
	procedure llegue(int turno: in){
		listos[turno]= true;
		if(sig == turno){
			signal(enfermera);
		}
		wait(esperando[id]);
	}
	procedure sig(int id:out){
		sig++;
		if(!listos[sig]){
			wait(enfermera);
		}
		signal(esperando[sig]);
	}
	
Monitor Consultorio //consultorio no existia
	cond enf,pac;
	bool llegoPac = false
	
	procedure vacuname(){
		llegoPac = true;
		signal(enf);
		wait(pac);
		signal(enf);
	}
	
	procedure ComenzarVacunacion(){
		if(!llegoPac){
			wait(enf);
		}
		llegoPac = false;
	}
	
	procedure terminarVacunacion(){
		signal(pac);
		wait(enf);
	}
``` 