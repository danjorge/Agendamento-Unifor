package br.unifor.pin.agendamento.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.entity.Aluno;

@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class AlunosDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void salvar(Aluno aluno) {
		entityManager.persist(aluno);
	}
	
	public void atualizar(Aluno aluno){
		entityManager.merge(aluno);
	}
	
	public void excluir(Aluno aluno){
		entityManager.remove(aluno);
	}
	
	public Aluno buscarPorId(Long id){
		return entityManager.find(Aluno.class, id);
	}
	
	public Aluno buscarPorNome(String nome) {
		String jpql = "select a from Alunos a where a.nome = :nome";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("nome", nome);
		
		return (Aluno) query.getSingleResult();
	}

}
