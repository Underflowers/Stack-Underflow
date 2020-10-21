package underflowers.stackunderflow.application.answer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import underflowers.stackunderflow.application.comment.CommentsDTO;
import underflowers.stackunderflow.application.vote.VotesDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class AnswersDTO {

    @Builder
    @Getter
    @EqualsAndHashCode
    public static class AnswerDTO {
        private UUID uuid;
        private String author;
        private String content;
        private LocalDateTime createdAt;

        private VotesDTO votes;
        private CommentsDTO comments;
    }

    @Singular
    private List<AnswerDTO> answers;
}
