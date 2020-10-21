package underflowers.stackunderflow.domain.question.comment;

import underflowers.stackunderflow.domain.IRepository;
import underflowers.stackunderflow.domain.question.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;

import java.util.Collection;

public interface ICommentRepository extends IRepository<Comment, CommentId> {
    public Collection<Comment> findQuestionComments(QuestionId id);

    public Collection<Comment> findAnswerComments(AnswerId id);
}
