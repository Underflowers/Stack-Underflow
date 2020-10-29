package underflowers.stackunderflow.application.question.answer;

import underflowers.stackunderflow.application.question.comment.CommentFacade;
import underflowers.stackunderflow.application.question.vote.VotesQuery;
import underflowers.stackunderflow.application.question.vote.VoteFacade;
import underflowers.stackunderflow.domain.question.answer.Answer;
import underflowers.stackunderflow.domain.question.answer.IAnswerRepository;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
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
    private VoteFacade voteFacade;


    public AnswerFacade(IAnswerRepository answerRepository, IQuestionRepository questionRepository,
                        IUserRepository userRepository, CommentFacade commentFacade, VoteFacade voteFacade) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.commentFacade = commentFacade;
        this.voteFacade = voteFacade;
    }

    public void giveAnswer(GiveAnswerCommand command) throws InvalidAnswerException {
        try {
            Answer givenAnswer = Answer.builder()
                    .id(command.getId())
                    .authorId(command.getAuthorId())
                    .questionId(command.getQuestionId())
                    .content(command.getText())
                    .createdAt(LocalDateTime.now())
                    .build();
            answerRepository.save(givenAnswer);
        } catch (Exception e) {
            throw new InvalidAnswerException(e.getMessage());
        }
    }

    public AnswersDTO getAnswers(AnswersQuery query) {
        Collection<Answer> allAnswers = answerRepository.find(query);

        List<AnswersDTO.AnswerDTO> allAnswersDTO = allAnswers.stream().map(answer -> {
                    User author = userRepository.findById(answer.getAuthorId()).get();

                    return AnswersDTO.AnswerDTO.builder()
                            .id(answer.getId())
                            .questionId(query.getId())
                            .author(author.getFirstname() + " " + author.getLastname())
                            .content(answer.getContent())
                            .createdAt(answer.getCreatedAt())
                            .comments(commentFacade.getAnswerComments(answer.getId()))
                            .votes(voteFacade.getVotes(VotesQuery.builder()
                                    .user(query.getAuthUserId())
                                    .relatedAnswer(answer.getId())
                                    .build()))
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
