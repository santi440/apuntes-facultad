79. Agregar a un hash llamado user:cronos los siguientes campos:
"razon social" "cronos s.a"
domicilio "47 236 La Plata"
"telefono" 2215556677

`HSET user:cronos "razon social" "cronos s.a" domicilio "47 236 La Plata" telefono 2215556677`
80. Agregar el campo mail con el valor info@cronos.com.ar al hash user:cronos.
    `HSET user:cronos mail  info@cronos.com.ar`
81. Obtener todos los campos y valores de user:cronos.
    `HGETALL user:cronos`
82. Obtener únicamente el valor del campo mail de user:cronos.
    `HGET user:cronos mail`
83. Eliminar el campo teléfono de user:cronos.
    `HDEL user:cronos telefono`
84. Obtener la cantidad de campos de user:cronos.
    `HLEN user:cronos`
85. Obtener las claves (nombres de campos) de user:cronos.
    `HKEYS user:cronos`
86. Determinar si existe el campo cuil en user:cronos.
    `HEXISTS user:cronos cuil`
87. Obtener todos los valores (sin los nombres de campos) de user:cronos.
    `HVALS user:cronos`
88. Obtener la longitud del valor del campo mail de user:cronos.
    `HSTRLEN user:cronos mail`