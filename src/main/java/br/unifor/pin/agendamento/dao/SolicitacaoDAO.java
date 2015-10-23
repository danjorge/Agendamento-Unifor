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
	public List<Solicitacao> retornaListaSolicitacoesPorCurso(Usuarios usuario){
		return (List<Solicitacao>) entityManager.createQuery("Select s "
														   + "from Solicitacao s "
														   + "inner join fetch s.usuario u "
														   + "inner join fetch u.cursos c " 
														   + "where c.id = :cursoId "
														   + "and s.statusSolicitacao.id = 1")
												.setParameter("cursoId", usuario.getCursos().get(0).getId())
								                .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Solicitacao> retornaListaRespostaSolicitacoesPorCurso(Usuarios usuario){
		return (List<Solicitacao>) entityManager.createQuery("Select s "
														   + "from Solicitacao s "
														   + "inner join fetch s.usuario u "
														   + "inner join fetch u.cursos c "
														   + "where c.id = :cursoId "
														   + "and s.statusSolicitacao.id = 6")
												.setParameter("cursoId", usuario.getCursos().get(0).getId())
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
	
	public void atualizarSolicitacao(Solicitacao sol){
		entityManager.merge(sol);
	}
	
	public Solicitacao recuperaSolicitacaoPorId(Integer solicitacaoId){
		return (Solicitacao) entityManager.createQuery("Select s from Solicitacao s where s.id = :solicitacaoId")
										  .setParameter("solicitacaoId", solicitacaoId)
										  .getSingleResult();
	}
	
}
