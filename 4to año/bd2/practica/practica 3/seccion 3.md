29. Crear una nueva base de datos llamada "tours2". Guardar el archivo "generador1.js" adjunto a esta practica y ejecutarlo con: `load(<ruta del archivo 'generador1.js'>)`
```bash
docker cp generator1.js mongodb-container:/tmp/generator1.js

db.createCollection("tours2")
load("/tmp/generator1.js")
```
30. Obtener una muestra de 5 rutas aleatorias de la coleccion.
```bash
db.route.aggregate([
   { $sample: { size: 5 } }
])
```
31. Extender la consulta anterior para incluir en el resultado toda la informacion de cada una de las stops. Tener en cuenta que pueden ligarse por su codigo.
```bash
db.route.aggregate([
   { $sample: { size: 5 } },
    { $lookup: { from: "stop", localField: "stops", foreignField: "code", as : "stops_completas" }}])
```
32. Obtener la informacion de las rutas (incluyendo la de sus stops) que tengan un precio mayor o igual a $90.000.
```bash
db.route.aggregate([
  { 
    $match: { 
      price: { $gte: 90000 } 
    } 
  },
  {
    $lookup: {
      from: "stop",
      localField: "stops",
      foreignField: "code",
      as: "stops_completas"
    }
  }
])
```
33. Obtener la informacion de las rutas que tengan 5 stops o mas.
```bash
db.route.aggregate([
|   { 
|     $match: { 
|       $expr: { $gte: [{$size: "$stops"}, 5] } 
|     } 
|   },
|
|   {
|     $lookup: {
|       from: "stop",
|       localField: "stops",
|       foreignField: "code",
|       as: "stops_completas"
|     }
|   }
| ])
```
34. Obtener la informacion de las rutas que tengan incluido en su nombre el string "111".
```bash
db.route.aggregate([   { 
      $match: { 
        name: { $regex : /111/ } 
      } 
    },
 
    {
      $lookup: {
        from: "stop",
        localField: "stops",
        foreignField: "code",
        as: "stops_completas"
      }
    }
 ])
```
35. Obtener solo las stops de la ruta con nombre "Route100".
```bash
db.route.aggregate([
  { $match: { name: "Route100" } },
  {
    $lookup: {
      from: "stop",
      localField: "stops",
      foreignField: "code",
      as: "detalles_stops"
    }
  },
  
  // Mostramos únicamente esos detalles completos
  {
    $project: {
      _id: 0,
      detalles_stops: 1
    }
  }
])
```
yo hice eso pero no hay route 100 (deje solo la primera con el match y trajo vacio)

36. Obtener la informacion del stop que mas apariciones tiene en rutas.
```bash

db.route.aggregate([
  // 1. Desarmamos el arreglo 'stops'. Si una ruta tenía 3 stops, ahora se duplica en 3 documentos individuales.
  { $unwind: "$stops" },

  // 2. Agrupamos por cada código de stop y contamos cuántas veces aparece
  {
    $group: {
      _id: "$stops",          // Agrupamos por el código del stop
      totalApariciones: { $sum: 1 } // Sumamos 1 por cada vez que lo encontramos
    }
  },

  // 3. Ordenamos el resultado de mayor a menor según las apariciones
  { $sort: { totalApariciones: -1 } },

  // 4. Nos quedamos únicamente con el primero de la lista (el que más tiene)
  { $limit: 1 },

  // 5. Buscamos la información completa de ese stop en la colección 'stop'
  {
    $lookup: {
      from: "stop",
      localField: "_id",     // Recuerda que el código quedó guardado en el campo '_id' tras el $group
      foreignField: "code",
      as: "info_stop"
    }
  },

  // 6. Opcional: Desarmamos el arreglo que genera el lookup para que quede como un objeto limpio
  { $unwind: "$info_stop" }
])
```
37. Obtener las rutas con precio inferior a $15.000. Agregar a cada una una nueva propiedad que especifique la cantidad de stops que posee. Crear una nueva coleccion llamada "rutas_economicas" y almacenar estos elementos.
```bash
db.route.aggregate([
  // 1. Filtramos las rutas con precio inferior a $15.000
  { 
    $match: { 
      price: { $lt: 15000 } 
    } 
  },

  // 2. Agregamos la nueva propiedad con la cantidad de stops
  { 
    $addFields: { 
      cantidad_stops: { $size: "$stops" } 
    } 
  },

  // 3. Guardamos los resultados creando la nueva colección
  { 
    $out: "rutas_economicas" 
  }
])
```

38. Por cada stop existente en la coleccion, calcular el precio promedio de las rutas que la incluyen.
```bash
db.route.aggregate([
  // 1. Desarmamos el arreglo de stops para analizar cada par de (stop, precio_ruta)
  { $unwind: "$stops" },

  // 2. Agrupamos por cada código de stop y calculamos el promedio de los precios
  {
    $group: {
      _id: "$stops",                     // Agrupamos por el código del stop
      precio_promedio: { $avg: "$price" } // Calculamos el promedio de los precios de las rutas que lo incluyen
    }
  },

  // 3. Cruzamos el resultado con la colección 'stop' para tener el nombre y detalles de la parada
  {
    $lookup: {
      from: "stop",
      localField: "_id",                 // El código del stop quedó en el _id por el $group
      foreignField: "code",
      as: "detalle_stop"
    }
  },

  // 4. Desarmamos el arreglo del lookup para que la información quede limpia y ordenada
  { $unwind: "$detalle_stop" },

  // 5. Opcional: Reestructuramos el resultado final para que sea súper legible
  {
    $project: {
      _id: 0,
      codigo_stop: "$_id",
      nombre_stop: "$detalle_stop.name", // Ajusta si en tu colección se llama 'nombre'
      precio_promedio: 1
    }
  }
])
```