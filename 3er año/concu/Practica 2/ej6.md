Suponga que existe una BD que puede ser accedida por 6 usuarios como máximo al mismo tiempo. Además, los usuarios se clasifican como usuarios de prioridad alta y usuarios de prioridad baja. Por último, la BD tiene la siguiente restricción:
• no puede haber más de 4 usuarios con prioridad alta al mismo tiempo usando la BD.
• no puede haber más de 5 usuarios con prioridad baja al mismo tiempo usando la BD.
Indique si la solución presentada es la más adecuada. Justifique la respuesta.
![[quien pone el codigo en tablas > punto6.png]]
El codigo es incorrecto => No produciria deadlock pero si una demora innecesaria. Supongamos que todos los usuarios de baja y uno solo de baja estan utilizando la BD, si uno de baja la libera, uno de alta intenta tomarla y antes de hacer el P(alta) le sacan la cpu y cualquier otro proceso intenta acceder no va a poder. Seria mas apropiado primero chequear por los semaforos menores y luego por los mas generales. 
```C
Var
sem: semaphoro := 6;
alta: semaphoro := 4;
baja: semaphoro := 5

Process Usuario-Alta [I:1..L]::{
	P (alta);
	P (sem);
	//usa la BD
	V(sem);
	V(alta);
}
Process Usuario-Baja [I:1..K]::{
P (baja);
P (sem);
//usa la BD
V(sem);
V(baja);
}
```