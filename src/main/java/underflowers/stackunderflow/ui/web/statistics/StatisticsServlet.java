package underflowers.stackunderflow.ui.web.statistics;

import io.underflowers.underification.ApiException;
import io.underflowers.underification.api.PointScaleApiControllerApi;
import io.underflowers.underification.api.UsersApiControllerApi;
import io.underflowers.underification.api.dto.LeaderBoardEntry;
import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.authenticate.LeaderboardDTO;
import underflowers.stackunderflow.application.identitymgmt.authenticate.PublicUserDTO;
import underflowers.stackunderflow.application.question.answer.AnswerFacade;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.domain.user.User;
import underflowers.stackunderflow.domain.user.UserId;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "StatisticsServlet", urlPatterns = "/statistics")
public class StatisticsServlet extends javax.servlet.http.HttpServlet {

    @Inject
    ServiceRegistry serviceRegistry;

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        AnswerFacade answerFacade = serviceRegistry.getAnswerFacade();

        // Global statistics

        int usersCount = identityManagementFacade.countUsers();
        int questionsCount = questionFacade.countQuestions();
        int answersCount = answerFacade.countAnswers();

        request.setAttribute("usersCount", usersCount);
        request.setAttribute("questionsCount", questionsCount);
        request.setAttribute("answersCount", answersCount);

        // Leaderboards

        List<LeaderboardDTO.LeaderboardEntryDTO> activityEntries = new ArrayList<>();
        List<LeaderboardDTO.LeaderboardEntryDTO> reputationEntries = new ArrayList<>();

        try {
            PointScaleApiControllerApi pointScaleApiControllerApi = new PointScaleApiControllerApi();
            List<LeaderBoardEntry> activityLeaderboardRaw = pointScaleApiControllerApi.getLeaderBoard("Activity", 20);
            List<LeaderBoardEntry> reputationLeaderboardRaw = pointScaleApiControllerApi.getLeaderBoard("Reputation", 20);

            // Retrieve leaderboard's users
            for(LeaderBoardEntry e : activityLeaderboardRaw) {
                PublicUserDTO user = identityManagementFacade.getPublicUser(new UserId(e.getAppUserId()));
                activityEntries.add(LeaderboardDTO.LeaderboardEntryDTO.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .score(e.getScore())
                        .build());
            }
            for(LeaderBoardEntry e : reputationLeaderboardRaw) {
                PublicUserDTO user = identityManagementFacade.getPublicUser(new UserId(e.getAppUserId()));
                reputationEntries.add(LeaderboardDTO.LeaderboardEntryDTO.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .score(e.getScore())
                        .build());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

        request.setAttribute("activityLeaderboard", LeaderboardDTO.builder()
                .name("Activity")
                .entries(activityEntries).build()
        );
        request.setAttribute("reputationLeaderboard", LeaderboardDTO.builder()
                .name("Reputation")
                .entries(reputationEntries).build()
        );

        request.getRequestDispatcher("/WEB-INF/views/statistics.jsp").forward(request, response);
    }
}
