Para la relación Purchase -> ItemService (composición):
	o) ¿Qué tipos de cascade configurarías? Justificar cada uno.
	p) ¿Usarias orphanRemoval? ¿Por qué?
	q) Describir qué ocurre a nivel de base de datos cuando se elimina un ItemService de la
	lista de una Purchase y Hibernate realiza el flush.
- cascadeType.ALL : Como es composición, el ítem no tiene sentido sin la compra. Necesitamos que se guarde, actualice y borre junto a ella.
- **orphanRemoval:** **Sí**. Si un usuario decide quitar un ítem ese registro debe desaparecer de la DB, no quedar flotando.
- **Nivel DB en el flush:** Hibernate detecta que el objeto ya no está en la colección y ejecuta un `DELETE FROM item_service WHERE id = ?`.