package gabia.library.exception;

import gabia.library.common.exception.BusinessException;

public class InvalidIdentifierException extends BusinessException {

    public InvalidIdentifierException(String errorMessage) {
        super(errorMessage);
    }
}
