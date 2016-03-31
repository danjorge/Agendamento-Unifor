package br.unifor.pin.agendamento.ws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import br.unifor.pin.agendamento.business.UsuarioBO;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.exceptions.DAOException;

@Path("/login")
public class LoginResource {
	
	@Autowired
	private UsuarioBO usuarioBO;
	private Usuarios usuario;
	
	
	@GET
	@Path("/{matricula}/json")
	@Produces("application/json")
	public Response getUsuarioByMatricula(@PathParam("matricula") String matricula){
		
		String result = "";
		try {
			usuario = usuarioBO.buscarUsuarioPorMatricula(matricula);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return Response.status(200).entity(usuario != null ? usuario : result ).build();
		
	}
	
	@GET
	@Path("/json")
	@Produces("application/json")
	public List<Usuarios> retornaTodosUsuarios() throws DAOException{
		return usuarioBO.buscarTodosUsuarios();
	}


}
