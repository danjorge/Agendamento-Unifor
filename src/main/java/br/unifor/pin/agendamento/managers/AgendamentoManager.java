package br.unifor.pin.agendamento.managers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.bussiness.AgendamentoBO;
import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.filter.SessionContext;


@RequestScoped
@ManagedBean(name="agendamentoManagedBean")
@Component(value="agendamentoManagedBean")
public class AgendamentoManager {
	
	@Autowired
	private AgendamentoBO agendamentoBO;
	
	@Autowired
	private SessionContext sessao;
	
	private ScheduleModel eventModel;
	
	private List<Agendamento> listaAgendamento;
	
	@PostConstruct
	public void init(){
		listaAgendamento = agendamentoBO.buscarTodosAgendamentos();
		eventModel = new DefaultScheduleModel();
		eventModel.addEvent(new DefaultScheduleEvent("Evento Teste", previousDay8Pm(), nextDay9Am()));
	}
	
	private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);
         
        return t.getTime();
    }
	
	private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
	
	private Date nextDay9Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 9);
         
        return t.getTime();
    }
	
	public Usuarios retornaCoordenador(){
		return agendamentoBO.retornaCoordenadorPorCurso((Usuarios) sessao.recuperaObjetoSessao("usuario"));
	}

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

}
