package br.unifor.pin.agendamento.managers;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.bussiness.UsuarioBO;
import br.unifor.pin.agendamento.entity.Cursos;
import br.unifor.pin.agendamento.entity.Papeis;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.to.SegurancaTO;
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
	@Autowired
	private SegurancaTO segurancaTO;
	private String nome;
	private String matricula;
	private String senha;
	private Usuarios usuario;
	private List<Cursos> listaCursos;
	private List<Papeis> listaPapeis;
	
	public List<Cursos> listarCursos(){
		return listaCursos = usuarioBO.retornaTodosCursos();
	}
	
	public List<Papeis> listarPapeis(){
		return listaPapeis = usuarioBO.retornaTodosPapeis(); 
	}
	
	public String salvar(){
		setUsuario(new Usuarios());
		getUsuario().setNome(nome);
		getUsuario().setMatricula(matricula);
		getUsuario().setSenha(Encripta.encripta(senha));
		segurancaTO.setUsuario(getUsuario());
		usuarioBO.salvar(getUsuario());
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
		
		return Navigation.PRINCIPAL;
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

	public List<Cursos> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List<Cursos> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public List<Papeis> getListaPapeis() {
		return listaPapeis;
	}

	public void setListaPapeis(List<Papeis> listaPapeis) {
		this.listaPapeis = listaPapeis;
	}	
}

