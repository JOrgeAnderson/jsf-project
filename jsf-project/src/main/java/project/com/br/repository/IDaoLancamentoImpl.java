package project.com.br.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import project.com.br.connection.ConnectionBD;
import project.com.br.entidades.Lancamento;

public class IDaoLancamentoImpl implements IDaoLancamento{

	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> consultar(Long codUser) {
		
		List<Lancamento> lista = null;
		
		EntityManager entityManager = ConnectionBD.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.createQuery(" from Lancamento where usuario.id = "+ codUser).getResultList();
		
		transaction.commit();
		entityManager.clear();
		
		return lista;
		
	}

}
