¿Qué estrategia resulta más robusta ante cambios futuros como agregar una nueva
subclase de User? Justificar con al menos dos argumentos.
- joined 
	1. Agregar una nueva clase es solo crear una nueva clase con `@Entity` que herede de User y tenga sus atributos propios en forma independiente y pudiendo manejar tema nulos o unique más fácil que con singleTable
	2. Si uso single table y aumento la cantidad de entidades hijas tendré demasiados datos `NULL` en la tabla que molestan a la legibilidad del modelo.
	