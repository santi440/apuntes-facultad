Verdadero/Falso en Transformación del modelo de E/R al modelo Relacional. Cursos
Dada la transformación 1 a 1 del modelo de entidades y relaciones al modelo relacional,responda si las siguientes afirmaciones son V o F:
A. La relación brinda tiene los atributos correspondientes y su clave está bien definida VERDADERO
B. La relación tiene tiene los atributos correspondientes y su clave está bien definida VERDADERO
C. La relación dicta tiene los atributos correspondientes y su clave está bien definida. FALSO-> El identificador deberia de ser el idic porque un curso es dictado por un y solo un profesor pero un profesor dicta uno o N cursos. Al poner el cuil no permito que aparezca más de una vez, por lo que, el profesor no puede dictar más de un curso.
D. La relación tiene no debería existir y los identificadores de la agregación deberían estar en InfoCurso. FALSO -> Como el pase es 1 a 1, todo se pasa. Lo que se menciona es un paso adicional para eficiencia que se considerará más adelante de requerirlo
E. La relación dicta no debería existir y los atributos de Profesor deberían estar en
InfoCurso  FALSO-> De nuevo, como es 1 a 1 todo se pasa.
![[Pasted image 20250831152732.png]]
semestre (**#semestre**, nro, año)
curso (**codigo**, nombre, reseña)
profesor (**cuil**, nyap, fecha_nac, fecha_ingreso)
infocurso (**idic**, fecha_comienzo, aula, día_semana, hora)
brinda (**#semestre, codigo**)
tiene (**#semestre, codigo, idic**)
dicta (idic, **cuil**)