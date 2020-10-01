package underflowers.stackunderflow.application;

import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.infrastucture.persistence.memory.InMemoryQuestionRepository;
import underflowers.stackunderflow.infrastucture.persistence.memory.InMemoryUserRepository;

public class ServiceRegistry {
    private static ServiceRegistry singleton; // Will be replaced soon ( 35:00 )

    // Will be replaced with dependency injection
    private static IQuestionRepository questionRepository;
    private static QuestionFacade questionFacade;
    private static IUserRepository userRepository;
    private static IdentityManagementFacade identityManagementFacade;

    public static ServiceRegistry getServiceRegistry() {
        if (singleton == null) {
            singleton = new ServiceRegistry();
        }

        return singleton;
    }

    private ServiceRegistry() {
        singleton = this;
        questionRepository = new InMemoryQuestionRepository();
        questionFacade = new QuestionFacade(questionRepository);
        userRepository = new InMemoryUserRepository();
        identityManagementFacade = new IdentityManagementFacade(userRepository);
    }

    public static QuestionFacade getQuestionFacade() {
        return questionFacade;
    }

    public static IdentityManagementFacade getIdentityManagementFacade() {
        return identityManagementFacade;
    }
}
