a) ¿Qué es un hypervisor?
Un **hipervisor** (también conocido como Monitor de Máquina Virtual o VMM) es el software o firmware que hace posible la virtualización. Su función principal es crear y ejecutar máquinas virtuales (VM), actuando como una capa intermedia entre el hardware físico y los sistemas operativos invitados.
Tipos de hipervisor:
- Tipo 1: Bare Metal (Nativo): Se instala directamente sobre el hardware físico de la máquina, sin necesidad de un sistema operativo previo.
- Tipo 2: Hosted:  Se instala como una aplicación más dentro de un sistema operativo convencional

b) ¿Qué beneficios traen los hypervisors? ¿Cómo se clasifican?
## Beneficios Principales
- **Eficiencia de Costos:** Antes se necesitaba un servidor físico para cada servicio (correo, base de datos, web). Hoy podés tener todo en un solo equipo potente, ahorrando en hardware, electricidad y refrigeración.
- **Aislamiento:** Es clave para la seguridad. Si la VM donde estás probando un script se rompe o es atacada, el hipervisor garantiza que el resto de las máquinas y el hardware real queden intactos.
- **Snapshots (Instantáneas):** Es como un "punto de guardado" de un videojuego. Antes de probar una configuración arriesgada en una red, sacás un snapshot. Si algo falla, volvés al estado anterior en segundos.
- **Escalabilidad Dinámica:** Podés asignar más memoria o núcleos de CPU a una VM según la demanda sin tener que desarmar el servidor.
- **Portabilidad:** Como las máquinas virtuales son básicamente archivos (ej. `.vmdk` o `.vdi`), podés moverlas entre diferentes servidores físicos con facilidad.