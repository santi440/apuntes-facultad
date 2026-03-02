Crear un Trigger de modo que al insertar un dato en la tabla Appointment, se
actualice la cantidad de appointments del paciente, la fecha de actualización y el
usuario responsable de la misma (actualiza la tabla APPOINTMENTS PER
PATIENT).
```sql
DELIMITER //
CREATE TRIGGER insertAppointmentPacient
AFTER INSERT ON Appointments FOR EACH ROW
BEGIN
	IF EXISTS (SELECT a.patient_id FROM appointments_per_patient a where a.patient_id=NEW.patient_id) THEN
	UPDATE appointments_per_patient
	SET count_appointments = count_appointments + 1,
		last_update = NOW(),
		user=CURRENT_USER()
	WHERE id_patient=NEW.patient_id;
	ELSE
		INSERT INTO appointments_per_patient(id_patient,count_appointments,last_update,user)
		VALUES(NEW.id_patient,1,NOW(),CURRENT_USER());
	END IF;
END//
DELIMITER ;
```