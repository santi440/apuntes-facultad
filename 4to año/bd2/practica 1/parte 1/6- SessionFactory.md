¿Qué es la SessionFactory? ¿Qué patrón de diseño implementa? Justificar por qué se
crea una sola instancia durante todo el ciclo de vida de la aplicación y no una por
operación.

Es un objeto de Hibernate que actúa como una **fábrica de sesiones** (`Session`). Su función principal es leer la configuración (el archivo `hibernate.cfg.xml` o el `persistence.xml`), mapear todas las entidades del diagrama (como `Route`, `Purchase`, `User`) y establecer la comunicación con la base de datos.

Es un objeto **pesado**, **inmutable** y **thread-safe** (seguro para ser usado por muchos hilos al mismo tiempo).

Implementa principalmente dos patrones:
- **Factory Method (Fábrica):**  Se encarga de la creación y ciclo de vida de los objetos `Session`. Abstrayendo al desarrollador de la complejidad de abrir conexiones JDBC manualmente.
- **Singleton (Generalmente):** Aunque Hibernate no te obliga por código a que sea un Singleton, en la práctica se implementa así dentro de la arquitectura de la aplicación para que exista **una única instancia** por cada base de datos.

Crear una `SessionFactory` por cada operación sería un error crítico de performance. Se mantiene una sola durante todo el ciclo de vida por estas razones:
#### A. El costo del "Startup" (Configuración pesada)
Cuando se instancia una `SessionFactory`, Hibernate realiza tareas muy costosas:
- **Parsing de metadatos:** Lee todas las anotaciones (`@Entity`, `@Table`) de tus clases.
- **Validación del esquema:** Compara tus clases con las tablas reales de la base de datos.
- **Generación de SQL:** Pre-genera gran parte del SQL básico para no tener que hacerlo en tiempo de ejecución.
- **Manejo del Pool de conexiones:** Inicializa el pool (como HikariCP) que gestionará las conexiones físicas.

> Hacer esto en cada consulta haría que la aplicación sea extremadamente lenta.

#### B. Gestión del Caché de Segundo Nivel (L2 Cache)
La `SessionFactory` es la dueña del **Caché de Segundo Nivel**. Este caché es compartido por toda la aplicación. Si tuvieras múltiples fábricas, cada una tendría su propio caché, desperdiciando memoria RAM y perdiendo la consistencia de los datos entre diferentes sesiones.
#### C. Inmutabilidad y Seguridad (Thread-Safety)
A diferencia de la `Session` (que representa una sola unidad de trabajo y **no** es segura entre hilos), la `SessionFactory` es **thread-safe**. Esto permite que una sola instancia atienda pedidos de cientos de usuarios simultáneos en un servidor web (como Tomcat), entregándole a cada uno una `Session` liviana y rápida.