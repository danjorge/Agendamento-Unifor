package br.unifor.pin.agendamento.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Usuarios;

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
	

	public List<Agendamento> retornaListaAgendamentoPorCurso(Usuarios usuario){
		return (List<Agendamento>) entityManager.createQuery("Select agend from Agendamento agend "
														   + "inner join fetch agend.solicitacao s "
														   + "inner join fetch s.usuario u "
														   + "inner join fetch u.cursos c "
														   + "where c.id = :cursoId "
														   + (usuario.getPapel().getId() == 3 ? " and u.id = " + usuario.getId() : ""))
														   .setParameter("cursoId", usuario.getCursos().get(0).getId())
														   .getResultList();
	}

	public Agendamento retornaAgendamentoPorSolicitacao(Integer solicitacaoId){
		return (Agendamento) entityManager.createQuery("Select agend from Agendamento agend "
													 + "where agend.solicitacao.id = :solicitacaoId")
													 .setParameter("solicitacaoId", solicitacaoId)
													 .getSingleResult();
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
