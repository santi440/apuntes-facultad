# Práctica 3 - Capa de Aplicación
## DNS (Domain Name Server)
#### Introducción
1. Investigue y describa cómo funciona el DNS. ¿Cuál es su objetivo?
	**DNS (Domain Name System)** es un sistema que traduce nombres de dominio (como `www.google.com`) en direcciones IP (como `142.250.78.68`) que las computadoras utilizan para comunicarse entre sí y ,viceversa, de dirección IP a dominio.
	Permitir que los usuarios usen nombres fáciles de recordar en lugar de tener que memorizar direcciones IP numéricas.
	#### **Funcionamiento básico:**
	1. El usuario escribe una dirección en el navegador (`www.ejemplo.com`).
	2. El sistema operativo pregunta al **resolver** local (generalmente del proveedor de Internet).
	3. Si el resolver no tiene la respuesta, hace una **consulta DNS** que puede pasar por:
	    - **Root server** (servidores raíz)
	    - **Servidores TLD** (como `.com`, `.org`)
	    - **Servidores autoritativos** (que tienen la información definitiva del dominio)
	4. La dirección IP encontrada se devuelve al navegador, que la usa para conectarse al sitio web.
2. ¿Qué es un root server? ¿Qué es un generic top-level domain (gtld)?
	 - **Root Server (Servidor raíz):** Son los servidores DNS más altos en la jerarquía. Responden consultas para los TLDs (como `.com`, `.net`, `.org`). No saben la IP exacta del dominio, pero indican cuál es el servidor TLD adecuado. Actualmente existen 13 roots servers 7 de los cuales trabajan con redundancia y las réplicas están distribuidos geográficamente. //en el grafico seria el "."
	 
	- **gTLD (Generic Top-Level Domain):** Son los **dominios de nivel superior genéricos**, como `.com`, `.org`, `.net`, `.info`, etc. Se encuentran justo debajo de los root servers y almacenan referencias a los **servidores autoritativos** de los dominios que terminan en esas extensiones.
	  ![[DNS jerarquia.png]]
3. ¿Qué es una respuesta del tipo autoritativa?
	Una **respuesta autoritativa** es aquella que proviene de un **servidor DNS que tiene autoridad sobre el dominio** que se consulta. Es decir, es el servidor responsable directo de esa información (no es una copia o caché). 
> [!TIP] Si preguntás por `example.com` y el servidor que responde es el autorizado por el dueño del dominio, esa es una respuesta autoritativa. 
4. ¿Qué diferencia una consulta DNS recursiva de una iterativa?
- **Recursiva:** El **resolver** se encarga de buscar la respuesta completa por vos, preguntando a todos los servidores necesarios hasta obtener la dirección IP final.
- **Iterativa:** El servidor DNS responde con la mejor información que tiene, generalmente la dirección de otro servidor al que podés consultar. Es el cliente quien debe seguir haciendo las preguntas.
> [!Note]
> - **Recursiva** = “Buscame la respuesta y traémela”.
> - **Iterativa** = “No sé la respuesta, pero consultá a este otro"
![[ejemplo con consultas recursivas e iterativas.png]]
5. ¿Qué es el resolver?
   El **resolver DNS** (también llamado **recursor**) es el componente que actúa como intermediario entre el usuario y el sistema DNS. Es quien recibe la consulta de tu dispositivo y se encarga de hacer las búsquedas (recursivas o iterativas) necesarias hasta obtener la dirección IP correspondiente.
   Normalmente es provisto por tu proveedor de internet (ISP) o por servicios como Google DNS (`8.8.8.8`) o Cloudflare (`1.1.1.1`).
6. Describa para qué se utilizan los siguientes tipos de registros de DNS:
	a. A: Asocia un **nombre de dominio** con una **dirección IPv4**. `www.ejemplo.com` → `192.0.2.1`
	b. MX : Define **qué servidores de correo** reciben los emails para un dominio. Incluye una **prioridad** para definir cuál se usa primero.
	c. PTR: Hace lo inverso del registro A: **convierte una IP en un nombre de dominio**. Se usa en **resolución inversa**, por ejemplo para verificar servidores de correo.
	d. AAAA: Igual que A pero ahora con IPv6
	e. SRV: Service, Define **servicios específicos** (como VoIP o LDAP) y los servidores que los soportan, junto con puerto y prioridad. Ejemplo: `_sip._tcp.ejemplo.com`
	f. NS: Indica **cuáles son los servidores DNS autorizados** para un dominio.
	g. CNAME: Son las siglas de Canonical Name. Apunta un dominio a **otro nombre de dominio** en lugar de una IP. Útil para alias. Ejemplo: `blog.ejemplo.com` → `ejemplo.wordpress.com`
	h. SOA: Start of Authority. Es el **registro principal de una zona DNS**. Contiene info como: servidor primario, correo del administrador, número de serie de la zona, y tiempos de refresco.
	i. TXT: Permite almacenar **texto arbitrario** en los registros DNS. Muy usado para verificación de propiedad de dominio, políticas SPF, DKIM, etc
7. En Internet, un dominio suele tener más de un servidor DNS, ¿por qué cree que esto es así?
	- **Redundancia:** Si un servidor falla, los otros pueden responder. 
	- **Disponibilidad:** Aumenta la probabilidad de que el dominio esté accesible siempre.
	- **Distribución geográfica:** Mejora los tiempos de respuesta dependiendo de dónde se haga la consulta.
8. Cuando un dominio cuenta con más de un servidor, uno de ellos es el primario (o
maestro) y todos los demás son secundarios (o esclavos). ¿Cuál es la razón de que sea
así?
- El **servidor primario** es donde se **crea y actualiza** la información DNS.
- Los **servidores secundarios** copian esa info automáticamente.
- Esto permite:
    - **Descentralización**: menos carga en el servidor principal.
    - **Respaldo**: si el primario cae, los secundarios siguen respondiendo.
    - **Sincronización automática**: los secundarios se mantienen al día mediante transferencias de zona.
9. Explique brevemente en qué consiste el mecanismo de transferencia de zona y cuál es
su finalidad.
	La **transferencia de zona (zone transfer)** es el proceso por el cual un servidor DNS **copia la base de datos DNS de una zona desde otro servidor**, usualmente desde el servidor primario a uno secundario. Permite:  
	- Mantener actualizados a los servidores secundarios con la información más reciente del dominio.
	- Asegurar **consistencia y disponibilidad** en todo el sistema DNS.
	Puede ser
	- **AXFR**: transferencia completa de la zona.
	- **IXFR**: solo transfiere los cambios, más eficiente
10. Imagine que usted es el administrador del dominio de DNS de la UNLP (unlp.edu.ar). A
su vez, cada facultad de la UNLP cuenta con un administrador que gestiona su propio
dominio (por ejemplo, en el caso de la Facultad de Informática se trata de info.unlp.edu.ar).
Suponga que se crea una nueva facultad, Facultad de Redes, cuyo dominio será redes.unlp.edu.ar, y el administrador le indica que quiere poder manejar su propio dominio.
¿Qué debe hacer usted para que el administrador de la Facultad de Redes pueda gestionar
el dominio de forma independiente? (Pista: investigue en qué consiste la delegación de dominios). Indicar qué registros de DNS se deberían agregar.
	La delegación de dominio implica transferir la responsabilidad de una zona DNS (en este caso `redes.unlp.edu.ar`) a otro conjunto de servidores DNS.
	Primero el adminstrador del la Facultad de Redes debera configurar sus propios servidores DNS autoritativos. Sea por ejemplo ns1.redes.unlp.edu.ar
	Luego, nosotros como adminstrador del dominio padre (unlp.edu.ar) deberemos agregar la delegacion DNS:
``` java
redes.unlp.edu.ar. IN NS ns1.redes.unlp.edu.ar.
```
De esta manera, el servidor DNS (unlp.edu.ar) funciona como intermediario al servidor autoritativo en el que deben estar los registros A, AAAA, MX, etc que considere necesario el administrador del subdominio. 
El manejo de `redes.unlp.edu.ar` sera totalmente independiente y podra operar por si mismo, aunque la resolución de la IP se realiza comunmente por DNS (pasa por el servidor intermedio si o si).

11. Responda y justifique los siguientes ejercicios.
	a. En la VM, utilice el comando dig para obtener la dirección IP del host www.redes.unlp.edu.ar y responda:
		![[respuesta a dig.png]]
		i. ¿La solicitud fue recursiva? ¿Y la respuesta? ¿Cómo lo sabe?
			Los flags indican:
			- rd: Recursividad deseada, la solicitud se pide que sea recursiva
			- ra: Recursive avalible, el servidor resolvió la petición en forma recursiva
		ii. ¿Puede indicar si se trata de una respuesta autoritativa? ¿Qué significa que lo sea?
			La presencia del flag aa (authority answer) significa que se trata de una respuesta autoritativa, es decir, viene directamente de un servidor que tiene autoridad sobre ese dominio (tiene acceso directo a los **datos originales** del dominio, no los esta reenviando y resultan mas fiables).
		iii. ¿Cuál es la dirección IP del resolver utilizado? ¿Cómo lo sabe?
			172.28.0.50
			Marcada dentro de la seccion de respuesta y marcada como registro A, registro hoja de tipo IPv4
b. ¿Cuáles son los servidores de correo del dominio redes.unlp.edu.ar? ¿Por qué hay más de uno y qué significan los números que aparecen entre MX y el nombre? Si se quiere enviar un correo destinado a redes.unlp.edu.ar, ¿a qué servidor se le entregará? ¿En qué situación se le entregará al otro?
	Los servidores de correo electronico del dominio son `mail2.redes.unlp.edu.ar.` y `mail.redes.unlp.edu.ar.` y existen dos por redundancia y confiabilidad. Esos numeros implican la prioridad, cuanto mas bajo es el numero su prioridad es mayor, prioritariamente se intentara entregar a  `mail.redes.unlp.edu.ar.` y si este servidor no responde (por estar apagado/sobresaturado/otro motivo) se intenta entregar a `mail2.redes.unlp.edu.ar.`
![[registros mx.png]]
c. ¿Cuáles son los servidores de DNS del dominio redes.unlp.edu.ar?
	Si se ejecuta NS me va a decir todos los servidores DNS autoritativos sobre el dominio especificado 
![[respuesta dig NS redes.png]]
d. Repita la consulta anterior cuatro veces más. ¿Qué observa? ¿Puede explicar a qué se debe?
	Cambian tres cosas:
		- orden de seccion de respuesta: Si bien los valores son los mismos en forma "aleatoria" el orden en que aparecen los servidores cambia de acuerdo a la tecnica de "Rotación o aleatorización de registros" (round-robin DNS). Esta tecnica permite distribuir la carga entre múltiples servidores, mejorar el balanceo de tráfico y evitar que siempre se use el primero de la lista
		- Valor Cookie: Se regenera en cada transacción o cada cierto tiempo, por lo que es **esperado que cambie**. Usado para **Autenticar clientes y servidores** sin conexión previa (evita ataques de spoofing), **Reducir ataques de denegación de servicio (DoS)** y evita respuestas **falsas** o alteradas
		- Valor ID: cada vez que hacés una nueva consulta, el cliente (como `dig`) genera un **nuevo ID aleatorio**. Es totalmente normal y necesario para distinguir respuestas válidas de posibles respuestas falsificadas (spoofing).
e. Observe la información que obtuvo al consultar por los servidores de DNS del
dominio. En base a la salida, ¿es posible indicar cuál de ellos es el primario?
	NO, en terminos de NS todos los servidores listados son equivalentes. Para conocer cual es el autoritativo se debe preguntar por el registro SOA de la sig manera 
``` c
	dig SOA	redes.unlp.edu.ar
```
f. Consulte por el registro SOA del dominio y responda.
![[Soa 11e.png]]
	i. ¿Puede ahora determinar cuál es el servidor de DNS primario?
		Si, es aquel que aparece en la quinta columna
	ii. ¿Cuál es el número de serie, qué convención sigue y en qué casos es importante actualizarlo?
		El **serial** se usa para que los servidores **secundarios sepan si deben actualizar la zona** desde el servidor primario.Cada vez que **modificás cualquier parte del archivo de zona DNS** en el servidor primario (por ejemplo, un registro **debés aumentar el número de serie** para que los secundarios sincronicen y no tener inconsistencias entre servidores.
		El numero de serie seria `2020031700`. Y siguiendo la convencion AAAAMMDDnn de acuerdo a :
		- `AAAA`: Año
	    - `MM`: Mes
	    - `DD`: Día
	    - `nn`: número de cambio ese mismo día (por si editás más de una vez en el mismo día)
	    Se corresponde con el 17/03/2020 version 00 de ese dia 
	iii. ¿Qué valor tiene el segundo campo del registro? Investigue para qué se usa y cómo se interpreta el valor.
		Es el campo refresh,  el tiempo que los servidores secundarios esperan antes de consultar al primario para ver si hay cambios en la zona. Si detectan que el serial ha cambiado, entonces descargan la nueva versión. Se interpreta en segundos de modo que 604800 corresponde a una semana.
	iv. ¿Qué valor tiene el TTL de caché negativa y qué significa?
		Es el ultimo valor que devuelve el dig SOA. Cuando un servidor DNS (o resolver) recibe una **respuesta negativa** (por ejemplo: “ese subdominio no existe”), **almacena esa respuesta por ese tiempo**, medido en segundos, en caché. Durante ese tiempo, no se vuelve a consultar al servidor DNS, sino que el resolver responde directamente, siguiendo con el ejemplo “no existe”.
![[Campos comunes en la answer section.png]]
![[extra del SOA campos tecnicos.png]]

g. Indique qué valor tiene el registro TXT para el nombre saludo.redes.unlp.edu.ar. Investigue para qué es usado este registro.
	Un registro TXT te permite almacenar información arbitraria como texto plano. Podria utilizarse para medidas de seguridad y validación.
		- Verificacion de dominio: Para verificar que sos dueño del dominio.
		- **SPF (Sender Policy Framework)**: Sirve para **evitar que otros envíen correos falsos con tu dominio**. Define qué servidores están autorizados a enviar mails en tu nombre.
		- **DKIM (DomainKeys Identified Mail)** :Se usa para firmar digitalmente los correos que envía tu dominio, lo que permite verificar que no fueron alterados.
		- **DMARC (Domain-based Message Authentication, Reporting & Conformance)** Ayuda a reforzar SPF y DKIM, y le dice a otros servidores qué hacer si el mail **falla la verificación** (rechazar, marcar como spam, etc.).
		- **Información adicional o personalizada**:A veces se usan para propósitos internos, como declarar versiones de sistemas, identificadores o configuraciones específicas.
		  En este caso, podriamos clasificarlo como el ultimo escenario: tiene un mensaje interno de "HOLA".
h. Utilizando dig, solicite la transferencia de zona de redes.unlp.edu.ar, analice la salida y responda.
	Para solicitar la transferencia es 
	`dig AXFR redes.unlp.edu.ar @<servidor_dns_autoritativo>`
	Siendo servidor dns autoritativo alguno que sale al hacer un dig ns redes.unlp.edu.ar
	i. ¿Qué significan los números que aparecen antes de la palabra IN?
	¿Cuál es su finalidad?
		Esos numeros representan el TTL **(Time To Live)**, en **segundos**. Indican cuánto tiempo **los resolvers (servidores DNS que consultan)** pueden guardar ese registro en **caché**. Pasado ese tiempo, deben volver a consultar al servidor DNS autoritativo.
	ii. ¿Cuántos registros NS observa? Compare la respuesta con los servidores de DNS del dominio redes.unlp.edu.ar que dio anteriormente. ¿Puede explicar a qué se debe la diferencia y qué significa?
		Hay 4 servidores NS. **La transferencia muestra el contenido completo del archivo de zona** en el servidor primario o secundario, incluyendo **toda la información disponible**, mientras que `dig NS` solo consulta por los registros NS, y puede verse afectado por la **caché del resolver** o por **respuestas parciales**. Muestra **todos los registros de la zona**, incluyendo:
			- Subdominios
			- NS específicos de subzonas delegadas (como `practica.redes.unlp.edu.ar`)
i. Consulte por el registro A de www.redes.unlp.edu.ar y luego por el registro A
de www.practica.redes.unlp.edu.ar. Observe los TTL de ambos. Repita la
operación y compare el valor de los TTL de cada uno respecto de la
respuesta anterior. ¿Puede explicar qué está ocurriendo? (Pista: observar los
flags será de ayuda).
![[ttl bajando no autoritativo.png]]
![[ttl fijo aa.png]]
	La respuesta de `dig www.redes.unlp.edu.ar A` es autoritativa (se puede apreciar gracias al flag `aa`), lo cual significa que la respuesta fue traída directamente de un servidor DNS autoritativo (primario o secundario), por lo que no fue un registro que tenía almacenado en la caché. Por otro lado, al utilizar `dig practicas.redes.unlp.edu.ar A` se puede notar la ausencia del flag `aa`, lo que indica que la respuesta fue traída de caché. Esto se puede confirmar ya que al volver a hacer la solicitud, el TTL (número previo a la palabra `IN`) decrementó, mostrando el tiempo restante hasta que necesite actualizarse en registro en la caché.
j. Consulte por el registro A de www.practica2.redes.unlp.edu.ar. ¿Obtuvo alguna respuesta? Investigue sobre los códigos de respuesta de DNS. ¿Para qué son utilizados los mensajes NXDOMAIN y NOERROR?
	Si, el servidor DNS respondió afirmando que no existe ese dominio en esta zona, se puede determinar por el status: NXDOMAIN.
	- NXDOMAIN (Non-Existent Domain): Significa que el nombre de dominio consultado no existe en la zona. El servidor autoritativo respondió que no hay ningún registro A (ni ningún otro) para www.practica2.redes.unlp.edu.ar. Esto es distinto de que no haya un registro A, ya que está diciendo que ni siquiera existe ese subdominio.
	- NOERROR: La consulta fue exitosa y puede tener datos o estar vacía (ej. no hay registros A, pero el dominio existe).
otros codigos posibles pero menos comunes pueden ser: 
![[another status code DNS.png]]
12. Investigue los comandos nslookup y host. ¿Para qué sirven? Intente con ambos
comandos obtener:
	- nslookup: Utilidad interactiva y también con modo directo para consultas DNS. Permite ver registros A, MX, NS, etc. 
	- host: Herramienta más simple, directa y enfocada exclusivamente en resolver nombres. Ideal para scripting o consultas rápidas.
	● Dirección IP de www.redes.unlp.edu.ar.
		![[nslookup host ip.png]]
	● Servidores de correo del dominio redes.unlp.edu.ar.
		![[nslookup y host mx.png]]
	● Servidores de DNS del dominio redes.unlp.edu.ar.
		![[nslookup y host ns.png]]
13. ¿Qué función cumple en Linux/Unix el archivo /etc/hosts o en Windows el archivo
\WINDOWS\system32\drivers\etc\hosts?
	Es un archivo que cumple una **función fundamental en la resolución de nombres de dominio**, especialmente antes de consultar a un servidor DNS. Es un **archivo de texto plano** que contiene una lista de **asociaciones entre nombres de host y direcciones IP**. Funciona como una pequeña base de datos local para traducir nombres de dominio en direcciones IP sin necesidad de usar un servidor DNS. Permite
		- **Resolver nombres de host de forma local**, sin consultar servidores DNS.
		- **Probar servicios antes de que el dominio real apunte a la IP correcta** (muy útil en desarrollo).
		- **Bloquear sitios web** (por ejemplo: redirigir `facebook.com` a `127.0.0.1`).
		- **Evitar depender del DNS durante pruebas o configuraciones de red internas**.
14. Abra el programa Wireshark para comenzar a capturar el tráfico de red en la interfaz con IP 172.28.0.1. Una vez abierto realice una consulta DNS con el comando dig para averiguar el registro MX de redes.unlp.edu.ar y luego, otra para averiguar los registros NS correspondientes al dominio redes.unlp.edu.ar. Analice la información proporcionada por dig y compárelo con la captura.
	A priori presentan la misma información con un formato diferente. A fin y al cabo, es el mismo trafico de red.
15. Dada la siguiente situación: “Una PC en una red determinada, con acceso a Internet,
utiliza los servicios de DNS de un servidor de la red”. Analice:
a. ¿Qué tipo de consultas (iterativas o recursivas) realiza la PC a su servidor de DNS?
	recursiva, hace la consulta o peticion y espera recibir una única respuesta.
b. ¿Qué tipo de consultas (iterativas o recursivas) realiza el servidor de DNS para resolver requerimientos de usuario como el anterior? ¿A quién le realiza estas consultas?
	Iterativas, primero verifica que lo que busca no se encuentre en la caché, si se encuentra lo devuelve, sino:
	- Consulta con el Root Server por el TLD.
	- Consulta con el TLD por la zona.
	- En caso de haber, consulta por la subzona a la zona (hasta llegar a la zona buscada).
	- Una vez en la zona deseada, trae el registro solicitado.
16. Relacione DNS con HTTP. ¿Se puede navegar si no hay servicio de DNS?
    Poder, se puede, pero tendrías que conocer la dirección IP de absolutamente todos los sitios a los que querés acceder. La realidad es que los usuarios somos humanos, y con la cantidad masiva de páginas en la web, resulta necesario tener una manera más fácil de recordarlas.
    Analogia copada(?): DNS es un sistema de resolución de nombres que actúa como una "guía telefónica" de Internet, permitiendo traducir nombres legibles como [www.google.com](http://www.google.com) en direcciones IP numéricas que las computadoras entienden.
17. Observar el siguiente gráfico y contestar:
    ![[grafico de consulta17.png]]
	a. Si la PC-A, que usa como servidor de DNS a "DNS Server", desea obtener la
	IP de www.unlp.edu.ar, cuáles serían, y en qué orden, los pasos que se
	ejecutarán para obtener la respuesta.
	1. PC-A intenta resolver con su archivo local
	2. Solicita al DNS Server la direccion IP y este la busca en su cache
	3. El DNS Server comienza descomponiendo el dominio desde el punto invisible al principio que identifica los root servers DNS. El DNS Server le pide a A.Root-Server la direccion del TLD `.ar`
	4. El DNS Server le pide a a.dns.ar la direccion de la zona `.edu.ar`
	5. El DNS Server le pide a ns1.riu.edu.ar la direccion de  la subzona `unlp.edu.ar`
	6. Con todo ese recorrido se solicita el registro A al servidor unlp.unlp.edu.ar, la devuelve a DNS server y este se la devuelve a PC-A
	b. ¿Dónde es recursiva la consulta? ¿Y dónde iterativa?
		La consulta recursiva se la hace PC-A al DNS Server, las consultas iterativas las realiza DNS Server a todos los servidores necesarios para llegar a la respuesta.
18. ¿A quién debería consultar para que la respuesta sobre www.google.com sea
autoritativa?
	Hay que conocer quienes son los Servidores Autoritativos de `www.google.com` utilizando `dig NS google.com`. Nos responde con:
```
; <<>> DiG 9.18.30-0ubuntu0.24.04.2-Ubuntu <<>> NS google.com
;; global options: +cmd
;; Got answer:
;; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 16424
;; flags: qr rd ra; QUERY: 1, ANSWER: 4, AUTHORITY: 0, ADDITIONAL: 9

;; OPT PSEUDOSECTION:
; EDNS: version: 0, flags:; udp: 65494
;; QUESTION SECTION:
;google.com.			IN	NS

;; ANSWER SECTION:
google.com.		85650	IN	NS	ns3.google.com.
google.com.		85650	IN	NS	ns4.google.com.
google.com.		85650	IN	NS	ns1.google.com.
google.com.		85650	IN	NS	ns2.google.com.

;; ADDITIONAL SECTION:
ns3.google.com.		85650	IN	A	216.239.36.10
ns3.google.com.		85650	IN	AAAA	2001:4860:4802:36::a
ns4.google.com.		85650	IN	A	216.239.38.10
ns4.google.com.		85650	IN	AAAA	2001:4860:4802:38::a
ns1.google.com.		85650	IN	A	216.239.32.10
ns1.google.com.		85650	IN	AAAA	2001:4860:4802:32::a
ns2.google.com.		86276	IN	A	216.239.34.10
ns2.google.com.		85650	IN	AAAA	2001:4860:4802:34::a

;; Query time: 12 msec
;; SERVER: 127.0.0.53#53(127.0.0.53) (UDP)
;; WHEN: Fri Apr 18 16:17:05 -03 2025
;; MSG SIZE  rcvd: 287

```
Podria ser a cualquiera de los NS mencionados ahi realizamos la consulta `dig @ns1.google.com google.com`, le pedimos a ns1.google.com que me la IP de google.com y en la respuesta se ve el flag de que fue autoritativo aa
```
; <<>> DiG 9.18.30-0ubuntu0.24.04.2-Ubuntu <<>> @ns1.google.com google.com
; (2 servers found)
;; global options: +cmd
;; Got answer:
;; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 28993
;; flags: qr aa rd; QUERY: 1, ANSWER: 1, AUTHORITY: 0, ADDITIONAL: 1
;; WARNING: recursion requested but not available

;; OPT PSEUDOSECTION:
; EDNS: version: 0, flags:; udp: 512
;; QUESTION SECTION:
;google.com.			IN	A

;; ANSWER SECTION:
google.com.		300	IN	A	142.251.134.78

;; Query time: 32 msec
;; SERVER: 216.239.32.10#53(ns1.google.com) (UDP)
;; WHEN: Fri Apr 18 16:24:54 -03 2025
;; MSG SIZE  rcvd: 55
```
19. ¿Qué sucede si al servidor elegido en el paso anterior se lo consulta por www.info.unlp.edu.ar? ¿Y si la consulta es al servidor 8.8.8.8?
    Si le pregunto a un servidor autoritativo de google por otro dominio me va a dar una respuesta poco util o que no tiene esa informacion. En este caso, arrojo un codigo de REFUSED.
    Si se lo consulto a 8.8.8.8, se encargara de realizar todas las respuestas recursivas necesarias hasta devolver una respuesta 
```
~$ dig @ns1.google.com www.info.unlp.edu.ar

; <<>> DiG 9.18.30-0ubuntu0.24.04.2-Ubuntu <<>> @ns1.google.com www.info.unlp.edu.ar
; (2 servers found)
;; global options: +cmd
;; Got answer:
;; ->>HEADER<<- opcode: QUERY, status: REFUSED, id: 39028
;; flags: qr rd; QUERY: 1, ANSWER: 0, AUTHORITY: 0, ADDITIONAL: 1
;; WARNING: recursion requested but not available

;; OPT PSEUDOSECTION:
; EDNS: version: 0, flags:; udp: 512
;; QUESTION SECTION:
;www.info.unlp.edu.ar.		IN	A

;; Query time: 30 msec
;; SERVER: 216.239.32.10#53(ns1.google.com) (UDP)
;; WHEN: Fri Apr 18 16:28:20 -03 2025
;; MSG SIZE  rcvd: 49

~$ dig @8.8.8.8 www.info.unlp.edu.ar 
; <<>> DiG 9.18.30-0ubuntu0.24.04.2-Ubuntu <<>> @8.8.8.8 www.info.unlp.edu.ar
; (1 server found)
;; global options: +cmd
;; Got answer:
;; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 31201
;; flags: qr rd ra; QUERY: 1, ANSWER: 1, AUTHORITY: 0, ADDITIONAL: 1

;; OPT PSEUDOSECTION:
; EDNS: version: 0, flags:; udp: 512
;; QUESTION SECTION:
;www.info.unlp.edu.ar.		IN	A

;; ANSWER SECTION:
www.info.unlp.edu.ar.	300	IN	A	163.10.5.71

;; Query time: 63 msec
;; SERVER: 8.8.8.8#53(8.8.8.8) (UDP)
;; WHEN: Fri Apr 18 16:28:05 -03 2025
;; MSG SIZE  rcvd: 65

```
Ejercicio de parcial
20. En base a la siguiente salida de dig, conteste las consignas. Justifique en todos los casos.
;; flags: qr rd ra; QUERY: 1, ANSWER: 2, AUTHORITY: 4, ADDITIONAL: 4
;; QUESTION SECTION:
;ejemplo.com. IN __
;; ANSWER SECTION:
ejemplo.com. 1634 IN __ 10 srv01.ejemplo.com. (1)
ejemplo.com. 1634 IN __ 5 srv00.ejemplo.com. (2)
;; AUTHORITY SECTION:
ejemplo.com. 92354 IN __ ss00.ejemplo.com.
ejemplo.com. 92354 IN __ ss02.ejemplo.com.
ejemplo.com. 92354 IN __ ss01.ejemplo.com.
ejemplo.com. 92354 IN __ ss03.ejemplo.com.
;; ADDITIONAL SECTION:
srv01.ejemplo.com. 272 IN __ 64.233.186.26
srv01.ejemplo.com. 240 IN __ 2800:3f0:4003:c00::1a
srv00.ejemplo.com. 272 IN __ 74.125.133.26
srv00.ejemplo.com. 240 IN __ 2a00:1450:400c:c07::1b
a. Complete las líneas donde aparece __ con el registro correcto.
- MX
- MX
- MX
- NS
- NS
- NS
- NS
- A
- AAAA
- A
- AAAA
b. ¿Es una respuesta autoritativa? En caso de no serlo, ¿a qué servidor le preguntaría
para obtener una respuesta autoritativa? No, no esta el flag aa, deberia preguntarle a alguno de los servidores NS
c. ¿La consulta fue recursiva? ¿Y la respuesta?
Ambas lo fueron estan los flags de rd y ra respectivamente
d. ¿Qué representan los valores 10 y 5 en las líneas (1) y (2)? son las prioridades de correo, especificamente el de prioridad 5 es mas prioritario. 