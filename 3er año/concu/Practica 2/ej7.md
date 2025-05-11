Existen N personas que deben imprimir un trabajo cada una. Resolver cada ítem usando semáforos:
a) Implemente una solución suponiendo que existe una única impresora compartida por todas las personas, y las mismas la deben usar de a una persona a la vez, sin importar el orden. Existe una función Imprimir(documento) llamada por la persona que simula el uso de la impresora. Sólo se deben usar los procesos que representan a las Personas.
sem  libre = 1
```C
process persona[0..N-1]
	P(libre)
    Imprimir(documento)
    V(libre)
```
b) Modifique la solución de (a) para el caso en que se deba respetar el orden de llegada.
```C
sem  impresora = 1
bool libre = true;

sem personas [N] = (N,0)
cola C;

process person [id: 0..N-1]
    P(impresora)
    if (libre)then 
            libre = false
            V(impresora) 
    else 
        push (C, id)
        V(impresora)
         P(persona[id])
    Imprimir(documento)
    
    P(impresora)
    if !(C.empty()){
        int aux  = pop(C)
        V(persona[aux])}
    else 
        libre = true
    V(impresora)
```
c) Modifique la solución de (a) para el caso en que se deba respetar estrictamente el orden dado por el identificador del proceso (la persona X no puede usar la impresora hasta que no haya terminado de usarla la persona X-1).

```C
sem personas[N] = (0, 1; N, 0)
    
process persona[id: N]

P(personas[id])
//uso la impresora
if !(id = N-1) then
    V(personas[id+1])
```

d) Modifique la solución de (b) para el caso en que además hay un proceso Coordinador que le indica a cada persona que es su turno de usar la impresora.
```C
sem  esperando =  0
sem  termine =  0
sem  cola =  0 
bool libre = true;

sem personas [N] = (N,0)
cola C;

process person [id: 0..N-1]
    P(cola)
    push (C, id)
    V(cola)
    V(esperando)
    P(persona[id])
    Imprimir(documento)
    V(termine)
    
process coordinador
while true
    P(esperando)
    P(cola)
    pop(C, aux)
    V(cola)
    V(personas[aux])
    P(termine)
```


e) Modificar la solución (d) para el caso en que sean 5 impresoras. El coordinador le indica a la persona cuando puede usar una impresora, y cual debe usar.
```C
sem  esperando =  0
sem  termine =  0
sem  cola =  1 
sem semImp=1
cola impresoras
sem libres = 5
sem personas [N] = (N,0)
int meToca [N] 
cola C;

process person [id: 0..N-1]
    P(cola)
    push (C, id)
    V(cola)
    V(esperando)
    
    P(persona[id])
    
    Imprimir(documento, meToca[id]) //meToca[id] tiene la impresora a la que lo mando
    P(semImp)
    push(meToca[id])
    V(libres)
    V(semImpr)
        
process coordinador
    while(true)
        int aux
        P(esperando)
        P(cola)
        pop(C, aux)
        V(cola)
        P(libres)
        P(semImpr)
        meToca[aux] = pop(impresoras)
        V(semImpr)
        V(personas[aux])
```