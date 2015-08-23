package br.unifor.pin.saa.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.saa.entity.Instituicoes;

@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class InstituicoesDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void salvar(Instituicoes instituicao) {
		entityManager.persist(instituicao);
	}
	
	public void atualizar(Instituicoes instituicao){
		entityManager.merge(instituicao);
	}
	
	public void excluir(Instituicoes instituicao){
		entityManager.remove(instituicao);
	}
	
	public Instituicoes buscarPorId(Long id){
		return entityManager.find(Instituicoes.class, id);
	}
	
	public Instituicoes buscarPorNome(String nome) {
		String jpql = "select i from Instituicoes i where i.nome = :nome";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("nome", nome);
		
		return (Instituicoes) query.getSingleResult();
	}

}
