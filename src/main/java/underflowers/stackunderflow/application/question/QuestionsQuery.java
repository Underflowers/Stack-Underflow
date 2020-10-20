package underflowers.stackunderflow.application.question;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import underflowers.stackunderflow.domain.user.UserId;

@Builder
@Getter
@Setter
@Getter
@EqualsAndHashCode
public class QuestionsQuery {

    @Builder.Default
    private boolean isAnswered = false;

    @Builder.Default
    private UserId authorId = null;

    private String searchTerm;
}
