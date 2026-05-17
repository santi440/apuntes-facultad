ItemService referencia a Service (muchos-a-uno). Analizar el diagrama: ¿es navegable
esta relación desde Service hacia ItemService? Justificar si conviene hacerla bidireccional
o no.
la relación es **bidireccional**.
- **Consultas Agregadas:** Al ser bidireccional, podés acceder fácilmente desde un objeto `Service` a todos los ítems de compra donde fue incluido. Esto facilita, por ejemplo, calcular estadísticas de venta o popularidad del servicio directamente desde la entidad.
- **Integridad en cascada:** Permite gestionar mejor qué ocurre con los ítems si, por ejemplo, un servicio se da de baja o se modifica.
- 