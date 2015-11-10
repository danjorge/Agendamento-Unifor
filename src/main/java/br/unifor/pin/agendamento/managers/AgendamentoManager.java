package br.unifor.pin.agendamento.managers;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.bussiness.AgendamentoBO;
import br.unifor.pin.agendamento.bussiness.SolicitacaoBO;
import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Status;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.filter.SessionContext;
import br.unifor.pin.agendamento.utils.Navigation;


@ViewScoped
@ManagedBean(name="agendamentoManagedBean")
@Component(value="agendamentoManagedBean")
public class AgendamentoManager {
	
	@Autowired
	private AgendamentoBO agendamentoBO;
	
	@Autowired
	private SolicitacaoBO solicitacaoBO;
	
	@Autowired
	private SolicitacaoManager solicitacaoManagedBean;
	
	@Autowired
	private SessionContext sessao;
	
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private Solicitacao sol;
	private Agendamento agendamento;
	private Agendamento agendamentoVisualizacao;
	
	private List<Agendamento> listaAgendamento;
	
	@PostConstruct
	public void init(){
		eventModel = new DefaultScheduleModel();
		carregarListas();
	}
	
	public void carregarListas(){
		listaAgendamento = agendamentoBO.buscarTodosAgendamentos();
		
		for(Agendamento event : listaAgendamento){
			this.event = (ScheduleEvent) event;
			eventModel.addEvent(this.event);
		}
	}	
		
	public void addEvent(){
		//guarda as informações do evento em tela no banco
		agendamento = new Agendamento();
		agendamento.setTitulo(event.getTitle());
		agendamento.setDscAgendamentoEvent(event.getDescription());
		agendamento.setDataInicio(event.getStartDate());
		agendamento.setDataFim(event.getEndDate());
		agendamento.setAllDay(event.isAllDay());
		
		//guarda as informações da solicitação e do evento em tela no banco
		sol.getStatusSolicitacao().setId(4);
		agendamento.setSolicitacao(sol);
		agendamento.setStatusAgendamento(new Status());
		agendamento.getStatusAgendamento().setId(4);
		
		//salva o agendamento
		agendamentoBO.salvarAgendamento(agendamento);
		
		//atualiza a solicitacao
		solicitacaoBO.atualizarSolicitacao(sol);
		
		//adiciona o agendamento à lista de agendamentos
		listaAgendamento.add(agendamento);
		
		if(event.getId() == null) {
			eventModel.addEvent(event);
		} else {
			eventModel.updateEvent(event);
		}
		
		event = new DefaultScheduleEvent();
		
		solicitacaoManagedBean.carregaListas();
	}
	
	public String visualizarAgendamento(Agendamento agendamento){
		agendamentoVisualizacao = agendamentoBO.retornaAgendamentoPorId(agendamento.getIdEvent());
		return Navigation.VISUALIZARAGENDAMENTO;
	}
	
	public void fecharAgendamento(Agendamento agendamento){
		agendamentoBO.fecharAgendamento(agendamento);
	}
	
	public void onEventSelect(SelectEvent selectEvent){
		event = (ScheduleEvent) selectEvent.getObject();
	}
	
	public void onDateSelect(SelectEvent selectEvent){
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
		sol = (Solicitacao) sessao.recuperaObjetoSessao("solicitacao");
	}
	
	public Usuarios retornaCoordenador(){
		return agendamentoBO.retornaCoordenadorPorCurso((Usuarios) sessao.recuperaObjetoSessao("usuario"));
	}
	
	public String voltarPrincipal(){
		return Navigation.PRINCIPAL;
	}
	
	//-------------------------------------------------GETTERS AND SETTERS -----------------------------------------------------
	public List<Agendamento> getListaAgendamento() {
		return listaAgendamento;
	}

	public void setListaAgendamento(List<Agendamento> listaAgendamento) {
		this.listaAgendamento = listaAgendamento;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public Solicitacao getSol() {
		return sol;
	}

	public void setSol(Solicitacao sol) {
		this.sol = sol;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public Agendamento getAgendamentoVisualizacao() {
		return agendamentoVisualizacao;
	}

	public void setAgendamentoVisualizacao(Agendamento agendamentoVisualizacao) {
		this.agendamentoVisualizacao = agendamentoVisualizacao;
	}
}
