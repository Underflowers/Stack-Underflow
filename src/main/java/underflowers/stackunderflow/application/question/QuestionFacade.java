package underflowers.stackunderflow.application.question;

import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.Question;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionFacade {

    private IQuestionRepository questionRepository;

    public QuestionFacade(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void proposeQuestion(ProposeQuestionCommand command) {
        Question submittedQuestion = Question.builder()
            .author(command.getAuthor())
            .title(command.getTitle())
            .content(command.getText())
            .build();
        questionRepository.save(submittedQuestion);
    }

    public QuestionsDTO getQuestions(QuestionsQuery query) {
        Collection<Question> allQuestions = questionRepository.find(query);

        List<QuestionsDTO.QuestionDTO> allQuestionsDTO = allQuestions.stream().map(question -> QuestionsDTO.QuestionDTO.builder()
            .author(question.getAuthor())
            .title(question.getTitle())
            .content(question.getContent())
            .build()).collect(Collectors.toList());

        return QuestionsDTO.builder()
            .questions(allQuestionsDTO)
            .build();
    }
}
