package underflowers.stackunderflow.application.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.user.User;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class UsersDTO {

    @Builder
    @Getter
    @EqualsAndHashCode
    public static class UserDTO {
        private String username;
    }

    @Singular
    private List<User> users;
}
