package underflowers.stackunderflow.application.answer;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticateCommand;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticationFailedException;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationCommand;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationFailedException;
import underflowers.stackunderflow.application.question.*;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(Arquillian.class)
public class AnswerFacadeTestIT {
    private final static String WARNAME = "arquillian-managed.war";

    @Inject
    ServiceRegistry serviceRegistry;

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        // Don't forget to fetch all package that will be used in our tests
        // Best way if we have lot of dependencies -> ShrinkWrap all pom.xml dependencies
        WebArchive archive = ShrinkWrap.create(WebArchive.class, WARNAME)
                .addPackages(true, "underflowers.stackunderflow")
                .addPackages(true, "org.mindrot")
                .addPackages(true, "org.opentest4j");
        return archive;
    }

    private UserId uid;
    private QuestionId qid = new QuestionId();

    // NOTE: All the data is created multiple times since this function is called in all the following tests
    //       Ideally, we would use @BeforeAll but, it requires the function to be static and we need the ids
    private void createTestData() {
        IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();

        String fname = "John";
        String lname = "Doe";
        String email = fname + lname + "@" + System.currentTimeMillis() + ".ch";
        String pwd   = "secure";

        try {
            // Create test user
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
            ).getUuid();

            // Create a question
            questionFacade.proposeQuestion(ProposeQuestionCommand.builder()
                    .questionUUID(qid)
                    .authorUUID(uid)
                    .title("What is the meaning of life?")
                    .text("I really don't get it, please someone clarify this nonsense. Thank you.")
                    .build()
            );
        } catch (RegistrationFailedException | AuthenticationFailedException | IncompleteQuestionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void userGivesAnswerToQuestion() {
        createTestData();
        AnswerFacade answerFacade = serviceRegistry.getAnswerFacade();

        assertDoesNotThrow(() -> answerFacade.giveAnswer(
                GiveAnswerCommand.builder()
                        .questionUUID(qid)
                        .authorUUID(uid)
                        .text("I am kindly giving an answer to this question")
                        .build()
        ));
    }

    @Test
    public void getAllAnswersOfQuestion() {
        createTestData();
        AnswerFacade answerFacade = serviceRegistry.getAnswerFacade();

        int nbAnswersToAdd = 3;
        int nbAnswersBeforeTest = answerFacade.getAnswers(AnswersQuery.builder()
                .questionId(qid)
                .build()
        ).getAnswers().size();

        try {
            for (int i = 0; i < nbAnswersToAdd; ++i)
                answerFacade.giveAnswer(GiveAnswerCommand.builder()
                        .authorUUID(uid)
                        .questionUUID(qid)
                        .text("I am kindly giving an answer to this question #" + (i + 1))
                        .build()
                );
        } catch (InvalidAnswerException e) {
            e.printStackTrace();
        }

        AnswersQuery answersQuery = AnswersQuery.builder()
                .questionId(qid)
                .build();
        assertEquals(nbAnswersBeforeTest + nbAnswersToAdd, answerFacade.getAnswers(answersQuery).getAnswers().size());
    }

    @Test
    public void getAllAnswersOfUser() {
        createTestData();
        AnswerFacade answerFacade = serviceRegistry.getAnswerFacade();

        int nbAnswersToAdd = 3;
        int nbAnswersBeforeTest = answerFacade.getAnswers(AnswersQuery.builder()
                .authorId(uid)
                .build()
        ).getAnswers().size();

        try {
            for (int i = 0; i < nbAnswersToAdd; ++i)
                answerFacade.giveAnswer(GiveAnswerCommand.builder()
                        .authorUUID(uid)
                        .questionUUID(qid)
                        .text("I am kindly giving an answer to this question #" + (i + 1))
                        .build()
                );
        } catch (InvalidAnswerException e) {
            e.printStackTrace();
        }

        AnswersQuery answersQuery = AnswersQuery.builder()
                .authorId(uid)
                .build();
        assertEquals(nbAnswersBeforeTest + nbAnswersToAdd, answerFacade.getAnswers(answersQuery).getAnswers().size());
    }
}
