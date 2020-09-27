package underflowers.stackunderflow.domain.user;

import lombok.*;
import underflowers.stackunderflow.domain.question.QuestionId;

@Data
@Builder (toBuilder = true)
public class User {

    @Setter(AccessLevel.NONE)
    @Builder.Default
    private UserId id = new UserId();

    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
