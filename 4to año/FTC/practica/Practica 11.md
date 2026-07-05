# Práctica 11 — Lógica de Primer Orden

**Ejercicio 1.** Señalar las ocurrencias libres o ligadas de $x_1, x_2, x_3$ en la siguiente fbf escrita en un lenguaje de primer orden donde $C = \{c\}$, $F = \{f, g\}$ y $P = \{A\}$, con $f$ de aridad 1; $g, A$ de aridad 2. Determinar cuál es una fbf abierta y cuál es cerrada.

Es ligada si esta dentro del scope de un cuantificador sobre esa variable, si no, es libre. 

I. $(\forall x_1)((\exists x_2) A(x_1, f(x_2, x_3)) \to (\forall x_3) A(g(c), x_1) \lor A(x_1, x_3))$
II. $(\forall x_1)((\exists x_2) A(x_1, f(x_2, x_3))) \to (\forall x_3) A(g(c), x_1) \lor A(x_1, x_3)$

i. $x_1$ es ligada el $\forall(x_1)$ tiene alcance para todo el resto de fbf
$x_2$ es ligada el $\exists(x_2)$ tiene alcance sobre $A(x_1, f(x_2, x_3))$ que es la única parte donde interviene $x_2$
$x_3$ esta libre en la primera parte $A(x_1, f(x_2, x_3))$, luego aparece el cuantificador pero tiene incidencia sobre una parte de fbf que no involucra la variable y queda fuera (sigue libre), el final de la fbf en la que si tengo un $x_3$

Como tiene AL MENOS una variable libre, es fbf abierta

ii. me dio paja, la ia dijo que era abierta y tiene varias libres. 

---

**Ejercicio 2.** Sea $A$ una fbf que no contiene cuantificadores (es decir, abierta) escrita en algún lenguaje de primer orden. Sea $I$ una interpretación para tal lenguaje. ¿Es posible decidir acerca del valor de verdad de $A$ en $I$? Fundamentar.

No es posible decidir el valor de verdad de $A$ en $I$ únicamente con la interpretación. Como $A$ es abierta (tiene variables libres) y no contiene cuantificadores, su valor de verdad depende de la **asignación de variables** (valuación) que asigne elementos del dominio a cada variable libre. Sin una valuación concreta $s: Var \to D$, no podemos determinar si $A$ es verdadera o falsa, porque el significado de las variables libres no está fijado por $I$.

Dada una interpretación $I = \langle D, \cdot_I \rangle$ y una valuación $s$, se puede calcular recursivamente el valor de verdad de $A$ usando la semántica de Tarski (para átomos se evalúan los términos y se aplica el predicado, y para los conectivos se usan las tablas de verdad). Pero sin $s$, las variables libres no tienen asignado un elemento del dominio, por lo que el valor de verdad queda indeterminado. Por ejemplo, si $A = P(x)$, con $I$ tal que $P_I$ es "ser par" en $\mathbb{N}$, $A$ podría ser verdadera o falsa según qué valor tome $x$.

---

**Ejercicio 3.** Analizar si son o no lógicamente equivalentes los siguientes pares de fbfs (usar noción de $i$-equivalencia o contraejemplos según corresponda):

I. $\forall x\, P(x)$ y $\exists x\, P(x)$

No son equivalentes. Contraejemplo: $D=\{a,b\}$, $P(a)=V$, $P(b)=F$.
 $\forall x P(x)=F$
 $\exists x P(x)=V$.

II. $\exists x\, \exists y\, Q(x, y)$ y $\exists y\, \exists x\, Q(x, y)$

Sí son $i$-equivalentes. El orden de cuantificadores existenciales no afecta el significado: ambas exigen $a,b\in D$ tales que $Q(a,b)$.

III. $\exists x\, (\forall y) R(x, y)$ y $(\forall y) \exists x\, R(x, y)$

No son equivalentes. La primera requiere un mismo $x$ para todo $y$; la segunda permite que $x$ dependa de $y$. Contraejemplo: $D=\mathbb{N}$, $R(x,y)=x\geq y$. $\forall y\exists x\, x\geq y$ es V (tomar $x=y$), $\exists x\forall y\, x\geq y$ es F (no hay máximo en $\mathbb{N}$).

Ejemplo un poco más humano: la primera dice que "Existe una persona $x$ que es amiga de todas las personas $y$" y la segunda dice "Para cada persona $y$, existe una persona $x$ que es su amiga". Notar que la primera es muchisimo más estricta, si se cumple ambas son verdaderas, no asi al reves.

IV. $\exists x\, (S(x) \land T(x))$ y $\exists x\, S(x) \land \exists x\, T(x)$

No son equivalentes. La primera exige un mismo $x$ que cumpla ambas; la segunda permite $x$ distintos. Contraejemplo: $D=\{a,b\}$, $S(a)=V,T(a)=F$, $S(b)=F,T(b)=V$. $\exists x(S\land T)=F$, $\exists x S \land \exists x T = V$.

V. $\exists x\, (S(x) \lor T(x))$ y $\exists x\, S(x) \lor \exists x\, T(x)$

Sí son $i$-equivalentes. Si existe $a$ con $S(a)\lor T(a)$, entonces vale $S(a)$ o $T(a)$, así que $\exists x S \lor \exists x T$ vale. Y recíprocamente.

VI. $\forall x\, (S(x) \lor T(x))$ y $\forall x\, S(x) \lor \forall x\, T(x)$

No son equivalentes. Contraejemplo: $D=\{a,b\}$, $S(a)=V,T(a)=F$, $S(b)=F,T(b)=V$. $\forall x(S\lor T)=V$, $\forall x S \lor \forall x T = F$.

---

**Ejercicio 4.** Sea un lenguaje de primer orden con las siguientes características:

- Conjunto de constantes: $C = \{c, u\}$
- Sin símbolos de función: $F = \emptyset$
- Conjunto de símbolos de predicado: $P = \{A\}$

Sea $I$ la siguiente interpretación para ese lenguaje sobre el dominio de los números Naturales:

- $I(c) = 0$
- $I(u) = 1$
- $I(A(x, y)) = \, "x \leq y"$

Verificar si las siguientes afirmaciones son o no correctas. Justificar las respuestas.

- $A(c, x)$ es satisfactible en $I$.

  Sí. $A(c,x) \equiv 0 \leq x$. Tomando $s(x)=0$ se cumple $0 \leq 0$, por lo que existe una valuación que la hace V.

- $A(u, x)$ es satisfactible en $I$.

  Sí. $A(u,x) \equiv 1 \leq x$. Tomando $s(x)=1$ se cumple $1 \leq 1$, por lo que existe una valuación que la hace V.

- $\forall x\, A(c, x)$ es satisfactible en $I$.

  Sí. $\forall x\, A(c,x) \equiv \forall x\, (0 \leq x)$ es V en $I$ porque $0$ es menor o igual que todo natural. Al ser cerrada y V en $I$, es satisfactible en $I$.

- $\forall x\, A(u, x)$ es satisfactible en $I$.

  No. $\forall x\, A(u,x) \equiv \forall x\, (1 \leq x)$ es F en $I$ porque $1 \leq 0$ no se cumple ($0 \in \mathbb{N}$). Al ser cerrada y F en $I$, no es satisfactible en $I$.

- $A(c, x)$ es verdadera en $I$.

  No. $A(c,x)$ tiene la variable libre $x$, por lo que su valor de verdad depende de la valuación. Sin una asignación concreta, no se puede decir que sea simplemente "verdadera en $I$". Por ejemplo, con $s(x)=0$ es V, pero con $s(x)= -1$ (si extendiéramos el dominio) sería F. En $\mathbb{N}$, con $s(x)=0$ es V, con cualquier $s(x)$ es V, pero igual depende de la valuación.

- $\forall x\, A(c, x)$ es lógicamente válida.

  No. Es V en $I$ porque $I(c)=0$, pero no en toda interpretación. Por ejemplo, si $I'(c)=1$ en el mismo dominio $\mathbb{N}$, $\forall x\, A(c,x) \equiv \forall x\, (1 \leq x)$ es F ($0$ no lo cumple). Luego no es verdadera en toda interpretación, por lo tanto no es lógicamente válida.

- $A(u, c) \land \lnot A(u, c)$ es contradictoria.

  Sí. $A(u,c) \equiv 1 \leq 0$, que es F; $\lnot A(u,c)$ es V. Pero más allá de $I$, tiene la forma $B \land \lnot B$, que es una contradicción: es F en toda interpretación y bajo toda valuación.

---

**Ejercicio 5.** Ofrecer una interpretación donde las siguientes fórmulas sean todas verdaderas y otra donde sean falsas. Traducir en cada caso las fórmulas dadas a oraciones apropiadas en lenguaje natural.

### Interpretación donde todas son verdaderas

- **Dominio:** $\mathbb{N}$
- $c = 0$
- $f(x) = x$
- $P(x,y) = \, "x \leq y"$

| Fórmula | Traducción |
|---|---|
| I. $\forall x\, P(x,x)$ | "Todo número natural es menor o igual a sí mismo." (V, $\leq$ es reflexiva) |
| II. $\lnot (\forall x\, \forall y\, (P(x,y) \to P(y,x)))$ | "No es cierto que $\leq$ sea simétrica" (V, ej: $0\leq1$ pero $1\not\leq0$) |
| III. $\forall x\, \forall y\, \forall z\, ((P(x,y) \land P(y,z)) \to P(x,z))$ | "$\leq$ es transitiva" (V) |
| IV. $\forall x\, P(c,x)$ | "$0$ es menor o igual a todo natural" (V) |
| V. $\forall x\, P(x,f(x))$ | "Todo natural es menor o igual a sí mismo" (V, $x\leq x$) |

### Interpretación donde todas son falsas

- **Dominio:** $D = \{a, b, c\}$
- $c = a$
- $f(x) = x$
- $P = \{(a,b), (b,a)\}$ (solo esos dos pares)

| Fórmula | Traducción |
|---|---|
| I. $\forall x\, P(x,x)$ | "Cada elemento se relaciona consigo mismo" — F, ningún $x$ cumple $P(x,x)$. |
| II. $\lnot (\forall x\, \forall y\, (P(x,y) \to P(y,x)))$ | "No es cierto que $P$ sea simétrica" — F, $P$ sí es simétrica ($(a,b)$ y $(b,a)$ están). |
| III. $\forall x\, \forall y\, \forall z\, ((P(x,y) \land P(y,z)) \to P(x,z))$ | "Si $xPy$ e $yPz$ entonces $xPz$" — F, $P(a,b)\land P(b,a)$ pero $\lnot P(a,a)$. |
| IV. $\forall x\, P(c,x)$ | "$a$ se relaciona con todo elemento" — F, $a$ no se relaciona consigo mismo ni con $c$. |
| V. $\forall x\, P(x,f(x))$ | "Cada elemento se relaciona consigo mismo" — F, ningún $x$ cumple $P(x,x)$. |

---

**Ejercicio 6.** Determinar para cada una de las siguientes fbfs escritas en algún lenguaje de primer orden si son satisfactibles en alguna interpretación, verdaderas en alguna interpretación, falsas en alguna interpretación, lógicamente válidas o contradictorias. Fundamentar.

I. $\forall x\, P(x)$

- **Satisfactible:** Sí. Ej: $D=\{a\}$, $P(a)=V$.
- **Verdadera en alguna $I$:** Sí (la misma).
- **Falsa en alguna $I$:** Sí. Ej: $D=\{a\}$, $P(a)=F$.
- **Lógicamente válida:** No (es F en la interpretación anterior).
- **Contradictoria:** No (es V en la primera).

II. $(\forall x\, \forall y\, Q(x, y)) \to Q(x, y)$

- **Lógicamente válida.** Si $\forall x\forall y Q(x,y)$ es V en $I$ bajo $s$, entonces $Q(a,b)$ vale para todo $a,b\in D$, en particular para $s(x),s(y)$, luego el consecuente es V. Si el antecedente es F, la implicación es V directamente. Siempre V.

III. $\exists x\, (\exists y)\, Q(x, y) \to \exists y\, (\exists x)\, Q(x, y)$

- **Lógicamente válida.** Ambas fbfs son $i$-equivalentes (intercambiar existenciales no altera el significado), por lo que la implicación es siempre V.

IV. $Q(x) \to Q(x)$

- **Lógicamente válida.** Tiene la forma $A\to A$, tautología.

V. $\exists x\, (\lnot P(x)) \lor (\forall x)(P(x) \lor Q(x))$

- **Lógicamente válida.** Si $\exists x\lnot P(x)$ es F, entonces $\forall x P(x)$ es V, y por tanto $\forall x(P(x)\lor Q(x))$ es V. Si $\exists x\lnot P(x)$ es V, la disyunción es V. Siempre V.

---

**Ejercicio 7.** Determinar si las siguientes fbfs son (o no) lógicamente válidas o contradictorias. Fundamentar en cada caso.

I. $(\forall x\, (P(x) \lor Q(x))) \to (\forall x\, P(x) \lor \forall x\, Q(x))$

No es lógicamente válida. Contraejemplo: $D=\{a,b\}$, $P(a)=V,P(b)=F$, $Q(a)=F,Q(b)=V$.
- $\forall x(P\lor Q)$: $a$: $V\lor F=V$, $b$: $F\lor V=V$ → V.
- $\forall x P \lor \forall x Q$: $\forall x P$ es F ($b$ no), $\forall x Q$ es F ($a$ no) → F.
- $V\to F = F$.
Tampoco es contradictoria: puede ser V (ej: $D=\{a\}$, $P(a)=V$, $Q(a)=F$ da $V\to V$).

II. $P(x, y) \to P(x, y)$

Es lógicamente válida. Tiene la forma $A\to A$, tautología: para toda $I$ y toda $s$, si $P(x,y)[s]$ es V, el consecuente también; si es F, $F\to F=V$.

III. $P(x, y) \to \forall x\, \forall y\, P(x, y)$

No es lógicamente válida. Contraejemplo: $D=\{a,b\}$, $P(a,a)=V$, $P$ falso en cualquier otro caso. Sea $s(x)=a,s(y)=a$:
- $P(x,y)[s] = P(a,a) = V$.
- $\forall x\forall y P(x,y)$ es F (ej: $P(a,b)$ no se cumple).
- $V\to F = F$.
Dice que dado un par x,y que cumpla, entonces cualquier par x,y cumple.
Tampoco es contradictoria: puede ser V (ej: $D=\{a\}$, $P(a,a)=V$ da $V\to V$).

---

**Ejercicio 8.** Si la fbf $P(x)$ es satisfactible, ¿entonces la fbf $\exists x\, P(x)$ es lógicamente válida? Fundamentar.

No. $P(x)$ satisfactible significa que existe una interpretación $I$ y una valuación $s$ tales que $I\models P(x)[s]$. Que $\exists x P(x)$ sea lógicamente válida requeriría que $I\models\exists x P(x)[s]$ para **toda** interpretación $I$ y toda valuación $s$, lo cual no se sigue.

Contraejemplo: $D=\{a\}$, con $P(a)=V$. $P(x)$ es satisfactible (tomar $s(x)=a$). Pero en $I'$ con $D'=\{a\}$, $P'(a)=F$, $\exists x P(x)$ es F. Por lo tanto $\exists x P(x)$ no es lógicamente válida.

---
