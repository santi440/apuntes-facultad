IMAP vs POP
a. Marque como leídos todos los correos que tenga en el buzón de entrada de
alumnopop y de alumnoimap. Luego, cree una carpeta llamada POP en la
cuenta de alumnopop y una llamada IMAP en la cuenta de alumnoimap.
Asegúrese que tiene mails en el inbox y en la carpeta recientemente creada
en cada una de las cuentas.
b. Cierre la sesión de la máquina virtual del usuario redes e ingrese
nuevamente identificándose como usuario root y password packer, ejecute el
cliente de correos. De esta forma, iniciará el cliente de correo con el perfil del
superusuario (diferente del usuario con el que ya configuró las cuentas antes
mencionadas). Luego configure las cuentas POP e IMAP de los usuarios
alumnopop y alumnoimap como se describió anteriormente pero desde el
cliente de correos ejecutado con el usuario root. Responda:
i. ¿Qué correos ve en el buzón de entrada de ambas cuentas? ¿Están
marcados como leídos o como no leídos? ¿Por qué?

En el buzon de entrada de ambas cuentas quedaron los mismos correos, en pop aparecen como leidos y en imap como ya leidos.  Porque
Pop3 no maneja el estado de los mensajes en el servidor. El concepto de "leído" o "no leído" es una función que gestiona el cliente de correo _localmente_. Si descargas un mensaje con POP3, el servidor no registra ese estado. Por eso, si te conectas con otro cliente POP3 o incluso con el mismo cliente después de un reinicio, los mensajes que aún están en el servidor (si elegiste no borrarlos) aparecerán como "no leídos" porque el servidor POP3 no almacena ese atributo.

IMAP, en cambio,  mantiene una Sincronización Bidireccional del Estado, el estado de los mensajes (leído/no leído, marcado/no marcado, respondido, reenviado, etc.) directamente en el servidor.
    - Si lees un correo en un dispositivo, el servidor IMAP marca ese correo como "leído". Cuando te conectas con otro dispositivo usando IMAP, ese dispositivo también lo verá como "leído".
    - Si eliminas un correo, se marca para eliminación en el servidor y luego se purga (si se configura).
2. **Acceso Múltiple desde Varios Dispositivos:** Dado que toda la gestión se realiza en el servidor, puedes acceder a tu correo desde múltiples clientes (ordenador de escritorio, laptop, móvil, webmail) y siempre verás el mismo estado de tu buzón: las mismas carpetas, los mismos mensajes en las mismas carpetas, y el mismo estado (leído/no leído).

ii. ¿Qué pasó con las carpetas POP e IMAP que creó en el paso
anterior?
POP3
1. **Enfoque en el "Inbox" (Bandeja de Entrada):** POP3 solo interactúa con la carpeta principal del servidor, que es tradicionalmente la "Bandeja de Entrada" (Inbox). No tiene conocimiento ni comandos para acceder a otras carpetas (como "Enviados", "Borradores", carpetas personalizadas que creaste, etc.).
2. **Modelo de "Descarga y Posible Eliminación":** Cuando un cliente POP3 se conecta, descarga los mensajes de la bandeja de entrada. Una vez descargados, el cliente puede configurarse para:
    - **Eliminarlos del servidor:** Es el comportamiento predeterminado en muchos clientes antiguos. Esto significa que si los descargas en un dispositivo, no estarán disponibles en otro.
    - **Mantenerlos en el servidor:** Permite que varios clientes descarguen el mismo correo, pero sigue siendo una copia del "Inbox".
3. **No hay sincronización de carpetas:** Como solo se enfoca en el Inbox, si tú mueves un correo a otra carpeta _en tu cliente de correo local_, esa acción no se refleja en el servidor POP3, porque el protocolo no tiene mecanismos para crear o interactuar con otras carpetas.
Imap: 
**Gestión de Carpetas (Folders/Mailboxes) en el Servidor:** IMAP es "consciente de las carpetas". Permite al cliente:
    - Listar todas las carpetas disponibles en el servidor (Inbox, Sent, Drafts, Spam, y cualquier carpeta personalizada que hayas creado).
    - Crear, renombrar y eliminar carpetas en el servidor.
    - Mover mensajes entre carpetas en el servidor.
    - Seleccionar y acceder a cualquier carpeta para ver sus contenidos.
    - Cuando mueves un mail a otra carpeta en tu cliente IMAP, el comando `COPY` o `MOVE` se envía al servidor IMAP, y el mail se mueve _en el servidor_.
Toda la gestión se realizá en el servidor y es visible desde cualquier dispositivo. 
c. En base a lo observado. ¿Qué protocolo le parece mejor? ¿POP o IMAP?
¿Por qué? ¿Qué protocolo considera que utiliza más recursos del servidor?
¿Por qué?
Depende para que, pop3 resulta muchisimo mas eficiente del lado del servidor, no mantiene contexto y mantiene el modelo de  "Descarga y Posible Eliminación". Imap mantiene más información pero permite acceso desde multiples dispositivos con la misma información. A dia de hoy, por el manejo de los usuarios con multiples dispositivos y uso alternado entre ellos, IMAP es una opción más amigable para el usuario final.