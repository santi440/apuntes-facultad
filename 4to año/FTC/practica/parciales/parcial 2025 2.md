![[Pasted image 20260503142744.png]]
COMPUTABILIDAD
Ejercicio 1. ¿Qué establece la Tesis de Church-Turing?

Cualquier algoritmo matematico que se pueda plantear se puede resolver con una maquina de Turing

Ejercicio 2. La jerarquía de la computabilidad se estructura en cuatro clases de lenguajes: 
a) R b) RE-R c) CO-RE-R d)L -(RE U CO-RE) 

Explicar cuándo un lenguaje pertenece a cada clase, y dar un ejemplo en cada caso.

R= recursivos. Lenguajes sobre los que podemos construir una MT M1 que siempre pare, decidiendo por si o por no. ej SAT
RE-R = Recursivamente enumerables - recursivos = Lenguajes sobre los que podemos construir una MT M2 que en caso afirmativo pare por qA, en caso negativo pare por no o puede loopear. Como R $\subset$ RE, la resta se plasma para no considerar aquellos que siempre paren. ej HP
CO-RE -  R = CO-RE son el conjunto de los lenguajes cuyo complemento esta en RE y eliminamos nuevamente del conjunto los lenguajes recursivos. ej $HP^c$
L - (RE U CO-RE) = Lenguajes sobre los que NO puedo construir una maquina de turing. ej $L_{\Sigma^*}$

Ejercicio 3. Sean L1 un lenguaje de RE y L2 un lenguaje de R. Construir una MT M que reconozca el lenguaje L1 U L2, es decir el lenguaje unión de L1 con L2 (dar sólo la idea general).

Podriamos ejecutar ambas maquinas y si una sola acepta la maquina M que estamos construyendo acepta, solo rechaza en caso de que ambas maquinas rechacen y puede loopear si L1 loopea para casos negativos. Podriamos ejecutar la maquina M2 (lenguaje L2) y, como tenemos la certeza que va a parar no hay riesgo de loop y que no consideremos la M1, si acepta aceptamos, si rechaza ejecutamos la M1, si acepta aceptamos, si rechaza rechazamos y si loopea -> loopeamos. De este modo cubrimos todos los posibles casos. 

Otro alternativa seria ejecutar ambas por pasos pero ESTA MUY MAL si ejecutamos M1 primero porque puede que loope y la maquina más grande tmb lo hará cuando M2 lo aceptaba.

Ejercicio 4. Sean los lenguajes:
	DHP = {wi|Mi para desde wi), considerando el orden canónico 
	HP = ((M, w) | M para desde w}
y sea la función:
f($w_i$) = ($M_i$, $w_i$), tal que $M_i$ para desde $w_i$, considerando el orden canónico Explicar por qué la función f es una reducción de DHP a HP.
Ayuda: hay que mostrar que la función f es total computable y que w € De sii (M, w) Є HP.

total computable: el hecho de crear el código de $M_i$  y la cadena $w_i$ en base a la cadena w siguiendo el orden canonico siempre va a terminar y producir una salida, no se queda en loop. No hace falta ejecutar la maquina para resolverlo sino solo determinar el orden
correctitud:
- Si w $\in$ DHP  = ejecutamos el código de la $M_i$ como si fuera un problema de HP y respondé qA.
- si w $\notin$ DHP = ejecutamos la MT M como una maquina de HP
	- responde qR
	- loopea
Ejercicio 5. Indicar y justificar si se cumplen los siguientes enunciados:
a) Si L es un lenguaje que no pertenece a RE, entonces no existe una reducción de L a HP. 
Verdadero, R esta comprendido dentro de RE, decir que  L no pertenece a RE dice a su vez que no pertenece a R. L deberia de ser tan o menos complicado que HP y sin los recursivos (R es menos complejo) ni los Recursivos Enumerables (RE, tan complejos). No puedo construir una funcion de reduccion por definición. No estoy considerando CO-RE como igual de complicado que RE.

b) Si existe una reducción de HP a un lenguaje L, entonces L pertenece a RE.
Falso. La reducción solo pone un piso. Si existe una reducción de HP a L, como sabemos que HP es RE, podemos afirmar que L es AL MENOS RE ( podria ser EXP por ejemplo).

c) Si todos los lenguajes de R se reducen a HP, entonces R = RE.
Falso. Lo hacen por definición, de hecho, y R$\neq$ RE. Todos los lenguajes de R cumplen que son tan o menos dificiles que HP


# COMPLEJIDAD COMPUTACIONAL
Ejercicio 6. ¿Qué establece la Tesis Fuerte de Church-Turing?
Todo algoritmo que se puede resolver en un tiempo poly(n) ocupa un espacio poly(n)

casi.
> Si L es decidible en tiempo poly(n) por un modelo computacional físicamente realizable, también es decidible en tiempo poly(n) por una MT


Ejercicio 7. Sea el lenguaje TAUT = ( es una fórmula booleana sin cuantificadores y toda asignación de valores de verdad la satisface). Explicar por qué TAUT no estaría en la clase P.

La única forma conocida de resolver el problema planteado seria fuerza bruta haciendo la tabla de verdad. En una tabla de verdad hay $2^n$ combinaciones posibles, estamos en el orden exponencial (exp) y no polinomial (P).

Ejercicio 8. Siguiendo con el lenguaje TAUT del ejercicio anterior, explicar por qué tampoco estaría en la clase NP.
Debido a que en NP me alcanza con decidir en un tiempo polinomial en base a un certificado sucinto. No tengo forma de con un solo certificado que no supere el tamaño polinomial (n) pasar todas las posibles combinaciones, el certificado seria nuevamente del orden $2^n$ y por ende la maquina demora tiempo al menos exp en recorrer el certificado de principio a fin, cosa necesaria para determinar si es valido :)

Ejercicio 9. Sean L1 un lenguaje NP-completo y L2 un lenguaje de NP. Explicar por qué si existe una reducción polinomial de L1 a L2, entonces también L2 es NP-completo.

NP completo:
1. es NP
2. es NP- dificil (para cada $L_i$  existe una función $f_i$  que me permite reducir a L)
Si decimos que L1 cumple estas dos caracteristicas y, además sabemos que L2 ya cumple con la primera (L2 $\in$ NP) y existe una función que reduce de L1 a L2, cumple la segunda condición. Propiedad de transitividad (si existe una reducción de $L_i$ -> L1  y una L1-> L2, entonces existe una reduccion de $L_i$ -> L2).

Ejercicio 10. Sean un lenguaje L1 de P y un lenguaje L2 de PSPACE 
a) ¿L1 pertenece a PSPACE? Justificar.
Si. P es considerado una subclase dentro de la complejidad computacional de PSPACE. Además, no tiene sentido que pueda ocupar más de N celdas si no me da el tiempo posible para recorrerlas 
b) ¿L2 pertenece a P? Justificar.
No necesariamente, puedo ocupar PSPACE  y resolver en tiempo exp por la naturaleza del problema. Por ejemplo resolver el problema de TAUT planteado reusando el espacio de modo que ocupo PSPACE pero pruebo las $2^n$ combinaciones posibles.  