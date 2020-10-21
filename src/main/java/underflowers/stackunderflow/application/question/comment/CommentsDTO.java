package underflowers.stackunderflow.application.question.comment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class CommentsDTO {

    @Builder
    @Getter
    @EqualsAndHashCode
    public static class CommentDTO {
        private UUID uuid;
        private String author;
        private String content;
        private LocalDateTime createdAt;
    }

    @Singular
    private List<CommentDTO> comments;
}
