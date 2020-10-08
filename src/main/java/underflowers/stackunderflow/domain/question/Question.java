package underflowers.stackunderflow.domain.question;

import lombok.*;
import underflowers.stackunderflow.domain.IEntity;
import underflowers.stackunderflow.domain.user.UserId;

import java.time.LocalDate;

@Data
@Builder (toBuilder = true)
public class Question implements IEntity<Question, QuestionId> {

    @Setter(AccessLevel.NONE)
    private QuestionId id = new QuestionId();
    private UserId authorUUID;
    private String title;
    private String content;
    private LocalDate creationDate;

    public static class QuestionBuilder {
        public Question build() {
            if (id == null) {
                id = new QuestionId();
            }

            /*
            if (author == null || author.isEmpty()) {
                throw new IllegalArgumentException("Author is mandatory");
            }*/

            if (title == null || title.isEmpty()) {
                throw new IllegalArgumentException("Title is mandatory");
            }

            if (content == null || content.isEmpty()) {
                throw new IllegalArgumentException("Content is mandatory");
            }

            if (creationDate == null) {
                creationDate = LocalDate.now();
            }

            return new Question(id, authorUUID, title, content, creationDate);
        }
    }

    @Override
    public Question deepClone() {
        return Question.builder()
                .id(new QuestionId())
                .authorUUID(authorUUID)
                .content(content)
                .title(title)
                .creationDate(creationDate)
                .build();
    }
}


