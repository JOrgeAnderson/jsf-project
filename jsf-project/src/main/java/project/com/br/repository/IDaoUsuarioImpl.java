package project.com.br.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import project.com.br.connection.ConnectionBD;
import project.com.br.entidades.Estados;
import project.com.br.entidades.Usuario;

public class IDaoUsuarioImpl implements IDaoUsuario, Serializable{


	private static final long serialVersionUID = 1L;

	@Override
	public Usuario consultarUsuario(String login, String senha) {
		
		Usuario usuario = null;
		
		EntityManager entityManager = ConnectionBD.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		usuario = (Usuario) entityManager.createQuery("select u from Usuario u where u.login = '"+login+"' and u.senha = '"+senha+"'").getSingleResult();
		
		transaction.commit();
		entityManager.clear();
		
		return usuario;
	}

	@Override
	public List<SelectItem> listaEstados() {
		
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		
		EntityManager entityManager = ConnectionBD.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		List<Estados> estados = entityManager.createQuery(" from Estados").getResultList();
		
		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}
		
		return selectItems;
	}
	
	

}
