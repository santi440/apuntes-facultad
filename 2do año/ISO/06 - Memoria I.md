El espacio de direcciones es una abstracción lógica que debe estar representado en la memoria física. En forma lógica tenemos tanta memoria como podemos referenciar por la arquitectura (32 bits : 2 ^ 32). 
El SO dentro de sus funciones principales debe asignar y liberar espacio en memoria principal y llevar un registro de las partes de memoria que se esta utilizando. El objetivo es alojar el mayor numero de procesos. 
![[Deberes de SO en memoria.png]]
# Administración de memoria
Se divide lógicamente la memoria para alojar múltiples procesos. Depende del mecanismo provisto por el HW.
### Requisitos 
1. Reubicación: Potencialmente un proceso puede ser swap out y al volver a memoria colocarse en diferentes direcciones. Las referencias a memoria se traducen a las pociones actuales que ocupa el proceso.
2. Protección: Un proceso no debe acceder a direcciones de memoria de otro proceso, salvo las compartidas. Este chequeo se hace durante la ejecución 
Ambos hechos por el HW
3. Compartir: Permite que varios procesos accedan a la misma porción de memoria (de datos compartidos o librerías /código en común)
### Conversión de direcciones:
Se puede hacer en tiempo de **ejecución** o de compilación.
- Memoria Lógica : Referencia a una localidad de memoria independiente de la asignación actual de memoria. Con ella trabaja la CPU. 
- Memoria Física: Dirección absoluta en la RAM, la CPU (en concreto, Memory Management Unit, MMU) debe traducir la dirección lógica -> física. 
Es necesario traducir las pos de memoria: Con el registro base y limite (de ese proceso) es un desplazamiento. Base + Pos = dir fisica< limite.
Cada vez que hay un context Switch se re-programa la MMU (donde están los registros) y solo se puede hacer en modo kernel

### Particiones
El modelo de dividir la memoria desde un base hasta un limite (tener continuidad, todo el proceso junto) se conoce como partición.
- Fija : De antemano el SO dividía la memoria y cada uno de los espacios de direcciones entraba en una partición. 
- Dinámica: A medida que los procesos iban creándose la memoria se dividía de acuerdo al espacio de direcciones de ese proceso. 
#### Fragmentación 
Espacio no utilizable porque no esta en espacios continuos.
- Interna: Espacio asignado sin utilizar. Producido en particiones fijas. No tiene solución :(
- Externa: A medida que los procesos finalizan quedan "huecos" de memoria. Podría tener suficiente memoria como para alojar un nuevo proceso pero al estar no contigua no es utilizable, se podría compactar pero es extremadamente costoso. Se produce en particiones dinámicas. 

## Maneras de romper la continuidad
### Paginación 
Divide la memoria lógica en trozos de igual tamaño -> **Páginas**
Divide la memoria física en trozos de igual tamaño -> **Marco**
Una pagina entra en un marco. Para cada proceso se lleva una **tabla de paginas** que especifica en que marcos fue guardada una página. La dirección lógica ahora se interpreta como el numero de marcos * tam + dirección lógica. Puede tener fragmentación interna.
![[Paginacion.png]]
### Segmentación
Un segmento es una unidad lógica de las partes que componen un programa (código, funciones, variables, stack, ...). Todo lo que cae en un segmento tiene características similares (rwx). Un programa es una colección de segmentos de distintos tamaños. 
Se tiene, por proceso, una tabla de segmentación que tiene la base y limite para cada segmento del programa. Acceder a una dirección implica que el selector de segmento busque quien le corresponde, la base asociada + dir logica = dir fisica < limite asociado
Puede producir fragmentación externa, la memoria ram se va cargando/descargando con segmentos pero este debe ser continuo, lo que potencialmente puede dejar espacios no asignados inutilizables.
![[segmentacion.png]]

#### Comparación entre S y P
Segmentación es mas eficiente para compartir y proteger > con una sola entrada en la tabla de segmentos se puede acceder al lugar deseado, si son varios procesos los que usan un mismo segmento se lo agrega a las tablas de segmento (en paginación serian múltiples paginas con múltiples entradas). Protegiendo un solo segmento contra escritura se mantiene una sola entrada.
Paginación no tiene fragmentación externa > cualquier pagina puede ir a parar a cualquier lugar (f. interna >>>>> f. externa).

### Segmentación Paginada
Tiene dos niveles:
- Level 1: Segmentación, visible para el programador. Modulariza el programa en segmentos
- Level 2: Paginación, trasparente. Elimina la Fragmentación externa, se aplica paginación sobre cada segmento y la pagina es lo que se sube a memoria ram. Por cada segmento hay una tabla de pagina.
Al compartir un segmento, comparto un conjunto de paginas. Por ende, se conservan las ventajas de ambas técnicas. 
![[Segmentación Paginada.png]]