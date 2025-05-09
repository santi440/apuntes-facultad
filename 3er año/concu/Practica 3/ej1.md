Se dispone de un puente por el cual puede pasar un solo auto a la vez. Un auto pide permiso para pasar por el puente, cruza por el mismo y luego sigue su camino.
![[monitores 1.png]]
a. ¿El código funciona correctamente? Justifique su respuesta.
	El código funciona, suponiendo que el parametro que se pasa no esta mal, no utilizo el id para nada pero lo puedo pasar perfectamente. Cant podria ser un booleano, se utiliza con 0 = false y 1 para true. 
	
> [!NOTE] Adicional 
> Considero que no esta tan bueno que sea un while porque se despierta para volver a dormirse. Se podria cambiar por un if y con un cant de elementos esperando de modo que solo se marque en true libre si no hay nadie esperando de modo que respeto el orden y no despierto gente al pedo. (Sacado de la explicación) algo asi:
> ![[monitor prolijo con libre y cant esperando.png]]

b. ¿Se podría simplificar el programa? ¿Sin monitor? ¿Menos procedimientos? ¿Sin variable condition? En caso afirmativo,rescriba el código.
	Podria hacerse de multiples formas, con semaforos por ejemplo pero como no hace nada "Importante" podria mantenerse la exclusion mutua con el simple uso del monitor pero no permite respetar el orden de llegada. 
```c
Monitor Puente
	Procedure cruzarPuente ()
			usoPuente()
		end;
End Monitor;
Process Auto [a:1..M]
	Puente.cruzarPuente();
End Process;
```
c. ¿La solución original respeta el orden llegada de los vehículos? Si rescribió el código en el punto b), ¿esa solución respeta el orden de llegada?
	La solucion original si respeta el orden, la solucion punto b) no respeta el orden de llegada todos compiten con la misma prioridad por el uso del monitor y mientras uno use el puente los demas no lo pueden usar.