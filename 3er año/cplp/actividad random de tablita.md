```C
static int puntoB = 0
void funA{
	static int puntoA= 0;
	puntoA++;
}
void enmascarandoB{
	int puntoB = 1123;
}
void puntoC{
	int variableC = 0;
}
```



| identificador | r-value | l-value    | tiempo de vida | alcance    |
| ------------- | ------- | ---------- | -------------- | ---------- |
| puntoB        | 0       | estatica   | <1-11>         | 1-5, 9-11> |
| funA          |         |            | 1-11           | 3-5        |
| puntoA        | 0       | estatica   | <1-11>         | 4-5        |
| enmascarandoB |         |            | 1-11           | 7-8        |
| puntoB        | 1123    | automatica | 8              | 8          |
| puntoC        |         |            | 1-11           | 10-11      |
| variableC     | 0       | automatica | 10             | 10         |
