package underflowers.stackunderflow.domain.question.answer;

import lombok.*;
import underflowers.stackunderflow.domain.IEntity;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import java.time.LocalDateTime;

@Data
@Builder (toBuilder = true)
public class Answer implements IEntity<Answer, AnswerId> {

    @Setter(AccessLevel.NONE)
    private AnswerId id;
    private UserId authorUUID;
    private QuestionId questionUUID;
    private String content;
    private LocalDateTime createdAt;

    public static class AnswerBuilder {
        public Answer build() {
            if (id == null)
                id = new AnswerId();

            if (authorUUID == null)
                throw new IllegalArgumentException("Answer must have an author ID.");

            if (questionUUID == null)
                throw new IllegalArgumentException("Answer must have a question ID.");

            if (content == null || content.isEmpty())
                throw new IllegalArgumentException("Answer must have a content.");

            if (createdAt == null)
                createdAt = LocalDateTime.now();

            return new Answer(id, authorUUID, questionUUID, content, createdAt);
        }
    }

    @Override
    public Answer deepClone() {
        return Answer.builder()
                .id(new AnswerId(this.id.asString()))
                .authorUUID(authorUUID)
                .questionUUID(questionUUID)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}


