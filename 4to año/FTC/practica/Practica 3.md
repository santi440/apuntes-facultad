(Se que el l1 lo hice en casa)

## Ejercicio 2. 
Explicar cómo enumeraría los números naturales pares, los números enteros, los números racionales (o fraccionarios), y las cadenas de Ʃ* siendo Ʃ = {0, 1}.


- Naturales Pares
En dos cintas una auxiliar y otra de output. 
1. Recorro la cinta auxiliar e imprimo en la de output tantos 1 como elementos no blancos tenga la cinta auxiliar 
2. En la cinta auxiliar escribo dos 1 para llevar un contador codificado en unario.  
3. escribo una "," u otro separador 
4. Vuelvo al paso 1.

imprime los numeros pares codificados en unario separados por coma.
0 = **λ**
2 = 11
4 =1111

- enteros
En 2 cintas una auxiliar y otra de output.
1. Escribo cadena vacia y una coma.   
2. En la cinta auxiliar escribo un 1 para llevar un contador codificado en unario. 
3. Recorro la cinta auxiliar e imprimo en la de output tantos 1 como elementos no blancos tenga la cinta auxiliar 
4. escribo una "," u otro separador 
5. escribo -
6. recorro hacia la izquierda hasta un B escribiendo tantos 1 como 1 hay en la cinta auxiliar.
7. escribo ,
8. Vuelvo al paso 2.

Escribe intercalado entre positivo y negativo 
0 = **λ**
1= 1
-1 = -1
2 = 11
-2 = -11

- Números racionales Todo número racional puede escribirse como p / q , entonces para enumerar a todos los racionales se puede enumerar todos los pares p y q. Se pueden formar combinaciones del tipo p+q = n, siendo n un número natural que se lleva como contador. Formar todas las cadenas  que sumen 1 (0/1), todas las que sumen 2 (0/2,1/1), todas las que sumen 3 (0/3. 1/2, 2/,1) .... 

- Cadenas de ∑ * ==?==
1. Se tiene una MT de dos cintas. Se inicializa la cinta 1 con un 0
2. Se copia en la cinta 2 todo símbolo 0 y 1 de la cinta 1 y se añade un separador para separar los números
3. Se incrementa el número de la cinta 1, moviendose al extremo derecho y siguiendo una serie de reglas
    1. Si el número en el extremo derecho es un 0 -> se cambia por un 1
    2. Si el número en el extremo derecho es un 1 -> se cambia por un 0 y se sigue moviendo hacia la izquierda hasta encontrar otro 0, si lo encuentra, se cambia por un 1. Por cada 1 encontrado, se escribe un 0
    3. Si todos los números fueron 1, y se llega a un Blanco, entonces ese Blanco es cambiado por un 1
4. Repetir desde el paso 2
## Ejercicio 3
Dar la idea general de cómo sería una MT que, teniendo como cadena de entrada un número natural i, genera la i-ésima fórmula booleana satisfactible según el orden canónico.
Comentario: asumir que existen una MT M1 que determina si una cadena es una fórmula booleana, y una MT M2 que determina si una fórmula booleana es satisfactible.

Dadas las MT que me dan no tengo forma de generar una formula booleana facilmente. 

MT M3: Recibe un i por parametro y 
1. setea una variable c que mantiene el contador en 0.
2. Mientras c < i , genera en orden canonico una cadena 
3. ejecuta el codigo de M1 para determinar si la cadena es una cadena valida.
4. En caso afirmativo, ejecuta el codigo de M2 para determinar si es satisfactible e incrementa en 1 el contador c
5. Vuelve al paso 2

Puedo asumir que M1 y M2 ∈ R? Dice que determina, lo cual, me lleva a oebsar que no loopea 

## Ejercicio 4
Sea M1 una MT que genera en su cinta de salida todas las cadenas de un lenguaje L. Dar la idea general de cómo sería una MT M2 que, usando M1, acepte una cadena w sii w ∈ L

M2 podria: 
1. Generar en una cinta auxiliar el lenguaje L usando M1