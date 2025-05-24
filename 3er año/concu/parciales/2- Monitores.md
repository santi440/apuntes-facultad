Resolver con MONITORES el siguiente problema. En una sala se juntan 20 conferencistas y un coordinador para una conferencia internacional. Cuando todos han llegado a la sala (los 20 conferencistas y el coordinador) el coordinador abre la sesión con una presentación de 30 minutos, y luego cada conferencista realiza su presentación de 10 minutos, de a uno a la vez y de acuerdo al orden en que llegaron a la sala. Cuando todas las presentaciones terminaron, las personas (conferencistas y coordinador) se retiran.

```c
process coordinador
    conferencia.llegarConf();
    delay(1800);
    conferencia.termine();
process conferencista[0..19]
    conferencia.llegarConf();
    delay(600);
    conferencia.termine();
    
monitor conferencia
    cond conf;
    cond coord;
    cond todos;
   int cant = 0;

procedure llegarCord(){
    if(cant != 20){
        wait(coord);
    }
}

procedure llegarConf(){
    cant++;
    if(cant == 20){
        signal(coord);
    }
    wait(conf);
}

procedure termine(){
    cant--;
    signal(conf);
    if(cant == 0){
        signalAll(todos);
    }else{
        wait(todos);
    }
}
```