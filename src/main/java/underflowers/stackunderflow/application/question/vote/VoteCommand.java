package underflowers.stackunderflow.application.question.vote;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import underflowers.stackunderflow.domain.question.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

@Builder
@Getter
@EqualsAndHashCode
public class VoteCommand {
    @Builder.Default
    private UserId author = null;

    @Builder.Default
    private QuestionId relatedQuestion = null;

    @Builder.Default
    private AnswerId relatedAnswer = null;

    @Builder.Default
    private boolean isUpvote = false;
}
