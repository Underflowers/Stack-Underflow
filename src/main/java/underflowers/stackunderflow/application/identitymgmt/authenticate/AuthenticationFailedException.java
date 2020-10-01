package underflowers.stackunderflow.application.identitymgmt.authenticate;

import underflowers.stackunderflow.application.BusinessException;

public class AuthenticationFailedException extends BusinessException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
