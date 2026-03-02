LUGAR_TRABAJO ( #empleado, #departamento )
CURSO_EXIGIDO ( #departamento, #curso )
CURSO_REALIZADO ( #empleado, #curso )

a) ¿Quiénes son los empleados que han hecho todos los cursos, independientemente de qué departamento los exija?

TODOS <- Π#curso(CURSO_EXIGIDO)
CURSO_REALIZADO % TODOS

Con la proyección me quedó con todos los cursos sin repetidos

b) ¿Quiénes son los empleados que ya han realizado todos los cursos exigidos por sus
departamentos?

NECESITO<- Π#empleado,curso (LUGAR_TRABAJO |X| CURSO_EXIGIDO)
Necesito tiene los empleados y el curso que necesitan para su depto

Π#empleado (LUGAR_TRABAJO) - (Π#empleado (NECESITO- CURSO_REALIZADO))
La primera diferencia se encarga de determinar los empleados que les faltan cursos para hacer (los que NO hicieron todos los exigidos por su depto) y la segunda los elimina del conjunto total dejando solo los que completaron todos.