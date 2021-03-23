package project.com.br.daogeneric;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import project.com.br.connection.ConnectionBD;

public class DaoGeneric<E> implements Serializable{

	private static final long serialVersionUID = 1L;

	public void salvar(E entity) { //C - CREATE (INSERT)
		EntityManager entityManager = ConnectionBD.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entity);
		transaction.commit();
		entityManager.clear();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<E> getListEntity(Class<E> entity){//R - READ (SELECT)
		EntityManager entityManager = ConnectionBD.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		List<E> retorno = entityManager.createQuery("from "+entity.getName()).getResultList();
		
		transaction.commit();
		entityManager.clear();
		
		return retorno;
	}
	
	public E merge(E entity) {//UPDATE

		EntityManager entityManager = ConnectionBD.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		E retorno = entityManager.merge(entity);

		transaction.commit();
		entityManager.clear();

		return retorno;
	}
	
	public void delete(E entity) {//D - DELETE
		EntityManager entityManager = ConnectionBD.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.remove(entity);
		
		transaction.commit();
		entityManager.clear();
		
	}
	
	public void deletePorId(E entity) {
		EntityManager entityManager = ConnectionBD.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Object id = ConnectionBD.getPrimaryKey(entity);
		entityManager.createQuery("delete from "+entity.getClass().getCanonicalName() + " where id = "+id).executeUpdate();
		
		transaction.commit();
		entityManager.clear();
		
	}
}
