Describir ventajas y desventajas concretas de EAGER y LAZY, en términos de
performance de acceso como de espacio en memoria. ¿Por qué configurar EAGER en
todas las relaciones suele ser una mala idea en aplicaciones reales?
## EAGER
- **Performance de acceso:**
    - **Ventaja:** El acceso inicial es rápido una vez que el objeto está en memoria, ya que los datos relacionados ya están ahí. No hay "latencia de consulta" cuando llamás al getter.
    - **Desventaja:** La consulta inicial es **pesada**. Hibernate suele generar `LEFT OUTER JOINs` complejos. Si tenés varias relaciones `EAGER`, una simple consulta a una tabla puede convertirse en una "superconsulta" que estrese al motor de base de datos.
- **Espacio en memoria:**
    - **Desventaja:** **Alto consumo**. Estás guardando en la memoria RAM objetos que quizás nunca uses. Si recuperás 100 `Purchase` y cada una trae su `Route`, sus `ItemService`, su `Review`, etc., estás llenando el heap de la JVM con datos "por si las dudas".
## LAZY 
- **Performance de acceso:**
    - **Ventaja:** La consulta inicial es **quirúrgica y veloz**. Solo trae los campos de la tabla principal.
    - **Desventaja:** Si luego necesitás los datos, JPA debe realizar una nueva consulta (latencia adicional). Si no se gestiona bien, podés caer en el **Problema de las N+1 consultas** (una consulta para traer la lista y una consulta extra por cada elemento de la lista para traer su relación).
- **Espacio en memoria:**
    - **Ventaja:** **Eficiencia máxima**. Solo ocupás espacio con los datos estrictamente necesarios en ese momento.

EAGER en todo es malo:
- **Explosión Combinatoria de Datos:** Si `Purchase` carga `ItemService` (EAGER), e `ItemService` carga `Service` (EAGER), y `Service` carga `Supplier` (EAGER)... una simple petición de "ver mi última compra" termina trayendo medio grafo de la base de datos.
- **Carga de Objetos Innecesarios:** Imagina un reporte que solo necesita el `totalPrice` y la `date` de 1.000 compras. Si tenés `EAGER`, estarías cargando miles de objetos `Route`, `Review` e `ItemService` que **no vas a mostrar**, desperdiciando ciclos de CPU y memoria.
- **Inflexibilidad:** Es muy difícil "apagar" un `EAGER` una vez definido en la entidad. En cambio, con `LAZY`, siempre podés forzar la carga cuando realmente la necesites usando `FETCH JOIN` en una consulta específica.

>[!Note]
>El estandar suele ser Lazy por defecto, eager por excepción. Ademas Lazy trae un posible `LazyInitializationException` si se cierra la session antes de recuperar un dato pero es lo ideal para no saturar la memoria por cada consulta.