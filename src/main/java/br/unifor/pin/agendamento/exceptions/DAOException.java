/**
 * Classe de exceção para camada de acesso a dados
 */
package br.unifor.pin.agendamento.exceptions;

/**
 * @author patrick.cunha
 * 
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 6986867524676026821L;
	
	public DAOException(String msg) {
		super(msg);
	}

}
