Resolver la administración de las impresoras de una oficina. Hay 3 impresoras, N usuarios y 1 director. Los usuarios y el director están continuamente trabajando y cada tanto envían documentos a imprimir. Cada impresora, cuando está libre, toma un documento y lo imprime, de acuerdo con el orden de llegada, pero siempre dando prioridad a los pedidos del director. Nota: los usuarios y el director no deben esperar a que se imprima el documento
```c
chan colaNormal(Documento);
chan colaPrioridad(Documento);
chan hayAlguien();
chan dameLaburo(int);
chan imprimi[N](Documento);

process Usuario[id: 0..N-1]::
	while(true)
		Documento d = trabajar();
		send colaNormal(d);
		send hayAlguien();
		
process Director::
	while(true)
		Documento d = trabajar();
		send colaPrioridad(d);
		send hayAlguien();
		
process Buffer
	int i;
	while true
		receive hayAlguien();
		receive dameLaburo(i);
		if !colaPrioridad.empty(){
			receive colaPrioridad(d);
		}else{
			if !colaNormal.empty(){
				receive colaNormal(d);
			}
		}
		send imprimi[i](d);
process Impresora[id: 0..2]::
	Documento d;
	while true
		send dameLaburo(id);
		receive imprimi[id](d);
		Imprimir(d); // nunca dice que se lo tengo que dar
```