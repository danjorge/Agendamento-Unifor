package br.unifor.pin.agendamento.manager.menu;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.stereotype.Component;

@RequestScoped
@ManagedBean(name="menuManagedBean")
@Component(value="menuManagedBean")
public class Menu {
	
	private final String LOGOFF = "/logoff"; 
	
	public Menu() {
		// TODO Auto-generated constructor stub
	}	
	
	public String logoff(){
		return LOGOFF;
	}
}
