package br.unifor.pin.agendamento.bussines;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.dao.SolicitacaoDAO;
import br.unifor.pin.agendamento.entity.Solicitacao;


@Service
public class SolicitacaoBO {
	
	@Autowired
	private SolicitacaoDAO solicitacaoDAO;
	
	public List<Solicitacao> buscarTodasSolcitacoes(){
		return solicitacaoDAO.retornaListaSolicitacoes();
	}

}
