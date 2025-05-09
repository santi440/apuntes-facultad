Punto 2 
ESTATICA

| *1  | *** Reg Activ Main<br>                                                                     |
| --- | ------------------------------------------------------------------------------------------ |
|     | Pto retorno<br>                                                                            |
|     | A(1)= ~~1~~ 2                                                                              |
|     | A(2)= ~~2~~ 5                                                                              |
|     | A(3)= 3                                                                                    |
|     | A(4)= 4<br>                                                                                |
|     | A(5)= 5<br>                                                                                |
|     | A(6)= 6<br>                                                                                |
|     | A(7)= 7<br>                                                                                |
|     | A(8)= 8<br>                                                                                |
|     | A(9)= 9<br>                                                                                |
|     | A(10)= 10<br>                                                                              |
|     | X= ~~1..10~~ ~~5~~ ~~13~~ ~~0~~ ~~6~~ ~~5~~ ~~0~~ ~~6~~ ~~5~~ ~~0~~ ~~6~~ ~~5~~ 1..10 <br> |
|     | Y= ~~1~~ ~~2~~ ~~3~~ 4                                                                     |
|     | Z=~~10~~  ~~11~~  5<br>                                                                    |
|     | Procedure A<br>                                                                            |
|     | Function T<br>                                                                             |
|     | Procedure B<br>                                                                            |
|     | VR . . . . . . . .<br>                                                                     |
| *2  | **Reg Activ A                                                                              |
|     | Pto Retorno<br>                                                                            |
|     | EE (*1)                                                                                    |
|     | ED (*1)                                                                                    |
|     | Y = 2                                                                                      |
|     | T = 1<br>                                                                                  |
|     | VR . . . . . . . .<br>                                                                     |
| *3  | *** Reg Activ B<br>                                                                        |
|     | Pto Retorno                                                                                |
|     | EE = 1*                                                                                    |
|     | ED = 2*                                                                                    |
|     | D = ~~0~~ ~~2~~ ~~4~~ 6                                                                    |
|     | Procedure I<br>                                                                            |
|     | VR . . . .<br>*** Reg Activ…(a partir de<br>acá lo debe continuar…<br>………8………              |
| *4  | *** Reg Activ I                                                                            |
|     | Pto Retorno                                                                                |
|     | EE = *3                                                                                    |
|     | ED = *3                                                                                    |
|     |                                                                                            |
|     | VR ................                                                                        |
| 4*  | *** Reg Activ I                                                                            |
|     | Pto Retorno                                                                                |
|     | EE = *3                                                                                    |
|     | ED = *3                                                                                    |
|     |                                                                                            |
|     | VR ................                                                                        |
| 4*  | *** Reg Activ I                                                                            |
|     | Pto Retorno                                                                                |
|     | EE = *3                                                                                    |
|     | ED = *3                                                                                    |
|     |                                                                                            |
|     | VR ................                                                                        |
|     |                                                                                            |
Dinamica

| *1  | *** Reg Activ Main<br>                                                         |
| --- | ------------------------------------------------------------------------------ |
|     | Pto retorno<br>                                                                |
|     | A(1)= ~~1, 2~~, 5                                                              |
|     | A(2)= 2                                                                        |
|     | A(3)= 3                                                                        |
|     | A(4)= 4<br>                                                                    |
|     | A(5)= 5<br>                                                                    |
|     | A(6)= 6<br>                                                                    |
|     | A(7)= 7<br>                                                                    |
|     | A(8)= 8<br>                                                                    |
|     | A(9)= 9<br>                                                                    |
|     | A(10)= 10<br>                                                                  |
|     | X= ~~1..10~~ ~~5~~ ~~6~~ ~~0~~ ~~6~~ ~~5~~ ~~0~~ ~~6~~ ~~5~~ ~~0~~ ~~6~~ 5<br> |
|     | Y= ~~1~~ 2                                                                     |
|     | Z=~~10~~  11<br>                                                               |
|     | Procedure A<br>                                                                |
|     | Function T<br>                                                                 |
|     | Procedure B<br>                                                                |
|     | VR . . . . . . . .<br>                                                         |
| *2  | **Reg Activ A                                                                  |
|     | Pto Retorno<br>                                                                |
|     | EE (*1)                                                                        |
|     | ED (*1)                                                                        |
|     | Y = 2                                                                          |
|     | T = 1<br>                                                                      |
|     | VR . . . . . . . .<br>                                                         |
| *3  | *** Reg Activ B<br>                                                            |
|     | Pto Retorno                                                                    |
|     | EE = 1*                                                                        |
|     | ED = 2*                                                                        |
|     | D = 0                                                                          |
|     | Procedure I<br>                                                                |
|     | VR . . . .<br>*** Reg Activ…(a partir de<br>acá lo debe continuar…<br>………8………  |
|     |                                                                                |
