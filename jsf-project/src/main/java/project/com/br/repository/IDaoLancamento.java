package project.com.br.repository;

import java.util.List;

import project.com.br.entidades.Lancamento;

public interface IDaoLancamento {

	List<Lancamento> consultar(Long codUser);
}
