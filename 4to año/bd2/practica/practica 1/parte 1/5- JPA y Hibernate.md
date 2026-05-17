¿Cuál es la relación entre JPA e Hibernate? ¿Puede usarse JPA sin Hibernate o
Hibernate sin JPA?

- JPA es la especificación. Hibernate es la implementación o proveedor lee esas anotaciones, abre los sockets de red, se conecta a la base de datos y ejecuta el SQL.

- JPA sin Hibernate: si, JPA es un estandar y hay otras implementaciones concretas que la implementan como EclipseLink, Jakarta,OpenJDK.

- Hibernate sin JPA: Si pero perdes portabilidad. Hibernate tiene su propia API nativa con funciones que no estan incluidas en JPA, por simplicidad.
	- En lugar de `EntityManager`, usa `Session`.
	- En lugar de `EntityManagerFactory`, usa `SessionFactory`.
	- En lugar de JPQL, usa HQL (aunque son casi iguales).