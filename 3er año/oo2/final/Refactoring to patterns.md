# Replace Constructor with method
Surge cuando el uso de constructores que se llamen igual resulta ambiguo sobre el tipo de objeto que estamos creando y sus caracteristicas
1. Extract method en la clase que llame al constructor. para luego aplicar un Move method a la clase que estamos creando.
2. Remplazar todas las demas llamadas por el nuevo metodo que creamos (entiendo que deberia de haber "codigo duplicado", del tipo new MiSuperClase(...), sino lo hay no hago nada)
3. Inline method: Dice que el constructor en la clase que estabamos creando que hacia internamente lo que ahora hace nuestro metodo más explicativo  deberiamos eliminarlo porque solo aporta ruido
4. Repetir los pasos 1 a 3 con todos los constructores que queramos remplazar con metodos creadores? (no se si es una buena traduccion de Creation Method)
5. El único constructor nos quedó, el más generico, que usan todos nuestros metodos no es llamado desde afuera nunca más = lo hacemos privado.
Por último, de existir 50 combinaciones diferentes de constructores no hay que crear 50 metodos diferentes. Se puede dejar un constructor  generico publico y hacer metodos para los constructores mas usados. En caso de que tengas de igual forma MULTIPLES metodos que te distraigan de la responsabilidad de la clase original (Single responsability, hablamos de la S de principios SOLID) aplicamos `Extract Factory` que se encargara de ser la cara visible para el cliente de como creamos nuestra clase.

# Replace condicional Logic with Strategy
1. Crear Strategy
2. Move method a la clase strategy
3. Extract Paramether, pasar la estrategia a la clase que la necesite
4. Replace condicional with polymorphism -> implica mover parte por parte de la clase strategy a la las nuevas clases que creamos. extract method
