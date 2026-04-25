# Ejercicio 1. 
Responder breve y claramente los siguientes incisos:
a. ¿Por qué la complejidad temporal sólo trata los lenguajes recursivos?

La complejidad temporal mide el número de pasos que da una **Máquina de Turing (MT)** hasta detenerse y dar una respuesta (aceptar o rechazar). Solo los **lenguajes recursivos** (decidibles) garantizan que la MT se detendrá siempre. Si el lenguaje fuera solo recursivamente enumerable (no decidible), la MT podría entrar en un bucle infinito para cadenas que no pertenecen al lenguaje, y no podríamos medir un "tiempo de ejecución" finito

b. Probar que n³= O(2^n). Ayuda: hay que encontrar un número natural n0 y una constante c> 0  tales que n³ $\leq$ c.2^n para todo n $\geq$ n0.

suponiendo una c constante 1:
- n = 1 == 1³ $\leq$ 1. 2¹ (verdadero)
- n = 2 == 2³ $\leq$ 1. 2² (falso)
- ... (da falso)
- n = 10 == 10³ $\leq$ 1. 2¹⁰ (verdadero)

**Conclusión:** Para todo n≥10, se cumple con c=1 y n0​=10. (También podrías usar c más grandes para valores de n0​ más chicos pero con ese cumple).

> Una función $T_1(n)$ es del orden de una función $T_2(n)$, que se anota así $T_1(n) = O(T_2(n))$, si para todo $n \ge n_0$ se cumple que $T_1(n) \le c.T_2(n)$, con $c \gt 0$

> Un lenguaje $L \in TIME(T(n))$ si existe una MT M que lo decide en tiempo $O(T(n))$. Esto es, que existe una máquina de Turing M que decide L y su tiempo de ejecución está acotado superiormente  por O(T(n)). Por lo que hay una constante c > 0 tal que para toda entrada w de tamaño n = |w|, M realiza a lo sumo c . T(n) pasos antes de detenerse

Sabiendo que $T_1(n) = O(T_2(n))$, entonces tenemos que $T_1(n) \le c . T_2(n)$.  
Si tenemos un lenguaje $L \in TIME(T_1(n))$, esto quiere decir que para toda entrada w de tamaño $n = |w|$ y n $\ge n_0$, la máquina M se detiene en un $tiempo \le T_1(n) \le c.T_2(n)$. Como $c.T_2(n) \in O(T_2(n))$, entonces $L \in TIME(T_2(n))$ y por lo tanto $TIME(T_1(n)) \subseteq TIME(T_2(n))$

d. ¿Cuándo un lenguaje pertenece a P, a NP y a EXP? ¿Por qué si un lenguaje pertenece a P
también pertenece a NP y a EXP?

- **P:** Lenguajes decidibles en tiempo polinomial por una MT **determinística** = tiempo O($n^k$).
- **NP:** Lenguaje que su solución es verificable en tiempo poly(n). Esta verificación, se hace en base a un certificado que lo ayuda, el cual es sucinto (me lo dan)
- **EXP:** Lenguajes decidibles en tiempo exponencial, en tiempo exp(n), es decir, tiempo O($C^{poly(n)}$)

**Inclusiones:**
- Un lenguaje L pertenece a P si es decidible en tiempo polinomial. 
- De la misma forma, se puede decir que pertenece a NP, porque una máquina puede verificar L en tiempo polinomial con la ayuda de un certificado que no utiliza. 
- También, se puede decir que pertenece a EXP, dado que cualquier función polinomial está acotada superiormente por una función exponencial.

e. ¿Qué formula la Tesis Fuerte de Church-Turing?

Si L es decidible en tiempo poly(n) por un modelo computacional físicamente realizable, también es decidible en tiempo poly(n) por una MT

f. ¿Por qué es indistinta la cantidad de cintas de las MT que utilizamos para analizar los
lenguajes, en el marco de la jerarquía temporal que definimos?

Es indistinto debido a que el poder computacional de una MT con varias cintas no es superior a una MT con una sola cinta. Cualquier tarea realizable con N cintas es realizable con 1 cinta.
La única diferencia, es el retardo cuadrático, dado que si una máquina de 2 cintas hace M pasos, con 1 cinta hará M²

g. ¿Qué codificación de cadenas se descarta en la complejidad temporal?

Se utiliza cualquier codificación distinta de la unaria, dado que codificar números grandes en unario no es físicamente realizable y además genera inconsistencias. Codificar en binario o cualquier otro sistema no altera la operación porque el pase entre ellos se puede realizar en un tiempo polinomial.

h. ¿Por qué si un lenguaje L pertenece a P, también su complemento LC pertenece a P?
Ayuda: hay que probar que si existe una MT M que decide L en tiempo poly(n), también
existe una MT M´ que decide LC en tiempo poly(n).

Para construir una MT $M^C$ a partir de una MT M, se puede simular la ejecución de M para una entrada w e invertir el resultado (Si M rechaza, $M^C$ acepta, y viceversa). Debido a esto, $M^C$ va a ejecutar una cantidad de pasos similar a la MT M, con la diferencia de los pasos extras para hacer la simulación y la inversión del resultado, pero estos pasos extras son constantes. Por lo que si el tiempo de ejecución de M es polinomial, el tiempo de ejecución de $M^C$ también lo será
Debido a que podemos tener una MT $M^C$ que acepta un $L^C$ en tiempo polinomial, a partir de una MT M que acepta un L en tiempo polinomial, se puede decir que $L^C \in P$

i. Sea L un lenguaje de NP. Explicar por qué los certificados de L miden un tamaño polinomial
con respecto al tamaño de las cadenas de entrada.

Si partimos de la base que un lenguaje pertenece a NP si se puede verificar su pertenencia a partir de un certificado sucito en tiempo polinomial pero el certificado mide tamaño exponencial no me daria el tiempo para recorrer la totalidad del certificado provisto o deberia ejecutarme en tiempo exponencial (en contra de la definición que acabamos de plantear)

# Ejercicio 2. 
Sea el lenguaje SMALL-SAT = {φ | φ es una fórmula booleana sin cuantificadores en la forma normal conjuntiva (o FNC), y existe una asignación de valores de verdad que la satisface en la que hay a lo sumo 3 variables con valor de verdad verdadero}. Probar que SMALL-SAT ∈ P.
Comentario: las fórmulas φ en la forma FNC son conjunciones de disyunciones de variables o variables negadas; p.ej. (x1 $\lor$ x2) $\land$ x4 $\land$( $\neg$x3 $\lor$ x5 $\lor$ x6).
Ayuda: una MT que decide SMALL-SAT debe contemplar asignaciones con cero, uno, dos y
hasta tres valores de verdad verdadero.

no necesitamos probar las 2n combinaciones posibles de una tabla de verdad (que sería exponencial). Como la condición es que haya **a lo sumo 3 variables en verdadero**, el número de combinaciones a probar es limitado.

Si ϕ tiene n variables, las asignaciones posibles que cumplen la condición son aquellas donde ponemos en "verdadero":

- 0 variables (hay $\binom{N}{0}$=1 forma). 
    
- 1 variable (hay $\binom{N}{1}$=n formas). == O(N)
    
- 2 variables (hay $\binom{N}{2}$=$\frac{N\cdot(N-1)}{2}$ formas). = O(N²)
    
- 3 variables (hay $\binom{N}{3}$ =$\frac{N\cdot(N-1)\cdot(N-2)}{6}$formas). = O(N³)
# Ejercicio 3. 
Dados los dos lenguajes siguientes, (1) justificar por qué no estarían en P, (2)
probar que están en NP, (3) justificar por qué sus complementos no estarían en NP:

a. El problema del conjunto dominante de un grafo consiste en determinar si un grafo no dirigido tiene un conjunto dominante de vértices. Un subconjunto D de vértices de un grafo G es un conjunto dominante de G, sii todo vértice de G fuera de D es adyacente a algún vértice de D. El lenguaje que representa el problema es DOM-SET = {(G, K) | G es un grafo no dirigido y tiene un conjunto dominante de K vértices}.

¿Por qué no estarían en P? 
	DOM-SET para un grafo con n vértices y un valor K, existen $\binom{n}{K}$ subconjuntos posibles de tamaño K. La forma de resolverlo por fuerza bruta implica probar todas las combinaciones, lo cual, si K es proporcional a n (por ejemplo K=n/2), resulta en una complejidad **exponencial**. No existe a dia de hoy un algoritmo capaz de resolverlo en tiempo polinomial y suponiendo que NP $\neq$ P, no puede pertenecer a P.
	
Probar que están en NP:
- **El Certificado (c):** Un subconjunto de vértices V′⊆V tal que ∣V′∣=K.
- **El Verificador (V):** Dada la entrada ((G,K),V′), el verificador realiza los siguientes pasos:
    1. Verifica que el tamaño de V′ sea exactamente K.
    2. Para cada vértice v∈G que **no** esté en V′:
        - Recorre la lista de adyacencia de v.
        - Comprueba si al menos uno de sus vecinos pertenece a V′.
    3. Si todos los vértices fuera de V′ tienen al menos un vecino en V′, el verificador **acepta**. De lo contrario, **rechaza**.
**Complejidad del verificador:** En el peor de los casos, recorremos todos los vértices y sus aristas. Esto es O(V+E), lo cual es claramente **polinomial**. Como existe un certificado de tamaño polinomial y un verificador polinomial, **DOM-SET ∈ NP**.

Justificar por qué sus complementos no estarían en NP
El lenguaje complemento sería DOM-SETC: el conjunto de grafos que **no** tienen un conjunto dominante de tamaño K.
- En el caso del complemento, para convencer a alguien de que **no existe** ningún conjunto de tamaño K que domine al grafo, no basta con mostrar un subconjunto; ¡tendrías que demostrar que **todos** los subconjuntos posibles fallan!
- Esto requeriría un certificado que enumere o pruebe la falla de una cantidad exponencial de combinaciones, lo cual no se puede verificar en tiempo polinomial.
- Por lo tanto, se cree que DOM-SETC pertenece a la clase **co-NP**, y a menos que NP=co-NP (algo que la comunidad científica cree falso), este lenguaje no está en NP.

b. El problema de los grafos isomorfos consiste en determinar si dos grafos son isomorfos. Dos grafos son isomorfos si son idénticos salvo por la denominación de sus arcos. P.ej.,dado el grafo G1 = ({1, 2, 3, 4}, {(1, 2), (2, 3), (3, 4), (4, 1)}), el grafo G2 = ({1, 2, 3, 4}, {(1, 2), (2, 4), (4, 3). (3, 1)}) es isomorfo a G1. El lenguaje que representa el problema de los grafos isomorfos es ISO = {(G1, G2) | G1 y G2 son grafos isomorfos}.

¿Por qué no estarían en P? 
Para determinar si dos grafos con n vértices son isomorfos, tendrías que probar todas las posibles permutaciones de los nombres de los vértices. El número de permutaciones es n! (factorial de n), lo cual crece mucho más rápido que cualquier función exponencial y, por tanto, no pertenece a P(no se resuelve en tiempo exponencial).

Probar que están en NP:
Para verificar si dos grafos son isomorfos, el certificado sería la permutación específica que relaciona cada vértice de un grafo con el otro. Entonces la MT debería verificar esa permutación y sólo verificar cada vértice adyacente de cada uno de los vértices, lo cual es polinomial

Justificar por qué sus complementos no estarían en NP
Probar que no sean isomorfos implicaria probar todas las posibles combinaciones(n!) fallan para estar totalmente seguros de que no existe una combinación posible de valores que si valga, no se puede pasar un certificado que contenga todas las combinaciones en un espacio polinomial, crece exponencialmente.  ISO^c∈co-NP
# Ejercicio 4. 
Se prueba que NP $\subseteq$ EXP. La prueba es la siguiente. Si L ∈ NP, entonces existe
una MT M que, para toda cadena de entrada w, verifica en tiempo poly(|w|) si w ∈ L, con la
ayuda de un certificado x tal que |x|  $\leq$ p(|w|) - p es un polinomio -, y de esta manera, se puede construir una MT M´ que decida en tiempo exp(|w|) si w ∈ L, sin usar ninguna cadena adicional: M´ simplemente barre todos y cada uno de los certificados posibles x de w. Se pide explicar por qué M´ efectivamente tarda tiempo exp(|w|). Ayuda: como |x| $\leq$  p(|w|) y los símbolos de x pertenecen a un alfabeto de k símbolos, ¿cuántos certificados x de tamaño p(|w|) tiene a lo sumo una cadena w?

Si la longitud de x está acotada por p(|w|), entonces, dado que tenemos k símbolos posibles, hay una cantidad de combinaciones $k^{p(|w|)}$, esto demuestra que el crecimiento es exponencial respecto al tamaño de la entrada w. Por lo que la cantidad de certificados crece de manera exponencial
Cada uno de estos certificados, si sabemos que estamos en NP, se verificarán en tiempo polinomial
El tiempo total, en el peor de los casos, será entonces el probar todos los certificados posibles, lo que llevaría un tiempo $O(k^{p(|w|)})\cdot poly(|w|)$.
Como el exponente será más grande que el tiempo polinomial, se demuestra que M' tarda tiempo exponencial

