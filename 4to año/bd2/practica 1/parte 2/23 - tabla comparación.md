| Relación                            | Tipo de anotación | FetchType elegido | Justificación                                                                                                              |
| ----------------------------------- | ----------------- | ----------------- | -------------------------------------------------------------------------------------------------------------------------- |
| Purchase -> User                    | @ManyToOne        | EAGER             | El usuario es un atributo que le da sentido a la compra y se consulta mucho                                                |
| Purchase -> Route                   | @ManyToOne        | EAGER             | Podria ser LAZY -> no siempre que miro la compra miro la ruta pero puedo traerla con anterioridad para ahorrarme problemas |
| Purchase -> itemServiceList         | @OneToMany        | LAZY              | No me voy a traer la lista completa de todos los items que contrato. No los usaria casi nunca                              |
| Purchase -> Review                  | @OneToOne         | EAGER/LAZY        | YO lo dejaria en lazy. no considero tan frecuente de consulta la review                                                    |
| ItemService -> Service              | @ManyToOne        | EAGER             | ItemService no tiene mucho sentido sin el servicio                                                                         |
| Route -> stops                      | @OneToMany        | LAZY              | No siempre pregunto por todos los stops                                                                                    |
| Route -> drivers(DriverUser)        | @ManyToMany       | LAZY              | Es una lista de todos los choferes, RARA vez lo consulto                                                                   |
| Route -> tourGuides (TourGuideUser) | @ManyToMany       | LAZY              | Mismo arriba pero guia turistico                                                                                           |
| User -> purchases                   | @OneToMany        | LAZY              | Considerar que podria taerme millones de Purchases al pedo cuando solo queria ver el nombre de un usuario                  |
| Service -> supplier                 | @ManyToOne        | EAGER/LAZY        | YO me tiro mas por lazy porque no miro la proveedora del servicio TAN frecuentemente por más que sea una sola              |

