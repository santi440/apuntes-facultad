Un sistema de control cuenta con 4 procesos que realizan chequeos en forma
colaborativa. Para ello, reciben el historial de fallos del día anterior (por simplicidad, de tamaño N). De cada fallo, se conoce su número de identificación (ID) y su nivel de gravedad (0=bajo, 1=intermedio, 2=alto, 3=crítico). Para cada item realice una solución adecuada a lo pedido:
a. Se debe imprimir en pantalla los ID de todos los errores críticos (no importa el orden).
```C
array[N]

process laburadores[id:0..3]
{int inico=id*(M/P);
int fin = (id +1)*(M/P);
for i:=inicio to fin-1 {
	id, gravedad = arreglo[i] 
	if 3 == gravedad then
		print(id)
	}
}
```
b. Se debe calcular la cantidad de fallos por nivel de gravedad, debiendo quedar los resultados en un vector global.
```C
array[N]
sem mutex = 1
ocurrencias[0..3] = (4,0)

process laburadores[id:0..3]
{int inico=id*(M/P);
int fin = (id +1)*(M/P);
for i:=inicio to fin-1 {
	id, gravedad = arreglo[i] 
	P(mutex)
	ocurrencias[gravedad] = ocurrecias[gravedad]++
	V(mutex)
	}
}
```
c. Ídem b) pero cada proceso debe ocuparse de contar los fallos de un nivel de gravedad determinado.
```C
array[N]
ocurrencias[0..3] = (4,0)
process laburadores[id:0..3]
	{for i=0 to =n-1 do {
		idProblema, gravedad = array[i]
		if(id==gravedad){
		//no se proteje porque todos tocan su posicion personal. Podria calcularse local y ponerlo en la pos para que sea "mas eficiente" pero es muy poca la diff
			ocurrencias[gravedad] = ocurrecias[gravedad]+1
		}
	}
}
```
