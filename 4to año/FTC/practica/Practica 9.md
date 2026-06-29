![[Pasted image 20260628204721.png]]
# **Ejercicio 1.** 
Dada la siguiente demostración sintáctica válida en $L$:

1. $(((\neg p) \rightarrow (\neg(q \rightarrow r))) \rightarrow ((q \rightarrow r) \rightarrow p))$
2. $((\neg p) \rightarrow (\neg(q \rightarrow r)))$
3. $((q \rightarrow r) \rightarrow p)$

- **a)** Identificar el conjunto $\Gamma$ con menor cantidad de fórmulas bien formadas (fbfs) y la fórmula $A$ tal que $\Gamma \vdash_L A$. Indicar, si es posible, qué axioma, hipótesis o regla de inferencia fue aplicado en cada paso de la demostración.
  
  La linea 1 correspone al aximo 3.
  La linea 2, tiene pinta de hipotesis
  La linea 3, se deduce de aplicar MP en 1 y 2.
  
  - **Fórmula $A$ (último paso):** $((q \rightarrow r) \rightarrow p)$
- **Conjunto $\Gamma$ mínimo:** $\{((\neg p) \rightarrow (\neg(q \rightarrow r)))\}$
- **b)** ¿Es $A$ un teorema de $L$? Justificar.
  
  NO. Por definición, una fbf es un teorema en un sistema formal si se puede demostrar a partir de un conjunto **vacío** de hipótesis (es decir, $\emptyset \vdash_L A$, lo cual se escribe simplemente como $\vdash_L A$). En este caso, la deducción de la fórmula $A$ requirió obligatoriamente el uso de la hipótesis introducida en el paso 2 ($\Gamma \neq \emptyset$). Sin esa hipótesis externa, no es posible construir una secuencia de pasos válidos empleando únicamente los axiomas de $L$ para llegar a $A$.
- **c)** ¿Es $A$ tautología? Justificar.
  
  Falso. Es un condicional, existe un caso en que el antecedente es Verdadero y el consecuente falso. Por ejemplo la asignacion de verdad q=V, r =V y p=F
  
# **Ejercicio 2.**
 Sean A, B y C tres fórmulas bien formadas (fbfs) del sistema formal L. Dar una demostración sintáctica en L de las siguientes deducciones. Justificar cada paso en la derivación, indicando cuáles son los axiomas instanciados y las reglas de inferencia utilizadas.

_Ayuda: es posible utilizar, si es necesario, propiedades ya demostradas en el libro de Hamilton, como por ejemplo, metateorema de la Deducción, silogismo hipotético (SH), y otros teoremas ya demostrados en el libro (ver prop 2.11a y prop 2.11b)._

- **I.** ⊢L​(((¬A)→A)→A) = 
  Aplico el METATEOREMA de la deduccion llevandolo a algo del estilo {(¬A)→A)} ⊢L​ A
  1.Instancio el axioma 1 con A=A y B=¬A == A→((¬A)→A)
  2.hipotesis (¬A)→A)
  3.aplico MP entre 1 y 2 =  A
  
- **II.** ⊢L​(((¬B)→(¬A))→(((¬B)→A)→B))= 
  aplico el Metateorema de la deduccion:
	 {(¬B)→(¬A)} ⊢L​(()→(¬B)→A)→B)
 aplico una segunda vez el metateorema de la deduccion
	 {(¬B)→(¬A), (¬B)→A} ⊢L​  B
Me queda demostrar B en base a las hipotesis, axiomas y MP.
1.Instancio axioma 3 con A=A y B=B
((¬B)→(¬A))→(B→A)
2.Hipotesis 1 
((¬B)→(¬A))
3.aplico MP entre 1 y 2
(B→A)
4.Hipotesis 2
(¬B)→A
5.Silogismo hipotetico? entre 3 y 4
¬B → B
6.Decimos que para cualquier fórmula $X$, $((\neg X \rightarrow X) \rightarrow X)$ es un teorema. Reemplazando por B, $((\neg B \rightarrow B) \rightarrow B)$ es un teorema.
7.Aplicamos MP entre las lineas 6 y 7 y me da  B
 
- **III.** {((A→B)→C),B}⊢L​(A→C) = 
  Nuevamente usamos el metateorema de la deduccion y subimos A como hipotesis:{((A→B)→C),B, A}⊢L​ C 
  1.Instancio el axioma 1 con A=B y B=A
  B→ (A→ B)
  2.Hipotesis 2
  B
  3.Aplico MP sobre 1 y 2.
  (A→ B)
  4.Hipotesis 1
  (A→B)→C
  5.Aplicar MP sobre 3 y 4
  C
  
  llegue a deducir C
  
  
# **Ejercicio 3.** 
Sea $\Gamma$ un conjunto de fbfs del sistema formal $L$. Se sabe que $\Gamma \vdash_L A$. ¿Es cierto que para todo $\Gamma_i$ tal que $\Gamma_i \subset \Gamma$; $\Gamma_i \vdash_L A$?. Fundar.

La relación de deducción sintáctica es _monótona_, lo que significa que si agrandamos el conjunto de premisas se mantienen las deducciones ($\Gamma \vdash A \implies \Gamma \cup \Delta \vdash A$). Sin embargo, el enunciado plantea la propiedad inversa (quitar hipótesis), lo cual es falso en el caso general. Si de un conjunto $\Gamma$ eliminamos una hipótesis que era **esencial e indispensable** para construir la demostración de $A$, entonces el subconjunto resultante $\Gamma_i$ ya no tendrá la fuerza suficiente para deducir $A$.

- **Contraejemplo:** Sea $\Gamma = \{p, p \rightarrow q\}$ y sea $A = q$. Sabemos por Modus Ponens que $\Gamma \vdash_L q$. Si tomamos el subconjunto propio $\Gamma_i = \{p \rightarrow q\} \subset \Gamma$, es evidente que únicamente con la implicación no podemos deducir de forma general la proposición $q$. Por lo tanto, $\Gamma_i \not\vdash_L q$
# **Ejercicio 4.** 
Sea $A$ una fbf y $\Gamma$ un conjunto de fbfs. Si se cumple $\Gamma \vdash_L A$, ¿Es cierto que vale $\vdash_L A$ para todo $A$ y para todo $\Gamma$? Justificar.

Que se cumpla $\Gamma \vdash_L A$ significa que $A$ es deducible _asumiendo como verdaderas_ las hipótesis contenidas en $\Gamma$. En cambio, $\vdash_L A$ significa que $A$ es un **teorema** (deducible a partir del conjunto vacío de hipótesis, apoyándose únicamente en los axiomas lógicos del sistema).

- **Contraejemplo:** Tomemos $\Gamma = \{p\}$ y la fbf $A = p$. Es trivialmente válido que $\{p\} \vdash_L p$ (cualquier hipótesis se deduce a sí misma en un solo paso). Sin embargo, $\not\vdash_L p$ debido a que la variable proposicional aislada $p$ no es una verdad lógica universal (tautología) ni un axioma, por lo que no es un teorema de $L$.
# **Ejercicio 5.** 
Determinar si las siguientes afirmaciones son válidas o no en el sistema formal $L$. Justificar en cada caso.
- **I.** $\{q\} \vdash_L (p \rightarrow q)$ = - **Válida.**  El **Axioma 1** de nuestro sistema formal establece el esquema de axiomas $X \rightarrow (Y \rightarrow X)$. Si instanciamos este axioma reemplazando $X$ por $q$ y $Y$ por $p$, obtenemos la fbf: $q \rightarrow (p \rightarrow q)$. si tenemos como hipotesis {q} aplicamos **Modus Ponens** entre nuestra hipótesis $q$ y el Axioma 1 instanciado, obteniendo de inmediato $(p \rightarrow q)$.
- **II.** $\{p \rightarrow q\} \vdash_L (q)$ = **NO VÁLIDA**. Por el Teorema de Corrección, si una deducción es válida sintácticamente ($\Gamma \vdash A$), también debe serlo semánticamente ($\Gamma \vDash A$). Evaluemos mediante tablas de verdad: si la hipótesis $p \rightarrow q$ es Verdadera, esto ocurre en tres escenarios distintos (cuando $p$ es F y $q$ es V, cuando $p$ es F y $q$ es F, o cuando ambos son V). En el escenario particular donde $p$ es Falso y $q$ es Falso, la premisa $\{p \rightarrow q\}$ se cumple, pero la conclusión $q$ toma el valor Falso. Al no preservarse la verdad, la deducción no es válida.

# **Ejercicio 6.** 
Sean $A$, $B$ y $C$ fbfs del C. de Enunciados. Sea $\Gamma$ un conjunto de fbfs del C. de Enunciados. Se sabe que $\Gamma \cup \{A, B\} \vdash_L C$ y también se sabe que $\Gamma \vdash_L A$.
- **I.** ¿Es cierto que $\Gamma \vdash_L (C \rightarrow B)$? Justificar.
  - **No necesariamente es cierto.** * **Justificación:** El enunciado nos dice que con $\Gamma, A$ y $B$ podemos llegar a $C$. Por el Metateorema de la Deducción, esto equivale a decir que $\Gamma, A \vdash_L (B \rightarrow C)$. También sabemos que poseemos las herramientas para demostrar $A$ desde $\Gamma$, lo que nos reduce la expresión anterior a $\Gamma \vdash_L (B \rightarrow C)$.
- Saber que podemos probar $B \rightarrow C$ (que de $B$ se sigue $C$) **no** nos garantiza bajo ninguna regla lógica que valga la recíproca, es decir, que de $C$ se deduzca $B$ ($\Gamma \vdash_L C \rightarrow B$). Sería incurrir en la falacia de afirmación del consecuente
- **II.** ¿Es cierto que $\vdash_L (A)$? Justificar.
  - **No necesariamente es cierto.** El enunciado afirma que $\Gamma \vdash_L A$. Siguiendo la misma lógica explicada en el **Ejercicio 4**, que $A$ se deduzca a partir de un conjunto de hipótesis $\Gamma$ no implica que $A$ sea un teorema absoluto ($\vdash_L A$) independiente de ellas, a menos que se demuestre explícitamente que $\Gamma = \emptyset$ o que sus premisas constituyan verdades lógicas.

# **Ejercicio 7.** 
¿Es el sistema formal $L$ decidible? Justificar.
_Ayuda: si es decidible, debería ser posible determinar (decidir) para cada fbf, si es o no teorema de L._

**Sí, el sistema formal $L$ de la lógica proposicional es decidible.**
Un sistema formal es decidible si existe un **algoritmo efectivo** (un procedimiento mecánico finito) que, para cualquier fbf dada del sistema, permite determinar con absoluta certeza en un número finito de pasos si dicha fbf es o no un teorema del sistema ($\vdash_L A$).
En el caso del Cálculo de Enunciados, por los teoremas fundamentales de **Corrección y Completitud**, sabemos que una fbf es un teorema de $L$ si y sólo si es una **tautología** ($\vdash_L A \iff \vDash A$).
Dado que contamos con el método mecánico de las **tablas de verdad** (o el de árboles semánticos), podemos calcular de forma completamente determinista y finita todos los valores de verdad de cualquier fbf según sus variables. Si la tabla da toda verdadera, es una tautología y por ende un teorema; si aparece al menos un falso, no lo es. Al existir este algoritmo disyuntivo, el sistema $L$ es **decidible**.