package br.unifor.pin.agendamento.manager.solicitacao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.bussines.SolicitacaoBO;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.filter.SessionContext;
import br.unifor.pin.agendamento.utils.MessagesUtils;
import br.unifor.pin.agendamento.utils.Navigation;

@RequestScoped
@ManagedBean(name="solicitacaoManagedBean")
@Component(value="solicitacaoManagedBean")
public class SolicitacaoManager {
	
	@Autowired
	private SolicitacaoBO solicitacaoBO;
	
	@Autowired
	private SessionContext sessao;
	
	private Solicitacao solicitacao;
	private Solicitacao solicitacaoVisualizacao;
	private List<Solicitacao> listaSolicitacoes;
	
	// VARIÁVEIS DE TELA
	// -----------------------------------------------------------
	private boolean exibeCampoResponderSolicitacao = false;
	private boolean escondeBtnResponderSolicitacao = false;
	// -----------------------------------------------------------	
	
	@PostConstruct
	public void init(){
		solicitacao = new Solicitacao();
	}
	
	public void carregaListas(){
		listaSolicitacoes = solicitacaoBO.buscarSolcitacoesPorCurso();		
	}
	
	public String salvarSolicitacao(){
		if(solicitacao.getAssunto() != null && solicitacao.getDescricao() != null){
			solicitacaoBO.salvarSolicitacao(solicitacao);
			MessagesUtils.info("Solicitação salva com sucesso");
			listaSolicitacoes.add(solicitacao);
		} else {
			MessagesUtils.error("Algo errado aconteceu.");
		}
		
		return Navigation.PRINCIPAL;
	}
	
	public String visualizarSolicitacao(Solicitacao sol){
		solicitacaoVisualizacao = solicitacaoBO.recuperaSolicitacaoPorId(sol.getId());
		return Navigation.VISUALIZARSOLICITACAO;
	}
	
	public Usuarios retornaCoordenador(){
		if(sessao != null){
			Usuarios usuario = solicitacaoBO.retornaCoordenadorPorCurso((Usuarios) sessao.recuperaObjetoSessao("usuario")); 
			if(usuario != null){
				return usuario;
			}
		}
		return null;
	}
	
	public void preparaCampoResponderSolicitacao(){
		setExibeCampoResponderSolicitacao(true);
		setEscondeBtnResponderSolicitacao(true);
	}
	
	public String voltar(){
		return Navigation.PRINCIPAL;
	}
	
	public void limparSolicitacao(){
		solicitacao.setAssunto("");
		solicitacao.setDescricao("");
	}

	public List<Solicitacao> getListaSolicitacoes() {
		return listaSolicitacoes;
	}

	public void setListaSolicitacoes(List<Solicitacao> listaSolicitacoes) {
		this.listaSolicitacoes = listaSolicitacoes;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public boolean isEscondeBtnResponderSolicitacao() {
		return escondeBtnResponderSolicitacao;
	}

	public void setEscondeBtnResponderSolicitacao(
			boolean escondeBtnResponderSolicitacao) {
		this.escondeBtnResponderSolicitacao = escondeBtnResponderSolicitacao;
	}

	public boolean isExibeCampoResponderSolicitacao() {
		return exibeCampoResponderSolicitacao;
	}

	public void setExibeCampoResponderSolicitacao(
			boolean exibeCampoResponderSolicitacao) {
		this.exibeCampoResponderSolicitacao = exibeCampoResponderSolicitacao;
	}

	public Solicitacao getSolicitacaoVisualizacao() {
		return solicitacaoVisualizacao;
	}

	public void setSolicitacaoVisualizacao(Solicitacao solicitacaoVisualizacao) {
		this.solicitacaoVisualizacao = solicitacaoVisualizacao;
	}

}
