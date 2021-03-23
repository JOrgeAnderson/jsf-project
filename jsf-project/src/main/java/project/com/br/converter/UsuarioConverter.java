package project.com.br.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import project.com.br.connection.ConnectionBD;
import project.com.br.entidades.Usuario;

@FacesConverter (forClass = Usuario.class, value="usuarioConverter")
public class UsuarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String idUsuario) {
		EntityManager entityManager = ConnectionBD.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Usuario usuario = entityManager.find(Usuario.class, Long.parseLong(idUsuario));
		return usuario;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object usuario) {
		if (usuario == null) {
			return null;
		} else if (usuario instanceof Usuario) {
			return ((Usuario) usuario).toString();

		} else {
			return usuario.toString();
		}
	}
	
	

	
	

}
