Análisis del protocolo POP
a. Utilizando Wireshark, capture el tráfico de red contra el servidor de correo mientras desde la cuenta alumnoimap@redes.unlp.edu.ar le envía una correo a alumnopop@redes.unlp.edu.ar y mientras
alumnopop@redes.unlp.edu.ar recepciona dicho correo.
b. Utilice el filtro POP para observar los paquetes del protocolo POP en la captura generada y analice el intercambio de dicho protocolo entre el cliente y el servidor para observar los distintos comandos utilizados y su correspondiente respuesta
![[Pasted image 20250526164900.png]]
1. **Inicio de la conexión y saludo del servidor:**    
    - `+OK Dovecot ready.`
        - El servidor se presenta, indicando que está listo para aceptar comandos. "Dovecot" es el software de servidor POP3 en este caso.
2. **Autenticación del usuario:**
    - `USER alumnoi`
        - El cliente envía el nombre de usuario (`alumnoi`).
    - `PASS [oculto]`
        - El cliente envía la contraseña. En la captura, Wireshark indica "AUTH PLAIN" y luego una cadena Base64 (`AGFsdW1ub2kBWnJ1cy51bmxlbmxwVkd5hcHBiBhV0Ym9wWDBBWyNxZ`). Esto significa que la contraseña se envía en texto plano (o codificada en Base64, que es fácilmente decodificable), lo cual es una vulnerabilidad si no se usa TLS/SSL.
    - `+OK Logged in.`
        - El servidor confirma que el usuario se ha autenticado correctamente.
3. **Consulta de estado de los mensajes:**
    
    - `STAT`
        - El cliente solicita el estado de la bandeja de entrada (número total de mensajes y tamaño total).
    - `+OK 3 131463`
        - El servidor responde que hay **3 mensajes** en la bandeja de entrada y un tamaño total de **131463 bytes**.
4. **Listado de mensajes:**
    - `LIST`
        - El cliente solicita una lista de los mensajes, mostrando sus números y tamaños.
    - `+OK 3 messages:`
    - `1 129035`
    - `2 772`
    - `3 756`
    - `.`
    - El servidor lista los 3 mensajes: el mensaje 1 tiene 129035 bytes, el mensaje 2 tiene 772 bytes, y el mensaje 3 tiene 756 bytes.
5. **Obtención de identificadores únicos (UIDL):**
    - `UIDL`
        - El cliente solicita los identificadores únicos para cada mensaje. Esto es útil para que el cliente pueda saber qué mensajes ya ha descargado previamente.
    - `+OK`
    - `1 0000000356eaa394`
    - `2 0000000456eaa394`
    - `3 0000000556eaa394`
    - `.`
    - El servidor proporciona los UIDLs para cada mensaje.
6. **Recuperación de mensajes (RETR):**
    - `RETR 3`
        - El cliente solicita el contenido completo del **mensaje número 3**.
    - `+OK 756 octets`
        - El servidor indica que el mensaje 3 tiene 756 octetos y procede a enviarlo.
    - A continuación, se envía el contenido del mensaje 3, que en este caso parece ser un correo electrónico simple (no una imagen como adjunto), con encabezados como `Return-Path`, `X-Original-To`, `Delivered-To`, `Received`, `Message-ID`, `Date`, `MIME-Version`, `User-Agent`, `Content-Language`, `To`, `Subject`, `Content-Type: text/plain`, y `Content-Transfer-Encoding: 7bit`.
**Intercambios clave resumidos:**
- **Autenticación:** `USER`, `PASS`, `+OK Logged in.`
- **Consulta de mensajes:** `STAT`, `+OK 3 131463`, `LIST`, `UIDL`
- **Descarga de mensajes:** `RETR [número_mensaje]`, seguido del contenido del mensaje.
Es importante notar que en este flujo no se ve el comando `DELE` (eliminar) que el cliente aún no ha decidido eliminar mensajes. Si bien no sale en la captura el cliente finaliza enviando un `QUIT` y el servidor le responde `+OK Loggin out`, de modo que al terminar se cerro la sesión.

En resumen, los intercambios más importantes son los que permiten al cliente **autenticarse**, **consultar** cuántos mensajes hay y sus detalles, y finalmente **descargar** el contenido de los mensajes de la bandeja de entrada del servidor POP3.