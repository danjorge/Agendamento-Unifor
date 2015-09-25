package br.unifor.pin.agendamento.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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
		
	private static final String LOGIN = "/login/login.xhtml";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		HttpSession sess = ((HttpServletRequest) request).getSession(true);
		
		String newCurrentPage = ((HttpServletRequest) request).getServletPath();
		
		if(sess.getAttribute("currentPage") == null){
			sess.setAttribute("lastPage", newCurrentPage);
			sess.setAttribute("currentPage", newCurrentPage);
		} else{
			String oldCurrentPage = sess.getAttribute("currentPage").toString();
			if(!oldCurrentPage.equals(newCurrentPage)){
				sess.setAttribute("lastPage", oldCurrentPage);
				sess.setAttribute("currentPage", newCurrentPage);
			}
		}
		
		if(!this.auth(sess)){
			req.getRequestDispatcher(LOGIN).forward(request, response);
		} else { 
			resp.setHeader("Cache-Control", "no-cache");
			resp.setHeader("Pragma", "no-cache");
			resp.setDateHeader("Expires", 0);
			chain.doFilter(request, response);			
		}
		
	}

	private boolean auth(HttpSession sess) {
		boolean retorno = false;
		Usuarios u = (Usuarios) sess.getAttribute("usuario");
		if(u != null){
			retorno = true;
			return retorno;
		}
		return retorno;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
