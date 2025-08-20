# Pthreads
## Thread
proceso “liviano” que tiene su propio contador de programa y su pila de ejecución, pero no controla el “contexto pesado” (por ejemplo, las tablas de página)

## Funciones reentrantes
Funciones en las cuales puede haber dos procesos ejecutandolas en paralelo que no hay problema porque usan variables internas y no compartidas. Idealmente deberiamos trabajar con estas

## Operaciones
Existe un primer punto de sincronización tipo barrera que donde el “main” debe esperar a que todos los threads terminen.

#include <pthread.h>   -> importar libreria

int pthread_create (pthread_t *thread_handle, const pthread_attr_t *attribute, void * (*thread_function)(void *), void *arg); -> crear hilo
// prestar atención al tercer parametro que es la función a hacer y el 4to que son los argumentos (segundo se ve más adelante y primero ni idea)

int pthread_exit (void *res); -> usado al final de las funciones para finalizar el thread y devolver un valor

int pthread_join (pthread_t thread, void **ptr); -> punto de sincronización. Espera a que el thread termine y le devuelve el valor

int pthread_cancel (pthread_t thread); -> solicita,**no termina**, la terminación del hilo.

## Mutex
Las secciones críticas se implementan en Pthreads utilizando mutex locks. Todas atómicas, solo quien lo bloquea puede desbloquearlo, al crear un mutex por defecto está desbloqueado.
- int pthread_mutex_lock ( pthread_mutex_t *mutex);
- int pthread_mutex_unlock (pthread_mutex_t *mutex);
- int pthread_mutex_init (pthread_mutex_t *mutex, const pthread_mutexattr_t *lock_attr);
- int pthread_mutex_trylock (pthread_mutex_t *mutex_lock); == evita tiempos ociosos, si se puede bloquear lo bloqueas sino devuelve un código de error.


### Tipos
Pthreads soporta tres tipos de Mutexs (Locks) se especifica por atributos:
- Un Mutex con el atributo Normal NO permite que un thread que lo tienen bloqueado vuelva a hacer un lock sobre él (deadlock), genera demora.
- Un Mutex con el atributo Recursive SI permite que un thread que lo tienen bloqueado vuelva a hacer un lock sobre él. Simplemente incrementa una cuenta de control.
- Un Mutex con el atributo ErrorCheck responde con un reporte de error al intento de un segundo bloqueo por el mismo thread.

## condición
para mejor eficiencia. operaciones
Dormirese, se presupone que para para esto fue necesario ingresar a un area critica con un mutex, para evitar errores del programador la libreria nos da dos instrucciones que se encargan de liberar la seccion critica, dormirse y volver a tomarla. La segunda tiene un tiempo maximo en el cual sino la despertaron devuelve un codigo de error
- int pthread_cond_wait ( pthread_cond_t *cond, pthread_mutex_t *mutex)
- int pthread_cond_timedwait ( pthread_cond_t *cond, pthread_mutex_t *mutex const struct timespec *abstime)

Despiertan a uno o a todos
- int pthread_cond_signal (pthread_cond_t *cond)
- int pthread_cond_broadcast (pthread_cond_t *cond)

Creacion y destruccion = obvio no se pueden usar antes de un init ni despues de un destroy
- int pthread_cond_init ( pthread_cond_t *cond, const pthread_condattr_t *attr)
- int pthread_cond_destroy (pthread_cond_t *cond)

