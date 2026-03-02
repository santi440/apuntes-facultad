¿En el dataset del ejercicio 1 de la práctica 1 indique para cada Job, si se vería
beneficiado por una función combiner? En caso afirmativo, ¿cuál es la implementación de dicha función? ¿Qué datos recibe cada reduce, al utilizar la función combiner?
a) JOB A
Casi, como esta hecho no pero modificandolo es probable que se pueda aprovechar para un conteo parcial a medida que se van escribiendo los datos. Es necesario reescribir el map y reduce pero llego al mismo result.
```python
def map(k1, v1, context):
	context.write(v1, 1)
def combiner(k2, v2, context):
	n = 0
	for v in v2:
		n = n + v
	context.write(k2, n)
def reduce(k2, v2, context):
	n = 0
	for v in v2:
		n = n + v
	context.write(k2, n)
```
b) JOB B
si. Puede realizar la suma y el codigo es igual al del reducer 
``` python
def combiner(k2, v2, context):
	n = 0
	for v in v2:
		n = n + v
	context.write(k2, n)
```
c) JOB C
si. Como estoy calculando un maximo el combiner puede simplificar los datos sacando el maximo local.
```python
def combiner(k2, v2, context):
	max = -1
	for v in v2:
		if(v > max):
			max = v
	context.write(k2, max)
```
d) JOB D
creo que no se puede :(
e) JOB E
no se puede
