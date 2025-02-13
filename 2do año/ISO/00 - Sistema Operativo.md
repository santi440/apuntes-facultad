Es Software que necesita procesador y memoria (conjunto de instrucciones y estructuras de datos que abstraen los datos necesarios que permiten cumplir los objetivos de un SO), una capa intermedia entre los programas y el Hardware.
Esta capa se puede observar:
- De arriba hacia abajo: Los programas son clientes del SO, le piden servicios y desconocen el como se utiliza el hardware, se dice que el SO  "oculta" el HW y la Arquitectura.
- De abajo hacia arriba: Administrador de recursos, para uno o mas procesos en forma simultanea. Comparte la CPU y la memoria (Multiplexación en tiempo y espacio, respectivamente)
# Objetivos
1. Comodidad : hacer mas fácil el uso del HW
2. Eficiencia: Uso optimo en la administración de los recursos
3. Evolución: El diseño de un SO tiene que tener en cuenta que el HW evoluciona o que el propio SO evoluciona para ofrecer mejoras, sin interferir en las funciones existentes. 
# Componentes
1. Kernel (Núcleo) : Fragmento del SO que administra los recursos que debe:
	- Manejar la memoria
	- Manejar la CPU
	- Administrar los procesos ([[02 - Procesos I]])
	- Comunicación y concurrencia
	- Gestión de E/S
1. Shell
2. Aplicaciones de uso o Herramientas que vienen preinstaladas

# Extras
- Contabilidad: El SO además, recopila información o estadísticas de uso (cuanto tiempo estuvo en CPU, cuando tiempo estuvo esperando, memoria máxima que ocupo, cuanta E/S tuvo), monitorea el rendimiento, se anticipa a necesidades futuras y permite facturar tiempo de procesamiento
  
Los problemas se detallan en [[01 - Problemas a evitar por el SO]]
