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

import br.unifor.pin.agendamento.business.AgendamentoBO;
import br.unifor.pin.agendamento.business.SolicitacaoBO;
import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.filter.SessionContext;
import br.unifor.pin.agendamento.utils.MessagesUtils;
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
	
	private String viewOfSchedule;
	
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
		
		boolean edicao = (Boolean) sessao.recuperaObjetoSessao("edicao");
		
		if(!edicao){
			//verifica se a data fim do dialog é anterior a data início
			// ou
			//se a data do agendamento é anterior a atual
			if (event.getStartDate().after(event.getEndDate()) || event.getStartDate().before(new Date())){
				MessagesUtils.error("Data final da janela é anterior a data inicial ou a data que você escolheu para o agendamento é anterior a data atual.");
			} else {
				//salva o agendamento
				agendamentoBO.salvarAgendamento(event);
				
				//adiciona o agendamento à lista de agendamentos
				listaAgendamento.add(agendamento);	
			}
			
		} else {
			
			agendamentoBO.atualizarAgendamento(agendamento);
			
		}
		
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
		setInitialDate(agendamento);
		return Navigation.VISUALIZARAGENDAMENTO;
	}
	
	public String irParaAgendamentos(){
		setViewOfSchedule("agendaDay");
		return Navigation.AGENDAR;
	}
	
	public void fecharAgendamento(Agendamento agendamento){
		agendamentoBO.fecharAgendamento(agendamento);
	}
	
	public void onEventSelect(SelectEvent selectEvent){
		event = (ScheduleEvent) selectEvent.getObject();
		agendamento = (Agendamento) event;
		sessao.setarObjetoSessao("edicao", true);
		
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
	
	public Date getInitialDate(){
		if(agendamento != null){
			return agendamento.getDataInicio();
		}
		return null;
	}
	
	public void setInitialDate(Agendamento agendamento){
		this.agendamento = agendamento;
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

	public String getViewOfSchedule() {
		return viewOfSchedule;
	}

	public void setViewOfSchedule(String viewOfSchedule) {
		this.viewOfSchedule = viewOfSchedule;
	}
}
