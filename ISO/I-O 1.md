Generar comando de entrada salida: Como se debe comportar el dispositivo, brinda una interfaz (API) para simplificar la diversidad de los dispositivos existentes. =! syscall de entrada salida.

Caching: Usar una memoria cache para evitar hacer entrada salida constantemente con el disco, no sirve para todos los dispositivos. 
Spooling: Cola de requerimientos de dispositivos para concurrencias de dispositivos. Mandan a cola de impresion y despues el demonio de impresion se encara de comunicarse con el dispositivo.

Formas de realizar E/O: 
	Bloqueante: Se suspende hasta que el requerimiento se complete. 
	No Bloqueante:Incluye complejidad en el flujo de N procesos de E/S 
