package underflowers.stackunderflow.ui.web.login;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticateCommand;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticationFailedException;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoginCommandServlet", urlPatterns = "/login.do")
public class LoginCommandServlet extends javax.servlet.http.HttpServlet {

    @Inject
    ServiceRegistry serviceRegistry;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();
        request.getSession().removeAttribute("errors");

        AuthenticateCommand command = AuthenticateCommand.builder()
                .email(request.getParameter("email"))
                .clearPassword(request.getParameter("password"))
                .build();

        try {
            AuthenticatedUserDTO user = identityManagementFacade.authenticate(command);
            request.getSession().setAttribute("authUser", user);
            response.sendRedirect(" questions");
        } catch (AuthenticationFailedException e) {
            request.getSession().setAttribute("errors", List.of(e.getMessage()));
            response.sendRedirect(" login");
        }
    }
}
