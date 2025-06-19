Describa el saludo de tres vías de TCP. ¿UDP tiene esta característica?

El saludo de tres vías (o "three-way handshake") es el proceso fundamental que utiliza TCP para establecer una conexión fiable y orientada a la conexión entre dos hosts (un cliente y un servidor) antes de que comience cualquier intercambio de datos. Su propósito principal es sincronizar los números de secuencia y los números de acuse de recibo de ambos extremos, asegurando que ambas partes estén listas para comunicarse y que la comunicación sea ordenada.
Rol Cliente y Servidor es solo como referencia. Potencialmente podrian darse invertidos, es una coneccion entre 2 host, no importa su proposito.
**Paso 1: SYN(Syncronizar)** :
- **Acción del Cliente (Host A):** El cliente inicia la conexión enviando un segmento TCP con el flag **SYN (Synchronize)** activado.
    - Este segmento incluye un **Número de Secuencia Inicial (ISN - Initial Sequence Number)** aleatorio generado por el cliente (por ejemplo, `SYN = x`). Este `x` será el número de secuencia para el primer byte de datos que el cliente enviará.
- **Propósito:** El cliente le dice al servidor: "Quiero establecer una conexión contigo y mi primer número de secuencia será `x`."

**Paso 2: SYN-ACK (Sincronizar-Aceptar)**
- **Acción del Servidor (Host B):** Cuando el servidor recibe el segmento SYN del cliente, si está dispuesto a aceptar la conexión:
    - Envía un segmento TCP de respuesta con los flags **SYN** y **ACK (Acknowledgment)** activados.
    - El **ACK** confirma la recepción del SYN del cliente. El número de acuse de recibo (ACK Number) será el número de secuencia del cliente más uno (es decir, `ACK = x + 1`). Esto significa "He recibido tu SYN y estoy esperando el byte de datos `x + 1`".
    - El **SYN** que envía el servidor incluye su propio **Número de Secuencia Inicial (ISN)** aleatorio (por ejemplo, `SYN = y`). Este `y` será el número de secuencia para el primer byte de datos que el servidor enviará.
- **Propósito:** El servidor le dice al cliente: "Sí, acepto tu solicitud de conexión. He recibido tu SYN (y espero el byte `x+1`), y mi primer número de secuencia será `y`."

**Paso 3: ACK (Aceptar)**

- **Acción del Cliente (Host A):** Cuando el cliente recibe el segmento SYN-ACK del servidor:
    - Envía un segmento TCP final con el flag **ACK** activado.
    - El número de acuse de recibo (ACK Number) será el número de secuencia del servidor más uno (es decir, `ACK = y + 1`). Esto significa "He recibido tu SYN y estoy esperando el byte de datos `y + 1`".
    - Este segmento puede (y usualmente lo hace) incluir también los primeros datos de la aplicación si el cliente tiene datos para enviar inmediatamente.
- **Propósito:** El cliente le dice al servidor: "He recibido tu SYN y tu confirmación, y estoy listo para comunicarme. Espero el byte `y+1` de ti."

Una vez completados estos tres pasos, la conexión TCP está establecida y ambos hosts pueden comenzar a intercambiar datos de manera fiable, utilizando los números de secuencia y acuse de recibo sincronizados para gestionar el flujo de datos y garantizar la entrega ordenada


No UDP no tiene esta caracteristica.
- **UDP es un protocolo sin conexión.** No establece ni mantiene ningún tipo de "conexión" lógica o persistente entre el remitente y el receptor antes de enviar los datos.
- **UDP es un protocolo no confiable.** No le importa si los datos llegan, en qué orden o si hay errores. Simplemente "dispara y olvida".