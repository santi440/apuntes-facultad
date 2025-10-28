
Indique si las siguientes afirmaciones sobre triggers son verdaderas o falsas.
Justifique las falsas.
a. Un trigger se ejecuta únicamente cuando se inserta una fila en una tabla. [F] podria ser inserciones, eliminaciones o updates, depende el tipo de accion sobre la tabla asociada que se especifique cuando se crea el trigger.
b. Un trigger puede ejecutarse antes o después de la operación, esto es
definido automáticamente según el tipo de la operación (UPDATE, INSERT
o DELETE) [F] No es automatico, se especifica con la sentencia `BEFORE` o `AFTER`
c. Todo trigger debe asociarse a una tabla en concreto. [V]
d. NEW y OLD son palabras clave que permiten acceder a los valores de las
filas afectadas y se pueden usar ambos independientemente de la
operación utilizada. [F] `NEW` no tiene sentido en delete y `OLD` en insert. Ambos a la vez solo tienen sentido si la operación es UPDATE
e. FOR EACH ROW en un trigger se usa para indicar que el trigger se
ejecutará una vez por cada fila afectada por la operación. [V]