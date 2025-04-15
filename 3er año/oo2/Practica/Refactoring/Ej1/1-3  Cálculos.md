Analice el código que se muestra a continuación. Indique qué code smells encuentra y cómo pueden corregirse.
```
public void imprimirValores() {
	int totalEdades = 0;
	double promedioEdades = 0;
	double totalSalarios = 0;
	for (Empleado empleado : personal) {
		totalEdades = totalEdades + empleado.getEdad();
		totalSalarios = totalSalarios + empleado.getSalario();
	}
	promedioEdades = totalEdades / personal.size();
	String message = String.format("El promedio de las edades es %s y el total de salarios es %s", promedioEdades, totalSalarios);
	System.out.println(message);
}
```

Malos olores : 
- Reinventar la rueda (existen los streams)
- Long method -> Podria dividirse en 3 metodos diferentes (Extract method)

```
private Double calcularPromedioEdades(){
	totalEdades = personal.stream().mapToInt(e -> e.getEdad).sum ;
	return totalEdades / personal.size();
}

private Double calcularTotalSalarios(){
	return personal.stream().mapToDouble(e -> e.getSalario).sum;
}
public void imprimirValores() {
	totalSalarios = calcularTotalSalarios(); 
	promedioEdades = calcularPromedioEdades();
	String message = String.format("El promedio de las edades es %s y el total de salarios es %s", promedioEdades, totalSalarios);
	System.out.println(message);
}
```