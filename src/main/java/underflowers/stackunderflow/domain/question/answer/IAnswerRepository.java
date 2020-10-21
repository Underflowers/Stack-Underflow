package underflowers.stackunderflow.domain.question.answer;

import underflowers.stackunderflow.application.question.answer.AnswersQuery;
import underflowers.stackunderflow.domain.IRepository;

import java.util.Collection;


public interface IAnswerRepository extends IRepository<Answer, AnswerId> {
    public Collection<Answer> find(AnswersQuery query);
}
