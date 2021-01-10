package underflowers.stackunderflow.application.question.vote;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.question.answer.AnswerFacade;
import underflowers.stackunderflow.application.question.answer.GiveAnswerCommand;
import underflowers.stackunderflow.application.question.answer.InvalidAnswerException;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticateCommand;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticationFailedException;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationCommand;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationFailedException;
import underflowers.stackunderflow.application.question.IncompleteQuestionException;
import underflowers.stackunderflow.application.question.ProposeQuestionCommand;
import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.domain.question.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;


import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Arquillian.class)
public class VoteFacadeTestIT {
    private final static String WARNAME = "arquillian-managed.war";

    @Inject
    ServiceRegistry serviceRegistry;

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        // Don't forget to fetch all package that will be used in our tests
        // Best way if we have lot of dependencies -> ShrinkWrap all pom.xml dependencies
        WebArchive archive = ShrinkWrap.create(WebArchive.class, WARNAME)
                .addPackages(true, "underflowers.stackunderflow")
                .addPackages(true, "io.underflowers.underification")
                .addPackages(true, "org.mindrot")
                .addPackages(true, "okhttp3")
                .addPackages(true, "com.google.gson")
                .addPackages(true, "okio")
                .addPackages(true, "io.gsonfire")
                .addPackages(true, "org.opentest4j");
        return archive;
    }

    private UserId uid;
    private QuestionId qid = new QuestionId();
    private AnswerId aid = new AnswerId();

    // NOTE: All the data is created multiple times since this function is called in all the following tests
    //       Ideally, we would use @BeforeAll but, it requires the function to be static and we need the ids
    private void createTestData() {
        IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        AnswerFacade answerFacade = serviceRegistry.getAnswerFacade();

        String fname = "Jane";
        String lname = "Doe";
        String email = fname + lname + "@" + System.currentTimeMillis() + ".lo";
        String pwd   = "secure";

        try {
            identityManagementFacade.register(RegistrationCommand.builder()
                    .firstname(fname)
                    .lastname(lname)
                    .email(email)
                    .clearPassword(pwd)
                    .build()
            );

            uid = identityManagementFacade.authenticate(AuthenticateCommand.builder()
                    .email(email)
                    .clearPassword(pwd)
                    .build()
            ).getUserId();

            questionFacade.proposeQuestion(ProposeQuestionCommand.builder()
                    .questionId(qid)
                    .authorId(uid)
                    .title("Second Course Grenadillo Smash Cherry Bomb")
                    .text("Cherries Italian linguine puttanesca mediterranean luxury bowl springtime strawberry basil picnic salad lime mango crisp chia seeds apples spicy dessert overflowing berries dragon fruit crumbled lentils lemon tahini dressing.")
                    .build()
            );

            answerFacade.giveAnswer(GiveAnswerCommand.builder()
                    .id(aid)
                    .authorId(uid)
                    .questionId(qid)
                    .text("You should add some avocado drizzle")
                    .build()
            );
        } catch (RegistrationFailedException | AuthenticationFailedException | IncompleteQuestionException | InvalidAnswerException e) {
            e.printStackTrace();
        }
    }

    private void vote(boolean isUpvote, boolean onQuestion) {
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();
        if (onQuestion) {
            assertDoesNotThrow(() -> voteFacade.vote(VoteCommand.builder()
                    .author(uid)
                    .relatedQuestion(qid)
                    .isUpvote(isUpvote)
                    .build()
            ));
        } else {
            assertDoesNotThrow(() -> voteFacade.vote(VoteCommand.builder()
                    .author(uid)
                    .relatedAnswer(aid)
                    .isUpvote(isUpvote)
                    .build()
            ));
        }
    }

    private VotesDTO getVotes(boolean onQuestion) {
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();
        if (onQuestion) {
            return voteFacade.getVotes(VotesQuery.builder()
                    .user(uid)
                    .relatedQuestion(qid)
                    .build());
        } else {
            return voteFacade.getVotes(VotesQuery.builder()
                    .user(uid)
                    .relatedAnswer(aid)
                    .build());
        }
    }

    @Test
    public void userCanUpvoteQuestionTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(true, true);
        VotesDTO v = getVotes(true);

        assertEquals(1, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isPresent());
        assertEquals(true, v.getIsAuthUserUpvote().get());
    }

    @Test
    public void userCanDownvoteQuestionTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(false, true);
        VotesDTO v = getVotes(true);

        assertEquals(-1, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isPresent());
        assertEquals(false, v.getIsAuthUserUpvote().get());
    }

    @Test
    public void userCanUpvoteAnswerTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(true, false);
        VotesDTO v = getVotes(false);

        assertEquals(1, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isPresent());
        assertEquals(true, v.getIsAuthUserUpvote().get());
    }

    @Test
    public void userCanDownvoteAnswerTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(false, false);
        VotesDTO v = getVotes(false);

        assertEquals(-1, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isPresent());
        assertEquals(false, v.getIsAuthUserUpvote().get());
    }

    @Test
    public void userCanSwitchDownToUpOnQuestionTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(false, true);
        vote(true, true);
        VotesDTO v = getVotes(true);

        assertEquals(1, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isPresent());
        assertEquals(true, v.getIsAuthUserUpvote().get());
    }

    @Test
    public void userCanSwitchUpToDownOnQuestionTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(true, true);
        vote(false, true);
        VotesDTO v = getVotes(true);

        assertEquals(-1, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isPresent());
        assertEquals(false, v.getIsAuthUserUpvote().get());
    }

    @Test
    public void userCanSwitchDownToUpOnAnswerTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(false, false);
        vote(true, false);
        VotesDTO v = getVotes(false);

        assertEquals(1, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isPresent());
        assertEquals(true, v.getIsAuthUserUpvote().get());
    }

    @Test
    public void userCanSwitchUpToDownOnAnswerTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(true, false);
        vote(false, false);
        VotesDTO v = getVotes(false);

        assertEquals(-1, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isPresent());
        assertEquals(false, v.getIsAuthUserUpvote().get());
    }


    @Test
    public void userCanRemoveUpvoteOnQuestionTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(true, true);
        vote(true, true);
        VotesDTO v = getVotes(true);

        assertEquals(0, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isEmpty());
    }

    @Test
    public void userCanRemoveDownvoteOnQuestionTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(false, true);
        vote(false, true);
        VotesDTO v = getVotes(true);

        assertEquals(0, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isEmpty());
    }

    @Test
    public void userCanRemoveUpvoteOnAnswerTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(true, false);
        vote(true, false);
        VotesDTO v = getVotes(false);

        assertEquals(0, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isEmpty());
    }

    @Test
    public void userCanRemoveDownvoteOnAnswerTest() {
        createTestData();
        VoteFacade voteFacade = serviceRegistry.getVoteFacade();

        vote(false, false);
        vote(false, false);
        VotesDTO v = getVotes(false);

        assertEquals(0, v.getCount());
        assertTrue(v.getIsAuthUserUpvote().isEmpty());
    }
}