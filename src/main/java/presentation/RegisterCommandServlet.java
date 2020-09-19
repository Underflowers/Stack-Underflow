package presentation;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet(name = "RegisterCommandServlet", urlPatterns = "/registerCommand")
public class RegisterCommandServlet  extends javax.servlet.http.HttpServlet {

    private static HashMap<String, User> users = new HashMap<>(); // Temporary user "database"

    public static int usersCount() {
        return users.size();
    }

    public static boolean userAuthentication(String email, String password) {
        return users.containsKey(email) && users.get(email).getPassword().equals(password);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        User newUser = User.builder()
                .firstname("firstname")
                .lastname("lastname")
                .email("test@email.com")
                .password("test")
                .build();
        users.put("test@email.com", newUser);
    }

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        boolean hasError = false;

        // Both password field doesn't match
        if(!request.getParameter("password").equals(request.getParameter("passwordRepeat"))) {
            request.setAttribute("errorMsg", "Password and password repeat must correspond");
            hasError = true;
        }
        // Email already used
        else {
            Iterator it = users.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) it.next();
                User user = ((User) mapEntry.getValue());
                if (user.getEmail().equals(request.getParameter("email"))) {
                    request.setAttribute("errorMsg", "Email already used");
                    hasError = true;
                    break;
                }
            }
        }

        // Passwords and email OK
        if(!hasError){
            User newUser = User.builder()
                    .firstname(request.getParameter("firstname"))
                    .lastname(request.getParameter("lastname"))
                    .email(request.getParameter("email"))
                    .password(request.getParameter("password"))
                    .build();
            users.put(request.getParameter("email"), newUser);

            request.setAttribute("user", newUser);
        }

        request.setAttribute("usersCount", usersCount());
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

}