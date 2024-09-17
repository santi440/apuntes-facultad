### Características principales
Cumple con las siguientes características o un balance entre estas:
- Expresividad, es decir, capturar y presentar de la mejor forma posible la semántica de los datos del problema a resolver.
- Formalidad, que requiere que cada elemento representado en el modelo sea preciso y bien definido, con una sola interpretación posible. 
- Minimalidad, cada elemento del modelo conceptual tiene una única forma de representación posible y no puede expresarse mediante otros conceptos.
- Simplicidad, que establece que el modelo debe ser fácil de entender por el cliente/usuario y el desarrollador.
- Fácil de leer: un modelo que cumpla con lo anterior, debería, en consecuencia ,cumplir esta condición. 
Algunas son opuestas, por lo que el modelo no sera las 4 características en forma superlativa (Si es muy simple, no es muy expresivo)

### Elementos
Aplica las reglas de [[Modelado]] un formato de ER 
#### Entidad
Una entidad representa un elemento u objeto del mundo real con identidad, es decir, se diferencia unívocamente de cualquier otro objeto o cosa, incluso siendo del mismo tipo.
Una vez reconocidas las entidades, se buscan las propiedades comunes de estas, y genera una representación que aglutina a esas entidades. Esta representación se denomina conjunto de entidades.

#### Relaciones
Las relaciones representan agregaciones entre dos (binaria) o más entidades. Describen las dependencias o asociaciones entre dichas entidades. Son agregaciones y por ende tienen cardinalidad (= grado de relación existente).

???conjunto de relaciones: a partir de las características propias de cada relación existente entre dos entidades, las resume en un núcleo.

Se denomina relación recursiva a aquella relación que une dos entidades particulares del mismo conjunto. Su cardinalidad a su vez debe ser acompañada por un nombre o rotulo para evitar el problema del orden en que debe ser leída.

En relaciones ternarias se ve afectada la expresividad de la cardinalidad, al solo tener dos campos (min y max), la manera de representar sin errores es con la manera mas abarcativa.

#### Atributos



