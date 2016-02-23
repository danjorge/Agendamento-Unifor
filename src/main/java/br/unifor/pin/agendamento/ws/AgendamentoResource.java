package br.unifor.pin.agendamento.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import br.unifor.pin.agendamento.business.AgendamentoBO;
import br.unifor.pin.agendamento.entity.Agendamento;

@Path("/agendamento")
public class AgendamentoResource {
	
	@Autowired
	private AgendamentoBO agendamentoBusiness;
	
	@GET
	@Path("/{solicitacaoId}/json")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getAgendamentoBySolicitacaoIdJSON(@PathParam("solicitacaoId") Integer solicitacaoId){
		
		Agendamento agendamento = null;
		String result = "";
		
		try {
			agendamento = agendamentoBusiness.retornaAgendamentoPorSolicitacao(solicitacaoId);		
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
			// TODO: handle exception
		}
		return Response.status(200).entity( (agendamento != null ? agendamento : result) ).build();
		
	}
	
	@GET
	@Path("/{agendamentoId}/xml")
	@Produces(MediaType.APPLICATION_XML)
	public Agendamento getAgendamentoByIdJSON(@PathParam("agendamentoId") Integer agendamentoId){
		try {
			return agendamentoBusiness.retornaAgendamentoPorId(agendamentoId);
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return null;
	}

}
