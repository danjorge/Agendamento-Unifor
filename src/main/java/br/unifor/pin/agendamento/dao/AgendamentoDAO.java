package br.unifor.pin.agendamento.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.AgendamentoEvent;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@SuppressWarnings("unchecked")
public class AgendamentoDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Agendamento> retornaListaAgendamento(){
		return (List<Agendamento>) entityManager.createQuery("Select agend from Agendamento agend")
								   				.getResultList();
	}
	
	public List<AgendamentoEvent> retornaListaAgendamentosEvent(){
		return (List<AgendamentoEvent>) entityManager.createQuery("Select agendEvent from AgendamentoEvent agendEvent")
													 .getResultList();
	}
	
	
	public void salvarAgendamento(Agendamento agendamento){
		entityManager.persist(agendamento);
	}
	
	public void salvarAgendamentoEvent(AgendamentoEvent agendamentoEvent){
		entityManager.persist(agendamentoEvent);
	}
}
