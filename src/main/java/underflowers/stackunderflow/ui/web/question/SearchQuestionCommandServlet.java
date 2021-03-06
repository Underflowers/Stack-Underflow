package underflowers.stackunderflow.ui.web.question;

import underflowers.stackunderflow.application.ServiceRegistry;
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

@WebServlet(name = "SearchQuestionCommand", urlPatterns ="/search.do")
public class SearchQuestionCommandServlet extends HttpServlet {

    @Inject
    ServiceRegistry serviceRegistry;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();

        QuestionsQuery query = QuestionsQuery.builder()
                .searchTerm(req.getParameter("search"))
                .build();
        QuestionsDTO questionsDTO = questionFacade.getQuestions(query);
        req.setAttribute("questions", questionsDTO);
        req.setAttribute("count", questionFacade.countQuestions(query));
        req.setAttribute("searchTerm", req.getParameter("search"));
        req.getRequestDispatcher("/WEB-INF/views/searchQuestionResult.jsp").forward(req, resp);
    }

}
