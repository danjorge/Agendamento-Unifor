package br.unifor.pin.agendamento.exceptions;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.primefaces.context.RequestContext;

//Inicialmente devemos implementar a classe CustomExceptionHandler que extende a classe ExceptionHandlerWrapper
@SuppressWarnings({"unchecked","rawtypes"})
public class CustomExceptionHandler extends ExceptionHandlerWrapper {
 
    private ExceptionHandler wrapped;
 
    //Obtém uma instância do FacesContext
    final FacesContext facesContext = FacesContext.getCurrentInstance();
 
    //Obtém um mapa do FacesContext
    final Map requestMap = facesContext.getExternalContext().getRequestMap();
 
    //Obtém o estado atual da navegação entre páginas do JSF
    final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
 
    //Declara o construtor que recebe uma exceptio do tipo ExceptionHandler como parâmetro
    CustomExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }
 
    //Sobrescreve o método ExceptionHandler que retorna a "pilha" de exceções
    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }
 
    //Sobrescreve o método handle que é responsável por manipular as exceções do JSF
    @Override
    public void handle() throws FacesException {
 
        final Iterator iterator = getUnhandledExceptionQueuedEvents().iterator();
        
        while (iterator.hasNext()) {
            ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
 
            // Recupera a exceção do contexto
            Throwable exception = context.getException();
 
            // Aqui tentamos tratar a exeção
            try {
 
                // Aqui você poderia por exemploinstanciar as classes StringWriter e PrintWriter
                //StringWriter stringWriter = new StringWriter();
                
                // PrintWriter printWriter = new PrintWriter(stringWriter);
                // exception.printStackTrace(printWriter);
                // Por fim você pode converter a pilha de exceções em uma String
                //String message = stringWriter.toString();

                // Aqui você poderia enviar um email com a StackTrace
                // em anexo para a equipe de desenvolvimento

                if(requestMap.get("interceptorsTitle") != null && !requestMap.get("interceptorsTitle").toString().equals("")){
                	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, requestMap.get("interceptorsTitle").toString(), requestMap.get("interceptorsDetail").toString());
                	RequestContext.getCurrentInstance().showMessageInDialog(msg);
                }
                else{
                	// e depois imprimir a stacktrace no log
                    exception.printStackTrace();
                    
	                // Coloca uma mensagem de exceção no mapa da request
	                requestMap.put("exceptionMessage", exception.getMessage());
	 
	                // Avisa o usuário do erro
	                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
	                    (FacesMessage.SEVERITY_ERROR, "O sistema se recuperou de um erro inesperado.", ""));
	 
	                // Tranquiliza o usuário para que ele continue usando o sistema
	                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
	                    (FacesMessage.SEVERITY_INFO, "Você pode continuar usando o sistema normalmente!", ""));
	                
	                // Tranquiliza o usuário para que ele continue usando o sistema
	                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
	                    (FacesMessage.SEVERITY_ERROR, exception.getMessage(), ""));
                }
                
                
                
                facesContext.renderResponse();
            } finally {
                // Remove a exeção da fila
                iterator.remove();
            }
        }
        // Manipula o erro
        getWrapped().handle();
    }
}