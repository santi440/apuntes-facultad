![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXduFt3tTmQ1kGKyASZW6W5fJ7DeW7k-PzPXIOsGzL_d1gy_Wgbddlco5w3HPKV8fXitiyBuO1sJwkvDLh8IqnvBV431xAnooxP5-9vWLPxjRvgmUHSRyzLSUWu3gujLIVHoPgHPkA?key=c9sDTzatq0moM0lU9g-6aP7n)  

``` java
public class Producto {
    private String nombre;
    private double precio;
    
    public double getPrecio() {
        return this.precio;
    }

}

  

public class ItemCarrito {
    private Producto producto;
    private int cantidad;
    
    public Producto getProducto() {
        return this.producto;
    }

    public int getCantidad() {
        return this.cantidad;
    }

}

  

public class Carrito {
    private List<ItemCarrito> items;
    public double total() {
		return this.items.stream()
			.mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
		.sum();
    }

}
```
Feature Envy -> item.getProducto().getPrecio() es un pasamanos, podria estar un metodo en item que haga el pedido. 
Item y Pedido son Data/Lazzy class pero no esta mal en el modelo 
Juti says "hoy soy peor que mañana pero mejor que ayer" 