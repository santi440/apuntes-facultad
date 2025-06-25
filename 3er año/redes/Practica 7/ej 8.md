Si usted estuviese a cargo de la administración del bloque IP 195.200.45.0/24
a. ¿Qué máscara utilizaría si necesita definir al menos 9 subredes?
b. Indique la dirección de subred de las primeras 9 subredes.
c. Seleccione una e indique dirección de broadcast y rango de direcciones asignables en esa subred.

La IP es de tipo C, con mascara /24, los 24 primeros bit o tres primeros octetos no se pueden tocar. Para definir 9 subredes necesitaria 4 bits, 2⁴ = 16-2 =14 hosts. 
a. /28
b.
0001|0000
- ==195.200.45.16/28==
- 195.200.45.32/28
- 195.200.45.48/28
- 195.200.45.64/28
- 195.200.45.80/28
- 195.200.45.96/28
- 195.200.45.112/28
- 195.200.45.128/28
- 195.200.45.144/28
- 195.200.45.160/28
c. 195.200.45.16/28
Broadcast = 195.200.45.31/28
Red = 195.200.45.16/28
Cantidad de host = 2⁴ -2 = 14 host.