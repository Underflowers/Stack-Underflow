package underflowers.stackunderflow.domain.question;

import lombok.*;
import underflowers.stackunderflow.domain.IEntity;
import underflowers.stackunderflow.domain.user.UserId;

import java.time.LocalDateTime;

@Data
@Builder (toBuilder = true)
public class Question implements IEntity<Question, QuestionId> {

    @Setter(AccessLevel.NONE)
    private QuestionId id = new QuestionId();
    private UserId authorId;
    private String title;
    private String content;
    private LocalDateTime creationDate;

    public static class QuestionBuilder {
        public Question build() {
            if (id == null)
                id = new QuestionId();

            if (authorId == null)
                throw new IllegalArgumentException("Author is mandatory");

            if (title == null || title.isEmpty())
                throw new IllegalArgumentException("Title is mandatory");

            if (content == null || content.isEmpty())
                throw new IllegalArgumentException("Content is mandatory");

            if (creationDate == null)
                creationDate = LocalDateTime.now();

            return new Question(id, authorId, title, content, creationDate);
        }
    }

    @Override
    public Question deepClone() {
        return Question.builder()
                .id(new QuestionId(this.id.asString()))
                .authorId(authorId)
                .content(content)
                .title(title)
                .creationDate(creationDate)
                .build();
    }
}


