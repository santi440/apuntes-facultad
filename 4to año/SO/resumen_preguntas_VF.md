# Resumen de preguntas Verdadero/Falso — Sistemas Operativos

---

## 1. Hilos: ULT vs KLT

| Concepto | ULT (User-Level Threads) | KLT (Kernel-Level Threads) |
|---|---|---|
| Quién los gestiona | Librería de usuario (sin que el kernel lo sepa) | El kernel directamente |
| Modelo | N:1 (varios ULT → 1 KLT) | 1:1 (cada hilo = 1 KLT) |
| ¿El kernel los conoce? | No | Sí |
| Syscall bloqueante | Bloquea **todo el proceso** | Bloquea **solo ese hilo** |
| Paralelismo real | No (el proceso se ve como 1 unidad) | Sí (pueden ejecutar en distintos núcleos) |
| Planificación | Round-Robin, prioridades, cooperativo (non-preemptive) | Planificador del kernel (scheduler) |

### Mecanismos de sincronización
- `pthread_mutex_t` — mutex
- `pthread_cond_t` — variables de condición
- `pthread_barrier_t` — barreras
- `sem_init` — semáforos
- `pthread_delay_np` — sleep a nivel hilo
- `sleep()` — sleep a nivel **proceso** (duerme todo el proceso y todos sus hilos)

### Funciones clave
- `getpid()` → PID del proceso
- `getppid()` → PPID (parent PID)
- `gettid()` → TID a nivel de kernel
- `pthread_self()` → ID a nivel de **librería** (NO el TID)
- `pthread_create()` → a nivel kernel invoca `clone()`
- `pthread_join()` → espera al hilo y recupera recursos

### GIL (Global Interpreter Lock)
- Mutex global en Python/Ruby que serializa la ejecución de bytecodes
- **CPU-bound**: impacto severo (no hay paralelismo real)
- **I/O-bound**: impacto mínimo (GIL se libera en operaciones de E/S)
- Recomendación en CPython: usar **procesos** (`multiprocessing`) en vez de hilos para tareas CPU-bound

### fork() vs exec()
- `fork()`: crea proceso hijo idéntico → asigna **nuevo PID**
- `exec()`: reemplaza la imagen de memoria → **NO asigna nuevo PID**
- **Copy-On-Write (COW)**: padre e hijo comparten páginas como solo lectura; al escribir se crea copia privada

### Procesos zombie vs huérfanos
- **Zombie**: hijo terminó, padre no hizo `wait()`. Ocupa entrada en tabla de procesos.
- **Huérfano**: padre terminó antes que el hijo. `init` (PID 1) lo adopta y hace `wait()`.

### strace
- `strace -f` necesario para seguir KLT secundarios creados con `clone()`
- Sin `-f`, solo muestra syscalls del hilo principal
- En ULT, strace sin `-f` muestra todas las syscalls porque todos corren en el contexto del mismo proceso

### Flags de clone()
- `CLONE_THREAD`: mismo grupo de hilos (mismo PID, distinto TID)
- `CLONE_VM`: comparte espacio de direcciones de memoria

**Archivos:**
- `practica/Practica 3 real/01- Parte teorica.md`
- `practica/Practica 3 real/02 - Parte practica.md`
- `practica/explicacion 3 - Hilos.md`
- `teoria/03 - Threads.md`

---

## 2. System Calls

- **Definición**: Mecanismo por el cual un proceso de usuario solicita un servicio al SO
- Es la **API** del SO hacia los procesos de usuario
- Un proceso en **modo usuario** tiene su propio espacio de direcciones y **no puede acceder al hardware directamente**
- **Máximo 6 parámetros** para la syscall (7 en la macro `syscall()` de glibc contando el número de syscall)
- **GNU C Library (glibc)**: provee funciones que se llaman igual en Windows y Linux → portabilidad
- Llamar directamente a una syscall **pierde portabilidad**
- **HAL (Hardware Abstraction Layer)**: forma de trabajar con Plug and Play en Windows

### Macros del kernel
- `SYS_CALL_DEFINEx` (x = 0..6): declara syscalls. El número indica la cantidad de argumentos.
  - Ej: `SYS_CALL_DEFINE2(mi_syscall, int, a, int, b)` → syscall con 2 argumentos
- `for_each_process(task)`: recorre **todos los procesos** del sistema
- `for_each_thread(task, thread)`: recorre **los hilos de un proceso específico**
- `copy_to_user(dest, src, n)`: copia datos del **kernel al usuario** (verifica direcciones)
- `copy_from_user(dest, src, n)`: copia datos del **usuario al kernel** (verifica direcciones)
- `printk()`: imprime al log del kernel (visible con `dmesg`). `printf()` es de glibc (espacio usuario), no se usa en kernel

### strace
- Intercepta y registra todas las syscalls de un proceso
- `strace -p <PID>`: monitorear proceso en ejecución
- `strace -o salida.txt ./programa`: guardar salida a archivo

### ausyscall (paquete auditd)
- `ausyscall write` → devuelve el número (1 en x86_64)
- `ausyscall 60` → devuelve `exit`
- `ausyscall --dump` → lista todas

### Tabla de syscalls
- `arch/x86/entry/syscalls/syscall_64.tbl` (x86_64)
- Mapea: número → nombre → función interna del kernel

**Archivos:**
- `teoria/03- Syscall.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.01 - Primera parte.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.02 - segunda parte.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.03 -Monitoreando System Calls.md`

---

## 3. Virtualización

### Definición
Abstracción del hardware físico de la computadora.

### 5 niveles de virtualización
1. **Process Level** — portabilidad. Ej: JVM (Java Virtual Machine)
2. **Storage Level** — vista lógica del almacenamiento. Ej: RAID, LVM
3. **Network Level** — integración de recursos de red con software
4. **Operating System Level** — instancias aisladas de espacio de usuario. Ej: **contenedores**
5. **System Level** — máquinas virtuales completas

### Propiedades (3 condiciones)
1. **Equivalencia / Fidelidad**: se ejecuta como si no estuviera virtualizado
2. **Control de recursos / Seguridad**: instrucciones privilegiadas pasan por el VMM
3. **Eficiencia / Performance**: lo más posible en modo usuario sin intervención del VMM

### Trap & Emulate
- El kernel del guest corre en **modo usuario**
- Si ejecuta instrucción privilegiada, genera **trap** → el hipervisor la captura y la ejecuta simulada

### Problema x86
- Existen instrucciones **sensibles pero no privilegiadas** (ej: `popf`)
- No generan trap en modo usuario, pero su comportamiento cambia → rompe virtualización
- Solución de **1ra generación**: **Traducción binaria** (reemplaza instrucciones problemáticas sobre la marcha)

### Paravirtualización
- Se **modifica el SO invitado** para mejorar performance
- Se comunica directamente con el VMM mediante **hypercalls**
- Ejemplos: Xen, KVM con VirtIO

### Virtualización asistida por hardware
- No se modifica el guest
- El procesador agrega un nuevo anillo de privilegios ("Ring -1")
- Se cambia una característica de la BIOS/CPU

**Archivos:**
- `teoria/03 - Virtualizacion.md`
- `practica/Practica 4/Practica 4A.md`

---

## 4. Control Groups (cgroups) y Namespaces

### cgroups
- Por defecto el SO **no prioriza** procesos: CPU, RAM, I/O bandwidth se reparten sin preferencia
- cgroups permite control **"fine-grained"**: alocación, priorización, denegación y monitoreo
- **Los procesos no saben que están controlados**; ven recursos acotados
- Se manejan mediante un **pseudo-filesystem** llamado `cgroups`
- Un proceso creado con `fork()` pertenece al **mismo cgroup que el padre**
- **Importante**: un proceso puede estar en **un solo cgroup en toda la jerarquía**

#### cgroups v1
- Jerarquías **separadas** por recurso (cpu, memory, io cada una con su propia jerarquía)
- Archivos como `cpu.shares`, `tasks`
- Se empezó a implementar **antes de diseñarse**

#### cgroups v2
- **Jerarquía unificada** (un solo árbol para todos los controladores)
- `cgroup.controllers` — solo lectura, indica controladores disponibles
- `cgroup.subtree_control` — lectura/escritura, controladores habilitados para hijos
- `cgroup.events`: `Populated` (1 si tiene miembros), `Frozen` (1 si está congelado)
- **Solo pueden haber procesos en las hojas** (nodos sin hijos)
- Un nivel **no puede habilitarle a su hijo algo que a él no le habilitaron**

### Controladores comunes
`cpu`, `memory`, `io`, `pids`, `cpuset`, `hugetlb`

### Namespaces
Determinan **qué puede ver** un proceso (aislamiento visual).

| Namespace | Aísla |
|-----------|-------|
| Mount (mnt) | Puntos de montaje del filesystem |
| Process ID (pid) | IDs de proceso |
| Network (net) | Interfaces de red |
| IPC | Colas de mensajes IPC |
| UTS | Hostname |
| User | UID/GID |
| Cgroup | Visibilidad de cgroups |
| Time | Tiempo del sistema |

- El proceso dentro del namespace **cree que él solo posee el recurso**
- Las modificaciones **no se ven fuera** de ese namespace
- Un proceso solo puede estar en **un namespace de un tipo a la vez**
- **Level 0 (host)** ve todos los namespaces
- **User namespace**: cualquier usuario adentro puede crear nuevos procesos si los recursos lo permiten

### chroot
- Cambia el directorio raíz para un proceso ("chroot jail")
- Originalmente para seguridad, pero se le encontraron **fallas**
- Se puede escapar montando el disco original o viendo procesos
- **`linux-vdso.so.1`**: Virtual Dynamic Shared Object — no existe como archivo en disco, lo inyecta el kernel para syscalls rápidas sin cambio a modo kernel

**Archivos:**
- `teoria/05 - cont virt.md`
- `practica/Practica 4/Practica 4A.md`

---

## 5. Docker

### Docker Engine (3 componentes)
1. **Docker Daemon (dockerd)** — servidor. Crea, ejecuta y monitorea contenedores
2. **API** — interfaz para interactuar con el servidor
3. **CLI** — cliente de línea de comandos

- Cliente y servidor ejecutan en **espacio de usuario**
- **Docker no captura las syscall**
- Los procesos en contenedores funcionan igual que los que no lo están
- **No crea un nuevo nivel de privilegio**: ejecuta en **Ring 3**
- Un contenedor tiene sus propios filesystems, librerías, nombre, interfaz de red... **excepto su propio kernel** (comparte el del host)

### Imagen vs Contenedor
- **Imagen**: template de **solo lectura** con instrucciones para construir un contenedor. **Inmutable**
- **Contenedor**: instancia de una imagen **en ejecución**. Tiene una **capa RW** (escritura) sobre la imagen
- **`scratch`**: nombre especial que significa "desde cero" (sin imagen base)

### Union Mount FS (OverlayFS)
- Merge de filesystems por capas
- Las capas inferiores son **inmutables** (solo lectura)
- La capa **overlay** (del contenedor) es **RW** (lectura/escritura)
- Si dos archivos con el mismo nombre en distintas capas → se muestra el del **upper**
- **Eliminar archivos**: se **enmascaran** (siguen en las capas inferiores, no se borran realmente)
- Los cambios en el contenedor se **pierden al eliminarlo**
- **Copy-on-Write (CoW)**: al modificar un archivo de una capa inferior, se copia a la capa superior antes de escribir
- Si dos imágenes comparten capas base (mismo `FROM`), se descargan **una sola vez**

### Red
- Rango por defecto: `172.17.0.0/16` vía bridge `docker0`

### Persistencia de datos
| Tipo | Gestionado por | Almacenamiento | Recomendado |
|------|---------------|----------------|-------------|
| **Volumes** | Docker | `/var/lib/docker/volumes/` | Sí — portabilidad |
| **Bind Mounts** | Usuario | Cualquier ruta del host | Desarrollo |

### Dockerfile (instrucciones clave)
- `FROM` — imagen base
- `RUN` — ejecuta comandos durante el build
- `COPY` — copia archivos (foto estática del momento del build)
- `EXPOSE` — puerto expuesto
- `CMD` — comando por defecto (forma exec: `["nginx", "-g", "daemon off;"]`)
- `WORKDIR` — directorio de trabajo

**Nginx en Docker**: debe ejecutarse en **primer plano** (`daemon off;`). Docker monitorea el PID 1, si termina → contenedor se detiene.

### Docker Compose (bloques clave)
- `services` — define contenedores
- `image` / `build` — imagen a usar o Dockerfile a construir
- `volumes` / `ports` / `environment` / `networks`
- `restart` — política de reinicio
- `depends_on` — orden de inicio
- `healthcheck` + `condition: service_healthy` — orden condicional

### Comandos
- `docker compose create` — construye y deja apagado
- `docker compose up` — crea y enciende
- `docker compose stop` — detiene pero conserva contenedores
- `docker compose down` — detiene y elimina contenedores y redes
- `docker compose down -v` — también elimina **volúmenes con nombre**
- `docker compose run` — crea contenedor, ejecuta comando y finaliza
- `docker compose exec` — entra en contenedor **ya corriendo**

**DNS interno**: los servicios se comunican por **nombre de servicio** dentro de la misma red.

### Docker Secrets
- Ventajas sobre variables de entorno: no se filtran en logs/proc, no se heredan
- Montados en `tmpfs` (memoria RAM)
- En Docker Compose (local): archivo de texto plano en `/run/secrets/` — **no hay cifrado real**
- Permisos configurables con `uid`, `gid`, `mode`

**Archivos:**
- `teoria/06 - Docker.md`
- `practica/practica 4-b/Practica 4-b.md`

---

## 6. Seguridad y Protección

### Protección vs Seguridad
- **Protección**: mecanismos específicos del SO para resguardar información y controlar acceso a recursos
- **Seguridad**: medida de la confianza en preservar la integridad del sistema. **No es algo que se aplica, es algo que se logra**. Se aplica en **capas**

### Modelo de protección
- **Objeto (Object)**: entidad de HW (CPU, memoria) o SW (archivos, semáforos) protegida
- **Derecho (Right)**: permiso para ejecutar una operación sobre un objeto
- **Dominio de protección (Domain)**: conjunto de pares `(objeto, derechos)`
- **Matriz de acceso**: se almacena solo lo ocupado:
  - Por **filas**: Lista de Capacidades por Dominio
  - Por **columnas**: ACL (Access Control List) por objetos

### POLA (Principle of Least Authority / Mínimo Privilegio)
- Un proceso debe tener **solo** el conjunto mínimo de privilegios necesarios
- **Si no está declarado como permitido, está denegado**

### setuid / setgid
- El proceso se ejecuta con los privilegios del **dueño del archivo** (típicamente root), no del usuario que lo ejecuta
- La presencia de **switch** indica si la vinculación es dinámica o estática

### ASLR (Address Space Layout Randomization)
- Aleatoriza posiciones de áreas de datos clave (ejecutable, stack, heap, bibliotecas)
- Previene ataques de reutilización de código
- Control: `/proc/sys/kernel/randomize_va_space`
  - **0**: Desactivado
  - **1**: Activado parcial (bibliotecas, stack, vDSO — **no incluye heap**)
  - **2**: Activado total (agrega heap vía `brk`) → **valor por defecto**

### Buffer Overflow
- `gets()` es peligroso (no verifica límites) → usar `fgets()` con tamaño máximo
- Medidas del compilador:
  1. **Stack Canaries** (`-fstack-protector`): valor secreto antes de dirección de retorno. Si se modifica → aborta
  2. **Fortificación** (`-D_FORTIFY_SOURCE=2`): intercepta funciones inseguras
  3. **PIE + ASLR** (`-fPIE -pie`): código independiente de posición

### KASLR, SMEP, SMAP
- **KASLR**: aleatorización de direcciones del kernel en RAM (opción de boot, separada de ASLR de usuario)
- **SMEP (Supervisor Mode Execution Prevention)**: prohíbe al kernel ejecutar código en espacio de usuario
- **SMAP (Supervisor Mode Access Prevention)**: prohíbe al kernel leer/escribir en espacio de usuario sin funciones explícitas (`copy_from_user`, `copy_to_user`)

### SystemD
- `systemctl enable/disable` — inicio automático al boot
- `systemctl start/stop` — inicio/detención inmediata
- `systemctl daemon-reload` — recarga configuraciones
- `systemctl status` — estado detallado
- `systemd-cgls` — árbol de cgroups
- `journalctl -u [unit]` — logs de la unidad

**Opciones de seguridad en units:**
- `User/Group` — usuario/grupo de ejecución
- `ProtectHome` — oculta /home, /root
- `PrivateTmp` — /tmp aislado
- `ProtectProc` — controla acceso a /proc
- `MemoryAccounting`, `MemoryHigh` (límite suave), `MemoryMax` (límite estricto → OOM Killer)

### AppArmor (MAC - Mandatory Access Control)
- `aa-enabled` — verifica si está habilitado
- `aa-status` — perfiles cargados
- `aa-genprof` — genera perfil interactivo
- `aa-enforce` — modo enforcing
- `aa-complain` — modo complain (solo registra, no bloquea)

### CopyFail
- Vulnerabilidad del **kernel** en subsistema de memoria virtual y pipes
- El kernel falla al gestionar Copy-on-Write (CoW) al transferir páginas con banderas específicas
- Ocurre en el **Page Cache** (no en disco)
- Permite modificar binarios SUID en **memoria** (ej: `/bin/su`)
- **No persiste al reiniciar** (se recupera de disco)
- Puede saltar aislamiento de **contenedores** (comparten el mismo kernel)

### memfd_secret()
- Syscall que crea área de memoria aislada de las tablas de páginas del kernel
- **El kernel NO puede acceder** (ni root)
- Requiere `secretmem.enable=1` al arrancar
- Uso: guardar claves criptográficas

**Archivos:**
- `teoria/07 - proteccion y seguridad.md`
- `practica/Practica 5/Practica 5.md`

---

## 7. Deadlocks (Interbloqueos)

### Definición
Un conjunto de procesos está en deadlock cuando **cada uno espera por un recurso usado por otro proceso del mismo conjunto**. Puede involucrar recursos de diferentes tipos.

### Recursos
- **Apropiativos**: se pueden quitar sin daño (CPU, memoria)
- **No apropiativos**: quitarlos causa fallo (impresora, escritura a CD)
- **Clase de recurso**: conjunto de instancias idénticas de un recurso
- **Secuencia normal**: Solicitud → Uso → Liberación

### Grafo de asignación de recursos
- Nodos: procesos (cuadrados) y recursos (círculos con puntos por instancia)
- Arista `P → R`: proceso solicita recurso
- Arista `R → P`: recurso asignado al proceso

### 4 condiciones de Coffman (1971)
1. **Exclusión mutua**: solo un proceso puede usar una instancia a la vez
2. **Retención y espera**: los procesos retienen recursos asignados mientras esperan nuevos
3. **No apropiación**: los recursos asignados no pueden quitarse
4. **Espera circular**: cada proceso espera un recurso asignado a otro, formando un ciclo

**Para deadlock se deben cumplir las 4 simultáneamente.**

### Tratamiento: 3 enfoques

#### 1. Prevención (Prevention)
- Atacar que **no se cumpla al menos una** de las condiciones
- **Exclusión mutua**: usar spooler (demonio que encola solicitudes, el proceso no bloquea el recurso directamente)
- **Retención y espera**: el proceso debe solicitar **todos** los recursos antes de empezar. Desventajas: baja utilización, posible inanición
- **No apropiación**: virtualizar recursos (spooler). El proceso accede a un demonio, no al recurso físico directo
- **Espera circular**: definir `F: Recurso → N` (asigna número único a cada recurso). Un proceso con `Ri` puede solicitar `Rj` **solo si `F(Ri) < F(Rj)`** (orden ascendente). Así se evita el ciclo.

#### 2. Evitación (Avoidance)
- El SO tiene información **previa** sobre uso de recursos
- Toma decisiones dinámicas de asignación
- Si una asignación llevaría a estado inseguro → se deniega
- **Estado seguro**: existe una **cadena segura** `<P1, P2, ..., Pn>` para **todos** los procesos. Se pueden satisfacer requerimientos secuencialmente.
- **Estado inseguro**: no existe cadena segura. **No implica deadlock necesariamente**, pero hay riesgo.
- **Si hay deadlock → estado inseguro, pero no todo estado inseguro es deadlock**

#### 3. Detección y Recuperación
- **Instancia única**: análisis del **grafo wait-for** (nodos = procesos, arista `Pi → Pj` si Pi espera que Pj libere un recurso). Si hay ciclo → deadlock.
- **Múltiples instancias**: **Algoritmo del Banquero** (matrices `disponible`, `max`, `asignación`, `need`)

### Algoritmo del Banquero (Banker's Algorithm)
- Estructuras:
  - `disponible[m]`: recursos disponibles por tipo
  - `max[n][m]`: máximo que necesita cada proceso
  - `asignación[n][m]`: lo asignado a cada proceso
  - `need[n][m]`: lo que falta = `max - asignación`
- Busca una secuencia segura de procesos
- Es un algoritmo **teórico** — "re contra improductivo"
- No se ejecuta constantemente; se usa como referencia
- Los SO delegan en software de usuario ("el kernel confía en diosito")

### Recuperación
1. **Abortar procesos**:
   - Matar **todos** los procesos en deadlock (simple, caro)
   - Matar **de a uno** hasta romper el ciclo (requiere re-ejecutar detección cada vez)
2. **Expropiar recursos**
3. **Criterios para elegir víctima**: menor prioridad, menor tiempo de CPU, mayor tiempo restante, menor cantidad de recursos asignados, interactivo vs batch, posibilidad de rollback

### Estrategia combinada
Dividir recursos en **clases** y aplicar el método más adecuado a cada una (prevención, evitación o detección según el tipo de recurso).

**Archivos:**
- `teoria/08 - interbloqueos.md`
- `teoria/diapos/Tema 11 - Deadlocks-1.pdf`

---

## 8. Multiprocesadores

### Origen
Límites físicos: velocidad de la luz (20 cm/nseg), disipación de calor, consumo eléctrico → solución: **cómputo paralelo y/o distribuido**

### 3 esquemas de organización

| Esquema | Comunicación | Latencia | Memoria |
|---------|-------------|----------|---------|
| Multiprocesadores con Memoria Compartida | BUS compartido | 2-10 ns | Compartida (único espacio lógico) |
| Multicomputadoras (clusters) | Pasaje de mensajes | 10-50 μs | Cada CPU tiene su memoria local |
| Sistemas Distribuidos | Red (pasaje de mensajes) | 10-100 ms | Cada nodo es una PC completa |

### UMA (Uniform Memory Access)
- Todas las CPUs acceden a la memoria con el **mismo tiempo de acceso**
- Arquitecturas: basada en bus, con caché, con barras cruzadas (n conexiones simultáneas), redes de conmutación multietapa
- La **caché** reduce el uso del bus compartido
- **Protocolo de coherencia de caché**: cuando una CPU escribe, las demás descartan copias "limpias" o escriban a memoria si tienen copia "sucia"
- Limitación: poco escalable y costoso

### NUMA (Non-Uniform Memory Access)
- Único espacio de direcciones visible por todas las CPUs
- El acceso a memoria **remota es más lento** que a memoria local
- Escalable a mayor número de CPUs
- **NC-NUMA**: sin caché
- **CC-NUMA (Cache Coherent NUMA)**: con caché. Usa **multiprocesador basado en directorios** — BD en hardware que indica dónde está cada línea y su estado (limpia/sucia)
- Dirección traducida como: `nodo + línea + desplazamiento`

### Chips multinúcleo
- Más transistores → más núcleos (no más clock ni más caché). Se logra paralelismo real.
- El software debe diseñarse teniendo en cuenta el hardware para aprovecharlo al máximo

### Tipos de SO Multiprocesador

#### 1. Cada CPU con su SO (poco usado)
- Memoria dividida estáticamente, cada CPU tiene su copia privada del SO
- Desbalance de carga, memoria desperdiciada, inconsistencia de caché de disco

#### 2. Maestro-Esclavo
- Única copia del SO. **Todas las syscalls se redirigen a una CPU (el maestro)**
- El maestro ejecuta procesos si "le sobra tiempo"
- Ventaja: única cola de planificación, páginas asignables dinámicamente
- Desventaja: **cuello de botella** en el maestro (ej: 10% de tiempo en syscalls → con 10 CPUs se satura)

#### 3. SMP (Multiprocesadores Simétricos)
- Única copia del SO en memoria, **cualquier CPU puede ejecutarlo**
- La syscall la ejecuta la **CPU que la invocó**
- Problemas de concurrencia: dos CPUs ejecutando código del kernel a la vez
- Soluciones:
  1. **Lock global** (gran sección crítica) — poco usado, mala performance
  2. **Lock por estructura** — varias secciones críticas con su propio mutex. **Es el más usado.** Riesgo de deadlock.

### Sincronización en multiprocesadores
- Deshabilitar interrupciones de una CPU **no alcanza** (otra CPU puede ejecutar)
- Se necesita protocolo de **exclusión mutua** (mutex) apropiado

### Planificación en Multiprocesadores

#### Hilos independientes (time-sharing)
- **Una única cola de listos**: simple, eficiente, balanceo de carga. Desventaja: cuello de botella
- **Problema de espera activa**: si un hilo termina su quantum sin liberar un spinlock, las otras CPUs esperan ociosas.
  - **Solución (Zahorjan, 1991)**: flag en el proceso que indica espera activa. No expulsar procesos con flag activado.
- **Planificación por afinidad (affinity scheduling)**: un hilo se ejecuta en la misma CPU donde ya se ejecutó, maximizando la probabilidad de que sus datos estén en caché.
  - **Algoritmo de 2 niveles**: al crear un hilo se asigna a una CPU; cada CPU tiene su propia colección y planifica por separado. Si una CPU queda ociosa, se reparten hilos.

#### Hilos que trabajan en conjunto (gang scheduling)
- **Planificación por pandillas**: todos los hilos relacionados se ejecutan **simultáneamente** en distintas CPUs. Inician y terminan sus intervalos en conjunto.
- Ventaja: pasaje de mensajes más rápido y eficiente
- Permite que un grupo de hilos se ejecute sincrónicamente

### Multicomputadoras (Clusters)
- CPUs con acoplamiento fuerte, memoria independiente
- Generalmente sin placa de video, sonido, a veces sin disco
- **Topologías**: estrella, anillo, malla, doble malla, hipercubo
- **Transmisión**:
  - **Almacenamiento y retransmisión** (store-and-forward): se almacena hasta formar paquete, luego se transmite. Inyecta latencia.
  - **Conmutación de circuitos**: se establece ruta virtual. Más rápida, sin control de flujo.
- **Problema de copiado de paquetes en exceso**: datos se copian RAM → placa de red → RAM. Si la placa está en espacio de kernel, cada copia requiere syscall.
  - Solución: asignar interfaz de red al **espacio de usuario** (más performante, pero problemas si múltiples procesos compiten)
  - Solución real: **canales DMA** (procesadores de red)
- **Software de comunicación**:
  - `send(dest, &mptr)` / `receive(direc, &mptr)` — con/sin bloqueo
  - **RPC (Remote Procedure Call)**: invocar procedimientos remotos de forma transparente
- **Planificación**: cada nodo tiene su propio conjunto de procesos. El balanceo de carga se modela como un **grafo**: cada nodo es un proceso, las aristas tienen costo de comunicación. Se debe particionar en subgrafos minimizando tráfico de red.

### Sistemas Distribuidos
- Tanembaum: "A distributed system is a collection of independent computers that appears to its users as a single coherent system"
- Menor acoplamiento que multicomputadoras (esparcidos geográficamente)
- **Middlewares**: capa de software sobre el SO que provee uniformidad entre distintos SOs. Soluciona heterogeneidad, provee servicios comunes (estructuras de datos, operaciones, interoperabilidad)

**Archivos:**
- `teoria/diapos/Tema 10 - Multiprocesadores 1-2.pdf`
- `teoria/diapos/Tema 10 - Multiprocesadores 2-1.pdf`

---

## 9. Kernel Linux, Módulos y Drivers

### Kernel Linux
- **Arquitectura**: monolítico pero con carga dinámica de módulos → **"mono híbrido"**
- Escrito en **C** con algo de **Assembly**
- **Versionado**: `A.B.C` donde A = Major (cambios estructurales), B = Minor (funcionalidades), C = Revision/Patch (correcciones). `rcX` = versión de prueba.
- **Funciones**: gestión de procesos, memoria (RAM), controladores de dispositivos, system calls
- **Licencia**: GPLv2 (copyleft). Las primeras versiones estaban bajo copyright.

### Compilación del kernel
```bash
make menuconfig       # genera .config
make -j<N>            # compilación paralela
make modules_install  # copia módulos a /lib/modules/[version]/
make install          # copia imagen y System.map a /boot
make clean            # limpia objetos, respeta .config
```
- `make oldconfig`: actualizar versión manteniendo configuración anterior
- Interfaces de configuración: `config` (CLI), `menuconfig` (ncurses, estándar), `xconfig` (Qt), `gconfig` (GTK), `oldconfig`

### initramfs
- Initial RAM File System — sistema de archivos temporal cargado en RAM al arranque
- Contiene drivers necesarios para leer el disco duro real
- No es necesario si el kernel es "monolítico puro" (drivers incluidos estáticamente)
- Se genera al instalar nuevo kernel, actualizar drivers críticos o cambiar montaje del disco raíz

### Módulos del kernel (.ko)
- Porción de código que se agrega al kernel en **tiempo de ejecución** sin reiniciar
- `insmod <ruta>.ko` — carga de bajo nivel (no maneja dependencias)
- `modprobe <nombre>` — carga de alto nivel (usa `modules.dep` para dependencias)
- `lsmod` — lista módulos cargados (lee de `/proc/modules`)
- `rmmod` — descarga módulo
- `modinfo` — información del módulo
- **Bug en un módulo**: puede causar kernel panic, brecha de seguridad o corrupción de datos (corre en modo kernel)

### Estructura básica de un módulo
```c
module_init(funcion_init);
module_exit(funcion_exit);
MODULE_LICENSE("Dual BSD/GPL");
```
- Sin `MODULE_LICENSE` → kernel se marca como **Tainted**, funciones `EXPORT_SYMBOL_GPL` no disponibles

### Makefile para módulos
```makefile
obj-m := modulo.o
```
```bash
make -C <KERNEL_CODE> M=$(pwd) modules
```

### Drivers
- **Driver**: componente que se comunica con un dispositivo hardware. Actúa como traductor entre el kernel y el hardware
- Un **driver** es un concepto funcional; un **módulo** es el formato de entrega (`.ko`). La mayoría de los drivers se implementan como módulos.

#### Tipos de drivers en Linux
| Tipo | Acceso | Ejemplos | ¿Aparece en /dev? |
|------|--------|----------|-------------------|
| **Caracter (Char)** | Flujo de bytes secuencial | Teclados, puertos serie, mouse | Sí (`c`) |
| **Bloque (Block)** | Bloques de tamaño fijo, acceso aleatorio | Discos, SSDs | Sí (`b`) |
| **Red (Network)** | Paquetes de datos | Ethernet, Wi-Fi | **No** |

### Comunicación con el driver (dispositivos caracter)
- `struct file_operations` — "tabla de saltos" con punteros a funciones (`open`, `release`, `read`, `write`)
- `register_chrdev(major, name, &fops)` — registra driver de caracter. Vincula **Major Number** con nombre y operaciones
- `unregister_chrdev(major, name)` — libera el Major Number

### Asociación módulo-dispositivo (Major/Minor numbers)
1. El módulo se registra con un número (ej: 60)
2. Se crea el nodo: `mknod /dev/memory c 60 0`
   - `c` = caracter, `b` = bloque
   - **Major** (60) = identifica al driver
   - **Minor** (0) = identifica una instancia/sub-dispositivo dentro del driver
3. Cuando un proceso abre `/dev/memory`, el kernel usa el major number para encontrar el driver

### Variables del kernel vs usuario
- `copy_to_user(dest, src, n)` — kernel → usuario
- `copy_from_user(dest, src, n)` — usuario → kernel
- Ambas verifican que las direcciones de destino/origen sean válidas antes de copiar

### Consideraciones para drivers reales (omitidas en ejercicios simples)
- Concurrencia: semáforos/spinlocks para evitar race conditions
- Control de hardware: `inb`/`outb` para puertos de I/O
- Gestión de interrupciones (IRQ handlers)
- `ioctl` para operaciones de configuración
- Espera no bloqueante (Wait Queues)

**Archivos:**
- `teoria/01- Intro.md`
- `teoria/02 - clase 2.md`
- `practica/practica 1/Practica 1 - A.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.04 - Modulos y drivers.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.05 - Guiada Modulos.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.06 - Drivers.md`

---

## 10. Intro / Conceptos Generales

### Sistema Operativo
- Software intermediario entre el usuario y la computadora
- Necesita **procesador y memoria** para funcionar

### Kernel
- Programa que **ejecuta programas** y **gestiona dispositivos de hardware**
- Primer programa cargado tras el gestor de arranque

### GCC
- GNU Compiler Collection — soporta C, C++, Fortran, Ada, Go
- No es exclusivo de C

### make
- Automatización de compilación basada en `Makefile`
- **Compilación incremental**: solo recompila archivos modificados

### Parches
- Archivo con diferencias (`diff`) entre versiones de código fuente
- Se aplica con: `patch -p1 < archivo.patch`

### Energy-Aware Scheduling (EAS)
- El scheduler calcula costo energético usando PELT (Per-Entity Load Tracking)
- Asigna tareas pequeñas a núcleos LITTLE, pesadas a núcleos big (ARM big.LITTLE)

**Archivos:**
- `teoria/01- Intro.md`
- `teoria/02 - clase 2.md`
- `practica/practica 1/Practica 1 - A.md`

---

## Índice de archivos fuente

### Teoría (apuntes de clase)
- `teoria/01- Intro.md`
- `teoria/02 - clase 2.md`
- `teoria/03 - Threads.md`
- `teoria/03 - Virtualizacion.md`
- `teoria/03- Syscall.md`
- `teoria/05 - cont virt.md`
- `teoria/06 - Docker.md`
- `teoria/07 - proteccion y seguridad.md`
- `teoria/08 - interbloqueos.md`

### Teoría (diapositivas PDF originales)
- `teoria/diapos/Tema 1 - Kernel - Parte 1-2.pdf`
- `teoria/diapos/Tema 1 - Kernel - Parte 2-1.pdf`
- `teoria/diapos/Tema 2 - Llamadas al Sistema-1.pdf`
- `teoria/diapos/Tema 3 - Threads - Apunte Threads.pdf`
- `teoria/diapos/Tema 3 - Threads - Transparencia 1.pdf`
- `teoria/diapos/Tema 4 - Virtualización-3.pdf`
- `teoria/diapos/Tema 8 - Seguridad - Transparencia 1-1.pdf`
- `teoria/diapos/Tema 10 - Multiprocesadores 1-2.pdf`
- `teoria/diapos/Tema 10 - Multiprocesadores 2-1.pdf`
- `teoria/diapos/Tema 11 - Deadlocks-1.pdf`
- `teoria/diapos/cgroups_namespaces_containers-1.pdf`
- `teoria/diapos/contnedores_docker-2.pdf`

### Práctica (respuestas del alumno)
- `practica/practica 1/Practica 1 - A.md`
- `practica/practica 1/Practica 1 - B.md`
- `practica/Practica 3 real/01- Parte teorica.md`
- `practica/Practica 3 real/02 - Parte practica.md`
- `practica/Practica 4/Practica 4A.md`
- `practica/practica 4-b/Practica 4-b.md`
- `practica/Practica 5/Practica 5.md`
- `practica/explicacion 3 - Hilos.md`

### Práctica (Panchito Cabra — guías)
- `practica/Practica 3 (panchito cabra)/Practica 2.01 - Primera parte.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.02 - segunda parte.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.03 -Monitoreando System Calls.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.04 - Modulos y drivers.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.05 - Guiada Modulos.md`
- `practica/Practica 3 (panchito cabra)/Practica 2.06 - Drivers.md`

### Otros
- `otro p3.md` — vacío
