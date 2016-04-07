package br.unifor.pin.agendamento.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.spi.resource.Singleton;

import br.unifor.pin.agendamento.business.AgendamentoBO;
import br.unifor.pin.agendamento.business.UsuarioBO;
import br.unifor.pin.agendamento.entity.Agendamento;
import br.unifor.pin.agendamento.entity.Cursos;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.exceptions.DAOException;
import br.unifor.pin.agendamento.to.SegurancaTO;

@Singleton
@Component
@Path("/agendamento")
public class AgendamentoResource {
	
	@Autowired
	private AgendamentoBO agendamentoBusiness;
	
	@Autowired
	private SegurancaTO seguranca;
	
	@Autowired
	private UsuarioBO usuarioBO;	
	
	private List<Agendamento> listaAgendamento;
	
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
	
	
	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Agendamento> getAgendamentosJSON() throws DAOException{
		
		Usuarios usuario = new Usuarios();
		usuario.setMatricula("1413556");
		
		Usuarios usuario1 = usuarioBO.buscarUsuarioPorMatricula(usuario.getMatricula());
		
		List<Cursos> cursos = usuarioBO.retornaTodosCursos();
		
		usuario1.setCursos(new ArrayList<Cursos>());
		usuario1.getCursos().add(cursos.get(0));		
		
		
		seguranca.setUsuario(usuario1);
		
		
		try {
			listaAgendamento = agendamentoBusiness.buscarAgendamentosPorCurso();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return listaAgendamento;
	}

}
