a) Seleccione cuál de las siguientes dependencias multivaluadas es válida, por sí
sola, en el esquema y además cumple en ser trivial. Justifique su elección.
R1 (#curso, #profesor, año)
Donde un curso se desarrolla cada año y en él participan varios profesores que
pueden variar por los años.
Dependencias multivaluadas:
DM1: ==#curso ->> #curso, #profesor, año== : trivial porque curso siempre ->curso y multivaluada porque multiples tuplas por cada profesor
DM2: #curso, año ->> #profesor : es multivaluada pero no trivial
DM3: #curso ->> #profesor : es multivaluada pero no trivial
DM4: #profesor, #curso, año ->> #profesor : es multivaluada pero no trivial

b) Dado el siguiente esquema, elija un conjunto de dependencias multivaluadas
válidas para el esquema:
R2 (#Línea, #Ramal, #Colectivo, dniEmpleado)
Donde cada línea de colectivo posee diversos ramales, numerados secuencialmente a partir de uno, y estos ramales poseen varios colectivos, exclusivos de cada ramal. En la empresa trabajan diversos empleados.
Dependencias multivaluadas:
DM1: ==#Linea ->> #Ramal==
DM2: #Linea ->> #Colectivos
DM3: ==#Linea, #Ramal ->> #Colectivo==
DM4: #Linea, #Colectivo ->> #Ramal
DM5: #Linea ->> dniEmpleado
==DM6: { } ->> dniEmpleado==

c) Para el esquema dado, el cual se sabe está en BCNF, seleccione de entre las
posibles un conjunto de dependencias multivaluadas válidas en el esquema.
¿Está actualmente en 4FN? Justifique por cada DM, porque es válida o porque
no.
R3 (#pelicula, #autor, #actor, #equipo_rodaje, #auspiciante)
Donde una película es realizada por varios autores, los cuales pueden realizar
varias películas. En ella participan varios actores, también ellos pueden
participar en muchas películas. En el rodaje de cada película se ven
involucrados varios equipos de rodaje y varios auspiciantes.
Dependencias multivaluadas:
DM1: #pelicula ->> #autor
DM2: #pelicula ->> #actor
DM3: #pelicula ->> #actor, #autor
DM4: #pelicula, #autor ->> #actor
DM4: #auspiciante ->> #pelicula
DM5: #pelicula ->> #auspiciante
DM6: #pelicula ->> #equipo_rodaje
DM7: { } ->> #equipo_rodaje

d) Dado el siguiente esquema con la siguiente clave candidata:
PROGRAMA(#programa, nombre, genero, descripcion)
CANAL(#canal, nombre)
PROGRAMA_CANAL(#programa, #canal, dia, hora)
CC = {#programa, #canal, dia, hora}
Donde un programa puede estar en muchos canales, y en cada canal se da en
diferentes días y horarios.

e)Marcar la opción correcta y justificar:
A. Las 3 relaciones se encuentran en 4FN
B. Las 3 relaciones se encuentran en BCNF y no es posible llevarlas a 4FN
C. Las relaciones PROGRAMA y CANAL se encuentran en BCNF (no siendo
posible llevarlas a 4FN) y PROGRAMA_CANAL se encuentra en 4FN
D. Las relaciones PROGRAMA y CANAL se encuentran en 4NF,
PROGRAMA_CANAL se encuentra en BCFN y puede llevarse a 4FN
E. Las relaciones PROGRAMA y CANAL se encuentran en 4NF
PROGRAMA_CANAL se encuentra en BCFN y no puede llevarse a 4FN