package br.unifor.pin.agendamento.bussiness;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.dao.AgendamentoDAO;
import br.unifor.pin.agendamento.dao.SolicitacaoDAO;
import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.utils.MessagesUtils;

@Service
public class AgendamentoBO {

	@Autowired
	private AgendamentoDAO agendamentoDAO;
	
	@Autowired
	private SolicitacaoDAO solicitacaoDAO;
	
	public List<Agendamento> buscarTodosAgendamentos(){
		return agendamentoDAO.retornaListaAgendamento();
	}
	
	public void salvarAgendamento(Agendamento agendamento){
		
		if (agendamento.getDataInicio().after(agendamento.getDataFim()) || agendamento.getDataInicio().after(new Date())){
			MessagesUtils.error("Verificar datas.");
			return;
		}
		agendamentoDAO.salvarAgendamento(agendamento);
	}
	
	public Agendamento retornaAgendamentoPorId(Integer agendamentoId){
		return agendamentoDAO.retornaAgendamentoPorId(agendamentoId);
	}
	
	public void fecharAgendamento(Agendamento agendamento){
		agendamento.getStatusAgendamento().setId(5);
		agendamentoDAO.atualizarAgendamento(agendamento);
	}
	
	public Usuarios retornaCoordenadorPorCurso(Usuarios usuario){
		return solicitacaoDAO.retornaCoordenadorPorCurso(usuario);
	}
}
