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
6. ¿Cuáles son las distintas opciones y menús para realizar la configuración de opciones de compilación de un kernel? Cite diferencias, necesidades (paquetes adicionales de software que se pueden requerir), pro y contras de cada una de ellas.


| **Interfaz**          | **Tipo**               | **Requisitos**              | **Pros**                                                                   | **Contras**                                                                                   |
| --------------------- | ---------------------- | --------------------------- | -------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------- |
| **`make config`**     | Texto (CLI)            | Ninguno especial            | Funciona en cualquier terminal.                                            | Muy tedioso; hace preguntas una por una sin vuelta atrás. y si haces Ctrl + C volve a empezar |
| **`make menuconfig`** | Ncurses (Semi-gráfica) | `libncurses-dev`            | El estándar. Rápido, permite buscar opciones y es intuitivo.               | Requiere terminal con soporte de color/gráficos básicos.                                      |
| **`make xconfig`**    | Gráfica (Qt)           | `qtbase5-dev`               | Muy visual, permite usar el mouse y ver descripciones al lado.             | Requiere entorno gráfico (X11/Wayland) y muchas dependencias.                                 |
| **`make gconfig`**    | Gráfica (GTK)          | `libgtk2.0-dev`             | Similar a xconfig pero para entornos GNOME.                                | Poco utilizado hoy en día; requiere muchas librerías.                                         |
| **`make oldconfig`**  | Texto                  | Un archivo `.config` previo | Ideal para actualizar versiones del kernel manteniendo tu config anterior. | Solo pregunta por las opciones _nuevas_.                                                      |

7. Indique qué tarea realiza cada uno de los siguientes comandos durante la tarea de configuración/compilación del kernel:
a. make menuconfig
b. make clean
c. make (investigue la funcionalidad del parámetro -j)
d. make modules (utilizado en antiguos kernels, actualmente no es necesario)
e. make modules_install
f. make install
8. Una vez que el kernel fue compilado, ¿dónde queda ubicada su imagen? ¿dónde debería ser reubicada? ¿Existe algún comando que realice esta copia en forma automática?
9. ¿A qué hace referencia el archivo initramfs? ¿Cuál es su funcionalidad? ¿Bajo qué condiciones puede no ser necesario?
10. ¿Cuál es la razón por la que una vez compilado el nuevo
kernel, es necesario reconfigurar el gestor de arranque que tengamos instalado?
11. ¿Qué es un módulo del kernel? ¿Cuáles son los comandos principales para el manejo de módulos del kernel?
12. ¿Qué es un parche del kernel? ¿Cuáles son las razones principales por las cuáles se deberían aplicar parches en el kernel? ¿A través de qué comando se realiza la aplicación de parches en el kernel?
13. Investigue la característica Energy-aware Scheduling incorporada en el kernel 5.0 y explique brevemente con sus palabras:
a. ¿Qué característica principal tiene un procesador ARM big.LITTLE?
b. En un procesador ARM big.LITTLE y con esta característica habilitada. Cuando
se despierta un proceso ¿a qué procesador lo asigna el scheduler?
c. ¿A qué tipo de dispositivos opinás que beneficia más esta característica?