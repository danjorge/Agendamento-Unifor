package br.unifor.pin.agendamento.utils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

@Component
public class SessionContext {
	
	private static SessionContext instance;
	
	
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
		currentExternalContext().getSessionMap().clear();
	}
}
