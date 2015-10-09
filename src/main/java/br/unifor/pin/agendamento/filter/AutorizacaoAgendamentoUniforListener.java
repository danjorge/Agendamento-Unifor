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
	
	public AutorizacaoAgendamentoUniforListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		// SETA VARIÁVEIS
		// ------------------------------------------------------------------------------
		fc  = (fc == null ? event.getFacesContext() : fc);
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		Usuarios usuario = (Usuarios) session.getAttribute("usuario");
		String currentPage = fc.getViewRoot().getViewId();
		boolean isindexPage = (currentPage.lastIndexOf("login/login.xhtml") > -1);
		boolean isLogoffPage = (currentPage.lastIndexOf("logoff.xhtml") > -1);
		// ------------------------------------------------------------------------------
		
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
		else if (usuario == null && !isindexPage) { 
			session.setAttribute("logoffTitle", "Acesso negado!");
			session.setAttribute("logoffMsg", "Este sistema não permite acesso direto pela URL.<br/><br/>Você será redirecionado para a página de Login.");
			this.redirect(fc, "/logoff.xhtml");
		}
		
		// SE ACESSO VIA GUARDIÃO (ACESSO CORRETO) + USUÁRIO NÃO ENCONTRADO:
		/*else if (p != null && usuario == null){
				session.setAttribute("logoffTitle", "Usuário não encontrado!");
				session.setAttribute("logoffMsg", "O Sistema não encontrou suas credenciais de usuário. "
								   + "Por favor entre em contato com o atendimento para solucionar o problema.<br/><br/>Você será redirecionado para o Guardião.");
				this.redirect(fc, "/logoff.xhtml");
		}*/
			
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// SETA VARIÁVEIS
		// ------------------------------------------------------------------------------
		fc  = event.getFacesContext();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        request.getSession().setAttribute("p", fc.getExternalContext().getSessionId(true));
        // ------------------------------------------------------------------------------
        
        // CASO usuario == null, ACESSA A PÁGINA DE LOGIN.
        if (request.getSession().getAttribute("usuario") == null){
       		this.redirect(fc, "index.xhtml");
        }
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}
	
	// MÉTODO DE REDIRECIONAMENTO DE PÁGINA UTILIZADO NO MÉTODO "afterPhase"
	private void redirect(FacesContext fc, String outcome){
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, outcome);
	}

}
