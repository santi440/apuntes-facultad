# Docker
1. Utilizando sus palabras, describa qué es Docker y enumere al menos dos beneficios que encuentre para el concepto de contenedores.
	Es una plataforma que permite automatizar el despliegue de contendores. Es como una "caja" estándar que contiene todo lo necesario para que un software funcione (código, librerías, configuración), garantizando que se ejecute igual en tu laptop que en un servidor de producción.
	**Beneficios principales:**
	- **Portabilidad:** Eliminás el problema del "en mi máquina funciona". El contenedor lleva sus propias dependencias, por lo que corre en cualquier sistema que tenga Docker.
	- **Eficiencia de recursos:** A diferencia de una Máquina Virtual, los contenedores comparten el mismo kernel del host. Esto significa que podés correr decenas de contenedores donde antes solo entraban dos o tres VMs, con un arranque casi instantáneo.
2. ¿Qué es una imagen? ¿Y un contenedor? ¿Cuál es la principal diferencia entre
ambos?
	- **Imagen:** Es un archivo estático, de solo lectura, que contiene el "molde" o la captura del sistema de archivos y la configuración necesaria para la aplicación. Es como la **clase** en programación orientada a objetos
	- **Contenedor:** Es la **instancia** de esa imagen en ejecución. Es un proceso aislado (gracias a los namespaces y cgroups) que tiene una capa de escritura encima de la imagen. 
	- **Diferencia principal:** El estado. La **imagen es inmutable** (no cambia), mientras que el **contenedor es efímero y ejecutable**; podés crear, destruir o modificar archivos dentro de él mientras esté vivo.
3. ¿Qué es Union Filesystem? ¿Cómo lo utiliza Docker?
	El **UnionFS** es un sistema de archivos que permite que múltiples directorios (llamados capas) se superpongan para parecer uno solo.
	- **Uso en Docker:** Docker utiliza esto para optimizar el almacenamiento. Las imágenes están compuestas por **capas de solo lectura**. Si dos imágenes distintas usan la misma base, Docker solo descarga esa capa una vez.
	- **Copy-on-Write (CoW):** Cuando un contenedor necesita modificar un archivo de la imagen, el UnionFS lo copia de la capa de solo lectura a la capa superior de escritura del contenedor. Esto hace que el despliegue sea extremadamente liviano.
	![[Pasted image 20260512201138.png]]
4. ¿Qué rango de direcciones IP utilizan los contenedores cuando se crean? ¿De dónde
la obtiene?
	Por defecto, los contenedores utilizan el rango de direcciones **172.17.0.0/16**.
	- La obtiene de un bridge de red virtual llamado `docker0` que se crea al instalar Docker. Este bridge actúa como un switch virtual que asigna IPs dinámicamente a cada contenedor mediante un servidor DHCP interno
5. ¿De qué manera puede lograrse que las datos sean persistentes en Docker? ¿Qué dos
maneras hay de hacerlo? ¿Cuáles son las diferencias entre ellas?

|**Característica**|**Volumes (Volúmenes)**|**Bind Mounts**|
|---|---|---|
|**Ubicación**|Gestionados por Docker en una parte privada del FS (`/var/lib/docker/volumes`).|Cualquier lugar del sistema de archivos del host (ej. `/home/santi/data`).|
|**Control**|Docker los gestiona totalmente. Son más seguros y fáciles de respaldar.|Dependen de la estructura de carpetas del host y sus permisos.|
|**Recomendación**|Es la forma estándar y preferida para datos de producción.|Útiles para desarrollo (ej. linkear tu código fuente al contenedor).|
En los **Volumes**, Docker crea y administra el espacio por vos; en los **Bind Mounts**, vos le decís exactamente qué carpeta de tu máquina querés "espejar" dentro del contenedor.

# Taller
## 1. Instalar
Instale Docker CE (Community Edition) en su sistema operativo. Ayuda: seguir las
instrucciones de la página de Docker. La instalación más simple para distribuciones de
GNU/Linux basadas en Debian es usando los repositorios.

Supongo que como estamos en linux es lo mismo que docker engine. https://docs.docker.com/engine/install/debian/

Me sorprende que aguante 4 practicas sin sudo
```bash
su
apt update 
apt install sudo
/usr/sbin/usermod -aG sudo so
#para variar otro binario sin link, tambien lo agrego al path y me dejo de joder
export PATH=$PATH:/usr/sbin
```
este kernel no soporta bien tema nat:
en `sudo nano /etc/docker/daemon.json`
```bash
{ 
	"bridge": "none",
	"iptables": false,
	"ip-masq": false,
	"storage-driver": "vfs" }
```
y
```bash
sudo systemctl restart docker
```
## 2. grupos
Asegúrese de agregar al usuario con el que trabajará al grupo docker, por ejemplo en la
VM, luego de esta operación deberá cerrar la sesión del usuario so en la terminal y
volver a loguearse:
```bash
adduser so docker
```
## 3. verificar
Luego de volver a loguearse verifique que el usuario pertenece al grupo docker:
```bash
~$ ssh so@192.168.56.104
so@192.168.56.104's password:
Linux so 6.1.0-31-amd64 #1 SMP PREEMPT_DYNAMIC Debian 6.1.128-1
(2025-02-07) x86_64
The programs included with the Debian GNU/Linux system are free
software;
the exact distribution terms for each program are described in the
individual files in /usr/share/doc/*/copyright.
Debian GNU/Linux comes with ABSOLUTELY NO WARRANTY, to the extent
permitted by applicable law.
Last login: Sat Apr 18 22:10:44 2026 from 192.168.56.1
so@so:~$ groups
so cdrom floppy audio dip video plugdev users netdev bluetooth docker
```
## 4. tareas
Usando las herramientas (comandos) provistas por Docker realice las siguientes
tareas:
a. Obtener una imagen de la última versión de Ubuntu disponible. ¿Cuál es el
tamaño en disco de la imagen obtenida? ¿Ya puede ser considerada un
contenedor? ¿Qué significa lo siguiente: Using default tag: latest?

`docker pull ubuntu`
![[Pasted image 20260512204138.png]]
La imagen pesa MB
![[Pasted image 20260512204218.png]]
- no es un contenedor, es una imagen. contenedor será cuando se ponga en ejecución.
- tag:latest dice que como no le pedi una versión tipo ubuntu.22.04 me trajo la ultima que encontro
b. De la imagen obtenida en el punto anterior iniciar un contenedor que
simplemente ejecute el comando ls -l.

![[Pasted image 20260512204730.png]]
se levanta el contenedor, se hace un ls y muere

c. ¿Qué sucede si ejecuta el comando docker [container] run ubuntu /bin/bash?
¿Puede utilizar la shell Bash del contenedor?
![[Pasted image 20260512205054.png]]
no es la shell del contendor, es mi shell. Bash se ejecutó, no le pedi a Docker que mantuviera la entrada estándar abierta ni que asignara una terminal.
i. Modifique el comando utilizado para que el contenedor se inicie con una terminal interactiva y ejecutarlo. ¿Ahora puede utilizar la shell Bash del contenedor? ¿Por qué?
`docker run -it ubuntu /bin/bash`
**Sí**. El flag `-i` (interactive) mantiene STDIN abierto y `-t` (tty) asigna una terminal virtual.
ii. ¿Cuál es el PID del proceso bash en el contenedor? ¿Y fuera de éste?

- dentro= 1 ![[Pasted image 20260512205514.png]]
- fuera = 759208 ![[Pasted image 20260512205538.png]]
iii. Ejecutar el comando lsns. ¿Qué puede decir de los namespace?
tiene namespases diferentes a los del so anfitrion. Tenemos aislamiento :)
iv. Dentro del contenedor cree un archivo con nombre sistemas-operativos en el directorio raíz del filesystem y luego salga del contenedor (finalice la sesión de Bash utilizando las teclas Ctrl + D o el comando exit).
![[Pasted image 20260512205734.png]]
v. Corrobore si el archivo creado existe en el directorio raíz del sistema operativo anfitrión (host). ¿Existe? ¿Por qué?
![[Pasted image 20260512205832.png]]
no existe. El sistema de archivos del contenedor está aislado mediante un **Mount Namespace** y capas de UnionFS. No tenemos volumen ni bind mount a /
d. Vuelva a iniciar el contenedor anterior utilizando el mismo comando (con una terminal interactiva). ¿Existe el archivo creado en el contenedor? ¿Por qué?
![[Pasted image 20260512205951.png]]
No existe. Porque `docker run` crea un **nuevo** contenedor a partir de la imagen virgen. El contenedor anterior sigue guardado (pero detenido) con sus cambios
e. Obtenga el identificador del contenedor (container_id) donde se creó el archivo y utilícelo para iniciar con el comando docker start -ia container_id el contenedor en el cual se creó el archivo.
![[Pasted image 20260512210157.png]]
i. ¿Cómo obtuvo el container_id para para este comando?
`docker ps -a`
ii. Chequee nuevamente si el archivo creado anteriormente existe. ¿Cuál es el resultado en este caso? ¿Puede encontrar el archivo creado?
Si (era el segundo de esos, el primero es el ubuntu del bash no interactivo, el segundo la shell en la que creamos el archivo y la ultima la que creamos para ver que no habia tal archivo)
f. ¿Cuántos contenedores están actualmente en ejecución? ¿En qué estado se encuentra cada uno de los que se han ejecutado hasta el momento?
![[Pasted image 20260512210600.png]]
todos detenidos. No sabia que tenia tres corriendo, ayuda?
![[Pasted image 20260512210734.png]]
status exited (0) = terminaron bien

g. Elimine todos los contenedores creados hasta el momento. Indique el o los
comandos utilizados
![[Pasted image 20260512210835.png]]
`docker rm <id>`
## 5. contenedor
Creación de una imagen a partir de un contenedor. Siguiendo los pasos indicados a
continuación genere una imagen de Docker a partir de un contenedor:
a. Inicie un contenedor a partir de la imagen de Ubuntu descargada anteriormente ejecutando una consola interactiva de Bash.

```bash
docker run -it ubuntu /bin/bash
```

b. Instale el servidor web Nginx, https://nginx.org/en/, en el contenedor utilizando
los siguientes comandos2:
``` bash
	export DEBIAN_FRONTEND=noninteractive
	export TZ=America/Buenos_Aires
	apt update -qq
	apt install -y --no-install-recommends nginx
```
c. Salga del contenedor y genere una imagen Docker a partir de éste. ¿Con qué nombre se genera si no se especifica uno?

```bash
docker ps -a # saber el id
docker commit d07617a6332d
```

Si no se especifica un nombre, la imagen se genera con el nombre **`<none>`** tanto en la columna _Repository_ como en _Tag_ (se identifica únicamente por su IMAGE ID)

dato curioso docker image ls no lo lista sin el tag a menos que le pongas el -a 

d. Cambie el nombre de la imagen creada de manera que en la columna Repository aparezca nginx-so y en la columna Tag aparezca v1.
```bash
docker tag d07617a6332d nginx-so:v1
```
e.Ejecute un contenedor a partir de la imagen nginx-so:v1 que corra el servidor web nginx atendiendo conexiones en el puerto 8080 del host, y sirviendo una página web para corroborar su correcto funcionamiento. Para esto:
I. En el Sistema Operativo anfitrión (host) sobre el cual se ejecuta Docker crear un directorio que se utilizará para este taller. Éste puede ser el directorio nginx-so dentro de su directorio personal o cualquier otro directorio - para los fines de este enunciado haremos referencia a
éste como /home/so/nginx-so, por lo que en los lugares donde se mencione esta ruta usted deberá reemplazarla por la ruta absoluta al directorio que haya decidido crear en este paso.

`mkdir nginx-so`

II. Dentro de ese directorio, cree un archivo llamado index.html que
contenga el código HTML de este gist de GitHub:
https://gist.github.com/ncuesta/5b959fce1c7d2ed4e5a06e84e5a7efc8.
III. Cree un contenedor a partir de la imagen nginx-so:v1 montando el directorio del host (/home/so/nginx-so) sobre el directorio /var/www/html del contenedor, mapeando el puerto 80 del contenedor al puerto 8080 del host, y ejecutando el servidor nginx en primer
plano3. Indique el comando utilizado.

dato: al menos si el host es linux, dale permisos para que use el archivo  `chmod -R 755 index.html `
```bash
docker run -d --name web-server \
	-p 8080:80 \
	-v /home/santi/nginx-so:/var/www/html \
	nginx-so:v1 \ 
	nginx -g 'daemon off;'
```
f. Verifique que el contenedor esté ejecutándose correctamente abriendo un
navegador web y visitando la URL http://localhost:8080.
g. Modifique el archivo index.html agregándole un párrafo con su nombre y
número de alumno. ¿Es necesario reiniciar el contenedor para ver los
cambios?

**No es necesario reiniciar el contenedor.** Al haber utilizado un volumen (`-v`), el directorio del contenedor está vinculado directamente al del host.

h. Analice: ¿por qué es necesario que el proceso nginx se ejecute en primer
plano? ¿Qué ocurre si lo ejecuta sin -g 'daemon off;'?

Docker monitorea el proceso principal (PID 1) del contenedor. Si ese proceso termina, el contenedor se detiene automáticamente. Un contenedor vive únicamente mientras su proceso principal esté en ejecución.

**¿Qué ocurre si lo ejecuta sin `-g 'daemon off;'`?** Por defecto, Nginx se ejecuta como un "daemon" (en segundo plano). Si no usas esa bandera:
1. El comando `nginx` se lanza.
2. Inmediatamente se mueve al fondo y devuelve el control a la shell.
3. Al terminar la tarea de "lanzamiento", el proceso principal finaliza.
4. **Resultado:** Docker interpreta que el contenedor terminó su trabajo y se detiene (status _Exited_) a los pocos segundos de haber iniciado.

## 6. dockerfile
Creación de una imagen Docker a partir de un archivo Dockerfile. Siguiendo los pasos indicados a continuación, genere una nueva imagen a partir de los pasos descritos en un Dockerfile.
a. En el directorio del host creado en el punto anterior (/home/so/nginx-so), cree un archivo Dockerfile que realice los siguientes pasos:
i. Comenzar en base a la imagen oficial de Ubuntu.
ii. Exponer el puerto 80 del contenedor.
iii. Instalar el servidor web nginx.
iv. Copiar el archivo index.html del mismo directorio del host al directorio /var/www/html de la imagen.
v. Indicar el comando que se utilizará cuando se inicie un contenedor a partir de esta imagen para ejecutar el servidor nginx en primer plano:
nginx -g 'daemon off;'. Use la forma exec4 para definir el comando, de manera que todas las señales que reciba el contenedor sean enviadas directamente al proceso de nginx.
Ayuda: las instrucciones necesarias para definir los pasos en el Dockerfile son FROM, EXPOSE, RUN, COPY y CMD.

```bash
FROM ubuntu:latest

# ii. Exponer el puerto 80
EXPOSE 80

# iii. Instalar nginx
RUN apt update -qq && \
    export DEBIAN_FRONTEND=noninteractive && \
    apt install -y --no-install-recommends nginx && \
    rm -rf /var/lib/apt/lists/*

# iv. Copiar el archivo index.html
COPY index.html /var/www/html/index.html

# v. Comando de inicio en forma exec
CMD ["nginx", "-g", "daemon off;"]

```

b. Utilizando el Dockerfile que generó en el punto anterior construya una nueva imagen Docker guardándola localmente con el nombre nginx-so:v2.

![[Pasted image 20260514201927.png]]

c. Ejecute un contenedor a partir de la nueva imagen creada con las opciones adecuadas para que pueda acceder desde su navegador web ala página a través del puerto 8090 del host. Verifique que puede visualizar correctamente la página accediendo a http://localhost:8090.

```bash
docker run -d --name web-v2 -p 8090:80 nginx-so:v2
```

d. Modifique el archivo index.html del host agregando un párrafo con la fecha actual y recargue la página en su navegador web. ¿Se ven reflejados los cambios que hizo en el archivo? ¿Por qué?

el archivo quedo:
```html
<html lang="es">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>nginx en un container!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" i>
  </head>
  <body>
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-6">
          <h1>Sistemas Operativos <span id="y"></span></h1>
          <h2>Trabajo Práctico de Docker</h2>

          <p class="lead">¡Felicitaciones! El servidor web con nginx está funcionando correctamente.</p>
          <p>Alumno: Marcos,Matias Santiago legajo 23345/0</p>
          <p>Fecha actual <span id="fecha"></span></p>
        </div>
      </div>
    </div>

    <script>document.getElementById('y').innerHTML = new Date().getFullYear()</script>
  </body>
</html>
<script>
  const fecha = new Date();
  document.getElementById("fecha").innerText = fecha.toLocaleDateString();
</script>
```

A diferencia del ejercicio anterior, aquí no usamos un volumen (`-v`). La instrucción `COPY` en el Dockerfile toma una "foto" del archivo en el momento de la construcción y la mete dentro de la imagen. El archivo dentro del contenedor es una copia estática e independiente del archivo del host.

El de 8080 si actualiza


e. Termine el contenedor iniciado antes y cree uno nuevo utilizando el mismo comando. Recargue la página en su navegador web. ¿Se ven ahora reflejados los cambios realizados en el archivo HTML? ¿Por qué?

no. Porque el nuevo contenedor se está creando a partir de la misma imagen `nginx-so:v2`, la cual todavía tiene guardada la versión vieja del archivo `index.html` que existía cuando ejecutas el `docker build`.

f. Vuelva a construir una imagen Docker a partir del Dockerfile creado anteriormente, pero esta vez dándole el nombre nginx-so:v3. Cree un contenedor a partir de ésta y acceda a la página en su navegador web. ¿Se ven reflejados los cambios realizados en el archivo HTML? ¿Por qué?

```bash
docker build -t nginx-so:v3 .
docker run -d --name web-v3 -p 8091:80 nginx-so:v3
```

Si. La nueva imagen ahora contiene la versión del archivo con la fecha que agregaste

## Docker Compose
1. Utilizando sus palabras describa, ¿qué es docker compose?

Es una herramienta simple y elegante de levantar multiples contenedores de docker, que juntos conforman una aplicación, en orden. Permite definir y ejecutar aplicaciones de múltiples contenedores (base de datos, backend, frontend) con un solo comando.

2. ¿Qué es el archivo compose y cual es su función? ¿Cuál es el “lenguaje” del archivo?

- Es un archivo de configuración que actúa como el "plano" de tu infraestructura.
- Define qué imágenes usar, qué puertos abrir, cómo se conectan los contenedores entre sí y qué volúmenes de datos necesitan.
- Utiliza **YAML** (_YAML Ain't Markup Language_). Es un formato basado en texto muy legible para humanos que se estructura mediante **sangrías (identación)**.
Ej de docker compose
![[Pasted image 20260514223735.png]]

3. ¿Cuáles son las versiones existentes del archivo docker-compose.yaml existentes y qué características aporta cada una? ¿Son compatibles entre sí?¿Por qué?

- **Versión 1**: La original, hoy obsoleta. No soportaba redes avanzadas ni volúmenes definidos. 
- **Versión 2**: Introdujo la gestión de redes (_networks_) y volúmenes dedicados.
- **Versión 3**: Diseñada para ser compatible con _Docker Swarm_ (clústeres).
- **Compatibilidad**: No son totalmente compatibles de forma directa. La versión 3 eliminó algunas opciones de la v2 (como límites de memoria simples) para estandarizarlas hacia Swarm. Sin embargo, actualmente Docker ha unificado muchas de estas diferencias en la **"Compose Specification"**, que hace que la versión ya no sea estrictamente necesaria en la cabecera del archivo.

4. Investigue y describa la estructura de un archivo compose.
Desarrolle al menos sobre los siguientes bloques indicando para qué se usan:
a. services
b. build
c. image
d. volumes
e. restart
f. depends_on
g. environment
h. ports
i. expose
j. networks

|**Bloque**|**Función**|
|---|---|
|**`services`**|El corazón del archivo. Aquí se listan los contenedores que componen la app.|
|**`build`**|Indica la ruta al `Dockerfile` si quieres que Docker construya la imagen en lugar de descargarla.|
|**`image`**|Nombre de la imagen (de Docker Hub o local) que usará el servicio.|
|**`volumes`**|Define carpetas compartidas entre el host y el contenedor para persistir datos.|
|**`restart`**|Define la política de reinicio (ej: `always` si el contenedor falla).|
|**`depends_on`**|Establece el orden de inicio (ej: "no inicies el backend hasta que la DB esté arriba").|
|**`environment`**|Define variables de entorno (como contraseñas o claves de API).|
|**`ports`**|Mapea puertos hacia el exterior (`HOST:CONTENEDOR`).|
|**`expose`**|Abre puertos para comunicación **interna** entre contenedores, sin exponerlos al host.|
|**`networks`**|Define redes aisladas para que los servicios se comuniquen entre sí por nombre.|

5. Conceptualmente: ¿Cómo se podrían usar los bloques “healthcheck” y “depends_on” para ejecutar una aplicación Web dónde el backend debería ejecutarse si y sólo si la base de datos ya está ejecutándose y lista?

- En la **Base de Datos**, defines un `healthcheck` (un comando como `pg_isready` o un `curl`) que verifique si el motor está aceptando conexiones.
- En el **Backend**, usas `depends_on` con la condición `service_healthy`. Esto garantiza que Docker no intente levantar el backend hasta que el chequeo de salud de la DB devuelva un estado exitoso.

6. Indique qué hacen y cuáles son las diferencias entre los siguientes comandos:
a. docker compose create y docker compose up: 
create los construye pero los deja apagados, up los crea (si no existen) y los enciende 

b. docker compose stop y docker compose down
stop detiene los contenedores pero los conserva, down los apaga y los elimina(contenedor, red,todo)

c. docker compose run y docker compose exec
run crea un contendor, ejecuta un comando y finaliza. exec entra en una terminal interactiva de un contenedor que ya esta corriendo.

d. docker compose ps
no se contra que debo comparar pero ps lista los contenedores de este compose y el estado

e. docker compose logs
muestra la salida en terminal de los servicios corriendo
7. ¿Qué tipo de volúmenes puede utilizar con docker compose? ¿Cómo se declara cada tipo en el archivo compose?
- **Bind Mounts**: Mapean una ruta específica del host.
    - _Declaración_: `- /home/usuario/app:/var/www/html`
- **Named Volumes**: Volúmenes gestionados por Docker (más seguros y portables).
    - _Declaración_: Primero se definen en el bloque raíz `volumes:` y luego se asignan al servicio como `- nombre_volumen:/datos`.
- **Anonymous Volumes**: No tienen nombre y son difíciles de rastrear tras borrar el contenedor.
	los bind y los Anonymous no los use nunca y no veo porque lo haria
8. ¿Qué sucede si en lugar de usar el comando “docker compose down” utilizo “docker
compose down -v/--volumes”?
Si agregas el parámetro `-v` o `--volumes`, Docker también **borrará todos los volúmenes con nombre** definidos en el archivo luego de borrar los contenedores.

## Ej guiado
no lo instalo porque ya lo tengo instalado :)

codigo bien tabulado:
```bash
version: "3.9"

services:
  db:
    image: mysql:5.7
    networks:
      - wordpress
    volumes:
      - db_data:/var/lib/mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: somewordpress
      MYSQL_DATABASE: wordpress
      MYSQL_USER: wordpress
      MYSQL_PASSWORD: wordpress

  wordpress:
    depends_on:
      - db
    image: wordpress:latest
    networks:
      - wordpress
    volumes:
      - ${PWD}:/data
      - wordpress_data:/var/www/html
    ports:
      - "127.0.0.1:8000:80"
    restart: always
    environment:
      WORDPRESS_DB_HOST: db
      WORDPRESS_DB_USER: wordpress
      WORDPRESS_DB_PASSWORD: wordpress
      WORDPRESS_DB_NAME: wordpress

volumes:
  db_data: {}
  wordpress_data: {}

networks:
  wordpress:
```

Preguntas
Intente analizar el código ANTES de correrlo y responda:
● ¿Cuántos contenedores se instancian? 2 -> db y wordpress
● ¿Por qué no se necesitan Dockerfiles? usa imagenes derecho de docker hub
● ¿Por qué el servicio identificado como “wordpress” tiene la siguiente línea?
depends_on:
- db

espera a que la db este corriendo para levantar el contenedor

● ¿Qué volúmenes y de qué tipo tendrá asociado cada contenedor?

- **Contenedor `db`**:
    - **Volumen Nombrado (`Named Volume`)**: `db_data` mapeado a `/var/lib/mysql`.
        
- **Contenedor `wordpress`**:
    - **Bind Mount**: `${PWD}` (el directorio actual del host) mapeado a `/data`.
    - **Volumen Nombrado (`Named Volume`)**: `wordpress_data` mapeado a `/var/www/html`.

● ¿Por que uso el volumen nombrado
volumes:
- db_data:/var/lib/mysql
para el servicio db en lugar de dejar que se instancie un volumen anónimo con el
contenedor?

quiero mantener la informacion, persistencia,  cuando lo baje y lo quiera volver a levantar (incluso si borro el contenedor)

● ¿Qué genera la línea
volumes:
- ${PWD}:/data
en la definición de wordpress?

Genera un **Bind Mount**. Mapea el directorio actual donde estás parado en tu computadora (`${PWD}`) al directorio `/data` dentro del contenedor. Esto permite que cualquier archivo que pongas en esa carpeta de tu PC sea visible inmediatamente para WordPress, y viceversa


● ¿Qué representa la información que estoy definiendo en el bloque environment de cada
servicio?¿Cómo se “mapean” al instanciar los contenedores?

son credenciales/variables de entorno que se le pasan a cada contenedor por ej usuario, password, o como se llama la db. Son configuraciones que se inyectan dentro del Sistema Operativo del contenedor.

● ¿Qué sucede si cambio los valores de alguna de las variables definidas en bloque
“environment” en solo uno de los contenedores y hago que sean diferentes? (Por ej:
cambio SOLO en la definición de wordpress la variable WORDPRESS_DB_NAME)

si cambias alguno de los valores como el nombre de la base de datos cuando wordpress busque la db y se llame diferente de wordpress no la va a encontrar

● ¿Cómo sabe comunicarse el contenedor “wordpress” con el contenedor “db” si nunca
doy información de direccionamiento?

Se comunican a través del **DNS interno de Docker**. Al estar ambos servicios en la misma red llamada `wordpress`, Docker crea un servidor DNS automático donde el **nombre del servicio** funciona como su nombre de host (Hostname). Por eso, en la variable `WORDPRESS_DB_HOST: db`, WordPress sabe que al buscar "db", Docker resolverá internamente la IP correcta del contenedor de la base de datos.

● ¿Qué puertos expone cada contenedor según su Dockerfile? (pista: navegue el sitio
https://hub.docker.com/_/wordpress y https://hub.docker.com/_/mysql para acceder a los
Dockerfiles que generaron esas imágenes y responder esta pregunta.)

- **MySQL (imagen `mysql:5.7`):** Expone el puerto **3306**.
- **WordPress (imagen `wordpress:latest`):** Expone el puerto **80**.
● ¿Qué servicio se “publica” para ser accedido desde el exterior y en qué puerto?¿Es
necesario publicar el otro servicio? ¿Por qué?
wordpress tiene un mapeo de port:
```YML
ports:
      - "127.0.0.1:8000:80"
```
no, no expongas una db por favor. No hace falta porque se conecta el otro contenedor dentro de la red local.

![[Pasted image 20260514230625.png]]
