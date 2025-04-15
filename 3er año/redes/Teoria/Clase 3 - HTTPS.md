#capaDeAplicacion
Los datos viajan cifrado y asegura autenticidad (proviene de quien se espera, certificado de identidad), integridad (llegan como corresponde) y identidad

autenticidad:
Una entidad de autenficacion emite un certificado de autenticidad (CA) que asegura que y afirma como entidad que el sitio es seguro y los usuarios que confien en la entidad confian en los HTTPS con esa firma

Protocolo TLS = provee autencacion y cifrado esta en la capa de aplicacionen el principio de esta. Provee seguridad

# HTTP 2.0
Divide el recurso en partes en Frames para no congestionar la red 
streams: conexiones dentro de conexiones, son subconexiones dentro del servidor, cliente y servidor deben conocer el formato de como interpretarlo. A nivel de conexion se ven como una sola. 
Pasa de trabajar en TLS y en lugar de ser ASCII pasa a ser binario. La definicion de que version del protocolo se define en la negociacion. en el estandar especificado podria usarse sin ser HTTPS pero en la practica no aplicó.
