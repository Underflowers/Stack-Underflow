package underflowers.stackunderflow.application.question.answer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import underflowers.stackunderflow.domain.question.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

@Builder
@Getter
@EqualsAndHashCode
public class GiveAnswerCommand {

    @Builder.Default
    private AnswerId uuid = null;

    @Builder.Default
    private UserId authorUUID = null;

    @Builder.Default
    private QuestionId questionUUID = null;

    @Builder.Default
    private String text = "No content";
}
