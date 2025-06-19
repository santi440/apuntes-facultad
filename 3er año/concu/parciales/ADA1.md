Resolver el siguiente problema con ADA. Se quiere modelar un puente colgante de un solo sentido, el cual resiste hasta 100 personas. Suponga que hay una cantidad innumerable de personas que quieren cruzar y que sólo lo podrán hacer si no superan la cantidad máxima. Las personas cruzan el puente de acuerdo al orden de llegada, aunque los jubilados tienen prioridad sobre los no jubilados.

```c
procedure parcial
	TASK puente IS
		Entry cruzarNormal();
		Entry cruzarJubilado();
		Entry salir();
	TYPE TASK persona

	personas = array (1..P) of persona;
	TASK BODY puente
		cruzando : integer;
	BEGIN
		cruzando = 0;
		loop
			select
				accept salir() => cruzando--;
				OR WHEN (cruzando < 100) accept cruzarJubilado() => cruzando ++;
				OR WHEN (cruzando < 100) AND (cruzarJubilado`count == 0) accept cruzarNormal() => cruzando ++;
			end select;
		END loop;
	END puente;

	TASK BODY persona
		tipo Texto;
	BEGIN
		tipo = determinarJubilado(); //podria hacer dos procesos diferentes y que sea polimorfico, asi esta en la explicacion yo fiel a lo que dijo el profe
		if(tipo = "jubilado") {puente.cruzarJubilado()}
		else {puente.cruzarNormal()}
		cruzarPuente();
		puente.salir();
	END persona;
BEGIN 
	null;
END parcial;
```


> [!NOTE] Sobre la nota
> Cuando lo explica lo hace con dos tipo Jubilado y no jubilado pero aclara que un solo tipo tmb esta bien. Igual me re cago jaja 
