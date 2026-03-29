![[Pasted image 20260329163818.png]]

El modelo relacional y el modelo OO presentan tensiones conocidas como impedance
mismatch. Identificar cómo se manifiesta cada una en el modelo dado:
a) Identidad: cómo es posible identificar un objeto Purchase en Java vs. en la base de
datos?
- En Java dos objetos pueden tener los mismos valores en sus atributos y ser dos instancias independientes y diferentes. Se diferencian por los metodos `equals()` y `hashCode()`. 
- En el caso de una DB es necesario una `PrimaryKey`, un atributo especifico y univoco que identifique al elemento. Si dos registros tienen el mismo `id`, en este caso, son el mismo, indistintamente de los demás valores.
b) Relaciones: cómo se navega de una Purchase a sus ItemService en Java vs. en
SQL?
- **En Java:** La navegación es **direccional**. De una `Purchase`, navegás hacia sus `ItemService` a través de una colección (como un `List<ItemService>`). Es un puntero que te lleva de un objeto a otro. Si la relación no es bidireccional en el código, no podés ir de `ItemService` a `Purchase` sin una referencia explícita.
- **En SQL:** La navegación es **asociativa** y se realiza mediante **JOINs**. No hay punteros. Para ir de `Purchase` a sus ítems, tenés que cruzar las tablas por clave foránea: `SELECT * FROM ItemService WHERE purchase_id = X`. Lo bueno de SQL es que podés navegar en cualquier sentido (de hijo a padre o de padre a hijo) siempre que tengas las FKs.
c) Herencia: ¿cómo podría representarse la jerarquía User/DriverUser/TourGuideUser
en una tabla relacional?
Como las bases de datos relacionales no tienen herencia nativa, tenés tres estrategias clásicas:
1. **Single Table (Tabla única):** Una sola tabla `Users` que tiene todas las columnas de las tres clases más una columna "discriminadora" (tipo de usuario). Los campos específicos (como `education` o `expedient`) quedarían en _null_ si no corresponden.
2. **Joined (Tabla por subclase):** Una tabla para `User`, otra para `TourGuideUser` y otra para `DriverUser`. Las tablas de las subclases comparten el mismo ID que la de `User` y se vinculan mediante una FK.
3. **Table per Class:** Una tabla completa por cada clase concreta. La tabla `DriverUser` tendría sus propios campos y también los heredados de `User` (`username`, `password`, etc.), sin una tabla `User` central.

d) Ciclos de referencia: ¿existe algún ciclo en el modelo? ¿Cómo impacta en la
persistencia?

Sí, se puede observar un ciclo potencial de navegación: `Purchase` → `Route` → `TourGuideUser` → `Purchase` (a través de la colección `purchases` que hereda de `User`).

**Impacto en la persistencia:**
- **Carga infinita:** Al intentar convertir el objeto a JSON o imprimirlo (con `toString`), podrías entrar en un bucle infinito si no se corta la recursión.
- **Guardado/Inserción:** Genera problemas de "orden de inserción". ¿Qué se guarda primero si ambos se necesitan mutuamente?
- **Gestión de memoria:** En un ORM, si el ciclo no está bien mapeado (usando `mappedBy` en Hibernate), podrías terminar con inconsistencias donde el objeto A dice que conoce a B, pero B no sabe que pertenece a A.