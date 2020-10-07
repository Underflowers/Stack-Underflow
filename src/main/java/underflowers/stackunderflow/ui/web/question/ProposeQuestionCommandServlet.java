package underflowers.stackunderflow.ui.web.question;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.application.question.ProposeQuestionCommand;
import underflowers.stackunderflow.application.question.QuestionFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProposeQuestionCommandServlet", urlPatterns ="/ask.do")
public class ProposeQuestionCommandServlet extends HttpServlet {

    private ServiceRegistry serviceRegistry = ServiceRegistry.getServiceRegistry();
    private QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthenticatedUserDTO currentUser = (AuthenticatedUserDTO) request.getSession().getAttribute("authUser");
        ProposeQuestionCommand command = ProposeQuestionCommand.builder()
                .author(currentUser.getFirstname() + " " + currentUser.getLastname())
                .title(request.getParameter("title"))
                .text(request.getParameter("content"))
                .build();

        questionFacade.proposeQuestion(command);
        response.sendRedirect("/questions");
    }
}
