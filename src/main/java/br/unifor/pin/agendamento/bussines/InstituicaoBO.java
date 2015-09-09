package br.unifor.pin.agendamento.bussines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.agendamento.dao.InstituicoesDAO;
import br.unifor.pin.agendamento.entity.Instituicoes;

@Component
public class InstituicaoBO {

	private static final Logger logger = LoggerFactory
			.getLogger(InstituicaoBO.class);

	@Autowired
	private InstituicoesDAO instituicaoDAO;
	
	public void salvar(Instituicoes instituicoes) {
		loggerInit("salvar");
		instituicaoDAO.salvar(instituicoes);
		loggerFinhish("salvar");
	}
	
	public void atualizar(Instituicoes instituicoes){
		loggerInit("atualizar");
		instituicaoDAO.atualizar(instituicoes);
		loggerFinhish("atualizar");
		
	}

	public void loggerInit(String method) {
		logger.debug("Inicio do método " + method + " da classe"
				+ this.getClass().getName());
	}

	public void loggerFinhish(String method) {
		logger.debug("Fim do método "+method+" da classe"
				+ this.getClass().getName());
	}

}
