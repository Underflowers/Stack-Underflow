package underflowers.stackunderflow.application.question;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

@Builder
@Getter
@EqualsAndHashCode
public class GetQuestionQuery {
    private QuestionId id;

    @Builder.Default
    private UserId authUser = null;
}
