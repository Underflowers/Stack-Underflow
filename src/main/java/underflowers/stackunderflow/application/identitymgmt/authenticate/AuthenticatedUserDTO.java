package underflowers.stackunderflow.application.identitymgmt.authenticate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthenticatedUserDTO {
    private String firstname;
    private String lastname;
    private String email;
}
