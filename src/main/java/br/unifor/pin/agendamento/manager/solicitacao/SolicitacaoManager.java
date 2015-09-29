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
import br.unifor.pin.agendamento.utils.MessagesUtils;
import br.unifor.pin.agendamento.utils.Navigation;
import br.unifor.pin.agendamento.utils.SessionContext;

@RequestScoped
@ManagedBean(name="solicitacaoManagedBean")
@Component(value="solicitacaoManagedBean")
public class SolicitacaoManager {
	
	@Autowired
	private SolicitacaoBO solicitacaoBO;
	
	@Autowired
	private SessionContext sessao;
	
	private Solicitacao solicitacao;
	
	private List<Solicitacao> listaSolicitacoes;
	
	private MessagesUtils mensagem;
	
	@PostConstruct
	public void init(){
		listaSolicitacoes = solicitacaoBO.buscarTodasSolcitacoes();
		solicitacao = new Solicitacao();
	}
	
	@SuppressWarnings("static-access")
	public String salvarSolicitacao(){
		if(solicitacao.getAssunto() != null && solicitacao.getDescricao() != null){
			solicitacaoBO.salvarSolicitacao(solicitacao);
			mensagem.info("Solicitação salva com sucesso");
			listaSolicitacoes.add(solicitacao);
		} else {
			mensagem.error("Algo errado aconteceu.");
		}
		
		return Navigation.PRINCIPAL;
	}
	
	public String visualizarSolicitacao(){
		return "";
	}
	
	public Usuarios retornaCoordenador(){		
		return solicitacaoBO.retornaCoordenadorPorCurso((Usuarios) sessao.recuperaObjetoSessao("usuario"));
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

}
