  

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdmch7T95l8BXzA28dV-j_RZs-ZbkXxmMzpsHHUc7Mn6-bom4euwvX4ntM-6k1-Jmh7dSWEHJMF1jF3IwDkkcpDsvTQ23XVuVKxOg5N2qlJenaaTGxxBNnYu4aKOvwdkBEiUoh5uA?key=c9sDTzatq0moM0lU9g-6aP7n)

  En el uml deberia decir -post * para que no sea publica.
``` java
/**
* Retorna los últimos N posts que no pertenecen al usuario user
*/

public List<Post> ultimosPosts(Usuario user, int cantidad) {
    List<Post> postsOtrosUsuarios = new ArrayList<Post>();
    for (Post post : this.posts) {
        if (!post.getUsuario().equals(user)) {
            postsOtrosUsuarios.add(post);
        }
    }

   // ordena los posts por fecha
   for (int i = 0; i < postsOtrosUsuarios.size(); i++) {
       int masNuevo = i;
       for(int j= i +1; j < postsOtrosUsuarios.size(); j++) {
           if (postsOtrosUsuarios.get(j).getFecha().isAfter(
     postsOtrosUsuarios.get(masNuevo).getFecha())) {
              masNuevo = j;
           }    
       }

      Post unPost = postsOtrosUsuarios.set(i,postsOtrosUsuarios.get(masNuevo));
      postsOtrosUsuarios.set(masNuevo, unPost);    

   }
    List<Post> ultimosPosts = new ArrayList<Post>();
    int index = 0;
    Iterator<Post> postIterator = postsOtrosUsuarios.iterator();
    while (postIterator.hasNext() &&  index < cantidad) {
        ultimosPosts.add(postIterator.next());
    }
    return ultimosPosts;
}
```
Long Method -> extract method. Se aplica tres veces sobre la funcion ultimosPost

``` java
/**
* Retorna los últimos N posts que no pertenecen al usuario user
*/

private List<Post> filtrarSinUser (){
	List<Post> postsOtrosUsuarios = new ArrayList<Post>();
    for (Post post : this.posts) {
        if (!post.getUsuario().equals(user)) {
            postsOtrosUsuarios.add(post);
        }
    }
}

private List<Post> sortList(List<Post> listaUsuario){
	for (int i = 0; i < postsOtrosUsuarios.size(); i++) {
       int masNuevo = i;
       for(int j= i +1; j < postsOtrosUsuarios.size(); j++) {
           if (postsOtrosUsuarios.get(j).getFecha().isAfter(
     postsOtrosUsuarios.get(masNuevo).getFecha())) {
              masNuevo = j;
           }    
       }
      
      Post unPost = postsOtrosUsuarios.set(i,postsOtrosUsuarios.get(masNuevo));
      postsOtrosUsuarios.set(masNuevo, unPost);    

   } 
}

private List<Post> tailPost (List<Post> listaUsuario, int cantidad){
	List<Post> ultimosPosts = new ArrayList<Post>();
    int index = 0;
    Iterator<Post> postIterator = postsOtrosUsuarios.iterator();
    while (postIterator.hasNext() &&  index < cantidad) {
        ultimosPosts.add(postIterator.next());
    }
    return ultimosPosts;	
}

public List<Post> ultimosPosts(Usuario user, int cantidad) {
	postSinUsuario = filtrarSinUsuario(user);
	postSinUsuario = sortUsaurio(postSinUsuario)
	return tailPost(listaUsuario, cantidad)
}
```

Reinventar la rueda -> Se reemplazan los for por stream y su respectiva operacion. 

``` java
/**
* Retorna los últimos N posts que no pertenecen al usuario user
*/

private List<Post> filtrarSinUsuario (Usuario usuario){
	return post.stream().filter(post -> !post.equals(usuario)).collect(Collectors.toList());
}

private List<Post> sortList(List<Post> postSinUsuario){
	return postSinUsuario.stream().sort((p1,p2) -> p1.getFecha().compareTo((p2.getFecha())).collect(Collectors.toList());
}

private List<Post> tailPost (List<Post> postSinUsuario, int cantidad){
	return postSinUsuario.stream().limit(cantidad).collect(Collectors.toList());	
}

public List<Post> ultimosPosts(Usuario user, int cantidad) {
	postSinUsuario = filtrarSinUsuario(user);
	postUsuario = sortUsaurio(postUsuario)
	return tailPost(listaUsuario, cantidad)
}
```
