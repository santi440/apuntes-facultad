Existen N ==personas== que deben fotocopiar un documento. La ==fotocopiadora== sólo puede ser usada por una persona a la vez. Analice el problema y defina qué procesos, recursos y monitores serán necesarios/convenientes, además de las posibles sincronizaciones requeridas para resolver el problema. Luego, resuelva considerando las siguientes situaciones:
a. Implemente una solución suponiendo no importa el orden de uso. Existe una función Fotocopiar() que simula el uso de la fotocopiadora.
```c
Monitor fotocopiadora
	procedure Fotocopiar()
		usar()
process persona[0..N-1]
	fotocopiadora.usar()
```
b. Modifique la solución de (a) para el caso en que se deba respetar el orden de llegada.
```c
Monitor fotocopiadora
	cond espera;
	boolean libre = true
	int esperando = 0
	procedure Fotocopiar()
		if (libre) {
			libre = false;
			//lo uso
			if(esperando > 0) {
				esperando--;
				signal(espera);
			}
			libre = true;
		}
		else {esperando++; wait(espera)}
process persona[0..N-1]
	fotocopiadora.usar()
```
c. Modifique la solución de (a) para el caso en que se deba dar prioridad de acuerdo con la edad de cada persona (cuando la fotocopiadora está libre la debe usar la persona de mayor edad entre las que estén esperando para usarla).
```c
Monitor fotocopiadora
	cond espera[N];
	int esperando = 0;
	boolean libre = true;
	cola C; 
	procedure solicitar(int id; int edad)
		if(libre){
			libre = false;
			//usar;
		}
		else {esperando++;agregarMayorAMenor(id,edad); wait(espera[id])}
	procedure liberar(){
		if(esperando > 0){
				esperando--;
				signal(espera[pop(C)])
		}else libre = true
	}
process persona[0..N-1]
	fotocopiadora.solicitar()
	//uso fotocopia
	fotocopiadora.liberar()
```
d. Modifique la solución de (a) para el caso en que se deba respetar estrictamente el orden dado por el identificador del proceso (la persona X no puede usar la fotocopiadora hasta que no haya terminado de usarla la persona X-1).
```c
Monitor fotocopiadora
	cond espera[N];
	int sig = 1;
	procedure usar(int id)
		if(sig == id) {
			//usar
			if(id !=N){
				sig++
				signal[sig];
			}
		}
		else {wait(espera[id])}
process persona[1..N]
	fotocopiadora.usar()
```
VERSION GENIO DE LA CONCURRENCIA
```c
Monitor fotocopiadora
	cond espera[N];
	int sig = 1;
	procedure solicitar(int id)
		if(sig != id) {
			wait(espera[id])
		}
		
	procedure liberar(int id)
		if(id !=N){
			sig++
			signal[sig];
		}
process persona[1..N]
	fotocopiadora.usar(id)
	Fotocopiar();
	fotocopiadora.liberar(id)
```
e. Modifique la solución de (b) para el caso en que además haya un Empleado que le indica a cada persona cuando debe usar la fotocopiadora.
```c
Monitor EMPLEADO
	cond espera;
	boolean libre = true
	int esperando = 0
	procedure llegar()
		if (libre) {
			libre = false
		}
		else{esperando++;wait(espera)}
		
	procedure devolver(){
		if(esperando > 0) {
			esperando--
			signal(espera)
		}
		else 
			libre = true
	}
process persona[0..N-1]
	fotocopiadora.llegar()
	Fotocopiar()
	empleado.devolver()
```
f. Modificar la solución (e) para el caso en que sean 10 fotocopiadoras. El empleado le indica a la persona cuál fotocopiadora usar y cuándo hacerlo.
```c
Monitor EMPLEADO
	cond espera;
	cant usando = true
	int esperando = 0
	procedure llegar()
		if (usando>10) {
			usando++
		}
		else{esperando++;wait(espera)}
	procedure devolver(){
		if(esperando > 0) {
			esperando--
			signal(espera)
		}
		else 
			usando--
	}
process persona[0..N-1]
	fotocopiadora.llegar()
	Fotocopiar()
	empleado.devolver()
```