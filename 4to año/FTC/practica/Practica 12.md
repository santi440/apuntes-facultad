# Práctica 12 — Lógica de Hoare

**Ejercicio 1.** Responder breve y claramente los siguientes incisos:

1.1. ¿En qué se diferencia una prueba semántica de una prueba sintáctica de un programa?

Una **prueba semántica** utiliza la semántica de las instrucciones para modelar los estados del programa y sus transiciones, evaluando el comportamiento concreto del programa paso a paso. Una **prueba sintáctica** (o axiomática) utiliza axiomas y reglas de inferencia (como las de la lógica de Hoare) para deducir la correctitud del programa manipulando fórmulas sintácticamente, sin ejecutarlo ni simularlo.

1.2. ¿Qué es un estado de un programa? ¿Cuándo un estado satisface un predicado?

Un **estado** $\sigma$ es una función que asigna a cada variable del programa un valor concreto (ej: $\sigma(x)=1$, $\sigma(y)=2$). Un estado $\sigma$ **satisface** un predicado $p$ (notación: $\sigma \models p$) si al evaluar $p$ con los valores dados por $\sigma$, el resultado es verdadero.

1.3. ¿Cómo se especifica un programa?

Un programa se especifica mediante un **par de predicados** $(p, q)$, donde $p$ es la **precondición** (lo que debe valer antes de ejecutar) y $q$ la **postcondición** (lo que debe valer después). Se expresa con una terna de Hoare $\{p\}\ S\ \{q\}$.

1.4. ¿Cuál es el significado de la fórmula $\{p\}\ S\ \{q\}$?

Es una **terna de Hoare**. Significa: para todo estado $\sigma$, si $\sigma \models p$ (la precondición), entonces al ejecutar $S$ desde $\sigma$ se llega a un estado final $\sigma'$ tal que $\sigma' \models q$ (la postcondición). Si $\sigma \not\models p$, la terna es verdadera trivialmente.

1.5. ¿Qué son el invariante y el variante de un **while**?

El **invariante** $p$ es un predicado que vale antes y después de cada iteración del **while** (si $\{p \land B\}\ S\ \{p\}$, entonces $p$ se mantiene). El **variante** $t$ es una función entera que decrece estrictamente en cada iteración ($\{p \land B \land t = Z\}\ S\ \{t < Z\}$) y siempre es $\ge 0$ ($p \to t \ge 0$). El invariante sirve para probar la postcondición; el variante, para probar la terminación.

1.6. ¿Cuándo un método axiomático es sensato, y cuándo es completo?

Un método axiomático es **sensato** (*sound*) si todo lo que se puede probar sintácticamente (usando axiomas y reglas) es semánticamente verdadero. Es decir, si $\vdash \{p\}S\{q\}$ entonces $\models \{p\}S\{q\}$. Es una propiedad **obligatoria**: no queremos poder probar fórmulas falsas.

Un método axiomático es **completo** (*complete*) si toda fórmula semánticamente verdadera se puede probar sintácticamente: si $\models \{p\}S\{q\}$ entonces $\vdash \{p\}S\{q\}$. Es una propiedad **deseable** pero no siempre alcanzable; su cumplimiento depende de la expresividad del lenguaje de especificación (por ejemplo, requiere poder expresar invariantes para los while).

1.7. ¿Por qué la lógica de Hoare para los programas secuenciales es composicional?

Porque la prueba de un programa compuesto depende **solamente de las pruebas de sus subprogramas**, no de su implementación interna. Por la regla SEC, si $\{p\}S_1\{r\}$ y $\{r\}S_2\{q\}$ entonces $\{p\}S_1;S_2\{q\}$; si otro $S_3$ también satisface $\{r\}S_3\{q\}$, puede reemplazar a $S_2$ sin afectar la prueba. Es la noción de **caja negra**: el contenido interno de los subprogramas es irrelevante.

---

**Ejercicio 2.** Especificar un programa que a partir de un estado inicial en el que $x$ sea mayor que $0$, termine en un estado final en el que $y$ sea el cuadrado del valor inicial de $x$, y además $x$ tenga su valor inicial.

Usamos una **variable lógica** $X$ para congelar el valor inicial de $x$:

$$
\{x = X \land X > 0\}\ S\ \{y = X^2 \land x = X\}
$$

---

**Ejercicio 3.** Decir y justificar informalmente (es decir sin usar la lógica de Hoare), si se cumplen o no las siguientes fórmulas.
Comentario: el predicado **true** representa cualquier estado:

3.1. $\{x > 0\}\ \textbf{while } x > 0 \textbf{ do } x := x - 2 \textbf{ od } \{\textsf{true}\}$

**Verdadera.** Desde $x>0$, al restar $2$ repetidamente se alcanza un $x \le 0$ y el while termina. Como $\textsf{true}$ no exige nada del estado final, la terna se cumple.

3.2. $\{x > 0\}\ \textbf{while } x > 0 \textbf{ do } x := x - 2 \textbf{ od } \{x = 0\}$

**Falsa.** Si $x$ es impar (ej: $x=1$), se resta $2$ y se llega a $x=-1 \neq 0$.

3.3. $\{x > 0\}\ \textbf{while } x \neq 0 \textbf{ do } x := x - 2 \textbf{ od } \{\textsf{true}\}$

**Falsa.** Si $x$ es impar (ej: $x=1$), $1-2=-1 \neq 0$, se sigue restando $2$ infinitamente sin alcanzar $0$, por lo que el while **no termina** y la terna es falsa.

---

**Ejercicio 4.** Probar (usando la lógica de Hoare): $\{\textsf{true}\}\ x := 0\ ;\ x := x + 1\ ;\ x := x + 2\ \{x = 3\}$.

Se demuestra aplicando el **axioma ASI** de atrás hacia adelante y luego la **regla SEC** para componer:

1. $\{x = 1\}\ x := x + 2\ \{x = 3\}$  (ASI: $(x+2) = 3 \to x = 1$)
2. $\{x = 0\}\ x := x + 1\ \{x = 1\}$  (ASI: $(x+1) = 1 \to x = 0$)
3. $\{\textsf{true}\}\ x := 0\ \{x = 0\}$  (ASI: $0 = 0 \to \textsf{true}$)

Por **SEC** entre 3, 2 y 1:
$$
\{\textsf{true}\}\ x := 0\ ;\ x := x + 1\ ;\ x := x + 2\ \{x = 3\}
$$

El ASI dice: $\{p(e)\}\ x := e\ \{p(x)\}$. Se "sustituye" $x$ por $e$ en la postcondición para obtener la precondición. Trabajando de atrás para adelante, cada precondición se vuelve la postcondición de la instrucción anterior.

---

**Ejercicio 5.** Se cumple $\{x = 10\}\ \textbf{while } x > 0 \textbf{ do } x := x - 1 \textbf{ od } \{x = 0\}$. Se pide probar (usando la lógica de Hoare) que el predicado $p = (x \ge 0)$ es un invariante del **while**.
Ayuda: hay que probar, por un lado: $x = 10 \longrightarrow p$, y por otro lado: $\{p \land x > 0\}\ x := x - 1\ \{p\}$.

**Parte A:** $x = 10 \to (x \ge 0)$.
Si $x = 10$, entonces $x \ge 0$ se cumple trivialmente ($10 \ge 0$).

**Parte B:** $\{x \ge 0 \land x > 0\}\ x := x - 1\ \{x \ge 0\}$.
Por ASI: $\{x \ge 1\}\ x := x - 1\ \{x \ge 0\}$, ya que $(x-1) \ge 0 \equiv x \ge 1$.
Además $(x \ge 0 \land x > 0) \to (x \ge 1)$, por lo que $\{x \ge 0 \land x > 0\}\ x := x - 1\ \{x \ge 0\}$ vale.

Aplicando la regla **REP** con $p = (x \ge 0)$, $B = (x > 0)$:
$$
\{x \ge 0\}\ \textbf{while } x > 0 \textbf{ do } x := x - 1 \textbf{ od } \{x \ge 0 \land \lnot(x > 0)\}
$$
Y $x \ge 0 \land \lnot(x > 0) \equiv x = 0$.

Luego, por la **Parte A**, $x = 10 \to (x \ge 0)$, obtenemos:
$$
\{x = 10\}\ \textbf{while } x > 0 \textbf{ do } x := x - 1 \textbf{ od } \{x = 0\}
$$

---

**Ejercicio 6.** La instrucción **repeat** $S$ **until** $B$ consiste en ejecutar $S$, luego evaluar $B$, si $B$ es falsa volver a iterar, y en caso contrario terminar. Explicar informalmente por qué la siguiente regla para probar la instrucción es sensata (es decir, si se cumplen las premisas, entonces también se cumple la conclusión):

$$
\frac{\{p\}\ S\ \{q\} \quad q \land \lnot B \to p}{\{p\}\ \text{repeat } S \text{ until } B\ \{q \land B\}}
$$

**Explicación:** El **repeat-until** equivale a $S;\ \textbf{while } \lnot B \textbf{ do } S \textbf{ od}$.

1. Se ejecuta $S$ una vez. Partiendo de $p$, por $\{p\}S\{q\}$ se alcanza $q$.
2. Si $B$ es verdadero, se termina en $q \land B$. ✓
3. Si $B$ es falso, se tiene $q \land \lnot B$. Por la segunda premisa $q \land \lnot B \to p$, se cumple $p$ nuevamente, y se puede repetir el ciclo (volver a ejecutar $S$ desde $p$).

Este proceso se repite hasta que $B$ sea verdadero, momento en el que se cumple $q \land B$. La regla es sensata porque modela exactamente la semántica del **repeat-until**: siempre se ejecuta $S$ al menos una vez, y se itera mientras $B$ sea falso.
