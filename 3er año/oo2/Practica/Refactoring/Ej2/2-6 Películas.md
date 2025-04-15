 ![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXcyrMDY99MxJcdhOqy0_yjmbzP7tTE7ayRFAvGqzauDMpwqt65_3cYm9DpcuSjJuoWcZWh_TbdugTL-ZUQ9bTUCWmKpfM6tQRw4PSOrLVmpAwvCgKudRO3_JjxkucW7U5DzVVMh?key=c9sDTzatq0moM0lU9g-6aP7n)
``` java
public class Usuario {

    String tipoSubscripcion;
    // ...
    public void setTipoSubscripcion(String unTipo) {
	    this.tipoSubscripcion = unTipo;
    }

    public double calcularCostoPelicula(Pelicula pelicula) {
		double costo = 0;
	    if (tipoSubscripcion=="Basico") {
		    costo = pelicula.getCosto() + pelicula.calcularCargoExtraPorEstreno();
	    }
	    else if (tipoSubscripcion== "Familia") {
		    costo = (pelicula.getCosto() + pelicula.calcularCargoExtraPorEstreno()) * 0.90;
	    }
	    else if (tipoSubscripcion=="Plus") {
		    costo = pelicula.getCosto();
	    }
	    else if (tipoSubscripcion=="Premium") {
		    costo = pelicula.getCosto() * 0.75;
    }
    return costo;
    }

}

  

public class Pelicula {
    LocalDate fechaEstreno;

    // ...
    public double getCosto() {
	    return this.costo;
    }
    public double calcularCargoExtraPorEstreno(){
// Si la Película se estrenó 30 días antes de la fecha actual, retorna un cargo de 0$, caso contrario, retorna un cargo extra de 300$
	    return (ChronoUnit.DAYS.between(this.fechaEstreno, LocalDate.now()) ) > 30 ? 0 : 300;
    }
}
```

If statement -> Se generaliza el comportamiento en comun junto al extract method y se generaliza en Suscripcion con sus implementaciones concretas


No tiene tanto sentido el pelicula.getCosto() como generalización, podria hacerse pero es lo mismo sino esta. 

``` java
public abstract class Suscribcion {
	public double calcularCosto(Pelicula pelicula){
		return pelicula.getCosto();
	}
}

public class SuscripcionBasica{
	@Override
	public double calcularCosto(Pelicula pelicula){
		return super.calcularCosto(Pelicula pelicula) + pelicula.calcularCargoExtraPorEstreno();
	}
} 

public class SuscripcionFamilia{
	@Override
	public double calcularCosto(Pelicula pelicula){
		return super.calcularCosto(Pelicula pelicula) + pelicula.calcularCargoExtraPorEstreno() * 0.9;
	}
} 

public class SuscripcionPlus{
	@Override
	public double calcularCosto(Pelicula pelicula){
		return super.calcularCosto(Pelicula pelicula);
	}
} 

public class SuscripcionPlus{
	@Override
	public double calcularCosto(Pelicula pelicula){
		return super.calcularCosto(Pelicula pelicula) * 0.75;
	}
} 

public class Usuario {

    Suscripcion subscripcion;
    // ...
    public void setTipoSubscripcion(Suscripcion unTipo) {
	    this.tipoSubscripcion = unTipo;
    }

    public double calcularCostoPelicula(Pelicula pelicula) {
		return costo = suscripcion.calcularCosto();
    }

}

  

public class Pelicula {
    LocalDate fechaEstreno;

    // ...
    public double getCosto() {
	    return this.costo;
    }
    public double calcularCargoExtraPorEstreno(){
// Si la Película se estrenó 30 días antes de la fecha actual, retorna un cargo de 0$, caso contrario, retorna un cargo extra de 300$
	    return (ChronoUnit.DAYS.between(this.fechaEstreno, LocalDate.now()) ) > 30 ? 0 : 300;
    }
}
```