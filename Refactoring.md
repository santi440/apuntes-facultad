Transformación de código que preserva el comportamiento pero mejora el diseño. Las mecánicas para implementar el refactoring se definen como el conjunto atómico de pasos para realizar la transformación.

>[!Important]
> Refactoring no agrega funcionalidad

# Code smells

Son indicadores de posibles problemas en el diseño del código fuente
- No son errores en sí, pero en un futuro podrían ser más difíciles y demandar más horas hombre, por ende, más costo

# Paradoja Velocity vs Technical Debt

>[!NOTE]
>**Velocity**: cantidad de tareas (funcionalidad) / periodo de tiempo

>[!NOTE]
>**Deuda Técnica**: costo de rehacer una tarea que tiempo atrás fue resuelta con una solución rápida y desprolija

# Pasos de un Refactoring

1. Verificar Precondiciones 
2. Transformaciones 
3. Compilar y Testear

# Por qué es importante el Refactoring?

La idea es ganar en la **comprensión del código**, **reducir el costo del mantenimiento** debido a los cambios inevitables que sufrirá el sistema (por ejemplo, código duplicado que haya que cambiar). Además, **facilita la detección de bugs**, y la clave es, **poder agregar funcionalidad más rápido después de refactorizar** 

# CLEAN Code

- ==C==ohesive (cohesivo)
- ==L==oosely coupled (débilmente acoplado)
- ==E==ncapsulated (encapsulado)
- ==A==ssertive (asertivo)
- ==N==on-redundant (no redundante)

Además, **Legible**

>[!Important]
>Entonces, en otros términos, el Refactoring consiste en tomar un código que "huele mal" producto de un mal diseño (código duplicado, ilegible, complicado) y se lo trabaja para obtener un buen diseño 


# Refactoring to patterns

Los patrones son tentadores para no quedarnos envueltos y arrastrar un mal diseño, pero también nos pueden llevar al otro extremo (**Over-engineering**). Por esto, es muy importante conocer las consecuencias tanto positivas como negativas de un patrón.

El refactoring nos permite introducir patrones recién cuando el software que construimos evoluciona al punto que son necesarios. No necesitamos adivinar o prever de antemano.

> [!quote] Fowler, 2000
> Patterns are where you want to be; refactorings are ways to get there from somewhere else


# Catálogo de Code Smells

>[!note]
>Los code smells o refactorings que se listen en este documento no son todos los que existen, sino los mencionados en la materia

#### 1. Bloaters (Hinchazones)
> *Código, métodos y clases que han aumentado a proporciones gigantescas.*

> [!fail]- Long Method (Método Largo)
> **Definición:** Un método que contiene demasiadas líneas de código.
> **Problemas:**
> - Difícil de entender, cambiar y reusar.
> - Las variables temporales suelen fomentar su crecimiento.
> **Soluciones:**
> - Extract Method
> - Decompose Conditional
> - Replace Temp with Query
> - Split Temporary Variable

> [!fail]- Large Class (Clase Grande)
> **Definición:** Una clase que intenta hacer demasiado (muchos métodos/variables).
> **Problemas:**
> - Baja cohesión.
> - Código duplicado.
> **Soluciones:**
> - Extract Class
> - Extract Subclass

> [!fail]- Long Parameter List (Lista de Parámetros Larga)
> **Definición:** Métodos con demasiados parámetros.
> **Problemas:**
> - Difícil de entender y de reusar.
> **Soluciones:**
> - Replace Parameter with Method
> - Preserve Whole Object
> - Introduce Parameter Object

---
#### 2. Object-Orientation Abusers
> *Casos donde la orientación a objetos no se aplica correctamente.*

> [!fail]- Switch Statements (Sentencias Switch)
> **Definición:** Uso complejo de `switch` o `if` secuenciales según el tipo de objeto.
> **Problemas:**
> - Falta de polimorfismo.
> - Código repetido en varios lugares.
> **Soluciones:**
> - Replace Conditional with Polymorphism
> - Replace Conditional Logic with Strategy
> - Replace State-Altering Conditionals with State

> [!fail]- Temporary Field (Campo Temporal)
> **Definición:** Variables que solo se usan en circunstancias específicas.
> **Problemas:**
> - Código difícil de entender (el objeto tiene "basura" temporal).
> **Soluciones:**
> - Replace Temp with Query
> - Split Temporary Variable

---
#### 3. Dispensables (Prescindibles)
> *Algo innecesario cuya ausencia haría el código más limpio.*

> [!fail]- Duplicate Code (Código Duplicado)
> **Definición:** Código idéntico o muy similar en varios lugares.
> **Problemas:**
> - Mantenimiento costoso.
> - Código innecesariamente largo.
> **Soluciones:**
> - Extract Method
> - Pull Up Method
> - Form Template Method

> [!fail]- Data Class (Clase de Datos)
> **Definición:** Clase que solo tiene atributos y getters/setters.
> **Problemas:**
> - Diseño procedural.
> - Sugiere que la lógica está en el lugar equivocado.
> **Soluciones:**
> - Move Method
> - Encapsulate Field / Collection

---
#### 4. Couplers (Acopladores)
> *Clases que están demasiado unidas a otras.*

> [!fail]- Feature Envy (Envidia de Atributo)
> **Definición:** Un método se interesa más por los datos de otra clase que por los de la suya.
> **Problemas:**
> - Alta dependencia y lógica ubicada incorrectamente.
> **Soluciones:**
> - Move Method

> [!fail]- Middle Man (Intermediario)
> **Definición:** Una clase que delega todo el trabajo a otra sin aportar valor.
> **Problemas:**
> - Complejidad innecesaria.
> **Soluciones:**
> - Remove Middle Man
> - Inline Method / Inline Class

# Catálogo de Refactorings

#### 1. Composición de Métodos
> *Simplificar el código dentro de los métodos.*

> [!success]- Extract Method
> **Problema:** Tienes un fragmento de código que se puede agrupar.
> **Solución:** Convertir el fragmento en un método cuyo nombre explique el propósito.

> [!success]- Inline Method
> **Problema:** El cuerpo de un método es tan claro como su nombre.
> **Solución:** Poner el cuerpo del método en el lugar de la llamada y borrar el método.

> [!success]- Replace Temp with Query
> **Problema:** Usas una variable temporal para guardar el resultado de una expresión.
> **Solución:** Extraer la expresión a un método y sustituir las referencias a la variable por la llamada al método.

> [!success]- Split Temporary Variable
> **Problema:** Tienes una variable temporal que se asigna más de una vez.
> **Solución:** Crear una variable temporal separada para cada asignación.

> [!success]- Replace Method with Method Object
> **Problema:** Un método largo usa variables locales de tal forma que no puedes extraerlas fácilmente.
> **Solución:** Transformar el método en su propia clase.

> [!success]- Substitute Algorithm
> **Problema:** Algoritmo complejo.
> **Solución:** Reemplazarlo por uno más claro.

---
#### 2. Mover Características
> *Poner la responsabilidad en la clase correcta.*

> [!success]- Move Method
> **Problema:** Un método es usado más por otra clase que por la propia.
> **Solución:** Crear un nuevo método en la clase que más lo usa y mover el código.

> [!success]- Move Field
> **Problema:** Un campo es usado más por otra clase.
> **Solución:** Mover el campo a la clase que lo usa.

> [!success]- Extract Class
> **Problema:** Una clase hace el trabajo de dos.
> **Solución:** Crear una nueva clase y mover los campos y métodos relevantes.

> [!success]- Inline Class
> **Problema:** Una clase no hace mucho.
> **Solución:** Mover sus características a otra clase y borrarla.

> [!success]- Hide Delegate
> **Problema:** El cliente llama al delegado de un objeto (cadena de llamadas).
> **Solución:** Crear métodos en el servidor para esconder al delegado.

> [!success]- Remove Middle Man
> **Problema:** Una clase hace demasiadas delegaciones simples.
> **Solución:** Hacer que el cliente llame al delegado directamente.

---
#### 3. Organización de Datos
> *Manejar mejor los objetos de datos.*

> [!success]- Self Encapsulate Field
> **Problema:** Accedes a un campo directamente, pero el acceso necesita lógica.
> **Solución:** Crear getters y setters y usarlos incluso dentro de la clase.

> [!success]- Encapsulate Field / Collection
> **Problema:** Tienes un campo público o una colección que devuelve el array crudo.
> **Solución:** Hacer el campo privado y proveer métodos de acceso.

> [!success]- Replace Data Value with Object
> **Problema:** Tienes un dato simple que necesita lógica.
> **Solución:** Convertir el dato en un objeto.

> [!success]- Replace Array with Object
> **Problema:** Usas un array para guardar elementos distintos.
> **Solución:** Reemplazar el array con un objeto que tenga campos para cada elemento.

> [!success]- Replace Magic Number with Symbolic Constant
> **Problema:** Tienes un número literal con significado especial.
> **Solución:** Crear una constante con un nombre significativo.

---
#### 4. Simplificación de Condicionales
> *Hacer la lógica condicional más fácil de entender.*

> [!success]- Decompose Conditional
> **Problema:** Tienes un condicional complejo (`if-then-else`).
> **Solución:** Extraer métodos para la condición, el `then` y el `else`.

> [!success]- Consolidate Conditional Expression
> **Problema:** Tienes varios condicionales con el mismo resultado.
> **Solución:** Combinarlos en una sola expresión y extraerla.

> [!success]- Consolidate Duplicate Conditional Fragments
> **Problema:** El mismo código se ejecuta en todas las ramas de un condicional.
> **Solución:** Sacar el código repetido fuera del condicional.

> [!success]- Replace Conditional with Polymorphism
> **Problema:** Tienes un condicional que elige comportamiento según el tipo de objeto.
> **Solución:** Mover cada rama del condicional a un método redefinido en una subclase.

---
#### 5. Simplificación de Llamadas
> *Hacer que las interfaces sean más claras.*

> [!success]- Rename Method
> **Problema:** El nombre del método no revela su propósito.
> **Solución:** Cambiar el nombre.

> [!success]- Preserve Whole Object
> **Problema:** Obtienes varios valores de un objeto y los pasas como parámetros.
> **Solución:** Pasar el objeto entero.

> [!success]- Introduce Parameter Object
> **Problema:** Tienes un grupo de parámetros que siempre van juntos.
> **Solución:** Reemplazarlos por un objeto.

> [!success]- Parameterize Method
> **Problema:** Varios métodos hacen algo similar pero con valores distintos.
> **Solución:** Crear un método que use un parámetro para los distintos valores.

---
#### 6. Manejo de Generalización
> *Mover funcionalidad en la jerarquía de herencia.*

> [!success]- Pull Up Field / Method
> **Problema:** Dos subclases tienen el mismo campo o método.
> **Solución:** Moverlo a la superclase.

> [!success]- Push Down Field / Method
> **Problema:** Comportamiento en la superclase es relevante solo para algunas subclases.
> **Solución:** Moverlo a esas subclases.

> [!success]- Pull Up Constructor Body
> **Problema:** Constructores en subclases con código idéntico.
> **Solución:** Crear un constructor en la superclase y llamarlo con `super()`.

> [!success]- Extract Subclass / Superclass
> **Problema:** Una clase tiene características de uso parcial o común con otra.
> **Solución:** Crear la herencia necesaria.

> [!success]- Collapse Hierarchy
> **Problema:** Superclase y subclase no son muy diferentes.
> **Solución:** Unirlas en una sola clase.

> [!success]- Replace Inheritance with Delegation
> **Problema:** Una subclase usa solo una parte de la superclase.
> **Solución:** Usar delegación en vez de herencia.

> [!success]- Replace Delegation with Inheritance
> **Problema:** Usas delegación para toda la interfaz de la clase delegada.
> **Solución:** Hacer que la clase herede de la delegada.

---
#### 7. Refactoring to Patterns
> *Introducir patrones de diseño complejos solo cuando son necesarios.*

> [!success]- Form Template Method
> **Problema:** Pasos similares en subclases pero con detalles distintos (Código duplicado).
> **Solución:** Generalizar el esqueleto del algoritmo en la superclase y diferir los pasos específicos a las subclases.

> [!success]- Replace Conditional Logic with Strategy
> **Problema:** Condicionales complejos controlan qué variante de un algoritmo ejecutar.
> **Solución:** Crear una clase Strategy para cada variante y delegar el cálculo.

> [!success]- Replace State-Altering Conditionals with State
> **Problema:** Transiciones de estado complejas manejadas con muchos condicionales.
> **Solución:** Reemplazar los condicionales con objetos State que manejen transiciones.

> [!success]- Move Embellishment to Decorator
> **Problema:** Necesidad de agregar nuevas responsabilidades a un objeto sin herencia compleja.
> **Solución:** Usar un Decorator para envolver la clase original.

> [!success]- Introduce Null Object
> **Problema:** Lógica condicional repetida para chequear `null` (Null Check).
> **Solución:** Crear un objeto que implemente la interfaz pero "no haga nada" para reemplazar los nulos.

# Relaciones de smells y refactorings

```mermaid
	graph LR
	    %% --- ESTILOS ---
	    classDef smell fill:#ffcccc,stroke:#cc0000,stroke-width:2px,color:black,font weight:bold;
	    classDef refactor fill:#ccffcc,stroke:#00cc00,stroke-width:2px,color:black;
	
	    %% --- NODOS: BAD SMELLS ---
	    S1(Long Method)
	    S2(Large Class)
	    S3(Long Parameter List)
	    S4(Switch Statements)
	    S5(Temporary Field)
	    S6(Duplicate Code)
	    S7(Data Class)
	    S8(Feature Envy)
	    S9(Middle Man)
	
	    %% --- NODOS: REFACTORINGS ---
	    R1[Extract Method]
	    R2[Decompose Conditional]
	    R3[Replace Temp w/ Query]
	    R4[Extract Class / Subclass]
	    R5[Introduce Parameter Object]
	    R6[Preserve Whole Object]
	    R7[Replace Cond w/ Polymorphism]
	    R8[Split Temp Variable]
	    R9[Pull Up Method]
	    R10[Move Method]
	    R11[Encapsulate Field]
	    R12[Remove Middle Man]
	    R13[Inline Method / Class]
	    
	    %% --- NUEVOS REFACTORINGS TO PATTERNS ---
	    R14[Form Template Method]
	    R15[Replace w/ Strategy]
	    R16[Replace w/ State]
	    R17[Introduce Null Object]
	
	    %% --- CONEXIONES ---
	    %% Long Method
	    S1 --> R1
	    S1 --> R2
	    S1 --> R3
	
	    %% Large Class
	    S2 --> R4
	
	    %% Long Parameter List
	    S3 --> R5
	    S3 --> R6
	
	    %% Switch Statements
	    S4 --> R7
	    S4 --> R15
	    S4 --> R16
	
	    %% Temporary Field (y Null Checks)
	    S5 --> R3
	    S5 --> R8
	    S5 --> R17
	
	    %% Duplicate Code
	    S6 --> R1
	    S6 --> R9
	    S6 --> R14
	
	    %% Data Class
	    S7 --> R10
	    S7 --> R11
	
	    %% Feature Envy
	    S8 --> R10
	
	    %% Middle Man
	    S9 --> R12
	    S9 --> R13
	
	    %% --- APLICAR ESTILOS ---
	    class S1,S2,S3,S4,S5,S6,S7,S8,S9 smell;
	    class R1,R2,R3,R4,R5,R6,R7,R8,R9,R10,R11,R12,R13,R14,R15,R16,R17 refactor;