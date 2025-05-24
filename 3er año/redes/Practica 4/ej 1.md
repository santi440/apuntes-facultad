1. ¿Qué protocolos se utilizan para el envío de mails entre el cliente y su servidor de correo? ¿Y entre servidores de correo?
cliente-->Servidor de correo:
- Saliente: 
	  **SMTP (Simple Mail Transfer Protocol - Protocolo Simple de Transferencia de Correo):** Es el protocolo estándar para el **envío** de mensajes de correo electrónico. Cuando envías un correo desde tu cliente (Outlook, Thunderbird, Gmail, etc.), este se comunica con el servidor SMTP de tu proveedor de correo para entregar el mensaje. Utiliza los puertos 25 (sin cifrar, obsoleto y a menudo bloqueado), 587 (con STARTTLS, el más recomendado), 465 (con SSL/TLS, también seguro y común)
- Entrante: 
	- **POP3 (Post Office Protocol version 3 - Protocolo de Oficina de Correo versión 3):** Este protocolo descarga los correos electrónicos del servidor a tu dispositivo local y, por defecto, los elimina del servidor. Esto significa que si accedes a tu correo desde otro dispositivo, esos mensajes ya no estarán en el servidor. Puertos comunes: 110 (sin cifrar), 995 (con SSL/TLS).
	- **IMAP (Internet Message Access Protocol - Protocolo de Acceso a Mensajes de Internet):** Este protocolo permite acceder y gestionar los correos directamente en el servidor. Los mensajes permanecen en el servidor, lo que te permite sincronizar y acceder a tu correo desde múltiples dispositivos (ordenador, tablet, smartphone) y ver los cambios reflejados en todos ellos. Puertos comunes: 143 (sin cifrar), 993 (con SSL/TLS)
servidor-->servidor
	**SMTP (Simple Mail Transfer Protocol - Protocolo Simple de Transferencia de Correo):** SMTP es también el protocolo principal utilizado para la transferencia de mensajes de correo electrónico entre diferentes servidores de correo. Cuando envías un correo, tu servidor SMTP se conecta con el servidor SMTP del destinatario para entregar el mensaje. Este proceso se conoce como "retransmisión de correo" o "relay de SMTP".
	
