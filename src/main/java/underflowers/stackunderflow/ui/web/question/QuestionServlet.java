package underflowers.stackunderflow.ui.web.question;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.application.question.GetQuestionQuery;
import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.application.question.QuestionsDTO;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

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

        QuestionId questionId = new QuestionId(request.getParameter("uuid"));
        UserId authUser = null;

        if (request.getSession().getAttribute("authUser") != null) {
            authUser = ((AuthenticatedUserDTO) request.getSession().getAttribute("authUser")).getUuid();
        }

        // Retrieve question
        QuestionsDTO.QuestionDTO questionDTO = questionFacade.getQuestion(
                GetQuestionQuery.builder()
                        .id(questionId)
                        .authUser(authUser)
                        .build());
        request.setAttribute("question", questionDTO);

        request.getRequestDispatcher("/WEB-INF/views/question.jsp").forward(request, response);
    }
}
