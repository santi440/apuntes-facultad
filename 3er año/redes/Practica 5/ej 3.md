¿Cuál es el objetivo del uso de puertos en el modelo TCP/IP?
El objetivo fundamental del uso de puertos en el modelo TCP/IP es permitir que múltiples aplicaciones o servicios que se ejecutan en un mismo host (computadora o servidor) puedan comunicarse simultáneamente a través de la red de manera organizada y sin conflictos.

**Multiplexación y Demultiplexación de Aplicaciones:**
- Multiplexación: Un solo dispositivo (host) puede tener varias aplicaciones activas al mismo tiempo (por ejemplo, un navegador web, un cliente de correo electrónico, un programa de descarga, un servidor web, etc.). Todas estas aplicaciones pueden necesitar enviar o recibir datos a través de la misma conexión de red. Los puertos permiten que los datos de estas diferentes aplicaciones compartan la misma interfaz de red y la misma dirección IP.
- Demultiplexación: Cuando un paquete de datos llega a un host, la capa de transporte (TCP o UDP) necesita saber a qué aplicación específica están destinados esos datos. El número de puerto en la cabecera del segmento TCP o el datagrama UDP actúa como una "dirección" interna para esa aplicación.
**Identificación Unívoca de Servicios/Procesos:**
Cada aplicación o servicio que necesita comunicarse a través de la red "escucha" en un número de puerto específico. Por ejemplo:
- El tráfico web (HTTP) generalmente utiliza el puerto 80.
- El tráfico web seguro (HTTPS) utiliza el puerto 443.
- El correo electrónico (SMTP) utiliza el puerto 25.
- La resolución de nombres de dominio (DNS) utiliza el puerto 53.
Esto permite que un servidor ofrezca múltiples servicios (por ejemplo, un servidor web y un servidor FTP) en la misma dirección IP, pero en diferentes puertos. Cuando un cliente desea acceder a un servicio específico, especifica la dirección IP del servidor y el número de puerto asociado a ese servicio.
**Encapsulación y Desencapsulación en la Capa de Transporte:**
- Cuando una aplicación quiere enviar datos, la capa de transporte añade la cabecera TCP o UDP, que incluye el puerto de origen y el puerto de destino. Esto se conoce como encapsulación.
- Cuando los datos llegan al host de destino, la capa de transporte lee los números de puerto en la cabecera (esto es parte de la desencapsulación) y entrega los datos al proceso o aplicación correcto que está "escuchando" en ese puerto.

**Colisiones Evitadas**:
Sin puertos, si varios programas intentaran comunicarse a través de la misma dirección IP al mismo tiempo, los datos de un programa podrían mezclarse o ser entregados al programa incorrecto. Los puertos resuelven esta ambigüedad al proporcionar un mecanismo de direccionamiento a nivel de aplicación.