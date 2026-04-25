# Ejercicio 1. 
Responder breve y claramente:
a. Dar un esquema de prueba de la transitividad de las reducciones polinomiales.
Ayuda: composición de funciones de ciertas características computables eficientemente.

- A se reduce a B (A≤p​B)
- B se reduce a C (B≤p​C)
- entonces A se reduce a C (A≤p​C).

- Si $A \le_p B$ entonces A es menos dificil o igual de dificil que B, y al ser una reducción polinomial, el tiempo está acotado por un polinomio, por lo que la reducción se puede hacer en tiempo polinomial
- Si $B \le_p C$ entonces B es menos dificil o igual de dificil que C, y al ser una reducción polinomial, el tiempo está acotado por un polinomio, por lo que la reducción se puede hacer en tiempo polinomial
Entonces es lógico decir que $A \le_p C$, dado que A es menos dificil o igual de dificil que C, y al ser una reducción polinomial, el tiempo está acotado por un polinomio, por lo que la reducción se puede hacer en tiempo polinomial
Según la ayuda, hay que demostrarlo según composición de funciones
Asumamos que existen dos funciones, $f$ y $g$, computables en tiempo polinomial, tales que $f$ reduce A en B, y $g$ reduce B a C
Para probar que $A \le_p C$, definimos la función $h(w) = g(f(w))$
Como $f$ y $g$ son computables en tiempo polinomial, la composición también lo será
Además
$w \in A \iff f(w) \in B \iff g(f(w)) \in C$
Es decir
$w \in A \iff h(w) \in C$
Por lo tanto
$A \le_p C$

b. ¿Cuándo un lenguaje es NP-difícil y cuándo es NP-completo?

Un lenguaje $L$ es NP-Difícil cuando
- Todo $L_i \in NP$ cumple que $L_i \le_p L$
  Es decir, todo problema de NP se reduce en tiempo polinomial a L -> L puede no estar en NP pero no puede estar en P. 
Un lenguaje $L$ es NP-Completo cuando
- $L$ es NP-Difícil (Todo $L_i \in NP$ cumple que $L_i \le_p L$)
- $L \in NP$
c. ¿Por qué si P ≠ NP, un lenguaje NP-completo no pertenece a P?

Por definición, si un lenguaje L es **NP-completo**, entonces **cualquier** otro lenguaje A que pertenezca a la clase **NP** se puede reducir a L en tiempo polinomial (A$\le_p$​L).
Esto significa que L funciona como una "llave maestra": si pudieras resolver L eficientemente (en tiempo polinomial), podrías resolver **todos** los problemas de **NP** eficientemente y P = NP

d. Enunciar el esquema visto en clase para agregar un lenguaje a la clase NPC.
Para encontrar otro lenguaje $L$ que sea NP-Completo, se siguen los siguientes dos pasos
1. Probar que $L$ pertenece a $NP$
2. Construir una reducción polinomial $f$ de $SAT$ a $L$ (L es Np dificil)
Esto aplica para todos los lenguajes NP-Completos encontrados, no necesariamente se usa $SAT$, se puede reducir desde cualquier lenguaje NP-Completo hacia el lenguaje que queremos demostrar que está en NPC

e. ¿Cuándo se sospecha que un lenguaje de NP está en NPI?
Se sospecha que un lenguaje $L \in NP$ está en NPI cuando suceden dos situaciones al mismo tiempo
- $L$ no cuenta con una MT de tiempo polinomial que lo decida
- $L$ no cuenta con una reducción polinomial desde un lenguaje NP-Completo
# Ejercicio 2.
Probar:
a. Si L1 ∈ NPC y L2 ∈ NPC, entonces L1 $\le_p$​ L2 y L2 $\le_p$​ L1.

Por definición de la clase NPC, sabemos que $L_1 \in NP$ y $L_2 \in NP$
- Además, como ambos lenguajes pertenecen a $NPC$, todo lenguaje de NP se debe reducir polinomialmente a ellos
- En base a lo anterior, se puede deducir que $L_1 \le_p L_2$ y que $L_2 \le_p L_1$. Esto es porque
	- $L_2 \in NP$ y todo lenguaje NP se reduce a $L_1$, entonces $L_2 \le_p L_1$
	- Análogamente, como $L_1 \in NP$ y todo lenguaje NP se reduce a $L_2$, entonces $L_1 \le_p L_2$

b. Si L1 $\le_p$ L2, L2 $\le_p$ L1, y L1 ∈ NPC, entonces L2 ∈ NPC.
Ayuda: recurrir directamente a la definición de NPC.

Para que un lenguaje $L$ pertenezca a $NPC$ se cumple que
- $L \in NP$
- Todo lenguaje $L_i \in NP$ cumple $L_i \le_p L$ ($L$ es NP-Dificil)
Para demostrar que $L_2 \in NPC$
- Si $L_1 \le_p L_2$ y $L_1 \in NPC$ entonces sabemos que, por transitividad, todo $L_i \le_p L_2$, por lo tanto $L_2$ es NP-Dificil (lo de la derecha debe ser tanto o más dificil que lo de la izquierda)
- Si $L_2 \le_p L_1$ y $L_1 \in NP$, entonces sabemos que $L_2 \in NP$
Dado lo anterior, se cumplen las dos características para que un lenguaje esté en $NPC$, por lo que
$L_2 \in NPC$

# Ejercicio 3. 
Un lenguaje es CO-NP-completo sii todos los lenguajes de CO-NP se reducen 
polinomialmente a él. Probar que SATC es CO-NP-completo.
Ayuda: L1 $\le_p$ L2 sii $L1^C \le_p L2^C$.

- Como $SAT$ es NP-Completo, todo lenguaje de $NP$ se reduce polinomialmente a él
- Partiendo de un $L \in$ CO-NP, entonces $L^c \in$ NP
- Dado que $SAT$ es NP-Completo, se da que $L^c \le_p SAT$
- Usando la ayuda, se cumple entonces que $L \le_p SAT^c$
- Como esto se cumple para todo $L \in$ CO-NP, entonces $SAT^c$ es CO-NP-Completo

# Ejercicio 4. 
Sean los lenguajes A y B, tales que A ≠ $\emptyset$, A ≠ Ʃ*, y B ∈ P. Probar: (A ⋂ B) $\le_p$ A.
Ayuda: intentar con una reducción polinomial que, dada una cadena w, lo primero que haga sea chequear si w ∈ B, teniendo en cuenta que existe un elemento e que no está en A.

- Sabiendo A no es ni vacío ni todas las cadenas, entonces hay al menos una cadena $e$ que no está en A
- Sabiendo que $B \in P$ entonces hay una MT que decide si una cadena $w$ está en B en tiempo polinomial

Definición de la función de reducción $f$
- Para toda entrada $w$ primero se verifica que exista en B, ejecutando la MT de B
	- Si la MT acepta, entonces $f$ devolverá $w$
	- Si la MT rechaza, entonces $f$ devolverá $e$ (Un elemento que no está en A ni en B)
- Mantener la misma cadena de entrada es polinomial, y cambiar la cadena de entrada por una constante $e$ también es polinomial. La ejecución de la MT también es en tiempo polinomial porque $B \in P$

Correctitud
- Caso 1: $w \in (A \cap B)$
	- Esto implica que $w \in B$. Y $f(w) = w$, y $w \in A$; entonces $f(w) \in A$
- Caso 2: $w \notin (A \cap B)$
	- Si $w \notin B$, la función retorna $e$, entonces $f(w) \notin A$
	- Si $w \in B$ pero $w \notin A$, la función retorna $w$, entonces $f(w) \notin A$

# Ejercicio 5. 
Sea el lenguaje SH-s-t = {(G, s, t) | G es un grafo no dirigido y tiene un camino de Hamilton del vértice s al vértice t}. Un grafo G = (V, E) tiene un camino de Hamilton del vértice s al vértice t sii G tiene un camino entre s y t que recorre todos los vértices restantes una sola vez. Probar que SH-s-t es NP-completo. Ayuda: se sabe que CH, el lenguaje correspondiente al problema del circuito hamiltoniano, es NP-completo.

Para resolverlo tengo que
1. Probar que SH-s-t $\in NP$
Para esto, debo encontrar un certificado sucinto que me permita verificar la solución en tiempo polinomial. 
Para este problema, un certificado sería una lista de vértices ordenados que forman un camino.
La máquina verificadora, debería chequear que el primer vértice de la lista sea $s$ y el último sea $t$, siendo que todos los demás vértices del grafo aparezcan una sola vez y se pueda recorrer todo el grafo según el orden de los vértices indicados en la lista
Estas verificaciones se hacen en tiempo polinomial, por lo que SH-s-t $\in NP$ 
2. Construir una reducción polinomial $f$ de $CH$ a SH-s-t 
Si tenemos un grafo $G$ perteneciente a $CH$, lo que se hace es lo siguiente
- Se toma un vértice cualquiera $v$ de $G$ y se duplica en $v_1$ y $v_2$ (ambas copias están conectados a los mismos vecinos de $v$)
- Se agrega un vértice $s$ a $v_1$
- Se agrega un vértice $t$ a $v_2$

Correctitud
- Si el grafo original G cuenta con un circuito hamiltoniano, entonces hay un ciclo cerrado que recorre todos los vértices exactamente una vez. Al "cortar" el ciclo en el vértice elegido ($v$) entonces el recorrido se "abre", convirtiendolo en un camino desde un punto $s$ a un punto $t$
- De forma inversa, si fusionamos los vértices $v_1$ y $v_2$ y eliminamos $s$ y $t$, se reconstruye el vértice original $v$ y se mantiene el circuito hamiltoniano inicial

Esta construcción se hace en tiempo polinomial, ya que duplicar un vértice y unir sus aristas asociadas es algo polinomial respecto a la cantidad de vértices y aristas del vértice duplicado

Habiendo demostrado que existe una reducción polinomial y que SH-s-t pertenece a NP, entonces SH-s-t pertenece a NPC
