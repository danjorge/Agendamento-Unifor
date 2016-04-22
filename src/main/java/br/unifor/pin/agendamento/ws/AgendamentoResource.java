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
	@Path("/{solicitacaoId}/xml")
	@Produces(MediaType.APPLICATION_XML)
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
	@Path("/{matricula}/json")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<Agendamento> getAgendamentosJSON(@PathParam("matricula") String matricula) throws DAOException{
		
		Usuarios usuario = new Usuarios();
		usuario.setMatricula(matricula);
		
		Usuarios usuario1 = usuarioBO.buscarUsuarioPorMatricula(usuario.getMatricula());
		
		List<Cursos> cursos = usuarioBO.retornaTodosCursos();
		
		usuario1.setCursos(new ArrayList<Cursos>());
		usuario1.getCursos().add(cursos.get(0));		
		
		
		seguranca.setUsuario(usuario1);
		
		
		try {
			@SuppressWarnings("unchecked")
			ArrayList<Agendamento> lst = (ArrayList)agendamentoBusiness.buscarAgendamentosPorCurso();
			
			listaAgendamento = lst;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(listaAgendamento.size() == 1){
			listaAgendamento.add(new Agendamento());
		}
		
		return listaAgendamento;
	}

}
