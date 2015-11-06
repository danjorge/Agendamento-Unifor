package br.unifor.pin.agendamento.bussiness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.unifor.pin.agendamento.aspectj.Loggable;
import br.unifor.pin.agendamento.aspectj.PermitAll;
import br.unifor.pin.agendamento.aspectj.RolesAllowed;
import br.unifor.pin.agendamento.dao.UsuarioDAO;
import br.unifor.pin.agendamento.entity.Cursos;
import br.unifor.pin.agendamento.entity.Papeis;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.exceptions.DAOException;
import br.unifor.pin.agendamento.filter.SessionContext;

/**
 * @author patrick.cunha
 * 
 */
@Loggable
@Service
public class UsuarioBO {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private SessionContext sessao;

	@PermitAll
	@Loggable(enable = false)
	public void salvar(Usuarios usuario) {
		usuario.setAtivo(false);
		usuario.setPrimeiroAcesso(true);
		usuarioDAO.salvar(usuario);
	}

	@RolesAllowed(value = { "ALTERAR_USUARIO" })
	public void atualizar(Usuarios usuario) {
		usuarioDAO.atualizar(usuario);
	}

	@PermitAll
	@Loggable(enable = false)
	public Usuarios loggar(String matricula, String senha) {
		sessao.limparSessao();		
		Usuarios usuario = usuarioDAO.buscarPorMatriculaSenha(matricula, senha);
		if(usuario != null){
			sessao.setarObjetoSessao("usuario", usuario);
		}
		
		return usuario;
	}

	@PermitAll
	@Loggable(enable = false)
	public Usuarios buscarUsuarioPorMatricula(String matricula) {
		return usuarioDAO.buscarPorMatricula(matricula);
	}
	
	@PermitAll
	@Loggable(enable = false)
	public List<Cursos> retornaTodosCursos(){
		return usuarioDAO.retornaTodosCursos();
	}

	@RolesAllowed(value = { "LISTAR_USUARIO" })
	@Loggable(enable = false)
	public List<Usuarios> listaUsuarioPorNome(String nome) {
		List<Usuarios> usuarios = usuarioDAO.listarPorNome(nome);
		return usuarios;
	}

	@PermitAll
	@Loggable(enable = false)
	public Usuarios buscarPorId(Integer id) {
		try {
			return usuarioDAO.buscaPorId(id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RolesAllowed(value = { "EXCLUIR_USUARIO" })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void excluir(Usuarios usuario) {
		try {
			usuario = usuarioDAO.buscaPorId(usuario.getId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		usuarioDAO.excluir(usuario);
	}
	
	public List<Papeis> retornaTodosPapeis(){
		return usuarioDAO.buscaTodosPapeis();
	}

}
