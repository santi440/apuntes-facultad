Resolver con PMS (Pasaje de Mensajes SINCRÓNICOS) el siguiente problema. Simular la atención de una estación de servicio con un único surtidor que tiene un empleado que atiende a los N clientes de acuerdo al orden de llegada. Cada cliente espera hasta que el empleado lo atienda y le indica qué y cuánto cargar; espera hasta que termina de cargarle combustible y se retira. Nota: cada cliente carga combustible sólo una vez; todos los procesos deben terminar.

```c
process Empleado::
	int act; 
	Texto tipo;
	double total;
	for i=1;i<N;i++
		Admin!quieroLaburo();
		Admin?siguiente(act);
		Cliente[act]!pasar();
		Cliente[act]?quiero(tipo,total);
		cargarCombustible();
		Cliente[act]!termine();
	Admin!termine();	
process Admin::
	Cola orden;
	bool fin = false;
	while(!fin)
		if
			Cliente[*]?llegue(act) => push(orden,act);
			□ (not empty(orden)); Empleado?quieroLaburo() => Empleado!siguiente(pop(orden))
			□ Empleado?termine() => fin=true;
		fi
			
process Cliente[id:1..N]::
	Texto tipo = determinarTipo();
	double total = determinarTotalAPagar();
	Admin!llegue(id);
	Empleado?pasar();
	Empleado!quiero(tipo,total);
	Empleado?termine();
```
El tema es que en el if no deterministico tengo el termine que nunca va a ocurrir con las otras dos a la vez, es solo para que no se quede dormido esperando al pedo.
entiendo la opcion de abajo propuesta por la catedra pero estoy mandando muchos "no termines" al pedo.
![[Pasted image 20250618090546.png]]