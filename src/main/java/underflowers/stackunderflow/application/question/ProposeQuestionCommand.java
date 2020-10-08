package underflowers.stackunderflow.application.question;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class ProposeQuestionCommand {
    @Builder.Default
    private String author = "Anonymous";

    @Builder.Default
    private String title = "No title";

    @Builder.Default
    private String text = "No content";
}
