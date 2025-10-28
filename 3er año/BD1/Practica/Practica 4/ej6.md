Crear un Stored Procedure que realice los siguientes pasos dentro de una
transacción:
a. Realizar la siguiente consulta: cada pacient (identificado por id_patient),
calcule la cantidad de appointments que tiene registradas. Registrar la fecha
en la que se realiza esta carga y además del usuario con el se realiza.
b. Guardar el resultado de la consulta en un cursor.
c. Iterar el cursor e insertar los valores correspondientes en la tabla
APPOINTMENTS PER PATIENT. Tenga en cuenta que last_update es la
fecha en que se realiza esta carga, es decir la fecha actual, mientras que user es el usuario logueado actualmente, utilizar las correspondientes
funciones para esto.
```sql
DELIMITER //
CREATE procedure store()
BEGIN 
	DECLARE fin INT DEFAULT 0;
	DECLARE cursor_patient_id INT;
	DECLARE cursor_total_appointmnets INT;
	
	DECLARE cur CURSOR FOR
		select p.patient_id, count(a.appointment_date)
		from patient p
		left join appoiment a
		on a.patient_id = p.patiend_id
		group by p.patient_id
		
	DECLARE CONTINUE HANDLER FOR NOT FOUND
		SET fin = 1;
		
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
		ROLLBACK;
		
	START TRANSACTION;
	
	DELETE FROM appointments_per_patient;
	
	OPEN cur;
	loop_cursor : LOOP
		FETCH cur INTO cursor_patient_id, cursor_total_appointments;
		IF fin = 1 THEN
			LEAVE loop_cursor;
		END IF;
		INSERT INTO appointments_per_patient(id_patient,count_appointment,last_update,user)
		VALUES (cursor_patient_id, cursor_total_appointments, NOW(), CURRENT_USER());
	END LOOP;
	CLOSE cur;
	COMMIT;
END//
DELIMITER ;
```