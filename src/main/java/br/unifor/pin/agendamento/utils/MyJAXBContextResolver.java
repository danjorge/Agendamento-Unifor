package br.unifor.pin.agendamento.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.sun.jersey.api.json.JSONJAXBContext;

import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Solicitacao;

@Provider
@SuppressWarnings("unchecked")
public class MyJAXBContextResolver implements ContextResolver<JAXBContext> {

	private JAXBContext context;
	@SuppressWarnings("rawtypes")
	private Class[] types = { Solicitacao.class, Agendamento.class };

	@SuppressWarnings({ "deprecation", "serial", "rawtypes" })
	public MyJAXBContextResolver() throws Exception {
		Map props = new HashMap<String, Object>();
		props.put(JSONJAXBContext.JSON_NOTATION, JSONJAXBContext.JSONNotation.MAPPED);
		props.put(JSONJAXBContext.JSON_ROOT_UNWRAPPING, Boolean.TRUE);
		props.put(JSONJAXBContext.JSON_ARRAYS, new HashSet<String>(1) {
			{
				add("soicitacao");
			}
		});
		props.put(JSONJAXBContext.JSON_NON_STRINGS, new HashSet<String>(1) {
			{
				add("solicitacao");
				add("statusSolicitacao");
				add("usuario");
				add("cursos");
			}
		});
		this.context = new JSONJAXBContext(types, props);
	}

	public JAXBContext getContext(Class<?> objectType) {
		return (types[0].equals(objectType)) ? context : null;
	}
}