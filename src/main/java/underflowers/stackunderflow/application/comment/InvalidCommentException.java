package underflowers.stackunderflow.application.comment;

import underflowers.stackunderflow.application.BusinessException;

public class InvalidCommentException extends BusinessException {
    public InvalidCommentException(String message) {
        super(message);
    }
}
