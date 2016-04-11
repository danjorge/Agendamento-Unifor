package br.unifor.pin.agendamento.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.spi.resource.Singleton;

import br.unifor.pin.agendamento.business.SolicitacaoBO;
import br.unifor.pin.agendamento.business.UsuarioBO;
import br.unifor.pin.agendamento.entity.Cursos;
import br.unifor.pin.agendamento.entity.Solicitacao;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.exceptions.DAOException;
import br.unifor.pin.agendamento.to.SegurancaTO;

@Component
@Singleton
@Path("/solicitacao")
public class SolicitacaoResource {

	@Autowired
	private SolicitacaoBO solicitacaoBusiness;
	private Solicitacao solicitacao;
	
	@Autowired
	private SegurancaTO seguranca;
	
	@Autowired
	private UsuarioBO usuarioBO;
	
	private List<Solicitacao> listaSolicitacao;
	
	@Context
	private Providers providers;
	

	
	@GET
	@Path("/{solicitacaoId}/xml")
	@Produces("application/xml")
	public Response getSolicitacaoByIdJSON(@PathParam("solicitacaoId") Integer solicitacaoId){
		
		String result = "";
		try {
			solicitacao = solicitacaoBusiness.retornaSolicitacaoPorId(solicitacaoId);
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return Response.status(200).entity( (solicitacao != null ? solicitacao : result)).build();
	}
	
	@GET
	@Path("/{matricula}/json")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<Solicitacao> getListaSolicitacoes(@PathParam("matricula") String matricula) throws DAOException{
		
		Usuarios usuario = new Usuarios();
		usuario.setMatricula(matricula);
		
		Usuarios usuario1 = usuarioBO.buscarUsuarioPorMatricula(usuario.getMatricula());
		
		List<Cursos> cursos = usuarioBO.retornaTodosCursos();
		
		usuario1.setCursos(new ArrayList<Cursos>());
		usuario1.getCursos().add(cursos.get(0));		
		
		
		seguranca.setUsuario(usuario1);
		
		try {
			listaSolicitacao = solicitacaoBusiness.buscarSolcitacoesPorCurso(); 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		if(listaSolicitacao.size() == 1){
			listaSolicitacao.add(new Solicitacao());
		}
		
		
		return listaSolicitacao;
	}
	
	
	
}
