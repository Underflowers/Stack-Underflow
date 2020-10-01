package underflowers.stackunderflow.ui.web.login;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticateCommand;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticationFailedException;
import underflowers.stackunderflow.ui.web.register.RegisterCommandServlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoginCommandServlet", urlPatterns = "/login.do")
public class LoginCommandServlet extends javax.servlet.http.HttpServlet {

    private ServiceRegistry serviceRegistry = ServiceRegistry.getServiceRegistry();
    private IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.getSession().removeAttribute("errors");

        AuthenticateCommand command = AuthenticateCommand.builder()
                .email(request.getParameter("email"))
                .clearPassword(request.getParameter("password"))
                .build();

        try {
            identityManagementFacade.authenticate(command);
            response.sendRedirect(" questions");
        } catch (AuthenticationFailedException e) {
            request.getSession().setAttribute("errors", List.of(e.getMessage()));
            response.sendRedirect(" login");
        }
    }
}
