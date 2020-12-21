package underflowers.stackunderflow.ui.web.profile;

import io.underflowers.underification.ApiClient;
import io.underflowers.underification.ApiException;
import io.underflowers.underification.Configuration;
import io.underflowers.underification.api.UsersApiControllerApi;
import io.underflowers.underification.api.dto.Badge;
import io.underflowers.underification.api.dto.UserScore;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .authorId(currentUser.getUserId())
                .limit(5)
                .build();
        QuestionsDTO userQuestions = questionFacade.getQuestions(query);
        request.setAttribute("questions", userQuestions);
        query.setLimit(0);
        request.setAttribute("questionsCount", questionFacade.countQuestions(query));

        // User's answers count
        AnswersDTO userAnswers = answerFacade.getAnswers(AnswersQuery.builder().authUserId(currentUser.getUserId()).build());
        request.setAttribute("answersCount", userAnswers.getAnswers().size());

        // Call the gameification API to fetch all scales scores and badges earned
        List<UserScore> scores = new ArrayList<>();
        List<Badge> badges = new ArrayList<>();
        UsersApiControllerApi usersApiControllerApi = new UsersApiControllerApi();
        try {
            scores = usersApiControllerApi.getUserScores(currentUser.getUserId().asString());
            badges = usersApiControllerApi.getUserBadges(currentUser.getUserId().asString());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        request.setAttribute("scores", scores);

        // create hashmap with <badgeCount, badge> to pass to the view
        Map<Badge, Integer> mapBadges = new HashMap<>();
        for(Badge b1 : badges){
            boolean found = false;
            for(Badge b2 : mapBadges.keySet()) {
                if(b1.getName().equals(b2.getName())){
                    // Increment the badgeCount
                    mapBadges.replace(b2, mapBadges.get(b2), mapBadges.get(b2) + 1);
                    found = true;
                    break;
                }
            }
            // Add new badge with count of 1
            if(!found) {
                mapBadges.put(b1, 1);
            }
        }

        request.setAttribute("badges", mapBadges);

        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}
