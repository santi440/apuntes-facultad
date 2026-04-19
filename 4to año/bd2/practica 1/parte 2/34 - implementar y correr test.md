Implementar el mapeo de la jerarquía User/DriverUser/TourGuideUser usando la
estrategia SINGLE_TABLE. Especificar @Inheritance, @DiscriminatorColumn y
@DiscriminatorValue para cada clase. Incluir todos los atributos.
v) Ejecutar los tests provistos y analizar el DDL generado: ¿cuántas tablas aparecen?
¿Qué columnas tiene la tabla? ¿Dónde están los atributos expedient y education?
Tocará suponer porque el test no lo puedo correr hasta que haga el repository.
1 tabla para user
las columnas de user + las propias de Drivers + las propias de tourGuide + el diferenciador
ambos atributos en la tabla user

w) ¿Qué ocurre con las columnas de subclase cuando se inserta un TourGuideUser? ¿Y
cuando se inserta un DriverUser?
Se cargan los datos de ese tipo en ambos casos (expedient o education respectivamente y el texto DRIVER o GUIDE para saber el tipo)

x) ¿Cuáles son las ventajas y desventajas de esta estrategia para este modelo
concreto?
**Ventajas:**
- **Performance:** Es la estrategia más rápida para realizar consultas polimórficas (ej. buscar a todos los usuarios del sistema) ya que no requiere realizar `JOINs` ni `UNIONs`.
- **Simplicidad:** El esquema de base de datos es sencillo de entender y mantener.
**Desventajas:**
- **Integridad de datos:** No se pueden aplicar restricciones `NOT NULL` a nivel de base de datos para los atributos específicos (`expedient` o `education`), ya que esto impediría la creación de registros de las otras subclases.
- **Desperdicio de espacio:** La tabla contiene muchas celdas con valores `NULL`, lo cual se acentúa si la jerarquía crece con muchas más subclases y atributos únicos.