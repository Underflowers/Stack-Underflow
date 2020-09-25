package underflowers.stackunderflow.ui.web.user;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setAttribute("usersCount", RegisterCommandServlet.usersCount());
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
}