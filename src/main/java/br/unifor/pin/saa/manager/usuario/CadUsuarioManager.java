package br.unifor.pin.saa.manager.usuario;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.saa.bussines.UsuarioBO;
import br.unifor.pin.saa.entity.Papeis;
import br.unifor.pin.saa.entity.Usuarios;
import br.unifor.pin.saa.utils.Encripta;
import br.unifor.pin.saa.utils.MessagesUtils;
import br.unifor.pin.saa.utils.Navigation;
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
	private String email;
	private String senha;
	
	public String salvar(){
		Usuarios usuario = new Usuarios();
		usuario.setNome(nome);
		usuario.setEmail(email);
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
			
	public void limpaDados(){
		this.nome = "";
		this.email = "";
		this.senha = "";
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
