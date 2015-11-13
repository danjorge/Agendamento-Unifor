package br.unifor.pin.agendamento.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.business.SolicitacaoBO;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Status;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.exceptions.DAOException;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
@Transactional(propagation=Propagation.REQUIRED)
public class SolicitacaoTest {
	
	@Autowired
	private SolicitacaoDAO solicitacaoDAO;
	
	@Autowired
	private SolicitacaoBO solicitacaoBO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Test
	public void testeSalvarSolicitacao() throws DAOException{
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setAssunto("Assunto Teste Novo");
		solicitacao.setDescricao("Descrição teste");
		solicitacao.setRespostaSolicitacao("Resposta Solicitacao teste");
		solicitacao.setStatusSolicitacao(new Status());
		solicitacao.getStatusSolicitacao().setId(1);
		
		Usuarios usuario = usuarioDAO.buscaPorId(1);
		solicitacao.setUsuario(usuario);
		
		solicitacaoDAO.salvarSolicitacao(solicitacao);
		
		Solicitacao solicitacaoRetorno = solicitacaoDAO.retornaSolicitacaoPorAssunto(solicitacao);
		Assert.assertNotNull(solicitacaoRetorno);
		
		solicitacaoDAO.removerSolicitacao(solicitacaoRetorno);
		
	}
	
	@Test
	public void atualizarSolicitacao(){
		final String assunto = "Assunto Teste";
		Solicitacao solicitacao = solicitacaoDAO.recuperaSolicitacaoPorId(49);
		
		solicitacao.setAssunto(assunto);
		
		solicitacaoDAO.atualizarSolicitacao(solicitacao);
		
	}
	

}
