package underflowers.stackunderflow.application.answer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import underflowers.stackunderflow.application.comment.CommentsDTO;
import underflowers.stackunderflow.application.vote.VotesDTO;
import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class AnswersDTO {

    @Builder
    @Getter
    @EqualsAndHashCode
    public static class AnswerDTO {
        private AnswerId uuid;
        private QuestionId questionUuid;
        private String author;
        private String content;
        private LocalDateTime createdAt;

        private VotesDTO votes;
        private CommentsDTO comments;
    }

    @Singular
    private List<AnswerDTO> answers;
}
