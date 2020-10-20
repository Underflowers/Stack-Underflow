package underflowers.stackunderflow.domain.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import underflowers.stackunderflow.domain.IEntity;
import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class Comment implements IEntity<Comment, CommentId> {

    @Setter(AccessLevel.NONE)
    private CommentId id;

    private UserId authorId;
    private AnswerId answerId;
    private QuestionId questionId;
    private String content;
    private LocalDateTime createdAt;

    public static class CommentBuilder {
        public Comment build() {
            if (id == null)
                id = new CommentId();

            if (answerId == null && questionId == null)
                throw new java.lang.IllegalArgumentException("A comment needs to reference an Answer OR a Question!");

            if (answerId != null && questionId != null)
                throw new java.lang.IllegalArgumentException("A comment can't reference an Answer AND a Question!");

            if (content == null || content.isEmpty())
                throw new java.lang.IllegalArgumentException("Content is mandatory!");

            if(createdAt == null)
                createdAt = LocalDateTime.now();

            return new Comment(id, authorId, answerId, questionId, content, createdAt);
        }
    }

    @Override
    public Comment deepClone() {
        return Comment.builder()
                .id(new CommentId(this.id.asString()))
                .authorId(authorId)
                .answerId(answerId)
                .questionId(questionId)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}
