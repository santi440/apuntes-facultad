Resolver el funcionamiento en una fábrica de ventanas con 7 empleados (4 carpinteros, 1 vidriero y 2 armadores) que trabajan de la siguiente manera:
• Los carpinteros continuamente hacen marcos (cada marco es armando por un único carpintero) y los deja en un depósito con capacidad de almacenar 30 marcos.
• El vidriero continuamente hace vidrios y los deja en otro depósito con capacidad para 50 vidrios.
• Los armadores continuamente toman un marco y un vidrio (en ese orden) de los depósitos correspondientes y arman la ventana (cada ventana es armada por un único
armador).
```C
//para que quede prolijo deberian estar todas las variables aca arriba pero asi lo entiendo mejor :)
sem marcos = 30
process carpintero [4]
while (true)
    //labura
    P(marcos)
    //dejar en el depositoi
    V(listosM)

sem vidrios = 50
process vidriera [1]
while(true)
    //labura
    P(vidrios)
    //dejar en el deposito
    V(listosV)
    V(vidrios)
    
sem listosM = 0
sem listosV = 0
process armador [2]
while(true)
    P(libreM)
    V(marcos)
    P(libreV)
    V(vidrios)
    //armar ventana
```