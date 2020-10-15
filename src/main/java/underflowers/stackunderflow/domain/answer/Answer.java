package underflowers.stackunderflow.domain.answer;

import lombok.*;
import underflowers.stackunderflow.domain.IEntity;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

@Data
@Builder (toBuilder = true)
public class Answer implements IEntity<Answer, AnswerId> {

    @Setter(AccessLevel.NONE)
    private AnswerId id = new AnswerId();
    private UserId authorUUID;
    private QuestionId questionUUID;
    private String content;

    public static class AnswerBuilder {
        public Answer build() {
            if (id == null) {
                id = new AnswerId();
            }

            if (content == null || content.isEmpty()) {
                throw new IllegalArgumentException("Content is mandatory");
            }

            return new Answer(id, authorUUID, questionUUID, content);
        }
    }

    @Override
    public Answer deepClone() {
        return Answer.builder()
                .id(new AnswerId())
                .authorUUID(authorUUID)
                .questionUUID(questionUUID)
                .content(content)
                .build();
    }
}


