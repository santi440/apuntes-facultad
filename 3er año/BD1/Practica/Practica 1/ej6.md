Una compañía petrolera debe monitorear parámetros ambientales sobre los pozos en los que opera. Cada pozo se encuentra en una posición geográfica (latitud y longitud), tiene un nombre y una fecha de puesta en producción. Sobre cada pozo se realizan monitoreos periódicos con la intención de registrar distintas variables de interés y de los cuales se debe guardar la fecha del monitoreo y el método aplicado en el mismo. En cada monitoreo se miden diferentes parámetros (suelen repetirse entre mediciones), de los cuales se conoce un nombre y un valor de referencia. Además, en cada monitoreo, para cada parámetro específico, se obtiene un resultado, del cual se guarda el valor obtenido y el instrumento que se utilizó. Los instrumentos que se utilizan en los monitoreos pueden ser analógicos o digitales. De los analógicos se tiene la última fecha de calibración, y de los digitales se conoce la marca y modelo. De todos los instrumentos se conoce el número de serie

![[Pasted image 20250831162940.png]]
pozo = (**# pozo**, nombre, fecha)
ubicación = (**# ubicacion**, latitud, longitud)
se_encuentra = (**# pozo, # ubicación**)
monitoreo = (**# monitoreo**, fecha, metodo)
parametro = (**# parametro**, nombre, valor_ref)
se_miden = (**#monitoreo,#parametro**)
se_realiza = (**#monitoreo**, # pozo)
resultado = (**#resultado**,valor,metodo)
se_obtiene = (**#resultado,#monitoreo,#parametro**)
instrumento= (**#instrumento**,serie)
digital = (**#instrumento**, marca,modelo)
analogico = (**#instrumento**, fecha_calibracion)
hecho_con = (**#resultado**,#instrumento)
