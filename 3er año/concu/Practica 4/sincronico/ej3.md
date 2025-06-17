En un examen final hay N alumnos y P profesores. Cada alumno resuelve su examen, lo entrega y espera a que alguno de los profesores lo corrija y le indique la nota. Los
profesores corrigen los exámenes respetando el orden en que los alumnos van entregando.
a. Implemente una solución con PMS considerando que P=1.
b. Implemente una solución con PMS considerando que P>1.
c. Modifique (b) considerando que los alumnos no comienzan a realizar su examen hasta que todos hayan llegado al aula.
Nota: maximizar la concurrencia y no generar demora innecesaria.

a.
```c
process Alumno[id: 0..N-1]::
	int nota;
	Examen e = resolver(examen);
	Admin!corregime(e,id);
	Profesor?notaFinal(nota);

process Admin::
	Cola c;
	do Alumno[*]?corregime(examen,act)->{push(c,(act,examen))}
		(not empty(c)); Profesor?quieroLaburo()->{Profesor!(pop(c))}
	od

process Profesor::
	Examen local;
	int act;
	while(true)
		Admin!quieroLaburo();
		Admin?(act,local);
		int nota = corregir(local);
		Alumno[act]!(nota);
```
b.
```c
process Alumno[id: 0..N-1]::
	int nota;
	Examen e = resolver(examen);
	Admin!corregime(e,id);
	Profesor[*]?notaFinal(nota);

process Admin::
	Cola c;
	do Alumno[*]?corregime(examen,act)->{push(c,(act,examen))}
		(not empty(c)); Profesor[*]?quieroLaburo(act)->{Profesor[act]!(pop(c))}
	od
	
process Profesor[id: 0..P-1]::
	Examen local;
	int act;
	while(true)
		Admin!quieroLaburo(id);
		Admin?(act,local);
		int nota = corregir(local);
		Alumno[act] = nota;
```
c.
```c
process Alumno[id: 0..N-1]::
	int nota;
	Sinc!llegue();
	Sinc?continuen();
	Examen e = resolver(examen);
	Admin!corregime(e,id);
	Profesor[*]?notaFinal(nota);

process Sinc
	for i= 0; i<N; i++
		Alumno[*]?llegue();
	for i=0; i<N; i++
		Alumno[i]!continuen();

process Admin::
	Cola c;
	do Alumno[*]?corregime(examen,act)->{push(c,(act,examen))}
		(not empty(c)); Profesor[*]?quieroLaburo(act)->{Profesor[act]!(pop(c))}
	od
	
process Profesor[id: 0..P-1]::
	Examen local;
	int act;
	while(true)
		Admin!quieroLaburo(id);
		Admin?(act,local);
		int nota = corregir(local);
		Alumno[act] = nota;
```