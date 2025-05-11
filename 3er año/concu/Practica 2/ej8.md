Una fábrica de piezas metálicas debe producir T piezas por día. Para eso, cuenta con E empleados que se ocupan de producir las piezas de a una por vez. La fábrica empieza a
producir una vez que todos los empleados llegaron. Mientras haya piezas por fabricar, los empleados tomarán una y la realizarán. Cada empleado puede tardar distinto tiempo en
fabricar una pieza. Al finalizar el día, se debe conocer cuál es el empleado que más piezas fabricó.
a. Implemente una solución asumiendo que T > E.
Arranque haciendo el (a), sin querer llegue a la solución del (b). Consulte y para hacerlo es lo mismo solo que se chequea después de que cada empleado hizo una pieza. En lo personal le veo lagunas, podría ser que algunos Empleados sean muchísimo mas rápidos y terminar todas las piezas antes de la primera pasada de los mas perezosos o con menos suerte pero discutimos 5 min y fue la única opción que me dio => no lo pienso hacer, mi (b) tmb cumple el (a)
b. Implemente una solución que contemple cualquier valor de T y E (incluso T<E).

Esta todo hecho con un semáforo, según el ayudante seria mas fácil y mas lindo de ver con dos semáforos diferentes. A mi no me disgusta esta solución y no encontramos escenario en el que falle.
```C
int cant = 0
int piezas = T
sem dormir = 0
sem mutex = 1
int max = -1
int idmax
process  empleado
    P(mutex)
    cant++
    if (cant == E)
        for int i=1 to E-1
            V(dormir)
    else
        P(domir)
	V(mutex)
	int fabrique = 0     
	P(mutex)
    while(piezas > 0)
        //tomar pieza
        piezas--
        if (piezas == 0)
            int i
            for i=1 to E
                V(mutex)
        V(mutex)
        //realiza
        fabrique++
        P(mutex)
    P(mutex)
    if (fabrique > max) then
        max = fabrique
        idmax = id
    V(mutex) 
```