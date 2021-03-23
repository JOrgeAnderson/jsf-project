package project.com.br.beans;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import project.com.br.connection.ConnectionBD;
import project.com.br.daogeneric.DaoGeneric;
import project.com.br.entidades.Cidades;
import project.com.br.entidades.Estados;
import project.com.br.entidades.Usuario;
import project.com.br.repository.IDaoUsuario;
import project.com.br.repository.IDaoUsuarioImpl;

@ManagedBean(name = "usuarioBeans")
public class UsuarioBeans implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();
	private DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private IDaoUsuario iDaoUsuario = new IDaoUsuarioImpl();
	
	private List<SelectItem> estados;
	
	private List<SelectItem> cidades;
	
	
	public String salvar() {
	//	Map <String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//Long id = Long.parseLong(params.get("id"));
		//usuario = ConnectionBD.getEntityManager().find(Usuario.class, usuario.getId());
		if (usuario.getId() != null) {
	//		usuario.setId(id);
			usuario = daoGeneric.merge(usuario);
			usuario = new Usuario();
			carregarUsuarios();
			mostrarMsg("ATUALIZADO COM SUCESSO!!");
		} else {
			daoGeneric.salvar(usuario);
			usuario = new Usuario();
			carregarUsuarios();
			mostrarMsg("SALVO COM SUCESSO!!");			
		}
		return "";
	}
	
	public String novo() {
		usuario = new Usuario();
		
		return "";
	}
	
	public String limpar() {
		usuario = new Usuario();
		
		return "";
	}
	
	@PostConstruct
	private void carregarUsuarios() {
		usuarios = daoGeneric.getListEntity(Usuario.class);
		
	}
	
	public String remove() {
		daoGeneric.deletePorId(usuario);
		usuario = new Usuario();
		carregarUsuarios();
		mostrarMsg("REMOVIDO COM SUCESSO");
		
		return "";
	}
	
	public void mostrarMsg(String msg) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
		
	}
	
	@SuppressWarnings("static-access")
	public String deslogar(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("usuarioLogado");
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) context.getCurrentInstance().getExternalContext()
				.getRequest();
		
		httpServletRequest.getSession().invalidate();
		
		return "login.jsf";
	}
	
	public String logar() {
		
		Usuario usuarioLog = iDaoUsuario.consultarUsuario(usuario.getLogin(), usuario.getSenha());
		
		if(usuarioLog != null) {
			
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", usuarioLog);
			
			return "primeirapagina.jsf";
			
		}else {
		
		return "login.jsf";
		
		}
	}
	
	
	public boolean permiteAcesso(String acesso) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Usuario usuarioLog = (Usuario) externalContext.getSessionMap().get("usuarioLogado");
		
		return usuarioLog.getPerfilUser().equals(acesso);
		
	}
	
	@SuppressWarnings("resource")
	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {
			URL url = new URL("https://viacep.com.br/ws/"+usuario.getCep()+"/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			
			while((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}
			
			Usuario gsonAux = new Gson().fromJson(jsonCep.toString(), Usuario.class);
			
			
			usuario.setCep(gsonAux.getCep());
			usuario.setLogradouro(gsonAux.getLogradouro());
			usuario.setComplemento(gsonAux.getComplemento());
			usuario.setBairro(gsonAux.getBairro());
			usuario.setLocalidade(gsonAux.getLocalidade());
			usuario.setUf(gsonAux.getUf());
			
		}catch (Exception e) {
			e.printStackTrace();
			mostrarMsg("CEP NAÕ ENCONTRADO!!");
		}
		
	}
	
	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public DaoGeneric<Usuario> getDaoGeneric() {
		return daoGeneric;
	}


	public void setDaoGeneric(DaoGeneric<Usuario> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}


	public List<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public List<SelectItem> getEstados() {
		
		estados = iDaoUsuario.listaEstados();
		return estados;
	}
	
	
	@SuppressWarnings("unchecked")
	public void carregaCidades(AjaxBehaviorEvent event) {
		
		
		
		Estados estado = (Estados) ((HtmlSelectOneMenu) event.getSource()).getValue();
		
			
			if(estado != null) {
				usuario.setEstados(estado);
				
				List<Cidades> cidades = ConnectionBD.getEntityManager()
						.createQuery("from Cidades where estados.id = "
								+ estado.getId()).getResultList();
				
				List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
				
				for (Cidades cidade : cidades) {
//					SelectItem itemCidade = new SelectItem();
//					itemCidade.setLabel(cidade.getNome());
//					itemCidade.setValue(cidade);
//					selectItemsCidade.add(itemCidade);
					selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
				}
				
				setCidades(selectItemsCidade);
			
		}
		
		
		}
	
	public List<SelectItem> getCidades() {
		return cidades;
	}
	
	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}
	
}
