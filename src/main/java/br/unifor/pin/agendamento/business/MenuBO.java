package br.unifor.pin.agendamento.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.aspectj.Loggable;
import br.unifor.pin.agendamento.filter.SessionContext;

@Loggable
@Service
public class MenuBO {

	@Autowired
	private SessionContext sessao;
	
	public Object recuperaUsuarioSessao(){
		return sessao.recuperaObjetoSessao("usuario");
	}
	
	public void finalizarSessao(){
		sessao.encerraSessao();
	}
	
	public void limparSessao(){
		sessao.limparSessao();
	}
	
	public Object recuperaObjetoSessao(String string){
		return sessao.recuperaObjetoSessao(string);
	}
	
	public void removeObjetoSessao(String string){
		sessao.removeObjetoSessao(string);
	}
	
}
