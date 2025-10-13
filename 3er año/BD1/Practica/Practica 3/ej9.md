TIPOMUEBLE ( id_tipomueble, descripción )
FABRICANTE ( id_fabricante,nombrefabricante, cuit )
TIPOMADERA ( id_tipomadera, nombremadera )
AMBIENTE ( id_ambiente, descripcionambiente )
MUEBLE ( id_mueble, id_tipomueble, id_fabricante, id_tipomadera, precio, dimensiones, descripcion )
MUEBLEAMBIENTE ( id_mueble, id_ambiente )

a. Obtener los nombres de los fabricantes que fabrican muebles en todos los tipos de
madera.

Πnombrefabricante,id_tipomadera FABRICANTE |X| MUEBLE % Πid_tipomadera TIPOMADERA

a. Obtener los nombres de los fabricantes que sólo fabrican muebles en Pino.

NO_PINO <- Πid_tipomadera(σnombreMadera <> 'Pino' (TipoMadera))
FABRICA <- Πid_fabricante (FABRICANTE)- Πid_fabricante (MUEBLE |X| NO_PINO)
Πid_fabricante(FABRICANTE|X| FABRICA)

b. Obtener los nombres de los fabricantes que fabrican muebles para todos los ambientes.

FA<-Πnombrefabricante,id_ambiente(FABRICANTE|X|MUEBLE|X|MUEBLEAMBIENTE)
FA % (Πid_ambiente(AMBIENTE))

c. Obtener los nombres de los fabricantes que sólo fabrican muebles para oficina.

Me quedo con los muebles que no son de oficina:
NO_OFICINA <- Πid_mueble(σ descripcionambiente<> 'Oficina' (AMBIENTE)|X|MUEBLEAMBIENTE)

Me quedó con los fabricantes que hicieron algo distinto de oficina
OTRO <- Πid_fabricante(MUEBLE|X| NO_OFICINA)

Resta entre todos los fabricantes (unión compatible)y los que hicieron algo que no fue oficina:

Πnombrefabricante(FABRICANTE|X| (Πid_fabricante(FABRICANTE) - OTRO))

d. Obtener los nombres de los fabricantes que sólo fabrican muebles para baño y cocina.

Muy similar al anterior solo que pasa a ser un OR en la seleccion

Me quedo con los muebles que no son de baño ni cocina:
NO_BC <- Πid_mueble(σ descripcionambiente<> 'baño' OR descripcionambiente<> 'cocina'(AMBIENTE)|X|MUEBLEAMBIENTE)

Me quedó con los fabricantes que hicieron algo distinto de baño o cocina
OTRO <- Πid_fabricante(MUEBLE|X| NO_BC)

Resta entre todos los fabricantes (unión compatible)y los que hicieron algo que no fue baño o cocina:

Πnombrefabricante(FABRICANTE|X| (Πid_fabricante(FABRICANTE) - OTRO))

>[!NOTE] No entiendo el enunciado :)
>Esto me deja el fabricante si hizo una, otra o ambas. Para las dos necesitaria hacer otro proceso -> Saco los que hicieron hicieron baño, los que hicieron Cocina  y saco la intersección entre ellos, la intersección entre esos y mi act 'OTRO' da los que hicieron SOLO baño y cocina .  
>Algo asi como:
>```python
>NO_BC <- Πid_mueble(σ descripcionambiente<> 'baño' OR descripcionambiente<> 'cocina'(AMBIENTE)|X|MUEBLEAMBIENTE)
>
>Me quedó con los fabricantes que hicieron algo distinto de baño o cocina
>OTRO <- Πid_fabricante(MUEBLE|X| NO_BC)
>
> Los que hicieron baño y los que hicieron cocina
> FBAÑO<- Πid_fabricante(σ descripcionambiente= 'baño' (AMBIENTE)|X|MUEBLEAMBIENTE|X|MUEBLE|X|FABRICANTE)
> FCOCINA<- Πid_fabricante(σ descripcionambiente= 'cocina' (AMBIENTE)|X|MUEBLEAMBIENTE|X|MUEBLE|X|FABRICANTE)
> 
> OTRO<- OTRO ∩ (FBAÑO ∩ FCOCINA)
> Resta entre todos los fabricantes (unión compatible)y los que hicieron algo que no fue baño o cocina:
> Πnombrefabricante(FABRICANTE|X| (Πid_fabricante(FABRICANTE) - OTRO))
> ```
> Resulta mucho más rebuscado, deberia cumplir pero no se si es a lo que apunta el ejercicio. La pregunta es estricto el ambos o solo con incluirlo alcanza


e. Obtener los nombres de los fabricantes que producen muebles de cedro y roble.

Me quedo con los muebles que son de cedro o roble:
CR <- Πid_mueble(σ nombremadera= 'cedro' OR nombremadera= 'roble'(TIPOMADERA)|X|MUEBLEAMBIENTE)

Me quedó con los fabricantes que hicieron esos muebles
FABRICO <- Πid_fabricante(MUEBLE|X| NO_CR)

Recupero el nombre del fabricante que hizo alguno
Πnombrefabricante(FABRICANTE|X|  FABRICO))

>[!NOTE] No entiendo el enunciado :)
>Esto me deja el fabricante si hizo una, otra o ambas. Para las dos necesitaria hacer otro proceso -> Saco los que hicieron cada tipo, saco la interseccion que me dara los que son comunes a ambos y eso es lo que devuelvo>>>>> **Me parece lo mas correcto**
>Algo asi como:
>```python
> Los que hicieron cedro y los que hicieron roble
> FCEDRO<- Πid_fabricante(σ **nombremadera**= 'cedro' (TIPOMADERA)|X|MUEBLEAMBIENTE|X|MUEBLE|X|FABRICANTE)
> FROBLE<- Πid_fabricante(σ nombremadera= 'roble' (TIPOMADERA)|X|MUEBLEAMBIENTE|X|MUEBLE|X|FABRICANTE)
> 
> AMBOS<- (FCEDRO ∩ FROBLE)
> Πnombrefabricante(FABRICANTE|X|AMBOS))
> ```
 

f. Obtener los nombres de los fabricantes que producen muebles de melamina o MDF
Llegado a este punto me parece que me respondí solo la pregunta de los dos anteriores, opción dos en ambos casos :)

Me quedo con los muebles que no son de cedro ni roble:
ALGUNO <- Πid_mueble(σ nombremadera<> 'cedro' OR nombremadera<> 'roble'(TIPOMADERA)|X|MUEBLEAMBIENTE)

FABRICO <- Πid_fabricante(MUEBLE|X| ALGUNO)

Πnombrefabricante(FABRICANTE|X| ALGUNO))
