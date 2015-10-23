package br.unifor.pin.agendamento.managers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.bussiness.MenuBO;
import br.unifor.pin.agendamento.to.SegurancaTO;
import br.unifor.pin.agendamento.utils.Navigation;

@RequestScoped
@ManagedBean(name="menuManagedBean")
@Component(value="menuManagedBean")
public class MenuManagedBean {
	
	@Autowired
	private MenuBO menuBO;
	
	@Autowired
	private SegurancaTO seguranca;
	
	public MenuManagedBean() {}
	
	public String logoff(){
		getSeguranca().setUsuario(null);
		return Navigation.LOGOFF;
	}
	
	public Object recuperaObjetoSessao(String string){
		return menuBO.recuperaObjetoSessao(string);
	}
	
	public String cadastrarSolicitacao(){
		menuBO.limparSessao();
		return Navigation.CADASTRARSOLICITACAO;
	}
	
	public String principal(){
		return Navigation.PRINCIPAL;
	}
	
	public String manutencao(){
		return Navigation.CADASTROUSUARIO;
	}

	public SegurancaTO getSeguranca() {
		return seguranca;
	}

	public void setSeguranca(SegurancaTO seguranca) {
		this.seguranca = seguranca;
	}
}
