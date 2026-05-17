El patrón Repository puede pensarse como una colección de objetos en memoria que
además persiste su estado. Describir cómo se implementa este concepto usando
Hibernate: ¿qué responsabilidades concentra un repositorio? ¿Con qué objeto de
Hibernate interactúa internamente?
- **Responsabilidades:** Centralizar la lógica de selección de datos, encapsular las consultas (HQL/Criteria) y gestionar la persistencia del estado de los objetos.
- **Objeto Interno:** El Repositorio interactúa directamente con la **`Session`** de Hibernate. La `Session` es la que actúa como el "Contexto de Persistencia" o caché de primer nivel, permitiendo que el repositorio simule esa "colección en memoria".