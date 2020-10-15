package underflowers.stackunderflow.ui.web.question.comment;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.comment.CommentCommand;
import underflowers.stackunderflow.application.comment.CommentFacade;
import underflowers.stackunderflow.application.comment.InvalidCommentException;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CommentCommandServlet", urlPatterns ="/comment.do")
public class CommentCommandServlet extends HttpServlet {
    @Inject
    ServiceRegistry serviceRegistry;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommentFacade commentFacade = serviceRegistry.getCommentFacade();

        request.getSession().removeAttribute("errors");

        // if the question is related to an answer, we don't want to add the question uuid to the comment
        QuestionId questionId = new QuestionId(request.getParameter("questionUuid"));
        AnswerId answerId = null;

        if (!request.getParameter("answerUuid").isEmpty()) {
            answerId = new AnswerId(request.getParameter("answerUuid"));
            questionId = null;
        }

        AuthenticatedUserDTO currentUser = (AuthenticatedUserDTO) request.getSession().getAttribute("authUser");

        CommentCommand command = CommentCommand.builder()
                .authorId(currentUser.getUuid())
                .answerId(answerId)
                .questionId(questionId)
                .content(request.getParameter("content"))
                .build();

        try {
            commentFacade.comment(command);
        } catch (InvalidCommentException e) {
            request.getSession().setAttribute("errors", List.of(e.getMessage()));
        }

        response.sendRedirect("/question?uuid=" + request.getParameter("questionUuid"));
    }
}
