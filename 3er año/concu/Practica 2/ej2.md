Existen N personas que deben ser chequeadas por un detector de metales antes de poder ingresar al avión.
a. Analice el problema y defina qué procesos, recursos y semáforos serán necesarios/convenientes, además de las posibles sincronizaciones requeridas para resolver el problema.
- Procesos> personas
- sem> exclucion general
- variable> detector libre 
- Cola> para controlar un orden y que no sea aleatorio.
b. Implemente una solución que modele el acceso de las personas a un detector (es decir, si el detector está libre la persona lo puede utilizar; en caso contrario, debe esperar).
``` C
	Cola c 
	sem mutex = 1
	boolean libre = true
	sem espera[N] = ([N] 0) //hay otra manera mas prolija de hacerlo pasandole el baton?

	process persona[id:0..N-1]:
	P(mutex)
	if(libre){libre=false;V(mutex)}
	else {push(c,id); V(mutex);P(espera[id])}
	//usa el detector de metales
	P(mutex)
	if(empty(c)){libre=true; V(mutex)}
	else {pop(c,sig), V(espera[sig]); V(mutex)}
```
Respuesta: Es correcto, más no era lo que esperaba este punto. Como no especifica que se respeta el orden puede no hacerlo.
``` C
sem mutex = 1;

process Persona[1..P]
    P(mutex)
    //uso el detector
    V(mutex)
```
c. Modifique su solución para el caso que haya tres detectores.
``` C
	Cola c 
	sem mutex = 3
	sem general = 1
	int sig = -1
	sem espera[N] = ([N] 0) //hay otra manera mas prolija de hacerlo pasandole el baton?

	process persona[id:0..N-1]:
	P(general)
	if(sig == -1){sig=id;P(mutex)}
	else {push(c,sig),P(espera[sig])}
	V(general)
	//uso el recurso
	P(general)
	if(empty(c)){sig = -1}
	else {pop(c,sig), V(espera[sig])}
	V(general)
	V(mutex)	
```

Mismo caso:
``` C
sem mutex = 3;

process Persona[1..P]
    P(mutex)
    //uso el detector
    V(mutex)
```