package underflowers.stackunderflow.application.identitymgmt.authenticate;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class AuthenticateCommand {
    private String email;
    private String clearPassword;
}
