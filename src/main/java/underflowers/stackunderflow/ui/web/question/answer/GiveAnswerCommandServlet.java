package underflowers.stackunderflow.ui.web.question.answer;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.question.answer.AnswerFacade;
import underflowers.stackunderflow.application.question.answer.GiveAnswerCommand;
import underflowers.stackunderflow.application.question.answer.InvalidAnswerException;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.domain.question.QuestionId;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AnswerQuestionCommandServlet", urlPatterns ="/answer.do")
public class GiveAnswerCommandServlet extends HttpServlet {
    @Inject
    ServiceRegistry serviceRegistry;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AnswerFacade answerFacade = serviceRegistry.getAnswerFacade();

        request.getSession().removeAttribute("errors");

        AuthenticatedUserDTO currentUser = (AuthenticatedUserDTO) request.getSession().getAttribute("authUser");
        GiveAnswerCommand command = GiveAnswerCommand.builder()
                .authorId(currentUser.getUserId())
                .questionId(new QuestionId(request.getParameter("questionUuid")))
                .text(request.getParameter("content"))
                .build();

        try {
            answerFacade.giveAnswer(command);
        } catch (InvalidAnswerException e) {
            request.getSession().setAttribute("errors", List.of(e.getMessage()));
        }
        response.sendRedirect("/question?uuid=" + request.getParameter("questionUuid"));
    }
}
