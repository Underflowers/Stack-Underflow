package underflowers.stackunderflow.application.answer;

import underflowers.stackunderflow.application.question.IncompleteQuestionException;
import underflowers.stackunderflow.application.question.QuestionsDTO;
import underflowers.stackunderflow.domain.answer.Answer;
import underflowers.stackunderflow.domain.answer.IAnswerRepository;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.domain.user.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public AnswersDTO getAnswers(QuestionId questionId) {
        Collection<Answer> allAnswers = answerRepository.find(questionId);

        List<AnswersDTO.AnswerDTO> allAnswersDTO = allAnswers.stream().map(answer -> {
                    User author = userRepository.findById(answer.getAuthorUUID()).get();

                    return AnswersDTO.AnswerDTO.builder()
                            .uuid(answer.getId().getId())
                            .questionUuid(questionId.getId())
                            .author(author.getFirstname() + " " + author.getLastname())
                            .content(answer.getContent())
                            .build();
                }
        ).collect(Collectors.toList());

        return AnswersDTO.builder()
                .answers(allAnswersDTO)
                .build();
    }
}
