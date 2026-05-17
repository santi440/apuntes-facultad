Para la relación Purchase -> Review:
r) ¿Qué cascades configurarías?
s) Si se elimina una Purchase, ¿debería eliminarse también su Review? Justificar desde
el modelo de negocio.
- `PERSIST`, `MERGE` y `REMOVE`. (Sin DETACH o REFRESH para evitar sobrecarga innecesaria de la db al actualizar o modificar el padre, me dio paja y le mande ALL :) ).
- Justificación:** **Sí**, debería eliminarse. Una reseña (Review) está atada a una experiencia de compra específica. Si la compra desaparece del sistema, la reseña pierde su contexto y veracidad, por lo que lo más limpio es que se elimine en cascada.