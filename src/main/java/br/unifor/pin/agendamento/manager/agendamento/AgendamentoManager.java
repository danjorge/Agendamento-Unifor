package br.unifor.pin.agendamento.manager.agendamento;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.bussines.AgendamentoBO;
import br.unifor.pin.agendamento.entity.Agendamento;


@RequestScoped
@ManagedBean(name="agendamentoManagedBean")
@Component(value="agendamentoManagedBean")
public class AgendamentoManager {
	
	@Autowired
	private AgendamentoBO agendamentoBO;
	
	private List<Agendamento> listaAgendamento;
	
	@PostConstruct
	public void init(){
		listaAgendamento = agendamentoBO.buscarTodosAgendamentos(); 
	}

	public List<Agendamento> getListaAgendamento() {
		return listaAgendamento;
	}

	public void setListaAgendamento(List<Agendamento> listaAgendamento) {
		this.listaAgendamento = listaAgendamento;
	}

}
