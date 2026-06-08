1. ¿Qué tipo de base de datos es Redis? ¿En qué se diferencia de una base de datos relacional y de otras bases de datos NoSQL como MongoDB?
   
   Es una base de datos no relacional o no sql del tipo clave-valor. mapea una key con un valor asociado, como un diccionario en memoria.
   - **Diferencia con Relacionales (RDBMS):** Las bases relacionales (SQL) guardan la información en tablas rígidas con filas y columnas unidas por claves foráneas (relaciones). Redis no tiene tablas, ni esquemas, ni soporta operaciones complejas de `JOIN` nativas.
 - **Diferencia con MongoDB:** Aunque ambas son NoSQL, **MongoDB es una base de datos orientada a documentos** (guarda archivos JSON/BSON complejos y realiza consultas sobre cualquier atributo interno). Redis, en cambio, mapea una clave simple directamente a un valor estructurado, siendo mucho más simple, directa y rápida.
   
2. ¿Dónde almacena los datos Redis? ¿Qué implicancias tiene esto en términos de velocidad y de persistencia?
   
- Redis almacena absolutamente todos sus datos **en la memoria RAM** del servidor.
- **Implicancia en Velocidad:** Al no tener que leer ni escribir en el disco rígido para las operaciones cotidianas, ofrece una velocidad ridículamente alta. Las consultas se resuelven en **microsegundos** (sub-milisegundo), soportando cientos de miles de operaciones por segundo.
- **Implicancia en Persistencia:** La RAM es un medio volátil: si el servidor se apaga o se corta la luz, los datos se borran. Para evitar esto, Redis implementa mecanismos de respaldo que copian la información de la RAM al disco de forma asincrónica
   
3. ¿Qué tipos de datos soporta Redis? Listar y describir brevemente cada uno.

- **Strings (Cadenas):** El tipo más básico. Puede contener texto, números o incluso archivos binarios (como imágenes serializadas) de hasta 512 MB.
- **Lists (Listas):** Colecciones de strings ordenadas por orden de inserción. Permiten agregar elementos al principio o al final rápidamente (ideal para colas de tareas).
- **Sets (Conjuntos):** Colecciones de strings **no ordenadas y sin elementos duplicados**. Permiten hacer operaciones de intersección, unión y diferencia.
- **Hashes (Mapas/Diccionarios):** Campos de tipo clave-valor dentro de una clave de Redis. Son perfectos para representar objetos (por ejemplo, un usuario con `nombre`, `edad`, `email`).
- **Sorted Sets (Conjuntos Ordenados):** Similares a los Sets, pero cada elemento tiene asociado un puntaje (_score_). Redis los mantiene ordenados automáticamente por ese puntaje (ideal para tablas de posiciones).
- **Streams:** Estructuras tipo _log_ que permiten registrar eventos en tiempo real para sistemas de mensajería y mensajería histórica.

4. Enunciar las características principales de Redis.

- **Velocidad extrema:** Operaciones en memoria con latencia menor a un milisegundo.
- **Estructuras de datos nativas:** No requiere parsear texto; maneja listas, hashes y sets directamente desde los comandos.
- **Monohilo (Single-threaded):** Ejecuta un comando a la vez en un solo hilo principal. Esto elimina los problemas de concurrencia (condiciones de carrera) y hace que las operaciones sean sumamente predecibles y seguras.
- **Soporte de Expiración (TTL):** Permite asignarle un tiempo de vida a cualquier clave para que se borre sola automáticamente (ideal para sesiones y cachés).
- **Pub/Sub integrado:** Sistema nativo de mensajería de tipo "Publicador/Suscriptor" para comunicación en tiempo real entre servicios.

5. Comparar Redis con los RDBMS: ¿en qué casos conviene usar Redis en lugar de una base de datos relacional y en cuáles no?
   
- **Sistemas de Caché:** Para guardar consultas pesadas de la base de datos relacional que se repiten seguido.
- **Gestión de Sesiones:** Almacenar los tokens de inicio de sesión de millones de usuarios concurrentes de forma ultra rápida.
- **Contadores en tiempo real:** Contar visitas a un sitio, likes, o visualizaciones en vivo, donde escribir en disco a cada segundo rompería una base SQL.

Cuando no:
- **Datos Altamente Relacionados:** Si necesitas hacer reportes complejos que unan muchas tablas (ej: facturación, contabilidad, ERPs).
- **Volumen de datos mayor a la RAM:** Si tu base de datos pesa 2 Terabytes, guardarla entera en Redis saldría carísimo porque requerirías 2 TB de memoria RAM.
- **Sistemas financieros/críticos:** Donde no se puede tolerar la pérdida de un solo bit bajo ninguna circunstancia de corte eléctrico.

6. ¿Redis tiene soporte para transacciones? ¿Cómo funcionan? ¿Qué garantías ofrecen y qué limitaciones tienen respecto de las transacciones ACID?

Sí, Redis soporta transacciones mediante los comandos **`MULTI`**, **`EXEC`**, **`DISCARD`** y **`WATCH`**.

- Al ejecutar `MULTI`, Redis entra en un modo donde "encola" todos los comandos siguientes sin ejecutarlos. Cuando se envía `EXEC`, Redis ejecuta secuencialmente todo el bloque de comandos en un solo paso, garantizando que nadie se meta en el medio.
    
- **Garantías y Limitaciones frente a ACID:**
    - **No hay Rollback (No es Atómica en sentido estricto):** Si un comando dentro de la transacción falla (por ejemplo, intentar sumarle un número a un String), Redis **no tira atrás los comandos anteriores** que sí tuvieron éxito. Continúa ejecutando el resto de la cola de comandos.
    - **Aislamiento (Isolation):** Es perfecto, debido a su naturaleza monohilo. Mientras se ejecuta el bloque del `EXEC`, ningún otro cliente puede alterar los datos intermedios.

7. ¿Redis tiene persistencia? Describir los mecanismos disponibles (RDB y AOF) e indicar las diferencias entre ellos.

| **Característica**             | **RDB (Redis Database Backup)**                                                                                         | **AOF (Append Only File)**                                                                                                          |
| ------------------------------ | ----------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| **Funcionamiento**             | Toma "fotos" (snapshots) de toda la base de datos en momentos específicos (ej: cada 5 minutos si cambiaron 100 claves). | Registra en un archivo de texto plano **cada comando de escritura** recibido (`SET`, `HSET`, etc.) a medida que suceden.            |
| **Velocidad de Recuperación**  | **Muy rápida**. Al reiniciar, solo lee la foto del estado final y la carga directo en la RAM.                           | **Más lenta**. Tiene que volver a ejecutar uno por uno todos los comandos del historial para reconstruir la data.                   |
| **Riesgo de pérdida de datos** | **Alto**. Si el servidor cae entre capturas, se pierden todos los cambios hechos desde la última foto.                  | **Mínimo**. Usualmente se configura para escribir en el disco cada segundo, por lo que solo se perdería el último segundo de datos. |
| **Tamaño del archivo**         | Compacto y pequeño (archivo binario).                                                                                   | Grande (crece de manera continua con cada operación de escritura).                                                                  |

8. ¿Cuáles son los principales casos de uso de Redis en aplicaciones reales?

- **Capa de Caché (Application Caching):** Guardar respuestas de APIs o queries pesadas de SQL para que la aplicación responda al instante en la siguiente solicitud.
- **Manejo de Sesiones (Session Store):** Guardar las sesiones activas de usuarios en plataformas web (como carritos de compras de e-commerce), asegurando accesos instantáneos en cada clic.
- **Tablas de Posiciones / Líderes (Leaderboards):** Gracias a los _Sorted Sets_, plataformas de videojuegos manejan los rankings mundiales actualizados en tiempo real de millones de usuarios concurrentes.
- **Colas de Mensajería y Tareas:** Utilizando las _Lists_ (con comandos como `BRPOP`), se implementan sistemas de procesamiento de tareas en segundo plano (como el envío masivo de emails).
- **Limitadores de Tasa (Rate Limiting):** Controlar cuántas peticiones puede hacer un usuario por minuto a una API para evitar ataques informáticos o abusos de servicio.