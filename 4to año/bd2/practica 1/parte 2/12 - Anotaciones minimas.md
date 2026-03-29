¿Cuál es el conjunto mínimo de anotaciones que debe tener una clase para ser
persistente con JPA
- @Entity: Le indica a JPA que esta clase no es un simple objeto Java (POJO), sino que debe tener una representación (una tabla) en la base de datos. Sin esta anotación, el motor de persistencia ignorará la clase por completo.
- @Id: Define cuál de los atributos de la clase será la **Primary Key** (Clave Primaria) en la tabla. JPA exige que toda entidad tenga un identificador único para poder gestionar su ciclo de vida y diferenciarla de otras instancias.

Menciones honorificas
`@Table` o `@Column` = utiles en la practica, sino estan se asume que tiene el mismo nombre que el de lo que debe mapear pero los estandares en Objetos y relacional son diferentes (Service != services).
`@GeneratedValue`: no es obligatoria porque podes setear a mano el id. Con esta podes marcar que el id se genere automaticamente (autoincremental o secuencia)