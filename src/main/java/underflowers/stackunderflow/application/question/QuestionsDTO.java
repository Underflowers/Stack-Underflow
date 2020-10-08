package underflowers.stackunderflow.application.question;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import underflowers.stackunderflow.domain.question.Question;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class QuestionsDTO {

    @Builder
    @Getter
    @EqualsAndHashCode
    public static class QuestionDTO {
        private String author;
        private String title;
        private String content;
        private LocalDate creationDate;
    }

    @Singular
    private List<QuestionDTO> questions;
}
