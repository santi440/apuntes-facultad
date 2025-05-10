En un entrenamiento de fútbol hay 20 jugadores que forman 4 equipos (cada jugador conoce el equipo al cual pertenece llamando a la función DarEquipo()). Cuando un equipo está listo (han llegado los 5 jugadores que lo componen), debe enfrentarse a otro equipo que también esté listo (los dos primeros equipos en juntarse juegan en la cancha 1, y los otros dos equipos juegan en la cancha 2). Una vez que el equipo conoce la cancha en la que juega, sus jugadores
se dirigen a ella. Cuando los 10 jugadores del partido llegaron a la cancha comienza el partido, juegan durante 50 minutos, y al terminar todos los jugadores del partido se retiran (no es necesario que se esperen para salir).
```c
process jugador[1..20]{
	int miEquipo = DarEquipo()
	int miCancha
	equipo[miEquipo].DeterminarCancha(cancha)
	cancha[miCancha].llegada()
}
monitor equipo[id: 1..4]
	int llegaro = 0
	cond espera
	int vamos;
	procedure DeterminarCancha(int cancha:out){
		llegaron++;
		if(llegaron == 5){
			admin.listos(vamos)
			signalAll(espera)
		}else{
			wait(espera)
		}
		cancha = vamos;
	}
process partido[id: 1..2]{
	cancha[id].empezar()
	delay(3000) // 50 min
	cancha[id].terminar()
}
monitor cancha[1..2]{
	int cant = 0
	cond espera
	cond partido
	procedure llegada(){
		cant++
		if(cant == 10){signal(partido)}
		wait(espera)
	}
	procedure empezar(){
		if(cant<10){
			wait(partido)
		}
	}
	procedure terminar(){
		signalAll(espera)
	}
}
monitor admin
	int cant = 0
	procedure listos(int c:out){
		cant++
		if(cant <= 2){
			c=1
		}
		else{
			c=2
		}
	}
```