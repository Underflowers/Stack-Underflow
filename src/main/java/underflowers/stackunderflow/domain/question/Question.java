package underflowers.stackunderflow.domain.question;

import lombok.*;
import underflowers.stackunderflow.domain.IEntity;

@Data
@Builder (toBuilder = true)
public class Question implements IEntity {

    @Setter(AccessLevel.NONE)
    private QuestionId id = new QuestionId();
    private String author;
    private String title;
    private String content;

    public static class QuestionBuilder {
        public Question build() {
            if (id == null) {
                id = new QuestionId();
            }

            if (author == null) {
                author = "";
            }

            if (title == null) {
                title = "";
            }

            if (content == null) {
                content = "";
            }

            return new Question(id, author, title, content);
        }
    }
}


