Responda para cada job: ¿Cuántas veces (invocaciones) se ejecuta la función map?
¿Cuántas veces (invocaciones) se ejecuta la función reduce? ¿Cuántos mappers se
ejecutan? ¿Cuántos reducers se ejecutan? ¿Qué datos recibe cada función reduce?
¿Cuál es la salida de cada job?

a) Job A
def map(k1, v1, context):
	context.write(1, v1)
def reduce(k2, v2, context):
	n = 0
	for v in v2:
		n = n + 1
	context.write(k2, n)
Invocaciones del map: 16 
Invocaciones del map: 4
mappers : 4
Reducers:

b) Job B
def map(k1, v1, context):
		context.write(1, v1)
def reduce(k2, v2, context):
	n = 0
	for v in v2:
		n = n + v
	context.write(k2, n)
c) Job C	
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
d) Job D
def map(k1, v1, context):
	for v in range(v1):
		context.write(k1, v1)
def reduce(k2, v2, context):
	n = 0
	for v in v2:
		n = n + 1
	context.write(k2, n)
e) Job E
def map(k1, v1, context):
	context.write(v1, k1)
def reduce(k2, v2, context):
	n = 0
	for v in v2:
		n = n + 1
	context.write(v, n)