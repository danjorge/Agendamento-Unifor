package br.unifor.pin.saa.manager.usuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.saa.bussines.UsuarioBO;
import br.unifor.pin.saa.entity.Usuarios;
import br.unifor.pin.saa.utils.MessagesUtils;
import br.unifor.pin.saa.utils.Navigation;
/**
 * @author patrick.cunha
 * 
 */
@RequestScoped
@ManagedBean(name = "atualizaUsuario")
@Component(value = "atualizaUsuario")
public class AtualizaUsuarioManager {

	@Autowired
	private UsuarioBO usuarioBO;
	private Usuarios usuarioSelecionado;

	public String atualizar() {
		usuarioBO.atualizar(usuarioSelecionado);
		MessagesUtils.info("Usuário atualizado com sucesso!");

		return Navigation.SUCESSO;
	}

	public String preparaAtualizar(Usuarios usuario) {
		usuarioSelecionado = usuarioBO.buscarPorId(usuario.getId());

		return Navigation.ATUALIZA;
	}
	
	public void limparDados(){
		usuarioSelecionado.setNome("");
		usuarioSelecionado.setEmail("");
		usuarioSelecionado.setSenha("");
		usuarioSelecionado.setAtivo(false);
		usuarioSelecionado.setPrimeiroAcesso(true);
	}

	public Usuarios getUsuarioSelecionado() {
		return usuarioSelecionado;
	}
	public void setUsuarioSelecionado(Usuarios usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	
}
