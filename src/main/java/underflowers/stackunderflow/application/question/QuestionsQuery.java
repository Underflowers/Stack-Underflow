package underflowers.stackunderflow.application.question;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Builder
@Setter
@EqualsAndHashCode
public class QuestionsQuery {

    @Builder.Default
    private boolean isAnswered = false;
}
