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
import br.unifor.pin.agendamento.to.SegurancaTO;

@Service
@Controller("agendamentoBO")
public class AgendamentoBO {

	@Autowired
	private AgendamentoDAO agendamentoDAO;
	
	@Autowired
	private SolicitacaoDAO solicitacaoDAO;
	
	@Autowired
	private SessionContext sessao;
	
	@Autowired
	private SegurancaTO seguranca;
	
	public List<Agendamento> buscarAgendamentosPorCurso(){
		return agendamentoDAO.retornaListaAgendamentoPorCurso(seguranca.getUsuario());
	}
	
	public Agendamento salvarAgendamento(ScheduleEvent event){
		//guarda as informações do evento em tela no banco
		Agendamento agendamento = new Agendamento();
		agendamento.setTitle(event.getTitle());
		agendamento.setDescription(event.getDescription());
		agendamento.setStartDate(event.getStartDate());
		agendamento.setEndDate(event.getEndDate());
		agendamento.setAllDay(event.isAllDay());
		
		//guarda as informações da solicitação e do evento em tela no banco
		Solicitacao sol = (Solicitacao) sessao.recuperaObjetoSessao("solicitacao");
		sol.getStatusSolicitacao().setId(5); // 5 - ID de AGENDAMENTO CADASTRADO
		agendamento.setSolicitacao(sol);
		agendamento.setStatusAgendamento(new Status());
		agendamento.getStatusAgendamento().setId(5); // 5 - ID de AGENDAMENTO CADASTRADO
		
		//atualiza a solicitacao
		solicitacaoDAO.atualizarSolicitacao(sol);
		
		agendamentoDAO.salvarAgendamento(agendamento);
		
		return agendamento;
		
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
		agendamento.getStatusAgendamento().setId(6);
		agendamentoDAO.atualizarAgendamento(agendamento);
	}
	
	public Usuarios retornaCoordenadorPorCurso(Usuarios usuario){
		return solicitacaoDAO.retornaCoordenadorPorCurso(usuario);
	}
}
