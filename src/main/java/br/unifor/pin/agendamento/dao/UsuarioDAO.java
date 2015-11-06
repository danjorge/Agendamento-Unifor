package br.unifor.pin.agendamento.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.entity.Cursos;
import br.unifor.pin.agendamento.entity.Papeis;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.exceptions.DAOException;

/**
 * @author patrick.cunha
 * 
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
@SuppressWarnings("unchecked")
public class UsuarioDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void salvar(Usuarios usuario) {
		entityManager.persist(usuario);
	}
	
	public void atualizar(Usuarios usuario){
		entityManager.merge(usuario);
	}
	
	public Usuarios buscarPorMatricula(String matricula){
		try {
			return (Usuarios) entityManager.createQuery("Select u from Usuarios u where u.matricula = :matricula")
														.setParameter("matricula", matricula)
														.getSingleResult();
		} catch(NoResultException e){
			return null;
		}
	}
	
	public Usuarios buscarPorMatriculaSenha(String matricula, String senha){
		try {
		return (Usuarios) entityManager.createQuery("Select u from Usuarios u where u.matricula = :matricula and u.senha = :senha")
													.setParameter("matricula", matricula)
													.setParameter("senha", senha)
													.getSingleResult();
		} catch(NoResultException e){
			return null;
		}
	}
	
	public List<Cursos> retornaTodosCursos(){
		return (List<Cursos>) entityManager.createQuery("Select c from Cursos c")
										   .getResultList();
	}
	
	public Cursos retornaCursoADS(){
		return (Cursos) entityManager.createQuery("Select c from Cursos c where c.id = 1")
				.getSingleResult();
	}
	

	public List<Usuarios> listarPorNome(String nome) {
		return (List<Usuarios>) entityManager.createQuery("Select u from Usuarios u where u.nome like ('%' || :nome || '%')")
													.setParameter("nome", nome)
													.getResultList();
	}
	
	public Usuarios buscaPorId(Integer id) throws DAOException {
		String jpql = "select u from Usuarios u where u.id = :id";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("id", id);
		
		try {
			return (Usuarios) query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} 
		
	}
	
	public List<Papeis> buscaTodosPapeis(){
		return (List<Papeis>) entityManager.createQuery("Select p from Papeis p")
										   .getResultList();
	}
	
	public void excluir(Usuarios usuario) {
		entityManager.remove(usuario);
	}
}
