``` java
public class Document {
    List<String> words;
    public long characterCount() {
		  long count = this.words
				.stream()
				.mapToLong(w -> w.length())
				.sum();
     return count;
}

    public long calculateAvg() {
		long avgLength = this.words
			.stream()
			.mapToLong(w -> w.length())
			.sum() / this.words.size();
  return avgLength;

}

// Resto del código que no importa

}
```

Malos olores : 
- Rompe el encapsulamiento, las variables son publicas y deberian ser privadas.
- Duplicate code -> llamar al metodo `characterCount` en lugar de escribir  
- Inline temp -> Elimina las variables temporales de un solo uso y devuelve directamente
``` java
public class Document {
	private List<String> words;
	public long characterCount() {
		  return this.words
				.stream()
				.mapToLong(w -> w.length())
				.sum();
	}
	public long calculateAvg() {
		return characterCount() / this.words.size();
	}
	// Resto del código que no importa
}
```
No se comprueba si la cantidad de palabras es >0, no puedo dividir entre . No se soluciona y no hay ningun refactoring que lo solucione porque no corresponde hacerlo en esta etapa.