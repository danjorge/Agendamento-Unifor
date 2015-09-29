package br.unifor.pin.agendamento.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Usuarios;


@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class SolicitacaoDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	public List<Solicitacao> retornaListaSolicitacoes(){
		return (List<Solicitacao>) entityManager.createQuery("Select s from Solicitacao s")
								                .getResultList();
	}
	
	
	public Usuarios retornaCoordenadorPorCurso(Usuarios usuario){
		return (Usuarios) entityManager.createQuery("Select u "
									   			  + "from Usuarios u "
									   			  + "inner join fetch u.cursos c "
									   			  + "inner join fetch u.papel p "
									   			  + "where p.id = 1 "
									   			  + "and c.id = :cursoId ")
									   			  .setParameter("cursoId", usuario.getCursos().get(0).getId())
									   			  .getSingleResult();
	}
	
	public void salvarSolicitacao(Solicitacao sol){
		entityManager.persist(sol);
	}
}
