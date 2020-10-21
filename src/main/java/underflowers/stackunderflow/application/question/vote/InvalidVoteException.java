package underflowers.stackunderflow.application.question.vote;

import underflowers.stackunderflow.application.BusinessException;

public class InvalidVoteException extends BusinessException {

    public InvalidVoteException(String message) {
        super(message);
    }
}
