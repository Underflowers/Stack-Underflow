package underflowers.stackunderflow.application.answer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class AnswersQuery {

    @Builder.Default
    private QuestionId questionId = null;

    @Builder.Default
    private UserId authorId = null;
}
