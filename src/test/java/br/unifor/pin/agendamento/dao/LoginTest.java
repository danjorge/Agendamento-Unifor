package br.unifor.pin.agendamento.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.business.UsuarioBO;
import br.unifor.pin.agendamento.entity.Cursos;
import br.unifor.pin.agendamento.entity.Papeis;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.utils.Encripta;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
@Transactional(propagation=Propagation.REQUIRED)
public class LoginTest {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private UsuarioBO usuarioBO;
	
	
	@Test
	public void testeSalvar(){
		final String matricula = "11111111";
		final String senha = "12345678";
		final String nome = "DJ";
		Usuarios usuario = new Usuarios();
		usuario.setMatricula(matricula);
		usuario.setSenha(senha);
		usuario.setNome(nome);
		usuario.setAtivo(true);
		usuario.setPrimeiroAcesso(false);
		usuario.setPapel(new Papeis());
		usuario.getPapel().setId(1);
		
		Cursos curso =  usuarioDAO.retornaCursoADS();		
		
		usuario.setCursos(new ArrayList<Cursos>());
		usuario.getCursos().add(curso);
		
		usuarioDAO.salvar(usuario);
		Usuarios usuarioRetorno = usuarioBO.buscarUsuarioPorMatricula(matricula);
		Assert.assertNotNull(usuarioRetorno);
		
		usuarioDAO.excluir(usuarioRetorno);
	}
	
	@Test
	public void Loggar(){
		final String matricula = "1413556-1";
		final String senha = "12345678";
		final String senhaEncriptada = Encripta.encripta(senha);
		Usuarios usuarioRetorno = usuarioDAO.buscarPorMatriculaSenha(matricula, senhaEncriptada);
		Assert.assertNotNull(usuarioRetorno);
	}

	@Test
	public void testeAtualizarListarUsuarios(){
		final String nome = "DANIEL";
		List<Usuarios> usuariosRetorno = new ArrayList<Usuarios>();
		usuariosRetorno = usuarioBO.listaUsuarioPorNome(nome);
		Assert.assertNotNull(usuariosRetorno);
		
		Usuarios usuario = usuariosRetorno.get(0);
		usuario.setNome("Jorge");
		usuarioBO.atualizar(usuario);
	}
	
}
