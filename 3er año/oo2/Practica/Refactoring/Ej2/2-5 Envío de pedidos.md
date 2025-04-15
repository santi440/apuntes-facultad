  

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdrFZKU17qOwN5To8hVyWk5OXcFLdc4-uYgXh5I098sSNgzSTKDNdEfRvYb-M1Hc9gb5LJeLsRfhYc2pGvi6oi5IIR9TqupBQ6uRSGlOCmqgTSRnMeJzK8XmDYFI7lgBesuzHD3yA?key=c9sDTzatq0moM0lU9g-6aP7n)

``` java
public class Supermercado {
   public void notificarPedido(long nroPedido, Cliente cliente) {
     String notificacion = MessageFormat.format(“Estimado cliente, se le informa que hemos recibido su pedido con número {0}, el cual será enviado a la dirección {1}”, new Object[] { nroPedido, cliente.getDireccionFormateada() });

    // lo imprimimos en pantalla, podría ser un mail, SMS, etc..
    System.out.println(notificacion);
  }

}

  

public class Cliente {
   public String getDireccionFormateada() {
	return this.direccion.getLocalidad() + “, ” +
			this.direccion.getCalle() + “, ” +
			this.direccion.getNumero() + “, ” +
			this.direccion.getDepartamento();
	}
}
```

Rompe el encapsulamiento -> variables de direccion son publicas deberian ser privadas con getter y setter
Future Envy -> Cliente le pide todo a direccion podria ser un toString() con ese formato.