La parte más generica en la que siempre trabajamos igual de inyectar el SessionFactory y ejecutar la consulta podria generalizarse -> solo cambia los repositories y la config de la db y permite trabajar con las mismas tecnologias

## Spring Data 
es un modulo de spring. crea una abstraccion sobre diferentes tecnologias de persisstencia de datos, incluido el ORM hibernate
Ademas tiene diferentes implementaciones para dbs. Con esta cpaa intermedia podemos abstraernos hasta de si es Relacional o no Relacional (Spring Data JPA != Spring Data Mongo) 

- Soporta la configuracion de mapeo mediante JPA
- Soporte para Transacciones
- Soporte para la implementacion del patron Repository = SpringData Repositories
- Soporte para consultas: SQL, JPQL, JQL y Criteria
![[Pasted image 20260409092034.png]]
reflection: La capacidad de leer el propio codigo que escriba y el propio framework escribe el codigo faltante. 
> [!quote] Reflection in the context of the Spring Framework refers to the use of the Java Reflection API to inspect, instantiate, and manipulate objects at runtime. It is the fundamental mechanism that allows Spring to provide its "magic" features without requiring developers to write boilerplate code.

Funciona como un proxy dinamico, intercepta todas las consultas a esa interfaz, implementa la consulta y la ejecuta.
repositorios no son implementaciones reales sino solo las cabeceras o interfaz que describe cuales son los posibles metodos o consultas que debe implementar 

- Facilita el acceso a datos
- Creamos repositorios sin escribir la implemencion real 
- se basa en lo repetitivo de las consultas, no sirve para consultas complejas -> QueryMethods, si sigo una implementacion establecida usa el reflection y me genera el codigo. @Query si la consulta es demasiado compleja (por ejemplo agrupacion, having o subconsulta)

### Interfaces base
type = clase de la que tengo que hacer las consultas
id = tipo del id = normalmente long
tiene codigo listo para usar -> hace save, delete, findAll, findById
- CrudRepository : optimizada para CRUD
- PaggingAndSortingrepository: optimizada para ordenamiento de datos
- JPARepository: Generica.
Siempre habla que son interfaces pero son clases abstractas en el fondo 
Custom repository -> casos muy especificos. mayor control pero se necesita mantener una interfaz paralela para no romper la estructura de Spring data