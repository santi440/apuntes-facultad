cuando hablamos de capa de aplicacion hablamos de datos 
cuando hablamos de capa de transporte hablamos de la comunicacion logica entre procesos, se encarga de multiplexar y desmultiplexar fuente agrega cabeceras y destino traduce y reensambla el mesnaje. 
TCP = Transmision Control Protocol. Mayor overhead. Da unos segundos mas de retardo pero asegura que se transfiere la pagina completa.
UDP = User datagram protocol. NO confiable. Brinda mayor velocidad donde la perdida de algunos bit no afecta el correcto funcionamiento de la aplicación.
La cabecera agregada por TCP es mayor porque brinnda mayor informacion y metadatos 
Capa de transporte brinda servicio a capa de aplicación y usa servicios
de capa de internet o RED

![[servicios por protocolo.png]]
El acceso a los servicios de transporte se hace mediante API:Network socket. La aplicacion elige que protocolo utilizar

FDX es full duplex el mismo canal puede transmitir datos en ambos sentidos
SInc y ACK = Establecen la conexion si estan en 1, Saludo de tres vieas, se solicita con un sinc y el servidor devuelve sinc y ack