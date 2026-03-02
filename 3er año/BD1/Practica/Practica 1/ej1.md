1) Análisis de un Modelo de E/R. Cuadrosa. 
![[cuadros.png]]
a. En este modelo cada período de exposición contiene múltiples cuadros en museos. ¿Qué parte del modelo indica esto? ¿Cómo la modificaría para que cada período fuese exclusivo de cada cuadro expuesto en un museo?
Se indica en la relación expuesto entre Período y la agregación de cuadro y museo.
Para la modificación propuesta basta con cambiar la cardinalidad de (1,N) a (1,1) como se muestra a continuación:
 ![[Pasted image 20250831141725.png]]
b. Si los cuadros se expusieran en un solo período dentro de cada museo ¿cómo ajustaría el modelo para reflejar esto?
![[Pasted image 20250831141928.png]]
c. Ajuste el modelo para representar museos de dos tipos: de arte contemporáneo, con fecha de inauguración, país, director, curador a cargo y movimiento artístico; y de arte en general, del cual se conoce una fecha estimada de inauguración, país, director, restaurador principal y datos históricos. De los datos históricos se registra un año y una descripción histórica, por ejemplo que una pintura famosa se exhibió por primera vez allí en un año determinado.
![[Pasted image 20250831143307.png]]