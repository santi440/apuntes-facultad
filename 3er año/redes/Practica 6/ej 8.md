¿Qué es el RTT y cómo se calcula? Investigue la opción TCP timestamp y los campos TSval y TSecr

El **RTT (Round Trip Time)**, o **Tiempo de Ida y Vuelta**, es una métrica fundamental en redes que mide el tiempo que tarda un paquete en ir desde un punto de origen a un punto de destino y que la confirmación de recepción (acuse de recibo o ACK) de ese paquete regrese al origen.

En términos más simples, es el tiempo total que tarda una señal en viajar de ida y vuelta a través de una conexión de red.

TCP utiliza un algoritmo dinámico para estimar el RTT y, en base a este, ajustar el tiempo de espera para retransmisiones (RTO - Retransmission Timeout). Es crucial que el RTO sea apropiado: si es demasiado corto, se producen retransmisiones innecesarias; si es demasiado largo, la recuperación de paquetes perdidos se retrasa.
#### Opción TCP Timestamp (TSopt)

La opción TCP Timestamp (TSopt), definida en **RFC 1323**, es una extensión del encabezado TCP que permite mediciones de RTT más precisas y previene el problema del "Wrapped Sequence Number" (WSN) en conexiones de alta velocidad.

Los dos campos principales dentro de la opción TCP Timestamp son:

1. **TSval (Timestamp Value):** Es un valor de tiempo de 32 bits que el remitente del segmento actual inserta. Este valor representa el tiempo actual del reloj interno del remitente cuando el segmento fue enviado. No es necesario que sea la hora del día, solo un valor creciente que el remitente mantiene.
    
2. **TSecr (Timestamp Echo Reply):** Es un valor de tiempo de 32 bits que el remitente del segmento actual ecoa (copia) del último valor de `TSval` que recibió del otro lado de la conexión. Es esencialmente el `TSval` del segmento al que se está respondiendo.