package underflowers.stackunderflow.application.identitymgmt.registration;

import underflowers.stackunderflow.application.BusinessException;

public class RegistrationFailedException extends BusinessException {
    public RegistrationFailedException(String message) {
        super(message);
    }
}
