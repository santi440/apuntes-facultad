Entidades con las que trabajan los programas:
- Variables
- Estructuras de control 
- unidades de programa: rutinas o funciones
Las entidades tienen atributos y se almacenan en descriptores, si es en compilacion se llenan antes de ejecutar sino durante la ejecucion pero se tienen que llenar antes de que se haga uso de ese recurso.
### Ligadura
BInding time: momento de ligadura, cuando se asocia un valor a ese atributo. Debe estar bien definido.

#### Ligadura estatica: 
Si se establece antes de la ejecución y No se puede modificar. El termino estática referencia al binding time y a su estabilidad 
#### Ligadura dinamica: 
Si se establece durante la ejecución y puede modificarse durante ejecución de acuerdo a alguna regla especifica del lenguaje.
Excepción: constantes (el binding es e ejecución/runtime pero no puede ser modificado luego de establecido)

# Atributos de Variables:
- Nombre: string de caracteres que se usa para referenciar a la variable. (identificador)
- Alcance: es el rango de instrucciones en el que se conoce el nombre, es visible, y puede ser referenciada.
	- Alcance estatico: tambien llamado lexico.Se define el alcance en términos de la estructura léxica del programa. Puede ligarse estáticamente a una declaración de variables (referencia explícita o implícita) examinando el texto del programa, sin necesidad de ejecutarlo.
	- Alcance dinamico: no lo conoce, ni lo usa, ni su vieja. Se conoce en tiempo de ejecucion. Cada declaración de variable extiende su efecto sobre todas las instrucciones ejecutadas posteriormente, hasta que una nueva declaración de la variable (con el mismo nombre) sea encontrada durante la ejecución
	  ![[Tipos de alcance.png]]
- Tipo: es el tipo de variables definidas, tiene asociadas rango de valores y conjunto de operaciones permitidas
- L-value: es el lugar de memoria asociado con la variable, está asociado al tiempo de vida (variables se alocan y desalocan)
- R-value: es el valor codificado almacenado en la ubicación de la variable