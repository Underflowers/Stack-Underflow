package underflowers.stackunderflow.application.question;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@EqualsAndHashCode
public class QuestionsQuery {

    @Builder.Default
    private boolean isAnswered = false;

    private String searchTerm;
}
