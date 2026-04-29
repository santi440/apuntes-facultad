# Parte 1 : Conceptos teóricos
## Ej 1 - virtualización

Defina virtualización. Investigue cuál fue la primera implementación que se realizó.

La virtualización es una tecnología que permite crear una capa de abstracción sobre el hardware físico para dividir sus recursos (CPU, memoria, almacenamiento) en múltiples entornos virtuales independientes, conocidos como **Máquinas Virtuales (VM)**.

No me van a tomar esto.
**CP-40 (1964-1967):** Fue el primer sistema operativo que implementó **virtualización completa**. Desarrollado en el Centro Científico de Cambridge de IBM, permitía crear hasta 14 "pseudo-máquinas" sobre un mainframe IBM System/360 Modelo 40.

## Ej 2 - Virtualizar vs emular
¿Qué diferencia existe entre virtualización y emulación?

En la virtualización, el sistema operativo invitado (Guest) corre sobre hardware que es **idéntico o muy similar** al del anfitrión (Host).

En la emulación, el sistema simula un hardware **completamente diferente** al que realmente tiene la máquina.

## Ej 3 - Hypervisor
a) ¿Qué es un hypervisor?
Un **hipervisor** (también conocido como Monitor de Máquina Virtual o VMM) es el software o firmware que hace posible la virtualización. Su función principal es crear y ejecutar máquinas virtuales (VM), actuando como una capa intermedia entre el hardware físico y los sistemas operativos invitados.
Tipos de hipervisor:
- Tipo 1: Bare Metal (Nativo): Se instala directamente sobre el hardware físico de la máquina, sin necesidad de un sistema operativo previo.
- Tipo 2: Hosted:  Se instala como una aplicación más dentro de un sistema operativo convencional

b) ¿Qué beneficios traen los hypervisors? ¿Cómo se clasifican?
### Beneficios Principales
- **Eficiencia de Costos:** Antes se necesitaba un servidor físico para cada servicio (correo, base de datos, web). Hoy podés tener todo en un solo equipo potente, ahorrando en hardware, electricidad y refrigeración.
- **Aislamiento:** Es clave para la seguridad. Si la VM donde estás probando un script se rompe o es atacada, el hipervisor garantiza que el resto de las máquinas y el hardware real queden intactos.
- **Snapshots (Instantáneas):** Es como un "punto de guardado" de un videojuego. Antes de probar una configuración arriesgada en una red, sacás un snapshot. Si algo falla, volvés al estado anterior en segundos.
- **Escalabilidad Dinámica:** Podés asignar más memoria o núcleos de CPU a una VM según la demanda sin tener que desarmar el servidor.
- **Portabilidad:** Como las máquinas virtuales son básicamente archivos (ej. `.vmdk` o `.vdi`), podés moverlas entre diferentes servidores físicos con facilidad.

## Ej 4 - full virtualizacion y asistida
¿Qué es la full virtualization? ¿Y la virtualización asistida por hardware?

1. Full Virtualización: En la virtualización completa, el sistema operativo invitado (Guest) está **totalmente aislado** y no sabe que se está ejecutando en una máquina virtual. Cree que tiene acceso directo al hardware físico, cuando en realidad el hipervisor está mediando todo.
2. Virtualización aislada del hardware: El procesador añade una nueva jerarquía de privilegios (a veces llamada "Anillo -1"). El hipervisor se sitúa ahí, y el hardware mismo se encarga de avisarle cuando una VM intenta hacer algo crítico. Ya no hace falta "traducir" el código por software; el procesador lo gestiona de forma nativa.

## Ej 5 - traduccion binaria o capturar y emular
¿Qué implica la técnica binary translation? ¿Y trap-and-emulate?

### Trap and emulate
Es la técnica más clásica y directa. Se basa en que el procesador genere una excepción cuando algo no sale como debería.
- El sistema operativo invitado (Guest) intenta ejecutar una instrucción privilegiada (ej. modificar la tabla de páginas de memoria).
- Como el Guest no está en el **Ring 0**, el procesador físico detecta que no tiene permisos para hacer eso en modo usurio. Esto genera un **Trap** (una interrupción o trampa).
- El **Hipervisor** captura ese error, analiza qué quería hacer el Guest, lo ejecuta él mismo de forma segura en el hardware real y le devuelve el resultado al Guest.
> No todas las instrucciones de x86 generan un "trap" cuando se ejecutan sin privilegios; algunas simplemente fallan silenciosamente o devuelven datos basura, lo que rompería la VM. Para solucionar este bache, nació la siguiente técnica.

### binary translation
En lugar de esperar a que ocurra un error, el hipervisor analiza el código antes de que se ejecute.

- **El proceso:**
    1. El hipervisor escanea el código binario del sistema invitado mientras se está cargando.
    2. Identifica las instrucciones "problemáticas" (aquellas que no generan _trap_ o que son sensibles).
    3. **Reemplaza** esas instrucciones en tiempo real por un pequeño bloque de código seguro (llamado _fragmento de traducción_) que hace lo mismo pero bajo el control del hipervisor.
    4. El resto del código (instrucciones comunes de usuario como sumas o restas) se deja pasar directamente al hardware para que corra a máxima velocidad.
- **Dato :** Se dice que es una traducción **dinámica**, porque ocurre mientras el software corre.

## Ej 6 - Paravirtualizacion
Investigue el concepto de paravirtualización y responda:
(a) ¿Qué es la paravirtualización?

Es un método de virtualización donde el sistema operativo invitado (Guest) **se comunica activamente** con el hipervisor a través de llamadas especiales denominadas **Hypercalls**.
En la paravirtualización el SO invitado "pide permiso" o solicita la ejecución de esas tareas directamente al hipervisor a través de una API. Esto elimina la necesidad de emular hardware complejo o traducir código en tiempo real.

(b) Mencione algún sistema que implemente paravirtualización.

- **Xen:** Es el pionero y el máximo exponente de esta técnica. Fue diseñado originalmente bajo este concepto, permitiendo ejecutar kernels de Linux modificados para ser "paravirtualizados".
- **KVM (Kernel-based Virtual Machine):** Aunque es un hipervisor de tipo 1 que usa virtualización asistida por hardware, utiliza **drivers VirtIO**. Estos drivers son una forma de paravirtualización para el manejo de disco y red, optimizando drásticamente el rendimiento.
- **IBM z/VM:** En los mainframes de IBM, esta técnica se utiliza desde hace décadas para permitir que los sistemas operativos se comuniquen de forma eficiente con el hardware.
> el ultimo no esta en la teoria, ni idea

(c) ¿Qué beneficios trae con respecto al resto de los modos de virtualización?
- **Rendimiento superior (Bajo Overhead):** Al no tener que interceptar errores o traducir binarios, la comunicación es mucho más fluida. El sistema operativo "colabora" en lugar de "pelear" por el hardware.
- **Menor complejidad del Hipervisor:** El hipervisor no necesita ser un emulador perfecto de hardware real; solo necesita exponer una API (interfaz) para que los SO invitados la usen.
- **Optimización de I/O (Entrada/Salida):** Es donde más se nota. El acceso a disco y red es casi tan rápido como en una máquina física, ya que el SO invitado no usa "drivers genéricos" emulados, sino drivers específicos que pasan los datos directamente al hipervisor.
- **Mejor gestión de recursos:** El SO invitado puede informar al hipervisor sobre qué memoria RAM no está usando, permitiendo que el hipervisor la asigne a otra VM de forma más inteligente (técnica conocida como _Ballooning_).

> [!WARNING] Desventaja
> A diferencia de la virtualización completa, la paravirtualización requiere **modificar el kernel** del sistema operativo invitado. Por eso es muy común en Linux (donde el código es abierto), pero fue históricamente difícil de implementar en Windows, ya que Microsoft no permitía modificar su núcleo para estas "llamadas al hipervisor".

## Ej 7 - contenedores
Investigue sobre containers y responda:
(a) ¿Qué son?

Un contenedor es una unidad estándar de software que empaqueta el código de una aplicación y todas sus dependencias (bibliotecas, archivos de configuración, entorno de ejecución) para que la aplicación se ejecute de forma rápida y confiable en cualquier entorno informático. A diferencia de una VM, los contenedores **comparten el kernel del sistema operativo anfitrión** y solo aíslan el espacio de usuario.

(b) ¿Dependen del hardware subyacente?

- **Abstracción:** El contenedor depende del **Kernel del SO**, no del hardware físico. Si tenés Docker en una laptop con un procesador Intel o en un servidor con AMD, el contenedor funcionará igual siempre que el Kernel sea compatible.
- **Limitación de Arquitectura:** Existe una dependencia indirecta: un contenedor construido para una arquitectura de CPU (ej. **x86_64**, linux o windows) no correrá nativamente en una arquitectura diferente (ej. **ARM** de una Raspberry Pi) a menos que se use emulación, ya que el binario de la aplicación debe ser compatible con las instrucciones del procesador.

(c) ¿Qué lo diferencia por sobre el resto de las tecnologías estudiadas?

- aislamiento: en un contenedor virtualizan el so a nivel de proceso. Mientras que el hypervisor tiene que virtualizar a nivel de hardware (todo el equipo)
- SO: cada vm tiene su propio so, el contenedor comparten el mismo SO anfitrión.
- Velocidad de inicio: el vm tarda minutos contra milisegundos del contenedor debido a lo liviano de los contenedores.
- Eficiencia: una vm tiene poca o nada eficiencia (considerar que tiene que duplicar recursos). El contenedor es mucho más eficiente, usa lo que la app necesita.

(d) Investigue qué funcionalidades son necesarias para poder implementar containers
- **Namespaces (Espacios de nombres):** Es lo que proporciona el **aislamiento**. Permiten que un proceso crea que tiene su propio árbol de procesos, su propia red, sus propios usuarios y sus propios sistemas de archivos, cuando en realidad está compartiendo todo con otros.
- **Cgroups (Control Groups):** Es lo que proporciona la **gestión de recursos**. Permiten limitar y monitorear cuánto CPU, memoria RAM y ancho de banda de red puede usar un contenedor, evitando que uno solo "se robe" todos los recursos del servidor físico.
- **Chroot / Pivot_root:** Funcionalidades que permiten cambiar el directorio raíz del proceso, haciendo que el contenedor vea solo sus propias carpetas y no las del sistema operativo real.
- **Union File Systems (UnionFS):** Permite manejar capas de archivos eficientes (como las imágenes de Docker), donde solo se guardan los cambios sobre una base común.

# Parte 2: chroot, Control Groups y Namespaces
## chroot
### Ej 1
¿Qué es el comando chroot? ¿Cuál es su finalidad?

El comando `chroot` (abreviatura de **change root**) permite cambiar el directorio raíz operativo para un proceso y sus hijos especificándole que directorio será su nuevo "/". Para el programa ejecutado dentro de este entorno, cualquier archivo o carpeta fuera de ese directorio designado es **invisible e inaccesible**.

> El entorno reducido se lo llama chroot jail 

lo tiene que crear un root y tenes la desventaja que escapar de ese jail es una pelotudes, por ejemplo podes montar el disco original o ver los procesos, encontrarte con el init y de ahi ver la estructura de las carpetas del root. SACALE PERMISOS DE ROOT Y PASALO A OTRO USUARIO. 

### Ej 2 - caso practico chroot

Crear un subdirectorio llamado sobash dentro del directorio root. Intente ejecutar el
comando chroot /root/sobash. ¿Cuál es el resultado? ¿Por qué se obtiene ese
resultado?
```bash
/usr/sbin/chroot /root/sobash
/usr/sbin/chroot: failed to run command ‘/bin/bash’: No such file or **directory**
```

como le estoy especificando que ahora su directorio raíz es /root/sobash, espera encontrarse el código binario como para ejecutar al menos una terminal y la carpeta esta vacia :(

### Ej 3 - mkdirs
Cree la siguiente jerarquía de directorios dentro de sobash:
```bash
sobash/
├── bin
├── lib
│ └── x86_64-linux-gnu
└── lib64
```
clave 4 mkdir, no tiene misterio

### Ej 4 - linux-vdso.so.1
Verifique qué bibliotecas compartidas utiliza el binario /bin/bash usando el comando ldd
/bin/bash.¿En qué directorio se encuentra linux-vdso.so.1? ¿Por qué?

```bash
root@debian:~/sobash#  ldd /bin/bash
	linux-vdso.so.1 (0x00007ffd3af7e000)
	libtinfo.so.6 => /lib/x86_64-linux-gnu/libtinfo.so.6 (0x00007fc209e63000)
	libdl.so.2 => /lib/x86_64-linux-gnu/libdl.so.2 (0x00007fc209e5d000)
	libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007fc209c98000)
	/lib64/ld-linux-x86-64.so.2 (0x00007fc209fe0000)
```

El caso de **`linux-vdso.so.1`**, a diferencia de las otras bibliotecas que listaste, **no existe como un archivo en tu disco rígido**.
¿En qué directorio se encuentra?
	En ninguno. No vas a encontrar un archivo llamado `linux-vdso.so.1`
### ¿Por qué?
**vDSO** significa _Virtual Dynamic Shared Object_. Es una biblioteca compartida que el **Kernel de Linux** inyecta automáticamente en el espacio de memoria de cada proceso que se inicia.
Las razones principales de su existencia son:
- **Rendimiento (System Calls rápidas):** Algunas llamadas al sistema (como obtener la hora actual con `gettimeofday` o consultar el tiempo con `clock_gettime`) son solicitadas miles de veces por segundo.
- **Evitar el "Context Switch":** Normalmente, para que un programa hable con el hardware, el procesador debe cambiar del "Modo Usuario" al "Modo Kernel", lo cual es una operación costosa en términos de ciclos de CPU.
- **La solución:** El Kernel coloca estas funciones básicas en una porción de memoria (la vDSO) que el programa puede leer directamente en "Modo Usuario" sin necesidad de interrumpir al procesador.
### Ej 5 - ahora si anda el bash
Copie en /root/sobash el programa /bin/bash y todas las librerías utilizadas por el
programa bash en los directorios correspondientes. Ejecute nuevamente el comando
chroot ¿Qué sucede ahora?

```bash
# Definimos la carpeta
export TARGET=/root/sobash
mkdir -p $TARGET

# Copiamos las librerías (el awk limpia la salida de ldd para obtener las rutas)
for i in $(ldd /bin/bash | grep -v linux-vdso | awk '{print $3}' | grep /); do 
    sudo mkdir -p $TARGET$(dirname $i); 
    sudo cp $i $TARGET$i; 
done

# Copiamos el cargador dinámico (el que aparece solito al final de ldd)
sudo mkdir -p $TARGET/lib64
sudo cp /lib64/ld-linux-x86-64.so.2 $TARGET/lib64/

# Copiamos el binario de bash
sudo mkdir -p $TARGET/bin
sudo cp /bin/bash $TARGET/bin/
```
me daba paja copiar cosas a mano jajajaja

Habemus terminales: 
![[Pasted image 20260427213832.png]]

aunque bastante pelada porque no puedo ejecutar comandos (no tengo los binarios je)
### Ej 6 - binarios faltantes
¿Puede ejecutar los comandos cd "directorio" o echo? ¿Y el comando ls? ¿A qué se
debe esto?
En mi defensa, lo contesté sin querer en el anterior. 
- cd y echo si porque son comandos internos de la bash (dentro del binario que copiamos). Entre ellos tenemos `cd`, `echo`, `pwd`, `exit` y `type` 
- comandos como el ls, Bash lo que hace es intentar ir a buscar los binarios a `/usr/bin` o `/bin` y al no estar definidos en su "/" me tira un hermoso `bash: ls: command not found`
### Ej 7 - pwd en chroot
¿Qué muestra el comando pwd? ¿A qué se debe esto?
```bash
/
```
pwd te muestra el directorio actual parado desde tu directorio raíz, como especificamos que /root/sobash/ -> / , interpreta que estoy parado en / 
### 8 - salir de chroot
Salir del entorno chroot usando exit

### 9 - busybox
Usando el repositorio de la cátedra acceda a los materiales en practica4/02-chroot:
a. Verifique que tiene instalado busybox en /bin/busybox

b. Cree un chroot con busybox usando /buildbusyboxroot.sh

tuve que cambiar el script, casi fin de archivo 
```
/usr/sbin/chroot busyboxroot /bin/busybox --install -s /bin
```
dios sabra porque pero no me encuentra el binario de chroot y le tengo que pegar el path (supongo que no esta como variable de entorno $PATH o alguna falopeada asi) pero anda :

```bash 
root@debian:~/sobash/codigo-para-practicas/practica4/02-chroot# ./buildbusyboxroot.sh
	linux-vdso.so.1 (0x00007ffd68dd0000)
	libresolv.so.2 => /lib/x86_64-linux-gnu/libresolv.so.2 (0x00007f6c64e3a000)
	libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007f6c64c75000)
	/lib64/ld-linux-x86-64.so.2 (0x00007f6c64f19000)
BusyBox root filesystem created in /root/sobash/codigo-para-practicas/practica4/02-chroot/busyboxroot
You can now chroot into it with:
chroot /root/sobash/codigo-para-practicas/practica4/02-chroot/busyboxroot /bin/sh
```

c. Entre en el chroot

mismo caso, agrego adelante de chroot el resto del path absoluto /usr/sbin/...

d. Busque el directorio /home/so ¿Qué sucede? ¿Por qué?

el directorio no existe dentro de la carpeta busybox que me creo por lo que no puedo acceder a dicho directorio.

e. Ejecute el comando “ps aux” ¿Qué procesos ve? ¿Por qué (pista: ver el
contenido de /proc)?

no se que esperas pero esta vacio? supongo eso, debido a que no hay procesos corriendo dentro del chroot y solo me creo la carpeta a mano con un mkdir.

f. Monte /proc con “mount -t proc proc /proc” y vuelva a ejecutar “ps aux” ¿Qué
procesos ve? ¿Por qué?

todos los procesos que se están ejecutando en el sistema operativo anfitrión.
- En Linux, `/proc` es un **sistema de archivos virtual** generado por el Kernel. No contiene archivos reales en el disco, sino que es una interfaz que el Kernel usa para exponer información sobre el estado del sistema y los procesos activos.
- con mount le estoy diciendo que monte en la carpeta /proc (la de mi chroot) ese sitema de archivos. Como el Kernel es el mismo para todo el sistema, simplemente te muestra la realidad global de lo que está manejando en ese momento.

g. Acceda a /proc/1/root/home/so ¿Qué sucede?

```bash
# me muestra los directorios de redes (unico usuario en el sistema)
ls /proc/1/root/home/redes/
```
no me deja hacer cd, no se por que:
**Qué sucede:** No se puede navegar (`cd`) libremente hacia afuera debido a las restricciones de seguridad del Kernel sobre los descriptores de archivos de `/proc`, pero es posible **listar o leer** archivos si los permisos lo permiten.

h. ¿Qué conclusiones puede sacar sobre el nivel de aislamiento provisto por
chroot?

El aislamiento de `chroot` es **extremadamente débil**. Si un proceso dentro de la jaula tiene privilegios suficientes para montar `/proc`, puede usarlo como un "túnel" para inspeccionar y potencialmente acceder a todo el sistema de archivos real, rompiendo la premisa de seguridad del entorno aislado.

## Control Groups
### preparación 
1. Editar /etc/default/grub:
	Cambiar:
		GRUB_CMDLINE_LINUX="quiet"
	Por:
		GRUB_CMDLINE_LINUX="quiet systemd.unified_cgroup_hierarchy=0"
	Asumi que era la DEFAULT, ni idea
2. Actualizar la configuración de GRUB:
	sudo update-grub
	Ya me jodio lo suficiente, agregar al path la carpeta sbin se hace 
	export PATH="$PATH:/usr/sbin:/sbin" 
3. Reiniciar la máquina.
4. Verificar que se esté usando CGroups 1. Para esto basta con hacer “ls /sys/fs/cgroup/”
Se deberían ver varios subdirectorios como cpu, memory, blkio, etc. (en vez de todo montado
de forma unificada).
A continuación se probará el uso de cgroups. Para eso se crearán dos procesos que
compartirán una misma CPU y cada uno la tendrá asignada un tiempo determinado.
Nota: es posible que para ejecutar xterm tenga que instalar un gestor de ventanas. Esto puede hacer con apt-get install xterm.

### Ej 1 - cgroups montados
¿Dónde se encuentran montados los cgroups? ¿Qué versiones están disponibles
los cgroups se encuentran montados como un sistema de archivos virtual en:
`/sys/fs/cgroup`
Si ejecutás el comando `mount | grep cgroup` en tu terminal, verás algo como esto:
- En **cgroup v1**: Verás múltiples puntos de montaje, uno por cada controlador (ej. `/sys/fs/cgroup/cpu`, `/sys/fs/cgroup/memory`, `/sys/fs/cgroup/pids`).
- En **cgroup v2**: Verás un único punto de montaje unificado en `/sys/fs/cgroup/cgroup2` o simplemente en `/sys/fs/cgroup`.
- ![[Pasted image 20260427223905.png]]
Dos tipos:
#### **cgroup v1 (El modelo jerárquico separado)**
Fue la primera implementación estable.
- **Características:** Cada recurso (CPU, RAM, red) vive en su propia jerarquía independiente  
- **Problema:** Es difícil coordinar recursos. Por ejemplo, si querés limitar el uso de disco y memoria de forma conjunta para un proceso, las jerarquías no "hablan" bien entre sí.
    
#### **cgroup v2 (La jerarquía unificada)**
Es la versión moderna (lanzada alrededor de 2016 pero estándar desde 2020-2021).
- **Características:** Hay una única jerarquía para todos los recursos. Todos los procesos están en el mismo lugar de la estructura para todos los controladores.
- **Beneficio:** Es mucho más eficiente y seguro. Permite un control mucho más granular (como el límite de I/O de disco basado en latencia).

### Ej 2 - controlador
¿Existe algún controlador disponible en cgroups v2? ¿Cómo puede determinarlo?
### Controladores comunes en cgroups v2
Los controladores más importantes que suelen estar disponibles son:
- **cpu:** Regula la distribución de ciclos de CPU (pesos y límites máximos).
- **memory:** Controla el uso de memoria RAM y swap.
- **io:** Gestiona el ancho de banda y las operaciones por segundo (IOPS) de los discos.
- **pids:** Limita la cantidad de procesos o hilos que pueden crearse dentro del grupo (evita bombas _fork_).
- **cpuset:** Permite asignar CPUs o nodos de memoria específicos a un grupo de procesos.
- **hugetlb:** Controla el uso de páginas de memoria grandes.

```bash
cat /sys/fs/cgroup/cgroup.controllers
```
Este archivo muestra todos los controladores que el Kernel tiene habilitados y que están listos para ser usados.

```bash
cat /sys/fs/cgroup/cgroup.subtree_control
```
Para ver cuáles están habilitados específicamente para los procesos "hijos" de un grupo, mirá este archivo

## Ej 3 - rm controlador
Analice qué sucede si se remueve un controlador de cgroups v1 (por ej. Umount /sys/fs/cgroup/rdma).