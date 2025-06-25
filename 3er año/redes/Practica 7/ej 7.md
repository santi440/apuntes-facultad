Su organización cuenta con la dirección 128.50.10.0. Indique:
a. ¿Es una dirección de red o de host?

Dado el primer octeto de la dirección dada podemos infereir que se trata de una direccion de clase B, la mascara por defecto para esta clase es /16, tomando los dos primeros octetos. Para que sea una direccion de red todos los bits destinados a hots deberian ser 0, el tercer octeto se puede escribir como `00001010`. Corresponderia a una dirección de host, si no especifica que hay subredes aplicadas.

b. Clase a la que pertenece y máscara de clase.
Las clases B se caracterizan por tener  en su primer octeto un numero entre 128-191 y su mascara es /16
c. Cantidad de hosts posibles.
2^16 = 65536
d. Se necesitan crear, al menos, 513 subredes. Indique:
Se deberan tomar 10 bits del host para subredes, quedando los primeros 16 para red, 10 para subredes y 6 para host
i. Máscara necesaria.
Comenzamos con un /16 y estamos tomando 10 bits mas = /26
ii. Cantidad de redes asignables.
2¹⁰ = 1024 redes. Si se intentaba con 2⁹ no se podia alcanzar el cometido porque otorgaria 512 posibles resultados.
iii. Cantidad de hosts por subred.
2⁶ = 64 - 2 (BROADCAST + RED) host.
iv. Dirección de la subred 710.
710 = ==10110001==10
128.50.177.128
v. Dirección de broadcast de la subred 710
Todos los bits de host en 1. Recordar que el 2⁶ es de red y esta en 0.
128.50.177.191