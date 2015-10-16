package br.unifor.pin.agendamento.filter;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.unifor.pin.agendamento.entity.Usuarios;

public class AutorizacaoAgendamentoUniforListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9143181125138243807L;
	
	private FacesContext fc;
	private String currentPage;
	private HttpSession session;
	
	public AutorizacaoAgendamentoUniforListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		// SETA VARIÁVEIS
		// ------------------------------------------------------------------------------
		fc  = (fc == null ? event.getFacesContext() : fc);
		session = ( session == null ? (HttpSession) fc.getExternalContext().getSession(true) : session );
		Usuarios usuario = (Usuarios) session.getAttribute("usuario");
		currentPage = (currentPage == null ? fc.getViewRoot().getViewId() : currentPage);
		boolean isIndexPage = (currentPage.lastIndexOf("login/login.xhtml") > -1);
		boolean isLogoffPage = (currentPage.lastIndexOf("logoff.xhtml") > -1);
		// ------------------------------------------------------------------------------
		
		System.out.println("Finalizando Fase: " +event.getPhaseId());
		// SE USUÁRIO JÁ ESTIVER LOGADO, NÃO FAZ NADA.
		if (session.getAttribute("usuario") != null && !isLogoffPage){ return; }
		
		// SE ESTIVER NA PÁGINA DE LOGOFF, LIMPA SESSÃO.
		if (isLogoffPage){
			fc.getExternalContext().getSessionMap().clear();
			System.out.println("Limpando a sessão.");
			session.setAttribute("logoffTitle", "Sessão Finalizada!");
			session.setAttribute("logoffMsg", "Você será redirecionado para a página de Login.");
		}
		
		// SE HOUVER TENTATIVA DE ACESSO DIRETO PELA URL (ACESSO INCORRETO):
		/*else if (usuario == null && !isIndexPage) { 
			session.setAttribute("logoffTitle", "Acesso negado!");
			session.setAttribute("logoffMsg", "Este sistema não permite acesso direto pela URL.<br/><br/>Você será redirecionado para a página de Login.");
			this.redirect(fc, fc.getExternalContext().getRequestContextPath().toString()  + "/logoff.xhtml");
		}*/
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("Iniciando Fase: " +event.getPhaseId());
		
		HttpServletRequest request = (HttpServletRequest) event.getFacesContext().getExternalContext().getRequest();
		
		if (request.getSession().getAttribute("usuario") == null){
			this.redirect(event.getFacesContext(), event.getFacesContext().getExternalContext().getRequestContextPath().toString() + "login/login.xhtml");
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	// MÉTODO DE REDIRECIONAMENTO DE PÁGINA UTILIZADO NO MÉTODO "afterPhase"
	private void redirect(FacesContext fc, String outcome){
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, outcome);
	}

}
