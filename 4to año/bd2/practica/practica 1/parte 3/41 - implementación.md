Implementar un repositorio por cada entidad del modelo (PurchaseRepository,
RouteRepository, UserRepository, ServiceRepository, SupplierRepository,
ReviewRepository). Cada repositorio debe incluir al menos las operaciones básicas:
guardar, buscar por ID, listar todos y eliminar. Utilizar la Session de Hibernate en cada
implementación, tal como se ha explicado previamente utilizando el método
getCurrentSession() sobre la SessionFactory (genere una inyección de este objeto tal
como se muestra en el código de ejemplo proporcionado)