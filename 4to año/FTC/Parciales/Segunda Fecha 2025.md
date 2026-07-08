![[Pasted image 20260706120554.png]]

Que  $A \to B$ sea tautologia implica que debe devolver verdadero siempre. La tabla de verdad seria:

| A   | B   | $A \to B$ |
| --- | --- | --------- |
| V   | V   | V         |
| F   | V   | V         |
| F   | F   | V         |
Al dar vuelta y negar los elementos y darlo vuelta, la tabla queda

| $\lnot A$ | $\lnot$ B | $\lnot B \to \lnot  A$ |
| --------- | --------- | ---------------------- |
| F         | F         | V                      |
| V         | F         | V                      |
| V         | V         | V                      |
Sigue siendo tautologia. 
C como formula cualquiera puede tomar V o F.

| C   | $\lnot B \to \lnot  A$ | $C \to (\lnot B \to \lnot  A)$ |
| --- | ---------------------- | ------------------------------ |
| V   | V                      | V                              |
| V   | V                      | V                              |
| F   | V                      | V                              |
| F   | V                      | V                              |
Es tautologia y queda demostrado el proceso que segui 

![[Pasted image 20260706122027.png]]
Se pueba con la tabla de verdad
(x#(y#z)) <-> ((x#y)#z)


| y   | z   | $\lnot(y \lor z)$ | x   | $\lnot(x \lor (\lnot(y \lor z)))$ | <-> | $\lnot(x \lor y)$ | $\lnot ( (\lnot(x \lor y))\lor z)$ |
| --- | --- | ----------------- | --- | --------------------------------- | --- | ----------------- | ---------------------------------- |
| V   | V   | F                 | V   | F                                 | V   | F                 | F                                  |
| V   | V   | F                 | F   | V                                 | F   | F                 | F                                  |
| V   | F   | F                 | V   | F                                 | F   | F                 | V                                  |
| V   | F   | F                 | F   | V                                 | V   | F                 | V                                  |
| F   | V   | F                 | V   | F                                 | V   | F                 | F                                  |
| F   | V   | F                 | F   | V                                 | F   | V                 | F                                  |
| F   | F   | V                 | V   | F                                 | F   | F                 | V                                  |
| F   | F   | V                 | F   | F                                 | V   | V                 | F                                  |
![[Pasted image 20260707145624.png]]

Si, la conclusion puede ser cualquier fbf, lo que incluye por ejemplo una tautologia. 

Por ejemplo podriamos tener
$A_1$ tal qeu A
$P$ || $P \to P$

![[Pasted image 20260707145930.png]]

1. Si, por ejemplo podriamos usar el metateorema de la deduccion y sacar p como hipotesis. En un solo paso, instanciar la primera hipotesis q, demostramos lo que queriamos demostrar.
2. No es cierto, debido a que no hay forma de alcanzar o demostrar p en base a {r}. Podriamos por ejemplo demostrar que si r es verdadero, p puede o ser verdadero o falso.
Ejercicio 5. Sea A una fbf cualquiera. ¿Es cierto que si A es satisfactible, se cumple A?. Justificar.

No. Que sea satisfactible implicaria que existe al menos una interpretacion que la hace verdadera. Que A se cumpla $\vdash_L A$ implicaria que es logicamente valida (ser verdadera en todas sus interpretaciones) $p \land q$ es logicamente valida (caso en que P=V y Q=V) pero no es logicamente valida (caso P=F y q=F).

![[Pasted image 20260707150548.png]]

Siendo P(x,y) que el numero X es menor o igual a otro numero Y.
$(\forall_A) (\forall_B) (\forall_C)(P(A,B) \land P(B,C)) \to P(A,C)$

![[Pasted image 20260707151206.png]]
(\forall_x) P(x,f(x))

Satisfactible = Si tomas naturales, f(x) =x, P = igualdad -> es V.
Verdadera = mismo ej
falsa = si tomamos otra interpretacion donde f(x) = x+1 -> falsa

![[Pasted image 20260707161050.png]]
Contradictoria seria que sea falsa en todas sus interpretaciones. Es una formula cerrada con multiples interpretaciones. Por ej, en el universo de los naturales, con P= es par. Existen casos en que dará Verdadero (x=2).

Ejercicio 9. En lógica de predicados, si la fbf "P(x)" es verdadera en una interpretación, ¿entonces "P(x)" es satisfactible en esa misma interpretación?. Justificar.

Si ya esta demostrado que existe al menos una interpretación en que P(x) es verdadera, cumple con la definición de satisfactible.

![[Pasted image 20260707161420.png]]

1. La especificacion de un programa corresponde al par (precondicion,postcondicion)
2. Es una terna de Hoare, representa que en base a ciertas condiciones p, si se aplica S (el programa) llegaremos al estado o resultado q
3. No se cumple porque existen casos en que entra en un bucle infinito. La condicion  del while es que sea distinto de 0, cosa que puede ocurrir con numeros pares (porque va restando de a 2) pero existen casos como los impares en que continuaria infinitamente (1 por ej).