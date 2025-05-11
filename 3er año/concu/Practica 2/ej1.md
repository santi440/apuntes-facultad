Un sistema operativo mantiene 5 instancias de un recurso almacenadas en una cola.Además, existen P procesos que necesitan usar una instancia del recurso. Para eso, deben sacar la instancia de la cola antes de usarla. Una vez usada, la instancia debe ser encolada nuevamente.
``` C
Cola c // de vuelta no me importa que hayan 5 o deberia considerarlo?
sem mutex = 1
sem existe = 5

process proceso [0..P-1]:
	recurso r;
	P(existe)
	P(mutex)
	r = Sacar(c)
	V(mutex)
	//usar el recurso 
	P(mutex)
	Agregar(c,r)
	V(mutex)
	V(existe)
```
