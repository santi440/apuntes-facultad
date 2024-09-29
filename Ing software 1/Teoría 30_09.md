# Tablas de decisión

a)Las personas que viajen a Europa o América y son pasajeros frecuentes, acceden a un descuento de un 17% en el valor de su pasaje.
b)Además, los que van a Europa sean o no frecuentes  reciben un descuento adicional.
c)Los pasajeros que pagaron en efectivo y son de tipo frecuente, tienen derecho a la compra de un pasaje al mismo destino por un 50% de su valor.
d)Los pasajeros que pagaron en efectivo, y no son del tipo frecuente, se les concede una cantidad de kilómetros gratuitos en su siguiente viaje.
e)Los que son o no son frecuentes y viajan a Europa, tienen derecho a una noche gratuita en un hotel de la ciudad destino, y tienen el mismo derecho los que van países de América y son frecuentes.

| CONDICIONES             |     |     |     |     |     |     |     |     |
| ----------------------- | --- | --- | --- | --- | --- | --- | --- | --- |
| Viajan a Europa         | V   | V   | V   | V   | F   | F   | F   | F   |
| Viajan a America        | F   | F   | F   | F   | V   | V   | V   | __  |
| pasajeros frecuentes    | V   | V   | F   | F   | V   | V   | F   | F   |
| Pagan en efectivo       | V   | F   | V   | F   | V   | F   | V   | F   |
| **Acciones**            |     |     |     |     |     |     |     |     |
| Descuento del 17%       | X   | X   |     |     | X   | X   |     |     |
| Descuento adicional     | X   | X   | X   | X   |     |     |     |     |
| Descuento mismo destino | X   |     |     |     | X   |     |     |     |
| kilometros gratuitos    |     |     | X   |     |     |     | X   |     |
| noche gratuita de hotel | X   | X   | X   | X   | X   | X   |     |     |
