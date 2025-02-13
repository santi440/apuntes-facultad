# Archivo:
Entidad abrstracta con un nombre de espacio logico (porque es abrstacto) continuo y direccionable, provee y permite guardar datos a los programas y los programas mismos son informacion que estan guardados en archivos.
![[atributos archivos.png]]
ACL: Access control list, usado por windows, los permisos se heredan de los directorios. 
### Diseño 
Se debe poder Implementar archivo, Implementar directorios, Manejo del espacio en disco (como lugar en donde se guardan archivos), Manejo del espacio libre y la Eficiencia y mantenimiento (en tanto, los metadatos que se encuentran en el mismo medio de almacenamiento que los archivos y deben permitir una busqueda eficiente y no desperdiciar espacio, almacenar la mayor cantidad de informacion). Es importante que la mayor cantidad del espacio en disco este ocupado por informacion util y no por las estructuras auxiliares 

## Sistema de manejo de Archivos
Conjunto de unidades de software (requieren de CPU, el menor tiempo posible para no afectar a los procesos) que proveen los servicios necesarios para la utilización de archivos y permiten las operaciones basicas (Crear, eliminar, escribir, leer, etc). Permite la abstracción al programador, ocultando el bajo nivel.
Acceder a archivos implica una entrada/salida (con el disco) y el FileSystem (implementacion logica de estructuras + operaciones) encargado de manejar los archivos podria ubicarse por encima de driver, quien se comunica directamente con el dispositivo, y debajo de la capa independiente de dispositivo, quien se encarga de recibir la instruccion abstracta y delegar su realizacion ([[09- I-O 1]]). El subsistema no sabe como hacer un read sobre el archivo /var/log/ejemplo.log pero si sabe que filesystem es y puede derivarle el trabajo, filesystem traduce -> driver se comunica con dispositivo -> backtracking hasta que el usuario recibe la respuesta 
![[Capas con filesystem.png]]
Objetivos:
- Cumplir con las solicitudes y gestionar los datos 
- Minimizar/eliminar la perdida de información. Integridad de información
- Dar soporte a distintos dispositivos
- Compartir archivos, en entornos multiusarios, con un esquema permisos y acceso simultaneo. El owner o propietario tiene todos los derechos y puede dar derechos a otros usuarios
- Bindar un conjunto de interfaces de E/S para tratamiento de archivos
Tipos de archivos:
- Regulares: ficheros con texto plano o binarios (fotos, ejecutables,etc)
- Directorios: son archivos que mantienen una estructura en el filesystem. Su contenido son relaciones a otros archivos. Este tipo no es necesario pero se incorporan por una cuestion de orden y agrupacion logica, eficiencia, uso del mismo "nombre" (el fullpath siempre es distinto), forman una estructura de arbol (en linux arrancando en / y en windows \ )
#### Path
Todo proceso tiene un directorio de trabajo
- Absoluto: Incluye todo el camino desde /
- Relativo: Se calcula desde el working directory (directorio de trabajo)