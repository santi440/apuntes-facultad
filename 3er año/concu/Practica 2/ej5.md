Suponga que se tiene un curso con 50 alumnos. Cada alumno debe realizar una tarea y existen 10 enunciados posibles. Una vez que todos los alumnos eligieron su tarea,
comienzan a realizarla. Cada vez que un alumno termina su tarea, le avisa al profesor y se queda esperando el puntaje del grupo, el cual está dado por todos aquellos que comparten
el mismo enunciado. Cuando todos los alumnos que tenían la misma tarea terminaron, el profesor les otorga un puntaje que representa el orden en que se terminó esa.
Nota: para elegir la tarea suponga que existe una función
elegir que le asigna una tarea a un alumno (esta función asignará 10 tareas diferentes entre 50 alumnos, es decir, que 5
alumnos tendrán la tarea 1, otros 5 la tarea 2 y así sucesivamente para las 10 tareas)
```C
int cant = 0;
sem esperando  =  0;
sem mutex = 1;
sem dame_mi_nota_de_redes[10] = (10,0)
queue cola;
sem listo = 0;
int terminaron[10] = (10,0)


process profesor
    int nota = 10    
    for i= 1 to 10 
        P(listo)
        P(semCola)
        int tema = pop(cola)
        V(semCola)
        terminaron [tema = nota
        nota--;
        for i= 1 to 5
            V(dame_mi_nota_de_redes[tema])

process alumno[id: 1..50]
    Tarea tema = elegir();
    P(mutex)
	
    cant = cant + 1
    if ( cant == 50)
        for int i=1 to 49
            V(esperando)
        V(mutex)
    else V(mutex); P(esperando) 	
    //Realiza su tarea
    P(mutex)
    terminaron[tema] ++
    if ( cant == 5)
        P(cola)
        push(cola,tema)
        V(cola) 
        V(listo)
    V(mutex)
    P(dame_mi_nota_de_redes[tema]) 
    
```