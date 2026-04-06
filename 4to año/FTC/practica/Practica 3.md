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
1. Se setea una variable contadora en unario en la primera cinta.
2. En la segunda cinta se ejecuta la M1 las k cadenas en base al contador cinta 1. 
3. reviso si la cadena w esta en la segunda cinta
4. se aumenta en 1 el contador
5. se vuelve al paso 2
## Ejercicio 5
El lenguaje LU = {(<M>, w) | M acepta w} se conoce como lenguaje universal, y representa el problema general de aceptación. Probar que LU ∈ RE. Ayuda: construir una MT que acepte LU.
![[Pasted image 20260406170356.png]]

Estamos diciendo que deberiamos de construir una MT que sea capaz de recibir una M codificada y una cadena w y aceptar en caso de que M acepte, rechace o loopea. 
Si suponemos lo contrario, caso en que somos capaces de construir una MT que sea decidible, seriamos capaces de resolver el HP. Una MT M puede ser cualquiera, por ejemplo la MT que resuelve el HP. Si la maquina que estamos construyendo siempre para por si o por no, podriamso resolver el HP. 

## Ejercicio 6
Ejercicio 6. Una función f : A ⟶ B es total computable sii existe una MT Mf que la computa para
todo elemento a  A. Sea la función f01 : Ʃ* ⟶ {0, 1} tal que:
f01(v) = 1, si v = (<M>, w) y M para a partir de w.
f01(v) = 0, si v = (<M>, w) y M no para a partir de w o bien v ≠ (<M>, w).
Probar que f01 no es total computable. Ayuda: ¿con qué problema se relaciona dicha función?

Se puede relacionar con el HP, podemos afirmar esto debido a que la maquina que estemos construyendo para por si o por no (caso que v no pertenezca) o loopea. Entoncess nuestra MT pertenece a RE


## Ejercicio 7
Responder breve y claramente cada uno de los siguientes incisos (en todos los casos, las MT mencionadas tienen una sola cinta):
a. Probar que se puede decidir si una MT M, a partir de la cadena vacía λ, escribe alguna vez un símbolo no blanco. Ayuda: ¿Cuántos pasos puede hacer M antes de entrar en un loop?

Sí, es decidible.Una MT con ∣Q∣ estados y una cinta inicialmente blanca puede realizar como máximo ∣Q∣ pasos sin repetir una configuración (estado, posición del cabezal y contenido de la cinta).

- Si en esos ∣Q∣ pasos no escribió un símbolo no blanco, solo pudo haberse movido por la cinta leyendo blancos.
- Al paso ∣Q∣+1, necesariamente repetirá un estado habiendo leído solo blancos, entrando en un bucle infinito donde nunca escribirá nada distinto.
- Algoritmo: Simular M durante ∣Q∣ pasos. Si escribe un símbolo no blanco, aceptar; si no, rechazar.

b. Probar que se puede decidir si una MT M que sólo se mueve a la derecha, a partir de una cadena w, para, Ayuda: ¿Cuántos pasos puede hacer M antes de entrar en un loop?

Si solo se mueve a la derecha podemos afirmar que avanza o se detiene exclusivamente. Con una entrada de datos w de tamaño N tenemos Q estados lo que nos da Q (caso qus se detiene) + (N+1) x Q (caso en que se mueve) combinaciones posibles por las que puede pasar la MT, es decir los a lo sumo Q (caso qus se detiene) + (N+1) x Q  pasos.

c. Probar que se puede decidir si dada una MT M, existe una cadena w a partir de la cual M para en a lo sumo 10 pasos. Ayuda: ¿Hasta qué tamaño de cadenas hay que chequear?

Como la cantidad de pasos es finita podemos decidir (frenar por si o por no) si a lo sumo luego de la ejecucion del decimo paso finaliza la maquina acepta, si deberia continuar la maquina rechaza. 
A lo sumo podria mirar los primeros 10 simbolos de la cadena de entrada

d. ¿Se puede decidir si dada una MT M, existe una cadena w de a lo sumo 10 símbolos a partir de la cual M para? Justificar la respuesta.

No es decidible debido a que no sabemos si en alguno de los simbolos anteriores al limite de la cadena M no loopea. Podria darse el caso que por la construccion de M en cierto caracter loopea, si aparece en el espacio de la cadena, entramos en bucle y no es decidible.
