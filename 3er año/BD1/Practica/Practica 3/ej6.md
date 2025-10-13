Choferes
DUEÑO ( id_dueño, nombre, teléfono, dirección, dni )
CHOFER ( id_chofer, nombre, teléfono, dirección, fecha_licencia_desde,
fecha_licencia_hasta, dni )
AUTO ( patente, id_dueño, id_chofer, marca, modelo, año )
VIAJE ( patente, hora_desde, hora_hasta, origen, destino, tarifa, metraje )

a) Listar el dni, nombre y teléfono de todos los dueños que NO son choferes

Πdni,nombre,teléfono(DUEÑO)-Πdni,nombre,teléfono (CHOFER)

b) Listar la patente y el id_chofer de todos los autos a cuyos choferes les caduca la licencia el 01/01/2026
Vence <- Πid_chofer (σfecha_licencia_hasta = 01/01/2026 (CHOFER))
Πid_chofer,patente(VENCE |X| AUTO)