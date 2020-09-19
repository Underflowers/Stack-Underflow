package presentation;

import model.User;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "RegisterCommandServlet", urlPatterns = "/registerCommand")
public class RegisterCommandServlet  extends javax.servlet.http.HttpServlet {

    private static HashMap<String, User> users = new HashMap<>(); // Temporary user "database"

    public static int usersCount() {
        return users.size();
    }

    public static boolean userAuthentication(String email, String password) {
        return users.containsKey(email) && users.get(email).getPassword().equals(password);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        // Verify both password fields
        if(!request.getParameter("password").equals(request.getParameter("passwordRepeat"))) {
            response.sendRedirect("/stack-underflow/register");
            return;
        }

        User newUser = User.builder()
                .firstname(request.getParameter("firstname"))
                .lastname(request.getParameter("lastname"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();
        users.put(request.getParameter("email"), newUser);

        request.setAttribute("user", newUser);
        response.sendRedirect("/stack-underflow/register");
    }

}