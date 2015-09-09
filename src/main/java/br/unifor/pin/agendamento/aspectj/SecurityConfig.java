/**
 * 
 */
package br.unifor.pin.agendamento.aspectj;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.entity.Papeis;
import br.unifor.pin.agendamento.entity.Permissoes;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.to.SegurancaTO;

/**
 * @author patrick.cunha
 * @since 07/05/2015
 */
@Aspect
@Component
public class SecurityConfig {

	@Autowired
	private SegurancaTO segurancaTO;
	private static final Logger LOG = LoggerFactory
			.getLogger(SecurityConfig.class);
	private static final String SECURITY_TAG = "[SEGURANCA] ";

	@Before("@within(RolesAllowed) || @within(PermitAll) || @annotation(RolesAllowed) || @annotation(PermitAll)")
	public void checkSecurity(JoinPoint joinPoint) {

		Method metodo = ((MethodSignature) joinPoint.getSignature())
				.getMethod();

		// Verifica se o acesso ao metodo esta liberado.
		if (metodo.getAnnotation(PermitAll.class) != null) {
			return;
		}

		// Contexto de segurança do usuário logado
		Usuarios usuario = this.segurancaTO.getUsuario();
		if (usuario == null || !this.segurancaTO.isAutenticado()) {
			this.dispararAcessoNegado();
		}

		LOG.debug(SECURITY_TAG + "Usuário: " + usuario);
		LOG.debug(SECURITY_TAG + "Funcionalidade acessada: "
				+ joinPoint.getSignature());

		//Se o usuario por Administrador, libera todos os acessos
		for (Papeis papel : usuario.getPapeis()) {
			if (papel.getNome().equals("Administrador")) {
				return;
			}
		}

		RolesAllowed permissoesDoMetodo = metodo
				.getAnnotation(RolesAllowed.class) != null ? metodo
				.getAnnotation(RolesAllowed.class) : metodo.getDeclaringClass()
				.getAnnotation(RolesAllowed.class);
				
		if(permissoesDoMetodo != null){
			final List<String> permissoesRequeridas = new ArrayList<String>(Arrays.asList(permissoesDoMetodo.value()));
			final List<String> permissoesDoUsuario = this.retornaPermissoesDoUsuario(usuario);
			//interseccao entre as listas
			permissoesRequeridas.retainAll(permissoesDoUsuario);
			if(permissoesRequeridas.size() > 0){
				return;
			}
		} else {
			this.dispararAcessoNegado();
		}

	}

	/**
	 * @param usuario
	 * @return Lista de permissoes do usuario
	 */
	private List<String> retornaPermissoesDoUsuario(Usuarios usuario) {
		List<String> permissoesDoUsuario = new ArrayList<String>();
		for (Papeis papel : usuario.getPapeis()) {
			for (Permissoes permissao : papel.getPermissoes()) {
				permissoesDoUsuario.add(permissao.getPermissao());
			}
		}
		return permissoesDoUsuario;
	}

	/**
	 * 
	 */
	private void dispararAcessoNegado() {
		SecurityException se = new SecurityException(SECURITY_TAG + "Capturada uma tentativa de acesso indevido. Tentativa abortada.");
		LOG.debug(SECURITY_TAG + "Capturada uma tentativa de acesso indevido. Tentativa abortada.", se);

		throw se;
	}
	
}
