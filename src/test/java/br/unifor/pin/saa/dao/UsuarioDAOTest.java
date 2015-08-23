package br.unifor.pin.saa.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.unifor.pin.saa.entity.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class UsuarioDAOTest {
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Test
	public void testSalvar() throws Exception {
		
		Usuarios usuario = new Usuarios();
		usuario.setNome("adriano");
		usuario.setSenha("123456");
		usuario.setEmail("adriano@gmail.com");
		usuario.setPrimeiroAcesso(true);
		usuario.setAtivo(false);
		usuarioDAO.salvar(usuario);
		
		Assert.assertNotNull(usuario.getId());
		System.out.println(usuario.getId());
		
	}
	
	@Test
	public void testListaPorNome(){
		List<Usuarios> usuarios = usuarioDAO.listarPorNome("adri");
		Assert.assertEquals(1, usuarios.size());
	}

}
