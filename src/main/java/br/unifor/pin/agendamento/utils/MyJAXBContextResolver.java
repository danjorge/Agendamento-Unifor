package br.unifor.pin.agendamento.utils;

import java.util.HashMap;
import java.util.HashSet;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Solicitacao;

import com.sun.jersey.api.json.JSONJAXBContext;

@Provider
@SuppressWarnings("unchecked")
public class MyJAXBContextResolver implements ContextResolver<JAXBContext> {

	private JAXBContext context;
	@SuppressWarnings("rawtypes")
	private Class[] types = { Solicitacao.class, Agendamento.class };

	
	@SuppressWarnings({ "serial", "deprecation", "rawtypes" })
	public MyJAXBContextResolver() throws Exception {
		HashMap props = new HashMap<String, Object>();
		props.put(JSONJAXBContext.JSON_ARRAYS, new HashSet<String>(1){{add("jobs");}});
		this.context = new JSONJAXBContext(types, props);
	}

	public JAXBContext getContext(Class<?> objectType) {
		return (types[0].equals(objectType)) ? context : null;
	}
}