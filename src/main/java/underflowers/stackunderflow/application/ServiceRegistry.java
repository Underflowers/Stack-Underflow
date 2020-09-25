package underflowers.stackunderflow.application;

import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.infrastucture.persistence.memory.InMemoryQuestionRepository;

public class ServiceRegistry {
    private static ServiceRegistry singleton; // Will be replaced soon ( 35:00 )

    // Will be replaced with dependency injection
    private static IQuestionRepository questionRepository;
    private static QuestionFacade questionFacade;

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
    }

    public static QuestionFacade getQuestionFacade() {
        return questionFacade;
    }
}
