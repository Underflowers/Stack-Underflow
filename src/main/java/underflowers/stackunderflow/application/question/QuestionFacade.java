package underflowers.stackunderflow.application.question;

import underflowers.stackunderflow.application.question.answer.AnswerFacade;
import underflowers.stackunderflow.application.question.answer.AnswersQuery;
import underflowers.stackunderflow.application.question.comment.CommentFacade;
import underflowers.stackunderflow.application.question.vote.VotesQuery;
import underflowers.stackunderflow.application.question.vote.VoteFacade;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.domain.user.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionFacade {

    public QuestionFacade() {}

    private IQuestionRepository questionRepository;
    private IUserRepository userRepository;
    private AnswerFacade answerFacade;
    private CommentFacade commentFacade;
    private VoteFacade voteFacade;

    public QuestionFacade(IQuestionRepository questionRepository, IUserRepository userRepository,
                          AnswerFacade answerFacade, CommentFacade commentFacade, VoteFacade voteFacade) {
        this.answerFacade = answerFacade;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.commentFacade = commentFacade;
        this.voteFacade = voteFacade;
    }

    public void proposeQuestion(ProposeQuestionCommand command) throws IncompleteQuestionException {
        try {
            Question submittedQuestion = Question.builder()
                    .id(command.getQuestionId())
                    .authorId(command.getAuthorId())
                    .title(command.getTitle())
                    .content(command.getText())
                    .build();
            questionRepository.save(submittedQuestion);
        } catch (Exception e) {
            throw new IncompleteQuestionException(e.getMessage());
        }
    }

    public QuestionsDTO getQuestions(QuestionsQuery query) {
        Collection<Question> allQuestions = questionRepository.find(query);

        List<QuestionsDTO.QuestionDTO> allQuestionsDTO = allQuestions.stream().map(question -> {
                    User author = userRepository.findById(question.getAuthorId()).get();

                    return QuestionsDTO.QuestionDTO.builder()
                            .uuid(question.getId().getId())
                            .author(author.getFirstname() + " " + author.getLastname())
                            .title(question.getTitle())
                            .content(question.getContent())
                            .creationDate(question.getCreationDate())
                            .answers(answerFacade.getAnswers(AnswersQuery.builder()
                                    .id(question.getId())
                                    .build()))
                            .comments(commentFacade.getQuestionComments(question.getId()))
                            .votes(voteFacade.getVotes(VotesQuery.builder()
                                    .relatedQuestion(question.getId())
                                    .build()))
                            .build();
                }
        ).collect(Collectors.toList());

        return QuestionsDTO.builder()
            .questions(allQuestionsDTO)
            .build();
    }

    public QuestionsDTO.QuestionDTO getQuestion(GetQuestionQuery command) {
        Question question = questionRepository.findById(command.getId()).orElse(null);
        User author = userRepository.findById(question.getAuthorId()).get();


        return QuestionsDTO.QuestionDTO.builder()
                .uuid(question.getId().getId())
                .author(author.getFirstname() + " " + author.getLastname())
                .title(question.getTitle())
                .content(question.getContent())
                .creationDate(question.getCreationDate())
                .answers(answerFacade.getAnswers(AnswersQuery.builder()
                        .id(command.getId())
                        .authUserId(command.getAuthUserId())
                        .build()))
                .comments(commentFacade.getQuestionComments(question.getId()))
                .votes(voteFacade.getVotes(VotesQuery.builder()
                        .user(command.getAuthUserId())
                        .relatedQuestion(command.getId())
                        .build()))
                .build();
    }

    public int countQuestions(QuestionsQuery query) {
        return this.questionRepository.count(query);
    }

    public int countQuestions() {
        return this.questionRepository.count();
    }
}
