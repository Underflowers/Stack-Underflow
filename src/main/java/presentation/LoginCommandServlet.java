package presentation;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "LoginCommandServlet", urlPatterns = "/loginCommand")
public class LoginCommandServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        if(RegisterCommandServlet.userAuthentication(request.getParameter("email"), request.getParameter("password")))
            response.sendRedirect("/stack-underflow/questions");
        else
            response.sendRedirect("/stack-underflow/login");
    }
}
