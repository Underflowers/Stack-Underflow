package underflowers.stackunderflow.application.answer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import underflowers.stackunderflow.application.comment.CommentsDTO;

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
        private UUID questionUuid;
        private String author;
        private String content;
        private LocalDateTime createdAt;

        private CommentsDTO comments;
    }

    @Singular
    private List<AnswerDTO> answers;
}
