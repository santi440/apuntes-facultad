Implemente una función combiner para el problema del WordCount.
```python
def fcombiner(key, values, context):
	c=0
	for v in values:
		c=c+1
	context.write(key, c)
```