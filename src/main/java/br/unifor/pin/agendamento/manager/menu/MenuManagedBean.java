package br.unifor.pin.agendamento.manager.menu;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.bussines.MenuBO;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.utils.Navigation;
import br.unifor.pin.agendamento.utils.SessionContext;

@RequestScoped
@ManagedBean(name="menuManagedBean")
@Component(value="menuManagedBean")
public class MenuManagedBean {
	
	@Autowired
	private MenuBO menuBO;
	
	@Autowired
	private SessionContext sessao;
	
	private Usuarios usuario;
	
	public MenuManagedBean() {}
	
	public String logoff(){
		sessao.encerraSessao();
		menuBO.finalizarSessao();
		return Navigation.LOGOFF;
	}
	
	public String principal(){
		return Navigation.PRINCIPAL;
	}

	public Usuarios getUsuario() {
		if(usuario == null){
			usuario = (Usuarios) menuBO.recuperaUsuarioSessao();
		}
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
}
