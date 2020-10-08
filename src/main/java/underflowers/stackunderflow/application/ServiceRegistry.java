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

    @Inject @Named("InMemoryQuestionRepository") // Inject equivalent à @EJB
    IQuestionRepository questionRepository;

    @Inject @Named("JdbcUserRepository")
    IUserRepository userRepository;

    private static QuestionFacade questionFacade;
    private static IdentityManagementFacade identityManagementFacade;
    /*
    public static ServiceRegistry getServiceRegistry() {
        if (singleton == null) {
            singleton = new ServiceRegistry();
        }

        return singleton;
    }
    */

    /*
    private ServiceRegistry() {
        singleton = this;
        questionFacade = new QuestionFacade(questionRepository);
        identityManagementFacade = new IdentityManagementFacade(userRepository);
    }
     */

    public static QuestionFacade getQuestionFacade() {
        return questionFacade;
    }

    public IdentityManagementFacade getIdentityManagementFacade() {
        return new IdentityManagementFacade(userRepository);
    }
}
