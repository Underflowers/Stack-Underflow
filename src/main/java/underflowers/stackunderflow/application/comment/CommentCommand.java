package underflowers.stackunderflow.application.comment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

@Builder
@Getter
@EqualsAndHashCode
public class CommentCommand {

    private UserId authorId;
    private AnswerId answerId;
    private QuestionId questionId;

    private String content;
}
