Si contáramos con un cluster donde podemos configurar 100 nodos para la tarea de reduce ¿De qué manera se podrían usar esos 100 nodos en el ejemplo de los eventos POSITIVO, NEGATIVO y NEUTRO visto en la teoría?

Se podría hacer con un random. como cada uno de las iteraciones son independientes no hay manera de mantener un orden explicito, dependerá de como este hecho esta función pero nos permite un mínimo de división entre los cluster. Los mappers deberan entregar algo del estilo 
```python
context.write(randint(1,100), v1.upper())
```
siendo v1 POSITIVO, NEGATIVO y NEUTRO -> desconfio que este bien escrito, upper me da tranquilidad.