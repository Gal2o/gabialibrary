package gabia.library.exception;

import gabia.library.common.exception.BusinessException;

public class AlreadyRentException extends BusinessException {

    public AlreadyRentException(String errorMessage) {
        super(errorMessage);
    }
}
