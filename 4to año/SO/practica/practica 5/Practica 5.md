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
b. Instale el servidor web Nginx, https://nginx.org/en/, en el contenedor utilizando
los siguientes comandos2:
	export DEBIAN_FRONTEND=noninteractive
	export TZ=America/Buenos_Aires
	apt update -qq
	apt install -y --no-install-recommends nginx
c. Salga del contenedor y genere una imagen Docker a partir de éste. ¿Con qué nombre se genera si no se especifica uno?
d. Cambie el nombre de la imagen creada de manera que en la columna Repository aparezca nginx-so y en la columna Tag aparezca v1.
Ejecute un contenedor a partir de la imagen nginx-so:v1 que corra el servidor
web nginx atendiendo conexiones en el puerto 8080 del host, y sirviendo una
página web para corroborar su correcto funcionamiento. Para esto:
I. En el Sistema Operativo anfitrión (host) sobre el cual se ejecuta
Docker crear un directorio que se utilizará para este taller. Éste puede
ser el directorio nginx-so dentro de su directorio personal o cualquier
otro directorio - para los fines de este enunciado haremos referencia a
éste como /home/so/nginx-so, por lo que en los lugares donde se
mencione esta ruta usted deberá reemplazarla por la ruta absoluta al
directorio que haya decidido crear en este paso.
II. Dentro de ese directorio, cree un archivo llamado index.html que
contenga el código HTML de este gist de GitHub:
https://gist.github.com/ncuesta/5b959fce1c7d2ed4e5a06e84e5a7efc8.
III. Cree un contenedor a partir de la imagen nginx-so:v1 montando el
directorio del host (/home/so/nginx-so) sobre el directorio
/var/www/html del contenedor, mapeando el puerto 80 del contenedor
al puerto 8080 del host, y ejecutando el servidor nginx en primer
plano3. Indique el comando utilizado.
f. Verifique que el contenedor esté ejecutándose correctamente abriendo un
navegador web y visitando la URL http://localhost:8080.
g. Modifique el archivo index.html agregándole un párrafo con su nombre y
número de alumno. ¿Es necesario reiniciar el contenedor para ver los
cambios?
h. Analice: ¿por qué es necesario que el proceso nginx se ejecute en primer
plano? ¿Qué ocurre si lo ejecuta sin -g 'daemon off;'?

## 6. dockerfile
Creación de una imagen Docker a partir de un archivo Dockerfile. Siguiendo los pasos
indicados a continuación, genere una nueva imagen a partir de los pasos descritos en un
Dockerfile.
a. En el directorio del host creado en el punto anterior (/home/so/nginx-so), cree un
archivo Dockerfile que realice los siguientes pasos:
i. Comenzar en base a la imagen oficial de Ubuntu.
ii. Exponer el puerto 80 del contenedor.
iii. Instalar el servidor web nginx.
iv. Copiar el archivo index.html del mismo directorio del host al directorio
/var/www/html de la imagen.
v. Indicar el comando que se utilizará cuando se inicie un contenedor a
partir de esta imagen para ejecutar el servidor nginx en primer plano:
nginx -g 'daemon off;'. Use la forma exec4 para definir el comando, de
manera que todas las señales que reciba el contenedor sean enviadas
directamente al proceso de nginx.
Ayuda: las instrucciones necesarias para definir los pasos en el
Dockerfile son FROM, EXPOSE, RUN, COPY y CMD.
b. Utilizando el Dockerfile que generó en el punto anterior construya una nueva
imagen Docker guardándola localmente con el nombre nginx-so:v2.
c. Ejecute un contenedor a partir de la nueva imagen creada con las opciones
adecuadas para que pueda acceder desde su navegador web ala página a través del puerto 8090 del host. Verifique que puede visualizar correctamente la
página accediendo a http://localhost:8090.
d. Modifique el archivo index.html del host agregando un párrafo con la fecha
actual y recargue la página en su navegador web. ¿Se ven reflejados los
cambios que hizo en el archivo? ¿Por qué?
e. Termine el contenedor iniciado antes y cree uno nuevo utilizando el mismo
comando. Recargue la página en su navegador web. ¿Se ven ahora reflejados
los cambios realizados en el archivo HTML? ¿Por qué?
f. Vuelva a construir una imagen Docker a partir del Dockerfile creado
anteriormente, pero esta vez dándole el nombre nginx-so:v3. Cree un
contenedor a partir de ésta y acceda a la página en su navegador web. ¿Se ven
reflejados los cambios realizados en el archivo HTML? ¿Por qué?