# Semáforo
instancia de un tipo de datos abstracto (o un objeto) con sólo 2 operaciones (métodos) atómicas: P y V.
Internamente el valor de un semáforo es un entero no negativo:
• V → Señala la ocurrencia de un evento (incrementa).
• P → Se usa para demorar un proceso hasta que ocurra un evento (decrementa)

Semáforo binario
P(b): <await (b > 0) b = b-1;>
V(b): <await (b < 1) b = b+1;>

Si la implementación de la demora por operaciones P se produce sobre una cola, las operaciones son fair

Usamos el semáforo general

```c
sem free= 1;
process SC[i=1 to n]{
    while (true){
         P(free);
         sección crítica;
         V(free);
         sección no crítica;
    }
}
```

### Semáforo de señalización 
Generalmente inicializado en 0. Un proceso señala el evento con V(s) -> llegue; otros procesos esperan la ocurrencia del evento ejecutando P(s).

### Semáforos binarios divididos
Los semáforos binarios b1, ...., bn forman un SBS en un programa si el siguiente es un invariante global:La suma de sus valores en todo momento es mayor o igual a 0 y menor o igual a 1

### Contadores de Recursos
cada semáforo cuenta el número de unidades libres de un recurso determinado. Esta forma de utilización es adecuada cuando los procesos compiten por recursos de múltiples unidades

## Passing de baton
Cuando un proceso está dentro de una SC mantiene el baton (testimonio, token) que significa permiso para ejecutar.
Cuando el proceso llega a un SIGNAL (sale de la SC), pasa el baton (control) a otro proceso. Si ningún proceso está esperando por el baton (es decir esperando entrar a la SC) el baton se libera para que lo tome el próximo proceso que trata de entrar.