Para la relación Supplier -> Service:
t) ¿Qué cascades tienen sentido?
u) Si se elimina un Supplier, ¿qué debería ocurrir con sus Service? ¿Y con las Purchase
que los contienen a través de ItemService?
- **Cascades:** `PERSIST` y `MERGE`. Evitaría `REMOVE`.
- **u) Escenario de eliminación:**
    - Si eliminas un `Supplier`, sus `Service` **no** deberían borrarse automáticamente si otros proveedores podrían ofrecerlos o si hay un histórico.
    - **Peligro:** Si borras el servicio en cascada, las `Purchase` que contienen ese servicio a través de `ItemService` tendrían una violación de integridad referencial (FK constraint) y la operación fallaría, o peor, si hay más cascadas, borrarías registros contables de compras.