Verdadero/Falso. Justificar
![[Pasted image 20250831145347.png]]
El estado nacional implementó distintos subsidios destinados a sectores productivos. Cada subsidio tiene un nombre y un monto asignado.
Para cada subsidio se realiza una liquidación mensual, de la cual se registra a qué mes y año corresponde, el total gastado y la fecha de realización. En esta liquidación, a cada beneficiario del subsidio se le liquida un monto, el cual dependerá de la situación del beneficiario. Un beneficiario puede ser una persona Jurídica o Física, y en el caso de la persona física, debe estar inscripta en el monotributo. De cada beneficiario se conoce la actividad económica en la cual se encuentra inscripto y su cuil o cuit que lo identifica. De las personas jurídicas se conoce la razón social, provincia, departamento, localidad y cantidad de empleados. De las personas  físicas se conoce nombre y apellido, provincia, departamento, localidad y categoría del monotributo.

Para el diagrama de Entidades y Relaciones propuesto responda si las siguientes afirmaciones son verdaderas (V) o falsas (F). Justificar:
A. La relación tiene está mal definida, ya que debería ser entre persona y
categoría_monotributo. FALSO -> es entre fisica según el enunciado
B. La relación realiza está bien definida, ya que todas las personas realizan actividades. VERDADERO? el enunciado dice LA actividad pero por cardinalidad permite que una persona realice 1 o más actividades
C. La jerarquía de Persona representa correctamente la problemática. FALSO -> según el enunciado solo existen personas fisicas o juridicas, no puede ser una especialización y algunos atributos en común pueden subirse al padre, como por ejemplo  el depto, provincia o localidad
D. La relación pertenece está mal definida, ya que no puede haber atributos en las
relaciones. FALSO -> no hay problema con el atributo
E. La agregación de la relación posee está correctamente definida ya que con una relación uno a muchos se puede agregar. FALSO -> La agregación solo es posible con cardinalidad muchos a muchos
F. Con este diseño es posible conocer el saldo disponible del subsidio para futuras
liquidaciones. FALSO -> como el diseño está mal debido a la agregación no puedo responder a la pregunta
G. El modelo no tiene redundancia de datos. FALSO -> hay multiples posibles redundancias como el nombre que se repite en persona y persona fisica o el conjunto locacalidad y provincia aparecera multiples veces en las diferentes personas