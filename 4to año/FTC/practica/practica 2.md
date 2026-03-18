Ejercicio 1. Responder breve y claramente los siguientes incisos:
1. ¿En qué se diferencian los lenguajes recursivos, los lenguajes recursivamente enumerables no recursivos y los lenguajes no recursivamente enumerables?

 lenguajes recursivos: aquellos lenguajes sobre los que podemos construir una maquina de turing que simpre decida por si o por no.
 
 lenguaje recursivamente enumerable: aquellos lenguajes sobre los que podemos definir una maquina de turing que en caso afirmativo siempre responde que si y en casos negativo puede responder que si o loopear.

lenguaje no recursivamente enumerables:  Aquellos lenguajes sobre los que no se puede construir una maquina de turing  pero se puede infereir su subconjunto por complemento de su contraparte ( lenguaje recursivamente enumerable)
 
1. Probar que R  RE  L. Ayuda: usar las definiciones.
Si L es el conjunto de todos los lenguajes RE esta contenido dentro de él. R esta contenido dentro de RE debido a que el subconjunto que este siempre pare esta contenido dentro del que **puede** no parar 

2. Dijimos en clase que el hecho de que si L es recursivo entonces LC también lo es, significa en términos de problemas que si un problema es decidible entonces también lo es el problema contrario. ¿Qué significa en términos de problemas que la intersección de dos lenguajes recursivos es también un lenguaje recursivo?

Significa que en terminos de problemas ambos lenguajes deben ser aceptados por una maquina de turing para poder ser considerados. 

1. Explicar por qué no es correcta la siguiente prueba de que si L ∈ RE, también LC ∈ RE: dada una MT M que acepta L, entonces la MT M’, igual que M pero con los estados finales permutados, acepta LC.

La definicion presentada podria corresponderse a L∈ R, también LC ∈ R. El complemento de una maquina de turing que si es afirmativo siempre para o si es negativo puede loopear seria una maquina que en caso afirmativo puede loopear y en caso negativo siempre para. Lo que no se corresponde con la definicion. Si L ∈ RE, LC∈ CO-RE

2. ¿Qué lenguajes de la clase CO-RE tienen MT que los aceptan? ¿También los deciden?

Los que estan englobados dentro de R. Sobre la segunda pregunta si

3. Probar que el lenguaje Ʃ* de todas las cadenas y el lenguaje vacío ∅ son recursivos. Alcanza con plantear la idea general. Ayuda: encontrar MT que los decidan.

Dado una entrada w ∈ Ʃ*, al ingresar a la maquina de Turing es aceptado. caso contrario rechazado.


4. Probar que todo lenguaje finito es recursivo. Alcanza con plantear la idea general. Ayuda: encontrar una MT que lo decida (pensar cómo definir sus transiciones para cada una de las cadenas del lenguaje).
5. 
Ejercicio 2. Considerando la Propiedad 2 estudiada en clase:
5. ¿Cómo implementaría la copia de la entrada w en la cinta 2 de la MT M?
6. ¿Cómo implementaría el borrado del contenido de la cinta 2 de la MT M?