# Ejercicio 1. 
Responder breve y claramente:
a. ¿Por qué en la complejidad espacial se utilizan MT con una cinta de entrada de sólo
lectura?

Se hace de solo lectura para que no tengamos que considerarla. Dada que la entrada que le damos a nuestra MT tiene un tamaño n, si consideramos la entrada como parte del espacio con el qeu podemos operar no tendriamos MT de espacio menos que lineal

b. ¿Por qué si una MT tarda tiempo poly(n) entonces ocupa espacio poly(n), y si ocupa
espacio poly(n) puede llegar a tardar tiempo exp(n)?

Si tarda tiempo poly(n) no tiene sentido que ocupe más espacio que poly(n) porque no le daria el tiempo para avanzar todas las posiciones de espacio. En un tiempo polinomial se puede recorrer T(n) celdas

Sin embargo, por otro lado, que ocupe espacio polinomial no nos asegura nada del tiempo de la MT sea polinomial. Todas las posibles combinaciones en N celdas de memoria para una maquina de Turing orden exponencial:

![[Pasted image 20260425232628.png]]
c. ¿Por qué los lenguajes de la clase LOGSPACE son tratables?

Un problema pertenece a LOGSPACE si es decidible en un espacio O($log_2N$)
El tiempo máximo de ejecución está limitado por la cantidad de configuraciones posibles, que será $c^{log_2N}$ (Siendo **c** una constante)
Por propiedades matemáticas $c^{log_2N} = n^{log_2C} = N^k$, lo que equivalente a un tiempo polinomial
En consecuencia, si un problema ocupa un espacio logarítmico, a lo sumo requiere tiempo polinomial y los problemas que requieren tiempo polinomial pertenecen a la clase $P$, la cual sabemos que todos los problemas contenidos en esta clase son tratables
# Ejercicio 2. 
Describir la idea general de una MT M que decida el lenguaje L = {anbn | n $\geq$ 1} en espacio logarítmico. Ayuda: basarse en el ejemplo mostrado en clase.

1. Verificar entrada 
	   - Realiza un primer recorrido sobre la cinta de entrada para verificar que la cadena siga el patrón a∗b∗.
	   - Si encuentra una b antes de una a, o una a después de una b, **rechaza**.
	   - Si la cadena está vacía (dependiendo de si n≥1), **rechaza**.
2. Contemos las "a"
	- Mueve el cabezal de lectura al inicio.
	- Cada vez que lee una a, incrementa un contador C en la cinta de trabajo (escrito en binario).
	- Se detiene al encontrar la primera b o el fin de la cadena.
3. comparar
	- Al encontrar la primera b, en lugar de usar un segundo contador, la máquina simplemente empieza a **decrementar** el contador C por cada b que lee.
	- Si el contador llega a cero antes de terminar de leer las b, o si termina de leer las b y el contador no es cero, significa que las cantidades no coinciden. Acepta si llega a la última "b" y el contador es 0.
- Como solo estoy usando la cinta contadora puedo afirmar que mi MT ocupa espacio $log_2N$, codificar un número N en binario.
# Ejercicio 3. 
Describir la idea general de una MT M que decida el lenguaje SAT en espacio polinomial. Ayuda: la generación y la evaluación de una asignación de valores de verdad se pueden efectuar en tiempo polinomial.

Podemos reaprovechar el espacio para no irnos a los caños 
Podemos construir una MT que generé todas las asignaciones posibles de verdad para el problema SAT o formula booleana planteada.
1. La maquina de Turing recorré toda la entrada contando cuantas variables tiene (necesito saber cuál es el limite que tengo que generar). Puedo setear un contador de cuantas hay y otro cuantas combinaciones llevó, total no me cambia drasticamente el espacio, siempre me quedó en polinomial.
2. La maquina generá la siguiente asignación de valores posibles y lo escribe en la cinta 2 (cinta distinta a la de los contadores) .
3. Evaluó la asignación de verdad propuesta, si acepta -> acepto, sino continuo.
4. Vuelvo al paso 2, pisando lo que hay en la cinta 2 por la nueva asignación de verdad
5. Si llegué a probar todas las posibles combinaciones y no acepté -> rechazo

Probamos fácilmente que necesitamos espacio polinomial (contador variables ($log_2N$ porque codifico en binario) + contador intento ($log_2N$ porque codifico en binario) +  asignación (mide N)) = espacio Polinomial
(notar que si quiero mantener todas las posibles combinaciones en memoria a la vez tendria $2^n$ combinaciones posibles y ahora lo acotamos a N)
# Ejercicio 4. 
Probar que NP $\subseteq$ PSPACE. Ayuda: Si L pertenece a NP, entonces existe una MT M1 capaz de verificar en tiempo poly(|w|) si una cadena w pertenece a L, con la ayuda de un certificado x de tamaño poly(|w|). De esta manera, existe también una MT M2 que decide L en espacio poly(|w|), sin la ayuda de ningún certificado.

- PSPACE = espacio polinomial
- no implica que se resuelva en tiempo polinomial

La maquina de Turing M2 ejecuta dentro de si misma la M1 con una salvedad, M2 seria quien le sucinte los certificados. 
1. Determinamos el tamaño maximo del certificado que genera M2 en forma arbitraria, se puede llevar un contador de ordenes lexicograficos por ejemplo 
2. La maquina de Turing M2 genera un certificado del siguiente nivel lexicógrafico (arranca con todos 0 , por ejemplo pero depende del problema). El certificado mide un tamaño n que debe escribirse en una cinta digamos cinta 2. 
3. Se ejecuta M1 usando esa ayuda. Si M1 acepta -> M2 se detiene y acepta. Si M1 rechaza, aumentamos en 1 el contador que usamos, siguiendo el orden lexicográfico.
4. Volvemos al paso 2
5. Si probamos todas las posibles combinaciones del certificado y M1 nunca aceptó ninguno -> M2 rechaza. 

Queda demostrado que NP $\subseteq$ PSPACE. Considerar que estamos pisando el certificado anterior en cada iteración (reciclamos el espacio) , si bien nos toma tiempo exponencial en el peor de los casos nuestro espacio se mantiene en polinomial (n, tamaño del certificado)
# Ejercicio 5. 
Supongamos que existe una MT M de tiempo polinomial que, dado un grafo G,
devuelve un circuito de Hamilton de G si existe o responde no si no existe. Describir la idea
general de una MT M´ que, utilizando M, decida en tiempo polinomial si un grafo G tiene un
circuito de Hamilton. Ayuda: basarse en el ejemplo mostrado en clase con FSAT y SAT

La máquina M′ recibe como entrada un grafo G=(V,E) y debe responder **SÍ** o **NO**. El algoritmo de M′ utilizando M sería el siguiente:
1. **Ejecución de la subrutina:** M′ toma el grafo G y lo pasa como entrada a la máquina M.
2. **Procesamiento de la salida de M:**
    - **Caso A:** Si M devuelve una secuencia de vértices (un circuito de Hamilton válido), M′ **acepta** (responde **SÍ**).
    - **Caso B:** Si M responde **NO** (indicando que no existe tal circuito), M′ **rechaza** (responde **NO**).
Debido a que la maquina M tarda tiempo polinomial (lo dice el enunciado) y nuestra M' no agrega lógica adicional, responde si o no en base a la respuesta que recibe, sigue siendo tiempo polinomial. 