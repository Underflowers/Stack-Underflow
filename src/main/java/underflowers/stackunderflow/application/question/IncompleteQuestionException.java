package underflowers.stackunderflow.application.question;

import underflowers.stackunderflow.application.BusinessException;

public class IncompleteQuestionException extends BusinessException {
    public IncompleteQuestionException(String message) {
        super(message);
    }
}
