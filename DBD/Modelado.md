Dos de los propósitos fundamentales del modelado de datos son: ayudar a comprender la semántica de los datos y facilitar la comunicación de los requerimientos del Sistemas de información (Si).

Un modelo de datos es un conjunto de herramientas conceptuales que permiten describir la información que es necesario administrar para un SI, las relaciones existentes entre estos datos, la semántica asociada y las restricciones de consistencia

Para construir un modelo se utilizan tres clases de abstracciones: clasificación, agregación y generalización. Estas abstracciones ayudan al diseñador a interpretar, clasificar y generar modelos de la realidad.

## Abstracción
La abstracción es un proceso que permite seleccionar algunas características de un conjunto de objetos del mundo real, dejando de lado
aquellos rasgos que no son de interés.

(La clasificación permite identificar los campos o atributos de los elementos individuales de datos. La agregación, en tanto, permite agrupar los campos o atributos formando registros de datos.)
### Abstracción de clasificación
Definir una clase. Una clase es una declaración o abstracción de objetos, lo que significa que es la definición de un objeto. Una clase se origina a partir de las características comunes que tienen los objetos que la componen. Con herencia múltiple. Se gráfica como árbol, la clase se define al tope del árbol, y los elementos de la clase, como hojas unidas al tope por líneas punteada. Relación **es un miembro de**
![[Pasted image 20240915171658.png]]

### Abstracción de agregación 
Define una nueva clase a partir de un conjunto de otras clases que representan sus partes componentes. En este caso, las clases hoja representan una relación **es_parte_de** de la clase raíz.
![[Pasted image 20240915171841.png]]
### Abstracción de generalización
Una abstracción de generalización define una relación de subconjunto entre los elementos de dos o más clases. Ej: Alumno y Docente -> Persona
El proceso inverso es la especialización. En una generalización, las especialidades (hijos) heredan las características del padre. Es una jerarquía o relación **es un**.
#### Cobertura
La propiedad de cobertura define el grado de relación entre padres e hijos.
- Total: cada elemento del padre está contenido en alguno de los hijos
- Parcial: cuando pueden existir elementos del padre que no se instancian sobre los hijos. 
Ambos dependen del problema 
- Superpuesta: si un elemento del padre puede estar en más de un hijo.
- Exclusiva: un elemento del padre puede estar en un y solo un hijo.

## Modelos de datos
Un modelo de datos es una serie de conceptos que puede utilizarse para describir un conjunto de datos y las operaciones para administrarlos. Los modelos de datos se construyen, en general, utilizando mecanismos de abstracción, y se describen mediante representaciones gráficas que tienen una sintaxis y una semántica asociadas.
Cuyo objetivo es disminuir la brecha existente entre la realidad del problema a resolver y el sistema a
desarrollar.
Genéricamente, los modelos de datos se dividen en tres grandes grupos: modelos basados en objetos, modelos basados en registros y modelos físicos.
### Modelos basados en OO
Utilizados para describir los datos de acuerdo con la visión que cada usuario tiene respecto de la BD (generar vistas de las necesidades que posee cada actor). 
Algunos ejemplos: **Modelo entidad relación**, Modelo orientado a objetos, Modelo de datos semántico y Modelo de datos funcional

### Modelos basados en registros
permiten describir los datos desde la perspectiva de cada usuario. Llevan su nombre, y se distinguen del anterior, debido a que la BD se estructura como registros de longitud fija, conformados por campos o atributos. El modelo relacional es el mas usado, antes existían modelos de red y jerárquico, hoy en desuso.

## Entidad Relación (ER) o Entidad Interrelaciones (EI)
Se basa en la concepción del mundo real como un conjunto de objetos  llamados entidades y las relaciones existentes entre dichas entidades.
Las **entidades** representan un elemento de utilidad para el problema, y cada elemento debe ser distinguible del resto. Cada entidad está conformada por un conjunto de **atributos** que la caracterizan. Una **relación** establece un nexo entre entidades.
El modelo ER **no llega a tener una implementación física**. Esto es, cualquier diagrama obtenido que represente el modelo de datos de un problema es solo un diagrama.
Una vez generado el modelo de datos ER definitivo para un problema, deberá utilizarse otra forma de modelado que permita obtener su implementación física (se utilizará el modelo relacional).

## Etapas
1. Modelado conceptual: es desarrollado durante la etapa de adquisición de conocimiento del problema; el analista se independiza del tipo de SGBD a utilizar y, por consiguiente, del producto de mercado. Así, el modelo conceptual se desarrolla independientemente de su implementación final (relacional, de red, jerárquico u OO).
2. Modelo lógico: el analista debe determinar el tipo de SGBD, debido a que las decisiones que debe tomar dependen de esa elección.
3. Modelo físico: es necesario tomar decisiones específicas. Estas últimas tienen que ver con el producto de mercado a utilizar, es decir, el SGBD específico.
## Modelo Relacional
Utiliza un conjunto de tablas para representar las entidades y las relaciones existentes, definidas en el modelo ER. Cada tabla tiene una estructura conformada por columnas o atributos que representan, básicamente, los mismos atributos definidos para el modelo ER. Cada fila (registro) de la tabla se denomina tupla o relación.
