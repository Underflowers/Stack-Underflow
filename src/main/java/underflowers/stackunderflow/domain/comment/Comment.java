package underflowers.stackunderflow.domain.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import underflowers.stackunderflow.domain.IEntity;
import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

@Data
@Builder(toBuilder = true)
public class Comment implements IEntity<Comment, CommentId> {

    @Setter(AccessLevel.NONE)
    private CommentId id;

    private UserId authorId;
    private AnswerId answerId;
    private QuestionId questionId;

    private String content;

    public static class CommentBuilder {
        public Comment build() {
            System.out.println("building life");
            if (id == null)
                id = new CommentId();

            if (answerId == null && questionId == null)
                throw new java.lang.IllegalArgumentException("A comment needs to reference an Answer OR a Question!");

            if (answerId != null && questionId != null)
                throw new java.lang.IllegalArgumentException("A comment can't reference an Answer AND a Question!");

            if (content == null || content.isEmpty())
                throw new java.lang.IllegalArgumentException("Content is mandatory!");

            return new Comment(id, authorId, answerId, questionId, content);
        }
    }

    @Override
    public Comment deepClone() {
        return Comment.builder()
                .id(new CommentId())
                .authorId(authorId)
                .answerId(answerId)
                .questionId(questionId)
                .content(content)
                .build();
    }
}
