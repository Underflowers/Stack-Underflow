package underflowers.stackunderflow.ui.web.question;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.application.question.QuestionsDTO;
import underflowers.stackunderflow.application.question.QuestionsQuery;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.question.QuestionId;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "QuestionServlet", urlPatterns = "/question")
public class QuestionServlet extends HttpServlet {

    @Inject
    ServiceRegistry serviceRegistry;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();

        request.getSession().removeAttribute("errors");

        QuestionsDTO.QuestionDTO questionDTO = questionFacade.getQuestion(new QuestionId(request.getParameter("uuid")));
        request.setAttribute("question", questionDTO);
        request.getRequestDispatcher("/WEB-INF/views/question.jsp").forward(request, response);
    }
}
