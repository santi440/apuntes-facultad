Route tiene relaciones muchos-a-muchos con DriverUser y TourGuideUser (subclases de
User). Analizar el impacto de la estrategia de herencia sobre la tabla join:
bb) Si la jerarquía es SINGLE_TABLE, ¿a que tabla apunta la FK en la tabla join?
apunta a la tabla users (la única que hay) pero a nivel db no puede identificar si es un driver o tourGuide sino que es la logica de Hibernate la que debe manejarlo 
cc) Si la jerarquía es JOINED, ¿cambia la tabla destino de esa FK?
sigue apuntando a la tabla padre users que es la que realmente tiene el id pero la integridad es más estricta y hibernate hace los joins necesarios para tratarlos y recuperar los datos con la tabla hija pertinente