package br.unifor.pin.agendamento.filter;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
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
		fc = event.getFacesContext();
		session = (HttpSession) fc.getExternalContext().getSession(true);
		Usuarios usuario = (Usuarios) session.getAttribute("usuario");
		currentPage = fc.getExternalContext().getRequestServletPath();
		boolean isIndexPage = (currentPage.lastIndexOf("/pages/login/login.xhtml") > -1);
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
		else if (usuario == null && !isIndexPage) { 
			session.setAttribute("logoffTitle", "Sessão Finalizada!");
			session.setAttribute("logoffMsg", "Você será redirecionado para a página de Login.");
			try {
				fc.getExternalContext().redirect(fc.getExternalContext().getRequestContextPath().toString()  + "/logoff.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
