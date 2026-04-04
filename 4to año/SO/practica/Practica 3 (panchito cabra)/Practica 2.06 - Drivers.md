1. Modifique el archivo memory.c para que tenga el siguiente código:
https://gitlab.com/unlp-so/codigo-para-practicas/-/blob/main/practica2/crear_driver/1_memory.c
```c
#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h> /* printk() */
#include <linux/slab.h> /* kmalloc() */
#include <linux/fs.h> /* everything... */
#include <linux/errno.h> /* error codes */
#include <linux/types.h> /* size_t */
#include <linux/proc_fs.h>
#include <linux/fcntl.h> /* O_ACCMODE */
#include <linux/uaccess.h> /* copy_from/to_user */

MODULE_LICENSE("Dual BSD/GPL");

int memory_open(struct inode *inode, struct file *filp);
int memory_release(struct inode *inode, struct file *filp);
ssize_t memory_read(struct file *filp, char *buf, size_t count, loff_t *
        f_pos);
ssize_t memory_write(struct file *filp, const char *buf, size_t count,
        loff_t *f_pos);
void memory_exit(void);
int memory_init(void);

/* Structure that declares the usual file */
/* access functions */
struct file_operations memory_fops = {
read: memory_read,
      write: memory_write,
      open: memory_open,
      release: memory_release
};

/* Declaration of the init and exit functions */
module_init(memory_init);
module_exit(memory_exit);

/* Global variables of the driver */
/* Major number */
int memory_major = 60;
/* Buffer to store data */
char *memory_buffer;

int memory_init(void) {
    int result;
    /* Registering device */
    result = register_chrdev(memory_major, "memory", &memory_fops);
    if (result < 0) {
        printk("<1>memory: cannot obtain major number %d\n", memory_major);
        return result;
    }
    /* Allocating memory for the buffer */
    memory_buffer = kmalloc(1, GFP_KERNEL);
    if (!memory_buffer) {
        result = -ENOMEM;
        goto fail;
    }
    memset(memory_buffer, 0, 1);
    printk("<1>Inserting memory module\n");
    return 0;
fail:
    memory_exit();
    return result;
}

void memory_exit(void) {
    /* Freeing the major number */
    unregister_chrdev(memory_major, "memory");

    /* Freeing buffer memory */
    if (memory_buffer) {
        kfree(memory_buffer);
    }
    printk("<1>Removing memory module\n");
}

int memory_open(struct inode *inode, struct file *filp) {
    /* Success */
    return 0;
}

int memory_release(struct inode *inode, struct file *filp) {
    /* Success */
    return 0;
}

ssize_t memory_read(struct file *filp, char *buf,
        size_t count, loff_t *f_pos) {
    printk("memory_read()\n");
    /* Transfering data to user space */
    if (copy_to_user(buf,memory_buffer,1)) {
        // return 0 if copy_to_user fails
        return 0;
    }
    /* Changing reading position as best suits */
    if (*f_pos == 0) {
        *f_pos+=1;
        return 1;
    } else {
        return 0;
    }
}

ssize_t memory_write( struct file *filp, const char *buf,
        size_t count, loff_t *f_pos) {
    const char *tmp;
    tmp=buf+count-1;
    printk("memory_write()\n");
    if (copy_from_user(memory_buffer,tmp,1)) {
        // return 0 if copy_from_user fails
        return 0;
    }
    return 1;
}

```
2. Responda lo siguiente
	a. ¿Para qué sirve la estructura ssize_t y memory_fops? ¿Y las funciones 	register_chrdev y unregister_chrdev?
		- ssize_t : un **tipo de dato** (signed size_t). Se usa para funciones que devuelven un tamaño en bytes pero que también deben ser capaces de devolver un
		- memory_fops: **`struct file_operations`)**: Es la "tabla de saltos". Contiene punteros a las funciones que vos escribiste (`memory_read`, `memory_write`, etc.). Le dice al kernel: "Cuando alguien quiera leer este dispositivo, usá mi función `memory_read`". Declara como: 
	```c
	struct file_operations memory_fops = {
		read: memory_read,
		write: memory_write,
		open: memory_open,
		release: memory_release
	};
	```
	- register_chrdev: Registra un driver de caracteres en el sistema. Vincula un **Major Number** (en tu código es el 60) con un nombre y una tabla de operaciones (`fops`).
	- unregister_chrdev: se usa en el exit para liberar el Major number, desocupando el 60.
	b. ¿Cómo sabe el kernel que funciones del driver invocar para leer y escribir al 	dispositivo?
		Lo sabe porque cuando se registro el dispotivo con major number 60 se le paso la estructura memory_fops que dice que si recibe una solicitud con major number 60 y pide read -> memory_read es la funcion a ejecutar. Igual para write -> memory_write
	c. ¿Cómo se accede desde el espacio de usuario a los dispositivos en Linux?
		Se accede a través de **archivos especiales** ubicados en **`/dev`**. Los programas de usuario no llaman a funciones del kernel directamente; usan las llamadas al sistema estándar (`open`, `read`, `write`, `close`) sobre un archivo de dispositivo (ej. `/dev/memory`).
	d. ¿Cómo se asocia el módulo que implementa nuestro driver con el dispositivo?
	La asociación se hace mediante el **Major Number**.
	1. El módulo se registra con el número **60**.
	2. El administrador crea un nodo en `/dev` con el comando `mknod /dev/memory c 60 0`.
	3. Cuando un proceso abre `/dev/memory`, el kernel mira el número 60 del archivo y lo machea con el driver que se registró con ese mismo número.
	   
	e. ¿Qué hacen las funciones copy_to_user y copy_from_user?
	(https://developer.ibm.com/technologies/linux/articles/l-kernel-memory-access/)
	- **`copy_to_user(dest, src, n)`**: Copia `n` bytes desde el espacio del kernel (`memory_buffer`) al espacio del usuario (`buf`). Se usa en el `read`.
	- **`copy_from_user(dest, src, n)`**: Copia `n` bytes desde el espacio del usuario (`buf`) al espacio del kernel (`memory_buffer`). Se usa en el `write`.
2.   Ahora ejecutamos lo siguiente
   ```bash
   mknod /dev/memory c 60 0
   ```
3. Y luego
```bash
   insmod memory.ko
```
4. Responda:
i. Para qué sirve el comando mknod? ¿qué especifican cada uno de sus
parámetros?.
El comando **`mknod`** (make node) se utiliza para crear **archivos especiales de dispositivo** en el sistema de archivos (normalmente dentro de `/dev`).
En Linux, los dispositivos no son archivos normales con datos en el disco; son "puntos de acceso" al driver. `mknod` crea ese punto de acceso vinculando un nombre de archivo con un driver específico en el kernel.
**Sus parámetros son:** `mknod [NOMBRE] [TIPO] [MAJOR] [MINOR]`
- **NOMBRE**: Es la ruta y el nombre que tendrá el dispositivo (ej. `/dev/memory`).
- **TIPO**: Define qué clase de dispositivo es.
    - `c`: Para dispositivos de **carácter** (como tu driver).
    - `b`: Para dispositivos de **bloque**.    
- **MAJOR**: El número principal que identifica al driver 
- **MINOR**: El número secundario que identifica una instancia o sub-dispositivo específico dentro de ese driver.
ii. ¿Qué son el “major” y el “minor” number? ¿Qué referencian cada uno?
respondido arriba

5. Ahora escribimos a nuestro dispositivo:
echo -n abcdef > /dev/memory
6. Ahora leemos desde nuestro dispositivo:
more /dev/memory

7. Responda lo siguiente:
a. ¿Qué salida tiene el anterior comando?, ¿Porque? (ayuda: siga la ejecución de las funciones memory_read y memory_write y verifique con dmesg)
![[Pasted image 20260403233144.png]]
![[Pasted image 20260403233219.png]]
Solo copia la ultima letra debido a esta linea
tmp=buf+count-1;
b. ¿Cuántas invocaciones a memory_write se realizaron?
6
c. ¿Cuál es el efecto del comando anterior? ¿Por qué?
Se observan 6 write porque la función `write` devuelve `1`, indicando que sólo se escribió un byte. Entonces el sistema vuelva a invocar la operación hasta completar los 6 bytes de "abcdef" Se observan 2 read porque la primera devuelve el byte almacenado y la segunda retorna 0 para indicar el fin del archivo, permitiendo que `more` finalice
d. Hasta aquí hemos desarrollado un ejemplo de un driver muy simple pero de manera completa, en nuestro caso hemos escrito y leído desde un dispositivo que en este caso es la propia memoria de nuestro equipo.
e. En el caso de un driver que lee un dispositivo como puede ser un file system, un dispositivo usb,etc. ¿Qué otros aspectos deberíamos considerar que aquí hemos omitido? ayuda: semáforos, ioctl, inb,outb.
- **Concurrencia (Semáforos/Spinlocks):** ¿Qué pasa si dos procesos intentan hacer `write` al mismo tiempo? Sin bloqueos tipo semaforos o mutex, podrías corromper la memoria del kernel (Race Conditions).
- **Control de hardware (inb / outb):** En lugar de copiar a la RAM, usarías estas funciones para leer o escribir en los **puertos de I/O** físicos del hardware (comunicación directa con los chips).
- **Gestión de interrupciones (IRQ):** El hardware real suele avisar cuando tiene datos listos. El driver debe tener un "Interrupt Handler" para reaccionar a esas señales.
- **`ioctl` (Input/Output Control):** Para operaciones que no son leer o escribir datos, sino configurar el dispositivo (ej. cambiar la velocidad de un puerto serie o expulsar un CD).
- **Espera no bloqueante / Wait Queues:** Si el dispositivo está ocupado, el driver debería poner al proceso a "dormir" hasta que el hardware responda, en lugar de trabar todo el sistema.