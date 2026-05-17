La SessionFactory ofrece dos formas de obtener una Session: openSession() y
getCurrentSession(). Responder:
a) ¿Cuál es la diferencia entre ambos métodos? ¿Qué ocurre con el ciclo de vida de la
Session en cada caso?
- **`openSession()`**: Siempre abre una **nueva conexión** física a la base de datos. No le importa si ya hay una sesión activa en el hilo actual. El desarrollador tiene el control total (y la responsabilidad) del ciclo de vida.
- **`getCurrentSession()`**: Intenta devolver una sesión que ya existe y está vinculada al **contexto actual** (usualmente el hilo de ejecución o la transacción). Si no existe, la crea; si existe, la reutiliza. La session se cierra sola.
b) ¿Qué condición debe cumplirse para poder usar getCurrentSession()? ¿Que
configuración requiere en Hibernate?
Para poder usar `getCurrentSession()`, Hibernate necesita saber **cómo** definir el "contexto actual". Sin esto, lanzará una `HibernateException`.
**Configuración requerida:** Debés definir la propiedad `hibernate.current_session_context_class` en tu archivo de configuración (`hibernate.cfg.xml` o `persistence.xml`). Los valores comunes son:
- `thread`: Para aplicaciones simples donde la sesión vive en el hilo de ejecución.
- `jta`: Para aplicaciones en servidores de aplicaciones (como WildFly) con transacciones distribuidas.
c) ¿Quién es responsable de cerrar la Session cuando se usa getCurrentSession()? ¿Y cuando se usa openSession()?
- **`openSession()`**: El **desarrollador** es 100% responsable. Si te olvidás de llamar a `session.close()`, agotarás el pool de conexiones y la aplicación se caerá.
- **`getCurrentSession()`**: **Hibernate** es el responsable. Al estar vinculada al contexto de la transacción, Hibernate detecta el cierre de la misma y limpia la sesión automáticamente.
d) ¿Cuál de los dos métodos resulta más adecuado para usar en los repositorios de
esta práctica y por qué?
`getCurrentSession()` es la mejor alternativa, laburo menos :)
- **Gestión de Transacciones:**`getCurrentSession()` asegura que todos los DAOs/Repositorios usen la misma sesión y la misma conexión.
- **Prevención de Memory Leaks:** Evitás el error humano de olvidarte un `.close()`. Es mucho más seguro dejar que el framework gestione el cierre al finalizar la transacción.
- **Código Limpio:** Te ahorrás bloques `try-finally` repetitivos en cada método de tus repositorios para cerrar la sesión manualmente.