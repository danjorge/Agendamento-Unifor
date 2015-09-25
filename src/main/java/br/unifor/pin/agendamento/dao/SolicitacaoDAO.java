package br.unifor.pin.agendamento.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.entity.Solicitacao;


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
}
