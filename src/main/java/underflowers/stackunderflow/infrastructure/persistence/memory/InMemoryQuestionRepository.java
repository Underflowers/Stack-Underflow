package underflowers.stackunderflow.infrastructure.persistence.memory;

import underflowers.stackunderflow.application.question.QuestionsQuery;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.question.QuestionId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Collection;

@ApplicationScoped
@Named("InMemoryQuestionRepository")
public class InMemoryQuestionRepository extends InMemoryRepository<Question, QuestionId> implements IQuestionRepository {
    @Override
    public Collection<Question> find(QuestionsQuery query) {
        // TODO
        return super.findAll();
    }
}
