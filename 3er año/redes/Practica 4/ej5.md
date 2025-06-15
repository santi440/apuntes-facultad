Análisis del protocolo IMAP
a. Utilizando Wireshark, capture el tráfico de red contra el servidor de correo
mientras desde la cuenta alumnopop@redes.unlp.edu.ar le envía un correo a
alumnoimap@redes.unlp.edu.ar y mientras alumnoimap@redes.unlp.edu.ar
recepciona dicho correo.
b. Utilice el filtro IMAP para observar los paquetes del protocolo IMAP en la
captura generada y analice el intercambio de dicho protocolo entre el cliente
y el servidor para observar los distintos comandos utilizados y su
correspondiente respuesta.
![[imap no se que tengo que ver.png]]
Aunque la captura muestra la actividad _después_ de la autenticación y selección de la bandeja de entrada, podemos inferir los intercambios más importantes basados en los comandos visibles:

1. **Mantenimiento de la conexión / Actividad (NOOP):**
    - `170 OK IDLE completed (38.001 + 38.069 + 38.060 secs).`
    - `171 noop`
    - `171 OK NOOP completed (0.001 + 0.000 secs).`
        - `NOOP` es un comando que el cliente envía al servidor para mantener la conexión activa o para solicitar cualquier actualización de estado sin realizar ninguna operación real. Es una especie de "ping". Los mensajes `IDLE completed` también indican un mecanismo para mantener la conexión abierta y recibir notificaciones de nuevos mensajes.
2. **Consulta de estado de mensajes y banderas (FETCH):**
    - `172 UID FETCH 7:* (FLAGS)`
    - `* 4 FETCH (UID 6 FLAGS (\Seen))`
    - `172 OK FETCH completed (0.001 + 0.000 secs).`
        - `UID FETCH` y `Workspace` son comandos para recuperar atributos de los mensajes. En este caso, el cliente está pidiendo las `FLAGS` (banderas) de los mensajes (por ejemplo, `\Seen` indica si el mensaje ha sido leído). Esto permite al cliente saber el estado de los mensajes sin descargarlos completamente.
3. **Monitoreo de la bandeja de entrada (EXISTS, RECENT):**
    - `* 5 EXISTS`
    - `* 0 RECENT`
    - `IDLE`
    - `173 OK IDLE completed (5.418 + 5.412 + 5.417 secs).`
        - `EXISTS` indica el número total de mensajes en la carpeta seleccionada.
        - `RECENT` indica el número de mensajes nuevos desde la última selección de la carpeta.
        - `IDLE` es un comando de IMAP que permite al cliente permanecer conectado y recibir actualizaciones push del servidor cuando hay nuevos mensajes o cambios en la bandeja de entrada, sin tener que sondear constantemente.
4. **Recuperación de encabezados de mensajes (FETCH BODY.PEEK[HEADER.FIELDS...]):**
    - `175 UID FETCH (UID RFC822.SIZE BODY.PEEK[HEADER.FIELDS (From To Cc Bcc Subject Date Message-ID Priority X-Priority References Newsgroups In-Reply-To Content-Type Reply-To)])`
    - `175 OK FETCH completed (0.001 + 0.000 secs).`
        - Este es un intercambio clave. El cliente está solicitando solo los encabezados específicos de los mensajes (como `From`, `To`, `Subject`, `Date`, `Message-ID`, `Content-Type`, etc.), no el cuerpo completo del mensaje. El `PEEK` significa que el mensaje no se marcará como leído (`\Seen`). Esto es muy eficiente para mostrar una lista de correos en un cliente sin descargarlos todos.
5. **Recuperación del cuerpo de mensajes (FETCH BODY.PEEK[TEXT]):**
    - `176 UID FETCH 7 (UID RFC822.SIZE BODY.PEEK[])`
    - `176 FETCH (UID 7 RFC822.SIZE 758 BODY[])`
    - `176 OK Fetch completed (0.002 + 0.000 + 0.001 secs).`
    - `177 UID FETCH 7 (UID RFC822.SIZE BODY.PEEK[])`
    - `177 FETCH (UID 7 RFC822.SIZE 758 BODY[]) {758}`
        - Aquí, el cliente está solicitando el cuerpo completo del mensaje número 7. De nuevo, `PEEK` se usa para no marcarlo como leído. Vemos que el mensaje es de tipo `Content-Type: text/plain` y `Content-Transfer-Encoding: 7bit`, lo que indica que es un correo de texto simple y no contiene una imagen como adjunto codificada en Base64 en este ejemplo.
6. **Recuperación de estructura y codificación del cuerpo (FETCH BODY.PEEK[HEADER.FIELDS (Content-Type Content-Transfer-Encoding)] BODY.PEEK[TEXT]<0.2048>):**
    - `178 UID FETCH 7 (UID BODY.PEEK[HEADER.FIELDS (Content-Type Content-Transfer-Encoding)]) BODY.PEEK[TEXT]<0.2048>)`
    - `178 OK Fetch completed (0.001 + 0.000 secs).`
    - `179 IDLE`
        - El cliente está haciendo una solicitud más granular, específicamente pidiendo los encabezados `Content-Type` y `Content-Transfer-Encoding` para el mensaje 7, y también un fragmento del cuerpo del mensaje (los primeros 2048 bytes). Esto se hace para entender la estructura del mensaje y cómo decodificarlo, especialmente si tuviera adjuntos.
**Intercambios clave resumidos (basados en la captura):**
- **Mantenimiento de sesión:** `NOOP`, `IDLE` (para mantener la conexión viva y recibir actualizaciones).
- **Consulta de estado/banderas:** `Workspace (FLAGS)` (para saber si un mensaje está leído, etc.).
- **Recuperación de encabezados:** `Workspace BODY.PEEK[HEADER.FIELDS (...)]` (para mostrar la lista de correos sin descargar el contenido completo).
- **Recuperación del cuerpo:** `Workspace BODY.PEEK[TEXT]` (para descargar el contenido del mensaje cuando el usuario lo selecciona).
- **Monitoreo de la bandeja de entrada:** `EXISTS`, `RECENT` (para saber cuántos mensajes hay y cuántos son nuevos).
A diferencia de POP3 que descarga mensajes y los elimina (opcionalmente) del servidor, IMAP se centra en la **sincronización** y la **gestión de mensajes directamente en el servidor**, permitiendo operaciones más complejas como mover mensajes entre carpetas, buscar, o mantener el estado (leído/no leído) sincronizado entre múltiples clientes.

Que no me tomen nada de esto porque ni idea :) 