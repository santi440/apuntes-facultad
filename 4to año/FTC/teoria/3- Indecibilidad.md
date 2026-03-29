# No Pertenencia de conjuntos
## Diagonalización 

### Maquina de Turing universal 
Recibe un par, M sea la maquina de turing universal codificada y un input w y ejecute M con la w, acepta rechaza o loopea
![[Pasted image 20260327190629.png]]- Consideración: la Maquina U debe tener una configuración. Busca en la primera parte de M una configuración de q0 y una letra para comenzar a barrer. (podria copiarse la M en la cinta 1 y a partir de un discriminante en la cinta 2 y comportarse buscando en base a la codificacion del estado)
Debe ser una quintupla  o más dependiendo de su cant de cintas por ej (<M>, w) = (1,2,1,3,2),(1,3,1,2,2),(1,1,2,1,3)#2,3

![[Pasted image 20260327191922.png]]
Validacion solo sintactica. No chequea si lo que escribimos es valido o coherente -> podria no tener un qA o q0

![[Pasted image 20260327193301.png]]

RE COMPLETO ==  Menciona que HP es un ejemplar especifico de la totalidad del conjunto RE