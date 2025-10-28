Crear una vista llamada ‘doctors_per_patients’ que muestre los id de los pacientes y los id de doctores de la ciudad donde vive el paciente.
```sql
create VIEW ‘doctors_per_patients’ as (
SELECT patient_id, doctor_id 
from `patient` 
inner join `doctor` on patient_city = doctor_city);
```

y para usarla
```sql
SELECT * FROM `‘doctors_per_patients’`;
```