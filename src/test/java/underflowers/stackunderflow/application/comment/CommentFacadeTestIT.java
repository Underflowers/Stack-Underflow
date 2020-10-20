package underflowers.stackunderflow.application.comment;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.answer.AnswerFacade;
import underflowers.stackunderflow.application.answer.GiveAnswerCommand;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticateCommand;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticationFailedException;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationCommand;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationFailedException;
import underflowers.stackunderflow.application.question.IncompleteQuestionException;
import underflowers.stackunderflow.application.question.ProposeQuestionCommand;
import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;


import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Arquillian.class)
public class CommentFacadeTestIT {
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
    private AnswerId aid = new AnswerId();

    // NOTE: All the data is created multiple times since this function is called in all the following tests
    //       Ideally, we would use @BeforeAll but, it requires the function to be static and we need the ids
    private void createTestData() {
        IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();
        QuestionFacade questionFacade = serviceRegistry.getQuestionFacade();
        AnswerFacade answerFacade = serviceRegistry.getAnswerFacade();
        CommentFacade commentFacade = serviceRegistry.getCommentFacade();

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
            ).getUuid();

            questionFacade.proposeQuestion(ProposeQuestionCommand.builder()
                    .questionUUID(qid)
                    .authorUUID(uid)
                    .title("Second Course Grenadillo Smash Cherry Bomb")
                    .text("Cherries Italian linguine puttanesca mediterranean luxury bowl springtime strawberry basil picnic salad lime mango crisp chia seeds apples spicy dessert overflowing berries dragon fruit crumbled lentils lemon tahini dressing.")
                    .build()
            );

            answerFacade.giveAnswer(GiveAnswerCommand.builder()
                    .uuid(aid)
                    .authorUUID(uid)
                    .questionUUID(qid)
                    .text("You should add some avocado drizzle")
                    .build()
            );

            commentFacade.comment(CommentCommand.builder()
                    .authorId(uid)
                    .questionId(qid)
                    .answerId(null)
                    .content("Dude what is wrong with you?")
                    .build()
            );

            commentFacade.comment(CommentCommand.builder()
                    .authorId(uid)
                    .questionId(null)
                    .answerId(aid)
                    .content("+1")
                    .build()
            );
        } catch (RegistrationFailedException | AuthenticationFailedException | IncompleteQuestionException | InvalidCommentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void userCanCommentOnQuestionTest() {
        createTestData();
        CommentFacade commentFacade = serviceRegistry.getCommentFacade();

        assertDoesNotThrow(() -> commentFacade.comment(CommentCommand.builder()
                .authorId(uid)
                .questionId(qid)
                .answerId(null)
                .content("Test if the user can comment on a question")
                .build()
        ));
    }


    @Test
    public void userCanCommentOnAnswerTest() {
        createTestData();
        CommentFacade commentFacade = serviceRegistry.getCommentFacade();

        assertDoesNotThrow(() -> commentFacade.comment(CommentCommand.builder()
                .authorId(uid)
                .questionId(null)
                .answerId(aid)
                .content("Test if a user can comment on an answer")
                .build()
        ));
    }

    @Test
    public void getAllCommentsRelatedToQuestionTest() {
        createTestData();
        CommentFacade commentFacade = serviceRegistry.getCommentFacade();

        int nbCommentsToAdd = 3;
        int nbCommentsBeforeTest = commentFacade.getQuestionComments(qid).getComments().size();

        try {
            for (int i = 0; i < nbCommentsToAdd; ++i)
                commentFacade.comment(CommentCommand.builder()
                        .authorId(uid)
                        .questionId(qid)
                        .answerId(null)
                        .content("Adding comments")
                        .build()
                );
        } catch (InvalidCommentException e) {
            e.printStackTrace();
        }

        assertEquals(nbCommentsBeforeTest + nbCommentsToAdd, commentFacade.getQuestionComments(qid).getComments().size());
    }

    @Test
    public void getAllCommentsRelatedToAnswerTest() {
        createTestData();
        CommentFacade commentFacade = serviceRegistry.getCommentFacade();

        int nbCommentsToAdd = 3;
        int nbCommentsBeforeTest = commentFacade.getAnswerComments(aid).getComments().size();

        try {
            for (int i = 0; i < nbCommentsToAdd; ++i)
                commentFacade.comment(CommentCommand.builder()
                        .authorId(uid)
                        .questionId(null)
                        .answerId(aid)
                        .content("Adding comments")
                        .build()
                );
        } catch (InvalidCommentException e) {
            e.printStackTrace();
        }

        assertEquals(nbCommentsBeforeTest + nbCommentsToAdd, commentFacade.getAnswerComments(aid).getComments().size());
    }
}
