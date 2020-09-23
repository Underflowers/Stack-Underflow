package presentation;

import model.Question;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "HomeServlet", urlPatterns = "/home")
public class HomeServlet extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Question testQuestion = Question.builder()
            .author("John Doe")
            .title("What is the meaning of life?")
            .content("Help me to answer this question please")
        .build();

        request.setAttribute("question", testQuestion);
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}