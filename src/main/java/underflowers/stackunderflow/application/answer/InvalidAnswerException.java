package underflowers.stackunderflow.application.answer;

import underflowers.stackunderflow.application.BusinessException;

public class InvalidAnswerException extends BusinessException {
    public InvalidAnswerException(String message) {
        super(message);
    }
}
