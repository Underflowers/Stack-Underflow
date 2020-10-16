package underflowers.stackunderflow.application;

import underflowers.stackunderflow.application.answer.AnswerFacade;
import underflowers.stackunderflow.application.comment.CommentFacade;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.domain.answer.IAnswerRepository;
import underflowers.stackunderflow.domain.comment.ICommentRepository;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.user.IUserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped // Equivalent à singleton (avec EJB), a la demande cela instancie cette classe mais une seule dans l'app
public class ServiceRegistry {

    @Inject @Named("JdbcQuestionRepository") // Inject equivalent à @EJB
    IQuestionRepository questionRepository;

    @Inject @Named("JdbcUserRepository")
    IUserRepository userRepository;

    @Inject @Named("JdbcAnswerRepository")
    IAnswerRepository answerRepository;

    @Inject @Named("JdbcCommentRepository")
    ICommentRepository commentRepository;

    private static QuestionFacade questionFacade;
    private static IdentityManagementFacade identityManagementFacade;

    public AnswerFacade getAnswerFacade() {
        return new AnswerFacade(answerRepository, questionRepository, userRepository, getCommentFacade());
    }

    public QuestionFacade getQuestionFacade() {
        return new QuestionFacade(questionRepository, userRepository, getAnswerFacade(), getCommentFacade());
    }

    public IdentityManagementFacade getIdentityManagementFacade() {
        return new IdentityManagementFacade(userRepository);
    }

    public CommentFacade getCommentFacade() {
        return new CommentFacade(commentRepository, userRepository);
    }
}
