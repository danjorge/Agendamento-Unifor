package br.unifor.pin.agendamento.business;

import java.util.List;

import org.primefaces.model.ScheduleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.dao.AgendamentoDAO;
import br.unifor.pin.agendamento.dao.SolicitacaoDAO;
import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Status;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.filter.SessionContext;

@Service
@Controller("agendamentoBO")
public class AgendamentoBO {

	@Autowired
	private AgendamentoDAO agendamentoDAO;
	
	@Autowired
	private SolicitacaoDAO solicitacaoDAO;
	
	@Autowired
	private SessionContext sessao;
	
	public List<Agendamento> buscarTodosAgendamentos(){
		return agendamentoDAO.retornaListaAgendamento();
	}
	
	public void salvarAgendamento(ScheduleEvent event){
		//guarda as informações do evento em tela no banco
		Agendamento agendamento = new Agendamento();
		agendamento.setTitulo(event.getTitle());
		agendamento.setDscAgendamentoEvent(event.getDescription());
		agendamento.setDataInicio(event.getStartDate());
		agendamento.setDataFim(event.getEndDate());
		agendamento.setAllDay(event.isAllDay());
		
		//guarda as informações da solicitação e do evento em tela no banco
		Solicitacao sol = (Solicitacao) sessao.recuperaObjetoSessao("solicitacao");
		sol.getStatusSolicitacao().setId(4);
		agendamento.setSolicitacao(sol);
		agendamento.setStatusAgendamento(new Status());
		agendamento.getStatusAgendamento().setId(4);
		
		agendamentoDAO.salvarAgendamento(agendamento);
		
		//atualiza a solicitacao
		solicitacaoDAO.atualizarSolicitacao(sol);
	}
	
	public void atualizarAgendamento(Agendamento agendamento){
		agendamentoDAO.atualizarAgendamento(agendamento);
	}
	
	public Agendamento retornaAgendamentoPorSolicitacao(Integer solicitacaoId){
		return agendamentoDAO.retornaAgendamentoPorSolicitacao(solicitacaoId);
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
