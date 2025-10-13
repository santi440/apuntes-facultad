Dado el siguiente esquema:

PASAJERO (#pasajero, nombre, dni, puntaje)
PASAJERO_RESERVA (#pasajero, #reserva)
RESERVA (#reserva, #vuelo, fecha_reserva, monto, #asiento)
VUELO (#vuelo, aeropuerto_salida, aeropuerto_destino, fecha_vuelo)

Indicar si las siguientes consultas obtienen el resultado correcto (sin tener en
cuenta la optimización).

A) Obtener los pasajeros que tengan reservas sobre vuelos del próximo año,
listando #pasajero, #vuelo y #asiento.

VUELOS_PROX_AÑO <— σ fecha_vuelo >= 1/1/2026 AND fecha_vuelo <= 31/12/2026 VUELO
RES <— Π #pasajero,#vuelo,#asiento ( VUELOS_PROX_AÑO |X| RESERVA |X|
PASAJERO_RESERVA)

VERDADERO 


B) Obtener el listado de montos de reservas realizadas para vuelos efectuados el
pasado Agosto desde Buenos Aires a Córdoba.
VUELOS_BUE_CBA <— σ ciudad_salida=“Buenos Aires” AND ciudad_destino=“Córdoba” VUELO
RESERV_AGO <— ( σ fecha_reserva >= 1/8/2025 AND fecha_reserva <= 31/8/2025 RESERVA) |X| VUELOS_BUE_CBA
RES <— Π monto RESERV_AGO

FALSO. En la primera consulta ciudad_destino y ciudad_salida no exiten como atributos 

C) Obtener el/los pasajeros que solo hayan reservado vuelos cuyo aeropuerto de
salida sea el aeropuerto “Ministro Pistarini”. Listar el nombre y dni de los
pasajeros.
VUELOS_PISTARINI <- Π #vuelo (σ aeropuerto_salida=“Ministro Pistarini” VUELO )
RESERVA_PISTARINI <- Π #pasajero (VUELOS_PISTARINI |X| RESERVA)
PASAJEROS_PISTARINI <- Π nombre,dni (RESERVA_PISTARINI |X| PASAJERO)

FALSO -> Reserva_pistarini no se puede llevar a cabo, no tengo el #pasajero, podria quedarme con el #reserva, con ello hacer un inner join con Reserva_pasajero y ahi si continuar.
D) Obtener el/los id/s de los pasajeros que hayan realizado reservas por un
monto superior a $99000
RESERVAS_MAS_99000 <- Π #pasajero (σmonto < 99000 RESERVA )
FALSO-> No tenes el #pasajero en la tabla reserva, te podira haber quedado con el #reserva y hacer |X| con RESERVA_PASAJERO