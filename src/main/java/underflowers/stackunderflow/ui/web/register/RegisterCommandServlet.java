package underflowers.stackunderflow.ui.web.register;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationCommand;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationFailedException;
import underflowers.stackunderflow.domain.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RegisterCommandServlet", urlPatterns = "/register.do")
public class RegisterCommandServlet  extends javax.servlet.http.HttpServlet {

    private ServiceRegistry serviceRegistry = ServiceRegistry.getServiceRegistry();
    private IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("errors");

        RegistrationCommand command = RegistrationCommand.builder()
                .email(request.getParameter("email"))
                .firstname(request.getParameter("firstname"))
                .lastname(request.getParameter("lastname"))
                .clearPassword(request.getParameter("password"))
                .repeatPassword(request.getParameter("passwordRepeat"))
                .build();

        try {
            identityManagementFacade.register(command);
            request.getRequestDispatcher("login.do").forward(request, response);
        } catch (RegistrationFailedException e) {
            request.getSession().setAttribute("errors", List.of(e.getMessage()));
            response.sendRedirect(" register");
        }
    }

}