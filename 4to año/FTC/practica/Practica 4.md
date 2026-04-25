# Ejercicio 1. 
Considerando la reducción de HP a LU descripta en clase, responder:
a. Explicar por qué la función identidad, es decir la función que a toda cadena le asigna la misma cadena, no es una reducción de HP a LU.

- **HP (Halting Problem):** HP={⟨M,w⟩∣M se detiene al procesar w}.
- **LU (Lenguaje Universal):** LU={⟨M,w⟩∣M acepta la cadena w}.
- HP≤LU del par ⟨M,w⟩∈HP -> f(⟨M,w⟩)∈LU pero la funcion de identidad hace ⟨M,w⟩∈HP-> ⟨M,w⟩∈LU
Esto implica que HP y LU deberian de ser el mismo conjunto de datos para que la reducción sea correcta, por los primeros dos puntos no lo son. HP dice si una maquina para o no pero LU dice si acepta una cadena, casos en los que HP se detiene y rechaza la cadena, en LU me darian qA, entonces tengo una contradicción y no es una reducción correcta.

b. Explicar por qué las MT M2 generadas en los pares de salida (M2, w), o bien paran aceptando, o bien loopean.

![[Pasted image 20260422215803.png]]
Bajo esta reduccion podemos decir que para aceptando por como se hizo la reduccion, reemplazando los estados de qR (rechazo) por qA (entradas que pertenecen a HP, para, LU debe aceptar) o bien loopear para todas aquellas entradas negativas en las cuales la maquina M1 loopea tambien lo hara M2.

c. Explicar por qué la función utilizada para reducir HP a LU también sirve para reducir HPC a LUC.

Es debido a una de las propiedades
![[Pasted image 20260422220210.png]]
Dado que:
- HPc= {⟨M,w⟩∣M no se detiene al procesar w}.
- LUc={⟨M,w⟩∣M no acepta la cadena w}.
La transformacion de cambiar los estados de qR por qA sigue siendo valida. Si una máquina no se detiene, por definición **no acepta**. Por lo tanto, f(⟨M,w⟩)∈LUC. Si si se detiene, llega a un estado de qA, no pertenece a LUC).

d. Explicar por qué la función utilizada para reducir HP a LU no sirve para reducir LU a HP.

otra "propiedad"
![[Pasted image 20260422221140.png]]
Es decir, no puedo usar la misma transformacion. Por lo que veniamos razonando si aplicamos al reves si Lu rechaza, diciendo que w no es una cadena valida para ese lenguaje HP deberia rechazar, al pasar por la funcion ahora acepta.

e. Explicar por qué la siguiente MT Mf no computa una reducción de HP a LU: dada una cadena válida (M, w), Mf ejecuta M sobre w, si M acepta entonces genera la salida (M, w), y si M rechaza entonces genera la cadena 1.

Debido a que los lenguajes que estamos utilizando pertenecen al conjunto RE. Podrian llegar a loopear. Si Mf ejecuta M1 y esta loopea, Mf tambien lo hará. Cosa que no es total computable. Recordar que las reducciones deben ser 
1. total computable
2. correctitud

# Ejercicio 2
Sabiendo que LU ∈ RE y LUC ∈ CO-RE:
a. Probamos en clase que existe una reducción de LU a LƩ*. En base a esto, ¿qué se puede
afirmar con respecto a la ubicación de LƩ* en la jerarquía de la computabilidad?

LƩ* pertenece a RE. Debido a que utilizamos las reducciones para probar pertenencia de conjuntos y tratamos que a la derecha existen problemas tan o más complejos que desde el que partimos. LU es RE-hard por lo que probamos que LƩ* tambien lo es. Ademas es imposible que sea R ni que pertenezca a CO-RE

b. Se prueba que existe una reducción de LUC a L. En base a esto, ¿qué se puede afirmar con respecto a la ubicación de L en la jerarquía de la computabilidad?
Esto nos permite afirmar que $L_{\varnothing} \in$ CO-RE
Esto significa que Lu reduce a L vacio complemento

# Ejercicio 3. 
Sea el lenguaje DHP = {wi | Mi para a partir de wi} (considerar el orden canónico}.
Encontrar una reducción de DHP a HP. Comentario: hay que definir la función de reducción y
probar su total computabilidad y correctitud

- HP={⟨M,w⟩∣M se detiene al procesar w}.
- DHP = {wi | Mi para a partir de wi}
- Necesitamos construir una funcion del tipo DHP ≤f ​HP o x∈DHP->f(x)∈HP

Total computable:
f(w) = {Mi,wi} donde i es el i-esimo elemento en orden canonico:
1. Si la cadena no es del orden canonico  planteado , la máquina debería generar todas las cadenas en orden canónico y comparar en qué indice se encuentra,devuelvo una cadena fija que no pertenezca a HP (por ej algo con lo que sepas que loopea)
2. una vez qye ya conozco el orden canónico en el que estoy trabajando, la funcion deberia generar un par {Mi, wi}, es decir me faltan las Mi es lo que me falta. Nuevamente que generé las maquinas de turing en orden canonico hasta llegar al indice planteado. 

Probamos que es total computable ya que ningun caso la maquina loopea, siempre decide. 

correctitud: 
	x∈DHP->f(x)∈HP
Debemos demostrar que x pertenece a DHP si y solo si f(x) pertenece a HP:
- Si   $x \in D_{hp}$ ,entonces 
	- por definicion, la maquina Mi debe detenerse con la cadena wi. La funcion me otorga ese par de elementos, ese par cumple para HP (se detiene),  por lo que (Mi,wi) $\in$ HP
- Si  $x \notin D_{hp}$ 
	- Casos en los cuales la maquina deberia de loopear, si Mi loopea para la entrada x se cumple que no pertenece a DHP.
Se demuestra correctitud
# Ejercicio 4. 
Sean TAUT y NOSAT los lenguajes de las fórmulas booleanas sin cuantificadores
tautológicas (satisfactibles por todas las asignaciones de valores de verdad) e insatisfactibles (insatisfactibles por todas las asignaciones de valores de verdad). Encontrar una reducción de TAUT a NOSAT. Comentario: hay que definir la función de reducción y probar su total computabilidad y correctitud.

-TAUT={fórmulas booleanas sin cuantificadores tautológicas (satisfactibles por todas las asignaciones de valores de verdad)}
-NOSAT=insatisfactibles por todas las asignaciones de valores de verdad.
-TAUT≤NOSAT del par ⟨M,w⟩∈TAUT -> f(⟨M,w⟩)∈NOSAT 

f(w) = deberia de retornar el par de !w de modo que se reemplazan todos los valores de verdad por su opuesto (se niegan)

total computabilidad:
- La máquina leería la entrada w y escribiría un símbolo de negación delante de todo, efectivamente negando toda la fórmula y luego escribiendo el resultado en salida
De esta forma, para toda entrada w, se genera una salida !w y siempre se detiene


correctitud: 
- Si w $\in$ TAUT entonces
	- w es una fórmula válida, por lo que la función negará toda la fórmula booleana y esta siempre será falsa sin importar los valores de verdad asignados (porque si w pertenece a TAUT, entonces sin importar los valores de verdad asignados, siempre será verdadera)
	- De esta forma, demostramos que f(w) $\in$ NOSAT
- Si w $\notin$ TAUT entonces
	- Si es una fórmula válida, que no pertenezca a TAUT implica que existe al menos una asignación de valores de verdad que evalúa a falso. La función hará que todas las asignaciones den falso, menos esa que dará verdadero. Debido a esto, la fórmula es satisfactible y f(w) $\notin$ NOSAT
	- Si es una fórmula inválida, la función dará otra fórmula inválida, y como esta fórmula no es ni una fórmula booleana satisfactible ni insatisfactible válida, entonces f(w) $\notin$ NOSAT
# Ejercicio 5. 
Se prueba que existe una reducción de LUC a LƩ* (y así, como LUC ∉ RE, entonces se cumple que LƩ* ∉ RE). La reducción es la siguiente. Para toda w: f((M1, w)) = M2, tal que M2, a partir de su entrada v, ejecuta |v| pasos de M1 a partir de w, y acepta sii M1 no acepta. Probar que la función definida es efectivamente una reducción de LUC a LƩ*. Comentario: hay que probar su total computabilidad y correctitud.

Total computabilidad
- Es total computable debido a que para toda entrada, se produce una salida M2, no hay ejecución de código sino que le añade a la entrada la lógica para M2.

Correctitud
- Si (\<M1>, w) $\in L_{U}^C$ 
	- Esto significa que M1 no acepta w. Por lo que M2 al ejecutar |v| pasos de M1 sobre w, como sabemos que M1 nunca acepta w ($L_{U}$ es los lenguajes que siempre aceptan, $L_{U}^C$ serian los que no aceptan), entonces tampoco lo hará M2 al ejecutar M1 sobre w con |v| pasos. Por lo tanto, M2 aceptará v dado que M1 nunca lo hará en |v| pasos. Por lo tanto \<M2> $\in$ $L_{\sum*}$ 
- Si (\<M1>, w) $\notin L_{U}^C$ 
	- Que no pertenezca a $L_{U}^C$ quiere decir que pertenece a $L_U$, por lo que M1 acepta la cadena w en una cantidad finita de pasos. Así que M2 llegará a la simulación viendo que M1 acepta en |v| cantidad de pasos, entonces M2 no aceptará v. Entonces \<M2> $\notin L_{\sum*}$