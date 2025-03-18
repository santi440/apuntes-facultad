# El objetivo: 
- Evaluar y analizar los lenguajes de programación y elegir cuál usar en cada momento según las necesidades del proyecto. Los lenguajes de programación nos permiten comunicarnos con la máquinas y las personas. 
- El valor de un lenguaje se debe juzgar según la forma en que afecta la producción de Software y a la facilidad con la que puede integrarse a otras herramientas.
# Criterios de diseño de un lenguaje:
//se sobre entiende en toda la materia que refiere a lenguaje de programación
- Simplicidad y legibilidad: Poder producir programas fáciles de escribir y de leer. Fácil de aprender o enseñar.
- Claridad en los bindings: claro en el momento que se realiza la ligadura de tipos, debe haber constancia de cuando se realiza.
- Confiabilidad = seguridad: chequear tipos cuanto antes para prevenir errores y brindar un manejo de excepciones para capturar el error y realizar el tratamiento correspondiente.
- Soporte: Debería ser accesible para cualquiera que quiera usarlo o instalarlo(idealmente de dominio público) y multiplataforma. Con documentación y otros medios para familiarizarse.
- Abstracción: En términos de procesos y de datos, útil para manejar la complejidad en forma simple. Capacidad de definir y usar estructuras u operaciones complicadas de manera que sea posible ignorar muchos de los detalles, sin reinventar la rueda.
- Ortogonalidad: refiere a la posibilidad que ofrece el lenguaje de poder combinar sus elementos sin producir errores, con un conjunto pequeño de primitivas y de reglas de combinación se pueden construir estructuras de control y datos más complejas y fáciles de entender para el programador. Contraejemplo: en C una función devuelve cualquier tipo de dato despues de la palabra reservada return excepto un array u otra función.
- Eficiencia:
	- Tiempo y espacio: Cuanto le toma a un programa ejecutarse y como maneja los recursos de memoria, tráfico de red y disco.
	- Esfuerzo humano: Cuanto cuesta encontrar a alguien que conozca este lenguaje o capacitar a alguien / aprenderlo y corregir los errores que surjan.
	- Optimizable: Que herramientas tiene el lenguaje para hacer el código mucho mas optimo, por ejemplo con procesos predefinidos de tareas especificas.
# Sintaxis vs semántica
Lenguaje de programación -> notación formal
Sintaxis-> forma de escribir las instrucciones. Conjunto de reglas que definen como componer letras, dígitos y otros caracteres para formar los programas
Semántica -> el significado de eso que escribimos.Conjunto de reglas para dar significado a los programas sintácticamente válidos, el código tiene sentido solo si está bien escrito.

> [!Example] Por ejemplo
> En java, definir un arreglo de 10 espacios es:
> ```
> int [] arreglo = new int[10];
> ```
> - La sintaxis involucra la definición con sus reglas y palabras reservadas propias del lenguaje.
> - La semántica involucrara cuando quiera acceder a la posición 10 de dicho arreglo, me lanzara un error porque comienza en 0 y reserva 10 posiciones (0..9), deberia acceder a las novena.

La sintáxis debe contemplar soluciones a características tales como:
- Legibilidad
- Verificabilidad
- Traducción
- Falta de ambigüedad
Relacionado a los criterios de diseño vistos más arriba. 
## Sintaxis
Establece reglas que definen cómo deben combinarse las componentes básicas para formar sentencias y programas, es la manera en que escribimos.
### Elementos:
- Alfabeto o conjunto de caracteres: Se compara según los bits en el orden en que aparecen en el alfabeto, algunos caracteres aparecen antes que otros en algunos alfabetos. Por estándar, se usa el ASCII extendido. 
- Identificadores: Cadena de letras y dígitos, muchas veces obligatoriamente comienzan con una letra. Restringir su longitud afecta a su legibilidad. Si tengo disponible sola una letra es poco expresivo y tengo limitada cantidad de posibilidades. 
- Operadores: Sumas, restas, multiplicación,etc. Los dos primeros son uniformes en los distintos lenguajes, hay otros que varian como elevado que en algunos es ** y otros "^"
- Comentarios y uso de blancos: Se lee un programa más veces de lo que se escribe. Es necesario dejar algún tipo de documentación o explicación para mejorar la lectura y entendimiento.
- Palabra clave y palabra reservada:
	- Palabra clave o keywords, son palabras que tienen un significado dentro de un contexto.
	- Palabra reservada, son palabras claves que además no pueden ser usadas por el programador como identificador de otra entidad
### Estructura
- Words:Conjunto de caracteres y palabras necesarias para construir expresiones, sentencias y programas. Ej: identificadores, operadores, palabras claves, etc. No son elementales se construyen a partir del alfabeto.
- Expresiones: Son construcciones sintácticas compuestas de operadores y operandos, de cuya evaluación se obtiene un valor, a partir de los cuales se construyen las sentencias y programas
- Sentencias: Componente más importante. Impactan en la facilidad de escritura y legibilidad. Las hay simples, estructuradas y anidadas.
### Reglas
- Léxicas:Conjunto de reglas para formar las “word”, a partir de los caracteres del alfabeto. Por ejemplo el != o <> o en php que los identificadores comienzan con $.
- Sintácticas: Conjunto de reglas que definen cómo formar a partir de esas palabras, las “expresiones” y “sentencias”. El If en C no lleva “”then””, en Pascal si
### Tipos
- Abstracta: Se refiere a la estructura. Si entre dos códigos de lenguajes de programación diferentes que estructuralmente son iguales, no en termino bien bien puntuales sintácticos, se dice que la sintaxis abstracta es la misma 

> [!Example] Los dos códigos tienen la misma estructura de while condicion codigo pero difieren en especificaciones concretas de la sintaxis de cada lenguaje.
> En C
> ```
> 	while (x!= y){
> 		.....
> 	}
> ```
> En Pascal 
> ```
> 	while(x<>y) do
> 	begin
> 		....
> 	end;
> ```

- Concreta: Se refiere a la parte léxica, es exactamente lo que tenemos escrito.
- Pragmática: Se refiere al uso práctico. En C y Pascal {} o begin-end pueden omitirse si el
bloque está compuesto por una sola sentencia, puede conducir a error ya que si se necesitara agregar una sentencia debe agregar el begin end o las {} (es decir
que no define una única forma de escribirse).

### Cómo definir la sintaxis
Se necesitan métodos formales para describir un conjunto finito de reglas que permitan definir un conjunto infinito de programas bien escritos.
Ventajas:
- Brinda una única documentación, concreta, fiable y libre de ambiguedad
- Facilita el testeo, pruebas de implementación y automatización
#### Formas para definir
##### Lenguaje natural	
##### Gramáticas libres de contexto (Backus Naun Form):
Da reglas precisas de como se compone el lenguaje independientemente de que tenga sentido en el contexto, importa que lo escribamos bien.
Es una notación formal para describir la sintaxis, corresponde a un metalenguaje porque esta más arriba que el lenguaje en si. Los metasimbolos que utiliza son <, >, ::= y  | .
//la barra es para los opcionales 
![[Ejemplo BNF.png]]
No terminal: Permiten definir las reglas de mi gramatica. Necesita una producción, una regla que lo especifique. 
Terminal: No hay manera más átomica de escribirlo.
Del lado derecho del ::= puede haber una combinación entre terminales y no terminales.
El uso de BNF hace que formemos una Gramática.
###### Gramática:
Conjunto de reglas finita que define un conjunto infinito de posibles sentencias válidas
en el lenguaje. Es una 4-tupla:
 **G = ( N, T, S, P)**
 N= Conjunto de símbolos no terminales
 T= Conjunto de símbolos terminales
 S= Simbolo distinguido de la gramatica que pertenece a N (No terminales)
 P= Conjunto de producciones 
##### Diagramas sintácticos 
##### Árboles