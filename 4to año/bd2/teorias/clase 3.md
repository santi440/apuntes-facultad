# Patrones de persitencia
## DAO
Data access Object, cada clase tiene un DAO asociado que se encarga de las consulta de la db. Mantiene un nivel de acoplamiento alto pero no menor a que cada clase lo implemente por su cuenta. 

El problema entra en las clases que se relacionan entre si. quien seria responsable de llamar o guardarse si dos clases estan relacionadas

Se le pasa la logica de negocio se migra al dao y la clase de mi modelo se queda anemica. 

## ORM
Reemplazar implementacion concreta por de interface por orm 

## Repository 
Hermoso patron :)
Coleccion de objetos persistentes, con dao y demas hablamos de capa de persistencia. Se centra en las consultas fetch y fetchBy

# Lenguaje de consultas
## Oql 
se puede comenzar la consulta sin el select y estas en el dominio de los objetos. Notar que el from tiene solo Clase y no en plural porque solo marca una clase de comienzo.

## en Hibernate
- SQL pelado: es lo más performante que hay pero no es lo más común porque esta acoplado al motor que estoy usando 
- HQL: Un nivel más de abstracción. Hibernate traduce al SQL que corresponda. Se puede pedir dos objetos juntos y me da dos arrays o puedo crear nuevos objetos a partir de la consulta
- Criteria: sin saber de base de datos sino a traves de metodos propios de la session que manejamos. Mayor abstraccion a todas las demas
