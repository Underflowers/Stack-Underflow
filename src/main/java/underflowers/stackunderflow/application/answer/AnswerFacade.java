package underflowers.stackunderflow.application.answer;

import underflowers.stackunderflow.application.comment.CommentFacade;
import underflowers.stackunderflow.application.question.IncompleteQuestionException;
import underflowers.stackunderflow.domain.answer.Answer;
import underflowers.stackunderflow.domain.answer.IAnswerRepository;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.domain.user.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerFacade {
    private IAnswerRepository answerRepository;
    private IQuestionRepository questionRepository;
    private IUserRepository userRepository;
    private CommentFacade commentFacade;


    public AnswerFacade(IAnswerRepository answerRepository, IQuestionRepository questionRepository, IUserRepository userRepository, CommentFacade commentFacade) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.commentFacade = commentFacade;
    }

    public void giveAnswer(GiveAnswerCommand command) throws IncompleteQuestionException {
        try {
            Answer givenAnswer = Answer.builder()
                    .id(command.getUuid())
                    .authorUUID(command.getAuthorUUID())
                    .questionUUID(command.getQuestionUUID())
                    .content(command.getText())
                    .createdAt(LocalDateTime.now())
                    .build();
            answerRepository.save(givenAnswer);
        } catch (Exception e) {
            throw new IncompleteQuestionException(e.getMessage());
        }
    }

    public AnswersDTO getAnswers(AnswersQuery query) {
        Collection<Answer> allAnswers = answerRepository.find(query);

        List<AnswersDTO.AnswerDTO> allAnswersDTO = allAnswers.stream().map(answer -> {
                    User author = userRepository.findById(answer.getAuthorUUID()).get();

                    return AnswersDTO.AnswerDTO.builder()
                            .uuid(answer.getId().getId())
                            .author(author.getFirstname() + " " + author.getLastname())
                            .content(answer.getContent())
                            .createdAt(answer.getCreatedAt())
                            .comments(commentFacade.getAnswerComments(answer.getId()))
                            .build();
                }
        ).collect(Collectors.toList());

        return AnswersDTO.builder()
                .answers(allAnswersDTO)
                .build();
    }

    public int countAnswers() {
        return this.answerRepository.count();
    }
}
