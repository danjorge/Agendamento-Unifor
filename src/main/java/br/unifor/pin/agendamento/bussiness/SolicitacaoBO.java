package br.unifor.pin.agendamento.bussiness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.dao.SolicitacaoDAO;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Status;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.filter.SessionContext;
import br.unifor.pin.agendamento.to.SegurancaTO;


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
	
	public List<Solicitacao> buscarRespostasSolicitacoesPorCurso(){
		return solicitacaoDAO.retornaListaRespostaSolicitacoesPorCurso(seguranca.getUsuario());
	}
	
	public Usuarios retornaCoordenadorPorCurso(Usuarios usuario){
		return solicitacaoDAO.retornaCoordenadorPorCurso(usuario);
	}
	
	public void salvarSolicitacao(Solicitacao sol){
		sol.setUsuario((Usuarios) sessao.recuperaObjetoSessao("usuario"));
		sol.setStatusSolicitacao(new Status());
		sol.getStatusSolicitacao().setId(1);
		solicitacaoDAO.salvarSolicitacao(sol);
	}
	
	public void atualizarSolicitacao(Solicitacao sol){
		solicitacaoDAO.atualizarSolicitacao(sol);
	}
	
	public void excluirSolicitacao(Solicitacao sol){
		solicitacaoDAO.excluirSolicitacao(sol);
	}
	
	public void salvarRespostaSolicitacao(Solicitacao sol){
		sol.getStatusSolicitacao().setId(6);
		solicitacaoDAO.atualizarSolicitacao(sol);
	}
	
	public List<Solicitacao> pesquisarSolicitacao(Solicitacao sol, Status statusSolicitacao){
		sol.setStatusSolicitacao(new Status());
		sol.getStatusSolicitacao().setId(statusSolicitacao != null ? statusSolicitacao.getId() : null);
		return solicitacaoDAO.retornaPesquisaSolicitacao(sol);
	}
	
	public Solicitacao recuperaSolicitacaoPorId(Integer solicitacaoId){
		return solicitacaoDAO.recuperaSolicitacaoPorId(solicitacaoId);
	}
	
	public List<Status> recuperaStatusSolicitacao(){
		return solicitacaoDAO.retornaStatusSolicitacao();
	}

}
