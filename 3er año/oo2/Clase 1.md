NO SE TOMA ASISTENCIA. En practica son 3 temas que se apruban por separado.
- 7 de junio
- 28 de junio
- 12 de junio -> ult recu vs promo (6 o mas)
#### Regimen de Promo 
En primera y/o segunda fecha hay preguntas adicionales para acceder al regimen de promocion. Son preguntas teoricas opcionales, se apruban una unica vez.

## Métodologias ágiles
Contexto de la materia y del mundo actual.
- simplicidad y que funcione hoy, corregir, adaptando y transformando en base al feedback. Siempre en busca de la calidad y sustentabilidad. 
- se desarrolla en iteración
- valores/ principios/ practicas
- cada iteración extiende el codigo existente con nueva funcionalidad o corrigiendo bugs
- poca documentacion
- testing automatico e integracion continua.
Arquitecturas complejas: modulares, en capas con distintos onjetivos, arquitecturas orientadas a servicios (apis) propias o de terceros
el cambio es continuo, evoluciona y crece para no deteriorarse con el tiempo

### Refactoring
reconocer las fuerzas que llevan al deterioro de la arquitectura y aprender a reconocer oportunidades para mejorarla, con el objetivo de lograr codigo legible y extensible

### Patrones de diseño
Construir diseños (microarquitecturas), reusables y mas sensibles a al evolucion. 

> [!NOTE] 
> Hay que aprender a utilizarlos y cuando, todos tienen pros y contras
> 
### Frameworks
Instancia arquitecturas reusables para una familia de aplicaciones, reconociendo puntos de viabilidad y extension de estas arquitecturas. Los frameworks tambien evolucionan y se desarrollan en un dominio/contexto especifico.

### Calidad
 - Testing: Nos asegura que cada capa que cada modulo funcione bien en forma independiente a los otros. Test drive delopment (TDD).

----------------
## Refactoring
Trandormación de código que **preserva** el comportamiento y mejorar el diseño. Conceptualmente decimos, si hace lo mismo, es un refactoring, **NO** agrega funcionalidad.
Cada refactoring es atomico y podria realizarse en otra secuencia 

> [!Warning] Puede complicar deadlines
> Lo perfecto es enemigo de lo aceptable. Hacer refactoring con el tiempo contado puede ser contraproducente.

### Usos
- eliminar duplicacion
- simplificar logicas complejas
- clarificar el codigo
#### Escenarios
- Una vez que tengas el codigo que funciona y pasa los test.
- A medida que va desarrollando. 
	- Cuando encuentro codigo dificil de entender
	- Cuando tengo que hacer un cambio y necesito reorganizar primero

## Code smell
Posibles problemas en el codigo funete. Podria estar violando principios de diseño fundamentales . No son errores/bugs de por si pero pueden complicar el codigo o costo horas hombre.
Paradoja entre velocidad y prolijo
- velocity cantidad de tareas (funcionalidades)/periodo de tiempo
- deuda tecnica(technical debt): costo de rehacer una tarea que tiempo atras fue resuelta con una solucion rapida y desprolija(rapido y desprolijo)