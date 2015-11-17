package br.unifor.pin.agendamento.managers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.business.AgendamentoBO;
import br.unifor.pin.agendamento.business.SolicitacaoBO;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Status;
import br.unifor.pin.agendamento.filter.SessionContext;
import br.unifor.pin.agendamento.utils.Navigation;


@RequestScoped
@ManagedBean(name="pesquisaSolicitacaoManagedBean")
@Component(value="pesquisaSolicitacaoManagedBean")
public class PesquisaSolicitacaoManager {

	@Autowired
	private SolicitacaoBO solicitacaoBO;
	
	@Autowired
	private SolicitacaoManager solicitacaoManagedBean;
	
	@Autowired
	private AgendamentoManager agendamentoManagedBean;
	
	@Autowired
	private AgendamentoBO agendamentoBO;
	
	@Autowired
	private SessionContext sessao;
	
	private Solicitacao pesquisaSolicitacao;
	private Status statusSolicitacao;
	
	private List<Solicitacao> listaSolicitacoes;
	private List<Status> listaStatusSolicitacao;
	
	@PostConstruct
	public void init() {
		pesquisaSolicitacao = new Solicitacao();
	}
	
	public void pesquisarSolicitacao(){
		listaSolicitacoes = solicitacaoBO.pesquisarSolicitacao(pesquisaSolicitacao, statusSolicitacao);
	}
	
	public String visualizarSolicitacao(Solicitacao solicitacao){
		if(solicitacao.getStatusSolicitacao().getId() == 1 || solicitacao.getStatusSolicitacao().getId() == 2 || solicitacao.getStatusSolicitacao().getId() == 3){
			solicitacaoManagedBean.visualizarSolicitacao(solicitacao);
			sessao.setarObjetoSessao("voltarSolicitacaoPesquisa", true);
			return Navigation.VISUALIZARSOLICITACAO;
		} else {
			agendamentoManagedBean.visualizarAgendamento( agendamentoBO.retornaAgendamentoPorSolicitacao(solicitacao.getId()) );
			sessao.setarObjetoSessao("voltarAgendamentoPesquisa", true);
			return Navigation.VISUALIZARAGENDAMENTO;
		}
	}
	
	public void limparPesquisa(){
		if(listaSolicitacoes != null){
			listaSolicitacoes.clear();
		}
		pesquisaSolicitacao = new Solicitacao();
		statusSolicitacao = new Status();
	}
	
	
	//======================================== GETTERS AND SETTERS ===============================================
	public List<Status> listarStatusSolicitacao(){
		return listaStatusSolicitacao = solicitacaoBO.recuperaStatusSolicitacao();
	}

	public Solicitacao getPesquisaSolicitacao() {
		return pesquisaSolicitacao;
	}

	public void setPesquisaSolicitacao(Solicitacao pesquisaSolicitacao) {
		this.pesquisaSolicitacao = pesquisaSolicitacao;
	}

	public List<Status> getListaStatusSolicitacao() {
		return listaStatusSolicitacao;
	}

	public void setListaStatusSolicitacao(List<Status> listaStatusSolicitacao) {
		this.listaStatusSolicitacao = listaStatusSolicitacao;
	}

	public List<Solicitacao> getListaSolicitacoes() {
		return listaSolicitacoes;
	}

	public void setListaSolicitacoes(List<Solicitacao> listaSolicitacoes) {
		this.listaSolicitacoes = listaSolicitacoes;
	}

	public Status getStatusSolicitacao() {
		return statusSolicitacao;
	}

	public void setStatusSolicitacao(Status statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}
	
}
