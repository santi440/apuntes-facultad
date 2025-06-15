Utilizando la VM y teniendo en cuenta los siguientes datos, abra el cliente de correo (Thunderbird) y configure dos cuentas de correo. Una de las cuentas utilizará POP para solicitar al servidor los mails recibidos para la misma mientras que la otra utilizará IMAP.
Al crear cada una de las cuentas, seleccionar Manual config y luego de configurar las mismas según lo indicado, ignorar advertencias por uso de conexión sin cifrado.
● Datos para POP
	Cuenta de correo: alumnopop@redes.unlp.edu.ar
	Nombre de usuario: alumnopop
	Contraseña: alumnopoppass
	Puerto: 110
● Datos para IMAP
	Cuenta de correo: alumnoimap@redes.unlp.edu.ar
	Nombre de usuario: alumnoimap
	Contraseña: alumnoimappass
	Puerto: 143
● Datos comunes para ambas cuentas
	Servidor de correo entrante (POP/IMAP):
	• Nombre: mail.redes.unlp.edu.ar
	• SSL: None
	• Autenticación: Normal password
	Servidor de correo saliente (SMTP):
	• Nombre: mail.redes.unlp.edu.ar
	• Puerto: 25
	• SSL: None
	• Autenticación: Normal password
a. Verificar el correcto funcionamiento enviando un email desde el cliente de
una cuenta a la otra y luego desde la otra responder el mail hacia la primera.
Desde Pop a imap: ![[cami llorona.png]]
Desde Imap a Pop: 
![[imapApop.png]]
b. Análisis del protocolo SMTP
i. Utilizando Wireshark, capture el tráfico de red contra el servidor de correo mientras desde la cuenta alumnopop@redes.unlp.edu.ar envía un correo a alumnoimap@redes.unlp.edu.ar

ii. Utilice el filtro SMTP para observar los paquetes del protocolo SMTP
en la captura generada y analice el intercambio de dicho protocolo entre el cliente y el servidor para observar los distintos comandos utilizados y su correspondiente respuesta. Ayuda: filtre por protocolo	SMTP y sobre alguna de las líneas del intercambio haga click derecho y seleccione Follow TCP Stream. . .
![[smtp.png]]
![[filtrado con tcp.png]]
c. Usando el cliente de correo Thunderbird del usuario alumnopop@redes.unlp.edu.ar envíe un correo electrónico alumnoimap@redes.unlp.edu.ar el cual debe tener: un asunto, datos en el body y una imagen adjunta.
![[punto3c.png]]
i. Verifique las fuentes del correo recibido para entender cómo se utiliza el header “Content-Type: multipart/mixed“ para poder realizar el envío de distintos archivos adjuntos.

`Content-Type: multipart/mixed` es un header en correos electrónicos que indica que el mensaje contiene múltiples partes independientes (texto, HTML, archivos adjuntos), cada una con su propio tipo de contenido.
`multipart`, en términos generales, indica que un mensaje o una petición de datos está dividida en múltiples partes, cada una con su propia información de tipo de contenido. Facilita la inclusión de diferentes tipos de datos en una sola transmisión.

ii. Extraiga la imagen adjunta del mismo modo que lo hace el cliente de	correo a partir de los fuentes del mensaje.
Pasos para extraer la imagen adjunta:

**1.** Obtener el código fuente del correo.

**2.** Analizar el encabezado `Content-Type`: Buscar en el encabezado `multipart/mixed`, donde debería estar contenido un parámetro `boundary` para separar las diferentes partes del mensaje.

**3.** Dividir el mensaje en partes: Utilizando la cadena `boundary` para dividir el cuerpo del mensaje en sus diferentes secciones. Cada sección está delimitada por `--bounday` al principio y `--boundary--` al final del mensaje.

**4.** Identificar la parte de la imagen: Recorrer cada una de las partes y examinar sus encabezados:

- Su encabezado `Content-Type` debería indicar el tipo de imagen, ej. `image/jpeg`.
- Su encabezado `Content-Disposition` probablemente será `attachment` y podría incluir un `filename` con el nombre de la imagen. También podría tener un `Content-ID` si se referenia en el cuerpo HTML del correo.

**5.** Extraer el cuerpo de la parte de la imagen: Una vez que se encuentra la parte correspondiente a la imagen, el contenido de esa parte contendrá los binarios de la imagen codificados.

**6.** Decodificar los datos de la imagen (si es necesario): Dependiendo de cómo se haya codificado la imagen para su transmisión en el correo (lo más común es base64), es posible que se necesite decodificar estos datos binarios a su formato original para obtener el archivo de imagen. Se debe buscar un encabezado `Content-Transfer-Encoding: base64` dentro de la parte de la imagen. Si está presente, se deberá decodificar el cuerpo de esa parte utilizando un decodificador base64.

**7.** Guardar la imagen en un archivo: Guardar los binarios decodificados en un archivo con la extensión correcta.

![[wireshark mensaje foto.png]]
click derecho> follow> tcp stream> al final menciona el tipo de codificación y la imagen (muchos caracteres alfanumericos con =, -, +, y /). Lo copie en un archivo imagen.txt (si es con libreoffice por algun motivo no me andaba pero con nano si)
```c
base64 -d imagen.txt > perrito.jpg
```

