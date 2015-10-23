package br.unifor.pin.agendamento.bussiness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.dao.AgendamentoDAO;
import br.unifor.pin.agendamento.dao.SolicitacaoDAO;
import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Usuarios;

@Service
public class AgendamentoBO {

	@Autowired
	private AgendamentoDAO agendamentoDAO;
	
	@Autowired
	private SolicitacaoDAO solicitacaoDAO;
	
	public List<Agendamento> buscarTodosAgendamentos(){
		return agendamentoDAO.retornaListaAgendamento();
	}
	
	public Usuarios retornaCoordenadorPorCurso(Usuarios usuario){
		return solicitacaoDAO.retornaCoordenadorPorCurso(usuario);
	}
}
