package underflowers.stackunderflow.application.question;

import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.domain.user.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionFacade {

    private IQuestionRepository questionRepository;
    private IUserRepository userRepository;

    public QuestionFacade(IQuestionRepository questionRepository, IUserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public void proposeQuestion(ProposeQuestionCommand command) throws IncompleteQuestionException {
        try {
            Question submittedQuestion = Question.builder()
                    .authorUUID(command.getAuthorUUID())
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
                    User author = userRepository.findById(question.getAuthorUUID()).get();

                    return QuestionsDTO.QuestionDTO.builder()
                            .uuid(question.getId().getId())
                            .author(author.getFirstname() + " " + author.getLastname())
                            .title(question.getTitle())
                            .content(question.getContent())
                            .creationDate(question.getCreationDate())
                            .build();
                }
        ).collect(Collectors.toList());

        return QuestionsDTO.builder()
            .questions(allQuestionsDTO)
            .build();
    }
}
