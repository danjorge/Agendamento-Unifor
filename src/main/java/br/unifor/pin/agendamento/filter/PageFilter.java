package br.unifor.pin.agendamento.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.unifor.pin.agendamento.entity.Usuarios;



@WebFilter(filterName="PageFilter", urlPatterns={"/pages/*"})
public class PageFilter implements Filter{	
		
	//private static final String LOGIN = "/login/login.xhtml";
	
	@SuppressWarnings("unused")
	private ServletContext context;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context = filterConfig.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpSession sess = req.getSession();
		
		Usuarios u = (Usuarios) sess.getAttribute("usuario");
		
		if(u != null){
			sess.setAttribute("usuario", u);
		}
		else{
			sess.setAttribute("logoffTitle", "Acesso negado!");
			sess.setAttribute("logoffMsg", "<br/>Você será redirecionado para a página de login.");
			resp.sendRedirect("logoff.xhtml");
		}
		chain.doFilter(request, response);			
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
