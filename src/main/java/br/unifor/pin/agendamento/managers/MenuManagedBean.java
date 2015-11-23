package br.unifor.pin.agendamento.managers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.business.MenuBO;
import br.unifor.pin.agendamento.filter.SessionContext;
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
	
	@Autowired
	private SolicitacaoManager solicitacaoManagedBean;
	
	@Autowired
	private SessionContext sessao;
	
	public MenuManagedBean() {}
	
	public String logoff(){
		getSeguranca().setUsuario(null);
		return Navigation.LOGOFF;
	}
	
	public Object recuperaObjetoSessao(String string){
		return menuBO.recuperaObjetoSessao(string);
	}
	
	public String cadastrarSolicitacao(){
		solicitacaoManagedBean.retornaCoordenador();
		sessao.setarObjetoSessao("edicao", false);
		return Navigation.CADASTRARSOLICITACAO;
	}
	
	public String principal(){
		sessao.setarObjetoSessao("edicao", false);
		return Navigation.PRINCIPAL;
	}
	
	public String pesquisarSolicitacao(){
		return Navigation.PESQUISARSOLICITACAO;
	}
	
	public String cadastrarUsuario(){
		return Navigation.CADASTROUSUARIO;
	}

	public SegurancaTO getSeguranca() {
		return seguranca;
	}

	public void setSeguranca(SegurancaTO seguranca) {
		this.seguranca = seguranca;
	}
}
