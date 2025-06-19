Se requiere modelar un puente de un único sentido que soporta hasta 5 unidades de peso.
El peso de los vehículos depende del tipo: cada auto pesa 1 unidad, cada camioneta pesa 2 unidades y cada camión 3 unidades. Suponga que hay una cantidad innumerable de
vehículos (A autos, B camionetas y C camiones). Analice el problema y defina qué tareas, recursos y sincronizaciones serán necesarios/convenientes para resolver el problema.
a. Realice la solución suponiendo que no se tiene ningún orden ni prioridad entre los diferentes tipos de vehículos.
b. Modifique la solución de (a) para que tengan mayor prioridad los camiones que el resto de los vehículos.
```c
procedure ej1a is
	TASK puente IS
		ENTRY cruzarAuto();
		ENTRY cruzarCamioneta();
		ENTRY cruzarCamion();
		ENTRY salir(p: IN integer);
	TASK TYPE auto;
	TASK TYPE camioneta;
	TASK TYPE camion;

	vAuto = array[1..A] of auto;
	vCamioneta = array[1..B] of camioneta;
	vCamion = array[1..C] of camion;

	TASK BODY puente IS
		cargaAct: integer;
	BEGIN 
		cargaAct = 0;
		loop
			select 
				accept salir(p: IN integer) IS cargaAct -= p;
				or WHEN (cargaAct < 5-1) accept cruzarAuto() => cargaAct = cargaAct+1; END cruzarAuto;
				or WHEN (cargaAct < 5-2) accept cruzarCamioneta() => cargaAct = cargaAct+2; END cruzarCamioneta;
				or WHEN (cargaAct < 5-3) accept cruzarCamion() => cargaAct = cargaAct+3; END cruzarCamiones;
			end select;
		end loop;
	END puente;

	TASK BODY auto
	BEGIN
		puente.cruzarAuto();
		cruzarPuente();
		puente.salir(1);
	END auto;

	TASK BODY camioneta
		BEGIN
			puente.cruzarCamioneta();
			cruzarPuente();
			puente.salir(2);
		END camioneta;

	TASK BODY camion
	BEGIN
		puente.cruzarCamion();
		cruzarPuente();
		puente.salir(3);
	END camion;
END ejercicio1a
```

bloque de codigo choto no me toma ada :(((

b.
```c
procedure ej1a is
	TASK puente IS
		ENTRY cruzarAuto();
		ENTRY cruzarCamioneta();
		ENTRY cruzarCamion();
		ENTRY salir(p: IN integer);
	TASK TYPE auto;
	TASK TYPE camioneta;
	TASK TYPE camion;

	vAuto = array[1..A] of auto;
	vCamioneta = array[1..B] of camioneta;
	vCamion = array[1..C] of camion;

	TASK BODY puente IS
		cargaAct: integer;
	BEGIN 
		cargaAct = 0;
		loop
			select 
				accept salir(p: IN integer) IS cargaAct -= p;
				or WHEN (cargaAct < 5-1) && (cruzarCamion`count = 0) accept cruzarAuto() => cargaAct = cargaAct+1; END cruzarAuto;
				or WHEN (cargaAct < 5-2) && (cruzarCamion`count = 0) accept cruzarCamioneta() => cargaAct = cargaAct+2; END cruzarCamioneta;
				or WHEN (cargaAct < 5-3) accept cruzarCamion() => cargaAct = cargaAct+3; END cruzarCamiones;
			end select;
		end loop;
	END puente;

	TASK BODY auto
	BEGIN
		puente.cruzarAuto();
		cruzarPuente();
		puente.salir(1);
	END auto;

	TASK BODY camioneta
		BEGIN
			puente.cruzarCamioneta();
			cruzarPuente();
			puente.salir(2);
		END camioneta;

	TASK BODY camion
	BEGIN
		puente.cruzarCamion();
		cruzarPuente();
		puente.salir(3);
	END camion;
BEGIN
	null
END ejercicio1a
```