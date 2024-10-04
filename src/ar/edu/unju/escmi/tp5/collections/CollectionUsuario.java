package ar.edu.unju.escmi.tp5.collections;

import ar.edu.unju.escmi.tp5.dominio.Usuario;
import java.util.ArrayList;
import java.util.List;

public class CollectionUsuario {
	 public static List<Usuario> usuarios = new ArrayList<>();

	    public static void agregarUsuario(Usuario usuario) {
	        usuarios.add(usuario);
	    }
	    public static void mostrarUsuarios() {
	        for (Usuario usuario : usuarios) {
	            usuario.mostrarDatos();
	        }
	   }
	    
	   public static Usuario buscarUsuarioPorID(String id) {
		   for (Usuario usuario : usuarios) {
			   if(usuario.getId().equals(id)) return usuario;
		   }
		   return null;
	   }
}