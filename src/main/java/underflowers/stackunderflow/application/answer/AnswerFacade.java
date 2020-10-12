package underflowers.stackunderflow.application.answer;

import underflowers.stackunderflow.application.question.IncompleteQuestionException;
import underflowers.stackunderflow.domain.answer.Answer;
import underflowers.stackunderflow.domain.answer.IAnswerRepository;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.user.IUserRepository;

public class AnswerFacade {
    private IAnswerRepository answerRepository;
    private IQuestionRepository questionRepository;
    private IUserRepository userRepository;

    public AnswerFacade(IAnswerRepository answerRepository, IQuestionRepository questionRepository, IUserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public void giveAnswer(GiveAnswerCommand command) throws IncompleteQuestionException {
        try {
            Answer givenAnswer = Answer.builder()
                    .authorUUID(command.getAuthorUUID())
                    .questionUUID(command.getQuestionUUID())
                    .content(command.getText())
                    .build();
            answerRepository.save(givenAnswer);
        } catch (Exception e) {
            throw new IncompleteQuestionException(e.getMessage());
        }
    }
}
