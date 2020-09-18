package presentation;

import model.Question;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "HomeServlet", urlPatterns = "/home")
public class HomeServlet extends javax.servlet.http.HttpServlet {

    private Question testQuestion;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        testQuestion = new Question("John Doe", "What is the meaning of life?");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setAttribute("question", testQuestion);
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}