package br.unifor.pin.agendamento.bussines;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.dao.AgendamentoDAO;
import br.unifor.pin.agendamento.entity.Agendamento;

@Service
public class AgendamentoBO {

	@Autowired
	private AgendamentoDAO agendamentoDAO;
	
	public List<Agendamento> buscarTodosAgendamentos(){
		return agendamentoDAO.retornaListaAgendamento();
	}
}
