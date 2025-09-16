Dado el siguiente esquema:
TIENDA (#aplicacion, nombre_aplicacion, descripcion, #categoria, #etiqueta, #desarrollador, nombre_apellido_desarrollador, #actualizacion,descripcion_cambios)
Donde:
● #aplicacion, #categoria, #etiqueta y #desarrollador son únicos en el sistema.
● Una aplicación tiene un nombre y una descripción, y puede actualizarse muchas
veces
● Para cada actualización de una aplicación se registra un texto con los cambios
realizados. El #actualización es secuencial, cada aplicación define los suyos y
puede repetirse entre distintas aplicaciones.
● Cada aplicación tiene una única categoría y muchas etiquetas. Las etiquetas
pueden ir cambiando con cada actualización de la aplicación (en cada
actualización puede haber un conjunto diferente de etiquetas). La categoría
nunca cambia, es decir que se mantiene igual sin importar las actualizaciones.
● Una aplicación es realizada por varios desarrolladores de los cuales se conoce
su nombre y apellido.
Seleccione las DFs válidas / mínimas: Para las que no se seleccionen, indicar el
motivo.
1) #aplicacion, #actualizacion -> nombre_aplicacion, descripcion. ==Si==
   Entiendo que al decir   "y puede actualizarse muchas veces" se refiere al que al actualizar tengo cambios  y creo otra tupla. si se refiere "actualizarse" como modificar la tupla no aplica porque no seria minima (#aplicacion -> nombre_aplicacion, descripcion)
2) #aplicacion, #actualizacion -> descripcion_cambios ==Si==
3) nombre_apellido_desarrollador -> #desarrollador === no tiene sentido el nombre se puede repetir
4) #desarrollador -> nombre_apellido_desarrollador ==Si==
5) #aplicación -> #categoria ==Si==
Encontró alguna dependencia funcional más, que no se menciona entre las opciones?
A priori no.