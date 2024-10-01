# Tablas de decisión

Permite presentar el aspecto lógico de problemas complejos que describen al sistema como un conjunto de condiciones en determinado momento (Técnicas perfecciones dinámica).
### Componentes
- Posibles condiciones satisfechas por el sistema en un momento dado 
- Acciones a ser tomadas como resultado
- Reglas cuando se dan ciertas condiciones explica el como proceder.
Deben ser simples 
![[Estructura de TD.png]]
Excluyentes : Se aclara, se colocan N-1 condiciones y se engloba una en el caso falso de las otra. 

a)Las personas que viajen a Europa o América y son pasajeros frecuentes, acceden a un descuento de un 17% en el valor de su pasaje.
b)Además, los que van a Europa sean o no frecuentes  reciben un descuento adicional.
c)Los pasajeros que pagaron en efectivo y son de tipo frecuente, tienen derecho a la compra de un pasaje al mismo destino por un 50% de su valor.
d)Los pasajeros que pagaron en efectivo, y no son del tipo frecuente, se les concede una cantidad de kilómetros gratuitos en su siguiente viaje.
e)Los que son o no son frecuentes y viajan a Europa, tienen derecho a una noche gratuita en un hotel de la ciudad destino, y tienen el mismo derecho los que van países de América y son frecuentes.

Podria tener viajes a otros lugares que no sean Europa/ America ? 
Si viaja a America o a otro lugar y no es frecuente ni paga en efectivo no se le hace ninguna bonificación

| CONDICIONES                          | 1      | 2      | 3      | 4      | 5      | 6      | 7      | 8      | 9   |
| ------------------------------------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | --- |
| Viajan a Europa                      | V      | V      | V      | V      | F      | F      | F      | F      | F   |
| Viaja a America                      | F      | F      | F      | F      | V      | V      | _      | _      | F   |
| pasajeros frecuentes                 | V      | V      | F      | F      | V      | V      | F      | F      | V   |
| Pagan en efectivo                    | V      | F      | V      | F      | V      | F      | V      | F      | V   |
| **Acciones**                         | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ |     |
| Descuento del 17%                    | X      | X      |        |        | X      | X      |        |        |     |
| Descuento adicional                  | X      | X      | X      | X      |        |        |        |        |     |
| Descuento mismo destino              | X      |        |        |        | X      |        |        |        |     |
| kilometros gratuitos                 |        |        | X      |        |        |        | X      |        |     |
| noche gratuita de hotel              | X      | X      | X      | X      | X      | X      |        |        |     |
| No se le aplica ninguna Bonificación |        |        |        |        |        |        |        | X      | X   |
## Análisis Estructurado
La técnica de análisis estructurado permite lograr una representación gráfica que permite lograr una comprensión. Énfasis en el procesamiento y la transformación de datos.

### Diagrama de Flujo de Datos (DFD)
Red de procesos funcionales conectados entre si por conductos y almacenamiento de datos, o también llamado burbuja. Representa la transformación de entradas a salidas.
- Entidad externa -> rectangulito, es una fuente de datos o quien le tienen que llegar los datos. Serian los actores o entidades
- Circulo representa un proceso (burbuja): que trasforman los datos
- Flujo: flecha que salen y llegan a los procesos con los otros 2 elementos. Información viajando
- Almacén de datos: datos que persisten en el tiempo. Podría ser una BD.
#### Diagrama de contexto
Nivel mas abstracto que muestra las entradas básicas y salidas, solo un proceso representa todo el sistema. Explosión de un proceso: busca subprocesos. 

No pueden aparecer entidades externas nuevas. Si pueden aparecer nuevos almacenes de datos. En un nivel inferior deben estar representados todos los flujos.
Se redactan listas de eventos para poder armar un MiniDFD para cada evento (te dejaría en un nivel 2-3) y deberías aumentar el nivel de abstracción para los niveles superiores. 