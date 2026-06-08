36. Insertar una lista llamada pets con el valor "dog".
    `LPUSH pets "dog"`
37. ¿Qué sucede si se ejecuta el comando GET sobre pets? ¿Cómo se obtienen los valores de una lista?
    `GET pets (error) WRONGTYPE Operation against a key holding the wrong kind of value`
    Se hacen pops que elimina y recupera el dato insertado primero (tipo lista)  o con LRANGE = LRANGE vuelo:ar389 0 -1
38. Agregar a la lista pets el valor "cat" por la izquierda.
    `LPUSH pets "cat"`
39. Agregar a la lista pets el valor "fish" por la derecha.
    `RPUSH pets "fish"` 
40. ¿Qué tipo de dato es el valor de pets?
    list
41. Eliminar el valor del extremo izquierdo de la lista.
    `LPOP pets`
42. Eliminar el valor del extremo derecho de la lista.
    `RPOP pets`
43. Agregar a una clave "vuelo:ar389" los valores: aep, mdz, brc, nqn y mdq.
	`RPUSH "vuelo:ar389" aep mdz brc nqn mdq`
44. Ordenar los valores de la lista "vuelo:ar389". ¿Qué sucede si se solicitan todos los valores de la lista luego de ordenarla?
    `SORT vuelo:ar389 ALPHA`
    `LRANGE vuelo:ar389 0 -1` = devuelve la lista original porque no lo guardas sino que lo ordenas en ram y lo mostras (es solo lectura). Si se quiere ordenar se encadena con un store asi  `SORT vuelo:ar389 ALPHA STORE vuelo:ar389`
45. Insertar el valor "fte" inmediatamente después de "brc".
    `LINSERT vuelo:ar389 AFTER brc fte`
46. Insertar el valor "ush" inmediatamente antes de "fte".
    `LINSERT vuelo:ar389 BEFORE fte ush`
47. Modificar el último elemento de la lista por "sla".
    `LSET vuelo:ar389 -1 sla`
48. Obtener la cantidad de elementos de "vuelo:ar389".
    `LLEN vuelo:ar389`
49. Obtener el tercer valor de "vuelo:ar389".
    `LRANGE vuelo:ar389 2 2` = indice arranca en 0, el tercero seria el 2 
50. Eliminar el valor "aep" de "vuelo:ar389".
    `LREM vuelo:ar389 1 aep`
51. Quedarse únicamente con los valores de las posiciones 3 a 5 de "vuelo:ar389".
`LTRIM vuelo:ar389 3 5`    
52. Agregar en "vuelo:ar389" el valor "fte". ¿Cuántas veces aparece ahora en la lista?
    2 veces = `LPUSH vuelo:ar389 fte`