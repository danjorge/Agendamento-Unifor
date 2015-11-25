package br.unifor.pin.agendamento.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Status;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.managers.MenuManagedBean;


@Repository
@Transactional(propagation = Propagation.REQUIRED)
@SuppressWarnings("unchecked")
public class SolicitacaoDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private MenuManagedBean menuManagedBean;
	
	
	public List<Solicitacao> retornaListaSolicitacoesPorCurso(Usuarios usuario){
		return (List<Solicitacao>) entityManager.createQuery("Select s "
														   + "from Solicitacao s "
														   + "inner join fetch s.usuario u "
														   + "inner join fetch u.cursos c " 
														   + "where c.id = :cursoId "
														   + "and s.statusSolicitacao.id = 1 "
														   + (usuario.getId() == 3 ? " and u.id = " + usuario.getId() : ""))
														   .setParameter("cursoId", usuario.getCursos().get(0).getId())
														   .getResultList();
	}
	
	public Solicitacao retornaSolicitacaoPorAssunto(Solicitacao solicitacao){
		return (Solicitacao) entityManager.createQuery("Select s "
													 + "from Solicitacao s "
													 + "where s.assunto like ('%' || :assunto || '%')")
													 .setParameter("assunto", solicitacao.getAssunto())
													 .getSingleResult();
													 
	}
	
	public List<Solicitacao> retornaListaRespostaSolicitacoesPorCurso(Usuarios usuario){
		return (List<Solicitacao>) entityManager.createQuery("Select s "
														   + "from Solicitacao s "
														   + "inner join fetch s.usuario u "
														   + "inner join fetch u.cursos c "
														   + "where c.id = :cursoId "
														   + "and s.statusSolicitacao.id = 2 "
														   + (usuario.getId() == 3 ? " and u.id = " + usuario.getId() : ""))
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
	
	public List<Solicitacao> retornaPesquisaSolicitacao(Solicitacao sol){
		return (List<Solicitacao>) entityManager.createQuery("Select s "
														   + "from Solicitacao s "
														   + "inner join fetch s.usuario u "
														   + "inner join fetch u.cursos c "
														   + "inner join fetch s.statusSolicitacao st "
														   + "where "
														   + (sol.getId() != null ? "s.id = " + sol.getId() : "")
														   + (sol.getId() != null ? "and upper(s.assunto) like upper('%' || :assunto || '%') " : "upper(s.assunto) like upper('%' || :assunto || '%') ") 
														   + "and :statusSolicitacaoIdAux is NULL or s.statusSolicitacao.id = :statusSolicitacaoId "
														   + "and c.id = " +menuManagedBean.getSeguranca().getUsuario().getCursos().get(0).getId() + " "
														   + "order by s.id")
														   .setParameter("assunto", sol.getAssunto())
														   .setParameter("statusSolicitacaoIdAux", sol.getStatusSolicitacao().getId())
														   .setParameter("statusSolicitacaoId", sol.getStatusSolicitacao().getId())
														   .getResultList();
	}
	
	public List<Status> retornaStatus(){
		return (List<Status>) entityManager.createQuery("Select s from Status s ")
										   .getResultList();
	}
	
	public void salvarSolicitacao(Solicitacao sol){
		entityManager.persist(sol);
	}
	
	public void atualizarSolicitacao(Solicitacao sol){
		entityManager.merge(sol);
	}
	
	public void removerSolicitacao(Solicitacao sol){
		entityManager.remove(sol);
	}
	
	public Solicitacao recuperaSolicitacaoPorId(Integer solicitacaoId){
		return (Solicitacao) entityManager.createQuery("Select s from Solicitacao s where s.id = :solicitacaoId")
										  .setParameter("solicitacaoId", solicitacaoId)
										  .getSingleResult();
	}
	
}
