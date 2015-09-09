/**
 * 
 */
package br.unifor.pin.agendamento.to;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.entity.Usuarios;

/**
 * @author patrick.cunha
 * @since 07/05/2015
 */
@Component
@SessionScoped
public class SegurancaTO implements Serializable {

	private static final long serialVersionUID = -9069250861713212366L;
	
	private Usuarios usuario;

	public boolean isAutenticado() {
		return usuario != null;
	}
	
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
	public Usuarios getUsuario() {
		return usuario;
	}

}
