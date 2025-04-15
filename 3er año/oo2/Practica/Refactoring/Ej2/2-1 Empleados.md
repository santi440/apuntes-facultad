```
public class EmpleadoTemporario {

    public String nombre;
    public String apellido;
    public double sueldoBasico = 0;
    public double horasTrabajadas = 0;
    public int cantidadHijos = 0;

    // ......

	public double sueldo() {
		return this.sueldoBasico
			+ (this.horasTrabajadas * 500) 
		    - (this.cantidadHijos * 1000) 
			- (this.sueldoBasico * 0.13);
	}
}

public class EmpleadoPlanta {
    public String nombre;
    public String apellido;
    public double sueldoBasico = 0;
    public int cantidadHijos = 0;

    // ......

    public double sueldo() {
	    return this.sueldoBasico 
			+ (this.cantidadHijos * 2000)
			- (this.sueldoBasico * 0.13);
    }
}

  

public class EmpleadoPasante {
    public String nombre;
    public String apellido;
    public double sueldoBasico = 0;

    // ......

    public double sueldo() {

        return this.sueldoBasico - (this.sueldoBasico * 0.13);

    }

}
```

i y ii) Malos olores: 
- Encapsulate fields -> Las variables son publicas deberian ser privadas (tengo que ver cual es el refactoring). Pull Up method + Pull up fields
- Duplicate code -> Extract Superclass

```
public Abstract class Empleado {
	protected String nombre;
    protected String apellido;
    protected double sueldoBasico = 0;
	
	public abstract double sueldo() {
		return this.sueldoBasico - (this.sueldoBasico * 0.13);
	}
	protected double getSueldo(){
		return this.sueldoBasico;
	}
}
public class EmpleadoTemporario extends Empleado{
    protected double horasTrabajadas = 0;
    protected int cantidadHijos = 0;

    // ......

public double sueldo() {
	return super.sueldo()
		+ (this.horasTrabajadas * 500) 
	    - (this.cantidadHijos * 1000) 
	}
}

public class EmpleadoPlanta extends Empleado{
    protected int cantidadHijos = 0;

    // ......

    public double sueldo() {
	    return this.sueldoBasico 
			+ (this.cantidadHijos * 2000)
    }
}

  

public class EmpleadoPasante extends Empleado{
    // ......

    public double sueldo() {
        return super.sueldo();
    }

}
```

Considero que ademas puede llegar a no ser necesario Empleado Pasante, si no tiene nada mas seria Lazy Class