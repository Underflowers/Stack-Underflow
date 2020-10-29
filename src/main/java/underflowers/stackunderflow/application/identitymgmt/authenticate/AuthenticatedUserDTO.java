package underflowers.stackunderflow.application.identitymgmt.authenticate;

import lombok.Builder;
import lombok.Value;
import underflowers.stackunderflow.domain.user.UserId;

@Value
@Builder
public class AuthenticatedUserDTO {
    private String firstname;
    private String lastname;
    private String email;
    private UserId userId;
}
