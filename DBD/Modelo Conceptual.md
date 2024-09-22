### Características principales
Cumple con las siguientes características o un balance entre estas:
- Expresividad, es decir, capturar y presentar de la mejor forma posible la semántica de los datos del problema a resolver.
- Formalidad, que requiere que cada elemento representado en el modelo sea preciso y bien definido, con una sola interpretación posible. 
- Minimalidad, cada elemento del modelo conceptual tiene una única forma de representación posible y no puede expresarse mediante otros conceptos.
- Simplicidad, que establece que el modelo debe ser fácil de entender por el cliente/usuario y el desarrollador.
- Fácil de leer: un modelo que cumpla con lo anterior, debería, en consecuencia ,cumplir esta condición. 
Algunas son opuestas, por lo que el modelo no sera las 4 características en forma superlativa (Si es muy simple, no es muy expresivo)

### Elementos
Aplica las reglas de [[Modelado]] un formato de ER . Entidad, Relación y atributo usan abstracción de clasificación. Entidades, relaciones y atributos pueden contener abstraccion de agregacion. La abstraccion de generalizacion esta presente en Entidades y, menos frecuente, en relaciones.
#### Entidad
Una entidad representa un elemento u objeto del mundo real con identidad, es decir, se diferencia unívocamente de cualquier otro objeto o cosa, incluso siendo del mismo tipo.
Una vez reconocidas las entidades, se buscan las propiedades comunes de estas, y genera una representación que aglutina a esas entidades. Esta representación se denomina conjunto de entidades.

#### Relaciones
Las relaciones representan agregaciones entre dos (binaria) o más entidades. Describen las dependencias o asociaciones entre dichas entidades. Son agregaciones y por ende tienen cardinalidad (= grado de relación existente).

???conjunto de relaciones: a partir de las características propias de cada relación existente entre dos entidades, las resume en un núcleo.

Se denomina relación recursiva a aquella relación que une dos entidades particulares del mismo conjunto. Su cardinalidad a su vez debe ser acompañada por un nombre o rotulo para evitar el problema del orden en que debe ser leída.

En relaciones ternarias se ve afectada la expresividad de la cardinalidad, al solo tener dos campos (min y max), la manera de representar sin errores es con la manera mas abarcativa.

#### Atributos
Propiedad basica de una entidad o relacion.  Cada atributo debe tener definido un dominio y cardinalidad. El dominio
de un atributo define el conjunto de valores posibles que el atribu-
to puede tomar, asociado al de tipo de datos.
##### Cardinalidad: valores posibles de un atributo
- 1..1 : Monovalente obligatorio: Se la considera implicita y no es necesario incluirla. El atributo debe estar y tiene un y solo un valor.
- 0..1: Monovalente no obligatorio: Es opcional que este pero si lo esta tiene un solo valor.
- 1.. n : Polivalente obligatiorio, podrían existir múltiples valores para
este atributo.
- 0..n : Polivalente no obligatorio:

### Extras
Las jerarquías, subconjuntos, atributos compuestos e identificadores aumentan la expresividad del modelo conceptual.

#### Jerarquias
La generalización permite extraer propiedades comunes de varias entidades o relaciones, y generar con ellas una superentidad que las aglutine. Así, las características compartidas son expresadas una única vez en el modelo (evitando redundancia de datos), y los rasgos específicos de cada entidad quedan definidos en su subentidad. Utiliza la cobertura descripta en el [[Modelo Conceptual]]
#### Subconjunto
Caso especial de la jerarquia, se tiene una generalización de la que se desprende solamente una especialización. Su cobertura siempre es P,E (no puede ser Superpuesta porque no hay otras entidades hermanas y no puede ser Total porque existen elementos del padre que no esta en el unico hijo, si fuese total no existiria tal generalizacion)

#### Atributos Simples
Rrepresentan a un atributo generado a partir de la combinación de varios atributos simples, que me interesa englobar en un concepto pero distinguir sus componentes. Un atributo compuesto podría ser polivalente o no obligatorio. Lo mismo podría ocurrir con los atributos simples que lo componen.

#### Identificador 
Es un atributo o un conjunto de atributos que permiten distinguir o reconocer de manera univoca (claves primarias / candidatas) una entidad dentro del conjunto de entidades. Debe de ser minimo, para identificarlo se necesitan todos los atributos que lo componen .Si  quitarle uno de sus componentes y sigue identificando, esta mal contituido 
- Simple: Si un solo atributo simple lo compone.
- Compuesto: Se necesitan 2 o mas
- Interno: Todos los atributos que lo componen son propios a la entidad.
- Externo: Requiere de otra entidad para poder identificarse

#### Al finalizar el modelo debe ser
Autoexplicativo,Completito,Correcto, expresivo, extensible, legible y mínimo (una idea se expresa en él una sola vez, atributos derivados y ciclos).
La redundancia de información puede ser un efecto positivo si es conocida y controlada, o un efecto negativo si aparece en el esquema por deficiencia en la modelización.
- Atributo derivado es un atributo que aparece en el modelo, pero cuya información, si no existiera almacenada en él, podría igualmente ser obtenida.
- Ciclo: Un ciclo afecta la minimalidad cuando una de las relaciones existentes puede ser quitada del esquema, y aun en esas condiciones, el modelo sigue representando la misma información. A relacionada con B, relacionada con C, que se relaciona con A