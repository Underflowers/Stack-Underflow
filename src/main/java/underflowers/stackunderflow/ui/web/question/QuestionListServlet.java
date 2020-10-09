package underflowers.stackunderflow.ui.web.question;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.application.question.QuestionsDTO;
import underflowers.stackunderflow.application.question.QuestionsQuery;
import underflowers.stackunderflow.domain.question.Question;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "QuestionsServlet", urlPatterns = "/questions")
public class QuestionListServlet extends javax.servlet.http.HttpServlet  {

    @Inject
    ServiceRegistry serviceRegistry;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        
        request.getSession().removeAttribute("errors");

        QuestionsDTO questionsDTO = questionFacade.getQuestions(QuestionsQuery.builder()
            .isAnswered(false)
            .build());
        request.setAttribute("questions", questionsDTO);
        request.getRequestDispatcher("/WEB-INF/views/questions.jsp").forward(request, response);
    }

}
