package br.unifor.pin.agendamento.filter;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.to.SegurancaTO;

@Component
public class SessionContext {
	
	private static SessionContext instance;
	
	@Autowired
	private SegurancaTO seguranca;
	
	private Usuarios usuario;
	
	public static SessionContext getInstance(){
		if(instance == null){
			instance = new SessionContext();
		}
	
		return instance;
	}
	
	private SessionContext(){
		
	}
	
	private ExternalContext currentExternalContext(){
		if(FacesContext.getCurrentInstance() == null){
			throw new RuntimeException("O FacesContext não pode ser chamado fora de uma requisição HTTP");
		}
		else {
			return FacesContext.getCurrentInstance().getExternalContext();
		}
	}
	
	public void encerraSessao(){
		currentExternalContext().invalidateSession();
	}
	
	public String recuperaIdSessao(){
		return currentExternalContext().getSessionId(true);
	}
	
	public Object recuperaObjetoSessao(String name){
		return currentExternalContext().getSessionMap().get(name);
	}
	
	public void setarObjetoSessao(String name, Object valor){
		currentExternalContext().getSessionMap().put(name, valor);
	}
	
	public void removeObjetoSessao(String objeto){
		currentExternalContext().getSessionMap().remove(objeto);
	}
	
	public void limparSessao(){
		usuario = (usuario == null ? getUsuario() : seguranca.getUsuario());
		currentExternalContext().getSessionMap().clear();
		
		setarObjetoSessao("usuario", usuario);
	}

	public SegurancaTO getSeguranca() {
		return seguranca;
	}

	public void setSeguranca(SegurancaTO seguranca) {
		this.seguranca = seguranca;
	}

	public Usuarios getUsuario() {
		return (Usuarios) (usuario != null ? usuario : recuperaObjetoSessao("usuario"));
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
}
