package br.unifor.pin.agendamento.utils;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Solicitacao;

@Provider
public class MyJAXBContextResolver implements ContextResolver<JAXBContext> {

	private JAXBContext context;
	private Class[] types = { Solicitacao.class, Agendamento.class };

	public MyJAXBContextResolver() throws Exception {
		this.context = new JSONJAXBContext(JSONConfiguration.mapped().arrays("solicitacao", "agendamento").build(), types);
	}

	public JAXBContext getContext(Class objectType) {
		System.out.println("TUG CALL");
		for (Class type : types) {
			if (type == objectType) {
				return context;
			}
		}
		return null;
	}

}