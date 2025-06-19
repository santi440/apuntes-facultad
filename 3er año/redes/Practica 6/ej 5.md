¿Qué restricción existe sobre el tamaño de ventanas en el protocolo Selective Repeat?
**El tamaño de la ventana de envío (sender window size, WS​) y el tamaño de la ventana de recepción (receiver window size, WR​) deben ser, como máximo, la mitad del espacio de números de secuencia disponible ** para asegurar que los números de secuencia nunca se reciclen tan rápido como para causar  ambigüedad en el receptor, permitiendo que SR maneje correctamente la pérdida de segmentos y ACKs.

Es decir, si el espacio de números de secuencia es N (donde N=2k, siendo k el número de bits utilizados para los números de secuencia), entonces:

WS​≤N/2 
WR​≤N/2 
Y, en SR, generalmente se establece: WS​=WR​≤N/2.

Esta restricción es crucial para evitar una ambigüedad conocida como el **"problema del segmento duplicado"** o **"problema de solapamiento de ventanas"**