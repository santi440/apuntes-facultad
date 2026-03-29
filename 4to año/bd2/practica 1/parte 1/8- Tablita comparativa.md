| **Aspectos**                            | **JPA**                                                | **Hibernate**                                                              |
| --------------------------------------- | ------------------------------------------------------ | -------------------------------------------------------------------------- |
| **Tipo**                                | **Especificación / Estándar** (Documentación y API)    | **Implementación / Framework** (Motor de persistencia)                     |
| **Define interfaces y anotaciones**     | **Sí.** Define `@Entity`, `@Id`, `EntityManager`, etc. | **Sí.** Además de las de JPA, posee anotaciones propias (ej: `@Type`).     |
| **Proporciona implementación concreta** | **No.** Es solo un conjunto de firmas e interfaces.    | **Sí.** Contiene el código lógico que realiza la persistencia.             |
| **Genera SQL a partir de consultas**    | **No.** Delega esta tarea al proveedor elegido.        | **Sí.** Traduce las consultas a los distintos dialectos SQL.               |
| **JPQL / HQL**                          | Define **JPQL** (orientado a objetos estándar).        | Implementa JPQL y ofrece su propio **HQL** (superconjunto de JPQL).        |
| **Maneja el Persistence Context**       | Define cómo debe comportarse teóricamente.             | **Sí.** Gestiona físicamente el caché, el _dirty checking_ y las sesiones. |
