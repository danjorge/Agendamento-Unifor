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
public class AgendamentoDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Agendamento> retornaListaAgendamento(){
		return (List<Agendamento>) entityManager.createQuery("Select agend from Agendamento agend")
								   				.getResultList();
	}
}
