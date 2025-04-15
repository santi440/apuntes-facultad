Capa de sesion: Si hay una conexion establecida entre dos computadoras hace un chequeo para ver si se cayo o retomar desde algun check point
## Capa de presentacion 
Permite que dos dispositivos diferentes se comuniquen, lleva los datos a un protocolo estandar y cada dispositivo lo traduce a su forma particular. Agrega cifrado, encriptacion, comprension y descompresion. 

## Capa de aplicacion
Define como debe ser el intercambio de mensajes y la semantica propia de estos. No hay un estandar establecido de como debe ser 

## Modelos 
Modelo Mainframe (dumb client).
Modelo Cliente/Servidor: El cliente es efimero es quien realiza las peticiones, el servidor esta encargado de brindarle el servicio 
Modelo Peer to Peer (P2P): Todos hacen la funcion de cliente y servidor todo el tiempo 
Modelo Híbrido: Hay clientes/servidor y Peer to peer.


Socket : direccion ip + puerto, ip identifica la maquina, puerto el SO direcciona a un PID. Esos 4 valores (socket maquina1 y maquina2) y su protocolo de capa de transporte son unicos en todo internet. 

# HTTP
El nucleo no cambia(manejo de erorres y logica de comunicacion no cambia) . Nos comunicamos con el servidor y le pedimos que trabaje sobre un objeto (aplicacion/ pagina web/ lo que sea), ese servidor esta identificado por una URL
sin estados: el servidor no tiene memoria de que hizo, completa una solicitud y pasa a la siguiente, si se lo pido 3 veces realiza las 3 veces. 

0-1023 son puertos privilegiados. 

Estructura
Linea de estada o linea de estado 
Headers: Palabras dos puntos y un contenido. 
Linea en blanco 
body 

![[Ida y vuelta cliente servidor.png]]

1.1 = persistente por default. se abre una conexion y se puede solicitar todos los recursos y luego se puede cierra la sesion. En el ejemplo de la diapo 1.0 es no persistente por lo que el servidor cierra la conexion, sabe que no le va a pedir mas nada. Desde la 1.1 Connection es obligatoria. Se mantine si no se especifica supone que es persistente y cuando el cliente no incluya la header se cierra.  