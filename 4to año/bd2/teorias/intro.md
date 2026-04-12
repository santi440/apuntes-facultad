## RDMBS
Bases de datos relacionales, mantienen una estructura y sencillez  y su lenguaje de consulta asociado que las caracterizan. 
Mantienen hoy en dia por legacy 
Arquitectura -> objetos, medio desdibujado. 

## ORM 
Un lenguaje orientado a objetos  no necesariamente  tiene clases. JS es una verga y me lo recalcan (nuevamente).  Tiene objetos.
### Principio de impedancia 
Relacional y oo no tienen nada que ver

Transaccion 
el problema es la distribucion no la persistencia (gracias nefi, mi vida :) <3

Sepearan persistencia de logica. 

Hidratar los objetos - > requiere un constructor sin argumentos. Crea un objeto antes de cargarle los datos del db.
Uso de proxys para cargarlos objetos relacionados con retardo. -> clases no finales

Ortogonalidad -> cualquier objeto sin importar su tipo debe ser persistido 

Persistencia por alcance: todo objeto al cual se pueda llegar a partir de un
objeto ya persistente, debe ser necesariamente persistente

Capacidad del modelo de oo de ignorar la tecnologia de persistencia suyacente. 