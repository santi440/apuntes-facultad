```
public class Juego {
    // ......
    public void incrementar(Jugador j) {
        j.puntuacion = j.puntuacion + 100;
    }
    
    public void decrementar(Jugador j) {
        j.puntuacion = j.puntuacion - 50;
    }
}
  

public class Jugador {
    public String nombre;
    public String apellido;
    public int puntuacion = 0;
}
```
BadSmells : rompe el encapsulamiento. Encapsulate fields -> pasa las varibles de instancia a privadas.
```
public class Juego {
    // ......
    public void incrementar(Jugador j) {
        j.puntuacion = j.puntuacion + 100;
    }
    
    public void decrementar(Jugador j) {
        j.puntuacion = j.puntuacion - 50;
    }
}
  

public class Jugador {
    private String nombre;
    private String apellido;
    private int puntuacion = 0;
}
```
BadSmells : nombres poco descriptivos. Rename methods -> No se especifica que es lo que se incrementa y decrementa.

```
public class Juego {
    // ......
    public void incrementarPuntos(Jugador j) {
        j.setPuntuacion(j.getPuntuacion() + 100);
    }
    
    public void decrementarPuntos(Jugador j) {
        j.setPuntuacion(j.getPuntuacion() - 50);
    }
}
  

public class Jugador {
    private String nombre;
    private String apellido;
    private int puntuacion = 0;
    
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getPuntuacion() {
        return this.puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
```
Bad Smell: Feature Envy Solución y DataClass: Move Method > Los métodos "incrementarPuntos" y "decrementarPuntos" ejecutan lógica que debería estar asignada a jugador, por lo tanto tenemos responsabilidades mal asignadas. Se deben mover estos métodos a la clase Jugador y en la clase Juego escribir unos nuevos que los invoquen según corresponda. De esta manera la clase Juego es encargada de dar o quitar puntos, pero esta tarea debe pasar por el Jugador al que le corresponde. Podria ser que en lugar de recibir un parametro se incremente o decremente por los valores fijos pero lo dejo asi por escalabilidad.
```
public class Juego {
    // ......
    public void incrementarPuntos(Jugador j) {
        j.incrementarPuntos(100);
    }
    
    public void decrementarPuntos(Jugador j) {
        j.decrementarPuntos(50);
    }
}
public class Jugador {
    private String nombre;
    private String apellido;
    private int puntuacion = 0;
    
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getPuntuacion() {
        return this.puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    
    pubic void incrementarPuntos(int puntuacion){
	    this.puntuacion += puntuacion; 
    }

	pubic void decrementarPuntos(int puntuacion){
	    this.puntuacion -= puntuacion; 
    }
}
```
Si estuviesen los constructores seria ideal que el de jugador sea algo asi:
```
public Jugador (string nombre, String apellido){
	this.nombre = nombre;
	this.apellido = apellido;
	this.puntuacion = 0;
}
```
La otra manera tampoco esta pero para evitar conflito con algun ayudante que no piense asi. No es un mal olor propiamente dicho.