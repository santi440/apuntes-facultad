En una exposición aeronáutica hay un simulador de vuelo (que debe ser usado con exclusión mutua) y un empleado encargado de administrar su uso. Hay P personas que
esperan a que el empleado lo deje acceder al simulador, lo usa por un rato y se retira.
a. Implemente una solución donde el empleado sólo se ocupa de garantizar que el simulador es usado por una persona a la vez.
b. Modifique la solución anterior para que el empleado además considere el orden de llegada para dar acceso al simulador.
Nota: cada persona usa sólo una vez el simulador

a.
```c
process Empleado::
	int idLocal;
	while true
		Persona[*]?permiso(id)->idLocal = id;
		Persona[idLocal]!usalo();
		Persona[idLocal]?termine();
process Persona[id:0..P-1]::
	Empleado!permiso(id);
	Empleado?usalo();
	usarSimulador();
	Empleado!termine();
```
b.
```c
process Empleado::
	int idLocal;
	while true
		Admin!quienSigue();
		Admin?proximo(id) -> idLocal = id;
		Persona[idLocal]!usalo();
		Persona[idLocal]?termine();
process Admin::
	Cola c;
	do 	Persona[*]?permiso(id)->push(c);
		(not empty(c))Empleado?quienSigue() -> Empleado!proximo(pop(c));
	od
process Persona[id:0..P-1]::
	Admin!permiso(id);
	Empleado?usalo();
	usarSimulador();
	Empleado!termine();
```