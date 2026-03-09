# Computabilidad 
## Intro
![[Pasted image 20260309141218.png]]
Problemas: todo tipo de necesidad
Computables: se puede resolver con una computadora
decidibles: Se pueden resolver
- Intratables: Son tan complejos que no presentan una solución optima 
- Tratables: dentro de una solucion con orden polinomica 
![[Pasted image 20260309150010.png]]
## Repaso

![[Pasted image 20260309144212.png]]
 El conjunto A es el dominio y el conjunto B es el codominio de la función f.
- f es inyectiva si no llegan dos flechas a un mismo elemento de B.
- f es suryectiva si llegan flechas a todos los elementos de B.
- f es biyectiva si es inyectiva y suryectiva
-----
Una fórmula booleana φ es satisfactible si existe al menos una asignación de valores de verdad que la evalúa verdadera

Un **alfabeto** es un conjunto finito de símbolos.
Por ejemplo, Ʃ = {a, b, c} es un alfabeto de tres símbolos.
• Un **lenguaje** con alfabeto Ʃ es un conjunto de cadenas finitas de símbolos de Ʃ.

## Maquina de Turing
Modelo simplificado de una computadora moderna que permite estudiar la computabilidad y complejidad.
![[Pasted image 20260309145454.png]]
Dados todas las entradas inifitas del universo en que la maquina devuelve si-> se los considera lengauje y en consecuencia problema. 
 
 En ciertos casos, para una maquina de turing no siempre me devuelve que si o que no, se queda en bucle
 ![[Pasted image 20260309145539.png]]
 Si al menos una cadena M no puede decidir.
Es computable pero no decidible (halting problem).

ni siquiera cuentan con MT M que respondan sí en todos los casos positivos
![[Pasted image 20260309145909.png]]
NO existe una MT M que responda en todos los casos positivos -> no es computable

![[Pasted image 20260309150646.png]]Definición de la MT M = (Q, Γ, δ, q0, qA, qR)

ej de la funcion: δ(q0, a) = (qb, α, R)

Se puede una MT con multiples cintas pero no agrega capacidad de computo sino velocidad: δ(q, (a, b, c)) = (q’, (a, R), (b’, L), (c´, S))
![[Pasted image 20260309153700.png]]