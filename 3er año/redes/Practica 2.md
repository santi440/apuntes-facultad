1. ¿Cuál es la función de la capa de aplicación?
	La capa de aplicación es la interfaz entre el usuario y los dispositivos de red. Los protocolos definidos en esta capa son utilizados tanto por el remitente como por el destinatario y permite la comunicación entre programas de aplicación y el usuario final
2. Si dos procesos deben comunicarse:
	a. ¿Cómo podrían hacerlo si están en diferentes máquinas?
	 **Procesos en Diferentes Máquinas**
	Cuando los procesos están en distintos dispositivos, la comunicación debe realizarse a través de la red. Algunas opciones son:
	-  **Sockets (TCP/UDP)**: Los procesos pueden comunicarse utilizando sockets sobre TCP (orientado a conexión) o UDP (sin conexión). Es la opción más flexible y común para aplicaciones distribuidas. 
	- **HTTP/REST APIs**: Un proceso puede exponer una API RESTful que el otro proceso consulta mediante peticiones HTTP. Se usa mucho en sistemas web y microservicios.
	- **gRPC**: Protocolo basado en HTTP/2 que permite comunicación eficiente entre servicios, con soporte para streaming y serialización con Protocol Buffers.
	- **Mensajería (RabbitMQ, Kafka, ZeroMQ, MQTT)**: Utiliza un intermediario (message broker) que permite a los procesos intercambiar mensajes de manera asíncrona.
	- **Compartición de archivos/NFS**: Si ambos procesos pueden acceder a un sistema de archivos compartido (NFS, Samba), pueden leer y escribir archivos como mecanismo de comunicación.
	- **Bases de datos**: Ambos procesos pueden escribir y leer información en una base de datos compartida, aunque puede no ser eficiente para comunicación en tiempo real.
	
	b. Y si están en la misma máquina, ¿qué alternativas existen?
	- **Pipes (Pipes con nombre y anónimos)**: Se usan para comunicación entre procesos mediante flujos de datos, aunque funcionan mejor cuando hay una relación padre-hijo entre procesos.
	- **Sockets Unix (Domain Sockets)**: Similar a los sockets TCP, pero optimizados para comunicación en la misma máquina.
	- **Memoria compartida (Shared Memory, mmap, shmget)**: Permite compartir un espacio de memoria entre procesos, lo que es extremadamente rápido pero requiere sincronización.
    - **Mensajes y colas de mensajes (Message Queues, POSIX Message Queues, System V Queues)**: Los procesos pueden intercambiar datos usando colas gestionadas por el sistema operativo.
    - **Semáforos (Semaphores)**: Útiles para sincronización y para intercambiar pequeñas cantidades de información.
    - **Señales (Signals)**: Mecanismo ligero de comunicación para notificar eventos entre procesos.
    - **Archivos temporales o base de datos local**: Similar al enfoque en red, pero más lento que otros métodos en la misma máquina.

3. Explique brevemente cómo es el modelo Cliente/Servidor. Dé un ejemplo de un sistema
Cliente/Servidor en la “vida cotidiana” y un ejemplo de un sistema informático que siga el
modelo Cliente/Servidor. ¿Conoce algún otro modelo de comunicación?
	El **modelo Cliente/Servidor** es un paradigma de comunicación en redes donde un dispositivo (cliente) solicita servicios o recursos a otro dispositivo (servidor), el cual procesa la solicitud y envía una respuesta. Este modelo permite la centralización de recursos y la gestión eficiente del tráfico en una red. Por ejemplo un cliente de correo que envia y recibe correos de un servidor. Otros modelos conocidos son: Peer-to-Peer (P2P),  Publicación-Suscripción (Pub/Sub), Modelo de Mensajería (Message Passing) y Arquitectura basada en Microservicios, etc
4. Describa la funcionalidad de la entidad genérica “Agente de usuario” o “User agent”.
		El **User Agent** es una entidad en la capa de aplicación que actúa como intermediario entre el usuario y la red, permitiéndole interactuar con los servicios y aplicaciones disponibles en Internet. Es responsable de generar y enviar solicitudes a los servidores en nombre del usuario. Aplicacion que hace el pedido. 
	### **Funcionalidad del User Agent**
	- **Interpretar y generar solicitudes:** Envía peticiones a servidores web, servidores de correo u otros servicios en red.
    
	- **Procesar respuestas:** Recibe datos desde el servidor y los presenta al usuario en un formato adecuado.
    
	- **Gestionar la comunicación:** Puede manejar sesiones, almacenar cookies y seguir redirecciones según lo requiera la aplicación.
    
	### **Ejemplos de User Agents**
	
	- **Navegadores web:** Chrome, Firefox y Edge actúan como User Agents cuando solicitan páginas web a un servidor.
	    
	- **Clientes de correo:** Outlook y Thunderbird funcionan como User Agents para enviar y recibir correos electrónicos a través de protocolos como SMTP, IMAP o POP3.
	    
	- **Bots y scrapers web:** Googlebot es un User Agent que rastrea sitios web para indexarlos en el motor de búsqueda.
5. ¿Qué son y en qué se diferencian HTML y HTTP?
	- **HTML (HyperText Markup Language):** Es un **lenguaje de marcado** que define la estructura y contenido de las páginas web mediante etiquetas. Permite organizar textos, imágenes, enlaces y otros elementos visuales.
    
	- **HTTP (HyperText Transfer Protocol):** Es un **protocolo de comunicación** que permite la transferencia de información entre clientes (como navegadores web) y servidores. Define cómo se envían y reciben los datos en la web.
	
	Diferencias: HTTP es un protocolo de red y HTML es un lenguaje de marcado, el primero es usado cuando se solicita un elemento a un servidor y el segundo va a ser parte del contenido que devuelva este en su respuesta pero no tienen nada que ver entre si. 
6. HTTP tiene definido un formato de mensaje para los requerimientos y las respuestas.
(Ayuda: apartado “Formato de mensaje HTTP”, Kurose).
	a. ¿Qué información de la capa de aplicación nos indica si un mensaje es de requerimiento o de respuesta para HTTP? ¿Cómo está compuesta dicha información?¿Para qué sirven las cabeceras?
	Las solicitudes (request) tienen una linea de solicitud y las respuestas (response) tienen una linea de estado. Por ejemplo una linea de solicitud podria ser:
	
```
GET /unadireccion/pagina.html HTTP/1.1
```

Mientras que una linea de estado seria:
```
HTTP/1.1 200 OK
```

La línea de solicitud consta de tres campos: elcampo de método, el campo URL y el campo de la versión HTTP.
![[solicitud http.png]]
La línea de estado contiene tres campos: el que especifica la versión del protocolo, el correspondiente al código de estado y el tercero que contiene el mensaje explicativo del estado correspondiente. 
![[respuesta htpp.png]]
Las **cabeceras HTTP** son metadatos incluidos en las solicitudes y respuestas HTTP. Sirven para proporcionar información adicional sobre la comunicación entre cliente y servidor. Varian de acuerdo a la version de HTTP usada y las hay:
**Cabeceras de solicitud →  Enviadas por el cliente al servidor para indicar cómo desea recibir la respuesta.
- `User-Agent`: Indica el navegador o aplicación que hace la solicitud.
        
- `Accept`: Especifica los formatos de respuesta aceptados (ej. `text/html`, `application/json`).
- `Authorization`: Envía credenciales para autenticación.
        
**Cabeceras de respuesta** → Enviadas por el servidor para dar información sobre la respuesta.
    
    - `Content-Type`: Indica el tipo de contenido enviado (ej. `text/html`, `application/json`).
        
    - `Set-Cookie`: Permite al servidor enviar cookies al cliente.
        
    - `Cache-Control`: Define directivas de almacenamiento en caché.
        
**Cabeceras generales** → Aplican tanto a solicitudes como respuestas.
    
    - `Date`: Indica la fecha y hora en que se generó la respuesta.
        
    - `Connection`: Controla si la conexión debe mantenerse abierta (`keep-alive`) o cerrarse (`close`).
b. ¿Cuál es su formato? (Ayuda: https://developer.mozilla.org/es/docs/Web/HTTP/Headers)
	Una cabecera de petición esta compuesta por su nombre (no sensible a las mayusculas) seguido de dos puntos '`:`', y a continuación su valor (sin saltos de línea). Formato clave:valor
c. Suponga que desea enviar un requerimiento con la versión de HTTP 1.1 desde curl/7.74.0 a un sitio de ejemplo como www.misitio.com para obtener el recurso /index.html. En base a lo indicado, ¿qué información debería enviarse mediante encabezados? Indique cómo quedaría el requerimiento
- **Host:** En HTTP/1.1 es obligatorio especificar el dominio (`Host: www.misitio.com`).
    
- **User-Agent:** Identifica el cliente que realiza la solicitud (`User-Agent: curl/7.74.0`).
    
- **Accept:** Indica qué tipos de contenido acepta el cliente (`Accept: */*` para aceptar cualquier tipo).
    
- **Connection:** Puede ser `keep-alive` o `close`, según si queremos mantener la conexión abierta.
La solicitud quedaria como: 
```
GET /index.html HTTP/1.1  
Host: www.misitio.com  
User-Agent: curl/7.74.0  
Accept: */*  
Connection: keep-alive  

```
7. Utilizando la VM, abra una terminal e investigue sobre el comando curl. Analice para qué sirven los siguientes parámetros (-I, -H, -X, -s).
`curl` (_Client URL_) es una herramienta de línea de comandos que permite realizar solicitudes a servidores web mediante diferentes protocolos, como HTTP, HTTPS, FTP, entre otros. Se usa para obtener recursos, enviar datos y probar APIs.
- -I : Realiza una solicitud **HEAD**, obteniendo solo las cabeceras de la respuesta sin descargar el contenido.
- -H : Permite especificar **cabeceras personalizadas** en la solicitud. Se usa para modificar el `User-Agent`, enviar `Authorization` u otros encabezados. Por ejemplo : curl -H "User-Agent: curl/7.74.0" https://www.ejemplo.com
- -X : Define el **método HTTP** a utilizar (GET, POST, PUT, DELETE, etc.). Si no se especifica, `curl` usa `GET` por defecto.
- -S : Activa el **modo silencioso**, ocultando la barra de progreso y mensajes de error, útil para scripts automatizados.
8. Ejecute el comando curl sin ningún parámetro adicional y acceda a www.redes.unlp.edu.ar. Luego responda:
	a. ¿Cuántos requerimientos realizó y qué recibió? Pruebe redirigiendo la salida (>) del comando curl a un archivo con extensión html y abrirlo con un  navegador.
		Realizo un solo requerimiento y recibio una respuesta 200 ok con un  text/html. Al redireccionar se ve que incluso los archivos estan referenciados pero no se recuperaron del servidor, se ve una pagina sin los estilo css, con hipervinculos a la nada, etc.
	b. ¿Cómo funcionan los atributos href de los tags link e img en html?
		Los Tags link e img funcionan debido a que en href se les envia una ruta donde se encuentran almacenados dichos archivos o links a subpaginas.
	c. Para visualizar la página completa con imágenes como en un navegador, ¿alcanza con realizar un único requerimiento?
		No, hace faltar realizar tantos requerimientos como imagenes/archivos se especifiquen en el HTML + el del propio HTML
	d. ¿Cuántos requerimientos serían necesarios para obtener una página que
tiene dos CSS, dos Javascript y tres imágenes? Diferencie cómo funcionaría
un navegador respecto al comando curl ejecutado previamente.
	Serian necesarios 8 requerimientos uno por cada elemento, incluido la propia pagina HTML. Una web se diferencia de curl debido a que extrae el contenido directamente y realiza las solicitudes automaticamente, en cambio en curl todo debe hacerse manualmente.
9. Ejecute a continuación los siguientes comandos:
	curl -v -s www.redes.unlp.edu.ar > /dev/null
	curl -I -v -s www.redes.unlp.edu.ar
a. ¿Qué diferencias nota entre cada uno?
	La principal diferencia entre los comando `curl -v -s www.redes.unlp.edu.ar > /dev/null` y `curl -I -v -s www.redes.unlp.edu.ar` es que en la primera realiza un GET y la segunda realiza un HEAD , otra diferencia es es el estilo del header en la segunda se repiten algunso campos donde se muestra el contenido mejor ordenado.
b. ¿Qué ocurre si en el primer comando se quita la redirección a /dev/null? ¿Por
qué no es necesaria en el segundo comando?
	Se muestra el contenido del HTML debido a que se esta utilizando la salida estándar por defecto y no redireccionando a la salida null 
c. ¿Cuántas cabeceras viajaron en el requerimiento? ¿Y en la respuesta?
	Viajaron 3 cabeceras de requerimiento y 7 de respuesta. 
10. ¿Qué indica la cabecera Date?
	 indica la hora y la fecha en la que se creó la respuesta HTTP y fue enviada por el servidor. Observe que no especifica la hora en que el objeto fue creado o modificado por última vez; es la hora en la que el servidor recupera el objeto de su sistema de archivos, inserta el objeto en el mensaje de respuesta y lo envía.
11. En HTTP/1.0, ¿cómo sabe el cliente que ya recibió todo el objeto solicitado de manera
completa? ¿Y en HTTP/1.1?
	En HTTP/1.0 el cliente asume que recibió todo el objeto cuando el servidor cierra la conexión, si se desean otros elementos se debe abir otra conexión TCP.
	En HTTP/1.1 se introdujo el header `Content-Lenght` que especifica el tamaño del objeto, cuando se recibe ese tamaño finalizó el objeto y se mantiene la conexión TCP para los siguientes. Estas solicitudes de objetos pueden realizarse una tras otra sin esperar a obtener las respuestas a las solicitudes pendientes (pipelining, procesamiento en cadena). Normalmente, el servidor HTTP cierra una conexión cuando no se ha utilizado durante cierto tiempo (un intervalo de fin de temporización configurable)
12. Investigue los distintos tipos de códigos de retorno de un servidor web y su significado. Considere que los mismos se clasifican en categorías (2XX, 3XX, 4XX, 5XX).
![[status code http.png]]
13. Utilizando curl, realice un requerimiento con el método HEAD al sitio
www.redes.unlp.edu.ar e indique:
a. ¿Qué información brinda la primera línea de la respuesta? La versión de HTTP, la 1.1, y el status code de la operación 200 OK de que salió todo bien.
b. ¿Cuántos encabezados muestra la respuesta? 7 encabezados 
c. ¿Qué servidor web está sirviendo la página? Apache
d. ¿El acceso a la página solicitada fue exitoso o no? Si 
e. ¿Cuándo fue la última vez que se modificó la página? El domingo 19 de marzo de 2023
f. Solicite la página nuevamente con curl usando GET, pero esta vez indique que
quiere obtenerla sólo si la misma fue modificada en una fecha posterior a la que
efectivamente fue modificada. ¿Cómo lo hace? ¿Qué resultado obtuvo? ¿Puede
explicar para qué sirve? 

Es posible realizarlo con el comando `curl www.redes.unlp.edu.ar -H If-Modified-Since:'Mon, 20 Mar 2023 13:02:41 GMT' -v`. El resultado fue un codigo 304 Not modified. Con `-H If-Modified-Since:<fecha>` estoy agregando una cabecera adicional para que me lo devuelva solo si la fecha de modificado es desde o posterior a la ingresada. Esto permite un mejor manejo de recursos, sincronización eficaz y posibilidad de mantener un cache.
14. Utilizando curl, acceda al sitio www.redes.unlp.edu.ar/restringido/index.php y siga las
instrucciones y las pistas que vaya recibiendo hasta obtener la respuesta final. Será de
utilidad para resolver este ejercicio poder analizar tanto el contenido de cada página como
los encabezados.
![[P2 ej14 practicando autenticacion.png]]
Usar Authorization para acceder con usuario y contraseña (-u para abreviar directamente):
![[uso de authorization.png]]
Ingresando al link de Location:
![[the-end.php.png]]
15. Utilizando la VM, realice las siguientes pruebas:
a. Ejecute el comando ’curl www.redes.unlp.edu.ar/extras/prueba-http-1-0.txt’ y
copie la salida completa (incluyendo los dos saltos de línea del final).
```
GET /http/HTTP-1.1/ HTTP/1.0
User-Agent: curl/7.38.0
Host: www.redes.unlp.edu.ar
Accept: */*


```
b. Desde la consola ejecute el comando telnet www.redes.unlp.edu.ar 80 y
luego pegue el contenido que tiene almacenado en el portapapeles. ¿Qué
ocurre luego de hacerlo?
	La peticion HTTP se ejecuta, retorna el HTML correspondiente y se cierra automaticamente la conexión.
c. Repita el proceso anterior, pero copiando la salida del recurso /extras/prueba-http-1-1.txt. Verifique que debería poder pegar varias veces el mismo contenido sin tener que ejecutar el comando telnet nuevamente.
	Ocurre lo mismo solo que el servidor no cierra la conexión instantanemente. Se mantiene a la escucha hasta que pasa un tiempo configurable sin recibir peticiones.
16. En base a lo obtenido en el ejercicio anterior, responda:
a. ¿Qué está haciendo al ejecutar el comando telnet?
	Al ejecutarse el comando `telnet` se accede al sitio de `www.redes.unlp.edu.ar` a través del puerto `80` y nos pide que ingresemos un header para ejecutar una pagina.
b. ¿Qué método HTTP utilizó? ¿Qué recurso solicitó?
	El método HTTP que se utilizo fue el `GET` y se solicito el recurso html de la sección `/http/HTTP-1.1` de la pagina de `www.redes.unlp.edu.ar`
c. ¿Qué diferencias notó entre los dos casos? ¿Puede explicar por qué?
	El contenido almacenado en el HTML es el mismo la diferencia principal es como se genera el cierre de conexión al servidor y la version de HTTP que debería utilizar la conexión.
d. ¿Cuál de los dos casos le parece más eficiente? Piense en el ejercicio donde analizó la cantidad de requerimientos necesarios para obtener una página con estilos, javascripts e imágenes. El caso elegido, ¿puede traer asociado algún problema?
	Me parece más eficiente la segunda opción debido a que la conexión HTTP maneja también la carga de imágenes, estilos y scripts dentro de la misma conexión, no se requieren de mas de una conexión para obtener todos los requerimientos. De esta forma no hace falta sobrecargar el servidor abriendo y cerrando conexiones TCP constantemente. 
17. En el siguiente ejercicio veremos la diferencia entre los métodos POST y GET. Para ello, será necesario utilizar la VM y la herramienta Wireshark. Antes de iniciar considere:
	■ Capture los paquetes utilizando la interfaz con IP 172.28.0.1. (Menú “Capture -> Options”. Luego seleccione la interfaz correspondiente y presione Start).
	■ Para que el analizador de red sólo nos muestre los mensajes del protocolo http introduciremos la cadena ‘http’ (sin las comillas) en la ventana de especificación de filtros de visualización (display-filter). Si no hiciéramos esto veríamos todo el tráfico que es capaz de capturar nuestra placa de red. De los paquetes que son capturados, aquel que esté seleccionado será mostrado en forma detallada en la sección que está justo debajo. Como sólo estamos interesados en http ocultaremos toda la información que no es relevante para esta práctica (Información de trama, Ethernet, IP y TCP). Desplegar la información correspondiente al protocolo HTTP bajo la leyenda “Hypertext Transfer Protocol”.
	■ Para borrar la cache del navegador, deberá ir al menú “Herramientas->Borra historial reciente”. Alternativamente puede utilizar Ctrl+F5 en el navegador para forzar la petición HTTP evitando el uso de caché del navegador.
	■ En caso de querer ver de forma simplificada el contenido de una comunicación http, utilice el botón derecho sobre un paquete HTTP perteneciente al flujo capturado y seleccione la opción Follow TCP Stream.
	a. Abra un navegador e ingrese a la URL: www.redes.unlp.edu.ar e ingrese al link en la sección “Capa de Aplicación” llamado “Métodos HTTP”. En la página mostrada se visualizan dos nuevos links llamados: Método GET y Método POST. Ambos muestran un formulario como el siguiente:
b. Analice el código HTML
c. Utilizando el analizador de paquetes Wireshark capture los paquetes enviados y recibidos al presionar el botón Enviar.
GET:
![[metodo get from.png]]
POST:
![[metodo post form.png]]
d. ¿Qué diferencias detectó en los mensajes enviados por el cliente?
	La diferencia es que en el método `GET` se envía los campos del formulario por la URL mientras que en el método `POST` los campos del formularios son enviardos en el  cuerpo de la solicitud
e. ¿Observó alguna diferencia en el browser si se utiliza un mensaje u otro?
	Si, en el metodo `GET` la URL posee todos los campos enviados en el formulario (Muy malo para la seguridad), mientras que el metodo `POST` no se ven los campos en la URL
18. Investigue cuál es el principal uso que se le da a las cabeceras Set-Cookie y Cookie en HTTP y qué relación tienen con el funcionamiento del protocolo HTTP.
	Cuando un servidor responde a una solicitud HTTP, puede enviar una cookie con la cabecera `Set-Cookie`:
```	
HTTP/1.1 200 OK
Set-Cookie: sessionID=abc123; Path=/; HttpOnly; Secure
```
- `sessionID=abc123` → La clave y valor de la cookie.
- `Path=/` → La cookie es válida en todo el sitio.
- `HttpOnly` → No puede ser accedida por JavaScript.
- `Secure` → Solo se enviará si la conexión es HTTPS.
En las siguientes solicitudes, el cliente envía la cookie en la cabecera `Cookie` lo que permite que el servidor identifique la sesión del usuario:
```
GET /dashboard HTTP/1.1
Host: www.misitio.com
Cookie: sessionID=abc123
```
HTTP es **sin estado** (stateless), lo que significa que cada solicitud es independiente. **Las cookies permiten mantener información entre solicitudes**, logrando:  
✅ **Autenticación y sesiones** (por ejemplo, mantener el login).  
✅ **Personalización** (guardar preferencias de usuario).  
✅ **Seguimiento** (análisis de actividad del usuario en un sitio web).
19. ¿Cuál es la diferencia entre un protocolo binario y uno basado en texto? ¿De qué tipo
de protocolo se trata HTTP/1.0, HTTP/1.1 y HTTP/2?
**Protocolo basado en texto**:Utiliza caracteres legibles por humanos (ASCII o UTF-8) resultando más fácil de leer y depurar con herramientas como `telnet` o `curl` pero siendo una gran brecha de seguridad. Ejemplo: HTTP/1.0, HTTP/1.1, SMTP, FTP.
**Protocolo binario**:Usa datos en formato binario (no legible por humanos). Es más eficiente en transmisión y procesamiento. Ejemplo: HTTP/2, HTTPS, SSH, TLS.
20. Responder las siguientes preguntas:
a. ¿Qué función cumple la cabecera Host en HTTP 1.1? ¿Existía en HTTP 1.0? ¿Qué sucede en HTTP/2? (Ayuda:https://undertow.io/blog/2015/04/27/An-in-depth-overview-of-HTTP2.html para HTTP/2) 
	La cabecera `Host` indica el **nombre del servidor al que se dirige la solicitud**.
	Es obligatoria en **HTTP/1.1**, ya que un mismo servidor puede alojar múltiples sitios web mediante **virtual hosting**.
	**En HTTP/1.0**:No existía la cabecera `Host`.Cada dominio debía tener su propia dirección IP.
	**En HTTP/2**:La cabecera `Host` sigue existiendo pero se reemplaza por el **pseudo-header `:authority`**. Se optimiza el envío de datos con compresión de cabeceras (HPACK).
b. En HTTP/1.1, ¿es correcto el siguiente requerimiento?
```
GET /index.php HTTP/1.1
User-Agent: curl/7.54.0
```
No, en HTTP/1.1 es obligatorio que este la cabecera `Host`:
c. ¿Cómo quedaría en HTTP/2 el siguiente pedido realizado en HTTP/1.1 si se está usando https?
GET /index.php HTTP/1.1
Host: www.info.unlp.edu.ar

```
:method: GET
:scheme: https
:authority: www.info.unlp.edu.ar
:path: /index.php

```
**Ejercicio de Parcial**
```
curl -X ?? www.redes.unlp.edu.ar/??
> HEAD /metodos/ HTTP/??
> Host: www.redes.unlp.edu.ar
> User-Agent: curl/7.54.0
< HTTP/?? 200 OK
< Server: nginx/1.4.6 (Ubuntu)
< Date: Wed, 31 Jan 2018 22:22:22 GMT
< Last-Modified: Sat, 20 Jan 2018 13:02:41 GMT
< Content-Type: text/html; charset=UTF-8
< Connection: close
```
a. ¿Qué versión de HTTP podría estar utilizando el servidor? 1.1
b. ¿Qué método está utilizando? Dicho método, ¿retorna el recurso completo
solicitado? HEAD, no retorno solo sus cabeceras pero body vacio
c. ¿Cuál es el recurso solicitado? /metodos/
d. ¿El método funcionó correctamente? si, devolvió un code status 200 OK
e. Si la solicitud hubiera llevado un encabezado que diga:
`If-Modified-Since: Sat, 20 Jan 2018 13:02:41 GMT`
¿Cuál habría sido la respuesta del servidor web? ¿Qué habría hecho el navegador en este caso?
La fecha coincide **exactamente** ,incluso con los milisegundos, el servidor deberia retornar un código 304 not modified. La fecha pasada no es posterior a la ultima modificación. 