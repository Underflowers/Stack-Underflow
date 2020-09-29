package underflowers.stackunderflow.application.identitymgmt.registration;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class RegistrationCommand {
    private String firstname;
    private String lastname;
    private String email;
    private String clearPassword;
    private String repeatPassword;
}
