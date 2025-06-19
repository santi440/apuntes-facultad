¿Cuál es el puerto por defecto que se utiliza en los siguientes servicios?
Web / SSH / DNS / Web Seguro / POP3 / IMAP / SMTP
Investigue en qué lugar en Linux y en Windows está descrita la asociación utilizada por defecto para cada servicio.

- **Web (HTTP):** Puerto **80**
- **SSH (Secure Shell):** Puerto **22**
- **DNS (Domain Name System):** Puerto **53** (tanto TCP como UDP)
- **Web Seguro (HTTPS):** Puerto **443**
- **POP3 (Post Office Protocol version 3):** Puerto **110** (para conexiones no cifradas)
    - **POP3 Seguro (POP3S/SSL-POP):** Puerto **995**
- **IMAP (Internet Message Access Protocol):** Puerto **143** (para conexiones no cifradas)
    - **IMAP Seguro (IMAPS/SSL-IMAP):** Puerto **993**
- **SMTP (Simple Mail Transfer Protocol):** Puerto **25** (para envío de correo, a menudo bloqueado por ISPs)
Linux:
La asociación de puertos y servicios está definida en el archivo `/etc/services`.
Windows:
Principalmente el **Registro de Windows** (HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services`) y secundariamente el archivo `C:\Windows\System32\drivers\etc\services`.