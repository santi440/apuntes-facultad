![[Pasted image 20260706101958.png]]

a) FNC = $( \lnot p \lor q ) \land (p \lor \lnot q)$
b) no, para que sea logicamente equivalente deberia de poder ser reemplazadas en forma indistinta y sus tablas de verdad sean identicas. $(\lnot p \land p)$ es una contradicción, siempre es falsa.

Ejercicio 2. Determinar si el siguiente conjunto r = {p, q, ¬(p ^ q)} de fórmulas bien formadas (fbfs) es satisfactible. Justificar.

para que el conjunto sea satisfactible, las tres fbf deben ser verdaderas al menos en una de sus filas para un cierto valor de verdad de sus elementos.
Para que p = V, q = V, la última si partimos de lo anterior es imposible que se de por que $(p \land q)$ dará verdadero y al negarlo falso. Se puede hacer la tablita tmb

Ejercicio 3. Dadas dos fórmulas A y B, si se cumple que A → B y $\lnot B$ son tautologías, ¿A también lo es?  

No. Sabemos que si A → B  es tautologia siempre arroja verdadero. Viendo su tabla de verdad: 

| A   | B   | A → B |
| --- | --- | ----- |
| V   | V   | V     |
| V   | F   | F     |
| F   | V   | V     |
| F   | F   | V     |

nos elimina la 2da fila. 
Sobre eso tambien sabemos que $\lnot B$ es tautologia. Nos quedamos con los casos en que $\lnot B$ sea Verdadero (o B sea falso, que es lo mismo) = Nos queda la última fila, donde para que ambas precondiciones dadas por el enunciado se cumplan, solo es posible que A=F y B=F. A no es tautologia


Ejercicio 4. Dada la siguiente argumentación en lenguaje natural dar una forma argumentativa en lenguaje simbólico que se corresponda con ella y determinar si es válida o inválida:  
"El modelo clasifica bien, si los datos son precisos. Los datos no son precisos. Por lo tanto, el modelo no clasifica bien."

p= los datos son precisos
q = El modelo clasifica bien

{$(p \to q) , \lnot p$} $\vdash \lnot q$ 

No. Presento un contraejemplo para demostrar que es invalida. p = F y q = V (datos no precisos y modelo clasifica bien). 
- premisa 1: $p \to q$ da Verdadero. 
- premisa 2: $\lnot p$ da verdadero.
- conclusion es falso. 

Ejercicio 5. Considerando el sistema formal L . 
a) Proveer una prueba sintáctica para demostrar que {r} $\vdash (p \to r)$. Utilizar SOLO: la regla de inferencia modus ponens (MP), la fórmula dentro del conjunto de hipótesis y los axiomas de L  (L1, L2 o L3) que considere necesario. No usar el Teorema de la Deducción.

![[Pasted image 20260706110021.png]]

1.Axioma L1, con A=r y B=p. $(r \to (p \to r))$
2.hipotesis 1. r
3.MP entre 1 y 2. $p \to r$
b) Considerando lo anterior, ¿es (p→r) un teorema de L? Justificar  
No porque el conjunto de hipotesis no es vacio, tiene r.
Ejercicio 6. Dada la siguiente fbf escrita en un lenguaje de primer orden determinar si se trata de una fbf abierta o cerrada. Justificar indicando las ocurrencias libres o ligadas de $x_1 , x_2, x_3$   $$(\exists x_3)(A(x_1,x_2) \to (\forall x_1)(\forall x_2) A(x_1, x_2) \land B(f(x_3)))$$
$x_3$ esta ligada en toda la fbf, esta en el scope del exists del principio que abarca todo el resto de la fbf.
$x_1$ en la primera aparición $A(x_1,x_2)$, antes del entonces, esta libre, no hay un cuantificador a la izquierda que la involucre, en su segunda aparición, del otro lado del entonces esta ligada al para todo.
$x_2$ mismo caso que $x_1$

Como tiene al menos una variable libre en algun momento de su especificación decimos que es un fbf abierta.

Ejercicio 7. Determinar si la siguiente fórmula "$(\forall x) P(a,f(x))$" es verdadera en alguna interpretación sobre el dominio de los números naturales. Justificar.  
Nota: Constantes = {a}

Sí, existe una interpretación sobre ℕ que la hace verdadera.

| Símbolo | Interpretación |
|---------|---------------|
| Dominio | ℕ |
| a | 0 |
| f | función constante f(n) = 0 para todo n ∈ ℕ |
| P | {(0,0)} (o la relación universal ℕ × ℕ) |

(∀x) P(a, f(x)) equivale a (∀x) P(0, 0), que es verdadero porque P(0,0) se cumple para todo x ∈ ℕ.

Ejercicio 8. Determinar si la siguiente fórmula "$(\forall x) P(x)$" es lógicamente válida. Justificar.

No porque deberia de ser verdadero en todas sus interpretaciones. Si me baso en la interpretación de que  P=V si es valida pero en caso de que P'=F ya no lo es. Existe un contraejemplo en que no es verdadero.

Ejercicio 9. En lógica de predicados, ¿si la fbf A es satisfactible en una interpretación, es cierto que A es también lógicamente válida?. Justificar.

Falacias. Que sea satisfactible implica que al menos una interpretación da verdadera pero no asegura que todas den verdaderas, si lo hace asi que sea lógicamente válida. 
Lógicamente válida es mucho más estricta, si se da que una fbf B es LV, tambien seria satisfactible pero no al reves
  
Ejercicio 10. Especificar un programa que a partir de un estado inicial en el que x sea mayor que 0, termine en un estado final en el que y sea el doble del valor inicial de x.  

{$x=X \land X>0$} y:=2*x {y= 2X} 

Ejercicio 11. Aplicar el axioma de asignación (ASI) para obtener la precondicion ({ ? }) correspondiente a las siguiente terna de Hoare: { ?} x:= y {x $\ne$ 0}  

ASI es {P(e)} x:=e {P(x)}. Si reemplazamos con lo que tenemos {P(e)} x:=y {x $\ne$ 0}, entonces {y $\ne$ 0} x:=y {x $\ne$ 0}

Ejercicio 12. Dada la implicación (y > 10) → (y >= 0) y la terna de Hoare {y >=0} y := 1 {y >= 1}, indicar cómo quedaría la terna de Hoare resultante si se aplica la regla CONS entre ambas.

Regla CONS (consecuencia):
```
P → P'    {P'} C {Q}
────────────────────
     {P} C {Q}
```

Acá:
- P = (y > 10), P' = (y ≥ 0). Se cumple (y > 10) → (y ≥ 0) ✓
- C = y := 1
- Q = (y ≥ 1)

Aplicando CONS:
```
(y > 10) → (y ≥ 0)    {y ≥ 0} y := 1 {y ≥ 1}
───────────────────────────────────────────────
                {y > 10} y := 1 {y ≥ 1}
```

Terna resultante: **{y > 10} y := 1 {y ≥ 1}**