# Ejercicio 1. 
Expresar en un lenguaje de predicados de primer orden las siguientes afirmaciones:
I. Algunas aves no vuelan =  $\exists(x)$ (A(x) ^ ~V(x))
II. No todas las aves vuelan = ~$\forall(x)$ (A(x) -> V(x) )
Analizar la relación entre ambas. Mostrar cómo se puede transformar una expresión en la otra.
Representan lo mismo. Y se puede demostrar
1. La el segundo predicado
$$\neg \forall x (A(x) \rightarrow V(x))$$
2. Negacion para adentro: $$\exists x \, \neg(A(x) \rightarrow V(x))$$
3. reemplazar el condicional $$\exists x \, \neg(\neg A(x) \lor V(x))$$
4. De morgan de la negacion de afuera $$\exists x (A(x) \land \neg V(x))$$
# Ejercicio 2. 
Expresar en un lenguaje de predicados de primer orden el conocimiento asociado a
las siguientes situaciones:
I. Los usuarios que contribuyen en proyectos open source son colaborativos.
$$\forall(x)((U(x) \land C(x))\rightarrow Colab(x))$$
II. Ningún sistema que tenga bugs críticos puede ser entregado ni desplegado en producción.
$$\forall(x) (S(x) \land B(x) \rightarrow (\lnot E(x) \lor \lnot D(x)))$$

III. Ningún modelo de IA que se entrena con datos erróneos es preciso.
$$\forall(x) (M(x) \land E(x) \rightarrow \lnot P(x))$$

IV. Todo estudiante que cursa FTC (Fundamentos de Teoría de la computación) y sube sus ejercicios a IDEAS aprueba la práctica.
$$\forall(x) ((E(x) \land C(x) \land S(x)) \rightarrow A(x))$$
V. Todos los alumnos de FTC, cuyo documento es par y han aprobado el parcial con nota mayor a 7 están inscriptos en la mesa de finales de agosto.
$$\forall(x) ((A(x) \land P(x) \land M(x)) \rightarrow I(x) )$$

VI. Todos los estudiantes que cursan FTC y subieron correctamente el código al repositorio
están habilitados para correr las pruebas automáticas del sistema.
$$\forall(x)((E(x) \land S(x)) \rightarrow H(x))$$

VII. Algunos modelos de inteligencia artificial entrenados por alumnos de FTC lograron
superar el umbral de precisión del 90%.
$$\exists(x) (M(x) \land E(x,ftc) \land U(x))$$


# Ejercicio 3. 
Escribir las siguientes proposiciones usando un lenguaje de predicados de primer orden:
I. El cero es el menor natural.
$$\forall(x) (N(x) \rightarrow (M(0,x))$$
II. El conjunto vacío está incluido en cualquier conjunto.
$$\forall(x)(C(x) \rightarrow I(\emptyset,x))$$

III. Si se prueba una propiedad para el cero y luego se prueba que esa misma propiedad vale para el número n+1 si vale para n, entonces se ha probado que la propiedad vale para cualquier natural.
$$(P(0) \land \forall(n) ((N(n) \land P(n) \rightarrow P(f(n)))) \rightarrow \forall(x) (N(x) \rightarrow P(n))$$

IV. Si hay un número natural que cumple una cierta propiedad, entonces hay un mínimo natural que cumple esa propiedad.
$$\exists(x) (N(x) \land P(x)) \rightarrow \exists(y) (N(y) \land P(y) \land (\forall(n)(N(n) \land P(n) \rightarrow M(y,n))))$$