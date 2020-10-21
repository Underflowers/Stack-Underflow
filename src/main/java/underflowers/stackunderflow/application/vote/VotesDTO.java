package underflowers.stackunderflow.application.vote;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
@EqualsAndHashCode
public class VotesDTO {
    private int count;

    @Builder.Default
    private Optional<Boolean> isAuthUserUpvote = Optional.empty();
}

