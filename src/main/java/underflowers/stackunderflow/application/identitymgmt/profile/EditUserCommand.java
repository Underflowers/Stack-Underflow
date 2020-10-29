package underflowers.stackunderflow.application.identitymgmt.profile;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class EditUserCommand {
    private String newFirstname;
    private String newLastname;
    private String newEmail;
    private String oldEmail;
    private String clearOldPassword;
    private String clearNewPassword;
}