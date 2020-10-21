package underflowers.stackunderflow.application.vote;

import underflowers.stackunderflow.application.BusinessException;

public class InvalidVoteException extends BusinessException {

    public InvalidVoteException(String message) {
        super(message);
    }
}
