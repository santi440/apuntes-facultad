# Clase 1

## Bases de datos relacionales
- Utilizadas por su sencillez
- Generalmente el paradigma utilizado para representar los datos es "orientado a objetos"

## Tipos de bases de datos
- Relacionales
- No relacionales
- Orientada a objetos

## Diferencia de impedancia
Diferencias que hay entre los distintos paradigmas  o modelos de bases de datos

## Transacciones
Es más un problema de distribución que de persistencia

## ORM
Separan la persistencia de la lógica del objeto

## Principios de los ORM
- Ortogonalidad
	- Toda clase debe poder ser persistente
- Independencia
	- La capacidad del modelo de objetos de ignorar los detalles de la solución de persistencia
- Persistencia por alcance
	- Todo objeto al cual se pueda llegar a partir de un objeto ya persistente, debe ser necesariamente persistente
## Hibernate
### HQL
HIbernate tiene su propio lenguaje de consulta debido a que así se agrega abstracción y permite tanto migrar como mantener distintos tipos de bases de datos

### Reglas POJO (Plain Old Java Object)
- Implementar un constructor sin argumentos -> **OBLIGATORIO**
	- Hibernate necesita hacer "hidratación", es decir, instanciar clases vacías para luego popular sus datos
- Proveer un identificador
- Clases no-finales
	- Hibernate usa muchos proxies y por esto requiere extender la clase, cosa que no sería posible con clases finales
- Setters y getters para los campos persistentes

# Clase 2
## Hibernate y Java Persistence API (JPA)
JPA es un conjunto de especificaciones, tiene una API que se utiliza para manejar la persistencia de objetos dentro de java

## Mapeo con JPA
A menos que un objeto se marque como `@Transient`, JPA va a persistir todo lo que se contenga en una clase persistente, marcada como `@Entity`.

### Jerarquías
Tiene 3 estrategias para pasarse al modelo relacional
1. Una sola tabla (default)
	- Se agregar una columna para identificar de qué clase es esa fila
		![[Pasted image 20260324142316.png]]
		![[Pasted image 20260324142434.png]]
2. Joined
	- Las columnas están distribuidas entre una tabla padre y tablas para hijos
		![[Pasted image 20260324142504.png]]
		![[Pasted image 20260324142552.png]]
		- Una desventaja es que para reconstruir un objeto, se deben traer los atributos de la tabla padre y la tabla del hijo
		- La ventaja es que se mantiene todo más normalizado
		
3. Una tabla por clase concreta
	- Si se trata por separado a las distintas clases, en lugar de querer aprovechar el polimorfismo, es útil
	![[Pasted image 20260324142703.png]]
	![[Pasted image 20260324142732.png]]

### Operaciones en cascada
Distintos tipos de operación en cascada
1. Persist
	1. Cuando persisto un objeto, el objeto relacionado se va a persistir también
2. Remove
	1. Cuando elimino un objeto, el objeto relacionado también se va a borrar
3. Detach
	1. Cuando un objeto se desconecta del contexto de persistencia, todos sus objetos relacionados también son desconectados
4. Refresh
	1.  Cuando un objeto es refrescado, sus objetos relacionados también son refrescados
5. Merge

#### Tipo de fetch
Dos tipos
- Traer sólo el que me piden (lazy)
- Traer todo, junto al relacionado (eager)

### Relaciones
#### Many to Many
![[Pasted image 20260324143121.png]]
#### Many to One
![[Pasted image 20260324143128.png]]
#### One to Many
Por default, se tiene una tabla intermedia
![[Pasted image 20260324143232.png]]

# Clase 3
## Patrones de persistencia
### DAO - Data Access Object
Por cada clase, se tiene un DAO que sabe cómo se guarda y se recupera ese objeto. El DAO está acoplado a la clase.
El problema es que si tengo clases relacionadas hay que poder determinar cómo se guarda o actualiza toda esa información relacionada. Hay que ver cómo cada DAO se llama uno a otro para poder persistirse o recuperarse esa información relacionada.
Cuando se utilizan DAOs, la clase queda anémica, debido a que el DAO es quien va a tener los métodos de lógica de negocio

### Repository
Se separa la base de datos del modelo
Es una colección de objetos persistentes

## OQL - Object Query Language
Se habla de clases y atributos en lugar de tablas y atributos

## Lenguajes de consulta
### En Hibernate
- SQL Normal
`Select p.* FROM Persona p WHERE nombre LIKE '%Matias%'`
No es buena idea hacer `selecet p.*` debido a que si se actualiza la base de datos y no tengo todos los atributos mapeados, va a fallar. Tampoco es recomendable si es necesario traer un único tipo de dato y de esa forma se traen todos
Este tipo de consulta es más eficiente, pero es más propenso a errores
- HQL
`FROM Persona p where p.nombre LIKE '%Matias%' `
Hay más nivel de abstracción
Hay un overhead por transformar la consulta a una consulta SQL, pero es más seguro
- Criteria
```java
session
	.createCriteria(Persona.class)
	.add(Expression.like("nombre", "%Matias%"))

```
Más nivel de abstracción

## HQL
### Tipos de retorno
`Select reserva, cliente from Reserva reserva where...`
Se trae un arreglo de objetos

`Select new Cliente(nombre, fecha) from Cliente c where ...`
Se crea un objeto a partir de la consulta

### Subconsultas
Igual que en sql
`from Cat as cat where not exists (from Cat as mate where mate.mate = cat)`
**Es más eficiente el EXISTS que un IN**
### Funciones de agregación
Practicamente los mismos de sql

## Proxies
Es un objeto que imita a objeto real persistido pero que alivia la carga de memoria
También alivia las cargas sobre la base ahorrando queries

## Caching
Por default tiene una caché de nivel 1
Está ligada a la sesión, cada sesión tiene su propia caché
Utiliza el ID de los objetos

### Caching nivel 1
Cuando se recupera un objeto, se almacena en la caché y los accesos posteriores se acceden desde la caché

### Caching nivel 2
La caché de nivel 2 es un hotspot (podés implementarla a mano), se implementa a nivel de SessionFactory y cubre a todas las sesiones a la vez
Permite cachear resultados de consultas HQL

# Clase 4
## Spring
Framework de propósito general para desarrollar aplicaciones en java
## Spring Data
Spring Data es un framework que se encarga de implementar los repositorios
Framework de Spring que proporciona abstracción sobre diferentes tecnologías de persistencia de datos
Simplifica el desarrollo de aplicaciones
No es un framework únicamente para bases de datos relacionales (Hibernate es un framework para bases de datos relacionales), con lo cual se tiene una implementación de Spring data para el tipo de base de datos que vayamos a utilizar

### Características de Spring Data
- Soporte de configuración de mapeo mediante anotaciones de JPA
- Soporte para el manejo de transacciones en la base de datos
- Soporte para la implementación del patrón **repository** a través de **Spring Data Repositories**
- Soporte para realizar consultas JPQL, HQL, Criteria y SQL

## Repositories
La principal característica de Spring Data en general es la capacidad de generar repositorios
El patrón repository es un patrón de diseño que actúa como una capa intermedia entre la lógica de negocio y la capa de persistencia
A diferencia del patrón DAO, no representa la capa de persistencia, sino la colección de objetos persistentes

### Spring Data Repositories
Componente de Spring Data que permite crear repositorios con métodos de consulta sin necesidad de escribir su implementación
Se basa en el patrón repository
Aprovecha la capacidad de Spring de usar **reflexión**
Proporciona una forma automatizada de implementar las operaciones CRUD (Se centra en estas operaciones)

#### Implementación
En lugar de escribir código repetitivo, estos permiten describir los métodos de acceso a datos y este, mediante reflexión, genera su correspondiente implementación
En tiempo de ejecución, el framework detecta estos repositories y genera un **proxy dinámico**
Este proxy, intercepta las llamadas al repositorio y genera las consultas correspondientes
En base al nombre del método, Spring detecta qué consulta implementar

### Ventajas
- Facilita el acceso a los datos al proporcionar una nueva capa de abstracción
- Nos permite la creación de métodos de consulta sin la necesidad de escribir la consulta directamente
- Se basa en que la mayoría de las consultas son sencillas y siguen una estructura similar

### Interfaces
Sólo se debe extender una interfaz e indicar la clase del repositorio y el tipo de clave primaria
- CrudRepository<type, id>
- PaggingAndSortingRepository<type, id>
- JpaRepository<type, id>

La diferencia en las interfaces depende del tipo de repositorios, por ejemplo, repositorios más pensados para hacer crud, o para hacer ordenado y obtención de datos, etc.

Algunos métodos por defecto
- save
- findAll
- findById
- delete

## Query Methods
Los repositorios de Spring Data permiten describir los métodos de acceso a los datos a través de métodos abstractos en estas interfaces
Utilizando reflexión, implementa automáticamente estos métodos
Se debe seguir una convención

## Anotación @Query
Si los query methods no son suficientes tenemos alternativas para implementar estas consultas
Por ejemplo, los Query Methods no tienen group by
La anotación @Query me permite definir la consulta a realizar en el lenguaje JPQL
Si aun no puedo realizar la consulta con JPQL, o no lo prefiero, la anotación @Query permite SQL nativo
Si aun no es suficiente eso o quiero un mayor control sobre la consulta, se pueden realizar implementaciones de las interfaces para implementar los métodos (Custom Repositories)
Para hacer esto, se realizan a través de una interfaz paralela que luego será padre de mi repositorio
Pero esto es necesario en casos muy específicos

# Clase 5
## Limitaciones de las bd relacionales
El estándar más importante de las bd relacionales es SQL
## Pros y contras de las bd relacionales
### Pros
- Estandarizadas por sql
- Acceso a los datos concurrente
- Transacciones que proveen consistencia y concurrencia
- La estructura es fija
### Contras
- Diferencia de impedancia
- No comparten el mismo paradigma que el lenguaje de programación que utilizamos
- No tienen el concepto de agregación

### Problema de la integración
Si una base de datos relacional es compartida y la consumen dos o más sistemas y un sistema necesita modificar la estructura de la base de datos relacional, se rompen los demás sistemas y se debe ver los cambios con los demás sistemas
Con lo cual, se terminan generando varias bases de datos por cada sistema y se comunican entre sistemas por API Rest o algún service para compartir los datos. Esto también nos provoca que cada sistema pueda tener una base de datos distinta, no necesariamente relacional

### Problema de volumen de datos
Conviene tener varias bases de datos donde cada una almacena una porción de bases de datos (Cluster de bases de datos)

## Bases de datos NoSQL
Son bases de datos no relacionales
Son bases de datos Clave-Valor
Redis: Base de datos que la clave funciona con acceso directo por hashing, donde el acceso a los datos es rápido

### Agregaciones
La forma en que se consultan los datos es lo que me va a dar el límite de las agregaciones. Por ejemplo, si siempre quiero acceder a las reservas a través de un cliente (con lo que se agregan las reservas al cliente) o si quiero acceder a cada reserva de forma individual
Al tener clusters, la información puede que se necesite propagar, por lo que no hay tanta consistencia. Pero se busca que la información esté siempre disponible y de forma rápida

#### Transacciones
Si tengo varios documentos relacionados, mongo me permite guardar documentos pero no la agregación
Las transacciones son distintas a las del relacional

## Familias NoSQL
### Clave-Valor
Por ejemplo, redis
Son rápidas para la recuperación
Es una tabla de hash donde una clave recupera un valor
Es raro que haya UPDATES, usualmente se inserta información y se recupera
### Documento
Por ejemplo, mongodb
Permite tener el concepto de agregación
La estructura es libre, el esquema no es fijo
Se almacena por columna y la agrupación se hace por familia de columnas (conjunto de columnas)
Cassandra a este agrupamiento de columnas lo llama supercolumnas

## Manejo de relaciones
No hay mucho mecanismo para relacionar datos.
Las transacciones son de un documento pero no de todos juntos
Mongo permite definir algunas referencias básicas

Hay bases de datos orientadas a grafos que permiten representar relaciones entre los distintos datos del modelo. Se tienen nodos y aristas
Es muy performante para recuperar datos pero no para inserts, porque al insertar un nodo hay que insertar también todas las relaciones de ese nodo
Hay clusters pero es más monolítico

## Algoritmos de búsqueda para BDs orientadas a grafos
- Pathfinding and search
- Centrality
- Community Detection
- Heuristic link prediction
- Similarity
- Embeddings

## MongoDB
Orientada a documentos basada en hashing
No maneja la noción de esquemas pero sí la de colecciones
No hay un lenguaje de definición de datos (DDL)
Utiliza BSON (Binary JSON) que es mucho más óptimo
Mongo suele guardar espacio extra para considerar que se van a agregar datos para poder almacenarlos sin necesidad de mover el documento
El tamaño máximo de un documento es 16mb

Si se tiene que relacionar con pocos datos
El padre agrega a los hijos
Si se tiene que relacionar a más datos
Se agregan las referencias 
Si se tiene que relacionar una gran cantidad de datos
Entonces los hijos referencian al padre en lugar de ser al revés

### Indices
Se dividen a nivel de colecciones
En background se pueden crear los índices sin limitar el acceso a la db, se pueden indexar cosas dentro de subcampos
El campo ID está indexado por defecto

### Aggregation Framework
Permite crear consultas complejas ejecutadas una tras otra en distintas etapas
Se pueden ejecutar varias veces para llegar al resultado, por lo que hay que evitar que los datos cambien mientras se hacen esas consultas

# Clase 6
La normalización afecta la velocidad de consulta
La eficiencia del modelo relacional es en el poco espacio que ocupa

La base de datos NoSQL mejora la velocidad de consulta
## Tipos de BD NoSQL
- Por columna
- Grafos
- Clave-Valor
- Documentos
## Escalabilidad MongoDB
Se busca el escalado horizontal, o sea, dividir los datos en distintos servidores para que cada uno contenga una parte del total de los datos
También se hace replicación de los datos, donde además en cada parte de los datos tengo replicación para poder tener más disponibilidad de los datos

## Replica Sets
Hay un nodo primario que se encarga de decidir sobre las escrituras y después nodos secundarios que pueden resolver consultas y si se cae el nodo primario pueden actuar como primarios
Cada replica set es un shard
## Sharding
Representaría a un servidor
Para resolver el problema de la distribución y que sea equitativo en los distintos servidores
Shard Key: Valor que te dice a qué shard va a caer un dato

## Chunks
Es una unidad que cubre un espacio de valores, por lo que se puede mover chunks enteros que agrupan datos
Permiten que el balance sea mejor, al no tener que pasar de un documento a la vez
Un shard puede tener muchos chunks

# Clase 7
Teorema CAP
- Consistencia (Consistency)
	- Todos los nodos ven los mismos datos al mismo tiempo
	- Una lectura devuelve la escritura más reciente
- Disponibilidad (Availability)
	- Cada solicitud recibe una respuesta, sin errores, garantizando que el sistema sigue operativo
- Tolerancia a particiones (Partition Tolerance)
	- El sistema sigue funcionando a pesar de que la red pierda mensajes o falle la comunicación entre nodos

# Clase 8
Teorema CAP
De las 3 propiedades sólo se pueden tener 2, en detrimento de la 3ra

## Quorum
Uno puede establecer cuántos nodos de un replica set necesitan confirmar para una escritura para considerarlo consistente
Y la relación con eso para los nodos que leen para saber que se tiene el dato correcto
Si tengo 5 nodos, les digo por ejemplo, a los 5 que me confirmen la escritura para poder leer y entonces con uno solo que lea ya va a poder leer de forma consistente. Si hay un solo nodo que confirma lectura, entonces para leer hay que leer los 5 nodos porque puede que no esté confirmada la escritura y haya una inconsistencia si no se leen todos
Por lo general los de escritura se utiliza la mitad de nodos 
Y la lectura es que la cantidad de nodos que confirman lectura más los que confirman escritura debe ser mayor a la cantidad total de nodos
## Replicación - Rendimiento
Los replica set, además del uso de que se puede tener disponibilidad aunque se caiga un nodo (porque otro nodo se levanta) hay otro uso que es distribuir la carga
También esto nos permite ubicar los nodos geográficamente en ubicaciones donde las lecturas son más frecuentes. Aunque caídas de nodos y la replicación de los datos puede causar más latencia por la distancia geográfica
## Quorum en MongoDB
Tipos para lectura
- local (default)
	- Lee lo que tenga el nodo local sin esperar replicacion ni consenso
	- Ejemplo: Lecturas rapidas donde se tolera ver datos ligeramente viejos o no confirmados. Logs, metricas, dashboards internos
- available (sharding)
	- Devuelve lo que este disponible, incluso si falta un shard o hay inconsistencia entre shards
	- Ejemplo: ECommerce global, catalogos grandes, vistas best effort
- majority
	- Solo ve datos que alcanzaron a la mayoria de los nodos
- snapshot
	- Foto consistente de los datos dentro de una transaccion

Tipos para escritura
- {w: 0}
- {w: 1}
- {w: N}
- {w: majority}
- ME FALTO UNO

## Bases de datos de familia de columnas
### Row Storage vs Column Storage
- Row Storage
	- Se guarda por filas, es el guardado tradicional
	- Hay un identificador de columna, por fuera del identificador autoincremental utilizado para la tupla
	- En las DB NoSql, lo que hay en disco son bloques de tamaño fijo
		- Estos bloques afectan según las consultas
		- Si quiero traer todos los datos, traer los bloques es eficiente
		- Si se busca, por ejemplo, usuarios de una edad X y quiero obtener solo la edad, el traer estos bloques no es tan eficiente dado que los bloques contienen toda la info
		- Estos errores son los que llevarían al almacenamiento por columna
- Column Storage
	- Se puede almacenar fisicamente por columna, de forma que, una "fila" con sus atributos, tendrá almacenado cada uno de sus atributos en una columna
	- También hay un identificador de fila, pero en disco se tiene cada atributo separado fisicamente y agrupados por atributo
	- Para traer todos los datos de, por ejemplo, un usuario en particular, hay que hacer varios accesos
	- Pero la ventaja es que si quiero hacer un promedio de edades u obtener información específica en su totalidad, es mucho más eficiente

## Cassandra
- No tiene almacenamiento columnar
- Propone un diseño "query first approach" donde el diseño de la db se hace a partir de las queries que se quieran hacer (se debe saber qué queries se van a tener)
- Utiliza almacenamiento por fila, pero el tipo de partición depende de lo que se defina en el modelo
- Los datos se modelan alrededor de las consultas y entonces se optimizan a partir de las consultas
- Las consultas se diseñan para que accedan a una sola tabla
- Hay redundancia
- Las lecturas son muy rápidas
- Las escrituras parecen ser también rápidas
- No hay JOIN
- Se utilizan las claves de partición para saber cómo están distribuidos los datos
- Las claves primarias contienen la clave de partición. Aunque si no hay nada que ordenar, pueden coincidir, es decir, la clave primaria puede ser tambien la clave de particion
- La clave de partición se puede repetir y permite dividir los datos en clusters
- Clustering Keys: Dicen cómo se ordenan fisicamente los datos
### Arquitectura
- Una base de datos en Cassandra se denomina Keyspace
- Las bases de datos van a tener particiones
- Las claves de particiones deben evitar el crecimiento monotónico pero también que no tenga tantas particiones. O sea, se evita que no crezcan ilimitadamente los datos en un mismo nodo ni que se divida todo constantemente
### Distribución
- La clave determina donde se guarda físicamente el dato
- Todos los nodos se comunican entre sí
- Rara vez se necesitará más de un nodo en una query
### Particiones y distribución
- Todas las partition keys se tokenizan por hashing
- Cada nodo guarda 3 espacios de valores, el suyo, el del vecino, y el del vecino del vecino
- Es p2p
- También tiene un sistema de quorums
### Casos de uso
- Patrones de lectura predecibles
- Logging
- Mucha demanda de escritura

## Trivia
- Relacional o grafos para datosm uy relaciodos
- Clave valor para sesiones de usuario
- Cassandra pero se suele usar mongo 
- Relacional
- Para perfiles de usuario con familias de columnas, puede ser redis
- Para un CMS relacional
- Clave valor
- Datos con expiración clave valor
- Logs, Cassandra

## Criterios para elegir una db
- Popularidad (lamentablemente)
- Rendimiento para el usuario
- Novedad (tampoco es recomendable)
- Necesidades del sistema o de propiedades

# Clase 10
## Fuentes de datos que existen en una organización
Fuentes de datos internas
- Documentación física
- Planillas
- Bases de datos
Fuentes de datos externas
- Redes sociales
- Sensores/IOT
- Analíticas
Hay fuentes de datos internas y externas

## Data Warehouse
Según Inmon
Conjunto de datos orientados a temas, integrados, variantes en el tiempo y no volátiles, que tienen por objetivo dar soporte a la toma de decisiones

Segun Kimball
Es una copia de los datos transaccionales específicamente estructurada para la consulta y el análisis

No es un tipo de base de datos 
Se crea una base de datos de tipo OLAP, que son bases de datos analíticas, en lugar de algo transaccional nos permite una forma analítica
### Definición
Es un sistema que se utiliza para almacenar en un único repositorio grandes volúmenes de datos que provienen de múltiples fuentes de la organización. A su vez, se encuentra altamente estructurado, proporcionando una visión global, común e integrada de los datos, independientemente de cómo se vayan a utilizar posteriormente los mismos
Está diseñado específicamente para el análisis y la generación de informes, no para el procesamiento de transacciones del día a día

### Características
- Integración
- Consistencia
- Variable en el tiempo
	- Permite por ejemplo hacer un análisis de tendencias, importa cómo cambian los valores a lo largo del tiempo
- No volátil
- Organización temática

### Funciones y uso
- Business intelligence
- Informes y/o reportes gerenciales
- Decisiones basadas en datos
- Análisis de tendencias y comportamientos
- Data mining
- Machine Learning

### Componentes
- Data sources
- Herramientas de ETL
- Metadata
- Bases de datos
- Herramientas de consulta y análisis

### ETL
- Extracción
- Transformación 
- Carga (Load)

### Arquitectura
1. Fuentes de datos
2. Area de staging
	- Se recomienda almacenar una copia de los datos sin procesar para poder reprocesarlos ante cualquier necesidad
3. Warehouse
4. Data Marts
	- Particionar la información
5. Usuarios finales

### Tablas
Hay dos tipos
- Tablas de HECHOS (medidas)
	- Son el objeto del análisis, lo que se quiere estudiar
	- Son objetos relacionados con las dimensiones
	- Son tablas generalmente muy grandes y suelen estar desnormalizadas
	- a menudo incluyen diferentes agregaciones como máximo, mínimo, media, etc
	- Almacena datos cuantitativos o numéricos sobre un proceso de negocio (ventas, compras, incidencias, etc.) para su análisis
	- Suele tener dos tipos de columnas
		- Claves foráneas para tablas de dimensiones y medidas, que contienen datos numéricos
		- ==TODO==
- Tablas de DIMENSIONES
	- Representan factores por los que se analiza una determinada área de negocio, describiendo y dando contexto a los datos numéricos
	- Son pequeñas y usualmente están desnormalizadas
	- ==TODO==

### Modelos
- Estrella
	- Una tabla de hechos y varias tablas de dimensión que describen esos hechos
	- Es el más simple de todos los modelos, es fácil de integrar
	- Tiene muy buen rendimiento en las consultas
	- La escalabilidad puede ser peor ya que existe un uso más ineficiente del almacenamiento
	- El tamaño de la tabla puede ser grande y el tiempo de carga de datos en tablas de dimensiones puede ser relativamente largo
- Copo de nieve
	- Evolución del modelo estrella
	- Las dimensiones pueden conocer otras dimensiones
	- Hay una tabla de hechos
	- ==Rellenar de pros y contras==
	- El código es mucho más complejo ya que hay dos niveles de join a la hora de trabajar con una dimensión
- Galaxia o constelación
	- Hay más de una tabla de hechos
	- Las dimensiones pueden ser compartidas entre las tablas de hechos
	- Cuando una dimensión es compartida, se le dice "Conformada"

### Data Mart
- Subconjunto de datos orientado a un área específica con el objetivo de responder a determinados análisis o necesidades de dicha area

## Data Lake
- Repositorio orientado a almacenar gran cantidad de datos, tanto estructurados como sin estructurar
- Permite almacenar los datos tal cual vienen sin necesidad de ser tansformados
- Ejecuta diferentes tipos de análisis, dashboards y visualizaciones, ademas de generar procesos de big data, análisis en tiempo real y Machine Learning que facilitan la toma de decisiones
## Bases de datos vs Data Warehouse
==TODO: Sacarle fotito a la diapo cuando les pinte publicarla==

## Sistema de Información Geográfica (SIG)
- Sistema de información compuesto por hardware, software, procedimientos, datos y equipo humano
- Permiten capturar, almacenar, analizar y visualizar datos que tienen una ubicación en el espacio
- Modelos
	- Vectorial
		- Espagueti
		- Topológico
	- Raster

### Modelo vectorial
- Se representa el espacio mediante geometrías definidas por coordenadas
- Se tienen
	- Puntos: Ubicaciones especificas (pozos, ciudades, etc)
	- Lineas: Elemmentos lineales (rutas, ríos, etc)
	- Polígonos: Areas cerradas (provincias, lagos)
- Es ideal para representar objetos discretos y bien definidos

### Modelo raster
- Representa el espacio como una matriz de celdas
- Cada celda tiene un valor (ej, altitud, temperatura, etc.)
- Muy usado en imágenes satelitales y análisis ambiental
- Es mejor para fenómenos continuos