## Ejercicio 1. 
Dada la siguiente información:
“Si el unicornio es mítico, entonces es inmortal, pero si no es mítico, entonces es un
mamıfero mortal. Si el unicornio es o inmortal o un mamıfero, entonces tiene un cuerno. El
unicornio es mágico sí tiene un cuerno. Simbolizar en el Cálculo de Enunciados y
responder:
I. El unicornio es mıtico?. Fundamentar.
II. El unicornio no es mítico?. Fundamentar.
III. El unicornio es mágico?. Fundamentar.
Sabiendo ahora que el unicornio es inmortal, responder:
IV. El unicornio no es mágico?. Fundamentar.
Ayuda: simbolizar como forma argumentativa y determinar si es válida o no. Ver def. 1.28, Hamilton.

p: el unicornio es mitico
q: el unicornio es inmortal
r: el unicornio es un mamifero 
s: el unicornio tiene un cuerno
t: el unicornio es magico

- p -> q
- ¬p -> (r ^¬q)
- (q v r) -> s
- s-> t
I. no necesariamente, podria serlo o no ya que existe al menos una asignacion de verdad en la que el unicornio no es mitico y el enunciado es veridico. $(P_1 \land P_2 \land P_3 \land P_4) \rightarrow p$, este no es una tautología. Existe un modelo perfectamente válido donde el unicornio no es mítico ($\neg p$), es un mamífero ($r$), no es inmortal ($\neg q$), tiene cuerno ($s$) y es mágico ($t$), cumpliendo todas las premisas. Por lo tanto, no se puede afirmar que lo sea.
II. Mismo razonamiento. puede serlo, es mítico ($p$), es inmortal ($q$), puede o no ser mamífero, tiene cuerno ($s$) y es mágico ($t$). Como ambas posibilidades ($p$ y $\neg p$) mantienen la consistencia del sistema, el cálculo de enunciados no permite asegurar ninguna de las dos.
III. Si. En base a lo que sabemos el unicornio solo puede ser mitico o mamifero mortal y en cualquier caso por la tercera propocision compuesta tendra cuerno y por ende ser magico (proposicion 4).

> El argumento $(P_1 \land P_2 \land P_3 \land P_4) \rightarrow t$ **es válido** (es una tautología). Como demostramos en la deducción por pasos, la combinación de $P_1$ y $P_2$ garantiza que el unicornio o es inmortal o es un mamífero ($q \lor r$). Por $P_3$, esto obliga a que tenga un cuerno ($s$), y por $P_4$, tener un cuerno obliga a que sea mágico ($t$). Se deduce por leyes lógicas puras.

IV. **Falso. El unicornio SÍ sigue siendo mágico** (por lo tanto, afirmar que "no es mágico" es una contradicción).
Añadir información al cálculo proposicional (en este caso, que es inmortal) puede expandir lo que sabemos, pero en la lógica clásica, **no altera ni invalida las verdades que ya habíamos deducido** (propiedad de monotonicidad).
Como ya habíamos demostrado en la pregunta III de manera general que el unicornio es mágco ($t$ es verdadero) basándonos únicamente en las primeras cuatro premisas, el hecho de que ahora sepamos con certeza que es inmortal no cambia esa deducción. De hecho, si $q$ es verdadero, reafirma de forma directa que $(q \lor r)$ es verdadero, manteniendo la cadena lógica que concluye que el unicornio tiene un cuerno y es mágico. Por lo tanto, asegurar que "no es mágico" ($\neg t$) es lógicamente falso
## Ejercicio 2. 
Para cada una de las siguientes argumentaciones, escribir una forma argumentativa que se corresponda con ella y determinar si es válida o inválida.
I. Si la función f no es continua, entonces la función g no es diferenciable. g es
diferenciable. Por lo tanto, f no es continua.

p: la funcion f es continua
q: la funcion g es diferenciable

premisa 1: $\neg p \rightarrow \neg q$
premisa 2: $q$
conclsion: $\neg p$

$$\{(\neg p \rightarrow \neg q), \quad q\} \vdash \neg p$$
Por premisa 2, q es verdadera y, por ende, $\neg q$ es falsa.
Debemos demostrar que $((\neg p \rightarrow \neg q) \wedge  q) \rightarrow \neg p)$
Para cumplir premisa 1, $\neg p$ debe ser Falso (![[Pasted image 20260517221424.png]]). Si la conclusion es falsa en base a un conjunto de asignaciones de verdad que lo hace verdadero, demostramos que el argumento no sostiene una estructura lógica sólida. De hecho, se trata de la falacia formal conocida como **Afirmación del Consecuente** (atribuida erróneamente sobre el contrarecíproco).
invalida. En  cuyo caso f deberia de ser continua.

II. Sí Juan ha instalado la calefacción central, entonces ha vendido su coche o ha
pedido dinero prestado al banco. Juan no ha vendido su coche ni ha pedido dinero
prestado al banco. Por lo tanto, Juan no ha instalado la calefacción central.
Ayuda: Ver def. 1.28, Hamilton.

p: Juan ha instalado la calefacción central
q: Juan ha vendido su coche
r: Juan ha pedido dinero prestado al banco

premisa 1: $p \rightarrow (q \vee r)$
premisa 2$(\neg q \wedge \neg r)$
conclusion: $\neg p$

$\{p \rightarrow (q \vee r), \quad (\neg q \vee \neg r\} \vdash \neg p$
Debemos demostrar que $(p \rightarrow (q \vee r)) \wedge (\neg q \vee \neg r) \rightarrow \neg p$
Por premisa 2, sabemos que $\neg q$ es verdadero y su negado (q) falso y  $\neg r$ es verdadero y su negado (r) es falso.
ya sabemos que q y r son falsos y su diyuncion sera falsa. para cumplir el condicional p debe ser Falso y su negado verdadero.
demostramos que desde una premisa verdadera, llegamos a una conclusión verdadera
valido.  
## Ejercicio 3. Una fórmula booleana es satisfactible si existe al menos una combinación de valores de verdad (una asignación de verdadero o falso a las variables) que hace que la fórmula sea verdadera. Dados los siguientes enunciados, determinar cuales son verdaderos y cuáles son falsos. Justificar en cada caso.
I. Una fórmula que es una tautología es satisfactible. Verdadero, si tautologia es que siempre de verdadero, cumple a su vez que al menos una de verdadero
II. Una fórmula que es satisfactible es una tautología. Falso, no alcanza con que una asignacion sea verdadera, hay que probar todas
III. Una fórmula que no es satisfactible es una contradicción. Verdadero. Contradiccion es que siempre de falso
IV. Una fórmula que es una contradicción no puede ser satisfactible. Verdadero. Si siempre da falso, no existe ninguna asignacion de verdad que la haga verdadera
Ayuda: Ver def. 1.5, Hamilton.
## Ejercicio 4. Un conjunto de fórmulas es satisfactible si existe al menos una asignación de valores de verdad (una interpretación) que hace que todas las fórmulas del conjunto sean verdaderas al mismo tiempo. Determinar si los siguientes conjuntos son satisfactibles:
I. {p, (p→q), r}. Premisas 1 y 3, p y r deben ser verdaderas, respectivamente. q debe ser verdadero para que la formula sea satisfactible. es decir  que bajo p=V, q=V y r = V la formula es satisfactible
II. {p, q, (p ∧ ¬p)}. (p ∧ ¬p) es una contradicción. no existe forma de que p y $\neg p$ sean verdaderas a la vez. No es satisfactible
## Ejercicio 5. Sean A, B fbfs que cumplen que (¬A ∨ B) es tautología. Sea C una fbf cualquiera. Determinar, si es posible, cuáles de las siguientes fbfs son tautologías y cuales contradicciones. Justificar las respuestas.
I. ((¬(A → B) )→ C )
II. ( C → ((¬A ) ∨ B))
III. ((¬A ) → B )
Ayuda: Ver def. 1.5, Hamilton.

I.
 - (¬A ∨ B) = es tautologia. No existe escenario en el cual $\neg A$ y B sean Falso a la vez, es decir que el caso de A = V y B =F es imposible.
 - por lo anterior, (A → B) va a ser siempre verdadera = es una tautologia.
 - Negar una tautologia me da una contradiccion. ¬(A → B)
 - en un condicional si el antecedente es falso. no importa que sea C, asumo que esta bien formada y puede dar F o V, es una tautologia. Tablita de verdad del condicional si antecedente es falso, consecuente falso o verdadero me da verdadero :)
 $\rightarrow$ es tautologia
 II.
 - C puede ser verdadero o Falso. No me da mucha data
 - Por enunciado,  ((¬A ) ∨ B)) es una tautologia, esta mas parentisada pero no cambia el resultado final
 - Por tabla de verdad del condicional, si la consecuencia es verdadera no me importa si el antecedente es verdadero o falso, da siempre verdadero
  $\rightarrow$ es tautologia
III.   
- Por enunciado, no existe escenario en el cual $\neg A$ y B sean Falso a la vez, es decir que el caso de A = V y B =F es imposible.
- Recordando la tabla del condicional
  ![[Pasted image 20260517224805.png]]
- Solo eliminamos la ultima entrada de la tabla. aun existe el escenario en que $\neg A$  es verdadero y B sean Falso. Haciendo Falso nuestro argumento y por ende no ser tautologia
$\rightarrow$ no es tautologia ni una contradiccion es una contingencia

# Ejercicio 6 Responder y justificar:
I. ¿“(p → q)” es lógicamente equivalente a “(p ∨ ¬q)” ? Lo es si y solo si sus comparaciones de tabla de verdad resultan en una tautologia.

| p   | →     | q   | $\longleftrightarrow$ | p   | v     | ¬   | q   |
| --- | ----- | --- | --------------------- | --- | ----- | --- | --- |
| V   | **V** | V   | **V**                     | V   | **V** | F   | V   |
| V   | **F** | F   | **F**                     | V   | **V** | V   | F   |
| F   | **V** | V   | **F**                     | F   | **F** | F   | V   |
| F   | **V** | F   | **V**                     | F   | **V** | V   | F   |
No es una tautologia -> no es equivalente

II. ¿“(p ↔ q)” es lógicamente equivalente a “((p → q) ∧ (q → p))” ?
Si, es la descomposicion del si y solo si. Igual hago la tabla de verdad para chequear

| p   | ↔     | q   | ↔     | (p → q) | ∧     | (q → p) |
| --- | ----- | --- | ----- | ------- | ----- | ------- |
| V   | **V** | V   | **V** | V       | **V** | V       |
| V   | **F** | F   | **V** | F       | **F** | V       |
| F   | **F** | V   | **V** | V       | **F** | F       |
| F   | **V** | F   | **V** | V       | **V** | V       |

III. ¿“(¬(p ∧ q))” es lógicamente equivalente a "(¬p ∨ ¬q)” ?
Por leyes de de morgan se puede demostrar mas dejo la tablita

| p   | q   | ¬(p ∧ q) | (¬p ∨ ¬q) |
| --- | --- | -------- | --------- |
| V   | V   | **F**        | **F**         |
| V   | F   | **V**        | **V**         |
| F   | V   | **V**        | **V**         |
| F   | F   | **V**        | **V**         |

IV. ¿“(¬(p ∨ q))” es lógicamente equivalente a “(p ∧ q)” ?

Se puede demostrar con el contraejemplo en que ambos son verdaderos o ambos son falsos

| p   | q   | ¬(p ∨ q) | (p ∧ q) |
| --- | --- | -------- | ------- |
| V   | V   | F        | V       |
| V   | F   | F        | F       |
| F   | V   | F        | F       |
| F   | F   | V        | F       |


Ayuda: ver def. 1.7, Hamilton.

# Ejercicio 7
Sea # el operador binario definido como p#q = (p ∧ ¬q) ∨ (¬p ∧ q).
I. Probar que # es asociativo, es decir, x#(y#z) es lógicamente equivalente a (x#y)#z.

Lo reescribo todo
x#(y#z) =    (x ∧ (¬(y ∧ ¬z) ∨ (¬y ∧ z))) ∨ (¬p ∧ (y ∧ ¬z) ∨ (¬y ∧ z))
(x#y)#z = ((x ∧ ¬y) ∨ (¬x ∧ y) ∧ ¬z) ∨ (¬((x ∧ ¬y) ∨ (¬x ∧ y)) ∧ z) 

| x   | y   | z   | (y ∧ ¬z) ∨ (¬y ∧ z) | x#(y#z) | ↔   | (x ∧ ¬y) ∨ (¬x ∧ y) | (x#y)#z |
| --- | --- | --- | ------------------- | ------- | --- | ------------------- | ------- |
| V   | V   | V   | F                   | F       | F   | F                   | V       |
| V   | V   | F   | V                   | V       | F   | F                   | F       |
| V   | F   | V   | V                   | V       | F   | V                   | F       |
| V   | F   | F   | F                   | F       | F   | V                   | V       |
| F   | V   | V   | F                   | F       | V   | V                   | F       |
| F   | V   | F   | V                   | V       | V   | V                   | V       |
| F   | F   | V   | V                   | V       | V   | F                   | V       |
| F   | F   | F   | F                   | F       | V   | F                   | F       |
no es tautologia, no es logicamente equivalente, no es asociativo.
II. Probar que # es conmutativo, es decir, y#z es lógicamente equivalente a z#y.
Ya te digo yo que si

| y   | z   | (y ∧ ¬z) ∨ (¬y ∧ z) | ↔     | z   | y   | (z ∧ ¬y) ∨ (¬z ∧ y) |
| --- | --- | ------------------- | ----- | --- | --- | ------------------- |
| V   | V   | F                   | **V** | V   | V   | F                   |
| V   | F   | V                   | **V** | F   | V   | V                   |
| F   | V   | V                   | **V** | V   | F   | V                   |
| F   | F   | F                   | **V** | F   | F   | F                   |
# Ejercicio 8. 
¿Es cierto que en el Cálculo de Enunciados pueden escribirse dos fbfs que tengan diferentes letras de proposición y aun así ambas fbfs sean lógicamente equivalentes?. En caso de responder positivamente, dar un ejemplo y justificar. En caso de responder negativamente, justificar.

Si, existen enunciados que utilizan letras diferentes (p,q,r,s,etc) y aun asi ser equivalentes. Por ejemplo tenemos el caso de las tautogias que siempre son verdaderas y cualquier tautologia es logicamente equivalente a otra. El ejemplo mas simple que se me ocurre es (p v ¬p) y (q v ¬q). El valor de verdad es lo que me importa no la letra que use

# Ejercicio 9. 
Determinar cuáles de las siguientes fbfs son lógicamente implicadas por la fbf (A ∧ B). Fundamentar.

Una fbf $\alpha$ implica lógicamente a una fbf $\beta$ ($\alpha \vDash \beta$) si y sólo si en **todas** las interpretaciones (renglones de la tabla de verdad) donde $\alpha$ es **verdadera**, $\beta$ también es **verdadera**.

I. A
Podemos afirmar que es logicamente implicada porque si (A ∧ B) es verdadero, necesariamente por definicion de conjuncion A debe ser verdadero
II. A ↔ B.
Si es logicamente implicada. Podemos afirmar esto porque el unico caso en que (A ∧ B) es verdadero seria el caso que A sea verdadera y B tambien sea verdadero, caso que se cumple en el bidireccional (tiene un caso extra en que tambien es verdadero que seria que ambos sean falsos pero como estamos evaluando $(A ∧ B) \vDash (A ↔ B)$ y no al reves), podemos afirmar que esta logicamente implicado
III. ¬B → ¬A

Necesitamos evaluar solo el caso en que (A ∧ B) es verdadero , es decir A verdadero y B Verdadero. Si reemplazamos nos da F→F y si miramos la tabla de verdad del entonces nos da verdadero. Esta logicamente implicada
IV. A → B
Si esta logicamente implicada. Mismo que arriba salvo que ahora es V→V = V

# Ejercicio 10. Dada la siguiente tabla de verdad, encontrar tres fbfs para cada una que las
tenga por tablas de verdad:
I. una fbf (sin restricciones de conectivos). = $$\mathbf{\neg(p \leftrightarrow q)}$$
II. una fbf en FNC (forma normal conjuntiva) = (p ∧ ¬q) ∨ (¬p ∧ q)
III. una fbf en FND (forma normal disyuntiva) = (¬p ∨ ¬q) ∧ (p ∨ q)

| p   | q   | ?   |
| --- | --- | --- |
| V   | V   | F   |
| V   | F   | V   |
| F   | V   | V   |
| F   | F   | F   |
# Ejercicio 11.
Explicar porque el siguiente conjunto no es un conjunto adecuado de conectivas {∧, ∨}.
Un conjunto adecuado de conectivas me debe poder permitir reescribir cualquier formula bien formada usando solo esos conectivos. Por ejemplo reescribir (¬p ∨ ¬q) es inviable al no tener la negación. Un conjunto adecuado de conectivas podria ser {∧,¬}

# Ejercicio 12.
¿Es cierto que si una fbf A es satisfactible entonces A es una tautología”.
No es verdad, que sea satisfactible es que tenga AL MENOS un valor de verdad verdadero. Mientras que una tautologia debe tener todos sus valores de verdad verdaderos. (p-> q) es satisfactible y no es una tautologia. Si es tautologia si es satisfactible, por ejemplo (p v ¬p)

# Ejercicio 13. 
Sea A una fbf donde aparecen solo los conectivos ∧, ∨, ¬ . Sea A* la fbf que
se obtiene a partir de A reemplazando cada ∧ por ∨ y cada ∨ por ∧. Si A es una
tautología, A* ¿también lo es? Justificar.

Falso. Falacia. Mentira
y se puede demostrar con un contraejemplo:
- (p ∨ ¬p) es una fbf y tautologia. Hacer la tabla de verdad sino me crees pero creeme porque tengo paja ahora de hacer otra tabla. Principio de no contradiccion :)
- si la reescribimos y transformamos en un elemento A* obtenemos = (p ∧ ¬p) y llegamos a una contradiccion. Nunca una preposicion y su complemento pueden ser verdad a la vez :)

# Ejercicio 14
Demostrar utilizando la técnica del absurdo que dadas A y B fbfs cualesquiera, siempre ocurre que si A y A → B son tautologías entonces B también lo es.

Estamos diciendo que si A es una tautologia ya sabemos que f(A) = V. Por tanto los casos en que A es falso no existen. Si recordamos la tabla de verdad de A → B =

| A     | B     | A → B |
| ----- | ----- | ----- |
| ==V== | ==V== | ==V== |
| ==V== | ==F== | ==F== |
| F     | V     | V     |
| F     | F     | V     |
y ademas dijimos que A → B es tautologia, lo que elimina la fila de ==V== -> ==F== =  ==F== . Es decir que nos queda la primera tupla, en donde B siempre es verdadero y tambien seria una tautologia.

# Ejercicio 15. 
Demostrar utilizando la técnica del absurdo que (p∧¬p)→q es una tautología.

Por absurdo afirmamos y aceptamos la negación la conclusión hasta llegar a un absurdo. 
(i) (p∧¬p)→q
 (ii) (p∧¬p)→q no es tautologia
 
sabemos entonces que no deberia de existir una funcion que  f((p∧¬p)→q) = V
Si construimos la tabla de verdad de esta fbf = 

| p   | ¬p  | q   | (p∧¬p) | (p∧¬p)→q |
| --- | --- | --- | ------ | -------- |
| V   | F   | V   | F      | V        |
| V   | F   | F   | F      | V        |
| F   | V   | V   | F      | V        |
| F   | V   | F   | F      | V        |
Tambien se podria demostrar sencillamente diciendo que si el antecedente (en este caso una contradiccion que da siempre falso) es falso en un entonces no me importa el consecuente pues el resultado siempre sera verdadero. Llegamos a un absurdo al no encontrar una asignacion de verdad que de falso.

# Ejercicio 16. 
Sea A una fbf donde aparecen solo los conectivos ∧, ¬. Sea A* la fbf que se obtiene a partir de A reemplazando cada ∧ por ∨ y cada letra de proposición por su negación (o sea, cada p por ¬p, cada q por ¬q, etc.). Probar, utilizando la técnica de inducción que A* es lógicamente equivalente a ¬A.

La idea es probar que $A^* \equiv \neg A$
Vamos a tomar elementos de menor complejidad para reescribir la formula, llamemolos B  y C
- $B^* \equiv \neg B$
- $C^* \equiv \neg C$
Hay dos pasos posibles a evaluar
1. que sea unaria
   $$A^* \equiv \neg (\neg B)$$
Por doble negacion A* = B y $\neg A = \neg(\neg B) \equiv B$. Como ambos caminos nos llevan a B concluimos que    $$A^* \equiv \neg A$$
2. que este construida por negaciones y a lo sumo una conjuncion
   - Si $A = B \land C$, aplicando la regla del enunciado reemplazamos el $\land$ por un $\lor$ y transformamos cada componente: $A^* = B^* \lor C^*$.
- Por Hipótesis Inductiva (1 y 2), sustituimos $B^*$ por $\neg B$ y $C^*$ por $\neg C$:$$A^* \equiv \neg B \lor \neg C$$- Aplicamos una de las **Leyes de De Morgan** ($\neg B \lor \neg C \equiv \neg(B \land C)$): $$A^* \equiv \neg (B \land C)$$- Dado que originalmente definimos que $A = B \land C$, podemos sustituirlo de regreso:$$A^* \equiv \neg A$$
# Ejercicio 17.
Demostrar utilizando la técnica de inducción que cualquier fórmula bien formada A que contenga sólo los conectivos {∨, ∧} puede tomar el valor F.
![[Pasted image 20260628193456.png]]