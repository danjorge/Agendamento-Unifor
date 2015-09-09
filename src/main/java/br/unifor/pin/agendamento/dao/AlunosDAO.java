package br.unifor.pin.agendamento.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.entity.Alunos;

@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class AlunosDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void salvar(Alunos aluno) {
		entityManager.persist(aluno);
	}
	
	public void atualizar(Alunos aluno){
		entityManager.merge(aluno);
	}
	
	public void excluir(Alunos aluno){
		entityManager.remove(aluno);
	}
	
	public Alunos buscarPorId(Long id){
		return entityManager.find(Alunos.class, id);
	}
	
	public Alunos buscarPorNome(String nome) {
		String jpql = "select a from Alunos a where a.nome = :nome";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("nome", nome);
		
		return (Alunos) query.getSingleResult();
	}

}
