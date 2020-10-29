package underflowers.stackunderflow.ui.web.question.vote;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.application.question.vote.InvalidVoteException;
import underflowers.stackunderflow.application.question.vote.VoteCommand;
import underflowers.stackunderflow.application.question.vote.VoteFacade;
import underflowers.stackunderflow.domain.question.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "VoteCommandServlet", urlPatterns ="/vote.do")
public class VoteCommandServlet extends HttpServlet {
    @Inject
    ServiceRegistry serviceRegistry;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        request.getSession().removeAttribute("errors");

        QuestionId questionId = null;
        AnswerId answerId = null;

        if (!request.getParameter("questionUuid").isEmpty()) {
            questionId = new QuestionId(request.getParameter("questionUuid"));
        }

        if (!request.getParameter("answerUuid").isEmpty()) {
            String aaa = request.getParameter("answerUuid");
            answerId = new AnswerId(aaa);
        }

        AuthenticatedUserDTO currentUser = (AuthenticatedUserDTO) request.getSession().getAttribute("authUser");

        VoteCommand command = VoteCommand.builder()
                .author(currentUser.getUserId())
                .relatedQuestion(questionId)
                .relatedAnswer(answerId)
                .isUpvote(request.getParameter("voteType").equals("up"))
                .build();

        try {
            voteFacade.vote(command);
        } catch (InvalidVoteException e) {
            request.getSession().setAttribute("errors", List.of(e.getMessage()));
        }

        response.sendRedirect("/question?uuid=" + request.getParameter("redirectUuid"));
    }
}
