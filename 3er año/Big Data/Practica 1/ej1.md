Responda para cada job: ¿Cuántas veces (invocaciones) se ejecuta la función map?
¿Cuántas veces (invocaciones) se ejecuta la función reduce? ¿Cuántos mappers se
ejecutan? ¿Cuántos reducers se ejecutan? ¿Qué datos recibe cada función reduce?
¿Cuál es la salida de cada job?
![[Pasted image 20250831171446.png]]
a) Job A
```python
def map(k1, v1, context):
	context.write(1, v1)
def reduce(k2, v2, context):
	n = 0
	for v in v2:
		n = n + 1
	context.write(k2, n)
```
Invocaciones del map: 16 
Invocaciones del reduce: 1
mappers : 4
Reducers: 1
Cada reduce recibe un valor arbitrario 1 en k2  y el valor de v2 es la lista iterable de todos los values del split. Cuenta cuantos elementos tiene.
Se generan 1 archivos con el conteo del total de tuplas en todos los split

b) Job B
```python
def map(k1, v1, context):
		context.write(1, v1)
def reduce(k2, v2, context):
	n = 0
	for v in v2:
		n = n + v
	context.write(k2, n)
```
Invocaciones del map: 16 
Invocaciones del reduce: 1
mappers : 4
Reducers: 1
Cada reduce recibe un valor arbitrario 1 en k2  y el valor de v2 es la lista iterable de todos los values del split. Suma todos los valores.
Se generan 4 archivos con la suma de cada split

c) Job C	
```python
def map(k1, v1, context):
	if (v1 < 30):
		context.write(1, k1)
	else:
		context.write(2, k1)
def reduce(k2, v2, context):
	max = -1
	for v in v2:
		if(v > max):
			max = v
	context.write(k2, max)
```
Invocaciones del map: 16 
Invocaciones del reduce: 2
mappers : 4
Reducers: 1
Cada reduce recibe un valor 1 o 2 en k2  y el valor de v2 es la lista iterable de todos los values que tiene que procesar. Determina el máximo todos los valores que recibió(originalmente claves).
Se generan 4 archivos con la suma de cada split

d) Job D
```python
def map(k1, v1, context):
	for v in range(v1):
		context.write(k1, v1)
def reduce(k2, v2, context):
	n = 0
	for v in v2:
		n = n + 1
	context.write(k2, n)
```
Invocaciones del map: 16 
Invocaciones del reduce: 15
mappers : 4
Reducers: 1

Cada reduce recibe el valor que originalmente habia en k1 en k2  y el valor de v2 es la lista iterable del mismo v1 repetido v1 veces. Cuenta cuantos elementos tiene la lista recibida.
Se generan 1 archivos con el conteo del total de tuplas recibido con esa clave.
>[!NOTE] No entiendo que pasa
>Preguntar que pasa que no me lee el  (4,97) que lo lee como 9, con el (3,21) que lo lee como 22 (nota de la nota, hay otro k1 3), (19,35) que lo lee como 3 y el 32 que esta repetido. -> a ciego patron que veo es que los ultimos de los archivos los lee mal y no se que hace con los duplicados, para mi con estos ultimos deberia sumarlos pero no me dan las matematicas
>

e) Job E
```python
def map(k1, v1, context):
	context.write(v1, k1)
def reduce(k2, v2, context):
	n = 0
	for v in v2:
		n = n + 1
		context.write(v, n)
```
Invocaciones del map: 16 
Invocaciones del reduce: 16
mappers : 4
Reducers: 1
Los map invierten la tupla, k1 pasa a ser v1 y v1 pasa a ser la k1.
Los reducers reciben todos los datos y cuentan cuantos elementos tienen con esa clave. Si originalmente tenia 21 en el v1 (que se repite dos veces), ese reducer escribe 21 2

>[!NOTE] No entiendo que pasa
>Resultado ni remotamente parecido :(
>