Para la captura tcp-captura.pcap, responder las siguientes preguntas.
a. ¿Cuántos intentos de conexiones TCP hay?
Filtrar por:
```bash
tcp.flags.syn == 1 && tcp.flags.ack == 0
```
Devuelve:
![[Pasted image 20250619163839.png]]
Hay 6 intentos de conexion tcp
b. ¿Cuáles son la fuente y el destino (IP:port) para c/u?
En todos los casos es la `10.0.2.10` intentando conectarse a `10.0.4.10`.
c. ¿Cuántas conexiones TCP exitosas hay en la captura? ¿Cómo diferencia las
exitosas de las que no lo son? ¿Cuáles flags encuentra en cada una?
Similar al punto A filtras con 
```bash
tcp.flags.syn == 1 && tcp.flags.ack == 1
```
dando 4 conexiones establecidas. Del lado de quien responde sera con un flag SYN y ACK encendidos. Podria darse que se pierde el ACK del cliente pero es un caso muy excepcional y el servidor quedar en un estado de espera, no lo voy a chequear si pasa :)
d. Dada la primera conexión exitosa responder:
	i. ¿Quién inicia la conexión?
		Quien inicia la conexion es quien envia el flag de SYN al comienzo, corresponde a `10.0.2.10`
	ii. ¿Quién es el servidor y quién el cliente?
		Cliente -> `10.0.2.10`, lo llamamos cliente por ser quien inicia la conexion pero es un rol demasiado arbitrario.
		Servidor -> `10.0.4.10`
	iii. ¿En qué segmentos se ve el 3-way handshake?
		Los segmentos con `No.` 3,4 y 5 denotan el saludo de 3 vias encargado de manejar el comienzo de la conexión TCP
	iv. ¿Cuáles ISNs se intercambian?
		Desde `10.0.2.10` manda el Sequence Number: 2218428254 y desde el `10.0.4.10` envia el Sequence Number: 1292618479
	v. ¿Cuál MSS se negoció?
		Se negocio a MSS=1460 segun los dos primeros pasos de 3WH.
	vi. ¿Cuál de los dos hosts envía la mayor cantidad de datos (IP:port)?
		En stadistics -> Conversations -> TCP se puede ver el siguiente detalle
		![[Pasted image 20250619170012.png]]
		La primera linea corresponde a la que estamos analizando, A es cliente el cliente con direccion 10.0.2.10:46907 y B es servidor con direccion 10.0.4.10:5003. En la columna Bytes A->B se ve que el cliente mando 822k y en el sentido opuesto hay 27k, el cliente mando mas info.
e. Identificar primer segmento de datos (origen, destino, tiempo, número de fila y
número de secuencia TCP).
Transmission Control Protocol, Src Port: 46907, Dst Port: 5001, Seq: 2218428255, Ack: 1292618480, Len: 24
    Source Port: 46907
    Destination Port: 5001
    [Stream index: 0]
    [TCP Segment Len: 24]
    Sequence Number: 2218428255
    [Next Sequence Number: 2218428279]
    Acknowledgment Number: 1292618480
    1000 .... = Header Length: 32 bytes (8)
    Flags: 0x018 (PSH, ACK)
    Window: 913
    [Calculated window size: 14608]
    [Window size scaling factor: 16]
    Checksum: 0xbfd7 [unverified]
    [Checksum Status: Unverified]
    Urgent Pointer: 0
    Options: (12 bytes), No-Operation (NOP), No-Operation (NOP), Timestamps
    [SEQ/ACK analysis]
    [Timestamps]
    TCP payload (24 bytes)
	i. ¿Cuántos datos lleva?
		24 bytes
	ii. ¿Cuándo es confirmado (tiempo, número de fila y número de secuencia
	TCP)?
		En el mensaje siguiente, tiene el ACK anterior + 24
		Transmission Control Protocol, Src Port: 5001, Dst Port: 46907, Seq: 1, Ack: 25, Len: 0
	    Source Port: 5001
	    Destination Port: 46907
	    [Stream index: 0]
	    [TCP Segment Len: 0]
	    Sequence Number: 1    (relative sequence number)
	    Sequence Number (raw): 1292618480
	    [Next Sequence Number: 1    (relative sequence number)]
	    Acknowledgment Number: 25    (relative ack number)
	    Acknowledgment number (raw): 2218428279
	    1000 .... = Header Length: 32 bytes (8)
	    Flags: 0x010 (ACK)
	    Window: 905
	    [Calculated window size: 14480]
	    [Window size scaling factor: 16]
	    Checksum: 0xbbdc [unverified]
	    [Checksum Status: Unverified]
	    Urgent Pointer: 0
	    Options: (12 bytes), No-Operation (NOP), No-Operation (NOP), Timestamps
	    [SEQ/ACK analysis]
	    [Timestamps]
	iii. La confirmación, ¿qué cantidad de bytes confirma?
		Confirma 24, los enviados.
f. ¿Quién inicia el cierre de la conexión? ¿Qué flags se utilizan? ¿En cuáles
segmentos se ve (tiempo, número de fila y número de secuencia TCP)
El cliente comienza el cierre de conexión al mandar el Flag de FIN encendido, en concreto en el siguiente segmento:

`No.` 958
Transmission Control Protocol, Src Port: 46907, Dst Port: 5001, Seq: 786289, Ack: 1, Len: 168
Source Port: 46907
Destination Port: 5001
[Stream index: 0]
[TCP Segment Len: 168]
Sequence Number: 786289    (relative sequence number)
Sequence Number (raw): 2219214543
[Next Sequence Number: 786458    (relative sequence number)]
Acknowledgment Number: 1    (relative ack number)
Acknowledgment number (raw): 1292618480
1000 .... = Header Length: 32 bytes (8)
Flags: 0x019 (FIN, PSH, ACK)
Window: 913
[Calculated window size: 14608]
[Window size scaling factor: 16]
Checksum: 0x132d [unverified]
[Checksum Status: Unverified]
Urgent Pointer: 0
Options: (12 bytes), No-Operation (NOP), No-Operation (NOP), Timestamps
[SEQ/ACK analysis]
 [Timestamps]
TCP payload (168 bytes)
