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
i. ¿La solicitud fue recursiva? ¿Y la respuesta? ¿Cómo lo sabe?
ii. ¿Puede indicar si se trata de una respuesta autoritativa? ¿Qué
significa que lo sea?
iii. ¿Cuál es la dirección IP del resolver utilizado? ¿Cómo lo sabe?
b. ¿Cuáles son los servidores de correo del dominio redes.unlp.edu.ar? ¿Por
qué hay más de uno y qué significan los números que aparecen entre MX y
el nombre? Si se quiere enviar un correo destinado a redes.unlp.edu.ar, ¿a
qué servidor se le entregará? ¿En qué situación se le entregará al otro?
c. ¿Cuáles son los servidores de DNS del dominio redes.unlp.edu.ar?
d. Repita la consulta anterior cuatro veces más. ¿Qué observa? ¿Puede
explicar a qué se debe?
e. Observe la información que obtuvo al consultar por los servidores de DNS del
dominio. En base a la salida, ¿es posible indicar cuál de ellos es el primario?
f. Consulte por el registro SOA del dominio y responda.
i. ¿Puede ahora determinar cuál es el servidor de DNS primario?
ii. ¿Cuál es el número de serie, qué convención sigue y en qué casos es
importante actualizarlo?
iii. ¿Qué valor tiene el segundo campo del registro? Investigue para qué
se usa y cómo se interpreta el valor.
iv. ¿Qué valor tiene el TTL de caché negativa y qué significa?
g. Indique qué valor tiene el registro TXT para el nombre
saludo.redes.unlp.edu.ar. Investigue para qué es usado este registro.
h. Utilizando dig, solicite la transferencia de zona de redes.unlp.edu.ar, analice
la salida y responda.
i. ¿Qué significan los números que aparecen antes de la palabra IN?
¿Cuál es su finalidad?
ii. ¿Cuántos registros NS observa? Compare la respuesta con los
servidores de DNS del dominio redes.unlp.edu.ar que dio
anteriormente. ¿Puede explicar a qué se debe la diferencia y qué
significa?
i. Consulte por el registro A de www.redes.unlp.edu.ar y luego por el registro A
de www.practica.redes.unlp.edu.ar. Observe los TTL de ambos. Repita la
operación y compare el valor de los TTL de cada uno respecto de la
respuesta anterior. ¿Puede explicar qué está ocurriendo? (Pista: observar los
flags será de ayuda).
j. Consulte por el registro A de www.practica2.redes.unlp.edu.ar. ¿Obtuvo
alguna respuesta? Investigue sobre los códigos de respuesta de DNS. ¿Para
qué son utilizados los mensajes NXDOMAIN y NOERROR?
12. Investigue los comandos nslookup y host. ¿Para qué sirven? Intente con ambos
comandos obtener:
● Dirección IP de www.redes.unlp.edu.ar.
● Servidores de correo del dominio redes.unlp.edu.ar.
● Servidores de DNS del dominio redes.unlp.edu.ar.
13. ¿Qué función cumple en Linux/Unix el archivo /etc/hosts o en Windows el archivo
\WINDOWS\system32\drivers\etc\hosts?
14. Abra el programa Wireshark para comenzar a capturar el tráfico de red en la interfaz con
IP 172.28.0.1. Una vez abierto realice una consulta DNS con el comando dig para averiguar
el registro MX de redes.unlp.edu.ar y luego, otra para averiguar los registros NS
correspondientes al dominio redes.unlp.edu.ar. Analice la información proporcionada por dig
y compárelo con la captura.
15. Dada la siguiente situación: “Una PC en una red determinada, con acceso a Internet,
utiliza los servicios de DNS de un servidor de la red”. Analice:
a. ¿Qué tipo de consultas (iterativas o recursivas) realiza la PC a su servidor de
DNS?
b. ¿Qué tipo de consultas (iterativas o recursivas) realiza el servidor de DNS
para resolver requerimientos de usuario como el anterior? ¿A quién le realiza
estas consultas?
16. Relacione DNS con HTTP. ¿Se puede navegar si no hay servicio de DNS?
17. Observar el siguiente gráfico y contestar:
    ![[grafico de consulta17.png]]
Si la PC-A, que usa como servidor de DNS a "DNS Server", desea obtener la
IP de www.unlp.edu.ar, cuáles serían, y en qué orden, los pasos que se
ejecutarán para obtener la respuesta.
b. ¿Dónde es recursiva la consulta? ¿Y dónde iterativa?
18. ¿A quién debería consultar para que la respuesta sobre www.google.com sea
autoritativa?
19. ¿Qué sucede si al servidor elegido en el paso anterior se lo consulta por
www.info.unlp.edu.ar? ¿Y si la consulta es al servidor 8.8.8.8?
Ejercicio de parcial
20. En base a la siguiente salida de dig, conteste las consignas. Justifique en todos los
casos.
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
b. ¿Es una respuesta autoritativa? En caso de no serlo, ¿a qué servidor le preguntaría
para obtener una respuesta autoritativa?
c. ¿La consulta fue recursiva? ¿Y la respuesta?
d. ¿Qué representan los valores 10 y 5 en las líneas (1) y (2)