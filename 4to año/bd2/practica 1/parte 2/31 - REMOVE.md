¿Por que CascadeType.REMOVE en una relación @ManyToMany (por ejemplo Route <->
DriverUser) suele ser peligroso? Describir un escenario donde su uso cause pérdida no
deseada de datos.
Route <-> DriverUser
- Si configuras `REMOVE` y decides eliminar una `Route` que ya no se hará más, Hibernate propagará el borrado y **eliminará al Conductor (User)** de la base de datos.
- **Resultado:** Perderías la cuenta de usuario del chofer, sus datos personales y su acceso al sistema, solo por querer dar de baja una ruta. En `@ManyToMany`, solo se debe borrar la relación en la tabla intermedia, nunca las entidades de los extremos.