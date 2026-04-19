Para las relaciones Route <-> DriverUser y Route <-> TourGuideUser (muchos-a-muchos):
l) ¿Qué anotaciones se usan?
@ManyToMany porque en ambos lados puedo tener muchos, si bien en el lado de los usuarios tengo un 1..* no tengo forma de atar esa restriccion ==me gustaria preguntar esto==
m) ¿Qué tabla adicional genera JPA? ¿Qué columnas tiene? Definirla explícitamente
usando @JoinTable.

JPA genera una **Tabla de Unión (Join Table)**. Esta tabla no tiene una entidad propia en Java, sino que vive solo en la base de datos para conectar las claves primarias de ambas entidades.
**Columnas que tiene:**
1. Una columna para la FK de la entidad dueña (ej: `route_id`).
2. Una columna para la FK de la entidad asociada (ej: `driver_id` o `guide_id`).

**Definición explícita en código (Ejemplo para `Route` <-> `DriverUser`):**
```Java
@ManyToMany
@JoinTable(
    name = "route_driver", // Nombre de la tabla intermedia
    joinColumns = @JoinColumn(name = "route_id"), // FK hacia Route
    inverseJoinColumns = @JoinColumn(name = "driver_id") // FK hacia DriverUser
)
private List<DriverUser> drivers;
```

n) ¿Pueden ambas relaciones compartir la misma tabla join? ¿Por qué?
Depende pero no deberia.
- Cada relación sea **independiente** (una ruta puede tener un conjunto de conductores y un conjunto de guías por separado). 
- Se mantenga la **integridad referencial** (cada FK apunta a donde corresponde).
- El **mapeo de JPA** sea limpio:
    - `@ManyToMany` de conductores -> `@JoinTable(name = "route_drivers", ...)`
    - `@ManyToMany` de guías -> `@JoinTable(name = "route_guides", ...)`