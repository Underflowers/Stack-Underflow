package presentation;

import model.User;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "RegisterCommandServlet", urlPatterns = "/registerCommand")
public class RegisterCommandServlet  extends javax.servlet.http.HttpServlet {

    private static HashMap<String, User> userMap = new HashMap<>(); // Temporary user "database"

    public static int userCount() {
        return userMap.size();
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        User newUser = User.builder()
                .firstname(request.getParameter("firstname"))
                .lastname(request.getParameter("lastname"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();
        userMap.put(request.getParameter("email"), newUser);

        request.setAttribute("user", newUser);
        response.sendRedirect("/stack-underflow/questions");
    }

}