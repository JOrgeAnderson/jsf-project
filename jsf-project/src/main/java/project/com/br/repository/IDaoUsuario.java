package project.com.br.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import project.com.br.entidades.Usuario;

public interface IDaoUsuario {

	Usuario consultarUsuario(String login, String senha);
	
	List<SelectItem> listaEstados();
}
