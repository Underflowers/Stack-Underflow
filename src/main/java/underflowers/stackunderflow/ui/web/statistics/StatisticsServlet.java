package underflowers.stackunderflow.ui.web.statistics;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.question.answer.AnswerFacade;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.question.QuestionFacade;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "StatisticsServlet", urlPatterns = "/statistics")
public class StatisticsServlet extends javax.servlet.http.HttpServlet {

    @Inject
    ServiceRegistry serviceRegistry;

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        AnswerFacade answerFacade = serviceRegistry.getAnswerFacade();

        int usersCount = identityManagementFacade.countUsers();
        int questionsCount = questionFacade.countQuestions();
        int answersCount = answerFacade.countAnswers();

        request.setAttribute("usersCount", usersCount);
        request.setAttribute("questionsCount", questionsCount);
        request.setAttribute("answersCount", answersCount);

        request.getRequestDispatcher("/WEB-INF/views/statistics.jsp").forward(request, response);
    }
}
