Dado el siguiente esquema:
CURSOS(#curso, titulo_curso, #nro_modulo, titulo_modulo, contenido_modulo, nombre_autor, email_autor, contraseña_autor, año_edicion, calificacion, referencia)
Donde:
● Cada curso (#curso) se va editando todos los años, y en cada año (año_edicion)
puede cambiar sus módulos, no así el título y el autor.
● En cada año que se edita un curso, recibe varias calificaciones anónimas.
● El email de cada autor se usa como login, y no puede repetirse en el sistema.
● Los números de módulo (#nro_modulo) son secuenciales (modulo 1, 2, 3, etc).
Es decir, en cada edición de cada curso se enumeran los módulos de la misma
forma, y se pueden repetir en diferentes ediciones de cursos.
● Cada curso tiene múltiples referencias bibliográficas, que se mantienen a través
de todas sus ediciones.
Dadas las siguientes DF:
● #curso -> titulo_curso, email_autor
● #curso, año_edicion, #nro_modulo -> titulo_modulo, contenido_modulo
● email_autor -> nombre_autor, contraseña_autor
Dada la siguiente CC:
● (#curso, año_edicion, #nro_modulo, calificacion, referencia)
Y el esquema en BCNF
CURSOS_N (#curso, año_edicion, #nro_modulo, calificacion, referencia)
Seleccione las DM que son válidas a la vez en el esquema CURSOS_N:
==● #curso ->> año_edicion==
==● #curso ->> referencia==
==● #curso,año_edicion ->> calificacion==
● referencia ->> #curso
==● año_edicion ->> #curso==
Existe alguna dependencia multivaluada más que no se menciona entre las opciones?