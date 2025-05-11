En un examen de la secundaria hay un preceptor y una profesora que deben tomar un examen escrito a 45 alumnos. El preceptor se encarga de darle el enunciado del examen a los alumnos cuando los 45 han llegado (es el mismo enunciado para todos). La profesora se encarga de ir corrigiendo los exámenes de acuerdo con el orden en que los alumnos van entregando. Cada alumno al llegar espera a que le den el enunciado, resuelve el examen, y al terminar lo deja
para que la profesora lo corrija y ==le envíe la nota==. => entiendo me las piro vampiro, sino lo dejo dormido y que me despierten cuando este la nota =>hice esta ult porque me pareció más apropiado.
Nota: maximizar la concurrencia; todos los procesos deben terminar su ejecución; suponga que la profesora tiene una función corregirExamen que recibe un examen y devuelve un entero con la nota.


Cual de las dos maneras?
```c
Monitor preceptor 
//se justifica que sea un proceso y que se comunique con los alumnos por un monitor (aula, por ejemplo) y que los alumnos lleguen y le avise el ultimo al preceptor dormido en su cond personal, similar partido con cancha ejemplo 5, cuando estan todos y este cambie una variable del monitor por el examen y lo tomen todos de ahi?   
	int llegaron = 0;
	cond dormidos;
	Examen prueba;
	
	procedure llegue(Examen test:out){
		llegaron++;
		if(llegaron==45){
			signalAll(dormidos);
		}else{wait(dormidos)}
		test = prueba;
	}
process profesora{
	Examen prueba;
	int alumno; //no se para que quisiera saber de quien es, lo puedo manejar con el monitor sino
	for (int i=1; i<45; i++){
		Escritorio.sig(prueba,alumno);
		double nota = corregirExamen(prueba);
		Escritorio.termine(id,nota)
	}
	
}
process alumno[id:1..45]{
	Examen prueba;
	double nota;
	preceptor.llegue(prueba);
	//lo resuelvo
	Escritorio.entregar(id,prueba,nota);
}
Monitor Escritorio
	cola terminaron;
	int esperando= 0;
	double notas[1..45];
	cond profe;
	cond esperando_nota[1..45]; 
	
	procedure entregar(id: IN int; prueba: IN Examen; nota: OUT double){
		push(terminaron,id,examen);
		signal(profe);
		esperando++;
		wait(esperando_nota[id]);
		nota = pop(resultado);
	}
	
	procedure sig(prueba: out Examen; id: out int){
		if(esperando = 0){
			wait(profe);
		}
		id,prueba = pop(terminaron);
		esperando--; //da igual si lo hago aca o en corregi
	}
	
	procedure corregi(id: IN int; nota IN double){
		notas[id] = nota;
		signal(esperando_nota[id]);
	}
```
Versión preceptor proceso
```c
process preceptor {
	Examen prueba;
	Aula.iniciar(prueba);
}

Monitor Aula
	int llegaron = 0;
	cond prece;
	cond dormidos;
	Examen prueba;
	
	procedure llegue(test:out Examen){
		llegaron++;
		if(llegaron==45){
			signal(prece);
		wait(dormidos);
		test = prueba;
	}
	
	procedure iniciar(test: IN Examen){
		if(terminaron<45){
			wait(prece);
		}
		prueba = test;
		signalAll(dormidos);
	}
process profesora{
	Examen prueba;
	int alumno; //no se para que quisiera saber de quien es, lo puedo manejar con el monitor sino
	for (int i=1; i<45; i++){
		Escritorio.sig(prueba,alumno);
		double nota = corregirExamen(prueba);
		Escritorio.termine(id,nota)
	}
	
}
process alumno[id:1..45]{
	Examen prueba;
	double nota;
	preceptor.llegue(prueba);
	//lo resuelvo
	Escritorio.entregar(id,prueba,nota);
}
Monitor Escritorio
	cola terminaron;
	int esperando= 0;
	double notas[1..45];
	cond profe;
	cond esperando_nota[1..45]; 
	
	procedure entregar(id: IN int; prueba: IN Examen; nota: OUT double){
		push(terminaron,id,examen);
		signal(profe);
		esperando++;
		wait(esperando_nota[id]);
		nota = pop(resultado);
	}
	
	procedure sig(prueba: out Examen; id: out int){
		if(esperando = 0){
			wait(profe);
		}
		id,prueba = pop(terminaron);
		esperando--; //da igual si lo hago aca o en corregi
	}
	
	procedure corregi(id: IN int; nota IN double){
		notas[id] = nota;
		signal(esperando_nota[id]);
	}
```