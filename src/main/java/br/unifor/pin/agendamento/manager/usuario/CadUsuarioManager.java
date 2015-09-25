package br.unifor.pin.agendamento.manager.usuario;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.bussines.UsuarioBO;
import br.unifor.pin.agendamento.entity.Papeis;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.utils.Encripta;
import br.unifor.pin.agendamento.utils.MessagesUtils;
import br.unifor.pin.agendamento.utils.Navigation;
/**
 * @author patrick.cunha
 * 
 */
@RequestScoped
@ManagedBean(name="cadUsuario")
@Component(value="cadUsuario")
public class CadUsuarioManager {

	@Autowired
	private UsuarioBO usuarioBO;
	@Autowired
	private ListUsuarioManager listUsuario;
	private String nome;
	private String matricula;
	private String senha;
	
	public String salvar(){
		Usuarios usuario = new Usuarios();
		usuario.setNome(nome);
		usuario.setMatricula(matricula);
		usuario.setSenha(Encripta.encripta(senha));
		Papeis p = usuarioBO.buscaPapelAdmin();
		usuario.setPapeis(new ArrayList<Papeis>());
		usuario.getPapeis().add(p);
		usuarioBO.salvar(usuario);
		MessagesUtils.info("Usuário salvo com sucesso!");
		listUsuario.lista();
		
		return Navigation.SUCESSO;
	}
	
	public String preparaSalvar(){
		this.limpaDados();
		
		return Navigation.SUCESSO;
	}
	
	public String voltar(){
		limpaDados();
		
		return Navigation.VOLTAR;
	}
			
	public void limpaDados(){
		this.nome = "";
		this.matricula = "";
		this.senha = "";
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}	
}
