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

@WebServlet(name = "QuestionsServlet", urlPatterns = "/questions/*")
public class QuestionListServlet extends javax.servlet.http.HttpServlet  {

    private static int QUESTIONS_PER_PAGE = 10;

    @Inject
    ServiceRegistry serviceRegistry;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        
        request.getSession().removeAttribute("errors");

        String uri = request.getRequestURI().substring(1);
        int page = 0;
        if (uri.contains("/")) {
            page = Integer.parseInt(uri.split("/")[1]);
        }
        QuestionsDTO questionsDTO = questionFacade.getQuestions(QuestionsQuery.builder()
            .isAnswered(false)
            .offset(page * QUESTIONS_PER_PAGE)
            .limit(QUESTIONS_PER_PAGE)
            .build());
        request.setAttribute("questions", questionsDTO);
        request.setAttribute("previousPage", page - 1);
        request.setAttribute("nextPage", questionsDTO.getQuestions().size() < QUESTIONS_PER_PAGE ? -1 : page + 1);
        request.setAttribute("count", questionFacade.countQuestions());
        request.getRequestDispatcher("/WEB-INF/views/questions.jsp").forward(request, response);
    }

}
