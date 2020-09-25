package underflowers.stackunderflow.application.question;

import lombok.Builder;
import underflowers.stackunderflow.domain.question.IQuestionRepository;

public class QuestionFacade {

    private IQuestionRepository questionRepository;

    public QuestionFacade(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    // TODO (video 31:00)
}
