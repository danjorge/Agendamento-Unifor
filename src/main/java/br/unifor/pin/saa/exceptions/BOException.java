/**
 * Classe de exceção na camada de negócio
 */
package br.unifor.pin.saa.exceptions;

/**
 * @author patrick.cunha
 * @since 05/05/2015
 */
public class BOException extends Exception {
	
	private static final long serialVersionUID = 6291013384988761126L;

	public BOException(String msg) {
		super(msg);
	}

}
