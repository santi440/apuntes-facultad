¿Qué problema concreto resuelve un ORM? Describir al menos 3 (tres) inconvenientes
que aparecen al intentar persistir objetos directamente con JDBC puro

JDBC = Java Database connectivity es una API estándar de Java que permite a las aplicaciones conectarse, consultar y manipular bases de datos relacionales mediante SQL.

ORMs (Object-Relational Mapper) = Nos permite guardar y gestionar más facilmente nuestros objetos en una db. Soporta Jerarquías, Polimorfismo, Relaciones, Tx, Lazy, HQL.

1. El "Boilerplate Code" (Código Repetitivo y Verboso)
Con JDBC, el desarrollador es responsable de gestionar manualmente todo el ciclo de vida de la conexión y la traducción de datos.
- **El problema:** Debes escribir código repetitivo para abrir la conexión, crear el `Statement`, ejecutar la query, recorrer el `ResultSet` y, lo más tedioso, mapear cada columna de la tabla a un atributo del objeto manualmente (y viceversa para los `INSERT`).
- **La solución del ORM:** El mapeo se define una sola vez (vía anotaciones tipo `@ID` o XML). El ORM se encarga de transformar las filas en objetos y los objetos en filas automáticamente.
1. Gestión de Relaciones y Carga de Datos (Lazy vs. Eager Loading)
Manejar relaciones complejas (uno-a-muchos, muchos-a-muchos) con JDBC es una pesadilla logística.
- **El problema:** Si traes un objeto `Cliente` que tiene 50 `Facturas`, en JDBC debes decidir: o haces un `JOIN` gigante (que puede ser ineficiente) o haces dos consultas manuales. Si luego quieres que las facturas solo se carguen cuando realmente las necesites, tienes que programar esa lógica de "carga bajo demanda" tú mismo.
- **La solución del ORM:** Implementa **Lazy Loading** (carga perezosa) de forma transparente. El objeto parece tener los datos, pero el ORM solo hace la consulta a la base de datos en el momento exacto en que accedes a la colección de facturas.
1. Dependencia del Dialecto SQL (Portabilidad)
SQL es un estándar, pero cada motor (PostgreSQL, Oracle, MySQL, SQL Server) tiene sus propias funciones, tipos de datos y sintaxis específicas (dialectos).
- **El problema:** Con JDBC, las consultas SQL están "hardcodeadas" como strings en tu código Java. Si decides cambiar de motor de base de datos, tendrías que revisar y modificar manualmente cientos de strings de SQL para adaptarlos al nuevo motor.
- **La solución del ORM:** Tú trabajas con objetos o lenguajes de consulta abstractos (como JPQL o HQL). El ORM tiene **dialectos** configurables que traducen esas operaciones al SQL específico del motor que estés usando sin tocar una sola línea de lógica de negocio.