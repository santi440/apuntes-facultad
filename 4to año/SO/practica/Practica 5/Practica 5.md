1. Defina política y mecanismo.
   **Política (_Policy_):** Define **qué** se debe hacer. Es la regla de negocio o el criterio de decisión.
   **Mecanismo (_Mechanism_):** Define **cómo** se implementa o se hace cumplir esa política. Es la herramienta técnica subyacente.
   
2. Defina objeto, dominio y right.
   **Objeto (_Object_):** Es cualquier entidad de hardware o software protegida por el sistema operativo a la que se puede acceder.
   **Derecho (_Right_ / _Access Right_):** Es la capacidad (o permiso) de ejecutar una operación específica sobre un objeto.
   **Dominio de protección (_Domain_):** Es un conjunto de pares `(objeto, derechos)`. Define exactamente qué operaciones puede realizar un proceso que se está ejecutando dentro de ese dominio sobre qué objetos. En un momento dado, un proceso corre "dentro" de un dominio de protección.
   
3. Defina POLA (Principle of least authority).
   El **Principio de Menor Privilegio** establece que cualquier usuario, proceso, programa o sistema debe configurarse y ejecutarse utilizando únicamente el **conjunto mínimo de privilegios y recursos estrictamente necesarios** para llevar a cabo su función asignada, y solo durante el tiempo que sea necesario.
4. ¿Qué valores definen el dominio en UNIX?
   El dominio de protección de un proceso está definido principalmente por las identidades de usuario y grupo asociadas a ese proceso. Específicamente, los valores que lo determinan son UID,GID,OtherID
5. ¿Qué es ASLR (Address Space Layout Randomization)? ¿Linux provee ASLR para los procesos de usuario? ¿Y para el kernel?
   **ASLR** es una técnica de seguridad informática que se utiliza para prevenir ataques de reutilización de código (como los clásicos desbordamientos de búfer / _buffer overflows_ o ataques de tipo _Return-to-libc_).
   Funciona **aleatorizando las posiciones** de las áreas de datos clave en el espacio de direcciones de un proceso. Esto incluye la base del ejecutable, el _stack_ (pila), el _heap_ (montículo) y las bibliotecas del sistema (como `libc`). De esta forma, un atacante no puede predecir de antemano a qué dirección exacta de memoria saltar para ejecutar código malicioso.
   Si. La mayoria de las distros lo proveen tanto para el espacio de usuario como para el del kernel (que se llama KASLR)
6. ¿Cómo se activa/desactiva ASRL para todos los procesos de usuario en Linux?
   El comportamiento de ASLR para el espacio de usuario se controla a nivel global mediante la interfaz del sistema de archivos virtual `/proc`.
   El archivo específico es: `/proc/sys/kernel/randomize_va_space`. Tiene tres valores `0`: **Desactivado.** El diseño del espacio de direcciones no se aleatoriza,`1`: **Activado de forma parcial.** Se aleatorizan las posiciones de las bibliotecas compartidas, la pila (_stack_), el segmento `vDSO` y las posiciones de las páginas compartidas de memoria.`2`: **Activado de forma total (Por defecto).** Además de lo que hace el valor `1`, se añade la aleatorización del _heap_ (gestionado vía `brk`).
   
# B- Ejercicio introductorio: Buffer Overflow simple
1. Compilar usando el makefile provisto el ejemplo 00-stack-overflow.c provisto en el repositorio de la cátedra.
```bash
cc -save-temps -g -fno-stack-protector -z execstack -no-pie -fcf-protection=none -O0    00-stack-overflow.c   -o 00-stack-overflow
00-stack-overflow.c: In function ‘login’:
00-stack-overflow.c:19:5: warning: implicit declaration of function ‘gets’; did you mean ‘fgets’? [-Wimplicit-function-declaration]
   19 |     gets(password);             // Vulnerable function reads without bounds
      |     ^~~~
      |     fgets
/usr/bin/ld: 00-stack-overflow.o: en la función `login':
/home/so/codigo-para-practicas/practica5/00-stack-overflow.c:19: aviso: the `gets' function is dangerous and should not be used.
cc -save-temps -g -fno-stack-protector -z execstack -no-pie -fcf-protection=none -O0    01-stack-overflow-ret.c   -o 01-stack-overflow-ret
/usr/bin/ld: 01-stack-overflow-ret.o: en la función `login':
/home/so/codigo-para-practicas/practica5/01-stack-overflow-ret.c:33: aviso: the `gets' function is dangerous and should not be used.
```
   me encanta que te lo avise tipo: sos idiota?
2. Ejecutar el programa y observar las direcciones de las variables access y password, así como la distancia entre ellas.
   `access pointer: 0x7ffd399db46f, password pointer: 0x7ffd399db450, distance: 31`
3. Probar el programa con una password cualquiera y con “big secret” para verificar que funciona correctamente.
   cualquier cosa: 
```bash
   Write password: asda            
	Access denied
```
con big secret
```bash
   Write password: big secret
	Now you know the secret
```
2. Volver a ejecutar pero ingresar una password lo suficientemente larga para sobreescribir access. Usar distance como referencia para establecer la longitud de la password.
`Write password: 12345678901234567890123456789012`
`Now you know the secret`
3. Después de realizar la explotación, reflexiona sobre las siguientes preguntas:
	a. ¿Por qué el uso de gets() es peligroso?
	
	El uso de `gets()` es extremadamente peligroso porque **no realiza ninguna verificación de límites (boundary checking)** sobre el buffer donde almacena los datos de entrada.
	La función lee caracteres desde la entrada estándar (`stdin`) y los guarda en el buffer de destino hasta que encuentra un carácter de nueva línea (`\n`) o un fin de archivo (`EOF`). Como la función no recibe como parámetro el tamaño máximo del buffer receptor, **asume que el espacio es infinito**. un atacante puede sobrescribir datos críticos de control del flujo del programa, como la **dirección de retorno (_Return Address_)** de la función actual.
	
	b. ¿Cómo se puede prevenir este tipo de vulnerabilidad?
	La forma más efectiva y segura de prevenirla es reemplazar las funciones inseguras por alternativas que **obliguen al desarrollador a especificar el tamaño máximo del buffer**.
- **La alternativa estándar: `fgets()`** En lugar de usar `gets(buffer)`, se debe usar:
    ```c
    fgets(buffer, sizeof(buffer), stdin);
    ```
    - `fgets` garantiza que nunca se leerán más caracteres que el tamaño máximo especificado menos uno (para dejar espacio al carácter nulo terminador `\0`), previniendo físicamente el desbordamiento.
	c. ¿Qué medidas de seguridad ofrecen los compiladores modernos para evitar
	estas vulnerabilidades?
	
### 1. Canarios de Stack (_Stack Canaries_ / _Stack Smashing Protection_)
El compilador inserta un valor entero aleatorio secreto (el "canario") en el _stack_ justo antes de la dirección de retorno de la función.  Antes de que la función retorne, el código generado verifica si el valor del canario sigue siendo el mismo. Si hubo un desbordamiento de búfer en esa función, el atacante obligatoriamente tuvo que sobrescribir el canario para poder llegar a la dirección de retorno. Al detectar que el valor cambió, el programa asume que fue comprometido y aborta inmediatamente (`SIGABRT`) antes de ejecutar la dirección de retorno alterada._En GCC se activa con:_ `-fstack-protector` o `-fstack-protector-strong`.
    

### 2. Fortificación de Funciones de la Biblioteca (`_FORTIFY_SOURCE`)
Es una macro que intercepta llamadas a funciones propensas a errores de memoria de la biblioteca estándar (como `memcpy`, `strcpy`, `read`, etc.) cuando el tamaño del buffer de destino es conocido en tiempo de compilación.
Si el compilador detecta que estás intentando copiar dinámicamente un string que es evidentemente más grande que el array estático de destino, reemplaza la función original por una versión segura en tiempo de ejecución (ej. `__strcpy_chk`) que frena el programa antes de que ocurra la corrupción. _En GCC se activa con:_ `-D_FORTIFY_SOURCE=2` o `-D_FORTIFY_SOURCE=3` (junto con optimización `-O1` o superior).
### 3. Soporte para PIE y ASLR (_Position Independent Executables_)
Aunque la aleatorización del espacio de direcciones (ASLR) es una tarea que ejecuta el kernel del sistema operativo , el compilador debe cooperar. Los compiladores modernos compilan el código fuente como un **Ejecutable Independiente de la Posición (PIE)** de manera predeterminada. Esto genera código que utiliza direccionamiento relativo en lugar de direcciones de memoria absolutas fijas. Gracias a esto, el sistema operativo puede mover libremente todo el código binario a cualquier dirección aleatoria de la memoria RAM al arrancar el proceso, haciendo que el atacante no pueda predecir a dónde apuntar su ataque._En GCC se activa con:_ `-fPIE -pie` (suele venir activo por defecto en distros modernas).

# C - Ejercicio: Buffer Overflow reemplazando dirección de retorno
1. Compilar usando el makefile provisto el ejemplo 01-stack-overflow-ret.c provisto en el repositorio de la cátedra.
2. Configurar setuid en el programa para que al ejecutarlo, se ejecute como usuario root.
   `sudo chmod u+s vulnerable`
3. Verificar si tiene ASLR activado en el sistema. Si no está, actívelo.
   `cat /proc/sys/kernel/randomize_va_space` = 2 o 1 = activado
   activarlo: 
   `echo 2 | sudo tee /proc/sys/kernel/randomize_va_space`
   o ir al archivo `/etc/sysctl.conf` y cambiarlo manual
4. Tenemos los siguientes datos sobre el stack:
a. El stack crece hacia abajo.
b. Si estamos compilando en x86_64 los punteros ocupan 8 bytes.
c. x86_64 es little endian.
d. Primero se apiló la dirección de retorno (una dirección dentro de la función
main()). Ocupa 8 bytes.
e. Luego se apiló la vieja base de la pila (rbp). Ocupa 8 bytes.
f. Luego se apila debug que ocupa 1 byte pero puede tener bytes de relleno
(verificar en el archivo .s la cantidad de padding que se agrega entre debug
y password).
g. password ocupa 16 bytes.
Calcule cuántos bytes de relleno necesita para pisar primero debug y luego la dirección de retorno.
= 16 + 1 + extra =17, no tiene padding hdp
5. Ejecute 01-stack-overflow-ret al menos 2 veces pisando el valor de debug para verificar que la dirección de memoria de privileged_fn() cambia.
   
6. Apague ASLR y repita el punto 3 para verificar que esta vez el proceso siempre retorna la misma dirección de memoria para privileged_fn().
   
7. Ejecute el script generate_payload.py para generar el payload.
   `python3 payload_pointer.py --padding 24 --pointer 5555555551c9 | ./01-stack-overflow-ret`
8. Pruebe el payload redirigiendo la salida del script a 01-stack-overflow-ret usando un pipe.
   lo mande de una en el ant
9. Para poder interactuar con el shell modifique exploit.expect para que envíe el payload correcto y ejecute ese archivo con el comando expect.
10. Pruebe algunos comandos para verificar que realmente tiene acceso a un shell con UID 0.
11. Conteste:
a. ¿Qué efecto tiene setear el bit setuid en un programa si el propietario del archivo es root? ¿Qué efecto tiene si el usuario es por ejemplo nobody?
El bit `setuid` (Set User ID) le indica al sistema operativo que, cuando un usuario ejecute ese archivo, el proceso resultante debe adoptar los privilegios del **dueño del archivo** (su EUID o _Effective User ID_) en lugar de los privilegios del usuario que lo lanzó. root tiene todos los privilegios. Nobody es una medida de **confinamiento o reducción de privilegios**, es una cuenta tradicionalmente configurada para no tener prácticamente ningún derecho sobre el sistema
b. ¿Cómo ASLR ayuda a evitar este tipo de ataques en un escenario real donde el programa no imprime en pantalla el puntero de la función objetivo?
En un entorno real, un software comercial o de servidor jamás te va a dar esa información. Cuando **ASLR** está activo y el binario está compilado como **PIE** (Position Independent Executable), el sistema operativo desordena y aleatoriza la estructura de la memoria RAM cada vez que el programa arranca. el atacante **no tiene idea de qué dirección de memoria inyectar.** (a menos que exista _Information Leak_)
c. ¿Cómo podría evitar este tipo de ataques en un módulo del kernel de Linux? ¿Qué mecanismo debería estar habilitado?

Si la vulnerabilidad de desbordamiento de búfer no ocurre en una aplicación de usuario sino dentro de un módulo del Kernel de Linux (`.ko`), el riesgo es catastrófico porque el Kernel ya corre con el nivel más alto de privilegios del procesador (Ring 0).

Para evitar que un atacante aproveche esto, deben estar habilitados los siguientes mecanismos defensivos a nivel de Kernel:

### 1. KASLR (Kernel Address Space Layout Randomization)

Es el equivalente de ASLR pero para el núcleo del sistema operativo. Al arrancar la máquina, el cargador de arranque (_bootloader_) y el kernel aleatorizan la dirección física y virtual donde se descomprime la imagen del Kernel en la RAM. Si un módulo tiene un bug de memoria, el atacante no sabrá dónde están las funciones internas del Kernel para saltar a ellas.

### 2. Canarios de Stack del Kernel (`CONFIG_STACKPROTECTOR`)

Esta opción de configuración del Kernel debe estar compilada (`CONFIG_STACKPROTECTOR_STRONG`). Funciona exactamente igual que en el espacio de usuario: el compilador introduce un valor secreto antes de la dirección de retorno en las funciones del kernel. Si el buffer de un módulo se desborda, el canario muere y el sistema genera un **Kernel Panic** inmediato, congelando la máquina antes de que el atacante tome el control.

### 3. Mitigaciones de Hardware modernas a nivel de CPU

El Kernel de Linux utiliza activamente características de los procesadores modernos para aislar la memoria:
- **SMEP (Supervisor Mode Execution Prevention):** Si el atacante logra desbordar la pila del kernel e intenta hacer que la CPU salte a ejecutar código que inyectó en el espacio de usuario, la CPU bloquea la ejecución en el acto porque el Kernel tiene prohibido ejecutar páginas de memoria de usuario.
- **SMAP (Supervisor Mode Access Prevention):** Prohíbe al Kernel leer o escribir datos directamente en el espacio de usuario a menos que esté explícitamente habilitado mediante funciones seguras (como `copy_from_user`), evitando la manipulación maliciosa de estructuras de control desde fuera del núcleo.

# Ejercicio SystemD
1. Investigue los comandos:
a. systemctl enable : Configura una unidad (servicio) para que **se inicie automáticamente durante el arranque (_boot_)** del sistema. Crea enlaces simbólicos en los directorios de destino
b. systemctl disable: Revierte el comando anterior. **Elimina el inicio automático** del servicio al arrancar el equipo (el enlace simbólico se borra), aunque todavía se puede iniciar manualmente.
c. systemctl daemon-reload: Le indica a `systemd` que vuelva a escanear los directorios de configuración (como `/etc/systemd/system/`) y **recargue en memoria todas las modificaciones** hechas a los archivos `.service`. Es obligatorio usarlo cada vez que editás una _unit_.
d. systemctl start: Inicia (ejecuta) un servicio **inmediatamente** en la sesión actual.
e. systemctl stop: Detiene un servicio que está en ejecución inmediatamente.
f. systemctl status: Muestra el **estado actual detallado** de un servicio (si está activo, inactivo, roto, su PID principal, uso de memoria, las últimas líneas de log, etc.).
g. systemd-cgls: Muestra de forma jerárquica el árbol de los **Cgroups (Control Groups)** del sistema. Permite ver exactamente qué procesos e hilos están corriendo dentro de cada servicio de `systemd`
h. journalctl -u [unit]: Filtra y muestra los **logs centralizados de la bitácora del sistema** (_journal_) pertenecientes específicamente a la unidad indicada.
2. Investigue las siguientes opciones que se pueden configurar en una unit service de
systemd:
a. User y Group: Define con qué usuario y grupo del sistema se ejecutará el proceso. Si no se especifican, por defecto corre como `root`, violando flagrantemente el principio de menor privilegio (POLA).
b. ProtectHome: Si se configura en `true`, vuelve los directorios `/home`, `/root` y `/run/user` totalmente **invisibles o inaccesibles** para el servicio. Si se pone en `read-only`, solo permite lectura.
c. PrivateTmp: Si se configura en `true`, el servicio obtiene un directorio `/tmp` y `/var/tmp` **completamente aislado y privado**, montado sobre un espacio de nombres (_namespace_) propio. No puede ver los archivos temporales del resto del sistema ni de otros usuarios, previniendo ataques de enlaces o filtrado de datos.
d. ProtectProc: Controla el acceso a la información del directorio `/proc` (donde están los datos de los procesos). Si se configura en `invisible`, el servicio **no podrá ver los procesos de otros usuarios** en el sistema, mitigando el espionaje entre procesos.
e. MemoryAccounting, MemoryHigh y MemoryMax: 
- `MemoryAccounting=true`: Habilita la contabilidad del uso de memoria para el servicio 
- `MemoryHigh`: Define un límite "suave" de memoria. Si se pasa, el sistema ralentiza el proceso y aplica presión para liberar memoria (_garbage collection_).
- `MemoryMax`: Define el **límite absoluto y estricto** de memoria. Si el servicio intenta alocar aunque sea un byte más allá de este límite, el kernel de Linux invoca al _OOM Killer_ (Out Of Memory Killer) y **mata el proceso inmediatamente** (`SIGKILL`).
3. Tenga en cuenta para los siguientes puntos:
a. La configuración del servicio se instala en:
/etc/systemd/system/insecure_service.service
b. Cada vez que modifique la configuración será necesario recargar el demonio de systemd y recargar el servicio:
i. systemctl daemon-reload
ii. systemctl restart insecure_service.service
4. En el directorio insecure_service del repositorio de la cátedra encontrará, el binario insecure_service, el archivo de configuración insecure_service.service y el script install.sh.
a. Instale el servicio usando el script install.sh.
b. Verifique que el servicio se está ejecutando con systemctl status.
c. Verifique con qué UID se ejecuta el servicio usando psaux | grep insecure_service.
```bash
root        2379  0.0  0.2 1232368 8304 ?        Ssl  21:08   0:00 /opt/sistemasoperativos/insecure_service
so          2438  0.0  0.0   6484  2140 pts/0    S+   21:09   0:00 grep insecure_service

```
d. Abra localhost:8080 en el navegador y explore los links provistos por este servicio.
5. Configure el servicio para que se ejecute con usuario y grupo no privilegiados (en Debian y derivados se llaman nobody y nogroup). Verifique con qué UID se ejecuta el servicio usando ps aux | grep insecure_service.
   `sudo nano /etc/systemd/system/insecure_service.service`
6. Explore el directorio /home y el directorio /tmp usando el servicio y luego:
   `Error opening directory`
a. Reconfigurelo para que no pueda visualizar el contenido de /home y tenga
su propio /tmp privado.
en `[Service]`: 
`ProtectHome=true
`PrivateTmp=true`
b. Recargue el servicio y verifique que estas restricciones surgieron efecto.
7. Limite el acceso a información de otros procesos por parte del servicio.
   `ProtectProc=invisible`
8. Establezca un límite de 16M al uso de memoria del servicio e intente alocar más de esa memoria en la sección “Memoria” usando el link “Aumentar Reserva de Memoria”
   `MemoryAccounting=true`
    `MemoryMax=16M`
    
# D - AppArmor
9. Instale las herramientas de espacio de usuario, perfiles por defecto de app-armor y
auditd (necesario para generar perfiles de forma interactiva).
`apt install apparmor apparmor-profiles apparmor-utils auditd`
10. Verifique si apparmor se encuentra habilitado con el comando aa-enabled. Si no se encuentra habilitado verifique el kernel que está ejecutando (el kernel de Debian de la VM lo trae habilitado por defecto).
11. Utilice la herramienta aa-status para determinar:
a. ¿Cuántos perfiles se encuentran cargados? `32 profiles are loaded`
b. ¿Cuántos procesos y cuáles procesos de tu sistema tienen perfiles definidos? `2 processes have profiles defined. 2 processes are in enforce mode.`
12. Detenga y deshabilite el servicio insecure_service creado en la parte 1 de la práctica de forma que no vuelva a iniciarse automáticamente.
systemctl stop insecure_service.service
systemctl disable insecure_service.service
13. Ejecute insecure_service manualmente usando el usuario root y verifique que puede acceder libremente al filesystem en http://localhost:8080 (o la IP correspondiente donde se ejecuta el servicio).
/opt/sistemasoperativos/insecure_service


14. Generación de un nuevo profile:
a. Ejecutar aa-genprof /…
b. Abrir otra terminal, ejecutar insecure_service y navegue el sistema de archivos usando la interfaz web provista por el servicio.
c. Genere un perfil que permita:
i. Abrir conexiones tcp ipv4
ii. Abrir conexiones tcp ipv6
iii. El perfil debe incluir los siguientes perfiles (y ningún otro):
15. include <abstractions/base>
16. include <abstractions/nameservice>
iv. Listar el contenido de / y /proc pero no de otros subdirectorios de /
v. Ejecutar con los permisos del perfil actual (mrix) los siguientes
comandos:
17. /usr/bin/dash
18. /usr/bin/ip
19. /usr/bin/mawk
20. /usr/bin/ps
21. Habilite el modo enforcing y verifique si funciona (aa-enforcing).
22. Si necesita volver a generar un perfil puede usar aa-complain + aa-logprofile o editar el profile a mano y aplicar con apparmor_parser -r

# E - CopyFail
1. Investigue la vulnerabilidad CopyFail (ver links en practica5/copy_fail/README.md)
a. ¿Es una vulnerabilidad en espacio de usuario o del kernel?
Es una vulnerabilidad **del Kernel**. Ocurre específicamente en el subsistema de gestión de memoria virtual (MMS) y el manejo de tuberías (_pipes_). El kernel falla al gestionar el mecanismo de **Copy-on-Write (CoW)** (Copia en Escritura) cuando se transfieren páginas de memoria con banderas específicas, permitiendo que un proceso de usuario escriba directamente sobre páginas de memoria mapeadas que deberían ser de solo lectura
b. ¿Por qué el exploit abre un archivo con suid?
El exploit necesita mapear en memoria un binario que pertenezca a `root` y tenga el bit SUID activo (como `/bin/su` o `/usr/bin/passwd`). Al abrir y mapear este archivo, el exploit aprovecha el bug del kernel para modificar la memoria RAM donde está cargado el código ejecutable de ese binario privilegiado. Reemplaza las instrucciones originales de validación (por ejemplo, la comprobación de la contraseña) por un _payload_ que directamente ejecuta `/bin/sh`.
c. ¿La página modificada se almacena luego en disco?
**No.** La modificación ocurre exclusivamente en el **Page Cache**
2. Ejecute en exploit copy_fail_exp.py en la máquina virtual de la cátedra y pruebe lo siguiente:
a. Verifique que obtuvo root 
> chi

b. Abra otra terminal con el usuario “so” y ejecute el comando “su” ¿Qué sucedió? ¿Por qué?
me dio root sin pedir contraseña porque la version de su que esta cargada en memoria esta sobreescrita por copyfail
c. Reinicie la máquina virtual, abra una terminal con el usuario “so” y ejecute el
comando “su” ¿Qué sucedió? ¿Por qué?
me da paja. Me va a pedir la contraseña porque al apagar la pc se vacia la memoria y cualquier modificacion que se hizo en cache desaparece y al recuperar de nuevo de disco se queda con la version no modificada 
3. ¿Por qué se dice que puede permitir saltar el aislamiento de containers?
   - Los contenedores de Docker **comparten el mismo Kernel** de la máquina host. No son máquinas virtuales con sistemas operativos independientes. Si un contenedor logra corromper el _Page Cache_ del Kernel compartido, esa corrupción altera la memoria de los archivos para el host y para todos los demás contenedores que estén corriendo en esa máquina.
4. ¿Qué condiciones deben darse para que CopyFail permita afectar el sistema host si se ataca un container?
   El contenedor atacante debe poder acceder o compartir de alguna forma el mismo archivo binario ejecutable (o una librería compartida como `libc`) que el host utilice, y el kernel del host debe ser vulnerable al bug de omisión de CoW (Copy-on-Write).
5. Laboratorio con docker compose (en la VM de la cátedra):
a. Compile `superuser.c` con el comando `make`. Asegúrese que el binario generado tenga propietario root y suid. El contenido del programa no es importante, No necesariamente tiene que tener alguna vulnerabilidad, podría ser cualquier binario con SUID + propietario root. 
b. Ejecute lo servicios con `docker-compose up --build -d`
c. Abra un shell en el container “attacker”: `docker exec -it attacker /bin/bash`
i. Verifique que el comando “superuser” pide una contraseña y si la contraseña no es  ingresada correctamente no da permisos de root.
ii. Ejecute el exploit “copy_fail_exp_for_docker.py”
iii. Verifique si obtuvo root
d. Abra un shell en victim “docker exec -it victim /bin/bash”
i. Ejecute “superuser”
ii. ¿Qué sucedió? ¿Por qué no pide password? dio root automaticamente. Porque ambos contenedores montan el mismo archivo binario `superuser` (o comparten las mismas librerías de memoria subyacentes mapeadas en el Page Cache del Kernel del Host). Al corromperse la página en la RAM del Kernel único, la vulnerabilidad se replica instantáneamente en el contenedor vecino.
iii. Ejecute el siguiente oneliner:
mount /dev/sda1 /mnt/ && touch /mnt/home/so/me_escape_del_container
e. Desde el usuario “so” de la VM ejecute “ls $HOME” ¿Qué sucedió?
El comando _oneliner_ monta el disco real de la máquina virtual (`/dev/sda1`) dentro del contenedor y crea un archivo en el Home del usuario de la VM. Al ejecutar `ls $HOME` en el host, vas a ver el archivo `me_escape_del_container`. El aislamiento fue totalmente vulnerado.
6. ¿Qué nos permite que el container “victim” se ejecute con --privileged?
   La bandera `--privileged` le da al contenedor capacidades de hardware casi idénticas a las del host. Le permite acceso directo a los archivos de dispositivos en `/dev/`. Gracias a esto, el contenedor `victim` pudo ver el disco físico de la VM (`/dev/sda1`) y montarlo (`mount`), algo que un contenedor estándar tiene estrictamente prohibido por políticas de seguridad de Docker.
7. ¿Por qué si el exploit se ejecutó en un container también afecta al otro container?
   Porque la vulnerabilidad **no ocurre dentro del entorno aislado del contenedor, sino en las estructuras de datos del Kernel del Host**. Al mutar una página del _Page Cache_ del núcleo central, y dado que ambos contenedores dependen del mismísimo mapa de memoria virtual gestionado por ese único Kernel, el cambio se propaga de forma global e instantánea a cualquier rincón del sistema que intente leer ese recurso.

# Secrets en Docker Compose
1. Investigue los secrets de docker-compose
a. ¿Qué ventajas tienen sobre las env vars?
- **No se filtran en los logs de procesos:** Las variables de entorno son visibles para cualquiera que pueda ejecutar `ps aux` o revisar `/proc/[PID]/environ`. Los secretos, al mapearse como archivos temporales, no aparecen allí.
- **No se heredan por defecto:** Las variables de entorno se heredan automáticamente a los procesos hijos (por ejemplo, si tu app levanta un script secundario o una shell, ese script hereda las claves). Los secretos requieren acceso explícito al sistema de archivos.
- **Ciclo de vida en memoria:** Docker monta los secretos en sistemas de archivos temporales en memoria RAM (`tmpfs`), garantizando que las credenciales nunca se escriban físicamente en el disco del host o de la imagen.
b. ¿Cómo se comparan con los secrets en docker swarm?
- **Docker Swarm:** Los secretos son nativos y centralizados. El mánager de Swarm los cifra en tránsito y en reposo (usando la base de datos Raft cifrada) y solo los envía al nodo _worker_ que los necesita en memoria RAM en el momento exacto en que levanta el contenedor.
- **Docker Compose (Local):** Es una simulación para entornos de desarrollo. Compose lee un archivo de texto plano local del host y lo monta dentro del contenedor en la ruta `/run/secrets/`. No hay cifrado real en reposo a nivel de orquestador; si alguien tiene acceso al host, puede leer el archivo original.
2. Verifique el archivo docker-compose.yaml en practica5/secrets
3. Ejecute los servicios con `docker-compose up --build` e:
a. Ejecute un shell dentro de vulnerable-insecure (docker exec -it vulnerable-insecure /bin/bash)
i. Ejecute el comando “env” y verifique si puede ver el los passwords y api keys
ii. ¿Qué procesos pueden ver esas variables de entorno?
**Cualquier proceso** que corra dentro de ese contenedor . Además, desde fuera del contenedor (en el Host), un administrador o atacante con privilegios puede tirar `docker inspect vulnerable-insecure` o leer `/proc/[PID]/environ` del proceso de la VM y extraer las credenciales sin ingresar al contenedor.
b. Ejecute un shell dentro de vulnerable-secure (docker exec -it vulnerable-secure /bin/bash)
i. Ejecute el comando “env” y verifique si puede ver el los passwords y api keys
ii. Ejecute el comando mount y busque la ubicación de los secrets
iii. Verifique los permisos del directorio de secrets dentro del container y responda: ¿Qué procesos pueden leer los archivos de secrets?
Por defecto, Docker monta estos archivos con permisos globales de lectura (`0444` o `r--r--r--`) pertenecientes al usuario `root`. Esto significa que **cualquier proceso dentro del contenedor puede leerlos** si sabe la ruta del archivo, pero el atacante ahora está obligado a tener una vulnerabilidad de "lectura de archivos" (_Local File Inclusion_) para llegar a ellos; ya no puede simplemente spawnear una shell ciega y mirar el entorno.
iv. Investigue en https://docs.docker.com/reference/compose-file/services/#secrets como asignar permisos usando UGO para los secrets.
Para mitigar el problema de que _cualquier_ usuario del contenedor pueda leer el archivo en `/run/secrets/`, la especificación de Docker Compose te permite ajustar de forma quirúrgica los permisos usando la sintaxis **UGO** (propietario, grupo, otros). En la definición del secreto, para que un solo usuario pueda verlo: 
```yml
services:
  vulnerable-secure:
    image: vulnerable-secure-image
    build: .
    secrets:
      - source: my_api_key
        target: api_key.txt
        uid: '1000'    # ID del usuario propietario dentro del container
        gid: '1000'    # ID del grupo propietario
        mode: 0400     # Permisos estrictos: Solo lectura para el Dueño (U=r, G=-, O=-)

secrets:
  my_api_key:
    file: ./secrets/api_key.txt
```

Al usar `mode: 0400`, si un atacante logra comprometer un proceso secundario que corra como el usuario `nobody` o cualquier otro UID intermedio, cuando intente hacer un `cat /run/secrets/api_key.txt` el sistema operativo le arrojará un rotundo **"Permission denied"**, aplicando a la perfección el Principio de Menor Privilegio (POLA).