package underflowers.stackunderflow.infrastucture.persistence.memory;

import underflowers.stackunderflow.application.question.QuestionsQuery;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.question.QuestionId;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryQuestionRepository implements IQuestionRepository {
    private Map<QuestionId, Question> store = new ConcurrentHashMap<>();


    @Override
    public Collection<Question> find(QuestionsQuery query) {
        // TODO
        return null;
    }

    @Override
    public void save(Question question) {
        // TODO
    }

    @Override
    public void remove(QuestionId questionId) {
        // TODO
    }

    @Override
    public Optional<Question> findById(QuestionId questionId) {
        // TODO
        return Optional.empty();
    }

    @Override
    public Collection<Question> findAll() {
        // TODO
        return null;
    }
}
