package gabia.library.exception;

import gabia.library.common.exception.BusinessException;

public class InvalidPageValueException extends BusinessException {

    public InvalidPageValueException(String errorMessage) {
        super(errorMessage);
    }

}
