

La clase Cliente tiene el siguiente protocolo. ¿Cómo puede mejorarlo? 

  
```
/** 

* Retorna el límite de crédito del cliente

*/

public double lmtCrdt() {...

  

/** 

* Retorna el monto facturado al cliente desde la fecha f1 a la fecha f2

*/

protected double mtFcE(LocalDate f1, LocalDate f2) {...

  

/** 

* Retorna el monto cobrado al cliente desde la fecha f1 a la fecha f2

*/

private double mtCbE(LocalDate f1, LocalDate f2) {...
```
Los nombres son poco descriptivos. Podrian renombrarse a algo como: 

```
/** 

* Retorna el límite de crédito del cliente

*/

public double limiteDeCredito() {...

  

/** 

* Retorna el monto facturado al cliente desde la fecha f1 a la fecha f2

*/

protected double montoFacturadoEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {...

  

/** 

* Retorna el monto cobrado al cliente desde la fecha f1 a la fecha f2

*/

private double montoCobradoEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {...
```