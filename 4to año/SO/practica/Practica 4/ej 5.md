¿Qué implica la técnica binary translation? ¿Y trap-and-emulate?

# Trap and emulate
Es la técnica más clásica y directa. Se basa en que el procesador genere una excepción cuando algo no sale como debería.
- El sistema operativo invitado (Guest) intenta ejecutar una instrucción privilegiada (ej. modificar la tabla de páginas de memoria).
- Como el Guest no está en el **Ring 0**, el procesador físico detecta que no tiene permisos para hacer eso en modo usurio. Esto genera un **Trap** (una interrupción o trampa).
- El **Hipervisor** captura ese error, analiza qué quería hacer el Guest, lo ejecuta él mismo de forma segura en el hardware real y le devuelve el resultado al Guest.
> No todas las instrucciones de x86 generan un "trap" cuando se ejecutan sin privilegios; algunas simplemente fallan silenciosamente o devuelven datos basura, lo que rompería la VM. Para solucionar este bache, nació la siguiente técnica.

# binary translation
En lugar de esperar a que ocurra un error, el hipervisor analiza el código antes de que se ejecute.

- **El proceso:**
    1. El hipervisor escanea el código binario del sistema invitado mientras se está cargando.
    2. Identifica las instrucciones "problemáticas" (aquellas que no generan _trap_ o que son sensibles).
    3. **Reemplaza** esas instrucciones en tiempo real por un pequeño bloque de código seguro (llamado _fragmento de traducción_) que hace lo mismo pero bajo el control del hipervisor.
    4. El resto del código (instrucciones comunes de usuario como sumas o restas) se deja pasar directamente al hardware para que corra a máxima velocidad.
- **Dato :** Se dice que es una traducción **dinámica**, porque ocurre mientras el software corre.