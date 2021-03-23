package project.com.br.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import project.com.br.connection.ConnectionBD;
import project.com.br.entidades.Cidades;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadeConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@Override /* Retorna objeto inteiro */
	public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {

		EntityManager entityManager = ConnectionBD.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		Cidades cidade = (Cidades) entityManager.find(Cidades.class, Long.parseLong(codigoCidade));

		return cidade;
	}

	@Override /* Retorna apenas o código em String */
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {

		if(cidade == null) {
			return null;
		}
		if(cidade instanceof Cidades) {
			return ((Cidades) cidade).getId().toString();
		
		}else {
			return cidade.toString();
		}

	}

}
