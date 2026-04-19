¿Cuál es la diferencia entre cascade = REMOVE y orphanRemoval = true? ¿Pueden
usarse conjuntamente? Ejemplificar con el par Purchase -> ItemService
- **`CascadeType.REMOVE`:** Es una cascada "hacia abajo". Si borro la `Purchase`, se borran sus `ItemService`. Pero si solo quito un ítem de la lista en Java (`items.remove(0)`), el ítem **sigue existiendo** en la DB con la FK en null.
- **`orphanRemoval = true`:** Es más agresivo. Si quito un ítem de la lista, JPA entiende que ya no tiene razón de ser y lo **elimina físicamente** de la tabla.
- **Uso conjunto:** Sí, pueden usarse juntos (es lo común en composiciones). En `Purchase -> ItemService`, garantiza que el ítem muera si la compra muere O si el ítem es removido de la compra.

No se porque usarias algo sin el orphanRemoval en false
