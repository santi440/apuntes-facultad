Se quiere modelar el funcionamiento de un banco, al cual llegan clientes que deben realizar un pago y retirar un comprobante. Existe un único empleado en el banco, el cual atiende de acuerdo con el orden de llegada.
a. Implemente una solución donde los clientes llegan y se retiran sólo después de haber sido atendidos.
b. Implemente una solución donde los clientes se retiran si esperan más de 10 minutos para realizar el pago.
c. Implemente una solución donde los clientes se retiran si no son atendidos inmediatamente.
d. Implemente una solución donde los clientes esperan a lo sumo 10 minutos para ser atendidos. Si pasado ese lapso no fueron atendidos, entonces solicitan atención una vez
más y se retiran si no son atendidos inmediatamente.

a.
```c
procedure ej2a
	TASK empleado IS
		ENTRY atencion(pago: IN Texto; comprobante: OUT Texto);
	END empleado;
	TYPE TASK cliente;

	clientes = array (1..C) of cliente;

	TASK BODY cliente
		pago,comprobante: texto;
	begin
		pago = queQuiereHacer();
		empleado.atencion(pago,comprobante);
	END cliente;

	TASK BODY empleado
		loop
			accept atencion(pago:IN Texto;comprobante: OUT Texto) IS 
				comprobante = craftearComprobante(pago);
		end loop;
	END empleado;
BEGIN
	null
END ej2a;
```
b.
```c
procedure ej2a
	TASK empleado IS
		ENTRY atencion(pago: IN Texto; comprobante: OUT Texto);
	END empleado;
	TYPE TASK cliente;

	clientes = array (1..C) of cliente;

	TASK BODY cliente
		pago,comprobante: texto;
	begin
		pago = queQuiereHacer();
		select 
			empleado.atencion(pago,comprobante);
		or delay(600)
			null
		END select;
	END cliente;

	TASK BODY empleado
		loop
			accept atencion(pago:IN Texto;comprobante: OUT Texto) IS 
				comprobante = craftearComprobante(pago);
		end loop;
	END empleado;
BEGIN
	null
END ej2a;
```
c.
```c
procedure ej2a
	TASK empleado IS
		ENTRY atencion(pago: IN Texto; comprobante: OUT Texto);
	END empleado;
	TYPE TASK cliente;

	clientes = array (1..C) of cliente;

	TASK BODY cliente
		pago,comprobante: texto;
	begin
		pago = queQuiereHacer();
		select 
			empleado.atencion(pago,comprobante);
		or else
			null
		END select;
	END cliente;

	TASK BODY empleado
		loop
			accept atencion(pago:IN Texto;comprobante: OUT Texto) IS 
				comprobante = craftearComprobante(pago);
		end loop;
	END empleado;
BEGIN
	null
END ej2a;
```
d.
```c
procedure ej2a
	TASK empleado IS
		ENTRY atencion(pago: IN Texto; comprobante: OUT Texto);
	END empleado;
	TYPE TASK cliente;

	clientes = array (1..C) of cliente;

	TASK BODY cliente
		pago,comprobante: texto;
	begin
		pago = queQuiereHacer();
		select 
			empleado.atencion(pago,comprobante);
		or delay(600)
			select
				empleado.atencion(pago,comprobante);
			or else 
				null
			END select;
		END select;
	END cliente;

	TASK BODY empleado
		loop
			accept atencion(pago:IN Texto;comprobante: OUT Texto) IS 
				comprobante = craftearComprobante(pago);
		end loop;
	END empleado;
BEGIN
	null
END ej2a;
```
