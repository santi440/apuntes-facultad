# Tipos de notas: 
 sfsd
## asdfgh
### asfdg
#### asfdgf
##### sdfgh
- sdfg
1. sfdf
> esdfg

 
>[!quote] cita
> para mencionar algun autor

> [!NOTE]
> Useful information that users should know, even when skimming content.

> [!TIP]
> Helpful advice for doing things better or more easily.

> [!IMPORTANT]
> Key information users need to know to achieve their goal.

> [!WARNING]
> Urgent info that needs immediate user attention to avoid problems.

> [!CAUTION]
> Advises about risks or negative outcomes of certain actions.

# Tipos de codigos

```java
public static Object getObjeto(String str, Integer num, int a){}
```

```mermaid
classDiagram
    direction LR

    %% Clases
    class Client {
    }

    class Target {
        <<interface>>
        Request()
    }

    class Adapter {
        Request()
    }

    class Adaptee {
        SpecificRequest()
    }

    %% Relaciones
    Client --> Target
    
    %% Herencia (Target es padre de Adapter)
    Target <|-- Adapter
    
    %% Asociación (Adapter usa Adaptee)
    Adapter --> Adaptee : adaptee

    %% Nota
    %% Mermaid conecta esto al borde de la clase 'Adapter' automáticamente.
    note for Adapter "adaptee->SpecificRequest()"
```


```mermaid
graph TD
    A[Inicio] --> B{¿Hay café?}
    B -- Sí --> C[Trabajar felizmente]
    B -- No --> D[Ir a la cafetería]
    D --> B
    C --> E[Fin del día]
```

```mermaid
sequenceDiagram
    Usuario->>Servidor: Petición de Login
    Servidor-->>Base de Datos: Validar credenciales
    Base de Datos-->>Servidor: Usuario OK
    Servidor-->>Usuario: Token de acceso (JWT)
```

```mermaid
gantt
    title Plan de Lanzamiento
    dateFormat  YYYY-MM-DD
    section Diseño
    Mockups          :a1, 2026-01-01, 10d
    Revisión         :after a1, 5d
    section Desarrollo
    Frontend         :2026-01-15, 20d
    Backend          :2026-01-20, 15d
```

```diff 
def suma(a, b):
- resultado = a - b
+ resultado = a + b 
  return resultado
```

$$ f(x) = \int_{-\infty}^{\infty} \hat{f}(\xi) e^{2 \pi i \xi x} d\xi $$

<u>hola</u>
