package br.unifor.pin.agendamento.managers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.business.UsuarioBO;
import br.unifor.pin.agendamento.entity.Cursos;
import br.unifor.pin.agendamento.entity.Papeis;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.exceptions.DAOException;
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
	private String nome;
	private String matricula;
	private String senha;
	private Usuarios usuario;
	private Cursos cursos;
	private Papeis papeis;
	private List<Cursos> listaCursos;
	private List<Papeis> listaPapeis;
	
	public List<Cursos> listarCursos() throws DAOException{
		return listaCursos = usuarioBO.retornaTodosCursos();
	}
	
	public List<Papeis> listarPapeis() throws DAOException{
		return listaPapeis = usuarioBO.retornaTodosPapeis(); 
	}
	
	public String salvar(){
		usuario = new Usuarios();
		usuario.setNome(nome.toUpperCase());
		usuario.setMatricula(matricula);
		usuario.setSenha(Encripta.encripta(senha));
		usuario.setCursos(new ArrayList<Cursos>());
		usuario.getCursos().add(cursos);
		usuario.setPapel(papeis);
		usuario.setAtivo(true);
		
		usuarioBO.salvar(usuario);
		MessagesUtils.info("Usuário salvo com sucesso!");
		
		return Navigation.SUCESSO;
		
	}	
	
	public String voltar(){
		limpaDados();
		
		return Navigation.PRINCIPAL;
	}
			
	public void limpaDados(){
		usuario = null;
		nome = null;
		matricula = null;
		senha = null;
		cursos = new Cursos();
		papeis = new Papeis();
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

	public Cursos getCursos() {
		return cursos;
	}

	public void setCursos(Cursos cursos) {
		this.cursos = cursos;
	}

	public Papeis getPapeis() {
		return papeis;
	}

	public void setPapeis(Papeis papeis) {
		this.papeis = papeis;
	}	
}

