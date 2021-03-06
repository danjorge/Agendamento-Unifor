package br.unifor.pin.agendamento.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.dao.SolicitacaoDAO;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Status;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.filter.SessionContext;
import br.unifor.pin.agendamento.to.SegurancaTO;

@Component
@Service
public class SolicitacaoBO {
	
	@Autowired
	private SolicitacaoDAO solicitacaoDAO;
	@Autowired
	private SessionContext sessao;
	@Autowired
	private SegurancaTO seguranca;
	
	public List<Solicitacao> buscarSolcitacoesPorCurso(){
		return solicitacaoDAO.retornaListaSolicitacoesPorCurso(seguranca.getUsuario());
	}
	
	public List<Solicitacao> buscarTodasSolicitacoesPorCurso(){
		return solicitacaoDAO.retornaListaTodasSolicitacoesPorCurso(seguranca.getUsuario());
	}
	
	public List<Solicitacao> buscarRespostasSolicitacoesPorCurso(){
		return solicitacaoDAO.retornaListaRespostaSolicitacoesPorCurso(seguranca.getUsuario());
	}
	
	public Usuarios retornaCoordenadorPorCurso(Usuarios usuario){
		return solicitacaoDAO.retornaCoordenadorPorCurso(usuario);
	}
	
	public void salvarSolicitacao(Solicitacao sol){
		sol.setUsuario((Usuarios) sessao.recuperaObjetoSessao("usuario"));
		sol.setStatusSolicitacao(new Status());
		sol.getStatusSolicitacao().setId(1);// 1 - ID de SOLICITAÇÃO CADASTRADA
		solicitacaoDAO.salvarSolicitacao(sol);
	}
	
	public void atualizarSolicitacao(Solicitacao sol){
		solicitacaoDAO.atualizarSolicitacao(sol);
	}
	
	public Solicitacao retornaSolicitacaoPorId(Integer solicitacaoId){
		return (Solicitacao) solicitacaoDAO.retornaSolicitacaoPorId(solicitacaoId);
	}
	
	public void fecharSolicitacao(Solicitacao sol){
		sol.getStatusSolicitacao().setId(4);// 4 - ID de SOLICITAÇÃO FECHADA
		solicitacaoDAO.atualizarSolicitacao(sol);
	}
	
	public void salvarRespostaSolicitacao(Solicitacao sol){
		sol.getStatusSolicitacao().setId(seguranca.getUsuario().getPapel().getId() == 1 ? 2 : 3);// 2 - ID de SOLICITAÇÃO RESPONDIDA PELO COORDENADOR / 3 - SOLICITAÇÃO RESPONDIDA PELO ALUNO
		solicitacaoDAO.atualizarSolicitacao(sol);
	}
	
	public List<Solicitacao> pesquisarSolicitacao(Solicitacao sol, Status statusSolicitacao){
		//sol.setId(sol.getId() == 0 ? null : sol.getId());
		sol.setStatusSolicitacao(new Status());
		sol.getStatusSolicitacao().setId(statusSolicitacao != null ? statusSolicitacao.getId() : null);
		return solicitacaoDAO.retornaPesquisaSolicitacao(sol);
	}
	
	public Solicitacao recuperaSolicitacaoPorId(Integer solicitacaoId){
		return solicitacaoDAO.recuperaSolicitacaoPorId(solicitacaoId);
	}
	
	public List<Status> recuperaStatusSolicitacao(){
		return solicitacaoDAO.retornaStatus();
	}

}
