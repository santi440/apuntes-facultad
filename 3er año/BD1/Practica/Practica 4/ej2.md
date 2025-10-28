Hallar aquellos pacientes que para todas sus consultas médicas siempre hayan dejado su número de teléfono primario (nunca el teléfono secundario).
```SQL
SELECT p.patient_name 
from `patient` p 
where not exists 
(select 1
from `appointment` a 
where a.contact_phone = p.secondary_phone);
```
