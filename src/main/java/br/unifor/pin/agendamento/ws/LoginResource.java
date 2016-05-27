package br.unifor.pin.agendamento.ws;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.sun.jersey.spi.resource.Singleton;

import br.unifor.pin.agendamento.business.AnexosBO;
import br.unifor.pin.agendamento.business.UsuarioBO;
import br.unifor.pin.agendamento.entity.Anexos;
import br.unifor.pin.agendamento.entity.Usuarios;
import br.unifor.pin.agendamento.exceptions.DAOException;

@Component
@Singleton
@Path("/login")
public class LoginResource {
	
	@Autowired
	private UsuarioBO usuarioBO;
	@Autowired
	private AnexosBO anexosBO;
	
	private Usuarios usuario;
	
	private List<Usuarios> listaUsuarios;
	
	
	@GET
	@Path("/{matricula}/json")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
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
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<Usuarios> retornaTodosUsuarios() throws DAOException{
		
		listaUsuarios = usuarioBO.buscarTodosUsuarios();
		
		return listaUsuarios;
	}

	@POST
	@Path("/{matricula}/salvarImagemUsuario")
	public void salvarImagemUsuario(@PathParam("matricula") String matricula,@RequestBody Anexos anexos, @Context HttpServletRequest request){		
		Usuarios usuarios = usuarioBO.buscarUsuarioPorMatricula(matricula);
		anexos.setUsuarios(usuarios);
		Anexos anexos1 = new Anexos();
		anexos1 = anexos;
		anexosBO.handlerFileUpload(anexos1, request);
	}

}
