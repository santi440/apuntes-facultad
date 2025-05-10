Existe una comisión de 50 alumnos que deben realizar tareas de a pares, las cuales son corregidas por un JTP. Cuando los alumnos llegan, forman una fila. Una vez que están todos en fila, el JTP les asigna un número de grupo a cada uno. Para ello, suponga que existe una función AsignarNroGrupo() que retorna un número “aleatorio” del 1 al 25. Cuando un alumno ha recibido su número de grupo, comienza a realizar su tarea. Al terminarla, el alumno le avisa al JTP y espera por su nota. Cuando los dos alumnos del grupo completaron la tarea, el JTP les asigna un puntaje (el primer grupo en terminar tendrá como nota 25, el segundo 24, y así sucesivamente hasta el último que tendrá nota 1).
Nota: el JTP no guarda el número de grupo que le asigna a cada alumno.

```c
Monitor JTP
	int llegaron = 0 
	cond fila
	cond esperaGrupos[1..25];
	int terminaron[1..25] = (25,0);
	int puntaje = 25
procedure llegar(int grupo:out){
	llegaron++
	if(llegaron == 50){
		signalAll(fila)
	}else{wait(fila)}
	grupo = AsignarNroGrupo()
}

procedure termine(int grupo; int nota: out){
	if(terminaron[grupo]++ != 2){
		terminaron[grupo]++
		wait(esperaGrupos[grupo])
	}
	nota = puntaje;
	puntaje--;
	signal(espera[grupo])
}
process alumno[id:1..50]{
	int grupo,nota;
	JTP.llegue(grupo);
	//hago mi tarea de concu
	JTP.termine(grupo,nota);
}
```