Ni en pedo me bajo mongo en mi so.
```bash
docker run -d \
  --name mongodb-container \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=secret_password \
  -v mongo-data:/data/db \
  mongo:latest
```

9. Crear una nueva base de datos llamada "tours" y una coleccion llamada "recorridos"
```
➜  tp-bbdd2-tours docker exec -it mongodb-container /bin/bash 


root@444210d35988:/# mongosh "mongodb://admin:secret_password@localhost:27017/"
Current Mongosh Log ID:	6a1f6605d73c3d6cfd9df8a2
Connecting to:		mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+2.8.3
Using MongoDB:		undefined
Using Mongosh:		2.8.3

For mongosh info see: https://www.mongodb.com/docs/mongodb-shell/


To help improve our products, anonymous usage data is collected and sent to MongoDB periodically (https://www.mongodb.com/legal/privacy-policy).
You can opt-out by running the disableTelemetry() command.

test> use tours
switched to db tours

```

No es necesario crear la colección explícitamente; al insertar un documento, MongoDB la crea de forma automática si no existe. Igual el comando es db.createCollection("recorridos")

10. En la nueva coleccion, utilizando el comando correspondiente, insertar el siguiente
documento:
`{ "nombre": "City Tour", "precio": 200, "stops": ["Diagonal Norte", "Avenida de Mayo", "Plaza del Congreso"], "totalKm": 5 }`

```bash
tours> db.recorridos.insertOne({ 
|   "nombre": "City Tour", 
|   "precio": 200, 
|   "stops": ["Diagonal Norte", "Avenida de Mayo", "Plaza del Congreso"], 
|   "totalKm": 5 
| })
{
  acknowledged: true,
  insertedId: ObjectId('6a1f6704c9f365b3619df8a3')
}
tours>
```

11. Recuperar la informacion insertada usando db.recorridos.find() (puede agregarse .pretty() al final para ver los datos indentados). ¿Que diferencia se observa entre el documento insertado y el documento recuperado?
```bash
tours> db.recorridos.find().pretty()
[
	{
		_id: ObjectId('6a1f6704c9f365b3619df8a3'),
		nombre: 'City Tour',
		precio: 200,
		stops: [ 'Diagonal Norte', 'Avenida de Mayo', 'Plaza del Congreso' ],
		totalKm: 5
	}
]
```
Devolvio un array que dentro tiene el objeto, en particular llama la atencion el `_id` que se agrego automaticamente a los campos y que ahora los campos no son un "string". - **¿Qué es un ObjectId?** Es un identificador de 12 bytes altamente probable de ser único a nivel global, compuesto por una combinación del timestamp (fecha y hora exacta de creación), un identificador del proceso y un contador incremental.
11. Agregar a la coleccion, utilizando un solo comando, los documentos especificados en el
archivo "material_adicional_1.json" adjunto a esta practica.
```bash 
docker cp material_adicional_1.json mongodb container:/tmp/material_adicional_1.json

mongoimport --username admin --password secret_password --authenticationDatabase admin --db tours --collection recorridos --file /tmp/material_adicional_1.json --jsonArray```


> 2026-06-02T23:42:42.697+0000	connected to: mongodb://localhost/
2026-06-02T23:42:42.698+0000	14 document(s) imported successfully. 0 document(s) failed to import.
```

13.Actualizar el recorrido "Cultural Odyssey" para que su total de kilometros sea 12
```
tours> db.recorridos.updateOne({nombre:'Cultural Odyssey'}, {$set: {totalKm:12}});
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
```
14. Actualizar el listado de stops del recorrido "Delta Tour" para agregar "Tigre".
    se puede hacer con $push o con $addToSet 
``` bash
    db.recorridos.updateOne(
  { "nombre": "Delta Tour" },
  { $addToSet: { "stops": "Tigre" } }
)
```
15. Aumentar un 10% el precio de todos los recorridos.
```bash
db.recorridos.updateMany(
  {},
  { $mul: { "precio": 1.10 } }
)
```
16. Eliminar el recorrido con nombre "Temporal Route".
```bash
db.recorridos.deleteOne(
  { "nombre": "Temporal Route" }
)
```
17. Crear el array de etiquetas (tags) para la ruta "Urban Exploration" y agregar el elemento "Gastronomia" a dicho arreglo.
```bash
    db.recorridos.updateOne( { "nombre": "Urban Exploration" }, { $push: { "tags": "Gastronomia" } } )
``` 
18. Obtener la ruta con nombre "Museum Tour". 
```bash
db.recorridos.find({ "nombre": "Museum Tour" }).pretty()
```
19. Las rutas con precio superior a $60.000. 
```bash
db.recorridos.find(
  { "precio": { $gt: 60000 } }
).pretty()
```
20. Las rutas con precio superior a $50.000 y con un total de kilometros mayor a 10.
```bash
db.recorridos.find({
  "precio": { $gt: 50000 },
  "totalKm": { $gt: 10 }
}).pretty()
```
21. Las rutas que incluyan el stop "San Telmo".
```bash
db.recorridos.find(
  { "stops": "San Telmo" }
).pretty()
```
22. Las rutas que incluyan el stop "Recoleta" y no el stop "Plaza Italia".
```bash
db.recorridos.find({
  "stops": "Recoleta",
  "stops": { $ne: "Plaza Italia" }
}).pretty()
```
23. El nombre y el total de km (si es que posee) de las rutas que incluyan el stop "Delta" y tengan un precio menor a $50.000.
```bash
db.recorridos.find({"stops": "Delta", "precio": {$lt: 50000}}, {"nombre": 1, "totalKm": 1, "_id":0})
```
24. Las rutas que incluyen tanto "San Telmo" como "Recoleta" y "Avenida de Mayo" entre sus stops.
25. 