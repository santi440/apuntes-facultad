# Transacciones
## 3.1 Aspectos avanzados de @Transactional
### 29. ¿Cuál es el atributo readOnly = true en @Transactional? ¿Qué optimizaciones activa? ¿En qué métodos del servicio conviene aplicarlo?
   El atributo `readOnly = true` le indica a Spring y al proveedor de persistencia (Hibernate) que la transacción **solo realizará operaciones de lectura**.
   Al activarlo, Hibernate habilita las siguientes optimizaciones críticas de rendimiento:
1. **Desactivación del _Dirty Checking_:** Hibernate no mantiene copias de los estados originales de las entidades en la caché de primer nivel (Session). Al finalizar la transacción, se saltea por completo el proceso de comparar objeto por objeto para ver si hubo cambios, ahorrando memoria y tiempo de CPU.
2. **Optimización del modo de descarga (_FlushMode.MANUAL_):** Se establece el modo de flush en `MANUAL`. Hibernate nunca intentará sincronizar de forma automática los objetos con la DB durante la consulta, evitando escrituras accidentales.
3. **Optimización del Driver JDBC:** Si el driver de la base de datos (ej. MySQL) soporta transacciones de solo lectura, se le pasa una pista (_hint_) para que optimice los bloqueos (_locks_) a nivel de tablas o filas, permitiendo lecturas concurrentes más veloces.
Debe aplicarse en **todos los métodos de consulta o búsqueda** (los que típicamente empiezan con `get`, `find`, o `list`). Una excelente práctica arquitectónica es anotar la **clase** del servicio con `@Transactional(readOnly = true)` a nivel global, y luego sobreescribir con un `@Transactional` común (que por defecto es `readOnly = false`) únicamente en los métodos de escritura (`create`, `update`, `delete`).
### 30.¿Cómo maneja Spring el rollback automático? ¿Sobre qué tipos de excepción hace rollback por defecto? ¿Cómo se configura para el rollback? Dar un ejemplo con el modelo.

El mecanismo de transacciones de Spring intercepta la ejecución del método mediante un Proxy de AOP (Programación Orientada a Aspectos). Si el método termina con éxito, el proxy ejecuta un `commit`. Si el método arroja una excepción, el proxy decide si debe deshacer los cambios (`rollback`) o confirmarlos.
Por defecto, Spring **solo hace rollback automático ante excepciones no chequeadas**, es decir, aquellas que heredan de **`RuntimeException`** (como `NullPointerException`, `IllegalArgumentException` o `DataAccessException`) y ante errores del sistema (**`Error`**).
Las excepciones chequeadas (aquellas que heredan directamente de `Exception`, como tu **`ToursException`**) **NO provocan un rollback automático** por defecto, **se guardarán igualmente (commit)** si no lo configurás explícitamente.
Se configura con el atributo `rollbackFor`
```java
@Override 
@Transactional(rollbackFor = ToursException.class) // <-- FORZA EL ROLLBACK ANTE TU EXCEPCIÓN 
public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException { 
	// 1. Lógica de negocio (ej. restar saldo o verificar duplicado) 
	if (quantity <= 0) { 
		throw new ToursException("La cantidad debe ser mayor a cero"); 
	// Gracias al rollbackFor, si esto se ejecuta, CUALQUIER cambio previo en la DB se deshace. 
	} 
	ItemService item = new ItemService(service, quantity, purchase); 
	return itemRepository.save(item); 
}
```
## 3.2 Adaptación de la capa de servicio
La capa de servicio implementada en la Práctica 1 ya utiliza @Transactional. Sin embargo, al
migrar los repositorios a Spring Data JPA, las llamadas internas al servicio deben actualizarse para usar los nuevos repositorios en lugar de la Session de Hibernate.
31. Revisar y actualizar la capa de servicio de la Práctica 1 para que utilice los repositorios
Spring Data JPA. Reemplazar todas las llamadas directas a la Session (session.save,
session.get, session.createQuery, etc.) por las operaciones equivalentes de los
repositorios. La lógica de negocio y las anotaciones @Transactional ya existentes no
deben modificarse salvo que sea necesario por los cambios anteriores.