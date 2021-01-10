package underflowers.stackunderflow.application.identitymgmt.authenticate;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class LeaderboardDTO {
    private String name;

    @Builder
    @Getter
    @EqualsAndHashCode
    public static class LeaderboardEntryDTO {
        private String firstname;
        private String lastname;
        private Integer score;
    }

    @Singular
    private List<LeaderboardDTO.LeaderboardEntryDTO> entries;
}
