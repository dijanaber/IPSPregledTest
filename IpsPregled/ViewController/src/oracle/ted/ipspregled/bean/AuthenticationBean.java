package oracle.ted.ipspregled.bean;

    import javax.faces.application.FacesMessage;
    import javax.faces.context.FacesContext;
    import javax.security.auth.Subject;
    import javax.security.auth.callback.CallbackHandler;

    /*import weblogic.security.URLCallbackHandler;*/

    import javax.security.auth.login.FailedLoginException;
    import javax.security.auth.login.LoginException;

    import javax.servlet.RequestDispatcher;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;

    //import oracle.security.jps.internal.api.servlet.ServletAuthentication;
    // import weblogic.security.SimpleCallbackHandler;  
   // import oracle.security.SimpleCallbackHandler;
    import weblogic.security.URLCallbackHandler;
    import weblogic.security.services.Authentication;
    import weblogic.servlet.security.ServletAuthentication;



public class AuthenticationBean {
    
    public AuthenticationBean() {
    }
    public String username;
    public String password;
    
    public String logout(){
    final String LOGOUT_URL = "/adfAuthentication?logout=true&end_url=/faces/login.jspx";
    FacesContext ctx = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
    HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
    RequestDispatcher dispatcher = request.getRequestDispatcher(LOGOUT_URL);
    
    try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    ctx.responseComplete();
    return null;

    }


    public void setUsername(String username) {
        this.username = username.toUpperCase();
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password.toUpperCase();
    }

    public String getPassword() {
        return this.password;
    }
    public String login() {
    final String WELCOME_URL = "/adfAuthentication?success_url=/faces/Dobrodosli.jspx";
    FacesContext ctx = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
    if (authenticate(request)) {
        HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
        RequestDispatcher dispatcher = request.getRequestDispatcher(WELCOME_URL);
        try {
                dispatcher.forward(request, response); 
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
        ctx.responseComplete();
        }
    return null;
    }
    private boolean authenticate(HttpServletRequest request) {
        //String password = getPassword() == null ? "" : getPassword();
        String password = getPassword();
        CallbackHandler handler = new URLCallbackHandler(getUsername(),password.getBytes());
       /*URLCallbackHandler handler = new URLCallbackHandler(getUsername(),password.getBytes());*/
        boolean authenticated = false;
        try {
            Subject subject = Authentication.login(handler);
            ServletAuthentication.runAs(subject, request);
            ServletAuthentication.generateNewSessionID(request);
            authenticated = true;
            
        } catch (FailedLoginException failedLoginException) {
            // TODO: Add catch code
            
            reportLoginError("Pogresne privilegije!");
            /*failedLoginException.printStackTrace();*/
        } catch (LoginException loginException) {
            // TODO: Add catch code
            reportLoginError(loginException.getMessage());
            /*loginException.printStackTrace();*/
        }
        return authenticated;
    }  
    private void reportLoginError(String errorMessage) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,null,errorMessage);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null, fm);
    }
    
}
