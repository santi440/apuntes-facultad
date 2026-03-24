### A - Introducción
1. ¿Qué es GCC?
La GNU Compiler Collection (GCC) ==es un sistema de compiladores versátil y de código abierto desarrollado por el proyecto GNU==, esencial en sistemas Unix/Linux. Soporta lenguajes como C, C++, Fortran, Ada y Go, traduciendo código fuente a binarios ejecutables. Es fundamental para compilar el núcleo Linux y gran variedad de software

2. ¿Qué es make y para que se usa?
`make` es una ==herramienta de automatización, comúnmente usada en sistemas Unix/Linux, que compila código fuente y gestiona la creación de ejecutables basándose en un archivo== `makefile`. Lee dependencias para decidir qué archivos actualizar, automatizando la compilación de bibliotecas, la ejecución de tests y la limpieza de archivos temporales.

- **Makefile:** Define "objetivos" (acciones), dependencias y los comandos necesarios, usualmente escritos en C/C++.
- **Compilación Incremental:** `make` solo compila los archivos modificados, ahorrando tiempo.
- **Uso:** Se invoca simplemente ejecutando `make` en la terminal, lo que facilita el desarrollo.
- **Compatibilidad:** Nativo en Linux/macOS; en Windows se usa mediante herramientas como WSL, Cygwin o MSYS2

3. La carpeta /home/so/practica1/ejemplos/01-make de la VM contiene ejemplos de
uso de make. Analice los ejemplos, en cada caso ejecute `make` y luego `make
run` (es opcional ejecutar el ejemplo 4, el mismo requiere otras dependencias que no vienen preinstaladas en la VM):
a. Vuelva a ejecutar el comando `make`. ¿Se volvieron a compilar los programas?
¿Por qué?

No, make identifica que hay un archivo compilado y no ejecuta de nuevo el compilador, a menos que se haga un `make clean && make`

b. Cambie la fecha de modificación de un archivo con el comando `touch` o
editando el archivo y ejecute `make`. ¿Se volvieron a compilar los programas?
¿Por qué?

es lo contrario que lo de arriba(ejecute touch sobre el .c), make identifica que cambio el código del archivo  y es necesario recompilar

c. ¿Por qué “run” es un target “phony”?




d. En el ejemplo 2 la regla para el target `dlinkedlist.o` no define cómo generar el
target, sin embargo el programa se compila correctamente. ¿Por qué es esto?
Nota, si no usás la VM podés descargar los ejemplos desde:
https://gitlab.com/unlp-so/codigo-para-practicas
4. ¿Qué es el kernel de GNU/Linux? ¿Cuáles son sus funciones principales dentro del Sistema Operativo?
El kernel es el software de más bajo nivel que se ejecuta en una computadora. Es el primer programa que se carga después del gestor de arranque y se mantiene en memoria hasta que el sistema se apaga.

Funciones principales:
- **Gestión de Procesos:** Decide qué programa usa el procesador (CPU), por cuánto tiempo y cuándo debe interrumpirse para dar paso a otro.
- **Gestión de Memoria (RAM):** Controla qué partes de la memoria están siendo utilizadas, por quién y asegura que los procesos no interfieran entre sí.
- **Controladores de Dispositivos:** Actúa como intermediario para que el sistema reconozca y use el teclado, la tarjeta de video, el Wi-Fi, etc.
- **Llamadas al Sistema (System Calls):** Provee una interfaz para que las aplicaciones soliciten servicios al hardware de forma segura.

5. Explique brevemente la arquitectura del kernel Linux teniendo en cuenta: tipo de kernel, módulos, portabilidad, etc.
- **Tipo de Kernel (Monolítico):** A diferencia de los microkernels, Linux es **monolítico**. Esto significa que casi todos los servicios (gestión de memoria, sistemas de archivos, drivers) se ejecutan en el "espacio del kernel" para maximizar el rendimiento.
- **Módulos (LKM - Loadable Kernel Modules):** Aunque es monolítico, es **modular**. Esto permite cargar y descargar controladores o funciones sobre la marcha sin necesidad de reiniciar la computadora. Si conectas un USB, el kernel carga el módulo necesario dinámicamente.
- **Portabilidad:** Está escrito principalmente en **C** (con algo de Assembly). Esto facilita que el código sea compilado para ejecutarse en casi cualquier arquitectura de procesador

6. ¿Cómo se define el versionado de los kernels Linux en la actualidad?
A.B.C
- **A (Versión Principal / Major):** Cambia cuando hay modificaciones estructurales significativas o simplemente cuando el segundo número crece demasiado (por decisión de Linus Torvalds).
- **B (Versión Menor / Minor):** Indica nuevas funcionalidades importantes o cambios grandes en la arquitectura.
- **C (Revisión / Patch):** Se lanza para corregir errores críticos o parches de seguridad
- **rcX**: versión de prueba.

7.  ¿Cuáles son los motivos por los que un usuario/a GNU/Linux puede querer re-compilar el kernel?
- **Optimización de Rendimiento:** Puedes compilar el kernel específicamente para tu modelo de procesador exacto, eliminando soporte para hardware que no tienes, lo que lo hace más ligero y rápido.
- **Seguridad:** Deshabilitar funciones o módulos innecesarios reduce la "superficie de ataque" del sistema.
- **Soporte de Hardware Específico:** Para habilitar un driver muy nuevo o experimental que aún no viene en el kernel estándar de la distribución.
- **Depuración (Debugging):** Los desarrolladores lo hacen para probar cambios en el código fuente o analizar fallos internos.
- **Aprendizaje:** No hay mejor manera de entender cómo funciona Linux que configurándolo desde sus cimientos.
8. ¿Cuáles son las distintas opciones y menús para realizar la configuración de opciones de compilación de un kernel? Cite diferencias, necesidades (paquetes adicionales de software que se pueden requerir), pro y contras de cada una de ellas.


| **Interfaz**          | **Tipo**               | **Requisitos**              | **Pros**                                                                   | **Contras**                                                                                   |
| --------------------- | ---------------------- | --------------------------- | -------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------- |
| **`make config`**     | Texto (CLI)            | Ninguno especial            | Funciona en cualquier terminal.                                            | Muy tedioso; hace preguntas una por una sin vuelta atrás. y si haces Ctrl + C volve a empezar |
| **`make menuconfig`** | Ncurses (Semi-gráfica) | `libncurses-dev`            | El estándar. Rápido, permite buscar opciones y es intuitivo.               | Requiere terminal con soporte de color/gráficos básicos.                                      |
| **`make xconfig`**    | Gráfica (Qt)           | `qtbase5-dev`               | Muy visual, permite usar el mouse y ver descripciones al lado.             | Requiere entorno gráfico (X11/Wayland) y muchas dependencias.                                 |
| **`make gconfig`**    | Gráfica (GTK)          | `libgtk2.0-dev`             | Similar a xconfig pero para entornos GNOME.                                | Poco utilizado hoy en día; requiere muchas librerías.                                         |
| **`make oldconfig`**  | Texto                  | Un archivo `.config` previo | Ideal para actualizar versiones del kernel manteniendo tu config anterior. | Solo pregunta por las opciones _nuevas_.                                                      |

9. Indique qué tarea realiza cada uno de los siguientes comandos durante la tarea de configuración/compilación del kernel:
a. make menuconfig : Lanza una interfaz gráfica basada en terminal (ncurses) para elegir qué drivers y opciones incluir. Genera el archivo `.config`.
b. make clean : Limpia el árbol de fuentes eliminando archivos objeto (`.o`) y binarios temporales de compilaciones previas, pero **respeta** tu configuración (`.config`).
c. make (investigue la funcionalidad del parámetro -j) : Inicia la compilación principal. El parámetro **`-j`** (jobs) permite la compilación paralela. Si usas `make -j4`, el sistema usará 4 hilos de procesamiento, acelerando drásticamente el tiempo de espera (si cuento con 4 nucleos o la capacidad para procesar 4 hilos).
d. make modules (utilizado en antiguos kernels, actualmente no es necesario): Compila los componentes seleccionados como módulos dinámicos (los marcados con `<M>`). En kernels actuales, `make` a secas ya suele incluirlos.
e. make modules_install: Copia los módulos recién compilados al directorio del sistema `/lib/modules/[version-del-kernel]/`.
f. make install: Es el paso final: copia la imagen del kernel y el archivo `System.map` a `/boot`, y suele disparar la actualización del gestor de arranque.
10. Una vez que el kernel fue compilado, ¿dónde queda ubicada su imagen? ¿dónde debería ser reubicada? ¿Existe algún comando que realice esta copia en forma automática?
Me siento redundante :(
- **Ubicación tras compilar:** Se encuentra dentro de la arquitectura específica, generalmente en `arch/x86/boot/bzImage`.
- **Reubicación:** Debe ser movida al directorio **`/boot`**, que es donde el gestor de arranque busca los kernels para iniciar el sistema.
- **Comando automático:** El comando **`make install`** se encarga de realizar esta copia, renombrar el archivo correctamente y configurar los enlaces simbólicos necesarios.

11. ¿A qué hace referencia el archivo initramfs? ¿Cuál es su funcionalidad? ¿Bajo qué condiciones puede no ser necesario?
El **initramfs** (_Initial RAM File System_) es un archivo comprimido que se carga en la memoria RAM al inicio del arranque.
- **Funcionalidad:** Actúa como un "sistema de archivos temporal" que contiene los controladores necesarios para que el kernel pueda leer el disco duro real. Por ejemplo, si tu disco usa _LVM_ o _RAID_, el kernel necesita cargarlos desde el `initramfs` antes de poder montar la partición raíz (`/`).
- **¿Cuándo no es necesario?** Si compilas un kernel "monolítico puro" donde incluyes el driver del controlador de disco y el sistema de archivos (ej. ext4) de forma estática (marcado con `[*]` en lugar de `<M>`). -> gracias Joaco tomassi :)
12. ¿Cuál es la razón por la que una vez compilado el nuevo kernel, es necesario reconfigurar el gestor de arranque que tengamos instalado?
Aunque copies el kernel a `/boot`, el gestor de arranque (como **GRUB**) no "adivina" que hay un nuevo archivo allí. Es necesario reconfigurarlo (usualmente con `update-grub` o `grub-mkconfig`) para que:
- Detecte la nueva imagen `vmlinuz`.
- Cree una nueva entrada en el menú de inicio.
- Asocie correctamente el `initramfs` correspondiente a esa versión.
13. ¿Qué es un módulo del kernel? ¿Cuáles son los comandos principales para el manejo de módulos del kernel?
Un **módulo** es una extensión del kernel que se puede cargar o descargar bajo demanda sin necesidad de reiniciar el equipo. Es como un "plug-in" para hardware.

**Comandos principales:**
- `lsmod`: Muestra qué módulos están cargados actualmente.
>[!NOTE] ejemplo random
> ![[Pasted image 20260321165212.png]]
- `modprobe`: La forma inteligente de cargar/descargar módulos (maneja dependencias automáticamente).
- `insmod` / `rmmod`: Carga o descarga manual de archivos `.ko` específicos.
- `modinfo`: Muestra detalles (autor, descripción, parámetros) de un módulo.
>[!NOTE] ejemplo
>![[Pasted image 20260321165357.png]]
>
14. ¿Qué es un parche del kernel? ¿Cuáles son las razones principales por las cuáles se deberían aplicar parches en el kernel? ¿A través de qué comando se realiza la aplicación de parches en el kernel?
Un **parche** es un archivo que contiene las diferencias (`diff`) entre dos versiones de código fuente.
- **¿Por qué aplicarlos?** Para corregir vulnerabilidades de seguridad de forma urgente, añadir soporte para hardware nuevo o aplicar mejoras de rendimiento sin tener que descargar los 100MB+ del código fuente completo nuevamente.
- **Comando:** Se utiliza el comando **`patch`**. Por ejemplo: `patch -p1 < parche_actualizacion.patch`.
15. Investigue la característica Energy-aware Scheduling incorporada en el kernel 5.0 y explique brevemente con sus palabras:
a. ¿Qué característica principal tiene un procesador ARM big.LITTLE?
Tenes dos tipos de procesadores combinados : los big que son rapidos pero gastan mucha energia y los little que son más lentos pero consumen menos. La idea es que todos los nucleos tengan acceso a las mismas areas de memoria y se puedan intercambiar entre nucleos grandes y pequeños sobre la marcha de acuerdo a la demanda (según wikidedia solo un lado, grande o pequeño esta encendido y tiene un enorme problema que al ser tan distintos los nucleos en potencia y reloj del Intruction counter) [https://es.wikipedia.org/wiki/ARM_big.LITTLE]
b. En un procesador ARM big.LITTLE y con esta característica habilitada. Cuando
se despierta un proceso ¿a qué procesador lo asigna el scheduler?
Con EAS, el kernel calcula el costo energético. Cuando un proceso "despierta":
- Si es una tarea pequeña (revisar notificaciones, mover el mouse), el scheduler lo asigna a un núcleo **LITTLE**.
- Si la tarea requiere potencia (abrir una app pesada, renderizado), lo asigna a un núcleo **big**.
Mi duda es: la única forma de calcular ese costo seria mantener un historial medible y cuantificable de que tan costosa es la actividad (deberia realizarla previamente) y ese calculo lo hace un nucleo big o uno little

>[!quote] Respuesta gemini
>### 1. ¿Cómo se calcula el costo? (PELT y el Modelo Energético)
> No es que el proceso deba realizarse "completamente" una vez para saber cuánto cuesta, sino que el kernel utiliza un mecanismo llamado **PELT** (_Per-Entity Load Tracking_).
> - **Historial Dinámico:** El kernel mantiene una "ventana" de tiempo (milisegundos). Si un hilo de ejecución utiliza el 100% de la CPU en los últimos 32ms, el scheduler asume que es una tarea pesada y la mueve a un núcleo **big**. Si su actividad cae, la mueve a un **LITTLE**.
> - **El Modelo Energético (EM):** El kernel tiene una tabla (cargada durante el arranque) que le dice: _"Un núcleo LITTLE a 1.2GHz consume X miliwatts, mientras que un núcleo big a la misma frecuencia consume 4X"._
> - **¿Quién hace el cálculo?:** El cálculo lo hace el **scheduler** (que es código del kernel) ejecutándose en el núcleo que esté activo en ese momento (puede ser cualquiera). Es una operación matemática muy ligera, por lo que no "pesa" significativamente en el consumo.
> ### 2. Desventajas de los procesadores big.LITTLE
> Aunque sobre el papel suenan ideales, tienen varios puntos en contra:
> #### A. Latencia de Migración
> Mover un proceso de un núcleo pequeño a uno grande no es instantáneo. Se deben copiar los registros del procesador y, lo más costoso, los datos en las memorias **Caché (L1 y L2)** del núcleo anterior se pierden o deben invalidarse. Si el scheduler se equivoca y mueve el proceso de un lado a otro constantemente (_ping-ponging_), el rendimiento cae en picada.
> #### B. Complejidad del Software (El Kernel)
> El scheduler de un procesador tradicional (donde todos los núcleos son iguales o _Symmetric Multi-Processing_) es "ciego": le da igual dónde poner la tarea. En **big.LITTLE**, el programador del kernel tiene que escribir código mucho más complejo para decidir qué es "importante" y qué es "ahorro", lo que puede generar _bugs_ o comportamientos erráticos.
> #### C. Inconsistencia en el Rendimiento
> A veces, un usuario siente "lags" momentáneos. Esto sucede cuando el sistema intenta ahorrar energía manteniendo una tarea en un núcleo **LITTLE** un poco más de lo debido antes de decidir que "realmente" necesita un núcleo **big**. Esa demora es perceptible en la fluidez de las animaciones.
> #### D. Desperdicio de Silicio
> Tienes núcleos (los **big**) que pasan gran parte del tiempo apagados para ahorrar batería. Estás pagando por un hardware que físicamente ocupa espacio en el chip pero que no se usa el 100% del tiempo, a diferencia de un procesador de escritorio donde todos los núcleos pueden trabajar al máximo en paralelo.

c. ¿A qué tipo de dispositivos opinás que beneficia más esta característica?
Beneficia enormemente a **dispositivos móviles (smartphones y tablets)** y laptops ligeras. En estos equipos, la prioridad es maximizar la duración de la batería sin sacrificar la sensación de fluidez cuando el usuario realmente exige potencia.

16. Investigue la system call memfd_secret() incorporada en el kernel 5.14 y explique brevemente con sus palabras
a. ¿Cuál es su propósito?
El propósito principal de `memfd_secret()` es crear un área de memoria que sea **exclusiva** para el proceso que la solicita. A diferencia de la memoria normal, estas regiones están completamente **aisladas de las tablas de páginas del kernel** (el _direct map_).
b. ¿Para qué puede ser utilizada?
Su uso está orientado casi exclusivamente a aplicaciones de **alta seguridad** y criptografía:

- **Almacenamiento de claves criptográficas:** Guardar llaves privadas o frases semilla (seeds) de carteras digitales para que no puedan ser extraídas de la RAM.
- **Mitigación de ataques de hardware:** Protege contra vulnerabilidades tipo _Speculative Execution_ (como **Spectre** o **Meltdown**). Si el kernel no tiene mapeada esa memoria, un ataque que intente "leer de más" a través de fallos en el procesador no encontrará nada en el mapa directo del sistema.
- **Prevención de volcados de memoria (Core Dumps):** Evita que, ante un error del programa, las claves secretas terminen escritas en un archivo de log en el disco duro.
c. ¿El kernel puede acceder al contenido de regiones de memoria creadas con
esta system call?
**No.** Esta es la característica más radical de esta función.
Cuando se crea una región con `memfd_secret()`, el kernel elimina esas páginas de su propio mapeo directo. Esto significa que:
1. Si el código del kernel intentara leer esa dirección de memoria, se produciría un **fallo de página** (page fault).
2. El contenido **no es visible** para otros procesos, ni para el administrador (_root_), ni para el propio núcleo del sistema operativo mientras realiza sus tareas rutinarias.
> **Dato importante:** Para usar esta función, el sistema debe haber sido arrancado con el parámetro de kernel `secretmem.enable=1`, ya que por seguridad no viene activa por defecto para no fragmentar la memoria innecesariamente.
> Siento que se usa 1 de cada 100.000.000 veces.

