Docker Engine est´a dividido en 3 componentes:
- Docker Daemon (dockerd): es el servidor. Responsable por crear, ejecutar y monitorear los contenedores, construcción de imágenes, etc.
- API: especifica la interface que los programas pueden usar para interactuar con el servidor.
- CLI: cliente. Permite a los usuarios interactuar con el servidor mediante comandos.

Ambos (cliente y servidor) ejecutan en el espacio del usuario. Procesos en containers funcionan igual que los que no lo están. Docker no captura las syscall.

- imagen: template (molde) de s´olo lectura con todas las instrucciones para construir un contenedor.
- container: Es una instancia de una imagen en ejecuci´on.
- registry: Es un almac´en de im´agenes de Docker. Puede ser p´ublico o privado. Por defecto, docker utiliza Docker Hub.
- Dockerfile: Archivo de texto que indica los pasos necesarios para construir una imagen.
Una imagen puede basarse en otras. Por ejemplo:httpd  debian:jessie-backports  debian:jessie  scratch
donde scratch es un nombre especial que significa que se inicia

Namespace: lo usa para que sean areas aisladas. El SO host tiene su namespacee sparado
Control Groups: opcional pero gestiona permisos ver [[05 - cont virt]]
Storage Drivers: file systems donde se guardan los contenedores
- Docker no crea un nuevo nivel de privilegio: ejecuta en el Ring 3.
- Contenedor tiene sus propios filesystems, librer´ıas, nombre, interface de red, etc., excepto su propio kernel

# Union Mount FS
Merge de FS. Las capas de abajo son inmutables, el overley es una mezcla de los dos que junta. Si hay un archivo que se llama igual muestra el upper. Sol la capa Overlay (la del contenedor) es RW
![[Pasted image 20260422182620.png]]
La capa escribible no existe en la imagen y es la que nos da la diferencia entre imagen - Contenedor -> cualquier cosa que agrego al contendor se borra al dar de baja el contenedor
Eliminar archivos -> enmascararlos pero en las capas inf siguen estando

# Almacenamiento
- Volumes: almacenados en una parte del filesystem administrada por Docker (por default /var/lib/docker/volumes) o en un directorio que le especifiquemos. Tmb se pueden eliminar si voy al directorio y lo borro normal dentro de la maquina host
- Bind Mounts: pueden estar en cualquier parte del filesystem. Pueden ser modificados por procesos que no sean de Docker. Es un filesystem externo. No se crean automaticamente sino que el / debe existir en todas las maquinas que usen el contenedor


Volumenes es la mejor :) por portabilidad

