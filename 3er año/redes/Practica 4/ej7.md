¿En algún caso es posible enviar más de un correo durante una misma conexión TCP?
Considere:
- Destinatarios múltiples del mismo dominio entre MUA-MSA y entre MTA-MTA
- Destinatarios múltiples de diferentes dominios entre MUA-MSA y entre
MTA-MTA
Sí, **es posible y de hecho común enviar múltiples correos electrónicos durante una misma conexión TCP utilizando SMTP**, tanto en la comunicación entre un Mail User Agent (MUA) y un Mail Submission Agent (MSA) como entre dos Mail Transfer Agents (MTAs).

El protocolo SMTP está diseñado para ser eficiente y permite una serie de "transacciones de correo" (MAIL FROM, RCPT TO, DATA) dentro de una única sesión de conexión TCP.

Analicemos los escenarios:

### Escenario 1: MUA (Cliente de correo) a MSA (Servidor de envío)

- **Destinatarios múltiples del mismo dominio:**
    
    - **Sí, absolutamente.** Cuando envías un correo a `usuario1@ejemplo.com`, `usuario2@ejemplo.com`, etc., tu cliente (MUA) establece una conexión TCP con el servidor de envío (MSA).
    - Dentro de esa misma conexión, el MUA envía:
        - `MAIL FROM: tu_correo@tu_dominio.com`
        - `RCPT TO: usuario1@ejemplo.com` (y recibe un `250 OK`)
        - `RCPT TO: usuario2@ejemplo.com` (y recibe un `250 OK`)
        - `DATA` (y luego el contenido del correo)
    - Todo esto ocurre dentro de la misma conexión TCP. Es la forma estándar y eficiente de manejar múltiples destinatarios para un _único mensaje_.
- **Destinatarios múltiples de diferentes dominios:**
    
    - **Sí, para un único mensaje con múltiples destinatarios.** Si envías un correo a `usuario1@ejemplo.com`, `usuario2@otrodominio.com`, `usuario3@tercerdominio.net`, tu cliente (MUA) establecerá una única conexión TCP con tu MSA.
    - Dentro de esa conexión, el MUA enviará un `MAIL FROM` y luego múltiples `RCPT TO` (uno por cada destinatario, independientemente de su dominio), seguido del `DATA`.
    - El MSA es quien se encargará de dividir ese mensaje y enviarlo a los MTAs correspondientes para cada dominio de destino. Es decir, tu cliente solo necesita una conexión para entregar _un mensaje_ a tu MSA, incluso si ese mensaje tiene destinatarios en diferentes dominios.
- **Múltiples correos electrónicos distintos (no un solo correo con múltiples destinatarios):**
    
    - **Sí, es posible y optimiza recursos.** Después de enviar un correo (`MAIL FROM`, `RCPT TO`, `DATA`), el cliente SMTP puede simplemente volver a emitir un nuevo comando `MAIL FROM` para enviar un segundo correo distinto, sin cerrar la conexión TCP. Esto es especialmente útil si el cliente tiene una cola de correos para enviar.
### Escenario 2: MTA (Servidor de Correo) a MTA (Servidor de Correo)

- **Destinatarios múltiples del mismo dominio (para un único mensaje):**
    
    - **Sí.** Si un MTA tiene un correo para `usuario1@destino.com` y `usuario2@destino.com` (desde un mismo remitente), al conectar con el MTA de `destino.com`, utilizará un `MAIL FROM` y luego múltiples `RCPT TO` dentro de la misma sesión para ese único mensaje.
- **Destinatarios múltiples de diferentes dominios (para un único mensaje):**
    
    - **No directamente en una única conexión de MTA a MTA para todos.** Si un MTA tiene un correo con destinatarios `usuario1@ejemplo.com` y `usuario2@otrodominio.com`, el MTA remitente generalmente realizará búsquedas DNS para cada dominio.
    - Luego, intentará establecer conexiones **separadas** a los MTAs de `ejemplo.com` y `otrodominio.com`. Aunque el _contenido_ del correo sea el mismo, a nivel de protocolo, el MTA lo tratará como dos "envíos" diferentes, cada uno con su propia conexión TCP al MTA de destino correspondiente.
    - **Excepción/Consideración:** Si ambos dominios (`ejemplo.com` y `otrodominio.com`) son servidos por el _mismo_ MTA físico (por ejemplo, ambos son dominios alojados en el mismo proveedor de hosting y apuntan al mismo servidor de correo), entonces sí podría usar una única conexión y enviar `RCPT TO` para ambos, ya que está hablando con la misma máquina. Sin embargo, esto es una situación de configuración de infraestructura, no una característica inherente al manejo de dominios diferentes por parte del protocolo SMTP estándar para MTAs genéricos.
- **Múltiples correos electrónicos distintos (no un solo correo con múltiples destinatarios):**
    - **Sí, es una práctica común y altamente eficiente.** Los MTAs suelen tener colas de correo y, cuando establecen una conexión TCP con otro MTA (por ejemplo, para entregar un correo a `gmail.com`), si tienen más correos pendientes para `gmail.com` (de otros remitentes, o del mismo remitente a diferentes destinatarios de `gmail.com`), intentarán enviar todos esos correos en la misma conexión TCP antes de cerrarla. Esto ahorra el overhead de establecer y cerrar múltiples conexiones TCP.
    - Los servidores de correo modernos están optimizados para este comportamiento, especialmente con el uso de extensiones SMTP como `PIPELINING`, que permiten enviar múltiples comandos antes de esperar la respuesta del servidor, mejorando aún más la eficiencia.