/**
 * 
 */
package br.unifor.pin.agendamento.managers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.business.UsuarioBO;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.filter.SessionContext;
import br.unifor.pin.agendamento.to.SegurancaTO;
import br.unifor.pin.agendamento.utils.Encripta;
import br.unifor.pin.agendamento.utils.MessagesUtils;
import br.unifor.pin.agendamento.utils.Navigation;

/**
 * @author patrick.cunha
 * @since 26/06/2015
 */
@RequestScoped
@Component(value = "login")
@ManagedBean(name = "login")
public class LoginManager {

	@Autowired
	private UsuarioBO usuarioBO;
	@Autowired
	private SegurancaTO seguranca;
	@Autowired
	private SessionContext sessao;
	
	@Autowired
	private SolicitacaoManager solicitacaoManagedBean;
	
	@Autowired
	private AgendamentoManager agendamentoManagedBean;
	
	private Usuarios usuario = new Usuarios();
	private boolean existsEmail;

	public String loggar() {
		sessao.limparSessao();
		Usuarios usuario = this.usuarioBO.loggar(this.usuario.getMatricula(),
				Encripta.encripta(this.usuario.getSenha()));
		this.usuario = new Usuarios();
		if (usuario != null) {
			seguranca.setUsuario(usuario);
			existsEmail = true;
			solicitacaoManagedBean.carregarListas();
			agendamentoManagedBean.carregarListas();
			MessagesUtils.info("Bem vindo "+usuario.getNome());
			return Navigation.SUCESSO;
		} else {
			existsEmail = false;
			MessagesUtils.error("O e-mail ou a senha inseridos estão incorretos.");
			return Navigation.FRACASSO;
		}
	}
	
	public String cadastrar(){
		return Navigation.CADASTRA;
	}

	/**
	 * @return the usuario
	 */
	public Usuarios getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the existsEmail
	 */
	public boolean isExistsEmail() {
		return existsEmail;
	}

	/**
	 * @param existsEmail
	 *            the existsEmail to set
	 */
	public void setExistsEmail(boolean existsEmail) {
		this.existsEmail = existsEmail;
	}

	public SolicitacaoManager getSolicitacaoManagedBean() {
		return solicitacaoManagedBean;
	}

	public void setSolicitacaoManagedBean(SolicitacaoManager solicitacaoManagedBean) {
		this.solicitacaoManagedBean = solicitacaoManagedBean;
	}

}
