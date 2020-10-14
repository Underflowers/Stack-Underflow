package underflowers.stackunderflow.application;

import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.question.QuestionFacade;
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

    private static QuestionFacade questionFacade;
    private static IdentityManagementFacade identityManagementFacade;

    public QuestionFacade getQuestionFacade() {
        return new QuestionFacade(questionRepository, userRepository);
    }

    public IdentityManagementFacade getIdentityManagementFacade() {
        return new IdentityManagementFacade(userRepository);
    }
}
