# Ejercicio 1.
¿Qué es una MT universal?
![[Pasted image 20260329202632.png]]
Una maquina de turing capaz de ejecutar cualquier otra maquina de turing que le venga como entrada junto a un input w. <M> es una maquina de turing codificada para poder ser recibida.
# Ejercicio 2.
Explicar cómo enumeraría los números naturales pares, los números enteros, los
números racionales (o fraccionarios), y las cadenas de Ʃ* siendo Ʃ = {0, 1}.

1. Se hace una 

Ejercicio 3. Dar la idea general de cómo sería una MT que, teniendo como cadena de entrada
un número natural i, genera la i-ésima fórmula booleana satisfactible según el orden canónico.
Comentario: asumir que existen una MT M1 que determina si una cadena es una fórmula
booleana, y una MT M2 que determina si una fórmula booleana es satisfactible.
Ejercicio 4. Sea M1 una MT que genera en su cinta de salida todas las cadenas de un lenguaje
L. Dar la idea general de cómo sería una MT M2 que, usando M1, acepte una cadena w sii w ∈ L.
Ejercicio 5. El lenguaje LU = {(<M>, w) | M acepta w} se conoce como lenguaje universal, y
representa el problema general de aceptación. Probar que LU ∈ RE. Ayuda: construir una MT que
acepte LU.
Ejercicio 6. Una función f : A ⟶ B es total computable sii existe una MT Mf que la computa para
todo elemento a  A. Sea la función f01 : Ʃ* ⟶ {0, 1} tal que:
f01(v) = 1, si v = (<M>, w) y M para a partir de w.
f01(v) = 0, si v = (<M>, w) y M no para a partir de w o bien v ≠ (<M>, w).
Probar que f01 no es total computable. Ayuda: ¿con qué problema se relaciona dicha función?
Ejercicio 7. Responder breve y claramente cada uno de los siguientes incisos (en todos los
casos, las MT mencionadas tienen una sola cinta):
a. Probar que se puede decidir si una MT M, a partir de la cadena vacía λ, escribe alguna vez
un símbolo no blanco. Ayuda: ¿Cuántos pasos puede hacer M antes de entrar en un loop?
b. Probar que se puede decidir si una MT M que sólo se mueve a la derecha, a partir de una
cadena w, para, Ayuda: ¿Cuántos pasos puede hacer M antes de entrar en un loop?
c. Probar que se puede decidir si dada una MT M, existe una cadena w a partir de la cual M
para en a lo sumo 10 pasos. Ayuda: ¿Hasta qué tamaño de cadenas hay que chequear?
d. ¿Se puede decidir si dada una MT M, existe una cadena w de a lo sumo 10 símbolos a partir
de la cual M para? Justificar la respuesta