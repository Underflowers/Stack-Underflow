package underflowers.stackunderflow.application.question;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

@Builder
@Getter
@EqualsAndHashCode
public class ProposeQuestionCommand {

    @Builder.Default
    private QuestionId questionUUID = null;

    @Builder.Default
    private UserId authorUUID = null;

    @Builder.Default
    private String title = "No title";

    @Builder.Default
    private String text = "No content";
}
