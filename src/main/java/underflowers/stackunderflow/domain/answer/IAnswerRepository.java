package underflowers.stackunderflow.domain.answer;

import underflowers.stackunderflow.domain.IRepository;
import underflowers.stackunderflow.domain.question.QuestionId;

import java.util.Collection;


public interface IAnswerRepository extends IRepository<Answer, AnswerId> {
    public Collection<Answer> find(QuestionId questionId);
}
