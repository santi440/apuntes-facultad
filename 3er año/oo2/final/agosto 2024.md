![[Pasted image 20260211140024.png]]

![[Pasted image 20260211140036.png]]
1. controlLoop() se encarga de comenzar la ejecución del hilo, instancia el scheduler (FIFO,LIFO y las antes mencionadas) y se queda en un bucle infinito haciendo uso de la función updateStatus() que los clases que implentan ThearedApp deberian implementar. Si ocurre un error simplemente lo imprime en una pila. no es constante sino que tiene un tiempo de quedarse esperando en que detiene la ejecucion del hilo. 
2. Inicialize se encarga de crear en base a los metodos del padre un hilo vacio con datos por defecto porque no le pasa nada. Todo esto en base a lo especificado y puede variar de acuerdo a la implementación especifica del programador sobre ThearedApp (dame algo más de documentación HDP)
3. Queda como hotspot aquellos metodos o clases que me permitan extender y completar la implementacion del framework para el fin que se necesite. En este caso es fácilmente identificable dos metodos:
   - initializeApp()
   - updateStatus()
   Ademas podriamos llegar a querer utilizar otras apps que manejen hilos, por lo que podriamos extender el framework en base a la jerarquia dada por ThearedApp
4. La inversión de control, que sigue el principio de no nos llames nosotros te llamamos, la prodriamos encontrar en controlLoop() cuando utiliza updateStatus() y en Inicialize() que hace uso de inicializeApp(). Ambos metodos son utilizados por el framework en ThearedApp() pero quedan como responsabilidad de quien utilice dicho framework implementarlas con su propia logica en particular.

> [! CAUTION ] CRITICA DE DISEÑO
> Estaria mucho más prolijo si me pones los metodos marcados como abstractos en la clase padre. Cualquier editor de codigo te va a poner un error de que no sabe que es ese metodo 
5.  Por medio de alguna de las clases de aplicaciones (Hub y AirCondicioner) utilizamos el framework (clase ThreadApp) utilizando sus metodos y estructura básica
6. Es de casa blanca debido a que las instanciaciones que implementemos o que ya nos vengan implementadas de antemano, como es el caso de las dos aplicaciones mencionadas, modifican o extienden el códido fuente (loop de control + hook clases).
7. Se puede testear, seria ideal que tengamos el código de la otra clase ya listo para una prueba de confiabilidad plena pero si por algun motivo no tenemos esta implementación podemos testear por medio de test Doubles por ejemplo a traves de la tecnica FAKE TEST que nos permite un comportamiento base, simplificado y no final pero que sirve para verificar en principio lo que estamos haciendo en Air Condicioner
8. Entiendo que esta redactado para que lo explique independiente de todo lo anterior, caso contrario aprendan a escribir :)
Template method: En casos en donde existen dos o más clases que tienen comportamiento muy similar pero que difieren en una parte única y diferente entre ellos podemos optar por crear una plantilla o template que englobe esta parte compartida. Minimizamos el código duplicado haciendo menos engorroso cambiar algo de nuestro diseño en todos los lugares y delegando lo que es único a las subclases que asi lo requieran de la manera que quieran. Un ejemplo podria ser el planteado más arriba donde ThearedApp funciona como la plantilla de Hub y AirCondicioner (dos aplicaciones que se manejan en hilos y que tienen comportamiento en comun, lo mostrado en ambos metodos) y comportamiento especifico, los dos metodso que implementan

