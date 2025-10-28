Crear un stored procedure que sirva para agregar un appointment, junto el registro
de un doctor que lo atendió (medical_review) y un medicamento que se le recetó
(prescribed_medication), dentro de una sola transacción. El stored procedure debe
recibir los siguientes parámetros: patient_id, doctor_id, appointment_duration,
contact_phone, appointment_address, medication_name. El appointment_date será
la fecha actual. Los atributos restantes deben ser obtenidos de la tabla Patient (o
dejarse en NULL).
```sql
DELIMITER //
CREATE PROCEDURE insertMedicalAtencion(
p_patient_id INT,
p_doctor_id INT,
p_appointment_duration INT,
p_contact_phone varchar(255),
p_appointment_address varchar(255),
p_medication_name varchar(255)
)

DECLARE EXIT HANDLER FOR SQLEXCEPTION
		ROLLBACK;

BEGIN
	START TRANSACTION;
	INSERT INTO APPOINTMENT(patient_id, appointment_date, appointment_duration, contact_phone, observations, payment_card)
	VALUES (p_patient_id,NOW(),p_appointment_duration,p_contact_phone,NULL,NULL)
	
	INSERT INTO MEDICAL_REVIEW(patient_id, appointment_date, doctor_id) 
	VALUES (p_patient_id,NOW(),p_doctor_id)
	
	INSERT INTO PRESCRIBED_MEDICATION(patient_id, appointment_date, medication_name)
	VALUES(p_patient_id,NOW(),p_medication_name)
	
	COMMIT;
END//
DELIMITER ;
```


TABLAS PARA TENERLO A MANO:
PATIENT (patient_id , patient_name, patient_address, patient_city, primary_phone,
secondary_phone)
DOCTOR (doctor_id, doctor_name, doctor_address, doctor_city, doctor_speciality)
APPOINTMENT (patient_id, appointment_date, appointment_duration, contact_phone, observations, payment_card)
MEDICAL_REVIEW (patient_id, appointment_date, doctor_id)
PRESCRIBED_MEDICATION (patient_id, appointment_date, medication_name)