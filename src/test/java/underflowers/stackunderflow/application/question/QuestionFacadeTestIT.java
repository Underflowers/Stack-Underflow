package underflowers.stackunderflow.application.question;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticateCommand;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticationFailedException;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationCommand;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationFailedException;
import underflowers.stackunderflow.domain.user.UserId;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Arquillian.class)
public class QuestionFacadeTestIT {

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

    /**
     * Create a user in DB that will be used for all tests
     * @return UserId that is the user UUID of the created user
     *
     * /!\ Best way to use @breforeClass test method (static) but
     * cannot use dependency injection into static method ...
     */
    private UserId createUniqueUserForTest(){
        String firstname = "john";
        String lastname = "doe";
        String email = firstname + lastname + "@" + System.currentTimeMillis() + ".com";
        String password = "john";

        IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();
        RegistrationCommand registrationCommand = RegistrationCommand.builder()
                .email(email)
                .firstname(firstname)
                .lastname(lastname)
                .clearPassword(password)
                .build();

        AuthenticateCommand authenticateCommand = AuthenticateCommand.builder()
                .email(email)
                .clearPassword(password)
                .build();

        try {
            identityManagementFacade.register(registrationCommand);
            AuthenticatedUserDTO user = identityManagementFacade.authenticate(authenticateCommand);
            return user.getUserId();
        } catch (RegistrationFailedException | AuthenticationFailedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Test
    public void userCanCreateQuestion() {
        UserId userId = createUniqueUserForTest();

        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        ProposeQuestionCommand command = ProposeQuestionCommand.builder()
                .authorId(userId)
                .title("Hey, how are you today ?")
                .text("Let me now more about you")
                .build();

        assertDoesNotThrow(() -> questionFacade.proposeQuestion(command));
    }

    @Test
    public void userCanCreateAndFetchQuestions() {
        UserId userId = createUniqueUserForTest();

        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        ProposeQuestionCommand questionCommand1 = ProposeQuestionCommand.builder()
                .authorId(userId)
                .title("Question 1")
                .text("Content 1")
                .build();
        ProposeQuestionCommand questionCommand2 = ProposeQuestionCommand.builder()
                .authorId(userId)
                .title("Question 2")
                .text("Question 2")
                .build();

        // We create 2 questions
        assertDoesNotThrow(() -> questionFacade.proposeQuestion(questionCommand1));
        assertDoesNotThrow(() -> questionFacade.proposeQuestion(questionCommand2));

        // We fetch all questions, it must be at least 2 questions
        QuestionsDTO questionsDTO = questionFacade.getQuestions(QuestionsQuery.builder()
                .isAnswered(false)
                .build());

        assertTrue(questionsDTO.getQuestions().size() >= 2);
    }

    @Test
    public void countQuestions() {
        UserId userId = createUniqueUserForTest();

        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        ProposeQuestionCommand questionCommand = ProposeQuestionCommand.builder()
                .authorId(userId)
                .title("Question 1")
                .text("Content 1")
                .build();

            assertDoesNotThrow(() -> {
                // Fetch all questions count
                int oldQuestions = questionFacade.countQuestions();
                // Propose one question
                questionFacade.proposeQuestion(questionCommand);
                // Count again and assert
                int afterCount = questionFacade.countQuestions();
                assertEquals(oldQuestions + 1, afterCount);
            });
    }

    @Test
    public void searchQuestion(){
        UserId userId = createUniqueUserForTest();

        String searchTerm = String.valueOf(System.currentTimeMillis());
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        ProposeQuestionCommand questionCommand = ProposeQuestionCommand.builder()
                .authorId(userId)
                .title("Question " + searchTerm + " test")
                .text("Content of the question")
                .build();

        assertDoesNotThrow(() -> {
            // Ask the question contains the search term in title
            questionFacade.proposeQuestion(questionCommand);
            QuestionsDTO questionsDTO = questionFacade.getQuestions(QuestionsQuery.builder().searchTerm(searchTerm).build());
            // We want at least 1 question result for the search term
            assertTrue(questionsDTO.getQuestions().size() >= 1);
        });
    }

}

