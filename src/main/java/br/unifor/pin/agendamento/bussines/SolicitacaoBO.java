package br.unifor.pin.agendamento.bussines;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.dao.SolicitacaoDAO;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.utils.SessionContext;


@Service
public class SolicitacaoBO {
	
	@Autowired
	private SolicitacaoDAO solicitacaoDAO;
	@Autowired
	private SessionContext sessao;
	
	public List<Solicitacao> buscarTodasSolcitacoes(){
		return solicitacaoDAO.retornaListaSolicitacoes();
	}
	
	public Usuarios retornaCoordenadorPorCurso(Usuarios usuario){
		return solicitacaoDAO.retornaCoordenadorPorCurso(usuario);
	}
	
	public void salvarSolicitacao(Solicitacao sol){
		sol.setUsuario((Usuarios) sessao.recuperaObjetoSessao("usuario"));
		sol.getStatusSolicitacao().setId(1);
		solicitacaoDAO.salvarSolicitacao(sol);
	}

}
