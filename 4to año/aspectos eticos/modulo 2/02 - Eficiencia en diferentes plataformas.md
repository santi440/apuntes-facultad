Energia = Capacidad para realizar un trabajo. Unidad Joule(J)
Potencia = Cantidad de energía transferida por unidad de tiempo. watt (w)= 1W= 1 Joule/seg

Originalmente un Joule por definicion es el esfuerzo de mover un kg de peso  en un metro.
Notar precisamente Watt-hora (Wh): 1 Wh = 3.600 Joules

- La potencia puede ser medida en cualquier instante de tiempo, mientras que la energía debe ser medida durante un cierto periodo de tiempo.
- Energía = promedio de la potencia x tiempo de ejecución de la tarea
- P (W) = Tensión (V) x Intensidad (I)
![[Pasted image 20260423153600.png]]
Cada electrón transporta una carga elemental negativa.La cantidad de carga se puede definir en términos del número de electrones, pero como es una magnitud muy pequeña, se suele utilizar como unidad de carga eléctrica el culombio (C)

Tensión
Trabajo realizado por unidad de carga eléctrica. La unidad es el Volt (V): Joule/Culombio
- Joule (J) es la unidad de energía
- Culombio (C) es la unidad de carga eléctrica

Intensidad o corriente eléctica
es el flujo de carga eléctrica por unidad de tiempo que recorre un material. La corriente eléctrica se mide en amperios (A) = Culombio/segundo
Un valor negativo significa que la corriente fluye en la dirección opuesta a la de referencia.

![[Pasted image 20260423154154.png]]

Ej
![[Pasted image 20260423154226.png]]
3 culombos /seg = 3 A
lo que varia es la cantidad de Joules/ seg = Potencia
![[Pasted image 20260423154315.png]]

Corriente alterna
AC es la corriente eléctrica en la que la magnitud y dirección varían cíclicamente. La forma de onda de la corriente alterna más comúnmente utilizada es la de una onda sinusoidal:
![[Pasted image 20260423155928.png]]

Y despues
![[Pasted image 20260423155954.png]]
no entendi ;)

- La potencia instantánea es válida solo para un instante de tiempo
- ![[Pasted image 20260423160020.png]]
- Valores positivos: El circuito absorbe energía del generador
- Valores negativos: El circuito entrega energía al generador

Potencia media es el promedio de potencia entre dos instantes de
tiempo. Significa un balance entre lo que entra y lo que sale del circuito
![[Pasted image 20260423160059.png]]
Un area bajo la curva por eso la integral 
![[Pasted image 20260423160134.png]]
Tenemos uno del area marcada cada 20ms

# Medidor de potencia: caracteristicas
- Rango de potencia: adecuado para la máquina que se quiere medir. El rango y la resolucion del instrumento (si el rango es muy amplio o la presicion que necesitamos)
- Precisión.
- Soporte USB: para descarga de muestras y posterior procesamiento.
- Frecuencia de muestreo:
	- Ráfagas: utilizan memoria interna para almacenar temporalmente las muestras a alta frecuencia. ¿Cuántas muestras puede almacenar?
	- Cuanto muestreo en base al storage que tengo en mi instrumento
	- Sostenida.
![[Pasted image 20260423160655.png]]

Instrumentos para medir tension y corriente
![[Pasted image 20260423160955.png]]

Asi se conecctan
![[Pasted image 20260423161017.png]]
Salida:
![[Pasted image 20260423161038.png]]
Ayuda?
- Precalentar la máquina inmediatamente antes de los experimentos
- Registrar mediciones de potencia (posiblemente tensión e intensidad) y tiempos (de inicio y fin) de la ejecución de la aplicación objeto de estudio.
- Sincronizar eventos de la curva de potencia con eventos del sistema de cómputo (tiempos de inicio y fin de la ejecución):
	- Método de aplicación testigo: aumenta bruscamente la potencia
para facilitar su detección y permitir la sincronización con el tiempo de ejecución.
- Procesar resultados usando herramientas específicas para elevado número de muestras

# Modelos de potencia
Un modelo de potencia permite inferir la potencia para una dada computadora a partir de ciertas variables relacionadas al uso de los componentes de la arquitectura del sistema
(contadores de rendimiento = registros del procesador ).
Estos registros se pueden programar para contar el número de veces que un evento ocurre dentro del procesador durante la ejecución de una aplicación.
- Nos permite predecir en base a factores anteriores.
- ![[Pasted image 20260423161652.png]]
