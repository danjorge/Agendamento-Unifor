package br.unifor.pin.agendamento.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.entity.Agendamento;

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
	
	public Agendamento retornaAgendamentoPorId(Integer agendamentoId){
		return (Agendamento) entityManager.find(Agendamento.class, agendamentoId);
	}
	
	public void salvarAgendamento(Agendamento agendamento){
		entityManager.persist(agendamento);
	}
	
	public void atualizarAgendamento(Agendamento agendamento){
		entityManager.merge(agendamento);
	}
}
