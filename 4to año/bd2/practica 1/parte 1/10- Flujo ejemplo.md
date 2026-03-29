Describir el ciclo de vida completo de una entidad persistente, tome como ejemplo un
objeto de la clase Purchase: desde que se instancia con new, pasando por su
persistencia, hasta que se elimina. Indicar explícitamente en qué estado se encuentra el
objeto en cada paso.

1. Instanciación: Estado **Transient** cuando hago el `Purchase p = new Purchase(...);`. Su id deberia de ser null
2. Persistencia: Estado **Managed** cuando hago un `session.save(p);` o `session.persist(p);`. Acá Hibernate le asigna un id y lo trackea con dirty checking hasta reflejar el cambio en la db con un commit
3. Fin de la transacción: Estado **Detached**. El objeto java existe pero como se termino la operación o se hizo un `session.close();`. No hay una session que lo este manejando, por ejemplo si quiero recuperar `items` (supongo que es Lazy)
4. Eliminación: Estado Removed. Hice un `session.remove(p);`, se lo marca para ser borrado hasta que se haga un commit, momento en el que hibernate haria el `DELETE FROM PURCHASES WHERE ID=105` (supongo id de p =105). Como la instancia del objeto existe aún y no tiene una referencia en la DB esta en estado Transient hasta que pase el garbage colector ==preguntar eso ult==