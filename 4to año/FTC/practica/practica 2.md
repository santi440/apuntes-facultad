# Ejercicio 1. 
Responder breve y claramente los siguientes incisos:
1. ¿En qué se diferencian los lenguajes recursivos, los lenguajes recursivamente enumerables no recursivos y los lenguajes no recursivamente enumerables?

 lenguajes recursivos: aquellos lenguajes sobre los que podemos construir una maquina de turing que simpre decida por si o por no.
 
 lenguaje recursivamente enumerable: aquellos lenguajes sobre los que podemos definir una maquina de turing que en caso afirmativo siempre responde que si y en casos negativo puede responder que si o loopear.

lenguaje no recursivamente enumerables:  No existe ninguna MT capaz siquiera de reconocer todas las cadenas del lenguaje; para algunas cadenas que sí pertenecen, la MT nunca se detendría.
 
1. Probar que R  RE  L. Ayuda: usar las definiciones.
Si L es el conjunto de todos los lenguajes RE esta contenido dentro de él. R esta contenido dentro de RE debido a que el subconjunto que este siempre pare esta contenido dentro del que **puede** no parar 

2. Dijimos en clase que el hecho de que si L es recursivo entonces LC también lo es, significa en términos de problemas que si un problema es decidible entonces también lo es el problema contrario. ¿Qué significa en términos de problemas que la intersección de dos lenguajes recursivos es también un lenguaje recursivo?

Si puedes decidir L1​ y L2​, puedes construir una MT que corra ambas y acepte solo si ambas aceptan.

1. Explicar por qué no es correcta la siguiente prueba de que si L ∈ RE, también LC ∈ RE: dada una MT M que acepta L, entonces la MT M’, igual que M pero con los estados finales permutados, acepta LC.

La prueba falla porque en los lenguajes RE, la MT puede **no detenerse (entrar en bucle)** para cadenas que no pertenecen al lenguaje.

- Si solo permutas estados finales, las cadenas que antes hacían que la MT colgara (no aceptadas), seguirán haciendo que la MT cuelgue en la "nueva" máquina. No pasarán a ser "aceptadas", por lo que no estarías reconociendo el complemento.

2. ¿Qué lenguajes de la clase CO-RE tienen MT que los aceptan? ¿También los deciden?

Los que estan englobados dentro de R. Sobre la segunda pregunta si

3. Probar que el lenguaje Ʃ* de todas las cadenas y el lenguaje vacío ∅ son recursivos. Alcanza con plantear la idea general. Ayuda: encontrar MT que los decidan.

Para el lenguaje  Ʃ*  como contempla todas las cadenas bastaria con construir una maquina de turing que siempre devuelva positivo o si(qA). Caso contrario para el vacio, siempre devuelve negativo o no (qR).

4. Probar que todo lenguaje finito es recursivo. Alcanza con plantear la idea general. Ayuda: encontrar una MT que lo decida (pensar cómo definir sus transiciones para cada una de las cadenas del lenguaje).

Si poseo un lenguaje finito puedo construir una maquina de turing que siempre pare. Bastaria con iterar sobre el conjunto finito de elementos (se que no va a entrar en bucle porque es recorrer una lista finita), si esta lo acepto y sino qR
# Ejercicio 2.
Considerando la Propiedad 2 estudiada en clase:
4. ¿Cómo implementaría la copia de la entrada w en la cinta 2 de la MT M?
5. ¿Cómo implementaría el borrado del contenido de la cinta 2 de la MT M?