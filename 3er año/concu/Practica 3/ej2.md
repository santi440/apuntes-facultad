Existen N ==procesos== que deben leer información de una base de datos, la cual es administrada por un ==motor== que admite una *cantidad limitada* de ==consultas== simultáneas.
a. Analice el problema y defina qué procesos, recursos y monitores serán  necesarios/convenientes, además de las posibles sincronizaciones requeridas para resolver el problema.
- procesos = procesos que intentan acceder al monitor
- motor = monitor
- consultas = procedimiento (deberia solicitar su uso y su liberacion)
- cantidad limitadas  = codicion de control.
- No dice nada de que respete el orden de llegada supongo que no importa, lo hago porque me sirve que tenga un cond 
b. Implemente el acceso a la base por parte de los procesos, sabiendo que el motor de base de datos puede atender a lo sumo 5 consultas de lectura simultáneas.
```c
Monitor motor
int usandola = 0
int esperando = 0 
cond espera;

procedure solicitarUso()
	if(usandola = 5) {esperando++;wait(cond);}
	else usandola++
procedure liberarUso()
	if(esperando > 0) {esperando--;signal(cond)}
	else usandola--;
```

```c
monitor BD
    cond espera
    integer cont = 5;
    boolean libre true
   integer esperando=0;
    
    Process ingresarBD
    If (cont = 0) OR (!libre)
        esperando++
        wait(espera)
        
    
    cont--
    if (cont=0)
        libre=false
   

    

    Process salirBD
    if (esperando = 0)    
         libre=true
    cont++
    signal(espera)
```