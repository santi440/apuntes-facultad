Indique si utilizando el dataset Libros es posible resolver los siguientes problemas:
a. Obtener los títulos de todos los libros. FALSO -> Una tarea map debería analizar una  tupla en forma independiente del resto de las tuplas por lo que no puedo saber cual es la primera.
b. Obtener la cantidad de palabras promedio por párrafo. VERDADERO -> cada parrafo es una linea
c. Obtener la cantidad de párrafos promedio por libro. FALSO-> Si son independientes no tengo manera de saberlo, es un gran dataset, no puedo determinar cual es el libro
d. Obtener la cantidad de caracteres del párrafo más extenso. VERADERO -> map cuenta los caracteres por parrafo y reduce determina el max
e. Cantidad total de párrafos con diálogos (se entiende por párrafo con diálogo
aquel que empieza con un guión) VERDADERO -> Es un if en el map, reduce solo cuenta la cantidad de tuplas
f. El diálogo más largo (se entiende por diálogo a una secuencia de párrafos con
diálogo que aparecen de manera consecutiva) FALSO-> al ser independientes no tengo manera de saberlo
g. El top 20 de las palabras más usadas por cada libro FALSO-> al ser independientes no tengo manera de saberlo