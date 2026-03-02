Dados los siguientes modelos E/R sobre vendedores que trabajan en locales, responda:
A. En qué casos modelaría un atributo fecha_de_ingreso en la relación se_emplea –entre Vendedor y Local - como se muestra en la variante “A”?
Como un empleado puede trabajar en múltiples locales y por ende tener una antigüedad diferente en todos ellos es necesario modelarlo.
El caso en que se marque como un solo atributo simple en la relación implica que si la persona renuncia o deja de trabajar en el local pero eventualmente quiere volver se va a sobrescribir la antigüedad anterior porque no pueden existir dos pares **`#vendedor,#local`** iguales en la relación `se emplea`.
![[Pasted image 20250831150959.png]]
B.¿En qué casos haría falta modelar una entidad Fecha de Ingreso relacionada con la
agregación Vendedor Local como se muestra en la parte llamada B en el modelo?
Similar al anterior pero si ahora importa mantener un historial de la empleación de la persona a lo largo del tiempo, es cuando la fecha de ingreso toma suficiente peso para convertirla en una entidad.
![[Pasted image 20250831151216.png]]
C. ¿Qué se está modelando con Horario cuando está la agregación? Indíquelo agregando la cardinalidad correspondiente.
Representa el horario en que trabaja un vendedor en un local. Se debería modelar con cardinalidad tal que se lea: un vendedor en un local trabaja en uno o n horarios y un horario es trabajado por 1 y  solo un 1 vendedor por local (depende del enunciado, es una decisión de diseño por si las franjas horarias son muy variantes)