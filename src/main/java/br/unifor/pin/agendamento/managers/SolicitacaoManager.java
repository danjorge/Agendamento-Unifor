package br.unifor.pin.agendamento.managers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.business.SolicitacaoBO;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.filter.SessionContext;
import br.unifor.pin.agendamento.to.SegurancaTO;
import br.unifor.pin.agendamento.utils.MessagesUtils;
import br.unifor.pin.agendamento.utils.Navigation;

@RequestScoped
@ManagedBean(name="solicitacaoManagedBean")
@Component(value="solicitacaoManagedBean")
public class SolicitacaoManager {
	
	@Autowired
	private SolicitacaoBO solicitacaoBO;
	
	@Autowired
	private AgendamentoManager agendamentoManagedBean;
	
	@Autowired
	private SessionContext sessao;
	
	@Autowired
	private SegurancaTO seguranca;
	
	private Usuarios usuario;
	
	private Solicitacao solicitacao;
	private Solicitacao solicitacaoVisualizacao;
	private List<Solicitacao> listaSolicitacoes;
	private List<Solicitacao> listaRespostasSolicitacoes;
	
	//------------------------------------------------------------
	// VARIÁVEIS DE CAMPO
	// -----------------------------------------------------------
	private boolean exibeCampoResponderSolicitacao = false;
	private boolean escondeBtnResponderSolicitacao = false;
	private boolean exibeCampoAlunoResponderSolicitacao = false;
	private boolean escondeBtnRespostaSolicitacaoAluno = false; 
	private boolean stopPoll = false;
	// -----------------------------------------------------------
	
	private boolean edicao;
	
	@PostConstruct
	public void init(){
		solicitacao = new Solicitacao();
		edicao = false;
	}
	
	public void carregarListas(){
		listaSolicitacoes = solicitacaoBO.buscarSolcitacoesPorCurso();
		listaRespostasSolicitacoes = solicitacaoBO.buscarRespostasSolicitacoesPorCurso();
	}
	
	//-------------------------------------------------------------------------------------	
	// TELA CADASTRO SOLICITAÇÃO
	//-------------------------------------------------------------------------------------
	
	public String headerPanelPage(){
		
		edicao = ( (Boolean) sessao.recuperaObjetoSessao("edicao") == null ? false : (Boolean) sessao.recuperaObjetoSessao("edicao") );
		
		if(edicao){
			return "Edição de Solicitação";
		} else {
			setSolicitacao(new Solicitacao());
			return "Cadastro de Solicitação";
		}
	}
	
	public String salvarSolicitacao(){
		edicao = (Boolean) sessao.recuperaObjetoSessao("edicao");
		
		if(!edicao){
			if(solicitacao.getAssunto() != null && solicitacao.getDescricao() != null){
				solicitacaoBO.salvarSolicitacao(solicitacao);
				MessagesUtils.info("Solicitação salva com sucesso");
				listaSolicitacoes.add(solicitacao);
			} else {
				MessagesUtils.error("Algo errado aconteceu. Preencha todos os campos e tente novamente.");
				return Navigation.CADASTRARSOLICITACAO;
			}
		} else {
			solicitacaoBO.atualizarSolicitacao(solicitacao);
			sessao.limparSessao();
		}
		carregarListas();
		
		return Navigation.PRINCIPAL;
	}
	
	public void limparSolicitacao(){
		solicitacao.setAssunto("");
		solicitacao.setDescricao("");
	}
	
	public Usuarios retornaCoordenador(){
		if(sessao != null){
			usuario = solicitacaoBO.retornaCoordenadorPorCurso((Usuarios) sessao.recuperaObjetoSessao("usuario")); 
			if(usuario != null){
				return usuario;
			}
		}
		return null;
	}
	//-------------------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------------------
	// TELA PRINCIPAL
	//-------------------------------------------------------------------------------------
	public String visualizarSolicitacao(Solicitacao sol){
		solicitacaoVisualizacao = solicitacaoBO.recuperaSolicitacaoPorId(sol.getId());
		sessao.setarObjetoSessao("solicitacao", solicitacaoVisualizacao);
		sessao.setarObjetoSessao("solicitacaoBanco", sol);
		return Navigation.VISUALIZARSOLICITACAO;
	}
	
	public String editarSolicitacao(Solicitacao sol){
		boolean edicaoRespostaSolicitacao = (sol.getRespostaSolicitacao() != null);
		sessao.setarObjetoSessao("edicao", true);
		solicitacao = solicitacaoBO.recuperaSolicitacaoPorId(sol.getId());
		return (edicaoRespostaSolicitacao ? Navigation.EDITARRESPOSTASOLICITACAO : Navigation.CADASTRARSOLICITACAO);
	}	
	
	public void fecharSolicitacao(Solicitacao solicitacao){
		solicitacaoBO.fecharSolicitacao(solicitacao);
	}
	
	public void atualizarListas(){
		limparListas(null);
		carregarListas();
		if(seguranca.getUsuario().getNome() == null){
			stopPoll = true;
		}
	}
	
	private void limparListas(Integer size){
		if(size == null){
		  listaSolicitacoes.clear();
		  listaRespostasSolicitacoes.clear();
		}
	}
	//-------------------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------------------
	// TELA VISUALIZAR SOLICITACAO
	//-------------------------------------------------------------------------------------
	public void preparaCampoResponderSolicitacao(){
		setExibeCampoResponderSolicitacao(true);
		setEscondeBtnResponderSolicitacao(true);
	}
	
	public String voltar(){
		if(!edicao){
			setExibeCampoResponderSolicitacao(false);
			setEscondeBtnResponderSolicitacao(false);
		}
		
		boolean voltarParaPesquisa = ((Boolean) sessao.recuperaObjetoSessao("voltarSolicitacaoPesquisa") == null 
										? 
										false 
										: 
										(Boolean) sessao.recuperaObjetoSessao("voltarSolicitacaoPesquisa"));
		
		if(voltarParaPesquisa){
			return Navigation.PESQUISARSOLICITACAO;			
		} else {
			return Navigation.PRINCIPAL;			
		}
	}
	
	public String salvarRespostaSolicitacao(){
		solicitacaoBO.salvarRespostaSolicitacao(solicitacaoVisualizacao);
		setExibeCampoResponderSolicitacao(false);
		MessagesUtils.info("Resposta salva com sucesso.");
		listaRespostasSolicitacoes.add(solicitacaoVisualizacao);
		solicitacaoVisualizacao.setRespostaSolicitacao(null);
		carregarListas();
		
		return Navigation.PRINCIPAL;
	}
	
	public void preparaCampoRespostaSolicitacaoAluno(){
		setExibeCampoAlunoResponderSolicitacao(true);
		setEscondeBtnRespostaSolicitacaoAluno(true);
	}
	
	public String salvarRespostaAlunoSolicitacao(){
		Solicitacao solicitacao = (Solicitacao) sessao.recuperaObjetoSessao("solicitacaoBanco");
		for(Solicitacao sol : listaRespostasSolicitacoes){
			if(sol == solicitacao){
				if(sol.getSegundaRespostaSolicitacao() != null){
					MessagesUtils.error("Já existe uma resposta do Aluno para a solicitação.");
					setExibeCampoAlunoResponderSolicitacao(false);				
					return Navigation.VISUALIZARSOLICITACAO;
				} else {
					solicitacaoBO.salvarRespostaSolicitacao(solicitacaoVisualizacao);
					setExibeCampoAlunoResponderSolicitacao(false);
					MessagesUtils.info("Resposta salva com sucesso");
					solicitacaoVisualizacao.setSegundaRespostaSolicitacao(null);
					carregarListas();
					return Navigation.PRINCIPAL;				
				}
			}
		}
		return Navigation.PRINCIPAL;
	}
	
	public String preparaAgendamento(){
		agendamentoManagedBean.setInitialDate(null);
		agendamentoManagedBean.setViewOfSchedule("month");
		return Navigation.AGENDAR;
	}
	//-------------------------------------------------------------------------------------	
	
	
	

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

	public boolean isEscondeBtnRespostaSolicitacaoAluno() {
		return escondeBtnRespostaSolicitacaoAluno;
	}

	public void setEscondeBtnRespostaSolicitacaoAluno(
			boolean escondeBtnRespostaSolicitacaoAluno) {
		this.escondeBtnRespostaSolicitacaoAluno = escondeBtnRespostaSolicitacaoAluno;
	}

	public boolean isExibeCampoResponderSolicitacao() {
		return exibeCampoResponderSolicitacao;
	}

	public void setExibeCampoResponderSolicitacao(
			boolean exibeCampoResponderSolicitacao) {
		this.exibeCampoResponderSolicitacao = exibeCampoResponderSolicitacao;
	}

	public boolean isExibeCampoAlunoResponderSolicitacao() {
		return exibeCampoAlunoResponderSolicitacao;
	}

	public void setExibeCampoAlunoResponderSolicitacao(
			boolean exibeCampoAlunoResponderSolicitacao) {
		this.exibeCampoAlunoResponderSolicitacao = exibeCampoAlunoResponderSolicitacao;
	}

	public Solicitacao getSolicitacaoVisualizacao() {
		return solicitacaoVisualizacao;
	}

	public void setSolicitacaoVisualizacao(Solicitacao solicitacaoVisualizacao) {
		this.solicitacaoVisualizacao = solicitacaoVisualizacao;
	}

	public List<Solicitacao> getListaRespostasSolicitacoes() {
		return listaRespostasSolicitacoes;
	}

	public void setListaRespostasSolicitacoes(
			List<Solicitacao> listaRespostasSolicitacoes) {
		this.listaRespostasSolicitacoes = listaRespostasSolicitacoes;
	}

	public boolean isEdicao() {
		return edicao;
	}

	public void setEdicao(boolean edicao) {
		this.edicao = edicao;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public boolean isStopPoll() {
		return stopPoll;
	}

	public void setStopPoll(boolean stopPoll) {
		this.stopPoll = stopPoll;
	}
}
