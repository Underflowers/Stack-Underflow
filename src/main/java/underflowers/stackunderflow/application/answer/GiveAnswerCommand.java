package underflowers.stackunderflow.application.answer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

@Builder
@Getter
@EqualsAndHashCode
public class GiveAnswerCommand {
    @Builder.Default
    private UserId authorUUID = null;

    @Builder.Default
    private QuestionId questionUUID = null;

    @Builder.Default
    private String text = "No content";
}