### A - Introducción
1. ¿Qué es GCC?
La GNU Compiler Collection (GCC) ==es un sistema de compiladores versátil y de código abierto desarrollado por el proyecto GNU==, esencial en sistemas Unix/Linux. Soporta lenguajes como C, C++, Fortran, Ada y Go, traduciendo código fuente a binarios ejecutables. Es fundamental para compilar el núcleo Linux y gran variedad de software

2. ¿Qué es make y para que se usa?
`make` es una ==herramienta de automatización, comúnmente usada en sistemas Unix/Linux, que compila código fuente y gestiona la creación de ejecutables basándose en un archivo== `makefile`. Lee dependencias para decidir qué archivos actualizar, automatizando la compilación de bibliotecas, la ejecución de tests y la limpieza de archivos temporales.

- **Makefile:** Define "objetivos" (acciones), dependencias y los comandos necesarios, usualmente escritos en C/C++.
- **Compilación Incremental:** `make` solo compila los archivos modificados, ahorrando tiempo.
- **Uso:** Se invoca simplemente ejecutando `make` en la terminal, lo que facilita el desarrollo.
- **Compatibilidad:** Nativo en Linux/macOS; en Windows se usa mediante herramientas como WSL, Cygwin o MSYS2

2. La carpeta /home/so/practica1/ejemplos/01-make de la VM contiene ejemplos de
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
3. ¿Qué es el kernel de GNU/Linux? ¿Cuáles son sus funciones principales dentro del
Sistema Operativo?
4. Explique brevemente la arquitectura del
kernel Linux teniendo en cuenta: tipo de
kernel, módulos, portabilidad, etc.
5. ¿Cómo se define el versionado de los
kernels Linux en la actualidad?
6. ¿Cuáles son los motivos por los que un usuario/a GNU/Linux puede querer
re-compilar el kernel?


