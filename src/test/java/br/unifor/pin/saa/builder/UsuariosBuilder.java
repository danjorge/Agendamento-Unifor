package br.unifor.pin.saa.builder;

import br.unifor.pin.saa.entity.Usuarios;

public class UsuariosBuilder {
	
	private Usuarios usuario;
	
	public UsuariosBuilder() {
		usuario = new Usuarios();
	}
	
	public UsuariosBuilder comNome(){
		usuario.setNome("adriano patrick");
		return this;
	}
	
	public UsuariosBuilder comSenha(){
		usuario.setSenha("123456");
		return this;
	}
	
	public UsuariosBuilder comEmail(){
		usuario.setEmail("adriano@gmail.com");
		return this;
	}
	
	public UsuariosBuilder sendoPrimeiroAcesso(){
		usuario.setPrimeiroAcesso(true);
		return this;
	}
	
	public UsuariosBuilder sendoAtivo(){
		usuario.setAtivo(false);
		return this;
	}
	
	public Usuarios build(){
		return usuario;
	}
	
}
