a)Obtener la cantidad de doctores por cada paciente que tiene disponible en
su ciudad
```sql
select patient_id, count(*) from doctors_per_patients group by patient_id;
```

b)Obtener los nombres de los pacientes sin doctores en su ciudad
```sql
select patient_name from patient left join doctors_per_patients as dp on dp.patient_id = patient.patient_id where dp.patient_id is null;
```

c)Obtener los doctores que comparten ciudad con más de cinco pacientes
```sql
select doctor_name, count(*) as "cant" from doctor d inner join doctors_per_patients as dp on dp.doctor_id = d.doctor_id  group by dp.doctor_id having cant
 > 5;
```