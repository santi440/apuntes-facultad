En una empresa de logística de paquetes existe una sala de contenedores donde se preparan las entregas. Cada contenedor puede almacenar un paquete y la sala cuenta con capacidad para N contenedores. Resuelva considerando las siguientes situaciones:
a. La empresa cuenta con 2 empleados: un empleado Preparador que se ocupa de preparar los paquetes y dejarlos en los contenedores; un empelado Entregador que 	se ocupa de tomar los paquetes de los contenedores y realizar la  entregas. Tanto el Preparador como el Entregador trabajan de a un paquete por vez.
```C
int i = 0;
int j = 0;
contenedores<paquetes>[N]
sem productor = N;
sem consumidor = 0;
sem criticoP = 1, criticoE= 1;

process preparador 
    while (true)
        //preparo el paquete
        P(productor)
        P(criticoP)
        contenedores[i] = paquete
        i = (i + 1) mod n;
        V(consumidor)
        V(criticoP)


process Entregador
    while (true)
        P(consumidor)
        P(criticoE)            
        paquete = contenedores[j] = paquete
        j = (j + 1 mod) N;
        V(productor)
        V(criticoE)
        //entrego el paquete
```
b. Modifique la solución a) para el caso en que haya P Preparadores y E Entregadores.
```C
int i = 0;
int j = 0;
contenedores<paquetes>[N]
sem productor = N;
sem consumidor = 0;
sem criticoP = 1, criticoE= 1;

process preparador [0..N-1 
    while (true)
        //preparo el paquete
        P(productor)
        P(criticoP)
        contenedores[i] = paquete
        i = (i + 1) mod n 
        V(consumidor)
        V(criticoP)


process Entregador[0..N-1
    while (true)
        P(consumidor)
        P(criticoE)            
        paquete = contenedores[j] = paquete
        j = (j + 1 mod) N
        V(productor)
        V(criticoE)
        //entrego el paquete
```