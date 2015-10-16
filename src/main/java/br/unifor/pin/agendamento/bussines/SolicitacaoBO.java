package br.unifor.pin.agendamento.bussines;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.dao.SolicitacaoDAO;
import br.unifor.pin.agendamento.entity.Solicitacao;
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
	private SegurancaTO segurança;
	
	public List<Solicitacao> buscarSolcitacoesPorCurso(){
		return solicitacaoDAO.retornaListaSolicitacoesPorCurso(segurança.getUsuario());
	}
	
	public Usuarios retornaCoordenadorPorCurso(Usuarios usuario){
		return solicitacaoDAO.retornaCoordenadorPorCurso(usuario);
	}
	
	public void salvarSolicitacao(Solicitacao sol){
		sol.setUsuario((Usuarios) sessao.recuperaObjetoSessao("usuario"));
		sol.getStatusSolicitacao().setId(1);
		solicitacaoDAO.salvarSolicitacao(sol);
	}
	
	public Solicitacao recuperaSolicitacaoPorId(Integer solicitacaoId){
		return solicitacaoDAO.recuperaSolicitacaoPorId(solicitacaoId);
	}

}
