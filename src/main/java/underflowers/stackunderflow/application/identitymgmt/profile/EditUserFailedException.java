package underflowers.stackunderflow.application.identitymgmt.profile;

import underflowers.stackunderflow.application.BusinessException;

public class EditUserFailedException extends BusinessException {
    public EditUserFailedException(String message) {
        super(message);
    }
}
