package underflowers.stackunderflow.ui.web.profile;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.question.answer.AnswerFacade;
import underflowers.stackunderflow.application.question.answer.AnswersDTO;
import underflowers.stackunderflow.application.question.answer.AnswersQuery;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.application.question.QuestionsDTO;
import underflowers.stackunderflow.application.question.QuestionsQuery;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    @Inject
    ServiceRegistry serviceRegistry;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        AnswerFacade answerFacade = serviceRegistry.getAnswerFacade();

        request.getSession().removeAttribute("errors");

        // Updated profile successfully
        if(request.getSession().getAttribute("updated") != null){
            request.getSession().removeAttribute("updated");
            request.setAttribute("updated", true);
        }

        // Retrieve current user
        AuthenticatedUserDTO currentUser = (AuthenticatedUserDTO) request.getSession().getAttribute("authUser");
        request.setAttribute("user", currentUser);

        // User's questions
        QuestionsQuery query = QuestionsQuery.builder()
                .authorId(currentUser.getUuid())
                .limit(5)
                .build();
        QuestionsDTO userQuestions = questionFacade.getQuestions(query);
        request.setAttribute("questions", userQuestions);
        query.setLimit(0);
        request.setAttribute("questionsCount", questionFacade.countQuestions(query));

        // User's answers count
        AnswersDTO userAnswers = answerFacade.getAnswers(AnswersQuery.builder().authUser(currentUser.getUuid()).build());
        request.setAttribute("answersCount", userAnswers.getAnswers().size());
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}
