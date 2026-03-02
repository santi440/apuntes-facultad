# 1. Modelo de datos
Provee una notación para describir los datos. Se define a partir de: 
-  Estructura de los datos
- Restricciones sobre los datos
- Operaciones con los datos (optativo)
Conjunto de conceptos que pueden usarse para describir la estructura de una base de datos.
### Modelo Entidad-Relacion (ER)
Basados en objetos
Se construye en base a: 
- Estructura:
	1. Entidad : Cosa o concepto distinguible de las demás
	2. Relacion: Asosición de dos o más entidades
	3. Atributos: Información de una entidad o relación
- Restricciones:
	1. Cardinalidad: Determina el número de veces en el que puede participar una entidad en una relación
	2. Identificador: Restriccion de unicidad de atributo que identifica a una entidad dentro de su conjunto, puede ser simple o compuesto
	3. Grado: Cantidad maxima de veces que una entidad puede estar relacionado con otra
	4. Acerca de los nombres : Era más facil decir simplemente que no se pueden repetir los nombres dentro del modelo :)
####  Ampliados
- Especialización: De un conjunto de datos se genera un subconjunto de datos de nivel más bajo. 
- Generalización: De uno o más subconjuntos de datos se genera uno de un nivel mayor.
- ![[Pasted image 20260221185507.png]]
#### Agregacion
En este modelo no se pueden expresar relaciones entre relaciones existentes. Una relación binaria se trata como una entidad de alto nivel (minima cardinalidad 1).

> [!NOTE]
> Es condicion NECESARIA que el grado de la relación en ambos lados sea muchos, relacion muchos a muchos
![[Pasted image 20250820095043.png]]
### Modelo relacional
basado en registros
Se construye en base a: 
- Estructura:
	1. Esquema : Relacion con atributos. ej Persona(dni,nombre,apellido). Los atributos de un esquema no tienen un orden fisico porque son un conjunto, no una lista.
	2. Relacion: tablas bidimencionales
	3. Atributos: nombre de cada columna
	4. Tupla: Fila de la relacion y tiene si o si un componente para cada atributo. ej. (12345,juan,perez)
- Restricciones:
	1. Dominio de atributos: Debe ser atomico (no lista ni registro)
	2. Identificador: conjunto de atributos que no pueden tomar el mismo valor en dos tuplas
	3. Acerca de los nombres : Era más facil decir simplemente que no se pueden repetir los nombres dentro del modelo :)
### Relacion entre los dos tipos
Cada uno es independiente y puedo crear uno en forma independiente de tener o no el otro. Pero podemos hacer una transformacion uno a uno entre ellos
# 2. Teoria de diseño de BD
Se organizan los datos siguiendo un conjunto de reglas que minimizan la redundancia, reduce anomalias y, en ciertos casos, mejora la performance. 
##  Normalización
Toma una relación <u>muy grande</u> de entrada y la descompone siguiendo un conjunto de reglas y pasos en relaciones mas pequeñas libre de redundancia de datos y otras anomalias de inserción. Se realiza sobre relacionales o DB Sql.
### Anomalia
Se define como un problema que surge a raiz del diseño de una relación.
#### Anomalia redundancia
Informacion que se repite en forma innecesaria en las distintas tuplas.
#### Anomalia actualizacion
Se puede actualizar el valor de una tupla sin modificar el de las demas. Muy de la mano con el anterior.
#### Anomalia insercion
Insertar valores en ciertos atributos de una relacion pero no en otros genera valores nulos -> inconsistencias. empleado que no esta asignado a un departamento.
#### Anomalia redundancia
Esta no la entendi. Dice que si borras algunos datos de una tupla algunos datos perdes info de la tupla completa -> me suena mas a una asignacion incorrecta de atributos a la tupla :)

### Dependencias Funcionales
Restriccion entre subconjuntos de atributos de una relación.
Es trivial si uno o mas elementos del determinado estan contenidos en el determinado.

### Clave de una relación 
Un conjunto {A1,A2,...} permite determinar funcionalmente todos los demas atributos de la relacion. Debe ser minimal.

### Clave candidada 
Un conjunto minimal que permite determinar funcionalmente, y sin depender de un subconjunto, todos los demas atributos de la relacion. Misma definicion

### Superclave
Lo mismo pero sin minimal.
Una clave esta contenida en la superclave.

### Axiomas
- Reflexion: hacerla trivial . a,b -> a
- Aumento: agregarle el mismo dato. z,a -> z,y
- transitiva: x->y , y-> z entonces x->z
- union : x->y, x-> z entonces x->yz
- descomposicion : ~union
- pseudotransitividad: x->y, Y,Z-> W, entonces X,Z->W
### Descomposicion/ particionar
en base a una DF, paticiono en dos relaciones.  No se debe perder informacion (R1∩ R2 clave en R1 o en R2) ni dfs (simple si estan en alguna Relacion, o mediante algoritmo en mas de una relacion)

### FN
Criterio para determinar cantidad de anomalias e inconsistencias.
- 1FN = Sin polivalentes
- 2FN = X-> A. Se cumple que A depende totalmente de la clave. Lo que quiera decir.
- BCNF= elimina anomalias y solo puede quedar redundancia. X es superclave del exquema R o bien X-> A es trivial

- 3FN= Si no llego a BCNF porque se perderian DFs queda en 3FN. No siempre se quitan las anomalias pero no se pierde nada. dado x->a. X es superclave y A es primo (esta incluido en alguna clave candidata). Dice ademas Se construye una tabla por cada dependencia funcional.  Si la clave de la tabla original no está incluida en ninguna de las tablas del punto anterior, se construye una tabla con la clave.
### Dependencias multivaluadas
X->> Y.
Dado un X determina un <u>conjunto de valores</u> Y,este conjunto de valores de Y NO está
relacionado (ni funcional ni multifuncionalmente) con los valores de R - X -Y, es independiente. es trivial si son todos los valores de R.

- 4 FN = no tiene multivaluadas o se cumple que toda X>>Y es trivial.

# 3. Algebra relacional
los cambios de estado se especifican mediante operaciones, los operandos son relaciones y el resultado es una nueva relación. 
### Operaciones fundamentales
- Selección (σ)
- Proyección (𝜋)
- Producto Cartesiano ( X )
- Renombre (ρ )
	▪ De una relación
	▪ De atributos de una relación
- Unión ( U ): «unión compatible» Relaciones con igual aridad (igual número de atributos) El dominio del i-ésimo atributo de ambas relaciones debe ser el mismo.
- Diferencia ( - )
- Intersección ( ∩  ) : es equivalente a  R –( R – S )
- Producto Theta ( |X|θ ) = Genera una nueva relación con las tuplas resultantes de aplicar una operación de selección con la condición indicada por θ sobre el resultado de un producto cartesiano.![[Pasted image 20260223181951.png]]
- Producto Natural ( |X| )
- División ( % ) : Los atributos del divisor S deben ser un subconjunto de los atributos de la relación R con igual dominio

# 4. DBMS 
Esta compuesto por :
- DDL(Data definition languaje): Permite definir las estructuras con la que se almacenarán los datos.
- DML(Data manipulation languaje) :  Lenguaje que permite escribir sentencias para:
	- Recuperar información
	- Agregar información
	- Eliminar información
	- Modificar información
	Hablemos claro CRUD.
- DCL(Data control lanjuage): Permite administrar los usuarios de la bases de datos y sus permisos.
- Data dictionario: Posee las definiciones de todas las variables de la base.
Debe proporcionar:
- Abstracción de la información: Esconde el como se almacenan los datos.
- Independencia: Se puede modificar la DB sin tocar las aplicaciones que se sirven de ella
- Redundancia mínima: Un buen diseño deberia poder eliminar la redundancia. por eficiencia en ocasiones puede ser beneficioso y se elige deliberadamente
- Consistencia: Cuando no se logra redundancia nula, todos los datos repetidos se deberian modificar a la vez.
- Seguridad: usuarios y grupos de usuarios, que permiten otorgar diversas categorías de permisos.
- Integridad: Intenta evitar corrupcion de los datos almacenados 
- Respaldo y recuperación: proporciona  una forma eficiente de realizar copias de seguridad de la información almacenada en ellos, y de restaurar a partir de estas copias los datos que se hayan podido perder.
- Control de la concurrencia: muchas personas intentan acceder -> se debe evitar la inconsistencia
- Tiempo de respuesta: Debe tardar poco.

### Tipos de DBMS
#### Relacional
Los mas comunes.
#### No SQL
Documentos
Clave – Valor
Grafos
Columnas

### Clave 
#### Primaria
Es un conjunto de atributos de una tabla que cumple con unicidad y no nulidad.
#### Foranea
Es un conjunto de atributos de una tabla que establece una relación con otra tabla, usualmente (para mi siempre) coincide con la clave primaria de la tabla relacionada.

### Motor de almacenamiento 
El motor de almacenamiento determina como se organizan, almacenan (formato) y  manejan los datos en el sistema. InnoDB es el act de mysql.

ME DIO PAJA Y EL RESTO SOLO LO LEI
# 5. Visualización 
