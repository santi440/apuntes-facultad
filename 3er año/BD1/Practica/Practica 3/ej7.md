ESTUDIANTE ( #legajo, nombreCompleto, nacionalidad, añoDeIngreso, códigoDeCarrera )
CARRERA ( códigoDeCarrera, nombre )
INSCRIPCIONAMATERIA ( #legajo, códigoDeMateria)
MATERIA (códigoDeMateria, nombre)
a) Obtener el nombre de los estudiantes que ingresaron en 2023.

ΠnombreCompleto(σañoDeIngreso=2023(ESTUDIANTE))

b) Obtener el nombre de los estudiantes con nacionalidad “Argentina” que NO estén en la carrera con código “LI07”

ΠnombreCompleto(ESTUDIANTE |X| ΠcodigoDeCarrera(Estudiante) - ΠcodigoDeCarrera(σcodigoDeCarrera = LI07(CARRERA)))
c) Obtener el legajo de los estudiantes que se hayan anotado en TODAS las materias
INCRIPCIONMATERIA % ΠcodigoDeMateria(MATERIA)