package project.com.br.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import project.com.br.daogeneric.DaoGeneric;
import project.com.br.entidades.Lancamento;
import project.com.br.entidades.Usuario;
import project.com.br.repository.IDaoLancamento;
import project.com.br.repository.IDaoLancamentoImpl;

@ManagedBean(name = "lancamentoBeans")
public class LancamentoBeans {

	private Lancamento lancamento = new Lancamento();
	private DaoGeneric<Lancamento> daoGeneric = new DaoGeneric<Lancamento>();
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	private IDaoLancamento iDaoLancamento = new IDaoLancamentoImpl();
	
	
	public String salvar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Usuario usuarioLog = (Usuario) externalContext.getSessionMap().get("usuarioLogado");
		lancamento.setUsuario(usuarioLog);
		
		if(lancamento.getId() != null) {//U = UPDATE
			lancamento = daoGeneric.merge(lancamento);
			lancamento = new Lancamento();
			carregarLancamentos();
		
		}else {//C = CREATE
			daoGeneric.salvar(lancamento);
			lancamento= new Lancamento();
			carregarLancamentos();
		}
		
		return "";
	}
	
	@PostConstruct
	public void carregarLancamentos() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Usuario usuarioLog = (Usuario) externalContext.getSessionMap().get("usuarioLogado");
		lancamentos = iDaoLancamento.consultar(usuarioLog.getId());
		
	}
	
	public String novo() {
		
		lancamento = new Lancamento();
		
		return "";
	}
	
	public String remove() {
		
		daoGeneric.deletePorId(lancamento);
		lancamento = new Lancamento();
		carregarLancamentos();
		
		return "";
	}
	
	
	
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public DaoGeneric<Lancamento> getDaoGeneric() {
		return daoGeneric;
	}
	public void setDaoGeneric(DaoGeneric<Lancamento> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	public IDaoLancamento getiDaoLancamento() {
		return iDaoLancamento;
	}
	public void setiDaoLancamento(IDaoLancamento iDaoLancamento) {
		this.iDaoLancamento = iDaoLancamento;
	}
	
}
