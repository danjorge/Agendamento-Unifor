package br.unifor.pin.agendamento.bussines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.aspectj.Loggable;
import br.unifor.pin.agendamento.utils.SessionContext;

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
	
}
